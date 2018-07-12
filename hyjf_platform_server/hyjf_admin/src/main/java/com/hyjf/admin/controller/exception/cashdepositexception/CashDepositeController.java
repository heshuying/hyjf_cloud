package com.hyjf.admin.controller.exception.cashdepositexception;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.AssetListService;
import com.hyjf.admin.service.CashDepositeService;
import com.hyjf.am.resquest.admin.AssetListRequest;
import com.hyjf.am.vo.admin.AssetListCustomizeVO;
import com.hyjf.am.vo.user.HjhInstConfigVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 保证金不足
 * @author jijun
 * @date 20180706
 */
@Api(value = "保证金不足")
@RestController
@RequestMapping("/exception/cashdepositexception")
public class CashDepositeController extends BaseController {

    @Autowired
    private AssetListService assetListService;
    @Autowired
    private CashDepositeService cashDepositeService;
    
    /**
     *  保证金不足的资产列表
     * @param request
     * @return 进入资产列表页面
     */
    @ApiOperation(value = "保证金不足的资产列表", notes = "保证金不足的资产列表")
    @PostMapping("/searchList")
    public JSONObject searchList(AssetListRequest request) {
    	JSONObject jsonObject = new JSONObject();
    	// 总条数
        Integer count = assetListService.getRecordCount(request);
        jsonObject.put("count",count);
        // 保证金不足的资产列表list
        List<AssetListCustomizeVO> recordList=assetListService.findAssetList(request);
        jsonObject.put("recordList",recordList);
        // 资金来源 下拉框
        List<HjhInstConfigVO> hjhInstConfigList=assetListService.getHjhInstConfigList();
        jsonObject.put("hjhInstConfigList", hjhInstConfigList);
        return jsonObject;
    }

   
    /**
     * 批量处理
     * 根据选择的方式，处理标的（重新校验保证金，或者流标）
     * @param form
     * @param menuHide  1  重新验证保证金 0 流标
     * @return
     */
    @PostMapping("/modifyAsset")
    private JSONObject modifyAction(AssetListRequest form,String menuHide){
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
