package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.SendTypeClient;
import com.hyjf.admin.service.SendTypeService;
import com.hyjf.am.response.admin.BorrowSendTypeResponse;
import com.hyjf.am.resquest.admin.BorrowSendTypeRequest;
import com.hyjf.am.vo.admin.BorrowSendTypeVO;
import com.hyjf.am.vo.config.ParamNameVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author by xiehuili on 2018/8/1.
 * @version SendTypeServiceImpl, v0.1
 */
@Service
public class SendTypeServiceImpl implements SendTypeService {

    @Autowired
    private SendTypeClient sendTypeClient;
    /**
     * 查询流程配置中的发标/复审
     * @param adminRequest
     * @return
     */
    @Override
    public BorrowSendTypeResponse selectBorrowSendList(BorrowSendTypeRequest adminRequest){
        return sendTypeClient.selectBorrowSendList(adminRequest);
    }
    /**
     * 查询流程配置中的发标/复审页面
     * @param sendCd
     * @return
     */
    @Override
    public BorrowSendTypeVO getBorrowSendInfo(String sendCd){
        return sendTypeClient.getBorrowSendInfo(sendCd);
    }

    /**
     * 获取数据字典表的下拉列表
     * @param code
     * @return
     */
    @Override
    public List<ParamNameVO> getParamNameList(String code){
        return sendTypeClient.getParamNameList(code);
    }

    /**
     * 数据插入
     * @param adminRequest
     */
    @Override
    public BorrowSendTypeResponse insertBorrowSend(BorrowSendTypeRequest adminRequest){
       return sendTypeClient.insertBorrowSend(adminRequest);
    }

    /**
     * 数据修改
     * @param adminRequest
     */
    @Override
    public BorrowSendTypeResponse updateBorrowSend(BorrowSendTypeRequest adminRequest){
        return sendTypeClient.updateBorrowSend(adminRequest);
    }
    /**
     * 删除
     * @param sendCd
     */
    @Override
    public BorrowSendTypeResponse daleteBorrowSend(String sendCd){
        return sendTypeClient.daleteBorrowSend(sendCd);
    }




}
