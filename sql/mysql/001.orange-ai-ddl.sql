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


-- ----------------------------
-- 知识库管理
-- ----------------------------
CREATE TABLE IF NOT EXISTS `ai_knowledge_base`
(
    `id`                 bigint(20)   NOT NULL COMMENT '表的主键',
    `tenant_id`          bigint(20)   NOT NULL COMMENT '租户id',
    `name`               varchar(128) NOT NULL COMMENT '知识库名称',
    `embedding_model_id` bigint(20)   NOT NULL COMMENT '嵌入式模型Id',
    `enabled`            boolean               DEFAULT TRUE COMMENT '启用状态 true:启用 false:禁用',
    `sort`               int                   DEFAULT 1 COMMENT '显示顺序',
    `description`        text                  DEFAULT NULL COMMENT '知识库描述',
    `created_by`         bigint(20)   NOT NULL COMMENT '创建人',
    `created_at`         datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_by`         bigint(20)            DEFAULT NULL COMMENT '更新人',
    `updated_at`         datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB COMMENT = '知识库管理';


-- ----------------------------
-- 知识库管理 - 知识文档
-- ----------------------------
CREATE TABLE IF NOT EXISTS `ai_knowledge_document`
(
    `id`         bigint(20)    NOT NULL COMMENT '表的主键',
    `tenant_id`  bigint(20)    NOT NULL COMMENT '租户id',
    `base_id`    bigint(20)    NOT NULL COMMENT '知识库ID',
    `file_name`  varchar(1024) NOT NULL COMMENT '文件名',
    `file_path`  varchar(1024)          DEFAULT NULL COMMENT '文件存储路径',
    `created_by` bigint(20)    NOT NULL COMMENT '创建人',
    `created_at` datetime      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_by` bigint(20)             DEFAULT NULL COMMENT '更新人',
    `updated_at` datetime      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB COMMENT = '知识库管理 - 知识文档';

-- ----------------------------
-- 知识库管理 - 知识文档段落切片
-- ----------------------------
CREATE TABLE IF NOT EXISTS `ai_knowledge_document_segment`
(
    `id`          bigint(20) NOT NULL COMMENT '表的主键',
    `tenant_id`   bigint(20) NOT NULL COMMENT '租户id',
    `base_id`     bigint(20) NOT NULL COMMENT '知识库ID',
    `document_id` bigint(20) NOT NULL COMMENT '文档ID',
    `content`     text                DEFAULT NULL COMMENT '段落内容',
    `created_by`  bigint(20) NOT NULL COMMENT '创建人',
    `created_at`  datetime   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_by`  bigint(20)          DEFAULT NULL COMMENT '更新人',
    `updated_at`  datetime   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB COMMENT = '知识库管理 - 知识文档段落切片';


-- ----------------------------
-- 智能体库表
-- ----------------------------
CREATE TABLE IF NOT EXISTS `ai_agent`
(
    `id`            bigint(20)   NOT NULL COMMENT '表的主键',
    `tenant_id`     bigint(20)   NOT NULL COMMENT '租户id',
    `name`          varchar(512) NOT NULL COMMENT '智能体名称',
    `model_id`      bigint(20)            DEFAULT NULL COMMENT '模型ID',
    `base_ids`      varchar(1024)         DEFAULT NULL COMMENT '关联知识库IDS，多一个以","分割',
    `system_prompt` text                  DEFAULT NULL COMMENT '系统提示词',
    `description`   varchar(2048)         DEFAULT NULL COMMENT '智能体描述',
    `created_by`    bigint(20)   NOT NULL COMMENT '创建人',
    `created_at`    datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_by`    bigint(20)            DEFAULT NULL COMMENT '更新人',
    `updated_at`    datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB COMMENT = '智能体库表';


-- ----------------------------
-- 聊天会话管理
-- ----------------------------
CREATE TABLE IF NOT EXISTS `ai_session`
(
    `id`             bigint(20)   NOT NULL COMMENT '表的主键',
    `tenant_id`      bigint(20)   NOT NULL COMMENT '租户id',
    `user_id`        bigint(20)   NOT NULL COMMENT '用户id',
    `model_id`       bigint(20)   NOT NULL COMMENT '模型ID',
    `name`           varchar(128) NOT NULL COMMENT '会话名称',
    `source`         varchar(64)           DEFAULT NULL COMMENT '会话来源',
    `association_id` bigint(20)            DEFAULT NULL COMMENT '关联ID，eg:智能体ID等',
    `created_by`     bigint(20)            DEFAULT NULL COMMENT '创建人',
    `created_at`     datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_by`     bigint(20)            DEFAULT NULL COMMENT '更新人',
    `updated_at`     datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB COMMENT = '聊天会话管理';

-- ----------------------------
-- 聊天会话消息管理
-- ----------------------------
CREATE TABLE IF NOT EXISTS `ai_session_message`
(
    `id`         bigint(20)  NOT NULL COMMENT '表的主键',
    `tenant_id`  bigint(20)  NOT NULL COMMENT '租户id',
    `parent_id`  bigint(20)  NOT NULL COMMENT '父级ID -1表示顶级',
    `session_id` bigint(20)  NOT NULL COMMENT '会话id',
    `role`       varchar(32) NOT NULL COMMENT '消息类型',
    `content`    text                 DEFAULT NULL COMMENT '会话内容',
    `created_by` bigint(20)           DEFAULT NULL COMMENT '创建人',
    `created_at` datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_by` bigint(20)           DEFAULT NULL COMMENT '更新人',
    `updated_at` datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB COMMENT = '聊天会话记录管理';

