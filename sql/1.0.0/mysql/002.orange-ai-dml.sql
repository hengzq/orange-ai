-- ---------------------------------------
-- orange-ai 服务预置数据
-- ---------------------------------------

use orange;

-- 默认用户ID
SET @u_id := -100;

-- 默认租户ID
SET @t_id := -100;

-- ----------------------------
-- 预置模型数据
-- ----------------------------
BEGIN;

delete
from ai_model
where id <= 1000;

#阿里大模型
INSERT INTO ai_model (id, tenant_id, platform, name, code, type, enabled, sort, description, created_by)
VALUES (1, @t_id, 'ALI_BAI_LIAN', 'qwen1.5-0.5b-chat', 'qwen1.5-0.5b-chat', 'CHAT', 1, 1,
        '', @u_id),
       (100, @t_id, 'ALI_BAI_LIAN', 'Cosplay动漫人物', 'wanx-style-cosplay-v1', 'TEXT_TO_IMAGE', 1, 1,
        '', @u_id),
       (101, @t_id, 'ALI_BAI_LIAN', 'AnyText图文融合', 'wanx-anytext-v1', 'TEXT_TO_IMAGE', 1, 1,
        '', @u_id);



COMMIT;