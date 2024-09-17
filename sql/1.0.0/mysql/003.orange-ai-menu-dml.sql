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
VALUES (1000, @t_id, -1, 'AI 大模型', 'ai', 0, '', 'system-settings', 0, 20, @u_id);

-- 部门管理
INSERT INTO sys_menu (id, tenant_id, parent_id, name, permission, preset, path, icon, hidden, sort, created_by)
VALUES (1001, @t_id, 1000, 'AI 聊天', 'system-permission:department:view', 0, 'large-model/chat',
        'system-people-circle', 0, 20, @u_id);

insert into sys_button (id, tenant_id, menu_id, name, permission, preset, sort, created_by)
values (100100, @t_id, 2, '新增', 'system-permission:department:add', 1, 1, @u_id),
       (100101, @t_id, 2, '删除', 'system-permission:department:delete', 1, 10, @u_id),
       (100102, @t_id, 2, '修改', 'system-permission:department:edit', 1, 20, @u_id)
;

INSERT INTO sys_menu (id, tenant_id, parent_id, name, permission, preset, path, icon, hidden, sort, created_by)
VALUES (1002, @t_id, 1000, 'AI 文生图', 'system-permission:department:view', 0, 'large-model/image',
        'system-people-circle', 0, 20, @u_id);

INSERT INTO sys_menu (id, tenant_id, parent_id, name, permission, preset, path, icon, hidden, sort, created_by)
VALUES (1003, @t_id, 1000, '模型管理', 'system-permission:department:view', 0, 'large-model/model',
        'system-people-circle', 0, 20, @u_id);

COMMIT;