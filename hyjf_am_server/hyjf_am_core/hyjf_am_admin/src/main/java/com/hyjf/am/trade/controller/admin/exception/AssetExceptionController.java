/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.admin.exception;

import com.hyjf.am.response.BooleanResponse;
import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.StringResponse;
import com.hyjf.am.response.admin.AssetExceptionCustomizeResponse;
import com.hyjf.am.resquest.admin.AssetExceptionRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.service.admin.exception.AssetExceptionService;
import com.hyjf.am.vo.admin.AssetExceptionCustomizeVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version AssetExceptionController, v0.1 2018/9/28 19:09
 */
@Api(value = "异常标的处理")
@RestController
@RequestMapping("/am-admin/asset_exception")
public class AssetExceptionController extends BaseController {

    @Autowired
    AssetExceptionService assetExceptionService;

    /**
     * @Author: liushouyi
     * @Desc :查询总件数
     */
    @ApiOperation(value = "查询保证金总件数")
    @PostMapping("/select_asset_exception_count")
    public IntegerResponse selectAssetExceptionCount(@RequestBody AssetExceptionRequest AssetExceptionRequest) {
        return new IntegerResponse(assetExceptionService.selectAssetExceptionCount(AssetExceptionRequest));
    }

    /**
     * @Author: liushouyi
     * @Desc :查询异常标的列表
     */
    @ApiOperation(value = "保证金查询列表")
    @PostMapping("/select_asset_exception_list")
    public AssetExceptionCustomizeResponse selectAssetExceptionRecordList(@RequestBody AssetExceptionRequest assetExceptionRequest) {
        AssetExceptionCustomizeResponse response = new AssetExceptionCustomizeResponse();
        Integer recordTotal = assetExceptionService.selectAssetExceptionCount(assetExceptionRequest);
        Paginator paginator = new Paginator(assetExceptionRequest.getCurrPage(), recordTotal, assetExceptionRequest.getPageSize());
        assetExceptionRequest.setLimitStart(paginator.getOffset());
        assetExceptionRequest.setLimitEnd(paginator.getLimit());

        List<AssetExceptionCustomizeVO> AssetExceptionCustomizeVOList = assetExceptionService.selectAssetExceptionRecordList(assetExceptionRequest);
        if (null != AssetExceptionCustomizeVOList && AssetExceptionCustomizeVOList.size() > 0) {
            List<AssetExceptionCustomizeVO> bankAccountManageCustomizeVOS = CommonUtils.convertBeanList(AssetExceptionCustomizeVOList, AssetExceptionCustomizeVO.class);
            response.setResultList(bankAccountManageCustomizeVOS);
            response.setRtn(Response.SUCCESS);
        }
        return response;
    }

    /**
     * @Author: liushouyi
     * @Desc 插入异常标的并更新保证金
     */
    @ApiOperation(value = "插入异常标的并更新保证金")
    @PostMapping("/insert_asset_exception")
    public BooleanResponse insertAssetException(@RequestBody AssetExceptionRequest assetExceptionRequest) {
        BooleanResponse response = new BooleanResponse();
        response.setResultBoolean(assetExceptionService.insertAssetException(assetExceptionRequest));
        response.setRtn(Response.SUCCESS);
        return response;
    }

    /**
     * @Author: liushouyi
     * @Desc 项目编号是否存在
     */
    @ApiOperation(value = "项目编号是否存在")
    @GetMapping("/is_exists_borrow/{borrowNid}")
    public StringResponse isExistsBorrow(@PathVariable String borrowNid) {
        StringResponse response = new StringResponse();
        response.setResultStr(assetExceptionService.isExistsBorrow(borrowNid));
        response.setRtn(Response.SUCCESS);
        return response;
    }

    /**
     * @Author: liushouyi
     * @Desc 删除异常标的并更新保证金
     */
    @ApiOperation(value = "删除异常标的并更新保证金")
    @PostMapping("/delete_asset_exception")
    public BooleanResponse deleteAssetException(@RequestBody AssetExceptionRequest assetExceptionRequest) {
        BooleanResponse response = new BooleanResponse();
        response.setResultBoolean(assetExceptionService.deleteAssetException(assetExceptionRequest));
        response.setRtn(Response.SUCCESS);
        return response;
    }

    /**
     * @Author: liushouyi
     * @Desc 修改异常标的并更新保证金
     */
    @ApiOperation(value = "修改异常标的并更新保证金")
    @PostMapping("/update_asset_exception")
    public BooleanResponse updateAssetException(@RequestBody AssetExceptionRequest assetExceptionRequest) {
        BooleanResponse response = new BooleanResponse();
        response.setResultBoolean(assetExceptionService.updateAssetException(assetExceptionRequest));
        response.setRtn(Response.SUCCESS);
        return response;
    }

}
