create table if not exists t_ecommerce_balance (
    id bigint(20) not null auto_increment comment 'ID',
    user_id bigint(20) not null default 0 comment 'user id',
    balance bigint(20) not null default 0 comment '账户余额',
    create_time datetime not null default '0001-01-01 00:00:00' comment '创建时间',
    update_time datetime not null default '0001-01-01 00:00:00' comment '更新时间',
    primary key (id),
    unique key user_id(user_id)
) ENGINE InnoDB default charset utf8 comment '用户账户表';