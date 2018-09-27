/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.config;

import com.hyjf.admin.beans.request.BailConfigRequestBean;
import com.hyjf.admin.beans.response.BailConfigResponseBean;
import com.hyjf.admin.beans.vo.DropDownVO;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.BailConfigService;
import com.hyjf.admin.utils.ConvertUtils;
import com.hyjf.admin.utils.Page;
import com.hyjf.am.resquest.admin.BailConfigAddRequest;
import com.hyjf.am.resquest.admin.BailConfigRequest;
import com.hyjf.am.vo.admin.BailConfigCustomizeVO;
import com.hyjf.am.vo.admin.BailConfigInfoCustomizeVO;
import com.hyjf.am.vo.config.AdminSystemVO;
import com.hyjf.am.vo.user.HjhInstConfigVO;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.util.GetterUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
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
    @ApiOperation(value = "保证金配置列表查询", notes = "保证金配置列表查询")
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
     * 画面迁移
     *
     * @param idStr
     * @return
     */
    @ApiOperation(value = "保证金配置详情", notes = "保证金配置详情")
    @GetMapping("/info/{idStr}")
    @ResponseBody
    public AdminResult<BailConfigInfoCustomizeVO> info(@PathVariable String idStr) {
        // 用户ID
        Integer id = GetterUtil.getInteger(idStr);
        if (null == id || 0 == id) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        BailConfigInfoCustomizeVO bailConfigInfoCustomizeVO = bailConfigService.selectBailConfigById(id);
        if (null == bailConfigInfoCustomizeVO) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        return new AdminResult<>(bailConfigInfoCustomizeVO);
    }

    /**
     * 未配置保证金的机构编号下拉框
     *
     * @return
     */
    @ApiOperation(value = "未配置保证金的机构编号下拉框", notes = "未配置保证金的机构编号下拉框")
    @GetMapping("/select_noused_inst_config_list")
    public AdminResult<List<DropDownVO>> selectNoUsedInstConfigList() {
        List<HjhInstConfigVO> hjhInstConfigVOList = bailConfigService.selectNoUsedInstConfigList();
        if (null == hjhInstConfigVOList || hjhInstConfigVOList.size() <= 0) {
            return new AdminResult<>(FAIL, "未查询到未配置保证金的机构");
        }
        List<DropDownVO> dropDownVOList = ConvertUtils.convertListToDropDown(hjhInstConfigVOList, "instCode", "instName");
        AdminResult<List<DropDownVO>> result = new AdminResult<List<DropDownVO>>();
        result.setData(dropDownVOList);
        return result;
    }

    /**
     * 添加保证金配置
     *
     * @param request
     * @param requestBean
     * @return
     */
    @ApiOperation(value = "添加保证金配置", notes = "添加保证金配置")
    @PostMapping("/insert_bail_config")
    public AdminResult insertBailConfig(HttpServletRequest request, @RequestBody BailConfigRequestBean requestBean) {

        BailConfigAddRequest bailConfigAddRequest = new BailConfigAddRequest();
        BeanUtils.copyProperties(requestBean, bailConfigAddRequest);
        // 获取当前添加机构的编号
        String instCode = bailConfigAddRequest.getInstCode();

        // 获取操作人id
        AdminSystemVO adminSystemVO = this.getUser(request);
        String loginUserId = adminSystemVO.getId();
        if (StringUtils.isNotBlank(loginUserId)) {
            bailConfigAddRequest.setCreateUserId(Integer.parseInt(loginUserId));
        }
        bailConfigAddRequest.setCreateTime(new Date());

        // 发标额度上限
        bailConfigAddRequest.setPushMarkLine(bailConfigAddRequest.getBailTatol().multiply(new BigDecimal("100")).divide(new BigDecimal(bailConfigAddRequest.getBailRate()), 2, BigDecimal.ROUND_DOWN));
        // 发标额度余额（默认为上限）
        bailConfigAddRequest.setRemainMarkLine(bailConfigAddRequest.getPushMarkLine());

        // 查询周期内发标已发额度
        BigDecimal sendedAccountByCycBD = BigDecimal.ZERO;
        String sendedAccountByCyc = this.bailConfigService.selectSendedAccountByCyc(bailConfigAddRequest);
        if (StringUtils.isNotBlank(sendedAccountByCyc)) {
            sendedAccountByCycBD = new BigDecimal(sendedAccountByCyc);
        }
        bailConfigAddRequest.setCycLoanTotal(sendedAccountByCycBD);

        boolean isInset = bailConfigService.insertBailConfig(bailConfigAddRequest);
        if (isInset) {
            // 根据还款方式更新保证金还款方式验证的有效性
            isInset = this.bailConfigService.updateBailInfoDelFlg(instCode);
        } else {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        // 更新info表失败
        if (!isInset) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        // 日推标上限记录到redis
        RedisUtils.set(RedisConstants.DAY_MARK_LINE + instCode, bailConfigAddRequest.getDayMarkLine().toString());
        // 月推标上限记录到redis
        RedisUtils.set(RedisConstants.MONTH_MARK_LINE + instCode, bailConfigAddRequest.getMonthMarkLine().toString());
        // 日额度累计redis(日累计不存再的情况初始化)
        if (!RedisUtils.exists(RedisConstants.DAY_MARK_ACCUMULATE + instCode)) {
            RedisUtils.set(RedisConstants.DAY_MARK_ACCUMULATE + instCode, "0");
        }
        return new AdminResult<>();
    }

    /**
     * 更新保证金配置
     *
     * @param request
     * @param requestBean
     * @return
     */
    @ApiOperation(value = "更新保证金配置", notes = "更新保证金配置")
    @PostMapping("/update_bail_config")
    public AdminResult updateBailConfig(HttpServletRequest request, @RequestBody BailConfigRequestBean requestBean) {

        BailConfigAddRequest bailConfigAddRequest = new BailConfigAddRequest();
        BeanUtils.copyProperties(requestBean, bailConfigAddRequest);
        // 获取当前添加机构的编号
        String instCode = bailConfigAddRequest.getInstCode();

        // 获取操作人id
        AdminSystemVO adminSystemVO = this.getUser(request);
        String loginUserId = adminSystemVO.getId();
        if (StringUtils.isNotBlank(loginUserId)) {
            bailConfigAddRequest.setUpdateUserId(Integer.parseInt(loginUserId));
        }
        bailConfigAddRequest.setUpdateTime(new Date());

        boolean isInset = bailConfigService.updateBailConfig(bailConfigAddRequest);
        if (isInset) {
            // 根据还款方式更新保证金还款方式验证的有效性
            isInset = this.bailConfigService.updateBailInfoDelFlg(instCode);
        } else {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        // 更新info表失败
        if (!isInset) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        // 日推标上限记录到redis
        RedisUtils.set(RedisConstants.DAY_MARK_LINE + instCode, bailConfigAddRequest.getDayMarkLine().toString());
        // 月推标上限记录到redis
        RedisUtils.set(RedisConstants.MONTH_MARK_LINE + instCode, bailConfigAddRequest.getMonthMarkLine().toString());
        return new AdminResult<>();
    }
}
