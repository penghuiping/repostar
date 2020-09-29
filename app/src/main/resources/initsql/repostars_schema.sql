CREATE TABLE if not exists `tb_user`
(
    `id`                 varchar(255) primary key,
    `login`              varchar(255),
    `token`              varchar(255),
    `avatar_url`         varchar(255),
    `html_url`           varchar(255),
    `name`               varchar(255),
    `email`              varchar(255),
    `create_time`        datetime,
    `last_modified_time` datetime,
    `enable`             int
)