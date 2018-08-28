package com.hyjf.cs.trade.service.projectlist.impl;

import com.hyjf.am.vo.api.ApiProjectListCustomize;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.common.bean.result.ApiResult;
import com.hyjf.cs.trade.bean.BaseBean;
import com.hyjf.cs.trade.bean.api.ApiBorrowReqBean;
import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.cs.trade.service.projectlist.ApiProjectListService;
import com.hyjf.cs.trade.service.svrcheck.CommonSvrChkService;
import com.hyjf.cs.trade.util.ProjectConstant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * api端 业务service
 * @author zhangyk
 * @date 2018/8/27 10:06
 */
@Service
public class ApiProjectListServiceImpl implements ApiProjectListService {


    @Autowired
    private CommonSvrChkService commonSvrChkService;

    @Autowired
    private AmTradeClient amTradeClient;

    /**
     * 查询标的列表
     *
     * @author zhangyk
     * @date 2018/8/27 10:12
     */
    @Override
    public ApiResult getBorrowList(ApiBorrowReqBean reqBean) {
        ApiResult result = new ApiResult();
        // 验证必填参数和分页参数
        commonSvrChkService.checkRequired(reqBean);
        commonSvrChkService.checkLimit(reqBean.getLimitStart(), reqBean.getLimitEnd());
        // 标的状态验证
        CheckUtil.check(Validator.isNotNull(reqBean.getBorrowStatus()), MsgEnum.STATUS_CE000001);
        // 标的状态=2投资中 验证
        CheckUtil.check(reqBean.getBorrowStatus().equals("2"), MsgEnum.ERR_OBJECT_VALUE, "borrowStatus");

        // 验签
        CheckUtil.check(ProjectConstant.verifyRequestSign(reqBean, ProjectConstant.API_METHOD_BORROW_LIST),
                MsgEnum.ERR_SIGN);

        List<ApiProjectListCustomize> list = searchProjectListNew(reqBean);
        result.setData(list);
        return result;
    }

    /**
     * 查询逻辑
     * @author zhangyk
     * @date 2018/8/27 14:06
     */
    private  List<ApiProjectListCustomize> searchProjectListNew(ApiBorrowReqBean bean){
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("projectType", "HZT");
        params.put("borrowClass", "");
        params.put("status", bean.getBorrowStatus());

        // add by xiashuqing 20171130 begin
        // 定向标过滤
        params.put("publishInstCode", bean.getInstCode());
        // add by xiashuqing 20171130 end

        params.put("limitStart", bean.getLimitStart());
        params.put("limitEnd", bean.getLimitEnd());
        List<ApiProjectListCustomize> list = amTradeClient.getApiProjectList(params);
        return list;
    }
}



