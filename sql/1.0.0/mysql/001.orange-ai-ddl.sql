-- ---------------------------------------
-- orange-ai 服务依赖MySql表结构
-- ---------------------------------------
CREATE SCHEMA if not exists `orange` DEFAULT CHARACTER SET utf8mb4;

use orange;

-- ----------------------------
-- 模型管理
-- ----------------------------
CREATE TABLE IF NOT EXISTS `ai_model`
(
    `id`          bigint(20)   NOT NULL COMMENT '表的主键',
    `tenant_id`   bigint(20)   NOT NULL COMMENT '租户id',
    `platform`    varchar(128) NOT NULL COMMENT '模型所属平台',
    `name`        varchar(128) NOT NULL COMMENT '模型名称',
    `code`        varchar(256) NOT NULL COMMENT '模型编码',
    `type`        varchar(256) NOT NULL COMMENT '模型类型',
    `enabled`     tinyint(1)            DEFAULT 1 COMMENT '启用状态 1 启用 0 禁用',
    `sort`        int                   DEFAULT 1 COMMENT '显示顺序',
    `description` text                  DEFAULT NULL COMMENT '模型描述',
    `created_by`  bigint(20)   NOT NULL COMMENT '创建人',
    `created_at`  datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_by`  bigint(20)            DEFAULT NULL COMMENT '更新人',
    `updated_at`  datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB COMMENT = '模型管理';



-- ----------------------------
-- 聊天会话管理
-- ----------------------------
CREATE TABLE IF NOT EXISTS `ai_chat_session`
(
    `id`         bigint(20)   NOT NULL COMMENT '表的主键',
    `tenant_id`  bigint(20)   NOT NULL COMMENT '租户id',
    `user_id`    bigint(20)   NOT NULL COMMENT '用户id',
    `name`       varchar(128) NOT NULL COMMENT '会话名称',
    `platform`   varchar(128) NOT NULL COMMENT '模型所属平台',
    `model_code` varchar(128) NOT NULL COMMENT '模型编码',
    `created_by` bigint(20)   NOT NULL COMMENT '创建人',
    `created_at` datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_by` bigint(20)            DEFAULT NULL COMMENT '更新人',
    `updated_at` datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB COMMENT = '聊天会话管理';

-- ----------------------------
-- 聊天会话记录管理
-- ----------------------------
CREATE TABLE IF NOT EXISTS `ai_chat_session_record`
(
    `id`           bigint(20)  NOT NULL COMMENT '表的主键',
    `tenant_id`    bigint(20)  NOT NULL COMMENT '租户id',
    `user_id`      bigint(20)  NOT NULL COMMENT '用户id',
    `session_id`   bigint(20)  NOT NULL COMMENT '会话id',
    `message_type` varchar(32) NOT NULL COMMENT '消息类型',
    `content`      text COMMENT '会话内容',
    `created_by`   bigint(20)  NOT NULL COMMENT '创建人',
    `created_at`   datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_by`   bigint(20)           DEFAULT NULL COMMENT '更新人',
    `updated_at`   datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB COMMENT = '聊天会话记录管理';

-- ----------------------------
-- 文生图管理
-- ----------------------------
CREATE TABLE IF NOT EXISTS `ai_text_to_image`
(
    `id`         bigint(20)    NOT NULL COMMENT '表的主键',
    `tenant_id`  bigint(20)    NOT NULL COMMENT '租户id',
    `user_id`    bigint(20)    NOT NULL COMMENT '用户id',
    `platform`   varchar(128)  NOT NULL COMMENT '模型所属平台',
    `model_code` varchar(128)  NOT NULL COMMENT '模型编码',
    `prompt`     varchar(1024) NOT NULL COMMENT '提示词',
    `quantity`   SMALLINT               DEFAULT 1 COMMENT '生成图片数量',
    `width`      SMALLINT      NOT NULL COMMENT '生成图片宽度',
    `height`     SMALLINT      NOT NULL COMMENT '生成图片高度',
    `urls`       text          NOT NULL COMMENT '生成图片ULR地址,多个以","分割存储',
    `created_by` bigint(20)    NOT NULL COMMENT '创建人',
    `created_at` datetime      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_by` bigint(20)             DEFAULT NULL COMMENT '更新人',
    `updated_at` datetime      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB COMMENT = '文生图管理';
