package cn.jiiiiiin.module.mngauth.service.impl;

import cn.jiiiiiin.ManagerApp;
import cn.jiiiiiin.module.common.entity.mngauth.Admin;
import cn.jiiiiiin.module.common.entity.mngauth.Role;
import cn.jiiiiiin.module.common.enums.mngauth.ResourceChannelEnum;
import cn.jiiiiiin.module.common.mapper.mngauth.RoleMapper;
import cn.jiiiiiin.module.mngauth.service.IAdminService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.val;
import org.apache.commons.collections.CollectionUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


/**
 * 测试Service
 * https://mrbird.cc/Spring-Boot%20TESTing.html
 */
// 使用spring runner执行测试用例
@RunWith(SpringRunner.class)
// 声明为spring boot的测试用例
@SpringBootTest(classes = ManagerApp.class)
public class AdminServiceImplTest {

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private IAdminService adminService;

    @Autowired
    private RoleMapper roleMapper;

    @Before
    public void before() {
        adminService.save(new Admin().setUsername("user").setPassword("admin_pwd"));
    }

    @Test
    public void findByUsername() {
        val admin = adminService.signInByUsername("user", ResourceChannelEnum.MNG);
        Assert.assertEquals("user", admin.getUsername());
        // 测试EHCache缓存
//        val admin2 = adminService.signInByUsername("user", ResourceChannelEnum.MNG);
    }


    @Test
    public void relationRole() {
        Set<Role> roles = new HashSet<>();
        val adminRole = roleMapper.selectOne(new QueryWrapper<Role>().eq(Role.AUTHORITY_NAME, "ADMIN"));
        val userRole = roleMapper.selectOne(new QueryWrapper<Role>().eq(Role.AUTHORITY_NAME, "DB_ADMIN"));
        roles.add(adminRole);
        roles.add(userRole);
        val admin = adminService.signInByUsername("admin", ResourceChannelEnum.MNG);
        admin.setRoles(roles);
        boolean res = adminService.relationRole(admin);
        Assert.assertTrue(res);
    }

    @After
    public void after() {
        adminService.remove(new QueryWrapper<Admin>().eq("username", "user"));
    }
}