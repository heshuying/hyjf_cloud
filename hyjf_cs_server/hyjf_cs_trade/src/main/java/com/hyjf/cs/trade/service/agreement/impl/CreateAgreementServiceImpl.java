package com.hyjf.cs.trade.service.agreement.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.bean.result.BaseResult;
import com.hyjf.am.resquest.trade.UserInvestListBeanRequest;
import com.hyjf.am.vo.admin.BorrowCustomizeVO;
import com.hyjf.am.vo.admin.WebProjectRepayListCustomizeVO;
import com.hyjf.am.vo.admin.WebUserInvestListCustomizeVO;
import com.hyjf.am.vo.task.autoreview.BorrowCommonCustomizeVO;
import com.hyjf.am.vo.trade.*;
import com.hyjf.am.vo.trade.borrow.DebtBorrowCustomizeVO;
import com.hyjf.cs.trade.service.agreement.CreateAgreementService;
import com.hyjf.cs.trade.service.impl.BaseTradeServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author pangchengchao
 * @version CreateAgreementServiceImpl, v0.1 2018/11/6 10:18
 */
@Service
public class CreateAgreementServiceImpl extends BaseTradeServiceImpl implements CreateAgreementService {
    @Override
    public JSONObject getIntermediaryAgreementPDFUrl(String nid) {
        JSONObject object=new JSONObject();
        List<TenderAgreementVO> list=amTradeClient.selectTenderAgreementByNid(nid);
        if(list!=null&&list.size()>0){
            TenderAgreementVO agreementVO=list.get(0);
            if(agreementVO.getStatus()==3){
                //本地pdf文件路径
                object.put("pdfUrl",agreementVO.getPdfUrl());
                //合同查看地址
                object.put("viewpdfUrl",agreementVO.getViewpdfUrl());
                //合同下载地址
                object.put("downloadUrl",agreementVO.getDownloadUrl());
                //脱敏图片存放地址
                object.put("imgUrl",agreementVO.getImgUrl());
                object.put("status",BaseResult.SUCCESS);
                object.put("statusDesc",BaseResult.SUCCESS_DESC);
            }else{
                object.put("status",BaseResult.ERROR);
                object.put("statusDesc","暂无可下载协议");
            }
        }else {
            object.put("status",BaseResult.ERROR);
            object.put("statusDesc","暂无可下载协议");
        }
        return object;
    }
}
