package com.hyjf.cs.trade.service.projectlist.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.api.ApiProjectListCustomize;
import com.hyjf.am.vo.trade.ProjectCustomeDetailVO;
import com.hyjf.am.vo.trade.borrow.BorrowCarinfoVO;
import com.hyjf.am.vo.trade.borrow.BorrowHousesVO;
import com.hyjf.am.vo.trade.borrow.BorrowManinfoVO;
import com.hyjf.am.vo.trade.borrow.BorrowUserVO;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.common.bean.result.ApiResult;
import com.hyjf.cs.trade.bean.api.ApiBorrowDetail;
import com.hyjf.cs.trade.bean.api.ApiBorrowReqBean;
import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.cs.trade.service.projectlist.ApiProjectListService;
import com.hyjf.cs.trade.service.svrcheck.CommonSvrChkService;
import com.hyjf.cs.trade.util.ProjectConstant;
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


    /**
     * 查询标的详情
     * @author zhangyk
     * @date 2018/8/29 13:45
     */
    @Override
    public ApiResult getBorrowDetail(ApiBorrowReqBean reqBean) {
        ApiResult result = new ApiResult();
        // 公用参数校验
        commonSvrChkService.checkRequired(reqBean);
        //获取项目编号
        String borrowNid = reqBean.getBorrowNid();
        CheckUtil.check(Validator.isNotNull(borrowNid), MsgEnum.STATUS_CE000001);
        // 验签
        CheckUtil.check(ProjectConstant.verifyRequestSign(reqBean, ProjectConstant.API_METHOD_BORROW_DETAIL),
                MsgEnum.ERR_SIGN);
        Map<String,Object> map  = new HashMap<>();
        map.put("borrowNid",borrowNid);
        ProjectCustomeDetailVO projectCustomeDetailVO = amTradeClient.searchProjectDetail(map);
        ApiBorrowDetail borrow = CommonUtils.convertBean(projectCustomeDetailVO,ApiBorrowDetail.class);
        //项目不存在
        CheckUtil.check(Validator.isNotNull(borrow), MsgEnum.STATUS_ZT000009);

        //授信额度如果为0 返回空
        if("0".equals(borrow.getUserCredit())){
            borrow.setUserCredit("");
        }

        //借款人企业信息
        BorrowUserVO borrowUsers = amTradeClient.getBorrowUser(borrowNid);
        //借款人信息
        BorrowManinfoVO borrowManinfo = amTradeClient.getBorrowManinfo(borrowNid);
        //房产抵押信息
        List<BorrowHousesVO> borrowHousesList = amTradeClient.getBorrowHousesByNid(borrowNid);
        //车辆抵押信息
        List<BorrowCarinfoVO> borrowCarinfoList = amTradeClient.getBorrowCarinfoByNid(borrowNid);

        //资产列表
        JSONArray json = new JSONArray();
        //基础信息
        String baseTableData = "";
        //资产信息
        String assetsTableData = "";
        //项目介绍
        String intrTableData = "";
        //信用状况
        String credTableData = "";
        //审核信息
        String reviewTableData = "";
        //借款类型  1.企业    2.个人
        int borrowType = Integer.parseInt(borrow.getComOrPer());

        if (borrowType == 1 && borrowUsers != null) {
            //基础信息
            baseTableData = JSONObject.toJSONString(ProjectConstant.packDetail(borrowUsers, 1, borrowType, borrow.getBorrowLevel()));
            borrow.setBaseTableData(baseTableData);
            //信用状况
            credTableData = JSONObject.toJSONString(ProjectConstant.packDetail(borrowUsers, 4, borrowType, borrow.getBorrowLevel()));
            borrow.setCredTableData(credTableData);
            //审核信息
            reviewTableData = JSONObject.toJSONString(ProjectConstant.packDetail(borrowUsers, 5, borrowType, borrow.getBorrowLevel()));
            borrow.setReviewTableData(reviewTableData);
        } else {
            if (borrowManinfo != null) {
                //基础信息
                baseTableData = JSONObject.toJSONString(ProjectConstant.packDetail(borrowManinfo, 1, borrowType, borrow.getBorrowLevel()));
                borrow.setBaseTableData(baseTableData);
                //信用状况
                credTableData = JSONObject.toJSONString(ProjectConstant.packDetail(borrowManinfo, 4, borrowType, borrow.getBorrowLevel()));
                borrow.setCredTableData(credTableData);
                //审核信息
                reviewTableData = JSONObject.toJSONString(ProjectConstant.packDetail(borrowManinfo, 5, borrowType, borrow.getBorrowLevel()));
                borrow.setReviewTableData(reviewTableData);
            }
        }

        //资产信息
        if (borrowHousesList != null && borrowHousesList.size() > 0) {
            //房产抵押信息
            for (BorrowHousesVO borrowHouses : borrowHousesList) {
                json.add(ProjectConstant.packDetail(borrowHouses, 2, borrowType, borrow.getBorrowLevel()));
            }
        }
        if (borrowCarinfoList != null && borrowCarinfoList.size() > 0) {
            //车辆抵押信息
            for (BorrowCarinfoVO borrowCarinfo : borrowCarinfoList) {
                json.add(ProjectConstant.packDetail(borrowCarinfo, 2, borrowType, borrow.getBorrowLevel()));
            }
        }
        assetsTableData = json.toString();
        borrow.setAssetsTableData(assetsTableData);
        //项目介绍
        intrTableData = JSONObject.toJSONString(ProjectConstant.packDetail(borrow, 3, borrowType, borrow.getBorrowLevel()));
        borrow.setIntrTableData(intrTableData);
        result.setData(borrow);
        return result;
    }
}



