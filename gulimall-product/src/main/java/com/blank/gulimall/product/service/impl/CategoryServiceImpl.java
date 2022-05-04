package com.blank.gulimall.product.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blank.common.utils.PageUtils;
import com.blank.common.utils.Query;

import com.blank.gulimall.product.dao.CategoryDao;
import com.blank.gulimall.product.entity.CategoryEntity;
import com.blank.gulimall.product.service.CategoryService;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<CategoryEntity> listTree() {
        // 获取时就按照级别和排序字段进行排序，保证第一级/第二级/第三级的顺序
        final List<CategoryEntity> list = baseMapper.selectList(
                Wrappers.lambdaQuery(CategoryEntity.class)
                        .orderByAsc(CategoryEntity::getCatLevel)
                        .orderByAsc(CategoryEntity::getSort)
        );

        List<CategoryEntity> result = new ArrayList<>();

        list.stream().forEach(category -> {
            // 一级菜单
            if (category.getParentCid() == 0) {
                category.setChildren(new ArrayList<>());
                result.add(category);
            }
            // 二级/三级菜单
            else {
                setChildren(result, category);
            }
        });

        return result;
    }

    @Override
    public void removeMenusByIds(List<Long> asList) {
        // TODO 检查当前删除的菜单是否被引用

        baseMapper.deleteBatchIds(asList);
    }

    private boolean setChildren(List<CategoryEntity> root, CategoryEntity child) {
        for(CategoryEntity cat : root) {
            if (cat.getChildren() == null) {
                cat.setChildren(new ArrayList<>());
            }

            if (cat.getCatId() == child.getParentCid()) {
                cat.getChildren().add(child);
                return true;
            }
            if (setChildren(cat.getChildren(), child)){
                return true;
            }
        };
        return false;
    }

    @Override
    public Long[] getCategoryPath(Long categoryId) {
        List<Long> path = new ArrayList<>();
        getParentPath(categoryId, path);
        Collections.reverse(path);
        return path.toArray(new Long[0]);
    }

    private void getParentPath(Long categoryId, List<Long> path) {
        if (categoryId == 0) {
            return;
        }

        path.add(categoryId);
        final CategoryEntity category = getById(categoryId);
        if (category == null ) {
            return ;
        }
        getParentPath(category.getParentCid(), path);
    }
}