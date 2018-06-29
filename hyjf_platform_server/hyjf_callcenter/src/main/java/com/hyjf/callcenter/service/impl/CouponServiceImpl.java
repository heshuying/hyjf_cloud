/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.callcenter.service.impl;

import com.hyjf.am.resquest.callcenter.CallCenterBaseRequest;
import com.hyjf.am.vo.callcenter.CallCenterCouponBackMoneyVO;
import com.hyjf.am.vo.callcenter.CallCenterCouponTenderVO;
import com.hyjf.am.vo.callcenter.CallCenterCouponUserVO;
import com.hyjf.callcenter.beans.CouponBackMoneyBean;
import com.hyjf.callcenter.beans.CouponBean;
import com.hyjf.callcenter.beans.CouponTenderBean;
import com.hyjf.callcenter.beans.ResultListBean;
import com.hyjf.callcenter.client.CouponClient;
import com.hyjf.callcenter.result.BaseResultBean;
import com.hyjf.callcenter.service.CouponService;
import com.hyjf.common.cache.CacheUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

/**
 * @author wangjun
 * @version CouponServiceImpl, v0.1 2018/6/19 11:40
 */
@Service
public class CouponServiceImpl implements CouponService {
    @Autowired
    CouponClient couponClient;
    /**
     * 查询优惠券
     * @param centerBaseRequest
     * @return ResultListBean
     * @author wangjun
     */
    public ResultListBean selectCouponUserList(CallCenterBaseRequest centerBaseRequest){
        ResultListBean result = new ResultListBean();
        List<CallCenterCouponUserVO> list = couponClient.selectCouponUserList(centerBaseRequest);
        if(!CollectionUtils.isEmpty(list)){
            for (CallCenterCouponUserVO recordBean : list) {
                CouponBean returnBean = new CouponBean();
                setUpCouponBean(recordBean);

                //检索bean→返回bean
                BeanUtils.copyProperties(recordBean, returnBean);
                //用户名
                returnBean.setUserName(centerBaseRequest.getUserName());
                //手机号
                returnBean.setMobile(centerBaseRequest.getMobile());

                //获取优惠券内容
                if(recordBean.getCouponSource().equals("3")){
                    returnBean.setCouponContent(couponClient.getCouponContent(recordBean.getCouponCode()));
                }
                result.getDataList().add(returnBean);
            }
            result.statusMessage(ResultListBean.STATUS_SUCCESS, ResultListBean.STATUS_SUCCESS_DESC);
        }else {
            result.statusMessage(BaseResultBean.STATUS_FAIL,"该用户没有优惠券信息！");
            return result;
        }

        return result;
    }

    /**
     * 查询优惠券使用（直投产品）
     * @param centerBaseRequest
     * @return ResultListBean
     * @author wangjun
     */
    public ResultListBean selectCouponTenderList(CallCenterBaseRequest centerBaseRequest){
        ResultListBean result = new ResultListBean();
        List<CallCenterCouponTenderVO> list =  couponClient.selectCouponTenderList(centerBaseRequest);
        if(!CollectionUtils.isEmpty(list)){
            for (CallCenterCouponTenderVO recordBean : list) {
                CouponTenderBean returnBean = new CouponTenderBean();
                setUpCouponTenderBean(recordBean);

                //检索bean→返回bean
                BeanUtils.copyProperties(recordBean, returnBean);
                //用户名
                returnBean.setUserName(centerBaseRequest.getUserName());
                //手机号
                returnBean.setMobile(centerBaseRequest.getMobile());
                result.getDataList().add(returnBean);
            }
            result.statusMessage(ResultListBean.STATUS_SUCCESS, ResultListBean.STATUS_SUCCESS_DESC);
        }else {
            result.statusMessage(BaseResultBean.STATUS_FAIL,"该用户没有优惠券使用记录！");
            return result;
        }

        return result;
    }

    /**
     * 查询优惠券回款（直投产品）
     * @param centerBaseRequest
     * @return List<CallCenterCouponBackMoneyVO>
     * @author wangjun
     */
    public ResultListBean selectCouponBackMoneyList(CallCenterBaseRequest centerBaseRequest){
        ResultListBean result = new ResultListBean();
        List<CallCenterCouponBackMoneyVO> recordList = this.couponClient.selectCouponBackMoneyList(centerBaseRequest);
        if (recordList == null) {
            result.statusMessage(BaseResultBean.STATUS_FAIL,"该用户没有优惠券回款记录！");
            return result;
        }

        //编辑返回信息
        for (CallCenterCouponBackMoneyVO recordBean : recordList) {
            CouponBackMoneyBean returnBean = new CouponBackMoneyBean();
            //检索bean→返回bean
            BeanUtils.copyProperties(recordBean, returnBean);
            //用户名
            returnBean.setUserName(centerBaseRequest.getUserName());
            //手机号
            returnBean.setMobile(centerBaseRequest.getMobile());
            result.getDataList().add(returnBean);
        }

        result.statusMessage(ResultListBean.STATUS_SUCCESS, ResultListBean.STATUS_SUCCESS_DESC);
        return result;
    }


    private void setUpCouponBean(CallCenterCouponUserVO recordBean) {
        // 操作平台
        Map<String, String> clients = CacheUtil.getParamNameMap("CLIENT");
        recordBean.setProjectType(createProjectTypeString(recordBean.getProjectType()));
        recordBean.setCouponSystem(createCouponSystemString(recordBean.getCouponSystem(),clients));
    }

    private void setUpCouponTenderBean(CallCenterCouponTenderVO recordBean) {
        // 操作平台
        Map<String, String> clients = CacheUtil.getParamNameMap("CLIENT");
        recordBean.setProjectType(createProjectTypeString(recordBean.getProjectType()));
        recordBean.setCouponSystem(createCouponSystemString(recordBean.getCouponSystem(),clients));
    }


    private String createProjectTypeString(String projectType) {
        String projectString = "";
        if (projectType.indexOf("-1") != -1) {
            projectString = "不限";
        } else {
            //勾选汇直投，尊享汇，融通宝
            if (projectType.indexOf("1")!=-1&&projectType.indexOf("4")!=-1&&projectType.indexOf("6")!=-1) {
                projectString = projectString + "债权,";
            }
            //勾选汇直投  未勾选尊享汇，融通宝
            if (projectType.indexOf("1")!=-1&&projectType.indexOf("4")==-1&&projectType.indexOf("6")==-1) {
                projectString = projectString + "债权(尊享,优选除外),";
            }
            //勾选汇直投，融通宝  未勾选尊享汇
            if(projectType.indexOf("1")!=-1&&projectType.indexOf("4")==-1&&projectType.indexOf("6")!=-1){
                projectString = projectString + "债权(尊享除外),";
            }
            //勾选汇直投，选尊享汇 未勾选融通宝
            if(projectType.indexOf("1")!=-1&&projectType.indexOf("4")!=-1&&projectType.indexOf("6")==-1){
                projectString = projectString + "债权(优选除外),";
            }
            //勾选尊享汇，融通宝  未勾选直投
            if(projectType.indexOf("1")==-1&&projectType.indexOf("4")!=-1&&projectType.indexOf("6")!=-1){
                projectString = projectString + "债权(仅限尊享,优选),";
            }
            //勾选尊享汇  未勾选直投，融通宝
            if(projectType.indexOf("1")==-1&&projectType.indexOf("4")!=-1&&projectType.indexOf("6")==-1){
                projectString = projectString + "债权(仅限尊享),";
            }
            //勾选尊享汇  未勾选直投，融通宝
            if(projectType.indexOf("1")==-1&&projectType.indexOf("4")==-1&&projectType.indexOf("6")!=-1){
                projectString = projectString + "债权(仅限优选),";
            }

            if (projectType.indexOf("3")!=-1) {
                projectString = projectString + "新手,";
            }
            if (projectType.indexOf("5")!=-1) {
                projectString = projectString + "计划,";
            }
        }
        projectString = StringUtils.removeEnd(projectString, ",");
        return projectString;
    }

    private String createCouponSystemString(String couponSystem, Map<String, String> clients) {
        String clientString = "";

        // 被选中操作平台
        String clientSed[] = StringUtils.split(couponSystem, ",");
        for (int i = 0; i < clientSed.length; i++) {
            if ("-1".equals(clientSed[i])) {
                clientString = clientString + "全部平台";
                break;
            } else {
                for (String key : clients.keySet()) {
                    if (clientSed[i].equals(key)) {
                        if (i != 0 && clientString.length() != 0) {
                            clientString = clientString + "、";
                        }
                        clientString = clientString + clients.get(key);
                    }
                }
            }
        }
        return clientString.replace("Android、iOS", "APP");
    }
}
