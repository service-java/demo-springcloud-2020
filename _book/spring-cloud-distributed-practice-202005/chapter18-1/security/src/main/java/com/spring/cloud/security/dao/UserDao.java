package com.spring.cloud.security.dao;

import com.spring.cloud.security.po.UserPo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserDao {

    public UserPo getUser(@Param("id") Long id);

    public UserPo getUserByUserName(@Param("userName") String userName);
}
