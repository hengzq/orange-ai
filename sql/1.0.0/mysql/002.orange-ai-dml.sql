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
VALUES (1, @t_id, 'TONGYI', 'qwen1.5-0.5b-chat', 'qwen1.5-0.5b-chat', 'CHAT', 1, 1, '通义千问-开源系列模型-qwen1.5-0.5-chat', @u_id),
       (100, @t_id, 'TONGYI', 'Cosplay动漫人物', 'wanx-style-cosplay-v1', 'TEXT_TO_IMAGE', 1, 1, '通义万相-Cosplay动漫人物生成', @u_id),
       (101, @t_id, 'TONGYI', 'AnyText图文融合', 'wanx-anytext-v1', 'TEXT_TO_IMAGE', 1, 1, '通义万相-AnyText图文融合;', @u_id);



COMMIT;