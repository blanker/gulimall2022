package com.blank.gulimall.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import com.blank.common.validator.ListValue;
import com.blank.common.validator.group.AddGroup;
import com.blank.common.validator.group.UpdateGroup;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.*;

/**
 * 品牌
 * 
 * @author blank
 * @email blank@gmail.com
 * @date 2022-04-23 13:49:04
 */
@Data
@TableName("pms_brand")
public class BrandEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 品牌id
	 */
	@TableId
	@NotNull(message = "修改必须指定品牌id", groups = {UpdateGroup.class})
	@Null(message = "新增不能指定品牌id", groups = {AddGroup.class})
	private Long brandId;
	/**
	 * 品牌名
	 */
	@NotBlank(message="品牌名必须填写", groups = {AddGroup.class, UpdateGroup.class})
	private String name;
	/**
	 * 品牌logo地址
	 */
	@URL(message = "品牌logo地址必须是合法的url地址", groups = {AddGroup.class, UpdateGroup.class})
	@NotBlank(message = "品牌logo地址必须填写", groups = {AddGroup.class})
	private String logo;
	/**
	 * 介绍
	 */
	private String descript;
	/**
	 * 显示状态[0-不显示；1-显示]
	 */
	@ListValue(values = {0, 1})
	private Integer showStatus;
	/**
	 * 检索首字母
	 */
	@Pattern(regexp = "^[a-zA-Z]$", message = "检索首字母必须是a-z或A-Z的一个字母", groups = {AddGroup.class, UpdateGroup.class})
	@NotBlank(message = "检索首字母必填", groups = {AddGroup.class})
	private String firstLetter;
	/**
	 * 排序
	 */
	@Min(value = 0, message="排序字段不能小于0", groups = {AddGroup.class, UpdateGroup.class})
	@NotNull(message = "排序字段必填", groups = {AddGroup.class})
	private Integer sort;

}
