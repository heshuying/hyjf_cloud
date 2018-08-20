package com.hyjf.admin.controller.finance.recharge;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.beans.request.AccountRechargeRequestBean;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.BaseResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.finance.recharge.AccountRechargeService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.trade.account.AccountRechargeResponse;
import com.hyjf.am.resquest.admin.AccountRechargeRequest;
import com.hyjf.am.vo.config.ParamNameVO;
import com.hyjf.am.vo.trade.BanksConfigVO;
import com.hyjf.am.vo.trade.account.AccountRechargeVO;
import com.hyjf.common.util.CommonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 资金中心 - 充值管理
 * @Author : huanghui
 */
@Api(value = "资金中心->充值管理",tags = "资金中心->充值管理")
@RestController
@RequestMapping("/hyjf-admin/recharge")
public class AccountRechargeController extends BaseController {

    @Autowired
    private AccountRechargeService rechargeService;

    /**
     * 资金中心 - 充值管理 检索下拉框
     * @return
     */
    @ApiOperation(value = "充值管理检索下拉框", notes = "充值管理检索下拉框")
    @RequestMapping(value = "/dropDownBox", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject dropDownBox(){
        JSONObject jsonObject = new JSONObject();

        //用户账户充值状态
        List<ParamNameVO> paramList = this.rechargeService.getParamNameList("RECHARGE_STATUS");

        List<Object> rechargeStatusList = new ArrayList<>();
        for(int i = 0; i<paramList.size(); i++){
            Map<String, Object> rechargeStatusMap = new HashedMap();
            rechargeStatusMap.put("key", paramList.get(i).getNameCd());
            rechargeStatusMap.put("value", paramList.get(i).getName());
            rechargeStatusList.add(rechargeStatusMap);
        }

        // 充值银行列表
        List<BanksConfigVO> banks = this.rechargeService.getBankcardList();

        List<Object> banksList = new ArrayList<>();
        for(int i = 0; i<banks.size(); i++){
            Map<String, Object> banksMap = new HashedMap();
            banksMap.put("key", banks.get(i).getName());
            banksMap.put("value", banks.get(i).getName());
            banksList.add(banksMap);
        }

        // 资金托管平台
        List<ParamNameVO> bankType = this.rechargeService.getParamNameList("BANK_TYPE");

        List<Object> bankTypeList = new ArrayList<>();
        for(int i = 0; i<bankType.size(); i++){
            Map<String, Object> bankTypeMap = new HashedMap();
            bankTypeMap.put("key", bankType.get(i).getNameCd());
            bankTypeMap.put("value", bankType.get(i).getName());
            bankTypeList.add(bankTypeMap);
        }

        //充值类型
        //初始充值类型List
        List<Object> rechargeList = new ArrayList<>();

        //初始充值类型Map
        Map<String, Object> rechargeType = new HashedMap();
        Map<String, Object> rechargeType2 = new HashedMap();
        Map<String, Object> rechargeType3 = new HashedMap();
        Map<String, Object> rechargeType4 = new HashedMap();

        //设置对应的键值对
        rechargeType.put("key", "B2C");
        rechargeType.put("value", "个人网银充值");
        rechargeType2.put("key", "B2B");
        rechargeType2.put("value", "企业网银充值");
        rechargeType3.put("key", "B2B");
        rechargeType3.put("value", "企业网银充值");
        rechargeType4.put("key", "B2B");
        rechargeType4.put("value", "企业网银充值");

        //map加入List
        rechargeList.add(rechargeType);
        rechargeList.add(rechargeType2);
        rechargeList.add(rechargeType3);
        rechargeList.add(rechargeType4);

        //充值平台
        //初始化充值平台的list 和 Map
        List<Object> rechargePlatformList = new ArrayList<>();
        Map<String, Object> rechargePlatform = new HashedMap();
        Map<String, Object> rechargePlatform2 = new HashedMap();
        Map<String, Object> rechargePlatform3 = new HashedMap();
        Map<String, Object> rechargePlatform4 = new HashedMap();

        rechargePlatform.put("key", 0);
        rechargePlatform.put("value", "PC");
        rechargePlatform2.put("key", 1);
        rechargePlatform2.put("value", "微信");
        rechargePlatform3.put("key", 2);
        rechargePlatform3.put("value", "Android");
        rechargePlatform4.put("key", 3);
        rechargePlatform4.put("value", "iOS");

        //Map 加入 List
        rechargePlatformList.add(rechargePlatform);
        rechargePlatformList.add(rechargePlatform2);
        rechargePlatformList.add(rechargePlatform3);
        rechargePlatformList.add(rechargePlatform4);

        //获取充值手续费方式
        //初始化获取充值手续费List 和 Map
        List<Object> accessFeeMethodList = new ArrayList<>();
        Map<String, Object> accessFeeMethodMap = new HashedMap();
        Map<String, Object> accessFeeMethodMap2 = new HashedMap();

        accessFeeMethodMap.put("key", 0);
        accessFeeMethodMap.put("value", "向用户收取");
        accessFeeMethodMap2.put("key", 1);
        accessFeeMethodMap2.put("value", "向商户收取");

        //Map 加入 List
        accessFeeMethodList.add(accessFeeMethodMap);
        accessFeeMethodList.add(accessFeeMethodMap2);

        //用户角色
        List<Object> userRoleList = new ArrayList<>();
        Map<String, Object> userRoleMap = new HashedMap();
        Map<String, Object> userRoleMap2 = new HashedMap();
        Map<String, Object> userRoleMap3 = new HashedMap();
        Map<String, Object> userRoleMap4 = new HashedMap();

        //选项加入Map
        userRoleMap.put("key", " ");
        userRoleMap.put("value", "全部");
        userRoleMap2.put("key", 1);
        userRoleMap2.put("value", "投资人");
        userRoleMap3.put("key", 2);
        userRoleMap3.put("value", "借款人");
        userRoleMap4.put("key", 3);
        userRoleMap4.put("value", "垫付机构");

        userRoleList.add(userRoleMap);
        userRoleList.add(userRoleMap2);
        userRoleList.add(userRoleMap3);
        userRoleList.add(userRoleMap4);

        // 初始化总的Map
        Map<String, Object> allMap = new HashedMap();
        allMap.put("rechargeStatusList", rechargeStatusList);
        allMap.put("banksList", banksList);
        allMap.put("bankTypeList", bankTypeList);
        allMap.put("rechargeTypeList", rechargeList);
        allMap.put("rechargePlatformList", rechargePlatformList);
        allMap.put("accessFeeMethodList", accessFeeMethodList);
        allMap.put("userRoleList", userRoleList);

        //初始化List 将所有Map放入此List 以达到返回指定格式数据的方式
//        List<Object> allList = new ArrayList<>();
//        allList.add(allMap);

        if (paramList != null && banks != null && bankType != null){
            jsonObject.put("status", BaseResult.SUCCESS);
            jsonObject.put("statusDesc", BaseResult.SUCCESS_DESC);
            jsonObject.put("data", allMap);
        }else {
            jsonObject.put("status", Response.FAIL);
            jsonObject.put("statusDesc", Response.FAIL_MSG);
            jsonObject.put("data", "");
        }
        return jsonObject;
    }

    /**
     * 资金中心 - 充值管理列表
     * @param requestBean
     * @return
     */
    @ApiOperation(value = "充值管理", notes = "资金中心->充值管理")
    @PostMapping(value = "/hjhDayCreditDetailList")
    @ResponseBody
    public AdminResult<ListResult<AccountRechargeVO>> queryRechargeList(@RequestBody AccountRechargeRequestBean requestBean){

        AccountRechargeRequest copyRequest = new AccountRechargeRequest();
        BeanUtils.copyProperties(requestBean, copyRequest);


        //初始化返回List
        List<AccountRechargeVO> returnList = null;
        AccountRechargeResponse rechargeResponse = rechargeService.queryRechargeList(copyRequest);

        if (rechargeResponse == null){
            return new AdminResult<>(FAIL, FAIL_DESC);
        }

        if (!Response.isSuccess(rechargeResponse)){
            return new AdminResult<>(FAIL, rechargeResponse.getMessage());
        }

        if (CollectionUtils.isNotEmpty(rechargeResponse.getResultList())){
            returnList = CommonUtils.convertBeanList(rechargeResponse.getResultList(), AccountRechargeVO.class);
            return new AdminResult<ListResult<AccountRechargeVO>>(ListResult.build(returnList, rechargeResponse.getCount()));
        }else {
            return new AdminResult<ListResult<AccountRechargeVO>>(ListResult.build(returnList, 0));
        }
    }

}
