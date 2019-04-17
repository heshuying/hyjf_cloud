package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.AmConfigClient;
import com.hyjf.admin.service.BusinessNameMgService;
import com.hyjf.am.response.config.BusinessNameMgResponse;
import com.hyjf.am.resquest.config.BusinessNameMgRequest;
import com.hyjf.am.vo.config.WorkNameVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: yinhui
 * @Date: 2019/4/15 9:55
 * @Version 1.0
 */
@Service
public class BusinessNameMgServiceImpl implements BusinessNameMgService {

    @Autowired
    private AmConfigClient amConfigClient;

    @Override
    public BusinessNameMgResponse searchBusinessName(BusinessNameMgRequest request){
        return amConfigClient.findBusinessName(request) ;
    }

    @Override
    public boolean insertBusinessName(BusinessNameMgRequest request){
        return amConfigClient.insertBusinessName(request) > 0 ? true : false;
    }

    /**
     * 通过ID 业务名称管理
     * @param id
     * @return
     */
    @Override
    public WorkNameVO findBusinessNameById(int id){
        return amConfigClient.findBusinessNameById(id);
    }

    /**
     * 修改业务名称管理
     * @param request
     * @return
     */
    @Override
    public boolean updateBusinessName(BusinessNameMgRequest request){
        return amConfigClient.updateBusinessName(request) > 0 ? true : false;
    }

    /**
     * 修改业务名称管理状态
     * @param request
     * @return
     */
    @Override
    public boolean updateStatusBusinessName(BusinessNameMgRequest request){
        return amConfigClient.updateStatusBusinessName(request) > 0 ? true : false;
    }

    @Override
    public boolean searchBusinessNameUq(BusinessNameMgRequest request,String fun){
        BusinessNameMgResponse response = amConfigClient.findBusinessName(request) ;
        if(response == null ){
            return false;
        }

        if(response.getResultList().size() > 0){
            WorkNameVO vo = response.getResultList().get(0);

            if("update".equals(fun)){

                if(request.getId() != vo.getId()){
                    return false;
                }
            }else{
                return false;
            }

        }
        return true;
    }

    /**
     * 查询业务名称
     * @param request
     * @return
     */
    @Override
    public BusinessNameMgResponse searchBusinessNameList(BusinessNameMgRequest request){
        return amConfigClient.searchBusinessNameList(request) ;
    }
}
