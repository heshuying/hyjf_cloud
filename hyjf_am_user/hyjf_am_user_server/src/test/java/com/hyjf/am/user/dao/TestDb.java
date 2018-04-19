package com.hyjf.am.user.dao;

import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.hyjf.am.user.AmUserApplication;
import com.hyjf.am.user.dao.mapper.auto.UsersMapper;
import com.hyjf.am.user.dao.model.auto.Users;

/**
 * @author xiasq
 * @version TestDb, v0.1 2018/4/19 11:31
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AmUserApplication.class)
public class TestDb {
    Logger logger = LoggerFactory.getLogger(TestDb.class);

    @Autowired
    UsersMapper usersMapper;

    @Test
    public void getUser(){
        Users users = usersMapper.selectByPrimaryKey(1);
        if(users !=null)
        logger.info("users is :{}", JSONObject.toJSONString(users));
        logger.info("none this userId");
    }
}
