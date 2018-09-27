/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.admin.config;

import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.response.admin.BailConfigCustomizeResponse;
import com.hyjf.am.resquest.admin.BailConfigRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.service.admin.config.BailConfigService;
import com.hyjf.am.vo.admin.BailConfigCustomizeVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @ApiOperation(value = "银行账户管理查询列表")
    @PostMapping("/select_bail_config_record_list")
    public BailConfigCustomizeResponse selectBailConfigRecordList(@RequestBody BailConfigRequest bailConfigRequest) {
        BailConfigCustomizeResponse response = new BailConfigCustomizeResponse();
        Integer recordTotal = bailConfigService.selectBailConfigCount(bailConfigRequest);
        Paginator paginator = new Paginator(bailConfigRequest.getCurrPage(), recordTotal,bailConfigRequest.getPageSize());
        bailConfigRequest.setLimitStart(paginator.getOffset());
        bailConfigRequest.setLimitEnd(paginator.getLimit());

        List<BailConfigCustomizeVO> bailConfigCustomizeVOList = bailConfigService.selectBailConfigRecordList(bailConfigRequest);
        if (null != bailConfigCustomizeVOList && bailConfigCustomizeVOList.size() > 0) {
            List<BailConfigCustomizeVO> bankAccountManageCustomizeVOS = CommonUtils.convertBeanList(bailConfigCustomizeVOList, BailConfigCustomizeVO.class);
            response.setResultList(bankAccountManageCustomizeVOS);
        }
        return response;
    }

}
