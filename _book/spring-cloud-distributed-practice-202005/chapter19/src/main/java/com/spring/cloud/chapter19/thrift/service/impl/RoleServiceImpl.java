package com.spring.cloud.chapter19.thrift.service.impl;

import com.spring.cloud.chapter19.thrift.pojo.RolePojo;
import com.spring.cloud.chapter19.thrift.service.RoleService;
import org.apache.thrift.TException;

import java.util.ArrayList;
import java.util.List;

/**** imports ****/
// 实现RoleService.Iface接口
public class RoleServiceImpl implements RoleService.Iface {

    // 实现接口方法
    @Override
    public List<RolePojo> getRoleByUserId(long userId) throws TException {
        List<RolePojo> roleList = new ArrayList<>();
        for (long i = userId ; i < userId + 3; i ++) {
            RolePojo role = new RolePojo();
            role.setId(i);
            role.setRoleName("role_name_" + i);
            role.setNote("note_" + i);
            roleList.add(role);
        }
        return roleList;
    }
}