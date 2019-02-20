/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.config;

import com.hyjf.admin.beans.response.BailConfigLogResponseBean;
import com.hyjf.admin.beans.vo.DropDownVO;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.BailConfigLogService;
import com.hyjf.admin.utils.ConvertUtils;
import com.hyjf.admin.utils.Page;
import com.hyjf.am.resquest.admin.BailConfigLogRequest;
import com.hyjf.am.vo.admin.BailConfigLogCustomizeVO;
import com.hyjf.common.cache.CacheUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author PC-LIUSHOUYI
 * @version BailConfigLogContorller, v0.1 2018/9/28 9:34
 */
@Api(value = "配置中心-保证金配置日志", tags = "配置中心-保证金配置日志")
@RestController
@RequestMapping("/hyjf-admin/bail_config_log")
public class BailConfigLogContorller extends BaseController {

    public static final String PERMISSIONS = "bailConfigLog";

    @Autowired
    BailConfigLogService bailConfigLogService;

    /**
     * 银行账户管理页面
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "保证金配置列表查询", notes = "保证金配置列表查询")
    @PostMapping(value = "/search")
    @ResponseBody
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<BailConfigLogResponseBean> search(@RequestBody BailConfigLogRequest request) {
        BailConfigLogResponseBean responseBean = new BailConfigLogResponseBean();
        // 资产来源
        List<DropDownVO> hjhInstConfigList = bailConfigLogService.selectHjhInstConfigList();
        responseBean.setInstNameList(hjhInstConfigList);
        // 修改字段
        Map<String, String> modifyColumnProperty = CacheUtil.getParamNameMap("BAIL_MODIFY_COLUMN");
        List<DropDownVO> modifyColumnList = ConvertUtils.convertParamMapToDropDown(modifyColumnProperty);
        responseBean.setModifyColumnList(modifyColumnList);

        Integer count = bailConfigLogService.selectBailConfigLogCount(request);
        count = (count == null) ? 0 : count;
        responseBean.setTotal(count);
        //分页参数
        Page page = Page.initPage(request.getCurrPage(), request.getPageSize());
        page.setTotal(count);
        request.setLimitStart(page.getOffset());
        request.setLimitEnd(page.getLimit());
        if (count > 0) {
            List<BailConfigLogCustomizeVO> bailConfigLogCustomizeVOList = bailConfigLogService.selectBailConfigLogList(request);
            if (bailConfigLogCustomizeVOList == null || bailConfigLogCustomizeVOList.size() == 0) {
                return new AdminResult<>(FAIL, FAIL_DESC);
            }
            responseBean.setRecordList(bailConfigLogCustomizeVOList);
        }
        return new AdminResult<>(responseBean);
    }
}
