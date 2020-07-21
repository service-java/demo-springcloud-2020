package cn.jiiiiiin.module.common.mapper.mngauth;


import cn.jiiiiiin.ManagerApp;
import cn.jiiiiiin.module.common.entity.mngauth.Admin;
import cn.jiiiiiin.module.common.entity.mngauth.Role;
import cn.jiiiiiin.security.rbac.component.dict.RbacDict;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.sql.SqlHelper;
import lombok.val;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ManagerApp.class)
public class RoleMapperTest {

    @Autowired
    private RoleMapper roleMapper;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    @Rollback
    public void testInsert(){
        int res = roleMapper.insert(new Role().setAuthorityName(RbacDict.ROLE_ADMIN_AUTHORITY_NAME).setName("系统管理员").setNum(0));
        Assert.assertTrue(SqlHelper.retBool(res));
        val adminRole = roleMapper.selectOne(new QueryWrapper<Role>().eq(Role.AUTHORITY_NAME, "ADMIN"));
        Assert.assertNotNull(adminRole);
        int res2 = roleMapper.insert(new Role().setAuthorityName(RbacDict.ROLE_DB_ADMIN_AUTHORITY_NAME).setName("数据库管理员").setNum(0).setPid(adminRole.getId()));
        Assert.assertTrue(SqlHelper.retBool(res2));
        int res3 = roleMapper.insert(new Role().setAuthorityName("OPERATOR").setName("部门操作员").setNum(1).setPid(adminRole.getId()));
        Assert.assertTrue(SqlHelper.retBool(res3));
    }

}
