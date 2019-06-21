package com.hyjf.admin.controller.repair.cashdepositrepair;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.beans.request.AssetListViewRequest;
import com.hyjf.admin.beans.response.InitCashDepositListResponse;
import com.hyjf.admin.beans.vo.AdminAssetListCustomizeVO;
import com.hyjf.admin.beans.vo.AdminHjhInstConfigAPIVO;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.AssetListService;
import com.hyjf.admin.service.CashDepositeService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.AssetListCustomizeResponse;
import com.hyjf.am.resquest.admin.AssetListRequest;
import com.hyjf.am.vo.admin.AssetListCustomizeVO;
import com.hyjf.am.vo.user.HjhInstConfigVO;
import com.hyjf.common.cache.CacheUtil;
import com.hyjf.common.util.AsteriskProcessUtil;
import com.hyjf.common.util.CommonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 保证金不足
 * @author jijun
 * @date 20180706
 */
@Api(value = "异常中心-合作额度不足",tags = "异常中心-合作额度不足")
@RestController
@RequestMapping("/hyjf-admin/exception/cashdepositexception")
public class CashDepositeController extends BaseController {

    @Autowired
    private AssetListService assetListService;
    @Autowired
    private CashDepositeService cashDepositeService;

    private static final String PERMISSIONS = "cashdepositexception";

    /**
     *  合作额度不足的资产列表
     * @return 进入资产列表页面
     */
    @ApiOperation(value = "合作额度不足的资产列表", notes = "合作额度不足的资产列表")
    @PostMapping("/searchList")
    public AdminResult<InitCashDepositListResponse> searchList(@RequestBody AssetListViewRequest viewRequest,HttpServletRequest httpServletRequest) {

        AssetListRequest request = new AssetListRequest();
        BeanUtils.copyProperties(viewRequest,request);
        // 合作额度不足的资产列表list
    	AssetListCustomizeResponse response = assetListService.findBZJBZList(request);
        //获取查看脱敏数据的权限
        boolean isShow = this.havePermission(httpServletRequest,PERMISSIONS + ":" + ShiroConstants.PERMISSION_HIDDEN_SHOW);
        List<AssetListCustomizeVO> assetList=response.getResultList();
        if(CollectionUtils.isNotEmpty(assetList)){
            Map<String, String> assetStatusMap = CacheUtil.getParamNameMap("ASSET_STATUS");
            Map<String, String> assetApplyStatusMap = CacheUtil.getParamNameMap("ASSET_APPLY_STATUS");
            Map<String, String> accountStatusMap = CacheUtil.getParamNameMap("ACCOUNT_STATUS");
            Map<String, String> userTypeMap = CacheUtil.getParamNameMap("USER_TYPE");
            for(AssetListCustomizeVO assetListCustomizeVO : assetList){
                assetListCustomizeVO.setStatus(assetStatusMap.getOrDefault(assetListCustomizeVO.getStatus(),null));
                assetListCustomizeVO.setVerifyStatus(assetApplyStatusMap.getOrDefault(assetListCustomizeVO.getVerifyStatus(),null));
                assetListCustomizeVO.setBankOpenAccount(accountStatusMap.getOrDefault(assetListCustomizeVO.getBankOpenAccount(),null));
                assetListCustomizeVO.setUserType(userTypeMap.getOrDefault(assetListCustomizeVO.getUserType(),null));
                if(!isShow){
                    assetListCustomizeVO.setMobile(AsteriskProcessUtil.getAsteriskedMobile(assetListCustomizeVO.getMobile()));
                    assetListCustomizeVO.setTruename(AsteriskProcessUtil.getAsteriskedCnName(assetListCustomizeVO.getTruename()));
                    assetListCustomizeVO.setIdcard(AsteriskProcessUtil.getAsteriskedIdcard(assetListCustomizeVO.getIdcard()));
                }

            }

        }

        //返回给前端
        InitCashDepositListResponse initCashDepositResponse = new InitCashDepositListResponse();

        if(response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }else if(!Response.isSuccess(response)){
            return new AdminResult<>(FAIL, response.getMessage());
        }else{
            initCashDepositResponse.setAssetCount(response.getCount());
            initCashDepositResponse.setAssetList(CommonUtils.convertBeanList(assetList,AdminAssetListCustomizeVO.class));
        }
        // 资金来源 下拉框
        List<HjhInstConfigVO> hjhInstConfigList=assetListService.getHjhInstConfigList();
        if (CollectionUtils.isNotEmpty(hjhInstConfigList)){
            initCashDepositResponse.setHjhInstConfigList(CommonUtils.convertBeanList(hjhInstConfigList,AdminHjhInstConfigAPIVO.class));
        }

        return new AdminResult<InitCashDepositListResponse>(initCashDepositResponse);
    }


    /**
     * 批量处理
     * 根据选择的方式，处理标的（重新校验合作额度，或者流标）
     * @param form
     * @return
     */
    @ApiOperation(value = "批量处理", notes = "批量处理")
    @PostMapping("/modifyAsset")
    private AdminResult modifyAction(@RequestBody AssetListViewRequest form){
        JSONObject ret = new JSONObject();
        String assetId = form.getAssetIdSrch();
        String[] split = assetId.split(",");
        Boolean flag=true;
        for (String asid : split) {
            try {

                this.cashDepositeService.updateCashDepositeStatus(asid, form.getMenuHide());
            }catch (Exception e){
                logger.info("==>" + "合作额度不足处理失败（资产编号assetId）："+asid+"处理方式（1重验，0 流标）："+form.getMenuHide());
                flag=false;
            }
        }
        if(flag){
            return new AdminResult<>(SUCCESS, "合作额度不足处理成功");
        }else{
            return new AdminResult<>(FAIL, "合作额度不足处理失败");
        }
    }




}
