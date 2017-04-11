package com.jianan.fingerboard.resonate.server.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.jianan.fingerboard.resonate.server.bean.User;

/**
 * @author by jianan.liu on 17/4/10.
 */
@Mapper
public interface UserDao {

    User selectByName(@Param("userName") String name);
}
