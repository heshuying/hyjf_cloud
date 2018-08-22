package com.hyjf.cs.message.service.userdevice;

import com.hyjf.cs.message.bean.ic.UserDeviceUniqueCodeEntity;

import java.util.List;

public interface UserDeviceUniqueCodeService {

    /**
     * 根据唯一标识码查询列表
     * @author zhangyk
     * @date 2018/8/21 17:06
     */
    List<UserDeviceUniqueCodeEntity> selectByUniqueCode(String uniqueCode);

    /**
     * 插入记录
     * @author zhangyk
     * @date 2018/8/21 17:41
     */
    void  insertEntity(UserDeviceUniqueCodeEntity entity);

    /**
     * 更新记录
     * @author zhangyk
     * @date 2018/8/21 17:42
     */
    void  updateEntity(UserDeviceUniqueCodeEntity entity);
}
