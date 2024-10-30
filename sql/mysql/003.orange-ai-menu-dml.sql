-- ---------------------------------------
-- orange-ai 服务依赖菜单
-- sys_menu ID 范围 [1000,1100)
-- sys_button ID 范围 [1*1000,100*1100)
--
-- 按钮ID范围为：[菜单ID*100, (菜单ID + 1) * 100)
-- eg: 菜单ID：1 按钮ID：[100,200)
-- ---------------------------------------
use orange;

-- 默认用户ID
SET @u_id := -100;

-- 默认租户ID
SET @t_id := -100;

-- ----------------------------
-- 系统管理-菜单和按钮数据预置
-- ----------------------------
BEGIN;


delete
from sys_menu
where id >= 1000
  and id < 1100;

delete
from sys_button
where id >= 100000
  and id < 110000;

-- 系统管理
INSERT INTO sys_menu (id, tenant_id, parent_id, name, permission, preset, path, icon, hidden, sort, created_by)
VALUES (1000, @t_id, -1, 'AI 大模型', 'orange-ai', 0, '', 'system-settings', 0, 20, @u_id);

-- 部门管理
INSERT INTO sys_menu (id, tenant_id, parent_id, name, permission, preset, path, icon, hidden, sort, created_by)
VALUES (1001, @t_id, 1000, 'AI 聊天', 'orange-ai:chat:view', 0, 'large-model/chat',
        'system-people-circle', 0, 20, @u_id);

INSERT INTO sys_menu (id, tenant_id, parent_id, name, permission, preset, path, icon, hidden, sort, created_by)
VALUES (1002, @t_id, 1000, 'AI 文生图', 'orange-ai:text-to-image:view', 0, 'large-model/image',
        'system-people-circle', 0, 20, @u_id);

INSERT INTO sys_menu (id, tenant_id, parent_id, name, permission, preset, path, icon, hidden, sort, created_by)
VALUES (1003, @t_id, 1000, 'AI 模型管理', 'orange-ai:model:view', 0, 'large-model/model', 'system-people-circle', 0, 20,
        @u_id);

INSERT INTO sys_button (id, tenant_id, menu_id, name, permission, preset, sort, created_by)
values (100300, @t_id, 1003, '新增', 'orange-ai:model:add', 1, 1, @u_id),
       (100301, @t_id, 1003, '删除', 'orange-ai:model:delete', 1, 10, @u_id),
       (100302, @t_id, 1003, '修改', 'orange-ai:model:update', 1, 20, @u_id)
;

COMMIT;