package com.hyjf.admin.client.impl;

import com.hyjf.admin.client.BankConfigClient;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.AdminBankConfigResponse;
import com.hyjf.am.response.trade.BanksConfigResponse;
import com.hyjf.am.resquest.admin.AdminBankConfigRequest;
import com.hyjf.am.vo.trade.BankConfigVO;
import com.hyjf.am.vo.trade.BanksConfigVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author by xiehuili on 2018/7/11.
 */
@Service
public class BankConfigClientImpl implements BankConfigClient {
    @Autowired
    private RestTemplate restTemplate;
    /**
     * 查询银行配置列表
     * @param adminRequest
     * @return
     */
    @Override
    public AdminBankConfigResponse bankConfigInit(AdminBankConfigRequest adminRequest){
        return restTemplate
                .postForEntity("http://AM-CONFIG/am-config/config/selectBankConfigListByPage" ,adminRequest, AdminBankConfigResponse.class).getBody();
    }
    /**
     * 根据id查询银行配置
     * @param bankId
     * @return
     */
    @Override
    public AdminBankConfigResponse selectBankConfigById(Integer bankId){
        AdminBankConfigResponse res= new AdminBankConfigResponse();
//        return restTemplate
//                .postForEntity("http://AM-CONFIG/am-config/bankconfig/selectBankConfigById" ,adminRequest, AdminBankConfigResponse.class).getBody();
        BanksConfigResponse response = restTemplate
                .getForEntity("http://AM-CONFIG/am-config/config/getBanksConfigByBankId/" + bankId, BanksConfigResponse.class).getBody();
        if (response != null) {
            BanksConfigVO vo=response.getResult();
            if(vo != null){
                BankConfigVO vO = new BankConfigVO();
                BeanUtils.copyProperties(vo,vO);
                res.setResult(vO);
                res.setRtn(Response.SUCCESS);
                return res;
            }
        }
        return null;
    }
    /**
     * 根据银行名称查询银行配置
     * @return
     */
    @Override
    public List<BankConfigVO> getBankConfigRecordList(String bankName){
        AdminBankConfigResponse response = restTemplate
                .postForEntity("http://AM-CONFIG/am-config/config/selectBankConfigByBankName" , bankName, AdminBankConfigResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 添加银行配置
     * @param adminRequest
     * @return
     */
    @Override
    public AdminBankConfigResponse insertBankConfigRecord(AdminBankConfigRequest adminRequest){
        return restTemplate
                .postForEntity("http://AM-CONFIG/am-config/config/insertBankConfig" , adminRequest, AdminBankConfigResponse.class).getBody();
    }

    /**
     * 修改银行配置
     * @param adminRequest
     * @return
     */
    @Override
    public AdminBankConfigResponse updateBankConfigRecord(AdminBankConfigRequest adminRequest){
        return restTemplate
                .postForEntity("http://AM-CONFIG/am-config/config/updadteBankConfig" , adminRequest, AdminBankConfigResponse.class).getBody();
    }
    /**
     * 删除银行配置
     * @param id
     * @return
     */
    @Override
    public AdminBankConfigResponse deleteBankConfigById(Integer id){
        AdminBankConfigResponse response = restTemplate
                .postForEntity("http://AM-CONFIG/am-config/config/deleteBankConfigById" ,id, AdminBankConfigResponse.class).getBody();
        return response;
    }

    /**
     * 上传文件
     * @param request
     * @param response
     * @return
     */
    @Override
    public AdminBankConfigResponse uploadFile(HttpServletRequest request, HttpServletResponse response){
        return restTemplate
                .postForEntity("http://AM-CONFIG/am-config/config/deleteBankConfigById" ,request, AdminBankConfigResponse.class).getBody();
    }

    /**
     * 保存之前的去重校验
     * @param adminBankConfigRequest
     * @return
     */
    @Override
    public AdminBankConfigResponse validateBeforeAction(AdminBankConfigRequest adminBankConfigRequest){
        return restTemplate
                .postForEntity("http://AM-CONFIG/am-config/config/validateFeildBeforeSave" ,adminBankConfigRequest, AdminBankConfigResponse.class).getBody();
    }


}
