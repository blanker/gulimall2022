create table if not exists t_ecommerce_order (
    id bigint(20) not null auto_increment comment 'id',
    user_id bigint(20) not null default 0 comment '用户id',
    address_id bigint(20) not null default 0 comment '用户地址记录id',
    order_detail text not null comment '订单详情(json存储 goodsId, count)',
    create_time datetime not null default '0000-01-01 00:00:00' comment '创建时间',
    update_time datetime not null default '0000-01-01 00:00:00' comment '更新时间',
    primary key (id)
) engine innodb default charset =utf8 comment='用户订单表';