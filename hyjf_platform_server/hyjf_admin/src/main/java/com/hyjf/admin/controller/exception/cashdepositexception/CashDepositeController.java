package com.hyjf.admin.controller.exception.cashdepositexception;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.beans.request.AssetListViewRequest;
import com.hyjf.admin.beans.response.InitCashDepositListResponse;
import com.hyjf.admin.beans.vo.AdminAssetListCustomizeVO;
import com.hyjf.admin.beans.vo.AdminHjhInstConfigAPIVO;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.AssetListService;
import com.hyjf.admin.service.CashDepositeService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.AssetListCustomizeResponse;
import com.hyjf.am.resquest.admin.AssetListRequest;
import com.hyjf.am.vo.admin.AssetListCustomizeVO;
import com.hyjf.am.vo.user.HjhInstConfigVO;
import com.hyjf.common.util.CommonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 保证金不足
 * @author jijun
 * @date 20180706
 */
@Api(value = "异常中心-保证金不足",tags = "异常中心-保证金不足")
@RestController
@RequestMapping("/hyjf-admin/exception/cashdepositexception")
public class CashDepositeController extends BaseController {

    @Autowired
    private AssetListService assetListService;
    @Autowired
    private CashDepositeService cashDepositeService;
    
    /**
     *  保证金不足的资产列表
     * @return 进入资产列表页面
     */
    @ApiOperation(value = "保证金不足的资产列表", notes = "保证金不足的资产列表")
    @PostMapping("/searchList")
    public AdminResult<InitCashDepositListResponse> searchList(AssetListViewRequest viewRequest) {
    	JSONObject jsonObject = new JSONObject();
    	List<AssetListCustomizeVO> list = null ;
    	// 总条数
        AssetListRequest request = new AssetListRequest();
        BeanUtils.copyProperties(viewRequest,request);
        // 保证金不足的资产列表list
    	AssetListCustomizeResponse response = assetListService.findAssetList(request);
        //返回给前端
        InitCashDepositListResponse initCashDepositResponse = new InitCashDepositListResponse();

    	if(response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
		}else if(!Response.isSuccess(response)){
            return new AdminResult<>(FAIL, response.getMessage());
        }else{
            initCashDepositResponse.setAssetList(CommonUtils.convertBeanList(response.getResultList(),AdminAssetListCustomizeVO.class));
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
     * 根据选择的方式，处理标的（重新校验保证金，或者流标）
     * @param form
     * @param menuHide  1  重新验证保证金 0 流标
     * @return
     */
    @ApiOperation(value = "批量处理", notes = "批量处理")
    @PostMapping("/modifyAsset")
    private JSONObject modifyAction(AssetListViewRequest form,String menuHide){
        JSONObject ret = new JSONObject();
        String assetId = form.getAssetIdSrch();
        String[] split = assetId.split(",");
        Boolean flag=true;
        for (String asid : split) {
            try {
            	
                this.cashDepositeService.updateCashDepositeStatus(asid, menuHide);
            }catch (Exception e){
                logger.info("==>" + "保证金不足处理失败（资产编号assetId）："+asid+"处理方式（1重验，0 流标）："+menuHide);
                flag=false;
            }
        }
        if(flag){
            ret.put("success",true);
            ret.put("msg","保证金不足处理成功");
        }else{
            ret.put("success",false);
            ret.put("msg","保证金不足处理失败");
        }
        return ret;
    }
    
    
    
    
}
