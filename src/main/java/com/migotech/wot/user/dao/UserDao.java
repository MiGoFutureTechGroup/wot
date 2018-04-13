package com.migotech.wot.user.dao;

import com.migotech.wot.user.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserDao {
    User login(@Param("name") String name,@Param("password") String password);

    List<User> findByCompanyId(@Param("companyId") long companyId);

    User findOne(@Param("id") long id);

    void editOne(@Param("user")User user);
}
