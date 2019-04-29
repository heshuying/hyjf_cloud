package com.hyjf.cs.trade.controller.app.user.myproject;

import com.hyjf.am.resquest.trade.AssetManageBeanRequest;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.assetmanage.*;
import com.hyjf.common.constants.CommonConstant;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.FormatRateUtil;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.trade.config.SystemConfig;
import com.hyjf.cs.trade.controller.BaseTradeController;
import com.hyjf.cs.trade.service.myproject.AppMyProjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author pangchengchao
 * @version WechatMyAssetController, v0.1 2018/7/24 12:02
 */

@Api(value = "app端-用户我的散标接口",tags = "app端-用户我的散标接口")
@Controller
@RequestMapping("/hyjf-app/user/invest")
public class AppMyProjectController extends BaseTradeController {

    @Autowired
    private AppMyProjectService appMyProjectService;
    @Autowired
    SystemConfig systemConfig;
    /**
     * 微信端获取首页散标列表
     * @date 2018/7/2 16:27
     */
    @ApiOperation(value = "app端-:获取我的散标信息", notes = "app端-:获取我的散标信息")
    @PostMapping(value = "/getMyProject", produces = "application/json; charset=utf-8")
    @ResponseBody
    public MyProjectResponse queryScatteredProject( HttpServletRequest request,
                                                    @RequestHeader(value = "token", required = false) String token,
                                                    @RequestHeader(value = "userId", required = false) Integer userId) {

        MyProjectResponse response = new MyProjectResponse();
        response.setRequest("/hyjf-app/user/invest/getMyProject");
        // 状态：1为当前持有，2为已回款，3为转让记录
        String type = request.getParameter("type");
        // 唯一标识
        String sign = request.getParameter("sign");
        String version = request.getParameter("version");

        // 检查参数正确性
        if (Validator.isNull(token) || Validator.isNull(type) || !Arrays.asList("1", "2", "3").contains(type)) {
            response.setStatus(CustomConstants.APP_STATUS_FAIL);
            response.setStatusDesc("参数非法");
            return response;
        }
        // 构建查询条件
        AssetManageBeanRequest  params = buildQueryParameter(request);
        params.setUserId(userId);
        // 构建分页查询条件
        //this.buildQueryParameter(request);
        // 这2个不用了，在返回的时候拼接
        params.setHost("");
        params.setSign(sign);
        AccountVO account = appMyProjectService.getAccountByUserId(userId);
        // 散标待收总额
        if (account != null) {
            response.setMoney(CommonUtils.formatAmount(version, account.getBankAwait()));
        }

        int count = 0;
        switch (type) {
            // 当前持有
            case "1":
                // 查询我的出借中债权总数
                count = this.appMyProjectService.selectCurrentHoldObligatoryRightListTotal(params);
                if (count > 0) {
                    // 获取用户当前持有债权列表
                    List<CurrentHoldObligatoryRightListCustomizeVO> recordList =
                            appMyProjectService.selectAppCurrentHoldObligatoryRightList(params);
                    response.setProjectList(
                            convertAppInvestListCustomizeToMyProjectVo(recordList, request, userId));
                } else {
                    response.setProjectList(new ArrayList<MyProjectVo>());
                }
                break;

            case "2":
                // 查询我的回款总数
                count = appMyProjectService.countAlreadyRepayListRecordTotal(params);
                if (count > 0) {
                    List<AppAlreadyRepayListCustomizeVO> appAlreadyRepayListCustomizes = appMyProjectService
                            .selectAppAlreadyRepayList(params);
                    response.setProjectList(
                            convertappAlreadyRepayListToMyProjectVo(appAlreadyRepayListCustomizes, request));
                } else {
                    response.setProjectList(new ArrayList<MyProjectVo>());
                }
                break;

            case "3":
                // 查询我的债转记录总数
                params.setCountStatus("2");
                count = appMyProjectService.countCreditRecord(params);
                if (count > 0) {
                    List<AppTenderCreditRecordListCustomizeVO> project = appMyProjectService
                            .searchAppCreditRecordList(params);
                    response.setProjectList(converCreditToMyProject(project, request));
                } else {
                    response.setProjectList(new ArrayList<MyProjectVo>());
                }
                break;
            default:
                logger.error("传入参数type错误");
                response.setStatus(CustomConstants.APP_STATUS_FAIL);
                response.setStatusDesc(CustomConstants.APP_STATUS_DESC_FAIL);
        }
        response.setProjectTotal(count);
        return response;
    }

    /**
     * 适应客户端数据返回 - 出借中
     *
     * @return
     */
    private List<MyProjectVo> convertAppInvestListCustomizeToMyProjectVo(
            List<CurrentHoldObligatoryRightListCustomizeVO> customizes, HttpServletRequest request, Integer userId) {
        List<MyProjectVo> vos = new ArrayList<>();
        MyProjectVo vo = null;
        if (CollectionUtils.isEmpty(customizes)) {
            return vos;
        }
        String investStatusDesc = "";
        for (CurrentHoldObligatoryRightListCustomizeVO entity : customizes) {
            vo = new MyProjectVo();
            BeanUtils.copyProperties(entity, vo);
            vo.setBorrowNid(entity.getBorrowNid());

            String couponType = entity.getCouponType();
            switch (couponType) {
                case "1":
                    vo.setLabel("体验金");
                    investStatusDesc = "未回款";
                    break;
                case "2":
                    vo.setLabel("加息券");
                    investStatusDesc = "未回款";
                    break;
                case "3":
                    vo.setLabel("代金券");
                    investStatusDesc = "未回款";
                    break;
                default:
                    if("2".equals(vo.getType())){
                        vo.setLabel("承接债转");
                    } else {
                        vo.setLabel("");
                    }
                    investStatusDesc = "现金出借".equals(entity.getData()) ? "还款中" : entity.getData();
            }
            // 加息收益
            if("4".equals(entity.getType())){
                vo.setLabel(entity.getData());
                investStatusDesc = "未回款";
                vo.setCouponType("4");
            }
            vo.setBorrowName(entity.getBorrowNid());
            vo.setBorrowTheFirst(CommonUtils.formatAmount(entity.getCapital()) + "元");
            vo.setBorrowTheFirstDesc("出借金额");
            vo.setBorrowTheSecond(entity.getBorrowPeriod());
            vo.setBorrowTheSecondDesc("项目期限");
            vo.setBorrowTheThird(entity.getAddtime());
            vo.setBorrowTheThirdDesc("出借时间");
            vo.setType("1");
            // 出借订单号
            String assignNid = "";
            String nid = entity.getNid();
            if (!StringUtils.isBlank(entity.getCreditTenderNid())) {
                assignNid = entity.getNid();
                logger.info("债转编号: {}", assignNid);
            }

            String borrowUrl = this.concatInvestDetailUrl(entity.getBorrowNid(),  nid,
                    request.getParameter("type"), entity.getCouponType(), assignNid, investStatusDesc);
            //vo.setBorrowUrl(CommonUtils.concatReturnUrl(request, borrowUrl));
            // 如果是产品加息
            if ("4".equals(entity.getType())) {
                borrowUrl += "&isIncrease=1";
            }
            vo.setBorrowUrl(borrowUrl);

            // 判断债权能否债转
            if (canDoTransfer(entity.getBorrowNid(), nid, userId)) {
                vo.setIsDisplay("1");
                String url = super.getFrontHost(systemConfig,CommonConstant.CLIENT_ANDROID)+"/user/borrow/transfer/setting?borrowId=" + entity.getBorrowNid()
                        + "&tenderId=" + nid;
                vo.setUrl(url);
            } else {
                vo.setIsDisplay("0");
            }
            CommonUtils.convertNullToEmptyString(vo);
            vo.setLabel(FormatRateUtil.formatBorrowApr(vo.getLabel()));
            vos.add(vo);
        }
        return vos;
    }

    /**
     * 适应客户端数据返回 - 已回款
     *
     * @param appAlreadyRepayListCustomizes
     * @return
     */
    private List<MyProjectVo> convertappAlreadyRepayListToMyProjectVo(
            List<AppAlreadyRepayListCustomizeVO> appAlreadyRepayListCustomizes, HttpServletRequest request) {
        List<MyProjectVo> vos = new ArrayList<>();
        MyProjectVo vo = null;
        if (CollectionUtils.isEmpty(appAlreadyRepayListCustomizes)) {
            return vos;
        }
        for (AppAlreadyRepayListCustomizeVO entity : appAlreadyRepayListCustomizes) {
            vo = new MyProjectVo();
            BeanUtils.copyProperties(entity, vo);
            vo.setBorrowTheFirst(entity.getAccount() + "元");
            vo.setBorrowTheFirstDesc("出借金额");
            vo.setBorrowTheSecond(entity.getPeriod());
            vo.setBorrowTheSecondDesc("项目期限");
            vo.setBorrowTheThird(GetDate.times10toStrYYYYMMDD(Integer.valueOf(entity.getRecoverTime())));
            vo.setBorrowTheThirdDesc("回款时间");
            vo.setType("2");
            vo.setIsDisplay("0");

            String assignNid = "";
            if ("HZR".equals(entity.getProjectType())) {
                assignNid = entity.getOrderId();
                logger.info("已回款状态，债转编号:{}", assignNid);
            }

            String borrowUrl = this.concatInvestDetailUrl(entity.getBorrowNid(), entity.getOrderId(),
                    request.getParameter("type"), entity.getCouponType(), assignNid, "已还款");
            //vo.setBorrowUrl(CommonUtils.concatReturnUrl(request, borrowUrl));
            // 产品加息
            if ("3".equals(entity.getInvestType())) {
                borrowUrl += "&isIncrease=1&isCalendar=1";
                vo.setCouponType("4");
            }
            vo.setLabel(FormatRateUtil.formatBorrowApr(vo.getLabel()));
            vo.setBorrowUrl(borrowUrl);
            CommonUtils.convertNullToEmptyString(vo);
            vos.add(vo);
        }
        return vos;
    }

    /**
     * 适应客户端数据返回 - 债转
     *
     * @param projectList
     * @return
     */
    private List<MyProjectVo> converCreditToMyProject(List<AppTenderCreditRecordListCustomizeVO> projectList,
                                                      HttpServletRequest request) {
        List<MyProjectVo> vos = new ArrayList<>();
        MyProjectVo vo = null;
        if (CollectionUtils.isEmpty(projectList)) {
            return vos;
        }
        for (AppTenderCreditRecordListCustomizeVO customize : projectList) {
            vo = new MyProjectVo();
            vo.setBorrowTheFirst(customize.getCreditCapital() + "元");
            vo.setBorrowTheFirstDesc("原始本金");
            vo.setBorrowTheSecond(customize.getCreditCapitalAssigned() + "元");
            vo.setBorrowTheSecondDesc("已转让金额");
            vo.setBorrowTheThird(customize.getCreditStatusDesc());
            vo.setStatusName(customize.getCreditStatusDesc());
            vo.setType("3");
            vo.setIsDisplay("0");
            vo.setBorrowName(customize.getCreditNid());

            String borrowUrl = super.getFrontHost(systemConfig,CommonConstant.CLIENT_ANDROID)+"/user/transfer/" + customize.getRealCreditNid();
            //vo.setBorrowUrl(CommonUtils.concatReturnUrl(request, borrowUrl));
            vo.setBorrowUrl(borrowUrl);
            CommonUtils.convertNullToEmptyString(vo);
            vos.add(vo);
        }
        return vos;
    }

    /**
     * 拼接出借详情url
     * @param borrowNid
     * @param orderId
     * @param type
     * @param couponType
     * @param assignNid
     * @param investStatusDesc  出借状态
     * @return
     */
    private String concatInvestDetailUrl(String borrowNid, String orderId, String type, String couponType, String assignNid, String investStatusDesc) {
        if (StringUtils.isEmpty(couponType)) {
            couponType = "0";
        }
        String url = super.getFrontHost(systemConfig,CommonConstant.CLIENT_ANDROID)+"/user/borrow"  + "/" + borrowNid + "?orderId=" + orderId + "&type=" + type
                + "&couponType=" + couponType + "&assignNid=" + assignNid +"&investStatusDesc=" + investStatusDesc;
        return url;
    }
    /**
     * 判断用户某一出借是否满足债转条件
     *
     * @param borrowNid
     * @param tenderNid
     * @param userId
     * @return
     */
    private boolean canDoTransfer(String borrowNid, String tenderNid, Integer userId) {

        if(!appMyProjectService.isAllowChannelAttorn(userId)){
            // 判断用户所处渠道不允许债转
            return false;
        }
        AssetManageBeanRequest  params=new AssetManageBeanRequest();
        params.setBorrowNid(borrowNid);
        params.setTenderNid(tenderNid);
        params.setUserId(userId);
        params.setNowTime((int)(System.currentTimeMillis() / 1000));
        params.setLimitStart(0);
        params.setLimitEnd(1);
        params.setNowDate(GetDate.getNowTime());
        Integer count = appMyProjectService.selectTenderToCreditListCount(params);
        if (count>0) {
            return true;
        }
        return false;
    }
    /**
     * 构造查询参数map
     * @param request
     * @return
     */
    private AssetManageBeanRequest buildQueryParameter(HttpServletRequest request) {
        AssetManageBeanRequest params = new AssetManageBeanRequest();
        Integer page = Integer.parseInt(request.getParameter("page")==null?"1":request.getParameter("page"));
        Integer pageSize = Integer.parseInt(request.getParameter("pageSize")==null?"10":request.getParameter("pageSize"));
        params.setLimitStart((page - 1) * pageSize);
        params.setLimitEnd(pageSize);
        return params;
    }

}
