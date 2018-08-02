package com.hyjf.admin.controller.manager;

import com.alibaba.fastjson.JSONArray;
import com.hyjf.admin.beans.request.AccountBalanceMonitoringRequestBean;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.AccountBalanceMonitoringService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.AdminAccountBalanceMonitoringResponse;
import com.hyjf.am.resquest.admin.AdminAccountBalanceMonitoringRequest;
import com.hyjf.am.vo.admin.MerchantAccountVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author by xiehuili on 2018/7/13.
 */
@Api(value = "配置中心平台账户配置",tags ="配置中心平台账户配置")
@RestController
@RequestMapping("/hyjf-admin/config/accountbalance")
public class AccountBalanceMonitoringController extends BaseController {


    //权限名称
    private static final String PERMISSIONS = "accountbalance";
    @Autowired
    private AccountBalanceMonitoringService accountBalanceMonitoringService;

    @ApiOperation(value = "配置中心平台账户配置", notes = "查询配置中心平台账户配置 余额监控")
    @RequestMapping("/init")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<ListResult<MerchantAccountVO>> accountBalanceMonitoringInit(@RequestBody AccountBalanceMonitoringRequestBean requestBean) {
        AdminAccountBalanceMonitoringRequest adminRequest= new AdminAccountBalanceMonitoringRequest();
        //可以直接使用
        BeanUtils.copyProperties(requestBean, adminRequest);
        AdminAccountBalanceMonitoringResponse response=accountBalanceMonitoringService.selectaccountBalanceMonitoringByPage(adminRequest);
        if(response==null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());

        }
        return new AdminResult<ListResult<MerchantAccountVO>>(ListResult.build(response.getResultList(), response.getRecordTotal())) ;
    }

        @ApiOperation(value = "配置中心平台账户配置", notes = "检索配置中心平台账户配置 余额监控")
        @RequestMapping("/searchAction")
        @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_SEARCH)
        public AdminResult<ListResult<MerchantAccountVO>> accountBalanceMonitoringSearch(@RequestBody AccountBalanceMonitoringRequestBean requestBean) {
            AdminAccountBalanceMonitoringRequest adminRequest= new AdminAccountBalanceMonitoringRequest();
            //可以直接使用
            BeanUtils.copyProperties(requestBean, adminRequest);
            AdminAccountBalanceMonitoringResponse response=accountBalanceMonitoringService.selectaccountBalanceMonitoringByPage(adminRequest);
            if(response==null) {
                return new AdminResult<>(FAIL, FAIL_DESC);
            }
            if (!Response.isSuccess(response)) {
                return new AdminResult<>(FAIL, response.getMessage());

            }
            return new AdminResult<ListResult<MerchantAccountVO>>(ListResult.build(response.getResultList(), response.getRecordTotal())) ;
        }

        @ApiOperation(value = "配置中心平台账户配置", notes = "平台账户配置 余额监控 详情页面")
        @PostMapping("/infoAction")
        @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_INFO)
        public AdminResult<MerchantAccountVO>  merchantAccountInfo(@RequestBody AccountBalanceMonitoringRequestBean requestBean) {
            AdminAccountBalanceMonitoringRequest adminRequest= new AdminAccountBalanceMonitoringRequest();
            //可以直接使用
            BeanUtils.copyProperties(requestBean, adminRequest);
            AdminAccountBalanceMonitoringResponse adminResponse= accountBalanceMonitoringService.selectaccountBalanceMonitoringById(adminRequest);
            if (adminResponse == null) {
                return new AdminResult<>(FAIL, FAIL_DESC);
            }
            if (!Response.isSuccess(adminResponse)) {
                return new AdminResult<>(FAIL, adminResponse.getMessage());
            }
            return new AdminResult<MerchantAccountVO>(adminResponse.getResult()) ;
        }

    @ApiOperation(value = "配置中心平台账户配置", notes = "平台账户配置 余额监控详情页面")
    @PostMapping("/updateAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_INFO)
    public AdminResult<MerchantAccountVO>  updateMerchantAccount(@RequestBody AccountBalanceMonitoringRequestBean requestBean) {
        AdminAccountBalanceMonitoringRequest adminRequest= new AdminAccountBalanceMonitoringRequest();
        BeanUtils.copyProperties(requestBean, adminRequest);
        // 画面传来的值
        List<AccountBalanceMonitoringRequestBean> updateDataList = this.setPageListInfo( requestBean);
        // 数据库检索的数据
        List<AccountBalanceMonitoringRequestBean> list = this.accountBalanceMonitoringService.searchMerchantAccountList(adminRequest, -1, -1);

        List<AccountBalanceMonitoringRequestBean> resultList = this.forback(list);
        // 判断数据是否更新
        List<AccountBalanceMonitoringRequestBean> updateList = this.compareResultList(resultList, updateDataList);
        // 数据更新
        AdminAccountBalanceMonitoringResponse adminResponse=this.accountBalanceMonitoringService.updateMerchantAccountList(updateList);
        if (adminResponse == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(adminResponse)) {
            return new AdminResult<>(FAIL, adminResponse.getMessage());
        }
        return new AdminResult<MerchantAccountVO>(adminResponse.getResult()) ;
    }

    /**
     *
     * 判断数据是否有变化
     * @param oldList
     * @param newList
     */
    private List<AccountBalanceMonitoringRequestBean> compareResultList(List<AccountBalanceMonitoringRequestBean> oldList,
                                                         List<AccountBalanceMonitoringRequestBean> newList) {
        List<AccountBalanceMonitoringRequestBean> resultList = new ArrayList<AccountBalanceMonitoringRequestBean>();
        for (int i = 0; i < oldList.size(); i++) {
            AccountBalanceMonitoringRequestBean oldRecord = oldList.get(i);
            AccountBalanceMonitoringRequestBean newRecord = newList.get(i);
            // 判断数据是否有更新
            if (oldRecord.getIds().equals(newRecord.getIds())) {
                Long oldData =
                        oldRecord.getBalanceLowerLimit() == null ? new Long(0) : oldRecord.getBalanceLowerLimit();
                Long newData =
                        newRecord.getBalanceLowerLimit() == null ? new Long(0) : newRecord.getBalanceLowerLimit();
                // 数据没有变更的情况
                if (oldData.equals(newData)
                        && oldRecord.getAutoTransferInto().equals(newRecord.getAutoTransferInto())
                        && oldRecord.getAutoTransferOut().equals(newRecord.getAutoTransferOut())
                        && (oldRecord.getTransferIntoRatio() == null ? 0 : oldRecord.getTransferIntoRatio()) == (newRecord
                        .getTransferIntoRatio() == null ? 0 : newRecord.getTransferIntoRatio())) {
                    newRecord.setUpdateFlg(false);
                } else {
                    // 数据变更的情况
                    newRecord.setUpdateFlg(true);
                }
            }
            resultList.add(newRecord);
        }

        return resultList;
    }
    /**
     *
     * 画面项目反馈
     * @param recordList
     * @return
     */
    private List<AccountBalanceMonitoringRequestBean> forback(List<AccountBalanceMonitoringRequestBean> recordList) {
        List<AccountBalanceMonitoringRequestBean> result = new ArrayList<AccountBalanceMonitoringRequestBean>();
        for (int i = 0; i < recordList.size(); i++) {
            AccountBalanceMonitoringRequestBean record = new AccountBalanceMonitoringRequestBean();
            BeanUtils.copyProperties(recordList.get(i), record);
            record.setIds(String.valueOf(recordList.get(i).getId()));
            result.add(record);
        }
        return result;
    }
    /**
     *
     * json数据转换成实体
     * @param from
     * @return
     */
    public List<AccountBalanceMonitoringRequestBean> setPageListInfo(AccountBalanceMonitoringRequestBean from) {
        List<AccountBalanceMonitoringRequestBean> merchantAccountList = new ArrayList<AccountBalanceMonitoringRequestBean>();
        merchantAccountList = JSONArray.parseArray(from.getBalanceDataJson(), AccountBalanceMonitoringRequestBean.class);
        for (AccountBalanceMonitoringRequestBean merchantAccount : merchantAccountList) {
            merchantAccount.setId(Integer.parseInt(merchantAccount.getIds()));
        }
        return merchantAccountList;
    }


}
