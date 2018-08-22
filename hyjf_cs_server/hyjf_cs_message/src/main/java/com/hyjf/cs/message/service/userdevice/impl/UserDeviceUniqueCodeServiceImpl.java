package com.hyjf.cs.message.service.userdevice.impl;

import com.hyjf.cs.message.bean.ic.UserDeviceUniqueCodeEntity;
import com.hyjf.cs.message.mongo.ic.UserDeviceUniqueCodeMongoDao;
import com.hyjf.cs.message.service.userdevice.UserDeviceUniqueCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDeviceUniqueCodeServiceImpl implements UserDeviceUniqueCodeService {

    @Autowired
    private UserDeviceUniqueCodeMongoDao mongoDao;

    /**
     * 根据唯一标识码查询列表
     * @author zhangyk
     * @date 2018/8/21 17:06
     */
    @Override
    public List<UserDeviceUniqueCodeEntity> selectByUniqueCode(String uniqueCode) {
        UserDeviceUniqueCodeEntity entity = new UserDeviceUniqueCodeEntity();
        entity.setUniqueIdentifier(uniqueCode);
        Query query = new Query();
        Criteria criteria = Criteria.where("uniqueIdentifier").is(uniqueCode);
        query.addCriteria(criteria);
        List<UserDeviceUniqueCodeEntity> list = mongoDao.find(query);
        return list;
    }

    /**
     * 插入记录
     * @author zhangyk
     * @date 2018/8/21 17:41
     */
    @Override
    public void insertEntity(UserDeviceUniqueCodeEntity entity) {
        mongoDao.insert(entity);
    }


    /**
     * 更新记录
     * @author zhangyk
     * @date 2018/8/21 17:42
     */
    @Override
    public void updateEntity(UserDeviceUniqueCodeEntity entity) {
        mongoDao.save(entity);
    }
}
