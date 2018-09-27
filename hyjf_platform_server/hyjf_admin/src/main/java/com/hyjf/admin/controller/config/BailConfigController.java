/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.config;

import com.hyjf.admin.beans.response.BailConfigResponseBean;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.BailConfigService;
import com.hyjf.admin.utils.Page;
import com.hyjf.am.resquest.admin.BailConfigRequest;
import com.hyjf.am.vo.admin.BailConfigCustomizeVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version BailConfigController, v0.1 2018/9/26 15:30
 */
@Api(value = "配置中心-保证金配置", tags = "配置中心-保证金配置")
@RestController
@RequestMapping("/hyjf-admin/bailConfig")
public class BailConfigController extends BaseController {

    @Autowired
    BailConfigService bailConfigService;

    /**
     * 银行账户管理页面
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "保证金配置", notes = "保证金配置")
    @PostMapping(value = "/search")
    @ResponseBody
    public AdminResult<BailConfigResponseBean> search(@RequestBody BailConfigRequest request) {

        BailConfigResponseBean bean = new BailConfigResponseBean();

        Integer count = bailConfigService.selectBailConfigCount(request);
        count = (count == null) ? 0 : count;
        bean.setTotal(count);
        //分页参数
        Page page = Page.initPage(request.getCurrPage(), request.getPageSize());
        page.setTotal(count);
        request.setLimitStart(page.getOffset());
        request.setLimitEnd(page.getLimit());
        if (count > 0) {
            List<BailConfigCustomizeVO> bailConfigCustomizeVOList = bailConfigService.selectRecordList(request);
            if (bailConfigCustomizeVOList == null || bailConfigCustomizeVOList.size() == 0) {
                return new AdminResult<>(FAIL, FAIL_DESC);
            }
            bean.setRecordList(bailConfigCustomizeVOList);
        }
        return new AdminResult(bean);
    }
}
