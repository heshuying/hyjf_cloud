package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.BorrowStyleClient;
import com.hyjf.admin.service.BorrowStyleService;
import com.hyjf.am.response.admin.AdminBorrowStyleResponse;
import com.hyjf.am.resquest.admin.AdminBorrowStyleRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author by xiehuili on 2018/7/12.
 */
@Service
public class BorrowStyleServiceImpl implements BorrowStyleService {

    @Autowired
    private BorrowStyleClient borrowStyleClient;

    /**
     * 查询列表
     * @param adminRequest
     * @return
     */
    @Override
    public AdminBorrowStyleResponse borrowStyelInit(AdminBorrowStyleRequest adminRequest){
        return  borrowStyleClient.borrowStyelInit(adminRequest);
    }
    /**
     * 根据id查询还款方式
     * @param adminRequest
     * @return
     */
    @Override
    public AdminBorrowStyleResponse searchBorrowStyleInfo(AdminBorrowStyleRequest adminRequest){
        return  borrowStyleClient.searchBorrowStyleInfo(adminRequest);
    }
    /**
     * 保存还款方式
     * @param adminRequest
     * @return
     */
    @Override
    public AdminBorrowStyleResponse insertBorrowStyle(AdminBorrowStyleRequest adminRequest){
        return  borrowStyleClient.insertBorrowStyle(adminRequest);
    }
    /**
     * 修改还款方式
     * @param adminRequest
     * @return
     */
    @Override
    public AdminBorrowStyleResponse updateBorrowStyle(AdminBorrowStyleRequest adminRequest){
        return  borrowStyleClient.updateBorrowStyle(adminRequest);
    }
    /**
     * 根据id删除还款方式
     * @return
     */
    @Override
    public AdminBorrowStyleResponse deleteBorrowStyle(Integer id){
        return  borrowStyleClient.deleteBorrowStyle(id);
    }
    /**
     * 根据id修改还款方式状态
     * @param id
     * @return
     */
    @Override
    public AdminBorrowStyleResponse modifyBorrowStyle(Integer id){
        return  borrowStyleClient.modifyBorrowStyle(id);
    }
    /**
     * 根据主键判断权限维护中权限是否存在
     *
     * @return
     */
    @Override
    public boolean isExistsPermission(AdminBorrowStyleRequest adminRequest){
        return  borrowStyleClient.validatorFieldCheck(adminRequest);
    }


}
