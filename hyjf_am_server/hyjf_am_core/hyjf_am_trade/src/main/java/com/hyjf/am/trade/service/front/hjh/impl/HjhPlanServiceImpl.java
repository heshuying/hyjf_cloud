/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.front.hjh.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.trade.HjhPlanRequest;
import com.hyjf.am.resquest.trade.TenderRequest;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.dao.model.customize.DebtPlanAccedeCustomize;
import com.hyjf.am.trade.dao.model.customize.DebtPlanBorrowCustomize;
import com.hyjf.am.trade.dao.model.customize.HjhPlanCustomize;
import com.hyjf.am.trade.dao.model.customize.UserHjhInvistDetailCustomize;
import com.hyjf.am.trade.service.front.hjh.HjhPlanService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.borrow.BorrowAndInfoVO;
import com.hyjf.am.vo.trade.hjh.HjhAccedeCustomizeVO;
import com.hyjf.am.vo.trade.hjh.HjhAccedeVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanVO;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.CheckException;
import com.hyjf.common.util.CommonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author fuqiang
 * @version HjhPlanServiceImpl, v0.1 2018/6/13 17:23
 */
@Service
public class HjhPlanServiceImpl extends BaseServiceImpl implements HjhPlanService {


    @Override
    public List<HjhInstConfig> selectHjhInstConfigByInstCode(String instCode) {
        HjhInstConfigExample example = new HjhInstConfigExample();
        HjhInstConfigExample.Criteria cra = example.createCriteria();
        if (StringUtils.isNotEmpty(instCode)&&!"-1".equals(instCode)) {
            cra.andInstCodeEqualTo(instCode);
        }
        cra.andDelFlagEqualTo(0);
        List<HjhInstConfig> list = this.hjhInstConfigMapper.selectByExample(example);
        return list;
    }

    @Override
    public List<HjhLabel> seleHjhLabelByBorrowStyle(String borrowStyle) {
        HjhLabelExample example = new HjhLabelExample();
        HjhLabelExample.Criteria cra = example.createCriteria();

        cra.andDelFlagEqualTo(0);
        cra.andLabelStateEqualTo(1);
        cra.andBorrowStyleEqualTo(borrowStyle);
        cra.andIsCreditEqualTo(0); // 原始标
        cra.andIsLateEqualTo(0); // 是否逾期
        example.setOrderByClause(" update_time desc ");

        List<HjhLabel> list = this.hjhLabelMapper.selectByExample(example);
        return list;
    }

    /**
     * @param planNid
     * @Description 根据计划编号查询计划
     * @Author sunss
     * @Version v0.1
     * @Date 2018/6/19 14:08
     */
    @Override
    public HjhPlan getHjhPlanByNid(String planNid) {
        HjhPlanExample example = new HjhPlanExample();
        HjhPlanExample.Criteria cra = example.createCriteria();
        cra.andPlanNidEqualTo(planNid);
        List<HjhPlan> list = this.hjhPlanMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(list)) {
            return list.get(0);
        }
        return null;
    }

    /**
     * @param planNid
     * @Description 根据计划编号查询计划(主库)
     * @Author sunss
     * @Version v0.1
     * @Date 2018/6/19 14:08
     */
    @Override
    public HjhPlan doGetHjhPlanByNid(String planNid) {
        HjhPlanExample example = new HjhPlanExample();
        HjhPlanExample.Criteria cra = example.createCriteria();
        cra.andPlanNidEqualTo(planNid);
        List<HjhPlan> list = this.hjhPlanMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(list)) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 取得全部汇计划列表
     * @return
     */
    @Override
    public List<HjhPlan> selectHjhPlanList() {
        HjhPlanExample example = new HjhPlanExample();
        return this.hjhPlanMapper.selectByExample(example);
    }

    /**
     * 插入计划明细表
     *
     * @param accedeVO
     * @param userAccount
     * @return
     */
    @Override
    public int insertHJHPlanAccede(HjhAccedeVO accedeVO, Account userAccount) {
        TenderRequest request = accedeVO.getRequest();
        HjhAccede planAccede = new HjhAccede();
        BeanUtils.copyProperties(accedeVO, planAccede);
        // 插入出借记录
        int planAccedeFlag = hjhAccedeMapper.insertSelective(planAccede);
        if(planAccedeFlag==1){
            // 插入资金明细表
            this.insertHJHPlanAccoutList(request,userAccount);
        }
        return 1;
    }

    /**
     * 获取汇计划出借详情
     * @param params
     * @return
     */
    @Override
    public UserHjhInvistDetailCustomize selectUserHjhInvistDetail(Map<String, Object> params) {
        return hjhPlanCustomizeMapper.selectUserHjhInvistDetail(params);
    }

    @Override
    public List<HjhPlanCustomize> selectAppHomeHjhPlan(HjhPlanRequest request) {
        Map<String,Object> param = new HashMap<>();
        param.put("limitStart",request.getLimitStart());
        param.put("limitEnd",request.getLimitEnd());
        param.put("lockFlag",request.getLockFlag());
        return hjhPlanCustomizeMapper.getHjhPlanAppList(param);
    }

    @Override
    public HjhPlanVO getHjhPlan(String planNid) {
        HjhPlanExample example=new HjhPlanExample();
        HjhPlan hjhPlan = new HjhPlan();
        example.createCriteria().andPlanNidEqualTo(planNid);
        List<HjhPlan> list=hjhPlanMapper.selectByExample(example);

        if(list !=null && !list.isEmpty()){
            hjhPlan =  list.get(0);

        }
        return CommonUtils.convertBean(hjhPlan,HjhPlanVO.class);
    }

    @Override
    public HjhAccedeVO getHjhAccede(String orderId) {
        HjhAccedeExample example = new HjhAccedeExample();
        example.createCriteria().andAccedeOrderIdEqualTo(orderId);
        List<HjhAccede> accedeList = hjhAccedeMapper.selectByExample(example);

        if(accedeList !=null && !accedeList.isEmpty()){
            return CommonUtils.convertBean(accedeList.get(0),HjhAccedeVO.class);

        }

        return null;
    }

    /**
     * 查询计划标的组成count
     * @author zhangyk
     * @date 2018/7/23 10:43
     */
    @Override
    public int getPlanBorrowListCount(Map<String, Object> params) {
     return hjhPlanCustomizeMapper.getPlanBorrowListCount(params);
    }

    /**
     * 查询计划标的组成list
     * @author zhangyk
     * @date 2018/7/23 10:43
     */
    @Override
    public List<BorrowAndInfoVO> getPlanBorrowList(Map<String, Object> params) {
        return hjhPlanCustomizeMapper.getPlanBorrowList(params);
    }

    @Override
    public Map<String, Object> getPlanAccecdeTotal(Map<String, Object> params) {
        return hjhPlanCustomizeMapper.getPlanAccedeTotal(params);
    }

    @Override
    public List<HjhAccedeCustomizeVO> getPlanAccecdeList(Map<String, Object> params) {
        return hjhPlanCustomizeMapper.getPlanAccedeList(params);
    }

    /**
     * 统计相应的计划加入记录总数
     * @param params
     * @return
     */
    @Override
    public int countPlanBorrowRecordTotal(Map<String, Object> params) {
        int count = this.hjhPlanCustomizeMapper.countPlanBorrowRecordTotal(params);
        return count;
    }


    /**
     * 查询相应的计划标的列表
     * @param params
     * @return
     */
    @Override
    public List<DebtPlanBorrowCustomize> selectPlanBorrowList(Map<String, Object> params) {
        List<DebtPlanBorrowCustomize> planAccedeList = this.hjhPlanCustomizeMapper.selectPlanBorrowList(params);
        for (DebtPlanBorrowCustomize planAccede : planAccedeList) {
            String borrowNid = planAccede.getBorrowNid();
            if ("1".equals(planAccede.getCompanyOrPersonal())) {//如果类型是公司 huiyingdai_borrow_users
                BorrowUserExample caExample = new BorrowUserExample();
                BorrowUserExample.Criteria caCra = caExample.createCriteria();
                caCra.andBorrowNidEqualTo(borrowNid);
                List<BorrowUser> selectByExample = this.borrowUserMapper.selectByExample(caExample);
                String trueName= selectByExample.get(0).getUsername();
                String str = "******";
                String userNameEncry = "";
                if (StringUtils.isNotBlank(trueName)) {
                    if (trueName.length()>=2){
                        userNameEncry = trueName.replaceFirst(trueName.substring(0,trueName.length()-2),str);
                    }else {
                        userNameEncry = str+trueName;
                    }
                }
                planAccede.setTrueName(userNameEncry);
            }else if("2".equals(planAccede.getCompanyOrPersonal())){//类型是个人 huiyingdai_borrow_maninfo
                //根据borrowNid查询查询个人的真实姓名
                BorrowManinfoExample boExample = new BorrowManinfoExample();
                BorrowManinfoExample.Criteria caCra = boExample.createCriteria();
                caCra.andBorrowNidEqualTo(borrowNid);
                List<BorrowManinfo> selectByExample = this.borrowManinfoMapper.selectByExample(boExample);
                String trueName = selectByExample.get(0).getName();
                String str = "**";
                String userNameEncry = "";
                if (StringUtils.isNotBlank(trueName)) {
                    userNameEncry=trueName.substring(0,1)+str;
                }
                planAccede.setTrueName(userNameEncry);
            }
        }
        return planAccedeList;
    }

    /**
     * 统计相应的计划总数
     * @param params
     * @return
     */
    @Override
    public Long selectPlanAccedeSum(Map<String, Object> params) {
        return hjhPlanCustomizeMapper.selectPlanAccedeSum(params);
    }

    /**
     * 查询计划的加入记录
     * @param params
     * @return
     */
    @Override
    public List<DebtPlanAccedeCustomize> selectPlanAccedeList(Map<String, Object> params) {
        List<DebtPlanAccedeCustomize> planAccedeList = this.hjhPlanCustomizeMapper.selectPlanAccedeList(params);
        return planAccedeList;
    }

    /**
     * 更新显示的计划开启或者关闭
     * 1 开启计划 2 关闭计划
     * @param status
     * @return
     */
    @Override
    public int updateHjhPlanForJoinSwitch(int status) {
        // 更新条件（只更新显示的计划开启或者关闭）
        HjhPlanExample example = new HjhPlanExample();
        HjhPlanExample.Criteria cra = example.createCriteria();
        cra.andPlanDisplayStatusEqualTo(1);
        // 更新内容（计划开启或者关闭）
        HjhPlanWithBLOBs hjhPlanBLOBs = new HjhPlanWithBLOBs();
        hjhPlanBLOBs.setPlanInvestStatus(status);
        // 更新
        return this.hjhPlanMapper.updateByExampleSelective(hjhPlanBLOBs, example);
    }

    /**
     * 插入资金明细表
     * @param request
     * @param accedeAccount
     */
    private void insertHJHPlanAccoutList(TenderRequest request, Account accedeAccount) {
        BigDecimal accountDecimal = new BigDecimal(request.getAccount());//用户出借金额
        AccountVO tenderAccount = request.getTenderAccount();
        // 冻结金额(查询当前用户)
        BigDecimal frost = tenderAccount.getBankFrost();
        // 总金额(查询当前用户)
        BigDecimal total = tenderAccount.getBankTotal();

        Account account = new Account();
        account.setUserId(request.getUser().getUserId());
        // 计划加入总金额
        account.setPlanAccedeTotal(accountDecimal);
        // 计划可用余额
        account.setPlanBalance(accountDecimal);

        // 计划累计待收总额
        account.setPlanInterestWait(request.getEarnings());
        // 计划累计待收本金
        account.setPlanCapitalWait(accountDecimal);
        account.setPlanBalance(accountDecimal);//注意：先set值，加法运算放在SQL中防并发
        account.setBankBalance(accountDecimal);//注意：先set值，减法运算放在SQL中防并发

        //new added APP 更新用户的累积出借金额
        account.setBankInvestSum(accountDecimal);//注意：先set值，加法运算放在SQL中防并发
        // 计划待收利息
        account.setPlanAccountWait(accountDecimal.add(request.getEarnings()));
        account.setBankTotal(accountDecimal);
        logger.info("加入计划账户 开始操作 account :{}",JSONObject.toJSONString(account));
        // 更新用户计划账户
        // mod by liuyang 20190221 更新账户时,可用大于投资金额时,才更新 start
        boolean accountFlag = hjhPlanCustomizeMapper.updateOfHjhPlanJoin(account)> 0 ? true : false;
        // mod by liuyang 20190221 更新账户时,可用大于投资金额时,才更新 end
        logger.info("加入计划账户 操作account结果 :{}",accountFlag);
        accedeAccount = getAccount(request.getUserId());
        // 组装accountList
        AccountList accountList = new AccountList();
        accountList.setIsBank(1);
        accountList.setIsShow(0);
        accountList.setNid(request.getPlanOrderId());//插入生成的计划订单号
        accountList.setAccedeOrderId(request.getPlanOrderId());//也插入生成的计划订单号
        accountList.setUserId(request.getUser().getUserId());
        accountList.setAmount(accountDecimal);//插入用户加入金额
        accountList.setType(2);// 收支类型1收入2支出3冻结
        accountList.setTrade("hjh_invest");// 汇计划出借
        accountList.setTradeCode("balance");
        accountList.setIp(request.getIp());
        accountList.setOperator(request.getUser().getUserId() + "");
        accountList.setRemark(request.getBorrowNid());
        accountList.setBankBalance(accedeAccount.getBankBalance());//江西银行账户余额
        accountList.setPlanBalance(accedeAccount.getPlanBalance());//汇计划账户可用余额
        accountList.setBalance(accedeAccount.getBalance());
        //accountList.setInterest(new BigDecimal(0));
        accountList.setAwait(accedeAccount.getAwait());
        accountList.setPlanFrost(accedeAccount.getPlanFrost());
        accountList.setBankFrost(frost);
        accountList.setRepay(new BigDecimal(0));
        accountList.setBankTotal(total);
        accountList.setWeb(0);
        accountList.setAccountId(request.getBankOpenAccount().getAccount());
        // 插入accountList
        accountListMapper.insertSelective(accountList);
        Map<String, Object> planUpdate = new HashMap<String, Object>();
        planUpdate.put("planId", request.getBorrowNid());
        planUpdate.put("accountDecimal", accountDecimal);//用户加入金额
        planUpdate.put("earnings", request.getEarnings());
        logger.info("开始更新计划表：{}",JSONObject.toJSONString(planUpdate));
        // 更新计划表
        boolean updateBorrowAccountFlag = hjhPlanCustomizeMapper.updateByDebtPlanId(planUpdate) > 0 ? true : false;
        if(!updateBorrowAccountFlag){
            logger.error("更新计划表失败   {} ",JSONObject.toJSONString(planUpdate));
            throw new CheckException(MsgEnum.ERR_AMT_TENDER_INVESTMENT);
        }
        /*(5)更新  平台累积出借   开始*/
       /* List<CalculateInvestInterest> calculates = this.calculateInvestInterestMapper.selectByExample(new CalculateInvestInterestExample());
        if (calculates != null && calculates.size() > 0) {
            CalculateInvestInterest calculateNew = new CalculateInvestInterest();
            calculateNew.setTenderSum(accountDecimal);
            calculateNew.setId(calculates.get(0).getId());
            calculateNew.setCreateTime(GetDate.getDate(nowTime));
            this.webCalculateInvestInterestCustomizeMapper.updateCalculateInvestByPrimaryKey(calculateNew);
        }*/
    }
}
