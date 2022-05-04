CREATE TABLE IF NOT EXISTS t_ecommerce_goods (
    id bigint(20) not null auto_increment comment 'id',
    goods_category varchar(64) not null default '' comment '商品分类',
    brand_category varchar(64) not null default '' comment '品牌分类',
    goods_name varchar(64) not null default '' comment '商品名称',
    goods_pic varchar(256) not null default '' comment '商品图片',
    goods_description varchar(512) not null default '' comment '商品描述信息',
    goods_status int(11) not null default 0 comment '商品状态',
    price int(11) not null default 0 comment '商品价格',
    supply bigint(20) not null default 0 comment '总供应量',
    inventory bigint(20) not null default 0 comment '库存',
    goods_property varchar(1024) not null default '' comment '商品属性',
    create_time datetime not null default '0001-01-01 00:00:00' comment '创建时间',
    update_time datetime not null default '0001-01-01 00:00:00' comment '更新时间',
    primary key (id),
    unique key goods_category_brand_name(goods_category, brand_category, goods_name)
) ENGINE InnoDB DEFAULT CHARSET UTF8 COMMENT '商品信息表';