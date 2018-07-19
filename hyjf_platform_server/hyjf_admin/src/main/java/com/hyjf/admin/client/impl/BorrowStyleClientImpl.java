package com.hyjf.admin.client.impl;

import com.hyjf.admin.client.BorrowStyleClient;
import com.hyjf.am.response.admin.AdminBorrowStyleResponse;
import com.hyjf.am.resquest.admin.AdminBorrowStyleRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author by xiehuili on 2018/7/12.
 */
@Service
public class BorrowStyleClientImpl implements BorrowStyleClient {
    @Autowired
    private RestTemplate restTemplate;

    /**
     * 查询配置中心还款方式
     *
     * @return
     */
    @Override
    public AdminBorrowStyleResponse borrowStyelInit(AdminBorrowStyleRequest adminRequest){
        String url = "http://AM-TRADE/am-trade/config/borrowstyle/selectBorrowStyleListByPage";
        return restTemplate.postForEntity(url,adminRequest,AdminBorrowStyleResponse.class).getBody();
    }
    /**
     * 根据id查询还款方式
     *
     * @return
     */
    @Override
    public AdminBorrowStyleResponse searchBorrowStyleInfo(AdminBorrowStyleRequest adminRequest){
        String url = "http://AM-TRADE/am-trade/config/borrowstyle/searchBorrowStyleInfo";
        return restTemplate.postForEntity(url,adminRequest,AdminBorrowStyleResponse.class).getBody();
    }
    /**
     * 保存还款方式
     *
     * @return
     */
    @Override
    public AdminBorrowStyleResponse insertBorrowStyle(AdminBorrowStyleRequest adminRequest){
        String url = "http://AM-TRADE/am-trade/config/borrowstyle/insertBorrowStyle";
        return restTemplate.postForEntity(url,adminRequest,AdminBorrowStyleResponse.class).getBody();
    }

    /**
     * 修改还款方式
     *
     * @return
     */
    @Override
    public AdminBorrowStyleResponse updateBorrowStyle(AdminBorrowStyleRequest adminRequest){
        String url = "http://AM-TRADE/am-trade/config/borrowstyle/updateBorrowStyle";
        return restTemplate.postForEntity(url,adminRequest,AdminBorrowStyleResponse.class).getBody();
    }
    /**
     * 根据id删除还款方式
     *
     * @return
     */
    @Override
    public AdminBorrowStyleResponse deleteBorrowStyle(Integer id){
        String url = "http://AM-TRADE/am-trade/config/borrowstyle/deleteBorrowStyle";
        return restTemplate.postForEntity(url,id,AdminBorrowStyleResponse.class).getBody();
    }
    /**
     * 修改还款方式状态
     *
     * @return
     */
    @Override
    public AdminBorrowStyleResponse modifyBorrowStyle(Integer id){
        String url = "http://AM-TRADE/am-trade/config/borrowstyle/modifyBorrowStyle";
        return restTemplate.postForEntity(url,id,AdminBorrowStyleResponse.class).getBody();
    }

    /**
     * 根据主键判断权限维护中权限是否存在
     *
     * @return
     */
    @Override
    public boolean validatorFieldCheck(AdminBorrowStyleRequest adminRequest){
        String url = "http://AM-TRADE/am-trade/config/borrowstyle/validatorFieldCheck";
        return restTemplate.postForEntity(url,adminRequest,boolean.class).getBody();
    }

}
