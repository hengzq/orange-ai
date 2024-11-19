-- ---------------------------------------
-- orange-ai 服务预置数据
-- sys_dict_type ID 范围 [1000,1100)
-- sys_dict_data ID 范围 [1000*100,1100*100+100)
--
-- 字典数据ID：[字典类型ID*100, 字典类型ID*100+100)
-- eg: 字典类型ID：1 字典数据ID：[100,200)
-- ---------------------------------------
use orange;

-- 默认用户ID
SET @u_id := -100;

-- 默认租户ID
SET @t_id := -100;

BEGIN;

delete
from sys_dict_type
where id >= 1000
  and id < 1100;

delete
from sys_dict_data
where id >= 100000
  and id < 110000;
-- ------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--  图片分辨率  ai_image_resolution      type ID范围 [1000]  data ID范围 [100000 - 100100）
-- ------------------------------------------------------------------------------------------------------------------------------------------------------------------------
-- 新增字典类型
INSERT INTO sys_dict_type (id, tenant_id, name, dict_type, enabled, preset, description, created_by)
VALUES (1000, @t_id, 'AI - 图片分辨率', 'ai_image_resolution', 1, 1, 'AI - 图片分辨率', @u_id);

-- 新增字典数据
INSERT INTO sys_dict_data (id, tenant_id, sort, dict_label, dict_value, dict_type, preset, show_style, enabled, description, created_by)
VALUES (100000, @t_id, 1, '512*1024', '512*1024', 'ai_image_resolution', 1, '#409eff', 1, '分辨率-512*1024', @u_id),
       (100001, @t_id, 1, '768*512', '768*512', 'ai_image_resolution', 1, '#909399', 1, '分辨率-768*512', @u_id),
       (100002, @t_id, 1, '768*1024', '768*1024', 'ai_image_resolution', 1, '#87d068', 1, '分辨率-768*1024', @u_id),
       (100003, @t_id, 1, '1024*576', '1024*576', 'ai_image_resolution', 1, '#e6a23c', 1, '分辨率-1024*576', @u_id),
       (100004, @t_id, 1, '576*1024', '576*1024', 'ai_image_resolution', 1, '#e6a23c', 1, '分辨率-576*1024', @u_id),
       (100005, @t_id, 1, '1024*1024', '1024*1024', 'ai_image_resolution', 1, '#f56c6c', 1, '分辨率-1024*1024', @u_id);


COMMIT;


