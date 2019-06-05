/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.controller.admin.electricitySales;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hyjf.am.config.dao.model.auto.CustomerServiceChannel;
import com.hyjf.am.config.dao.model.auto.CustomerServiceRepresentiveConfig;
import com.hyjf.am.config.service.config.CustomerServiceRepresentiveConfigService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.user.AccountMobileSynchResponse;
import com.hyjf.am.response.user.ElectricitySalesDataPushListResponse;
import com.hyjf.am.resquest.config.ElectricitySalesDataPushListRequest;
import com.hyjf.am.trade.dao.model.auto.AccountRecharge;
import com.hyjf.am.trade.service.admin.finance.impl.RechargeManagementServiceImpl;
import com.hyjf.am.user.controller.BaseController;
import com.hyjf.am.user.dao.model.auto.AppUtmReg;
import com.hyjf.am.user.dao.model.auto.BankOpenAccount;
import com.hyjf.am.user.dao.model.auto.ElectricitySalesDataPushList;
import com.hyjf.am.user.dao.model.auto.User;
import com.hyjf.am.user.dao.model.auto.UserInfo;
import com.hyjf.am.user.dao.model.auto.UtmPlat;
import com.hyjf.am.user.dao.model.auto.UtmReg;
import com.hyjf.am.user.service.admin.bank.BankOpenService;
import com.hyjf.am.user.service.admin.electricitySales.ElectricitySalesDataPushListService;
import com.hyjf.am.user.service.admin.exception.BorrowRegistRepairService;
import com.hyjf.am.user.service.admin.promotion.UtmRegService;
import com.hyjf.am.user.service.front.user.UserInfoService;
import com.hyjf.am.user.service.front.user.UserService;
import com.hyjf.am.user.service.front.user.UtmPlatService;
import com.hyjf.am.vo.config.CustomerServiceChannelVO;
import com.hyjf.am.vo.config.ElectricitySalesDataPushListVO;
import com.hyjf.am.vo.datacollect.AppUtmRegVO;
import com.hyjf.am.vo.trade.account.AccountRechargeVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UtmPlatVO;
import com.hyjf.am.vo.user.UtmRegVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;

import io.swagger.annotations.ApiOperation;

/**
 * @author: dzs
 * @version: AccountMobileSynchController, v0.1 2018/8/15 14:08
 */
@RestController(value = "electricitySalesDataPushListController")
@RequestMapping("/am-user/electricitySales")
public class ElectricitySalesDataPushListController extends BaseController {

    @Autowired
    private  ElectricitySalesDataPushListService electricitySalesDataPushListService;
    @Autowired
    private  CustomerServiceRepresentiveConfigService customerServiceRepresentiveConfigService;
    @Autowired
    private  UserService userService;
    @Autowired
    private  UserInfoService userInfoService;
    @Autowired
    private UtmRegService utmRegService;
    @Autowired
    private BorrowRegistRepairService borrowRegistRepairService;
    @Autowired
    private UtmPlatService utmPlatService;
    @Autowired
    private RechargeManagementServiceImpl rechargeManagementService;
    /**
     * 线下修改信息同步查询列表list
     * @auth dzs
     * @param
     * @return
     */
    @ApiOperation(value = "ist查询",notes = "list查询")
    @PostMapping(value = "/electricitySalesDataPushList")
    public ElectricitySalesDataPushListResponse searchModifyInfoList(@RequestBody ElectricitySalesDataPushListRequest request){
    	ElectricitySalesDataPushListResponse response = new ElectricitySalesDataPushListResponse();
        Integer count = electricitySalesDataPushListService.getCount(request);
        // currPage<0 为全部,currPage>0 为具体某一页
        List<ElectricitySalesDataPushList> list=new ArrayList<ElectricitySalesDataPushList>();
        if(request.getCurrPage()>0){
            Paginator paginator = new Paginator(request.getCurrPage(),count,request.getPageSize());
            list = electricitySalesDataPushListService.searchList(request,paginator.getOffset(),paginator.getLimit());
        }
        
        if(!CollectionUtils.isEmpty(list)){
            List<ElectricitySalesDataPushListVO> VOList = CommonUtils.convertBeanList(list,ElectricitySalesDataPushListVO.class);
            response.setResultList(VOList);
            response.setRtn(Response.SUCCESS);
            response.setRecordTotal(count);
        }
        return response;
    }

    /**
     * 添加信息
     * @auth dzs
     * @param
     * @return
     */
    @ApiOperation(value = "插入多条信息",notes = "插入多条信息")
    @PostMapping(value = "/insertAlectricitySalesDataPushList")
    public ElectricitySalesDataPushListResponse insertAccountMobileSynch(@RequestBody ElectricitySalesDataPushListRequest request){
		List<ElectricitySalesDataPushListVO> list = request.getElectricitySalesDataPushList();
		List<ElectricitySalesDataPushList> list2=new ArrayList<ElectricitySalesDataPushList>();
		for (ElectricitySalesDataPushListVO electricitySalesDataPushListVO : list) {
			
			ElectricitySalesDataPushList record=new ElectricitySalesDataPushList();
			CustomerServiceRepresentiveConfig csrcs = customerServiceRepresentiveConfigService.getCustomerServiceRepresentiveConfig(electricitySalesDataPushListVO.getOwnerUserName());
			if(csrcs==null) {
				continue;
			}
			User user = userService.findUserByUsernameOrMobile(electricitySalesDataPushListVO.getUserName());
			if(user==null) {
				continue;
			}
			UserInfo userinfo = userInfoService.findUserInfoById(user.getUserId());
			record.setOwnerUserName(electricitySalesDataPushListVO.getOwnerUserName());
			record.setGroupId(csrcs.getGroupId());	
			record.setGroupName(csrcs.getGroupName());
			record.setUserName(electricitySalesDataPushListVO.getUserName());
			record.setUserId(user.getUserId());
			record.setMobile(user.getMobile());
			record.setRoleId(userinfo.getRoleId());
			record.setTrueName(record.getTrueName());
			record.setSex(userinfo.getSex());
			record.setAge(getAgeByCertId(userinfo.getIdcard()));
			record.setBirthday(userinfo.getBirthday());
			record.setRegTime(user.getRegTime());
			BankOpenAccountVO account = borrowRegistRepairService.searchBankOpenAccount(user.getUserId());
			record.setBankAccount(account == null ? "" : account.getAccount());
			  // 判断用户渠道是否是推送禁用
            // 判断用户是否是PC推广渠道用户
            UtmReg utmReg = this.utmRegService.selectUtmRegByUserId(user.getUserId());
            // 推广渠道
            UtmPlat utmPlatVO = null;
            if (utmReg != null) {
                // 如果是PC推广渠道,判断渠道是否是推送禁用
                Integer utmId = utmReg.getUtmId();
                // 根据utmId查询推广渠道
                utmPlatVO = this.utmPlatService.getUtmPlat(utmId);
                if (utmPlatVO != null) {
                    // 渠道ID
                    Integer sourceId = utmPlatVO.getSourceId();
//                    // 根据sourceId查询该渠道是否被禁用
                    CustomerServiceChannel customerServiceChannel = this.customerServiceRepresentiveConfigService.selectCustomerServiceChannelBySourceId(sourceId);
                    if (customerServiceChannel != null) {
                        // 如果被禁用了,continue
                        continue;
                    }
                }
            }
            // 判断用户是否是App推广渠道用户
            AppUtmReg appUtmReg = this.utmRegService.selectAppUtmRegByUserId(user.getUserId());
            if (appUtmReg != null) {
                // 如果是App推广渠道的用户
                Integer sourceId = appUtmReg.getSourceId();
                // 根据sourceId查询该渠道是否被禁用
                CustomerServiceChannel customerServiceChannel = this.customerServiceRepresentiveConfigService.selectCustomerServiceChannelBySourceId(sourceId);
                if (customerServiceChannel != null) {
                    // 如果被禁用了,continue
                    continue;
                }
            }
            
            // PC推广渠道
    		record.setPcSourceId(utmPlatVO == null ? null : utmPlatVO.getSourceId());
            // PC推广渠道
    		record.setPcSourceName(utmPlatVO == null ? null : utmPlatVO.getSourceName());
            // App推广渠道
    		record.setAppSourceId(appUtmReg == null ? null : appUtmReg.getSourceId());
            // App推广渠道
    		record.setAppSourceName(appUtmReg == null ? null : appUtmReg.getSourceName());
            // 获取用户充值记录
            AccountRecharge accountRecharge = this.rechargeManagementService.selectAccountRechargeByUserId(user.getUserId());
            if (accountRecharge != null) {
                // 充值金额
            	record.setRechargeMoney(accountRecharge.getMoney());
                // 充值时间
            	record.setRechargeTime(accountRecharge.getCreateTime());
            } else {
                // 充值金额
            	record.setRechargeMoney(BigDecimal.ZERO);
                // 充值时间
            	record.setRechargeTime(null);
            }
            // 是否是渠道:固定0:非渠道
    		record.setChannel(0);
    		record.setUploadType(1);
    		record.setStatus(0);
    		list2.add(record);
			
		}
		ElectricitySalesDataPushListResponse response = new ElectricitySalesDataPushListResponse();
		int count = electricitySalesDataPushListService.insertElectricitySalesDataPushList(list2);
        response.setRecordTotal(count);
        response.setRtn(Response.SUCCESS);
        return response;
    }

    /**
     * 根据身份证号获取年龄
     * @param certId
     * @return
     */
    public static int getAgeByCertId(String certId) {
        String birthday = "";
        if (certId.length() == 18) {
           birthday = certId.substring(6, 10) + "/"
                   + certId.substring(10, 12) + "/"
                   + certId.substring(12, 14);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");  
        Date now = new Date();
        Date birth = new Date();
        try {
            birth = sdf.parse(birthday);
        } catch (ParseException e) {
        }
        long intervalMilli = now.getTime() - birth.getTime();
        int age = (int) (intervalMilli/(24 * 60 * 60 * 1000))/365;
        return age;
    }


}
