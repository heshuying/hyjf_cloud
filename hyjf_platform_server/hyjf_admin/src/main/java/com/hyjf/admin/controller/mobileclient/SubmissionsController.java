package com.hyjf.admin.controller.mobileclient;

import com.google.common.collect.Maps;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.mobileclient.SubmissionsService;
import com.hyjf.admin.utils.exportutils.DataSet2ExcelSXSSFHelper;
import com.hyjf.admin.utils.exportutils.IValueFormatter;
import com.hyjf.am.response.config.SubmissionsResponse;
import com.hyjf.am.response.user.UserResponse;
import com.hyjf.am.resquest.config.SubmissionsRequest;
import com.hyjf.am.vo.config.SubmissionsCustomizeVO;
import com.hyjf.am.vo.config.SubmissionsVO;
import com.hyjf.common.cache.CacheUtil;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.StringPool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.*;

/**
 * 意见反馈
 * @author lisheng
 * @version SubmissionsController, v0.1 2018/7/11 11:25
 */
@Api(tags = "移动客户端-意见反馈")
@RestController
@RequestMapping("/hyjf-admin/submissions")
public class SubmissionsController extends BaseController {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    SubmissionsService  submissionsService;
    //权限名称
    private static final String PERMISSIONS = "submissions";

    /**
     * 查询列表
     *
     * @return
     */
    @ApiOperation(value = "意见反馈列表查询", notes = "意见反馈列表查询")
    @PostMapping("/getRecordList")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<ListResult<SubmissionsCustomizeVO>> findAppBannerData(@RequestBody SubmissionsRequest form) {
        Map<String, String> userStatus = CacheUtil.getParamNameMap("CLIENT");
        if (StringUtils.isNotBlank(form.getUserName())) {
            UserResponse user = submissionsService.getUserIdByUserName(form.getUserName());
            if (user != null && user.getResult() != null) {
                Integer userId = user.getResult().getUserId();
                form.setUserId(userId);
            }
        }
        SubmissionsResponse submissionList = submissionsService.getSubmissionList(form);
        List<SubmissionsCustomizeVO> resultList = submissionList.getResultList();
        if (!CollectionUtils.isEmpty(resultList)) {
            for (SubmissionsCustomizeVO submissionsCustomizeVO : resultList) {
                if (StringUtils.isNotBlank(submissionsCustomizeVO.getSysVersion())) {
                    String type = userStatus.get(submissionsCustomizeVO.getSysType()) + "-" + submissionsCustomizeVO.getSysVersion();
                    submissionsCustomizeVO.setSysType(type);
                }
                Integer userId = submissionsCustomizeVO.getUserId();
                UserResponse users = submissionsService.getUserIdByUserId(userId);
                String userName = users.getResult() != null ? users.getResult().getUsername() : "";
                submissionsCustomizeVO.setUserName(userName);
            }
        }
        return new AdminResult<ListResult<SubmissionsCustomizeVO>>(ListResult.build(submissionList.getResultList(), submissionList.getRecordTotal()));
    }
    /**
     * 查询列表下拉框加载
     *
     * @return
     */
    @ApiOperation(value = "查询列表下拉框加载", notes = "查询列表下拉框加载")
    @GetMapping("/recordlistselect")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult recordListSelect() {
        AdminResult adminResult = new AdminResult();
        Map<String, String> userStatus = CacheUtil.getParamNameMap("CLIENT");
        adminResult.setData(userStatus);
        return  adminResult;
    }

    @ApiOperation(value = "意见反馈:获取详细画面", notes = "意见反馈:获取详细画面")
    @PostMapping(value = "/searchinfo")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_MODIFY)
    public AdminResult<SubmissionsVO> searchinfo(@RequestBody SubmissionsRequest request) {
        return new AdminResult<SubmissionsVO>(submissionsService.getRecord(request));
    }

    /**
     * 意见反馈更新保存
     *
     * @return
     * @throws ParseException
     */
    @ApiOperation(value = "意见反馈更新保存", notes = "意见反馈更新保存")
    @PostMapping("/updateSubmissionsAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_MODIFY)
    public AdminResult<ListResult<SubmissionsCustomizeVO>> updateSubmissions(@RequestBody SubmissionsRequest form) {
        SubmissionsResponse response = submissionsService.updateSubmissionStatus(form);
        return new AdminResult<ListResult<SubmissionsCustomizeVO>>(ListResult.build(response.getResultList(), response.getRecordTotal()));


    }

    /**
     * 根据业务需求导出相应的表格 此处暂时为可用情况 缺陷： 1.无法指定相应的列的顺序， 2.无法配置，excel文件名，excel sheet名称
     * 3.目前只能导出一个sheet 4.列的宽度的自适应，中文存在一定问题
     * 5.根据导出的业务需求最好可以在导出的时候输入起止页码，因为在大数据量的情况下容易造成卡顿
     * @param response
     * @throws Exception
     */
    @ApiOperation(value = "意见反馈导出", notes = "意见反馈导出")
    @PostMapping("/exportListAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_EXPORT)
    public void exportListAction(HttpServletRequest request, @RequestBody SubmissionsRequest form,
                                 HttpServletResponse response) throws Exception {

        //sheet默认最大行数
        int defaultRowMaxCount = Integer.valueOf(systemConfig.getDefaultRowMaxCount());
        // 表格sheet名称
        String sheetName = "意见反馈列表";
        // 文件名称
        String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;
        // 声明一个工作薄
        SXSSFWorkbook workbook = new SXSSFWorkbook(SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
        DataSet2ExcelSXSSFHelper helper = new DataSet2ExcelSXSSFHelper();
        //请求第一页5000条
        form.setPageSize(defaultRowMaxCount);
        form.setCurrPage(1);
        Map<String, String> userStatus = CacheUtil.getParamNameMap("CLIENT");
        if(StringUtils.isNotBlank(form.getUserName())){
            UserResponse user = submissionsService.getUserIdByUserName(form.getUserName());
            if(user!=null&&user.getResult()!=null){
                Integer userId = user.getResult().getUserId();
                form.setUserId(userId);
            }
        }
        SubmissionsResponse submissionsResponse = submissionsService.getExportSubmissionList(form);
        List<SubmissionsCustomizeVO> submissionsList = submissionsResponse.getResultList();
        for (SubmissionsCustomizeVO submissionsCustomizeVO : submissionsList) {
            String type = userStatus.get(submissionsCustomizeVO.getSysType()) + "-" + submissionsCustomizeVO.getSysVersion();
            submissionsCustomizeVO.setSysType(type);
            Integer userId = submissionsCustomizeVO.getUserId();
            UserResponse users = submissionsService.getUserIdByUserId(userId);
            String userName = users.getResult() != null ? users.getResult().getUsername() : "";
            submissionsCustomizeVO.setUserName(userName);
        }
        Integer totalCount = submissionsResponse.getRecordTotal();

        int sheetCount = (totalCount % defaultRowMaxCount) == 0 ? totalCount / defaultRowMaxCount : totalCount / defaultRowMaxCount + 1;
        Map<String, String> beanPropertyColumnMap = buildMap();
        Map<String, IValueFormatter> mapValueAdapter = new HashMap<>();
        String sheetNameTmp = sheetName + "_第1页";
        if (totalCount == 0) {

            helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, new ArrayList());
        }else {
            helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, submissionsList);
        }
        for (int i = 1; i < sheetCount; i++) {

            form.setCurrPage(i+1);
            form.setPageSize(defaultRowMaxCount);
            Map<String, String> userStatus2 = CacheUtil.getParamNameMap("CLIENT");
            SubmissionsResponse submissionList2 = submissionsService.getExportSubmissionList(form);
            List<SubmissionsCustomizeVO> submissionsList2 = submissionList2.getResultList();
            for (SubmissionsCustomizeVO submissionsCustomizeVO : submissionsList2) {
                String type = userStatus2.get(submissionsCustomizeVO.getSysType()) + "-" + submissionsCustomizeVO.getSysVersion();
                submissionsCustomizeVO.setSysType(type);
                Integer userId = submissionsCustomizeVO.getUserId();
                UserResponse users = submissionsService.getUserIdByUserId(userId);
                String userName = users.getResult() != null ? users.getResult().getUsername() : "";
                submissionsCustomizeVO.setUserName(userName);
            }
            if (submissionsList2 != null && submissionsList2.size()> 0) {
                sheetNameTmp = sheetName + "_第" + (i + 1) + "页";
                helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter,  submissionsList2);
            } else {
                break;
            }
        }
        DataSet2ExcelSXSSFHelper.write2Response(request, response, fileName, workbook);
    }

    private Map<String, String> buildMap() {
        Map<String, String> map = Maps.newLinkedHashMap();
        map.put("userName","用户名");
        map.put("sysType","操作系统");
        map.put("platformVersion","版本号");
        map.put("phoneType","手机型号");
        map.put("content","反馈内容");
        map.put("createTime","时间");
        return map;
    }

}
