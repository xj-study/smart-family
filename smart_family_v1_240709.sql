
create table if not exists t_game
(
    id           int auto_increment
        primary key,
    code         varchar(10)                          not null,
    name         varchar(50)                          not null comment '游戏名称',
    content      varchar(500)                         null comment '游戏描述',
    disable_flag tinyint(1) default 0                 null,
    update_time  datetime   default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    create_time  datetime   default CURRENT_TIMESTAMP not null,
    constraint code
        unique (code)
)
    comment '游戏表';

create table if not exists t_gift
(
    id           int auto_increment comment '用户礼物id'
        primary key,
    user_id      int      default -1                not null comment '用户id',
    update_time  datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    create_time  datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    name         varchar(50)                        null comment '礼物名称',
    price        float                              null,
    content      varchar(200)                       null comment '内容描述',
    disable_flag tinyint  default 0                 not null
)
    comment '用户的礼物表';

create table if not exists t_order_gift
(
    id           int auto_increment comment '订单id'
        primary key,
    user_id      int                                not null comment '用户id',
    status       int      default 0                 not null comment '订单状态 3、未发货， 4、已发货，5、交易成功，6、交易关闭',
    gift_id      int                                not null comment '礼物id',
    name         varchar(50)                        not null comment '礼物名称',
    num          int                                not null comment '礼物兑换数量',
    content      varchar(200)                       not null comment '礼物内容',
    price        float                              not null comment '礼物单价',
    total_price  float                              not null comment '礼物总价',
    update_time  datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    create_time  datetime default CURRENT_TIMESTAMP not null,
    disable_flag tinyint  default 0                 not null
)
    comment '礼物订单表';

create table if not exists t_record
(
    id           int auto_increment
        primary key,
    user_id      int                                    not null comment '用户id',
    ref_id       int                                    null,
    ref_type     int          default -1                not null comment '相应记录类型',
    content      varchar(500) default ''                null comment '记录json数据',
    update_time  datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    create_time  datetime     default CURRENT_TIMESTAMP not null,
    disable_flag tinyint      default 0                 not null
)
    comment '记录表';

create table if not exists t_story
(
    id           int auto_increment
        primary key,
    user_id      int                                  not null,
    title        varchar(20)                          not null comment '故事标题',
    content      varchar(500)                         not null comment '故事描述',
    cost_amount  int        default 0                 null comment '消耗数量',
    cost_type    int        default 0                 null comment '消耗类型 积分-0',
    update_time  datetime   default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    create_time  datetime   default CURRENT_TIMESTAMP not null,
    disable_flag tinyint(1) default 0                 null,
    status       int        default 0                 null comment '状态 未发布(0) 已发布(1) 下架(2)'
)
    comment '故事表';

create table if not exists t_story_level
(
    id           int auto_increment
        primary key,
    level_order  int                                  not null comment '关卡顺序',
    story_id     int                                  not null,
    title        varchar(20)                          not null comment '故事标题',
    content      varchar(500)                         not null comment '故事描述',
    prize        int        default 0                 null comment '奖励积分',
    ref_type     int        default 0                 null comment '关联内容类型',
    ref_rules    varchar(500)                         null comment '关联内容规则',
    update_time  datetime   default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    create_time  datetime   default CURRENT_TIMESTAMP not null,
    disable_flag tinyint(1) default 0                 null,
    constraint uni_story_order
        unique (level_order, story_id)
)
    comment '故事关卡表';

create table if not exists t_task
(
    id           int auto_increment
        primary key,
    user_id      int                                not null,
    title        varchar(100)                       not null,
    content      varchar(500)                       null,
    task_type    int      default 0                 null comment '任务类型',
    verify_flag  int      default 1                 null comment '是否需要审核',
    disable_flag tinyint  default 0                 null,
    update_time  datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    create_time  datetime default CURRENT_TIMESTAMP null,
    rules        varchar(500)                       null
);

create table if not exists t_task_integral
(
    task_id     int                                not null comment '任务id'
        primary key,
    integral    int      default 0                 not null comment '任务积分',
    update_time datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    create_time datetime default CURRENT_TIMESTAMP not null
)
    comment '任务积分表';

create table if not exists t_task_record
(
    id           int auto_increment
        primary key,
    task_id      int                                not null,
    user_id      int                                not null,
    task_date    date                               not null,
    status       int                                not null,
    update_time  datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    create_time  datetime default CURRENT_TIMESTAMP not null,
    disable_flag tinyint  default 0                 not null,
    constraint uni_task_user_record
        unique (task_id, user_id, task_date)
);

create table if not exists t_user
(
    id           int auto_increment comment '用户id'
        primary key,
    name         varchar(20)                        not null comment '用户登录名称',
    nickname     varchar(50)                        not null comment '用户昵称',
    mobile       varchar(20)                        null comment '用户手机号',
    password     varchar(100)                       not null comment '用户密码',
    update_time  datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '用户更新时间',
    create_time  datetime default CURRENT_TIMESTAMP not null comment '用户创建时间',
    parent_id    int      default -1                null comment '用户父节点id',
    disable_flag tinyint  default 0                 not null
);

create table if not exists t_user_integral
(
    user_id     int                                not null comment '用户id'
        primary key,
    integral    int      default 0                 not null comment '用户积分',
    update_time datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    create_time datetime default CURRENT_TIMESTAMP not null
)
    comment '用户积分表';

create table if not exists t_user_integral_record
(
    id              int auto_increment comment 'id'
        primary key,
    user_id         int                                not null comment '用户id',
    integral_change int                                not null comment '用户积分变化',
    ref_id          int                                null comment '关联事件id',
    update_time     datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    create_time     datetime default CURRENT_TIMESTAMP not null,
    disable_flag    tinyint  default 0                 not null
)
    comment '积分变化记录表';

create table if not exists t_word
(
    id          int auto_increment
        primary key,
    level       int      default 0                 null comment '难度级别',
    en_value    varchar(50)                        not null,
    ch_value    varchar(150)                       not null,
    update_time datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    create_time datetime default CURRENT_TIMESTAMP not null
)
    comment '单词表';

