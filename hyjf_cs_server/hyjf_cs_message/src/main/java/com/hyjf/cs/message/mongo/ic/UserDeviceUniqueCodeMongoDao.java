package com.hyjf.cs.message.mongo.ic;

import com.hyjf.cs.message.bean.ic.UserDeviceUniqueCodeEntity;
import org.springframework.stereotype.Repository;

/**
 * 用户请求记录
 * @author zhangyk
 * @date 2018/8/21 16:33
 */
@Repository
public class UserDeviceUniqueCodeMongoDao extends BaseMongoDao<UserDeviceUniqueCodeEntity> {
    @Override
    protected Class<UserDeviceUniqueCodeEntity> getEntityClass() {
        return UserDeviceUniqueCodeEntity.class;
    }
}
