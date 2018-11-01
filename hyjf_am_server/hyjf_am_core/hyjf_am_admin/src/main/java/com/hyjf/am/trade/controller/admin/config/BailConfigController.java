/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.admin.config;

import com.hyjf.am.response.BooleanResponse;
import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.StringResponse;
import com.hyjf.am.response.admin.BailConfigCustomizeResponse;
import com.hyjf.am.response.admin.BailConfigInfoCustomizeResponse;
import com.hyjf.am.response.user.HjhInstConfigResponse;
import com.hyjf.am.resquest.admin.BailConfigAddRequest;
import com.hyjf.am.resquest.admin.BailConfigRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.auto.HjhInstConfig;
import com.hyjf.am.trade.service.admin.config.BailConfigService;
import com.hyjf.am.vo.admin.BailConfigCustomizeVO;
import com.hyjf.am.vo.admin.BailConfigInfoCustomizeVO;
import com.hyjf.am.vo.user.HjhInstConfigVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.GetterUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version BailConfigController, v0.1 2018/9/26 17:18
 */
@Api(value = "保证金配置")
@RestController
@RequestMapping("/am-trade/bail_config")
public class BailConfigController extends BaseController {

    @Autowired
    BailConfigService bailConfigService;

    /**
     * @Author: liushouyi
     * @Desc :查询总件数
     */
    @ApiOperation(value = "查询保证金总件数")
    @PostMapping("/select_bail_config_count")
    public IntegerResponse selectBailConfigCount(@RequestBody BailConfigRequest bailConfigRequest) {
        return new IntegerResponse(bailConfigService.selectBailConfigCount(bailConfigRequest));
    }

    /**
     * @Author: liushouyi
     * @Desc :查询列表数据
     */
    @ApiOperation(value = "保证金查询列表")
    @PostMapping("/select_bail_config_record_list")
    public BailConfigCustomizeResponse selectBailConfigRecordList(@RequestBody BailConfigRequest bailConfigRequest) {
        BailConfigCustomizeResponse response = new BailConfigCustomizeResponse();
        Integer recordTotal = bailConfigService.selectBailConfigCount(bailConfigRequest);
        Paginator paginator = new Paginator(bailConfigRequest.getCurrPage(), recordTotal, bailConfigRequest.getPageSize());
        bailConfigRequest.setLimitStart(paginator.getOffset());
        bailConfigRequest.setLimitEnd(paginator.getLimit());

        List<BailConfigCustomizeVO> bailConfigCustomizeVOList = bailConfigService.selectBailConfigRecordList(bailConfigRequest);
        if (null != bailConfigCustomizeVOList && bailConfigCustomizeVOList.size() > 0) {
            List<BailConfigCustomizeVO> bankAccountManageCustomizeVOS = CommonUtils.convertBeanList(bailConfigCustomizeVOList, BailConfigCustomizeVO.class);
            response.setResultList(bankAccountManageCustomizeVOS);
            response.setRtn(Response.SUCCESS);
        }
        return response;
    }

    /**
     * @Author: liushouyi
     * @Desc 更新机构可用还款方式并返回最新的保证金配置
     */
    @ApiOperation(value = "更新机构可用还款方式并返回最新的保证金配置")
    @GetMapping("/update_select_bail_config_by_id/{id}")
    public BailConfigInfoCustomizeResponse updateSelectBailConfigById(@PathVariable String id) {
        BailConfigInfoCustomizeResponse response = new BailConfigInfoCustomizeResponse();
        BailConfigInfoCustomizeVO bailConfigInfoCustomizeVO = bailConfigService.updateBailInfoDelFlgById(GetterUtil.getInteger(id));
        if (null != bailConfigInfoCustomizeVO) {
            response.setResult(bailConfigInfoCustomizeVO);
            response.setRtn(Response.SUCCESS);
        }
        return response;
    }

    /**
     * @Author: liushouyi
     * @Desc 未配置保证金的机构编号
     */
    @ApiOperation(value = "未配置保证金的机构编号")
    @GetMapping("/select_noused_inst_config_list")
    public HjhInstConfigResponse selectNoUsedInstConfigList() {
        HjhInstConfigResponse response = new HjhInstConfigResponse();
        List<HjhInstConfig> hjhInstConfigList = bailConfigService.selectNoUsedInstConfigList();
        if (null != hjhInstConfigList && hjhInstConfigList.size() > 0) {
            List<HjhInstConfigVO> hjhInstConfigVOList = CommonUtils.convertBeanList(hjhInstConfigList, HjhInstConfigVO.class);
            response.setResultList(hjhInstConfigVOList);
            response.setRtn(Response.SUCCESS);
        }
        return response;
    }

    /**
     * @Author: liushouyi
     * @Desc 周期内发标已发额度
     */
    @ApiOperation(value = "周期内发标已发额度")
    @PostMapping("/select_sended_account_by_cyc")
    public StringResponse selectSendedAccountByCyc(@RequestBody BailConfigAddRequest bailConfigAddRequest) {
        StringResponse result = new StringResponse();
        String msg = bailConfigService.selectSendedAccountByCyc(bailConfigAddRequest);
        result.setResultStr(msg);
        result.setRtn(Response.SUCCESS);
        return result;
    }

    /**
     * @Author: liushouyi
     * @Desc 根据该机构可用还款方式更新可用授信方式
     */
    @ApiOperation(value = "根据该机构可用还款方式更新可用授信方式")
    @GetMapping("/update_bail_info_delflg/{instCode}")
    public BooleanResponse updateBailInfoDelFlg(@PathVariable String instCode) {
        BooleanResponse response = new BooleanResponse();
        response.setResultBoolean(bailConfigService.updateBailInfoDelFlg(instCode));
        response.setRtn(Response.SUCCESS);
        return response;
    }

    /**
     * @Author: liushouyi
     * @Desc 添加保证金配置
     */
    @ApiOperation(value = "添加保证金配置")
    @PostMapping("/insert_bail_config")
    public BooleanResponse insertBailConfig(@RequestBody BailConfigAddRequest bailConfigAddRequest) {
        BooleanResponse response = new BooleanResponse();
        response.setResultBoolean(bailConfigService.insertBailConfig(bailConfigAddRequest));
        response.setRtn(Response.SUCCESS);
        return response;
    }

    /**
     * @Author: liushouyi
     * @Desc 更新保证金配置
     */
    @ApiOperation(value = "更新保证金配置")
    @PostMapping("/update_bail_config")
    public BooleanResponse updateBailConfig(@RequestBody BailConfigAddRequest bailConfigAddRequest) {
        BooleanResponse response = new BooleanResponse();
        response.setResultBoolean(bailConfigService.updateBailConfig(bailConfigAddRequest));
        response.setRtn(Response.SUCCESS);
        return response;
    }

    /**
     * @Author: liushouyi
     * @Desc 删除保证金配置
     */
    @ApiOperation(value = "删除保证金配置")
    @PostMapping("/delete_bail_config")
    public BooleanResponse deleteBailConfig(@RequestBody BailConfigAddRequest bailConfigAddRequest) {
        BooleanResponse response = new BooleanResponse();
        response.setResultBoolean(bailConfigService.deleteBailConfig(bailConfigAddRequest));
        response.setRtn(Response.SUCCESS);
        return response;
    }

    /**
     * @Author: liushouyi
     * @Desc 获取当前机构可用还款方式
     */
    @ApiOperation(value = "获取当前机构可用还款方式")
    @GetMapping("/select_repay_method/{instCode}")
    public Response selectRepayMethod(@PathVariable String instCode) {
        Response response = new Response();
        response.setResultList(bailConfigService.selectRepayMethod(instCode));
        response.setRtn(Response.SUCCESS);
        return response;
    }
}
