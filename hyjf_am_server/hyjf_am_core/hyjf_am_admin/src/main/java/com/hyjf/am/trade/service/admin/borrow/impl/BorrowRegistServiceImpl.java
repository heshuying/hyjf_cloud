/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin.borrow.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.hyjf.am.response.Response;
import com.hyjf.am.resquest.admin.BorrowRegistListRequest;
import com.hyjf.am.resquest.admin.BorrowRegistUpdateRequest;
import com.hyjf.am.trade.dao.model.auto.Borrow;
import com.hyjf.am.trade.dao.model.auto.BorrowExample;
import com.hyjf.am.trade.dao.model.auto.BorrowInfo;
import com.hyjf.am.trade.dao.model.auto.BorrowProjectType;
import com.hyjf.am.trade.dao.model.auto.BorrowProjectTypeExample;
import com.hyjf.am.trade.dao.model.auto.BorrowStyle;
import com.hyjf.am.trade.dao.model.auto.BorrowStyleExample;
import com.hyjf.am.trade.dao.model.auto.StzhWhiteList;
import com.hyjf.am.trade.dao.model.auto.StzhWhiteListExample;
import com.hyjf.am.trade.dao.model.customize.BorrowRegistCustomize;
import com.hyjf.am.trade.service.admin.borrow.BorrowRegistService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.common.cache.CacheUtil;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.common.validator.Validator;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;

/**
 * @author wangjun
 * @version BorrowRegistServiceImpl, v0.1 2018/6/29 16:54
 */
@Service
public class BorrowRegistServiceImpl extends BaseServiceImpl implements BorrowRegistService {

    /**
     * 获取项目类型
     *
     * @return
     */
    @Override
    public List<BorrowProjectType> selectBorrowProjectList() {
        BorrowProjectTypeExample example = new BorrowProjectTypeExample();
        BorrowProjectTypeExample.Criteria cra = example.createCriteria();
        cra.andStatusEqualTo(Integer.valueOf(CustomConstants.FLAG_NORMAL));
        // 不查询融通宝相关
        cra.andBorrowNameNotEqualTo(CustomConstants.RTB);

        return borrowProjectTypeMapper.selectByExample(example);
    }

    /**
     * 获取还款方式
     *
     * @return
     */
    @Override
    public List<BorrowStyle> selectBorrowStyleList() {
        BorrowStyleExample example = new BorrowStyleExample();
        BorrowStyleExample.Criteria cra = example.createCriteria();
        cra.andStatusEqualTo(Integer.valueOf(CustomConstants.FLAG_NORMAL));

        return borrowStyleMapper.selectByExample(example);
    }

    /**
     * 获取标的备案列表count
     *
     * @param borrowRegistListRequest
     * @return
     */
    @Override
    public Integer getRegistCount(BorrowRegistListRequest borrowRegistListRequest) {
        return borrowRegistCustomizeMapper.getRegistCount(borrowRegistListRequest);
    }

    /**
     * 获取标的备案列表
     *
     * @param borrowRegistListRequest
     * @return
     */
    @Override
    public List<BorrowRegistCustomize> selectBorrowRegistList(BorrowRegistListRequest borrowRegistListRequest) {
        List<BorrowRegistCustomize> list = borrowRegistCustomizeMapper.selectBorrowRegistList(borrowRegistListRequest);
        if (!CollectionUtils.isEmpty(list)) {
            //处理标的备案状态
            Map<String, String> map = CacheUtil.getParamNameMap("REGIST_STATUS");
            list.forEach((borrowRegistCustomize) -> borrowRegistCustomize.setRegistStatusName(map.getOrDefault(borrowRegistCustomize.getRegistStatus(), null)));
        }
        return list;
    }

    /**
     * 统计总额
     *
     * @param borrowRegistListRequest
     * @return
     */
    @Override
    public String getSumBorrowRegistAccount(BorrowRegistListRequest borrowRegistListRequest) {
        return borrowRegistCustomizeMapper.sumBorrowRegistAccount(borrowRegistListRequest);
    }


    /**
     * 标的备案
     * @param request
     * @return
     */
    @Override
    public Response updateBorrowRegist(BorrowRegistUpdateRequest request){
        Response result = new Response();
        // 从请求实体类中获取相应的标的详情
        // 标的
        Borrow borrow = new Borrow();
        BeanUtils.copyProperties(request.getBorrowVO(), borrow);
        // 标的信息
        BorrowInfo borrowInfo = new BorrowInfo();
        BeanUtils.copyProperties(request.getBorrowInfoVO(), borrowInfo);
        // 标的编号
        String borrowNid = borrow.getBorrowNid();
        // 当前登录用户ID
        String currUserId = request.getCurrUserId();
        // 当前登录用户名
        String currUserName = request.getCurrUserName();

        //項目还款方式
        String borrowStyle = borrow.getBorrowStyle();
        //是否月标(true:月标, false:天标)
        boolean isMonth = CustomConstants.BORROW_STYLE_PRINCIPAL.equals(borrowStyle) || CustomConstants.BORROW_STYLE_MONTH.equals(borrowStyle)
                || CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrowStyle);
        //借款人用户ID
        int userId = borrow.getUserId();

        // 更新相应的标的状态为备案中
        boolean debtRegistingFlag = this.updateBorrowRegist(borrow, 0, 1, currUserId, currUserName);
        if (debtRegistingFlag) {
            // 获取共同参数
            String orderId = GetOrderIdUtils.getOrderId2(userId);
            String orderDate = GetOrderIdUtils.getOrderDate();
            // 调用开户接口
            BankCallBean debtRegistBean = new BankCallBean();
            // 调用银行接口部分共同参数删除
            // 消息类型(用户开户)
            debtRegistBean.setTxCode(BankCallConstant.TXCODE_DEBT_REGISTER);
            // 借款人电子账号
            debtRegistBean.setAccountId(request.getAccountId());
            // 标的表id
            debtRegistBean.setProductId(borrowNid);
            // 标的名称
            debtRegistBean.setProductDesc(borrowInfo.getName());
            // 募集日,标的保存时间
            debtRegistBean.setRaiseDate(borrowInfo.getBankRaiseStartDate());
            // 募集结束日期
            debtRegistBean.setRaiseEndDate(borrowInfo.getBankRaiseEndDate());
            if (isMonth) {
                debtRegistBean.setIntType(BankCallConstant.DEBT_INTTYPE_UNCERTAINDATE);
            } else {
                debtRegistBean.setIntType(BankCallConstant.DEBT_INTTYPE_EXPIREDATE);
            }
            // (借款期限,天数）
            debtRegistBean.setDuration(String.valueOf(borrowInfo.getBankBorrowDays()));
            // 交易金额
            debtRegistBean.setTxAmount(String.valueOf(borrow.getAccount()));
            // 年化利率
            debtRegistBean.setRate(String.valueOf(borrow.getBorrowApr()));
            //如果有担保机构ID
            if (Validator.isNotNull(borrowInfo.getRepayOrgUserId())) {
                debtRegistBean.setBailAccountId(request.getBailAccountId());
            }
            debtRegistBean.setLogOrderId(orderId);
            debtRegistBean.setLogOrderDate(orderDate);
            debtRegistBean.setLogUserId(String.valueOf(userId));
            debtRegistBean.setLogRemark("借款人标的登记");
            debtRegistBean.setLogClient(0);

            //备案接口(EntrustFlag和ReceiptAccountId要么都传，要么都不传)
            if (borrowInfo.getEntrustedFlg() == 1) {
                //查询受托支付记录
                StzhWhiteList stzhWhiteList = this.selectStzfWhiteList(borrowInfo.getInstCode().trim(), String.valueOf(borrowInfo.getEntrustedUserId()));
                if (stzhWhiteList != null) {
                    debtRegistBean.setEntrustFlag(borrowInfo.getEntrustedFlg().toString());
                    debtRegistBean.setReceiptAccountId(stzhWhiteList.getStAccountid());
                } else {
                    this.updateBorrowRegist(borrow, 0, 4, currUserId, currUserName);
                    return new Response(Response.FAIL, "受托白名单查询为空！");
                }
            }
            try {
                //调用银行接口
                BankCallBean registResult = BankCallUtils.callApiBg(debtRegistBean);
                String retCode = StringUtils.isNotBlank(registResult.getRetCode()) ? registResult.getRetCode() : "";
                if (BankCallConstant.RESPCODE_SUCCESS.equals(retCode)) {
                    //受托支付备案
                    if (borrowInfo.getEntrustedFlg() == 1) {
                        boolean debtEntrustedRegistedFlag = this.updateEntrustedBorrowRegist(borrow, 7, 2, currUserId, currUserName);
                        if (debtEntrustedRegistedFlag) {
                            result.setRtn(Response.SUCCESS);
                            result.setMessage("备案成功！");
                        } else {
                            result.setRtn(Response.FAIL);
                            result.setMessage("备案成功后，更新相应的状态失败,请联系客服！");
                        }
                    } else {
                        boolean debtRegistedFlag = this.updateBorrowRegist(borrow, 1, 2, currUserId, currUserName);
                        if (debtRegistedFlag) {
                            result.setRtn(Response.SUCCESS);
                            result.setMessage("备案成功！");
                        } else {
                            result.setRtn(Response.FAIL);
                            result.setMessage("备案成功后，更新相应的状态失败,请联系客服！");
                        }
                    }
                } else {
                    // 调用银行接口失败
                    this.updateBorrowRegist(borrow, 0, 4, currUserId, currUserName);
                    String message = registResult.getRetMsg();
                    result.setRtn(Response.FAIL);
                    result.setMessage(StringUtils.isNotBlank(message) ? message : "银行备案接口调用失败！");
                }
            } catch (Exception e) {
                logger.error("标的备案失败,编号："+ borrow.getBorrowNid(),e);
                this.updateBorrowRegist(borrow, 0, 4, currUserId, currUserName);
                result.setRtn(Response.FAIL);
                result.setMessage("银行备案接口调用失败！");
            }
        } else {
            result.setRtn(Response.FAIL);
            result.setMessage("更新相应的标的信息失败,请稍后再试！");
        }
        return result;
    }

    /**
     * 受托白名单查询
     * @param instCode
     * @param entrustedAccountId
     * @return
     */
    private StzhWhiteList selectStzfWhiteList(String instCode, String entrustedAccountId) {
        StzhWhiteListExample example = new StzhWhiteListExample();
        StzhWhiteListExample.Criteria crt = example.createCriteria();
        crt.andStAccountidEqualTo(entrustedAccountId);
        crt.andInstcodeEqualTo(instCode);
        crt.andDelFlagEqualTo(0);
        crt.andStateEqualTo(1);
        List<StzhWhiteList> list = this.stzhWhiteListMapper.selectByExample(example);
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    /**
     * 更新相应的标的信息
     * @param borrow
     * @param status
     * @param registStatus
     * @param currUserId
     * @param currUserName
     * @return
     */
    private boolean updateBorrowRegist(Borrow borrow, int status, int registStatus, String currUserId, String currUserName) {
        Borrow updateBorrow = new Borrow();
        Date nowDate = new Date();
        BorrowExample example = new BorrowExample();
        example.createCriteria().andIdEqualTo(borrow.getId());
        updateBorrow.setRegistStatus(registStatus);
        updateBorrow.setStatus(status);
        updateBorrow.setRegistUserId(Integer.parseInt(currUserId));
        updateBorrow.setRegistUserName(currUserName);
        updateBorrow.setRegistTime(nowDate);
        boolean flag = this.borrowMapper.updateByExampleSelective(updateBorrow, example) > 0 ? true : false;
        return flag;
    }

    /**
     * 更新标的信息(受托支付备案)
     * @param borrow
     * @param status
     * @param registStatus
     * @param currUserId
     * @param currUserName
     * @return
     */
    private boolean updateEntrustedBorrowRegist(Borrow borrow, int status, int registStatus, String currUserId, String currUserName) {
        Borrow updateBorrow = new Borrow();
        Date nowDate = new Date();
        BorrowExample example = new BorrowExample();
        example.createCriteria().andIdEqualTo(borrow.getId());
        updateBorrow.setRegistStatus(registStatus);
        updateBorrow.setStatus(status);
        updateBorrow.setRegistUserId(Integer.parseInt(currUserId));
        updateBorrow.setRegistUserName(currUserName);
        updateBorrow.setRegistTime(nowDate);
        boolean flag = this.borrowMapper.updateByExampleSelective(updateBorrow, example) > 0 ? true : false;
        return flag;
    }
}
