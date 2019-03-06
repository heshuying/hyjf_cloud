package com.hyjf.admin.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.client.AmUserClient;
import com.hyjf.admin.service.PushMoneyManageService;
import com.hyjf.am.response.trade.PushMoneyResponse;
import com.hyjf.am.resquest.admin.PushMoneyRequest;
import com.hyjf.am.resquest.admin.TenderCommissionRequest;
import com.hyjf.am.vo.admin.OADepartmentCustomizeVO;
import com.hyjf.am.vo.admin.TenderCommissionVO;
import com.hyjf.am.vo.bank.BankCallBeanVO;
import com.hyjf.am.vo.config.AdminSystemVO;
import com.hyjf.am.vo.trade.PushMoneyVO;
import com.hyjf.am.vo.trade.borrow.BorrowAndInfoVO;
import com.hyjf.am.vo.trade.borrow.BorrowApicronVO;
import com.hyjf.am.vo.trade.borrow.BorrowTenderVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserInfoCustomizeVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.http.HtmlUtil;
import com.hyjf.common.util.*;
import com.hyjf.common.util.calculate.AverageCapitalPlusInterestUtils;
import com.hyjf.common.validator.Validator;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallMethodConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author Zha Daojian
 * @version BorrowRecoverServiceImpl, v0.1 2018/7/2 10:17
 */
@Service
public class PushMoneyManageServiceImpl extends BaseAdminServiceImpl implements PushMoneyManageService {

    @Autowired
    AmTradeClient amTradeClient;

    @Autowired
    public AmUserClient amUserClient;


//    @Autowired
//    private BankAccountManageClient bankAccountManageClient;




    /**
     * 查找直投提成管理列表
     *
     * @param request
     * @return
     */
    @Override
    public PushMoneyResponse findPushMoneyList(PushMoneyRequest request) {
        return amTradeClient.findPushMoneyList(request);
    }

    /**
     * 取得借款API表
     *
     * @param borrowNid
     * @return
     */
    @Override
    public BorrowApicronVO getBorrowApicronBorrowNid(String borrowNid) {
        List<BorrowApicronVO> list = this.amTradeClient.selectBorrowApicronListByBorrowNid(borrowNid);
        if (list!= null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 发提成处理- 计算提成
     *
     * @param apicornId,request
     * @return
     */
    @Override
    public int insertTenderCommissionRecord(Integer apicornId, PushMoneyRequest request) {
        int ret = -1;
        // 项目编号
        String borrowNid = request.getBorrowNid();

        // 根据项目编号取得borrow表

        BorrowAndInfoVO borrow = this.amTradeClient.selectBorrowByNid(borrowNid);
        if(borrow==null){
            logger.info("根据项目编号取得borrow表失敗borrowNid："+borrowNid);
            return ret;
        }

        // 根据项目编号取得borrowTender表
        List<BorrowTenderVO> borrowTenderList = this.amTradeClient.searchBorrowTenderByBorrowNid(borrowNid);
        // 循环BorrowTender表,计算提成
        for (BorrowTenderVO borrowTender : borrowTenderList) {
            boolean is51 = false;
            // 插入提成数据
            TenderCommissionRequest tenderCommissionRequest = new TenderCommissionRequest();
            // 1 直投类提成
            tenderCommissionRequest.setTenderType(1);
            // 出借人
            tenderCommissionRequest.setTenderUserId(borrowTender.getUserId());
            // 出借金额
            tenderCommissionRequest.setAccountTender(borrowTender.getAccount());
            // 项目编号
            tenderCommissionRequest.setBorrowNid(borrowNid);
            // 出借ID
            tenderCommissionRequest.setTenderId(borrowTender.getId());
            // 出借时间
            tenderCommissionRequest.setTenderTime(GetDate.getTime10(borrowTender.getCreateTime()));
            // 状态 0：未发放；1：已发放
            tenderCommissionRequest.setStatus(0);
            // 备注
            tenderCommissionRequest.setRemark("3");
            // 计算时间
            tenderCommissionRequest.setComputeTime(GetDate.getMyTimeInMillis());
            // 订单号
            tenderCommissionRequest.setOrdid(borrowTender.getNid());

            // 获得提成的地区名
            tenderCommissionRequest.setRegionName(borrowTender.getInviteRegionName());
            tenderCommissionRequest.setRegionId(borrowTender.getInviteRegionId());
            // 获得提成的分公司
            tenderCommissionRequest.setBranchName(borrowTender.getInviteBranchName());
            tenderCommissionRequest.setBranchId(borrowTender.getInviteBranchId());
            // 获得提成的部门
            tenderCommissionRequest.setDepartmentName(borrowTender.getInviteDepartmentName());
            tenderCommissionRequest.setDepartmentId(borrowTender.getInviteDepartmentId());

            UserInfoVO tenderUsersInfo = amUserClient.findUserInfoById(borrowTender.getUserId());
            int tenderIs51 = 0; // 1 是
            if (tenderUsersInfo != null && tenderUsersInfo.getIs51()!=null) {
                tenderIs51 = tenderUsersInfo.getIs51();
            }
            // 提成人id
            if (borrowTender.getTenderUserAttribute() == 3) {
                // 出借时出借人是线上员工时，提成人是自己
                tenderCommissionRequest.setUserId(borrowTender.getUserId());
                if (tenderIs51 == 1) {
                    is51 = true;
                }
            } else {
                UserInfoVO refererUsersInfo = amUserClient.findUserInfoById(borrowTender.getInviteUserId());
                if (borrowTender.getInviteUserAttribute() != null && borrowTender.getInviteUserAttribute() == 3) {
                    // 出借时推荐人的用户属性是线上员工，提成人是推荐人
                    tenderCommissionRequest.setUserId(borrowTender.getInviteUserId());
                    if (refererUsersInfo != null && refererUsersInfo.getIs51()!=null && refererUsersInfo.getIs51() == 1) {
                        is51 = true;
                    }
                } else if (borrowTender.getInviteUserAttribute() != null && borrowTender.getInviteUserAttribute() < 2) {
                    // 出借时推荐人不是员工，且推荐人是51老用户，提成人是推荐人
                    if (refererUsersInfo != null && refererUsersInfo.getIs51()!=null && refererUsersInfo.getIs51() == 1) {
                        tenderCommissionRequest.setUserId(borrowTender.getInviteUserId());
                        is51 = true;
                    }
                }
            }
            logger.info("提成人用户id：" + tenderCommissionRequest.getUserId() + " 当前标的编号：" + borrowNid + " 当前出借订单号：" + borrowTender.getNid() + " 当前出借用户id：" + borrowTender.getUserId());
            if(tenderCommissionRequest.getUserId()==null ||tenderCommissionRequest.getUserId()==0){
                //如果没有提成人，返回
                continue;
            }
            // 出借人的账户信息
            BankOpenAccountVO bankOpenAccountInfo = amTradeClient.getBankOpenAccount(tenderCommissionRequest.getUserId());
            if(bankOpenAccountInfo != null){
                tenderCommissionRequest.setAccountId(bankOpenAccountInfo.getAccount());
            }

            // 计算提成(提成金额,提成人,提成人部门ID,出借人部门ID)
            logger.info("计算提成borrow：" + JSONObject.toJSON(borrow));
            calculaeCommission(tenderCommissionRequest, borrowTender.getTenderUserAttribute(), // 出借时出借人的用户属性
                    borrowTender.getInviteUserAttribute(), // 出借时推荐人的用户属性
                    borrow.getBorrowStyle(), // 还款方式（endday表示天，其它表示月）
                    borrow.getBorrowPeriod(), // 借款期限（几个月/天）
                    borrow.getProjectType(), // 0汇保贷 1汇典贷 2汇小贷 3汇车贷 4新手标
                    borrow.getBorrowApr(), // 借款利率`
                    is51);
            logger.info("计算提成tenderCommissionRequest.getCommission()：" + JSONObject.toJSON(tenderCommissionRequest));
            if (tenderCommissionRequest.getCommission()!=null && tenderCommissionRequest.getCommission().compareTo(BigDecimal.ZERO) > 0) {
                Integer counts = amTradeClient.getCountTenderCommissionByTenderIdAndTenderType(tenderCommissionRequest);
                if (counts == 0) {
                    // 执行插入
                    ret += this.amTradeClient.saveTenderCommission(tenderCommissionRequest);
                    logger.info("执行插入tenderCommissionRequest：" + JSONObject.toJSON(tenderCommissionRequest));
                } else {
                    ret++;
                }
            }
        }

        // 更新借款API表

        ret += this.amTradeClient.updateBorrowApicronByPrimaryKeySelective(apicornId+"");

        return ret;
    }

    /**
     * 取得提成配置
     *
     * @param type
     * @return
     */
    private PushMoneyVO getPushMoney(String type) {
        PushMoneyRequest pushMoneyRequest = new PushMoneyRequest();
        pushMoneyRequest.setProjectType(1);
        pushMoneyRequest.setType(type);
        List<PushMoneyVO> list = this.amTradeClient.getPushMoney(pushMoneyRequest);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 计算提成
     *
     * @param tenderCommission
     * @param tenderAttr
     * @param refererAttr
     * @param borrowStyle
     * @param borrowPerios
     * @param projectType
     * @param borrowApr
     */
    private void calculaeCommission(TenderCommissionRequest tenderCommission, Integer tenderAttr, Integer refererAttr,
                                     String borrowStyle, Integer borrowPerios, Integer projectType, BigDecimal borrowApr, Boolean is51) {
        // 提成人
        Integer commissionUserId = tenderCommission.getUserId();
        // 提成金额
        BigDecimal commission = BigDecimal.ZERO;
        // 提成利率(天标)
        BigDecimal rateDay = BigDecimal.ZERO;
        // 提成利率(月标)
        BigDecimal rateMonth = BigDecimal.ZERO;
        // 出借金额
        BigDecimal accountTender = tenderCommission.getAccountTender();
        // 年利率
        borrowApr = borrowApr.divide(new BigDecimal(100), 6, BigDecimal.ROUND_HALF_UP);

        // 判断提成人是否开户  (提成人未开户的不计算提成)

       // BankOpenAccountVO bankOpenAccountInfo = this.getBankOpenAccount(commissionUserId);
        BankOpenAccountVO bankOpenAccountInfo = this.amTradeClient.getBankOpenAccount(commissionUserId);
        if (bankOpenAccountInfo == null || Validator.isNull(bankOpenAccountInfo.getAccount())) {
            //LogUtil.errorLog(this.getClass().getName(), "calculateCommission", "计算提成失败，因为用户没有开户", null);
            return;
        }

        // 取得提成利率
//		PushMoney pushMoney = getPushMoney(!is51 ? "51老用户" : "线上员工");//如果提成人即是51老用户，又是线上员工，那么安装线上员工标准来计算。
        String tcrAttr="";
        if( (tenderAttr!=null && tenderAttr==3) || (refererAttr!=null && refererAttr==3) ){
            tcrAttr="线上员工";
        }else{
            tcrAttr="51老用户";
        }

        // 51老用户不发提成
        if(tcrAttr.equals("51老用户")){
            return;
        }

        PushMoneyVO pushMoney = getPushMoney(tcrAttr);
        if(pushMoney ==null || pushMoney.getRewardSend() != 1){
            return;
        }

        if (pushMoney != null && NumberUtils.isNumber(pushMoney.getDayTender())) {
            rateDay = new BigDecimal(pushMoney.getDayTender());// 提成利率(天标)
            rateMonth = new BigDecimal(pushMoney.getMonthTender());// 提成利率(月标)
        }

        // 汇消费以外
        if (projectType != 8) {
            // 按天计息,到期还本还息
            if (CustomConstants.BORROW_STYLE_ENDDAY.equals(borrowStyle)) {
                // 线上员工
                if ( (tenderAttr!=null && tenderAttr==3) || (refererAttr!=null && refererAttr==3) ) {
                    // 每笔提成= 出借金额*提成比例（天-线上员工）*融资天数
                    commission = accountTender.multiply(rateDay).multiply(new BigDecimal(borrowPerios));
                }
                //51老用户（非员工）
                else {
                    if (borrowPerios >= 50) {
                        // 融资期限≥50天时，每笔提成=出借金额*提成比例（月-51老用户）
                        commission = accountTender.multiply(rateMonth);
                    } else {
                        // 融资期限＜50天时，每笔提成=出借金额*提成比例（天-51老用户）*天数
                        commission = accountTender.multiply(rateDay).multiply(new BigDecimal(borrowPerios));
                    }
                }
            }
            // 其他还款方式 (月标)
            else {
                // 线上员工
                if ( (tenderAttr!=null && tenderAttr==3) || (refererAttr!=null && refererAttr==3) ) {
                    // 每笔提成=出借金额*提成比例（月-线上员工）*融资月数
                    commission = accountTender.multiply(rateMonth).multiply(new BigDecimal(borrowPerios));
                }
                //51老用户（非员工）
                else {
                    // 每笔提成= 出借金额*提成比例（月-51老用户）
                    commission = accountTender.multiply(rateMonth);
                }
            }
        }
        // 汇消费(等额本息)
        else {
            // 线上员工
            if ( (tenderAttr!=null && tenderAttr==3) || (refererAttr!=null && refererAttr==3) ) {
                // 等额本息还贷第n个月还贷本金之和
                Map<Integer, BigDecimal> monthPrincipal = AverageCapitalPlusInterestUtils
                        .getPerMonthPrincipal(accountTender, borrowApr, borrowPerios);
                if (monthPrincipal != null && monthPrincipal.size() > 0) {
                    for (Map.Entry<Integer, BigDecimal> entry : monthPrincipal.entrySet()) {
                        commission = commission
                                .add(entry.getValue().multiply(rateMonth).multiply(new BigDecimal(entry.getKey())));
                    }
                }
            }
            //51老用户（非员工）
            else {
                // 出借金额*提成比例
                commission = accountTender.multiply(rateMonth);
            }
        }

        logger.info("计算出的提成金额：" + commission + " 提成人：" + commissionUserId + " 出借金额：" +accountTender);
        // 提成金额
        tenderCommission.setCommission(commission == null ? BigDecimal.ZERO : commission);

        // 根据用户ID查询部门信息
        UserInfoCustomizeVO userInfoCustomize =  amUserClient.getUserInfoCustomizeByUserId(commissionUserId);
        if (userInfoCustomize != null) {
            tenderCommission.setRegionId(userInfoCustomize.getRegionId());
            tenderCommission.setRegionName(userInfoCustomize.getRegionName());
            tenderCommission.setBranchId(userInfoCustomize.getBranchId());
            tenderCommission.setBranchName(userInfoCustomize.getBranchName());
            tenderCommission.setDepartmentId(userInfoCustomize.getDepartmentId());
            tenderCommission.setDepartmentName(HtmlUtil.unescape(userInfoCustomize.getDepartmentName()));
        }
    }

    /**
     * 直投提成列表count
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public int getPushMoneyListCount(PushMoneyRequest request) {
        return amTradeClient.getPushMoneyListCount(request);
    }

    /**
     * 直投提成列表list
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public List<PushMoneyVO> searchPushMoneyList(PushMoneyRequest request) {
        List<PushMoneyVO> pushMoneyVOList = amTradeClient.searchPushMoneyList(request);
        return pushMoneyVOList;
    }

    /**
     * 直投提成列表总额
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public Map<String, Object> queryPushMoneyTotle(PushMoneyRequest request) {
        return amTradeClient.queryPushMoneyTotle(request);
    }

    /**
     * 发提成
     * @auth sunpeikai
     * @param id 提成id
     * @return
     */
    @Override
    public JSONObject pushMoney(HttpServletRequest request,Integer id,Integer loginUserId) {

        JSONObject ret = new JSONObject();
        ret.put("status","");
        ret.put("statusDesc","");

        TenderCommissionVO tenderCommissionVO = amTradeClient.queryTenderCommissionByPrimaryKey(id);

        //TenderCommission tenderCommission = this.pushMoneyService.queryTenderCommissionByPrimaryKey(id);
        // 如果 未发放 //且 提成>0
        if (tenderCommissionVO != null && tenderCommissionVO.getStatus() == 0
                && tenderCommissionVO.getAccountTender().compareTo(BigDecimal.ZERO) > 0) {
            Integer userId = tenderCommissionVO.getUserId();

            /** 验证员工在平台的身份属性是否和crm的一致 如果不一致则不发提成 begin */

            /** redis 锁 */
            boolean reslut = RedisUtils.tranactionSet(RedisConstants.PUSH_MONEY_ + id, 60);
            // 如果没有设置成功，说明有请求来设置过
            if(!reslut){
                ret.put("status","error");
                ret.put("statusDesc","数据已发生变化,请一分钟后再操作!");
                return ret;
            }

            UserInfoVO userInfoVO = amUserClient.findUserInfoById(userId);
            // cuttype 提成发放方式（3线上2线下）

            Integer cuttype = amTradeClient.queryCrmCuttype(userId);

            if (userInfoVO.getAttribute() != null && userInfoVO.getAttribute() > 1) {
                if (userInfoVO.getAttribute() != cuttype) {
                    ret.put("status","error");
                    ret.put("statusDesc","该用户属性异常!");
                    logger.error("该用户平台属性与CRM 不符！[userId=" + userId + "]");
                    return ret;
                }
            }
            /** 验证员工在平台的身份属性是否和crm的一致 如果不一致则不发提成 end */

            BankOpenAccountVO bankOpenAccountVO = amUserClient.searchBankOpenAccount(userId);

            if (bankOpenAccountVO != null && !Validator.isNull(bankOpenAccountVO.getAccount())) {
                // 查询商户子账户余额

                String merrpAccount = systemConfig.getBANK_MERRP_ACCOUNT();

                BigDecimal bankBalance = getBankBalance(loginUserId, merrpAccount);

                // 如果接口调用成功
                if (bankBalance != null) {
                    // 检查余额是否充足
                    if (bankBalance.compareTo(tenderCommissionVO.getCommission()) < 0) {
                        logger.error("推广提成子账户余额不足,请先充值或向该子账户转账");
                        ret.put("status","error");
                        ret.put("statusDesc","推广提成子账户余额不足,请先充值或向该子账户转账!");
                        return ret;
                    }
                } else {
                    logger.error("没有查询到商户可用余额");
                    ret.put("status","error");
                    ret.put("statusDesc","没有查询到商户可用余额");
                    return ret;
                }


                // IP地址
                String ip = CustomUtil.getIpAddr(request);
                String orderId = GetOrderIdUtils.getOrderId2(userId);

                BankCallBean bean = new BankCallBean();
                bean.setVersion(BankCallConstant.VERSION_10);// 版本号
                bean.setTxCode(BankCallMethodConstant.TXCODE_VOUCHER_PAY);// 交易代码
                bean.setTxDate(GetOrderIdUtils.getTxDate()); // 交易日期
                bean.setTxTime(GetOrderIdUtils.getTxTime()); // 交易时间
                bean.setSeqNo(GetOrderIdUtils.getSeqNo(6));// 交易流水号
                bean.setChannel(BankCallConstant.CHANNEL_PC); // 交易渠道
                bean.setAccountId(merrpAccount);// 电子账号
                bean.setTxAmount(tenderCommissionVO.getCommission().toString());
                bean.setForAccountId(bankOpenAccountVO.getAccount());
                bean.setDesLineFlag("1");
                bean.setDesLine(tenderCommissionVO.getOrdid());
                bean.setLogOrderId(orderId);// 订单号
                bean.setLogOrderDate(GetOrderIdUtils.getOrderDate());// 订单时间(必须)格式为yyyyMMdd，例如：20130307
                bean.setLogUserId(String.valueOf(userId));
                bean.setLogClient(0);// 平台
                bean.setLogIp(ip);

                BankCallBean resultBean;
                try {
                    resultBean = BankCallUtils.callApiBg(bean);
                } catch (Exception e) {
                    logger.error(e.getMessage());
                    ret.put("status","error");
                    ret.put("statusDesc","请求红包接口失败");
                    return ret;
                }

                if (resultBean == null || !BankCallConstant.RESPCODE_SUCCESS.equals(resultBean.getRetCode())) {
                    ret.put("status","error");
                    ret.put("statusDesc","调用红包接口发生错误");
                    return ret;
                }

                int cnt = 0;
                // 接口返回正常时,执行更新操作
                try {
                    // 发提成处理
                    PushMoneyRequest pushMoneyRequest = new PushMoneyRequest();
                    pushMoneyRequest.setTenderCommissionVO(tenderCommissionVO);
                    AdminSystemVO adminSystemVO = amConfigClient.getUserInfoById(loginUserId);
                    pushMoneyRequest.setLoginUserName(adminSystemVO.getUsername());
                    pushMoneyRequest.setBankOpenAccountVO(bankOpenAccountVO);
                    pushMoneyRequest.setBankCallBeanVO(CommonUtils.convertBean(resultBean,BankCallBeanVO.class));
                    cnt = amTradeClient.updateTenderCommissionRecord(pushMoneyRequest);

                } catch (Exception e) {
                    logger.error(e.getMessage());
                }

                // 返现成功
                if (cnt > 0) {
                    ret.put("status","success");
                    ret.put("statusDesc","发提成操作成功!");
                    logger.info("提成发放成功，用户id：" + userId + " 金额:"  + tenderCommissionVO.getCommission().toString());
                } else {
                    ret.put("status","error");
                    ret.put("statusDesc","发提成时发生错误,请重新操作!");
                }
                return ret;

            }else {
                ret.put("status","error");
                ret.put("statusDesc","该用户未开户!");
                return ret;
            }

        }else{
            ret.put("status","error");
            ret.put("statusDesc","提成已经发过啦");
        }
        return ret;
    }

    /**
     * 获取部门信息
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public JSONArray getCrmDepartmentList(String[] list) {
        List<OADepartmentCustomizeVO> departmentList = amUserClient.queryDepartmentInfo(null);

        Map<String, String> map = new HashMap<String, String>();
        if (departmentList != null && departmentList.size() > 0) {
            for (OADepartmentCustomizeVO oaDepartment : departmentList) {
                map.put(String.valueOf(oaDepartment.getId()), HtmlUtil.unescape(oaDepartment.getName()));
            }
        }
        return treeDepartmentList(departmentList, map, list, "0", "");
    }

    /**
     * 部门树形结构
     *
     * @param departmentTreeDBList
     * @param map
     * @param selectedNode
     * @param topParentDepartmentCd
     * @param topParentDepartmentName
     * @return
     */
    private JSONArray treeDepartmentList(List<OADepartmentCustomizeVO> departmentTreeDBList, Map<String, String> map, String[] selectedNode, String topParentDepartmentCd,
                                         String topParentDepartmentName) {
        JSONArray ja = new JSONArray();
        JSONObject joAttr = new JSONObject();
        if (departmentTreeDBList != null && departmentTreeDBList.size() > 0) {
            JSONObject jo = null;
            for (OADepartmentCustomizeVO departmentTreeRecord : departmentTreeDBList) {
                jo = new JSONObject();
                jo.put("title", departmentTreeRecord.getName());
                jo.put("key", UUID.randomUUID());
                jo.put("value", departmentTreeRecord.getId().toString());
                String departmentCd = String.valueOf(departmentTreeRecord.getId());
                String departmentName = String.valueOf(departmentTreeRecord.getName());
                String parentDepartmentCd = String.valueOf(departmentTreeRecord.getParentid());
                if (topParentDepartmentCd.equals(parentDepartmentCd)) {
                    JSONArray array = treeDepartmentList(departmentTreeDBList, map, selectedNode, departmentCd, departmentName);
                    if(null!=array&&array.size()>0){
                        jo.put("value", "P"+departmentTreeRecord.getId().toString());
                    }
                    jo.put("children", array);
                    ja.add(jo);
                }
            }
        }
        return ja;
    }

    /**
     * 部门查询条件
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public String[] getDeptId(String[] combotreeListSrch) {
        String[] strIds = null;
        List<String> stringList = new ArrayList<String>();
        if (Validator.isNotNull(combotreeListSrch)) {
            //判断部门选择里是否有父级部门
            for (int i = 0; i < combotreeListSrch.length; i++) {
                String st = combotreeListSrch[i];
                if (st.contains("P")) {
                    //选择的是父级部门的情况下
                    String strDe = st.split("P")[1];
                    if (StringUtils.isNotBlank(strDe)) {
                        stringList = getDeptInfoByDeptId(Integer.parseInt(strDe), stringList);
                    }
                } else {
                    //既选择了父级部门又选择子级菜单的情况下
                    stringList.add(st);
                }
                //其他
                if (("-10086").equals(st)) {
                    stringList.add("0");
                }
            }
        }
        if (null != stringList && stringList.size() > 0) {
            strIds = stringList.toArray(new String[stringList.size()]);
        }
        return strIds;
    }

    /**
     * 根据部门查找是否有子级部门并循环将部门id设置的list中
     */
    private List<String> getDeptInfoByDeptId(int deptId, List<String> keysList) {
        List<OADepartmentCustomizeVO> list = amUserClient.getDeptInfoByDeptId(deptId);
        if (null != list && list.size() > 0) {
            for (OADepartmentCustomizeVO oaDepartmentCustomizeVO : list) {
                keysList.add(oaDepartmentCustomizeVO.getId().toString());
                getDeptInfoByDeptId(oaDepartmentCustomizeVO.getId(), keysList);
            }
        }
        return keysList;
    }
}
