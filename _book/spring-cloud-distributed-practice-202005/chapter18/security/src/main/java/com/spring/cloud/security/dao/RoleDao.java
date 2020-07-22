package com.spring.cloud.security.dao;

import com.spring.cloud.security.po.RolePo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RoleDao {

    public List<RolePo> findRolesByUserId(@Param("userId") Long userId);
}
