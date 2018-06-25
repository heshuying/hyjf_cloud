package com.hyjf.cs.trade.service.impl;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.trade.CreditListResponse;
import com.hyjf.am.response.trade.ProjectListResponse;
import com.hyjf.am.resquest.trade.CreditListRequest;
import com.hyjf.am.resquest.trade.ProjectListRequest;
import com.hyjf.am.vo.trade.AppProjectListCsVO;
import com.hyjf.am.vo.trade.TenderCreditDetailCustomizeCsVO;
import com.hyjf.am.vo.trade.WebProjectListCsVO;
import com.hyjf.am.vo.trade.WebProjectListCustomizeVo;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.cs.common.bean.result.AppResult;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.common.util.Page;
import com.hyjf.cs.trade.client.WebProjectListClient;
import com.hyjf.cs.trade.service.AppProjectListService;
import com.hyjf.cs.trade.service.BaseTradeServiceImpl;
import com.hyjf.cs.trade.service.WebProjectListService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * App端项目列表Service实现类
 *
 * @author zhangyk
 * @version WebProjectListServiceImpl, v0.1 2018/6/13 10:21
 */
@Service
public class AppProjectListServiceImpl extends BaseTradeServiceImpl implements AppProjectListService {

    private static Logger logger = LoggerFactory.getLogger(AppProjectListServiceImpl.class);

    @Autowired
    private WebProjectListClient webProjectListClient;

    /**
     * APP端投资散标项目列表
     *
     * @param request
     * @return
     * @author liuyang
     */
    @Override
    public AppResult searchAppProjectList(ProjectListRequest request) {
        // TODO: 2018/6/20   参数验证
        CheckUtil.check("HZT".equals(request.getProjectType()), MsgEnum.ERR_OBJECT_VALUE, "peojectType");
        // 初始化分页参数，并组合到请求参数
        Page page = Page.initPage(request.getCurrPage(), request.getPageSize());
        request.setLimitStart(page.getOffset());
        request.setLimitEnd(page.getLimit());
        request.setProjectType("CFH");  // 原来逻辑： 如果projectType == "HZT" ，则setProjectType == CFH；
        // ①查询count
        ProjectListResponse response = webProjectListClient.countAppProjectList(request);
        // 对调用返回的结果进行转换和拼装
        AppResult appResult = new AppResult();
        // 先抛错方式，避免代码看起来头重脚轻。
        if (!Response.isSuccess(response)) {
            logger.error("app端查询散标投资列表原子层count异常");
            throw new RuntimeException("app端查询散标投资列表原子层count异常");
        }
        int count = response.getCount();
        page.setTotal(count);
        //由于result类在转json时会去掉null值，手动初始化为非null，保证json不丢失key
        appResult.setData(new ArrayList<>());
        if (count > 0) {
            List<AppProjectListCsVO> result = new ArrayList<>();
            ProjectListResponse dataResponse = webProjectListClient.searchAppProjectList(request);
            if (!Response.isSuccess(dataResponse)) {
                logger.error("app端查询散标投资列表原子层List异常");
                throw new RuntimeException("app端查询散标投资列表原子层list数据异常");
            }
            result = CommonUtils.convertBeanList(dataResponse.getResultList(), AppProjectListCsVO.class);
            appResult.setData(result);
        }
        appResult.setPage(page);
        return appResult;


    }

    /**
     * APP端投资债转列表数据
     *
     * @author zhangyk
     * @date 2018/6/20 9:11
     */
    @Override
    public AppResult searchAppCreditList(ProjectListRequest request) {
        // 初始化分页参数，并组合到请求参数
        Page page = Page.initPage(request.getCurrPage(), request.getPageSize());
        request.setLimitStart(page.getOffset());
        request.setLimitEnd(page.getLimit());
        ProjectListResponse res = webProjectListClient.countAppCreditList(request);
        AppResult appResult = new AppResult();
        if (!Response.isSuccess(res)) {
            logger.error("查询债权转让原子层count异常");
            throw new RuntimeException("查询债权转让原子层count异常");
        }
        int count = res.getCount();
        page.setTotal(count);
        appResult.setData(new ArrayList<>());
        if (count > 0) {
            List<TenderCreditDetailCustomizeCsVO> result = new ArrayList<>();
            ProjectListResponse dataResponse = webProjectListClient.searchAppCreditList(request);
            if (!Response.isSuccess(dataResponse)) {
                logger.error("查询债权转让原子层list数据异常");
                throw new RuntimeException("查询债权转让原子层list数据异常");
            }
            result = CommonUtils.convertBeanList(dataResponse.getResultList(), TenderCreditDetailCustomizeCsVO.class);
            appResult.setData(result);
        }
        appResult.setPage(page);
        return appResult;
    }


    /**
     * 移动端计划列表
     *
     * @author zhangyk
     * @date 2018/6/21 19:12
     */
    @Override
    public AppResult searchAppPlanList(ProjectListRequest request) {
        Page page = Page.initPage(request.getCurrPage(),request.getPageSize());
        request.setLimitStart(page.getOffset());
        request.setLimitEnd(page.getLimit());
        Integer count = webProjectListClient.countAppPlanList(request);
        AppResult appResult = new AppResult();
        appResult.setData(new ArrayList<>());
        if (count == null){
            logger.error("app查询计划原子层count异常");
            throw new RuntimeException("app查询计划原子层count异常");
        }
        page.setTotal(count);
        List<WebProjectListCustomizeVo> list = webProjectListClient.searchAppPlanList(request);
        if (CollectionUtils.isEmpty(list)){
            logger.error("app查询计划原子层list异常");
            throw new RuntimeException("app查询计划原子层list异常");
        }
        appResult.setData(list);
        appResult.setPage(page);
        return appResult;

    }


}
