package com.blank.gulimall.coupon.controller;

import java.util.Arrays;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import com.blank.gulimall.coupon.entity.CouponEntity;
import com.blank.gulimall.coupon.service.CouponService;
import com.blank.common.utils.PageUtils;
import com.blank.common.utils.R;



/**
 * 优惠券信息
 *
 * @author blank
 * @email blank@gmail.com
 * @date 2022-04-23 17:18:18
 */
@RestController
@RequestMapping("coupon/coupon")
@Slf4j
@RefreshScope
public class CouponController {
    @Autowired
    private CouponService couponService;

    @Value("${coupon.user.name}")
    private String username;
    @Value("${coupon.user.age}")
    private Integer age;

    @RequestMapping("/member/list")
    public R memberCoupons(){
        final CouponEntity couponEntity = new CouponEntity();
        couponEntity.setCouponName("满100减10");
        return R.ok().put("coupons", Arrays.asList(couponEntity));
    }

    @RequestMapping("/test-config")
    public R testConfig(){
        log.info("[{}: {}]", username, age);
        return R.ok().put("username", username).put("age", age);
    }
    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = couponService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		CouponEntity coupon = couponService.getById(id);

        return R.ok().put("coupon", coupon);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody CouponEntity coupon){
		couponService.save(coupon);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody CouponEntity coupon){
		couponService.updateById(coupon);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		couponService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
