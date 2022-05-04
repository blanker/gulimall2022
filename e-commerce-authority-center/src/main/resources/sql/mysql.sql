create table if not exists t_ecommerce_user (
    id bigint(20) NOT NULL AUTO_INCREMENT comment 'primary key',
    username varchar(64) not null default '' comment '用户名',
    password varchar(256) not null default  '' comment 'MD5加密后的密码',
    extra_info varchar(1024) not null default '' comment '额外信息',
    create_time datetime not null default '0000-01-01 00:00:00' comment '创建时间',
    update_time datetime not null default '0000-01-01 00:00:00' comment '更新时间',
    primary key (id),
    unique key username(username)
) engine InnoDB default charset=utf8 comment='用户表';