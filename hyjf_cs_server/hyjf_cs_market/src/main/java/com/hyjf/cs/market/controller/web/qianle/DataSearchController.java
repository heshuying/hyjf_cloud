package com.hyjf.cs.market.controller.web.qianle;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.trade.DataSearchCustomizeResponse;
import com.hyjf.am.resquest.trade.DataSearchRequest;
import com.hyjf.am.vo.trade.DataSearchCustomizeVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.StringPool;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.common.util.Page;
import com.hyjf.cs.market.bean.DataSearchBean;
import com.hyjf.cs.market.service.DataSearchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lisheng
 * @version DataSearchController, v0.1 2018/8/21 9:37
 */
@Api(tags = "web端-千乐数据查询统计")
@RestController
@RequestMapping("/hyjf-web/qianle")
public class DataSearchController {
    @Autowired
    DataSearchService dataSearchService;

    /**
     * 千乐数据查询
     * @return
     */
    @ApiOperation(value = "千乐数据查询", notes = "千乐数据查询")
    @PostMapping("/querylist")
    public  WebResult<Map<String, Object>> getQianleSanList(@RequestBody DataSearchBean request){
        DataSearchRequest dataSearchRequest = CommonUtils.convertBean(request, DataSearchRequest.class);
        DataSearchCustomizeResponse response = dataSearchService.findDataList(dataSearchRequest);
        HashMap<String, Object> result = new HashMap<>();
        result.put("data", response.getResultList());
        result.put("money",response.getMoney());
        WebResult webResult = new WebResult(result);
        Page page = new Page();
        page.setTotal(response.getCount());
        page.setCurrPage(request.getCurrPage());
        page.setPageSize(request.getPageSize());
        webResult.setPage(page);
        return webResult;
    }
    @ApiOperation(value = "退出登录", notes = "退出登录")
    @PostMapping("/cancle")
    public WebResult cancle(){
        WebResult webResult = new WebResult();
        return webResult;
    }

    @ApiOperation(value = "登录", notes = "登录")
    @PostMapping("/login")
    public WebResult login(){
        WebResult webResult = new WebResult();
        return webResult;
    }

    @ApiOperation(value = "获取验证码", notes = "获取验证码")
    @PostMapping("/sendsms")
    public WebResult sendsms(){
        WebResult webResult = new WebResult();
        return webResult;
    }
    /**
     * 千乐数据查询
     * @return
     */
    @ApiOperation(value = "千乐数据导出", notes = "千乐数据导出")
    @PostMapping("/exportdata")
    public  void exportAction(@RequestBody DataSearchBean request,HttpServletResponse response){
        DataSearchRequest dataSearchRequest = CommonUtils.convertBean(request, DataSearchRequest.class);
        DataSearchCustomizeResponse result = dataSearchService.findDataList(dataSearchRequest);
        // 表格sheet名称
        String sheetName = "数据导出";
        List<DataSearchCustomizeVO> resultList = result.getResultList();
        String fileName = sheetName + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;
        String[] titles = new String[]
                { "序号", "注册日期", "用户名", "姓名", "手机号", "投资类型",
                        "项目/计划编号", "项目标题", "投资金额", "投资期限","年化金额"
                        ,"佣金7%","推荐人姓名","推荐人手机号","投资日期"
                };
        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格
        //HSSFSheet sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles, sheetName + "_第1页");

    }
}
