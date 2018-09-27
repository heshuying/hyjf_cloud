/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.config;

import com.hyjf.admin.beans.response.BailConfigResponseBean;
import com.hyjf.admin.beans.vo.DropDownVO;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.controller.finance.bankaccountmanage.BankAccountManageController;
import com.hyjf.admin.service.BailConfigService;
import com.hyjf.admin.utils.ConvertUtils;
import com.hyjf.admin.utils.Page;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.trade.FddTempletResponse;
import com.hyjf.am.response.user.HjhInstConfigResponse;
import com.hyjf.am.resquest.admin.BailConfigRequest;
import com.hyjf.am.vo.admin.BailConfigCustomizeVO;
import com.hyjf.am.vo.admin.BailConfigInfoCustomizeVO;
import com.hyjf.am.vo.trade.FddTempletVO;
import com.hyjf.am.vo.user.HjhInstConfigVO;
import com.hyjf.common.util.GetterUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    /**
     * 画面迁移(含有id更新，不含有id添加)
     *
     * @param idStr
     * @return
     */
    @ApiOperation(value = "保证金配置-画面迁移", notes = "保证金配置-画面迁移")
    @GetMapping("/info/{idStr}")
    @ResponseBody
    public AdminResult<BailConfigInfoCustomizeVO> info(@PathVariable String idStr) {
        // 用户ID
        Integer id = GetterUtil.getInteger(idStr);
        if (null == id || 0 ==id) {
            // 不含id返回下拉框列表数据
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        BailConfigInfoCustomizeVO bailConfigInfoCustomizeVO = bailConfigService.selectBailConfigById(id);
        if(null == bailConfigInfoCustomizeVO) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        return new AdminResult<>(bailConfigInfoCustomizeVO);
    }
    /**
     * 未配置保证金的机构编号下拉框
     * @return
     */
    @ApiOperation(value = "未配置保证金的机构编号下拉框", notes = "未配置保证金的机构编号下拉框")
    @GetMapping("/select_noused_inst_config_list")
    public AdminResult<List<DropDownVO>> selectNoUsedInstConfigList(){
        List<HjhInstConfigVO> hjhInstConfigVOList = bailConfigService.selectNoUsedInstConfigList();
        if (null == hjhInstConfigVOList || hjhInstConfigVOList.size() <= 0) {
            return new AdminResult<>(FAIL, "未查询到未配置保证金的机构");
        }
        List<DropDownVO> dropDownVOList = ConvertUtils.convertListToDropDown(hjhInstConfigVOList,"instCode","instName");
        AdminResult<List<DropDownVO>> result=new AdminResult<List<DropDownVO>> ();
        result.setData(dropDownVOList);
        return result ;
    }
}
