package com.hyjf.admin.client.impl;

import com.hyjf.admin.client.SendTypeClient;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.BorrowSendTypeResponse;
import com.hyjf.am.response.config.ParamNameResponse;
import com.hyjf.am.resquest.admin.BorrowSendTypeRequest;
import com.hyjf.am.vo.admin.BorrowSendTypeVO;
import com.hyjf.am.vo.config.ParamNameVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * @author by xiehuili on 2018/8/1.
 */
@Service
public class SendTypeClientImpl implements SendTypeClient {
    @Autowired
    private RestTemplate restTemplate;
    /**
     * 查询流程配置中的发标/复审
     * @param adminRequest
     * @return
     */
    @Override
    public BorrowSendTypeResponse selectBorrowSendList(BorrowSendTypeRequest adminRequest){
        String url = "http://AM-TRADE/am-trade/config/sendtype/list";
        BorrowSendTypeResponse response = restTemplate.postForEntity(url,adminRequest,BorrowSendTypeResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }
    /**
     * 查询流程配置中的发标/复审页面
     * @param sendCd
     * @return
     */
    @Override
    public BorrowSendTypeVO getBorrowSendInfo(String sendCd){
        String url = "http://AM-TRADE/am-trade/config/sendtype/info/"+sendCd;
        BorrowSendTypeVO response = restTemplate.getForEntity(url,BorrowSendTypeVO.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    /**
     * 获取数据字典表的下拉列表
     * @param code
     * @return
     */
    @Override
    public List<ParamNameVO> getParamNameList(String code){
        List<ParamNameVO> paramNameVOS = new ArrayList<>();
        String url = "http://AM-CONFIG/am-config/accountconfig/getParamNameList/"+code;
        ParamNameResponse response =restTemplate.getForEntity(url,ParamNameResponse.class).getBody();
        List<ParamNameVO> paramNames = new ArrayList<>();
        if (Response.isSuccess(response)) {
            paramNames= response.getResultList();
            return paramNameVOS;
        }
        return null;
    }

    /**
     * 数据插入
     * @param adminRequest
     */
    @Override
    public BorrowSendTypeResponse insertBorrowSend(BorrowSendTypeRequest adminRequest){
        String url = "http://AM-TRADE/am-trade/config/sendtype/insert";
        BorrowSendTypeResponse response = restTemplate.postForEntity(url,adminRequest,BorrowSendTypeResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    /**
     * 数据修改
     * @param adminRequest
     */
    @Override
    public BorrowSendTypeResponse updateBorrowSend(BorrowSendTypeRequest adminRequest){
        String url = "http://AM-TRADE/am-trade/config/sendtype/update";
        BorrowSendTypeResponse response = restTemplate.postForEntity(url,adminRequest,BorrowSendTypeResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }
    /**
     * 删除
     * @param sendCd
     */
    @Override
    public BorrowSendTypeResponse daleteBorrowSend(String sendCd){
        String url = "http://AM-TRADE/am-trade/config/sendtype/delete/"+sendCd;
        BorrowSendTypeResponse response = restTemplate.getForEntity(url,BorrowSendTypeResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

}
