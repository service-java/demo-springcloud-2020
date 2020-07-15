package cn.jiiiiiin.module.mngauth.service.impl;

import cn.jiiiiiin.ManagerApp;
import cn.jiiiiiin.module.common.entity.mngauth.Role;
import cn.jiiiiiin.module.mngauth.service.IAdminService;
import cn.jiiiiiin.module.mngauth.service.IRoleService;
import lombok.val;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ManagerApp.class)
public class RoleServiceImplTest {

    @Autowired
    private IRoleService roleService;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }
}