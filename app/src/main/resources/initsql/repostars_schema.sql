CREATE TABLE if not exists `tb_user`
(
    `id`                 bigint primary key,
    `login`              varchar(255) unique,
    `token`              varchar(255),
    `avatar_url`         varchar(255),
    `html_url`           varchar(255),
    `name`               varchar(255),
    `email`              varchar(255),
    `create_time`        datetime,
    `last_modified_time` datetime,
    `last_login_time`    datetime,
    `enable`             int
);

CREATE TABLE if not exists `tb_gist`
(
    `id`                 bigint primary key,
    `name`               varchar(255),
    `full_name`          varchar(255),
    `html_url`           varchar(255),
    `description`        varchar(255),
    `language`           varchar(255),
    `readme`             text,
    `forks`              datetime,
    `watchers`           datetime,
    `create_time`        datetime,
    `last_modified_time` datetime,
    `login`              varchar(255),
    `enable`             int,
    `is_join_group`      bit
);

CREATE TABLE if not exists `tb_repos`
(
    `id`                 bigint primary key,
    `name`               varchar(255),
    `full_name`          varchar(255),
    `html_url`           varchar(255),
    `description`        varchar(255),
    `language`           varchar(255),
    `readme`             text,
    `forks`              datetime,
    `watchers`           datetime,
    `create_time`        datetime,
    `last_modified_time` datetime,
    `login`              varchar(255),
    `enable`             int
);

CREATE TABLE if not exists `tb_group`
(
    `id`    bigint primary key,
    `name`  varchar(255),
    `login` varchar(255)
);

CREATE TABLE if not exists `tb_group_gist`
(
    `gist_id`  bigint primary key,
    `group_id` bigint
);