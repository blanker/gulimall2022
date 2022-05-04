
CREATE TABLE  IF NOT EXISTS t_ecommerce_address(
   id BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
   user_id bigint(20) NOT NULL DEFAULT 0 COMMENT '用户ID',
   username varchar(64) NOT NULL DEFAULT '' COMMENT '用户名',
   phone varchar(64) NOT NULL DEFAULT '' COMMENT '电话号码',
   province varchar(64) NOT NULL DEFAULT '' COMMENT '省',
   city varchar(64) NOT NULL DEFAULT '' COMMENT '市',
   address_detail varchar(256) NOT NULL DEFAULT '' COMMENT '详细地址',
   create_time datetime NOT NULL DEFAULT '0001-01-01 00:00:00' COMMENT '创建时间',
   update_time datetime NOT NULL DEFAULT '0001-01-01 00:00:00' COMMENT '更新时间',
   primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户地址表'
;
