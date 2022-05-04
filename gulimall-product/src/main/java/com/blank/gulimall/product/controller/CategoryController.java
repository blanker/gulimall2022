package com.blank.gulimall.product.controller;

import com.blank.common.utils.PageUtils;
import com.blank.common.utils.R;
import com.blank.gulimall.product.entity.CategoryEntity;
import com.blank.gulimall.product.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;



/**
 * 商品三级分类
 *
 * @author blank
 * @email blank@gmail.com
 * @date 2022-04-23 13:49:04
 */
@RestController
@RequestMapping("product/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 列表
     */
    @RequestMapping("/list/tree")
    public R list(@RequestParam Map<String, Object> params){
        List<CategoryEntity> list = categoryService.listTree();
        return R.ok().put("data", list);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{catId}")
    public R info(@PathVariable("catId") Long catId){
		CategoryEntity category = categoryService.getById(catId);

        return R.ok().put("data", category);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody CategoryEntity category){
		categoryService.save(category);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody CategoryEntity category){
		categoryService.updateById(category);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update/sort")
    public R updateSort(@RequestBody List<CategoryEntity> categories){
        categoryService.updateBatchById(categories);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] catIds){
//		categoryService.removeByIds(Arrays.asList(catIds));
		categoryService.removeMenusByIds(Arrays.asList(catIds));

        return R.ok();
    }

}
