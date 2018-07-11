package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.common.service.BaseServiceImpl;
import com.hyjf.admin.service.BatchBorrowRecoverService;
import com.hyjf.am.response.admin.BatchBorrowRecoverReponse;
import com.hyjf.am.resquest.admin.BatchBorrowRecoverRequest;
import com.hyjf.am.vo.admin.BatchBorrowRecoverVo;
import com.hyjf.am.vo.config.ParamNameVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther:yangchangwei
 * @Date:2018/7/7
 * @Description:
 */
@Service
public class BatchBorrowRecoverServiceImpl extends BaseServiceImpl implements BatchBorrowRecoverService{


    @Autowired
    private AmTradeClient amTradeClient;

    /**
     * 获取批次放款的显示列表
     * @param request
     * @return
     */
    @Override
    public BatchBorrowRecoverReponse queryBatchBorrowRecoverList(BatchBorrowRecoverRequest request) {

        BatchBorrowRecoverReponse reponse = amTradeClient.getBatchBorrowRecoverList(request);
        return reponse;
    }

    /**
     * 获取批次内容的显示状态描述
     * @param listAccountDetail
     */
    @Override
    public void queryBatchCenterStatusName(List<BatchBorrowRecoverVo> listAccountDetail,String nameClass) {
        //获取放款相关状态描述
        List<ParamNameVO> paramNameList = this.getParamNameList(nameClass);

        for (BatchBorrowRecoverVo vo:
             listAccountDetail) {
            String paramName = this.getParamName(vo.getStatus(), paramNameList);
            vo.setStatusStr(paramName);
        }
    }

    @Override
    public BatchBorrowRecoverVo queryBatchCenterListSum(BatchBorrowRecoverRequest request) {
        BatchBorrowRecoverReponse reponse = amTradeClient.getBatchBorrowCenterListSum(request);
        if(reponse != null){
            return reponse.getResult();
        }
        return null;
    }
}
