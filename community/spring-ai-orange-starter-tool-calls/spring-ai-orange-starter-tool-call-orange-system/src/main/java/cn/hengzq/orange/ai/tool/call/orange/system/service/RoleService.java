package cn.hengzq.orange.ai.tool.call.orange.system.service;

import cn.hengzq.orange.system.common.biz.role.vo.RoleVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class RoleService {

    @Tool(description = "Welcome users to the library")
    void welcome() {
        log.info("Welcoming users to the library");
    }

    @Tool(description = "欢迎特定用户访问图书馆")
    void welcomeUser(String user) {
        log.error("..............xxx");
        log.info("欢迎 {} 进入博物馆", user);
        System.out.println("xv欢迎 {} 进入博物馆" + user);
    }

    @Tool(description = "获取当前用户的角色列表")
    List<RoleVO> booksByAuthor(String author) {
        log.info("Getting books by author: {}", author);
        List<RoleVO> roleVOList = new ArrayList<>();
        RoleVO roleVO = new RoleVO();
        roleVO.setId("1");
        roleVO.setName("管理员");
        roleVO.setPermission("admin");
        roleVO.setSort(1);
        roleVO.setEnabled(true);
        roleVO.setPreset(true);
        roleVO.setRemark("管理员");
        roleVOList.add(roleVO);
        return roleVOList;
    }
}
