package com.blank.ecommerce.service.async;

import cn.hutool.core.lang.hash.Hash;
import com.alibaba.fastjson.JSON;
import com.blank.ecommerce.constant.GoodsConstant;
import com.blank.ecommerce.dao.EcommerceGoodsDao;
import com.blank.ecommerce.entity.EcommerceGoods;
import com.blank.ecommerce.goods.GoodsInfo;
import com.blank.ecommerce.goods.SimpleGoodsInfo;
import com.blank.ecommerce.service.async.IAsyncService;
import io.jsonwebtoken.lang.Collections;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class AsyncServiceImpl implements IAsyncService {
    @Autowired
    private EcommerceGoodsDao goodsDao;
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    @Async()
//    @Async("getAsyncExecutor")
    public void asyncImportGoods(List<GoodsInfo> goodsInfos, String taskId) {
        log.info("async task running, taskId: [{}]", taskId);
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        boolean illegal = false;
        Set<String> jointGoodsInfo = new HashSet<>(goodsInfos.size());
        List<GoodsInfo> filteredGoodsInfos = new ArrayList<>(goodsInfos.size());

        for (GoodsInfo goods : goodsInfos) {
            if (goods.getPrice() <= 0 || goods.getSupply() <= 0) {
                log.warn("invalid goods: [{}]", JSON.toJSONString(goods));
                continue;
            }
            String jointInfo = String.format("%s,%s,%s",
                    goods.getGoodsCategory(), goods.getBrandCategory(),
                    goods.getGoodsName());
            if (jointGoodsInfo.contains(jointInfo)) {
                illegal = true;
            }
            jointGoodsInfo.add(jointInfo);
            filteredGoodsInfos.add(goods);
        }

        if (illegal || Collections.isEmpty(filteredGoodsInfos)) {
            stopWatch.stop();
            log.warn("import nothing: [{}]", JSON.toJSONString(filteredGoodsInfos));
            log.info("check and import goods done, taskId: [{}], totalTime:[{}ms] ", taskId, stopWatch.getTotalTimeMillis());
            return;
        }

        final List<EcommerceGoods> ecommerceGoods = filteredGoodsInfos.stream()
                .map(EcommerceGoods::to)
                .collect(Collectors.toList());

        List<EcommerceGoods> targetGoods = new ArrayList<>(ecommerceGoods.size());
        ecommerceGoods.forEach(goods -> {
            if (null != goodsDao.findFirst1ByGoodsCategoryAndBrandCategoryAndGoodsName(
                    goods.getGoodsCategory(), goods.getBrandCategory(), goods.getGoodsName()
            ).orElse(null)) {
                return;
            }
            targetGoods.add(goods);
        });

        final List<EcommerceGoods> savedGoods
                = IterableUtils.toList(goodsDao.saveAll(targetGoods));
        log.info("save goods info to db and redis: [{}]", savedGoods.size());
        saveGoodsToRedis(savedGoods);

        stopWatch.stop();
        log.info("check and import goods success, taskId: [{}], totalTime:[{}ms] ", taskId, stopWatch.getTotalTimeMillis());
    }

    private void saveGoodsToRedis(List<EcommerceGoods> savedGoods) {
        final List<SimpleGoodsInfo> simpleGoodsInfos = savedGoods.stream()
                .map(EcommerceGoods::toSimple)
                .collect(Collectors.toList());
        Map<String, String> id2JsonObject = new HashMap<>(simpleGoodsInfos.size());
        simpleGoodsInfos.forEach(
                g -> id2JsonObject.put(g.getId().toString(), JSON.toJSONString(g))
        );

        redisTemplate.opsForHash().putAll(
                GoodsConstant.ECOMMERCE_GOODS_DICT_KEY,
                id2JsonObject
        );
    }
}
