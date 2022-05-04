package com.blank.ecommerce.entity;

import com.alibaba.fastjson.JSON;
import com.blank.ecommerce.constant.BrandCategoryEnum;
import com.blank.ecommerce.constant.GoodsCategoryEnum;
import com.blank.ecommerce.constant.GoodsStatusEnum;
import com.blank.ecommerce.converter.BrandCategoryConverter;
import com.blank.ecommerce.converter.GoodsCategoryConverter;
import com.blank.ecommerce.converter.GoodsStatusConverter;
import com.blank.ecommerce.goods.GoodsInfo;
import com.blank.ecommerce.goods.SimpleGoodsInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name="t_ecommerce_goods")
public class EcommerceGoods {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable = false)
    private Long id;

    @Column(name="goods_category", nullable = false)
    @Convert(converter = GoodsCategoryConverter.class)
    private GoodsCategoryEnum goodsCategory;

    @Column(name="brand_category", nullable = false)
    @Convert(converter = BrandCategoryConverter.class)
    private BrandCategoryEnum brandCategory;

    @Column(name="goods_name", nullable = false)
    private String goodsName;

    @Column(name="goods_pic", nullable = false)
    private String goodsPic;

    @Column(name="goods_description", nullable = false)
    private String goodsDescription;

    @Column(name="goods_status", nullable = false)
    @Convert(converter = GoodsStatusConverter.class)
    private GoodsStatusEnum goodsStatus;

    @Column(name="price", nullable = false)
    private Integer price;

    @Column(name="supply", nullable = false)
    private Long supply;

    @Column(name="inventory", nullable = false)
    private Long inventory;

    @Column(name="goods_property", nullable = false)
    private String goodsProperty;

    @Column(name="create_time", nullable = false)
    @CreatedDate
    private Date createTime;

    @Column(name="update_time", nullable = false)
    @LastModifiedDate
    private Date updateTime;

    public static EcommerceGoods to(GoodsInfo goodsInfo) {
        EcommerceGoods ecommerceGoods = new EcommerceGoods();
        ecommerceGoods.setGoodsPic(goodsInfo.getGoodsPic());
        ecommerceGoods.setGoodsCategory(GoodsCategoryEnum.of(goodsInfo.getGoodsCategory()));
        ecommerceGoods.setGoodsDescription(goodsInfo.getGoodsDescription());
        ecommerceGoods.setGoodsName(goodsInfo.getGoodsName());
        ecommerceGoods.setGoodsStatus(GoodsStatusEnum.ONLINE);
        ecommerceGoods.setBrandCategory(BrandCategoryEnum.of(goodsInfo.getBrandCategory()));
        ecommerceGoods.setPrice(goodsInfo.getPrice());
        ecommerceGoods.setSupply(goodsInfo.getSupply());
        ecommerceGoods.setInventory(goodsInfo.getSupply());
        ecommerceGoods.setGoodsProperty(
                JSON.toJSONString(goodsInfo.getGoodsProperty())
        );
        return ecommerceGoods;
    }

    public GoodsInfo toGoodsInfo(){
        GoodsInfo goodsInfo = new GoodsInfo();
        goodsInfo.setId(id);
        goodsInfo.setGoodsPic(goodsPic);
        goodsInfo.setGoodsCategory(goodsCategory.getCode());
        goodsInfo.setGoodsDescription(goodsDescription);
        goodsInfo.setGoodsName(goodsName);
        goodsInfo.setGoodsStatus(goodsStatus.getStatus());
        goodsInfo.setBrandCategory(brandCategory.getCode());
        goodsInfo.setPrice(price);
        goodsInfo.setSupply(supply);
        goodsInfo.setInventory(inventory);
        goodsInfo.setGoodsProperty(
                JSON.parseObject(goodsProperty, GoodsInfo.GoodsProperty.class)
        );
        goodsInfo.setCreateTime(createTime);
        goodsInfo.setUpdateTime(updateTime);
        return goodsInfo;
    }

    public SimpleGoodsInfo toSimple(){
        SimpleGoodsInfo simpleGoodsInfo = new SimpleGoodsInfo();
        simpleGoodsInfo.setGoodsPic(goodsPic);
        simpleGoodsInfo.setGoodsName(goodsName);
        simpleGoodsInfo.setId(id);
        simpleGoodsInfo.setPrice(price);
        return simpleGoodsInfo;
    }
}
