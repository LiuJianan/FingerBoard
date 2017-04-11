package com.jianan.fingerboard.resonate.server.service;

import com.jianan.fingerboard.resonate.server.bean.User;
import com.jianan.fingerboard.resonate.server.dao.mapper.UserDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author by jianan.liu on 17/4/10.
 */
@Service
public class ProviderService {

    @Resource
    private UserDao userDao;

    public User query(String name){
        User user = userDao.selectByName(name);
        return user;
    }
}
