package com.hyjf.cs.trade.service.impl;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.trade.CreditListResponse;
import com.hyjf.am.response.trade.ProjectListResponse;
import com.hyjf.am.resquest.trade.CreditListRequest;
import com.hyjf.am.resquest.trade.ProjectListRequest;
import com.hyjf.am.vo.trade.TenderCreditDetailCustomizeCsVO;
import com.hyjf.am.vo.trade.WebProjectListCsVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.common.util.Page;
import com.hyjf.cs.trade.client.WebProjectListClient;
import com.hyjf.cs.trade.service.BaseTradeServiceImpl;
import com.hyjf.cs.trade.service.WebProjectListService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * web端项目列表Service实现类
 *
 * @author liuyang
 * @version WebProjectListServiceImpl, v0.1 2018/6/13 10:21
 */
@Service
public class WebProjectListServiceImpl extends BaseTradeServiceImpl implements WebProjectListService {

    private static Logger logger = LoggerFactory.getLogger(WebProjectListServiceImpl.class);

    @Autowired
    private WebProjectListClient webProjectListClient;

    /**
     * 获取Web端项目列表
     * @param request
     * @author liuyang
     * @return
     */
    @Override
    public WebResult searchProjectList(ProjectListRequest request) {
        // 参数验证 略

        // 初始化分页参数，并组合到请求参数
        Page page = Page.initPage(request.getCurrPage(),request.getPageSize());
        request.setLimitStart(page.getOffset());
        request.setLimitEnd(page.getLimit());
        // ①查询count
        ProjectListResponse response = webProjectListClient.countProjectList(request);
        // 对调用返回的结果进行转换和拼装
        WebResult webResult = new WebResult();
        // 先抛错方式，避免代码看起来头重脚轻。
        if (!Response.isSuccess(response)){
            logger.error("查询散标投资列表原子层count异常");
            throw new RuntimeException("查询散标投资列表原子层count异常");
        }
        int count = response.getCount();
        page.setTotal(count);
        //由于result类在转json时会去掉null值，手动初始化为非null，保证json不丢失key
        webResult.setData(new ArrayList<>());
        if (count > 0){
            List<WebProjectListCsVO> result = new ArrayList<>();
            ProjectListResponse dataResponse = webProjectListClient.searchProjectList(request);
            if (!Response.isSuccess(dataResponse)){
                logger.error("查询散标投资列表原子层List异常");
                throw  new RuntimeException("查询散标投资列表原子层list数据异常");
            }
            result = CommonUtils.convertBeanList(dataResponse.getResultList(),WebProjectListCsVO.class);
            webResult.setData(result);
        }
        webResult.setPage(page);
        return  webResult;

        //传统的if多重嵌套判断方式
        /*if (Response.isSuccess(response)){
            count = response.getCount();
            page.setTotal(count);
            if (count > 0){
                List<WebProjectListCsVO> result = new ArrayList<>();
                ProjectListResponse dataResponse = webProjectListClient.searchProjectList(request);
                if (Response.isSuccess(dataResponse)){
                    result = CommonUtils.convertBeanList(dataResponse.getResultList(),WebProjectListCsVO.class);
                    webResult.setData(result);
                }else{
                    throw  new RuntimeException("查询原子层list数据异常");
                }
            }
            webResult.setPage(page);
            return  webResult;
        }else{ // 如果需要还可以把原子层的错误信息继续向上抛
            throw new RuntimeException("查询原子层count异常");
        }*/

    }

    @Override
    public WebResult searchCreditList(CreditListRequest request) {
        // 初始化分页参数，并组合到请求参数
        Page page = Page.initPage(request.getCurrPage(),request.getPageSize());
        request.setLimitStart(page.getOffset());
        request.setLimitEnd(page.getLimit());
        // 原逻辑默认写死以下参数
        request.setBorrowPeriodMin(0);
        request.setBorrowPeriodMax(100);
        request.setBorrowAprMin(0);
        request.setBorrowAprMax(100);
        request.setDiscountSort("DESC");
        request.setTermSort("DESC");
        request.setCapitalSort("DESC");
        request.setInProgressSort("DESC");
        CreditListResponse res = webProjectListClient.countCreditList(request);
        WebResult webResult = new WebResult();
        if (!Response.isSuccess(res)){
            logger.error("查询债权转让原子层count异常");
            throw new RuntimeException("查询债权转让原子层count异常");
        }
        int count = res.getCount();
        page.setTotal(count);
        webResult.setData(new ArrayList<>());
        if (count > 0){
            List<TenderCreditDetailCustomizeCsVO> result = new ArrayList<>();
            CreditListResponse dataResponse = webProjectListClient.searchCreditList(request);
            if (!Response.isSuccess(dataResponse)){
                logger.error("查询债权转让原子层list数据异常");
                throw  new RuntimeException("查询债权转让原子层list数据异常");
            }
            result = CommonUtils.convertBeanList(dataResponse.getResultList(),TenderCreditDetailCustomizeCsVO.class);
            webResult.setData(result);
        }
        webResult.setPage(page);
        return  webResult;
    }
}
