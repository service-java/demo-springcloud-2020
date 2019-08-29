package cn.jiiiiiin.module.common.mapper.mngauth;


import cn.jiiiiiin.ManagerApp;
import cn.jiiiiiin.module.common.dto.mngauth.Menu;
import cn.jiiiiiin.module.common.entity.mngauth.Resource;
import cn.jiiiiiin.module.common.entity.mngauth.Role;
import cn.jiiiiiin.module.common.enums.mngauth.ResourceChannelEnum;
import cn.jiiiiiin.module.common.enums.mngauth.ResourceTypeEnum;
import cn.jiiiiiin.security.rbac.component.dict.RbacDict;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.sql.SqlHelper;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.annotation.Transient;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

import static cn.jiiiiiin.security.core.dict.CommonConstants.GET;
import static cn.jiiiiiin.security.core.dict.CommonConstants.POST;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ManagerApp.class)
@Slf4j
public class ResourceMapperTest {

    @Autowired
    private ResourceMapper resourceMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    @Transactional
    @Rollback
    public void testInsert() {
        val home = new Resource()
                .setName("首页")
                .setIcon("home")
                .setPath("/index")
                .setLevels(1)
                .setNum(1);
        int homeRes = resourceMapper.insert(home);
        Assert.assertTrue(SqlHelper.retBool(homeRes));
        Assert.assertTrue(home.getId() > 0);

//        val resource = new Resource()
//                .setName("系统设置")
//                .setIcon("cog")
//                .setUrl("/sys")
//                .setLevels(1)
//                .setNum(2);
//        int res = resourceMapper.insert(resource);
//        Assert.assertTrue(SqlHelper.retBool(res));
//        Assert.assertTrue(resource.getId() > 0);
//
//        val resource2 = new Resource()
//                .setPid(resource.getId())
//                .setName("操作员管理")
//                .setIcon("users")
//                .setUrl("/mngauth/admin")
//                .setLevels(2)
//                .setNum(1);
//        int res2 = resourceMapper.insert(resource2);
//        Assert.assertTrue(SqlHelper.retBool(res2));
//
//        val resource3 = new Resource()
//                .setPid(resource.getId())
//                .setName("角色管理")
//                .setIcon("id-badge")
//                .setUrl("/mngauth/role")
//                .setLevels(2)
//                .setNum(2);
//        int res3 = resourceMapper.insert(resource3);
//        Assert.assertTrue(SqlHelper.retBool(res3));
//
//        val resource4 = new Resource()
//                .setPid(resource.getId())
//                .setName("资源管理")
//                .setIcon("tree")
//                .setUrl("/mngauth/resource")
//                .setLevels(2)
//                .setNum(3);
//        int res4 = resourceMapper.insert(resource4);
//        Assert.assertTrue(SqlHelper.retBool(res4));
//
//        val operator = roleMapper.selectOne(new QueryWrapper<Role>().eq(Role.AUTHORITY_NAME, "ADMIN"));
//        val set = new HashSet<Resource>();
//        set.add(resource);
//        set.add(resource2);
//        set.add(resource3);
//        set.add(resource4);
//        operator.setResources(set);
//        int temp = roleMapper.relationResource(operator);
//        Assert.assertTrue(SqlHelper.retBool(temp));
    }

    @Test
    @Transactional
    @Rollback
    public void testAddBtnResource(){
        val resourceMngMenu = resourceMapper.selectById(1061818318517747714L);
        Assert.assertNotNull(resourceMngMenu);
//        val resourceAdd = new Resource()
//                .setPid(resourceMngMenu.getId())
//                .setName("新增资源")
//                .setUrl("resource")
//                .setMethod("POST")
//                .setLevels(3)
//                .setNum(1)
//                .setType(ResourceTypeEnum.BTN);
        val update = new Resource()
                .setPid(resourceMngMenu.getId())
                .setName("修改资源")
                .setUrl("resource")
                .setMethod("UPDATE")
                .setLevels(3)
                .setNum(2)
                .setType(ResourceTypeEnum.BTN);
        int updateres = resourceMapper.insert(update);
        Assert.assertTrue(SqlHelper.retBool(updateres));
        val operator = roleMapper.selectOne(new QueryWrapper<Role>().eq(Role.AUTHORITY_NAME, "ADMIN"));
        val set = operator.getResources();
//        set.add(resourceAdd);
        set.add(update);
        operator.setResources(set);
        int temp = roleMapper.relationResource(operator);
    }

    @Test
    public void testSelectByRoleId() {
        val operator = roleMapper.selectOne(new QueryWrapper<Role>().eq(Role.AUTHORITY_NAME, "ADMIN"));
        Assert.assertNotNull(operator);
        val res = resourceMapper.selectByRoleId(operator.getId(), ResourceChannelEnum.MNG);
        log.info("testSelectByRoleId {}", res);
        Assert.assertNotNull(res);
        Assert.assertTrue(res.size() > 0);
    }

    @Test
    public void testModelMapper() {
        val modelMapper = new ModelMapper();
        val resource = new Resource()
                .setName("控制台")
                .setIcon("home")
                .setLevels(1)
                .setUrl("/")
                .setNum(1)
                .setMethod(GET);
        modelMapper.addMappings(new PropertyMap<Resource, Menu>() {

            @Override
            protected void configure() {
                map().setPath(source.getUrl());
                map().setTitle(source.getName());
            }
        });
        val menu = modelMapper.map(resource, Menu.class);
        log.info("menu {}", menu);
    }
}
