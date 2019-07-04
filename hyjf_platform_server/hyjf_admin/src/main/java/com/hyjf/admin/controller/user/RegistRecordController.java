/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.user;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.hyjf.admin.beans.request.RegistRcordRequestBean;
import com.hyjf.admin.beans.response.UserManagerInitResponseBean;
import com.hyjf.admin.beans.vo.DropDownVO;
import com.hyjf.admin.beans.vo.RegistRecordCustomizeVO;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.mq.base.CommonProducer;
import com.hyjf.admin.mq.base.MessageContent;
import com.hyjf.admin.service.RegistRecordService;
import com.hyjf.admin.utils.exportutils.DataSet2ExcelSXSSFHelper;
import com.hyjf.admin.utils.exportutils.IValueFormatter;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.user.RegistRecordResponse;
import com.hyjf.am.resquest.user.RegistRcordRequest;
import com.hyjf.am.vo.config.AdminSystemVO;
import com.hyjf.am.vo.user.RegistRecordVO;
import com.hyjf.common.cache.CacheUtil;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.util.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.*;

/**
 * @author nxl
 * @version RegistRecordController, v0.1 2018/6/23 15:16
 */

@Api(value = "会员中心-注册记录", tags = "会员中心-注册记录")
@RestController
@RequestMapping("/hyjf-admin/registRecord")
public class RegistRecordController extends BaseController {
    @Autowired
    private CommonProducer commonProducer;
    @Autowired
    private RegistRecordService registRecordService;
    public static final String PERMISSIONS = "registlist";

    @ApiOperation(value = "注册记录页面初始化", notes = "注册记录页面初始化")
    @PostMapping(value = "/userRegistInit")
    @ResponseBody
    public AdminResult<UserManagerInitResponseBean> userRegistInit() {
        UserManagerInitResponseBean userManagerInitResponseBean = new UserManagerInitResponseBean();
        // 注册平台
        Map<String, String> registPlat = CacheUtil.getParamNameMap("CLIENT");
        List<DropDownVO> listRegistPlat = com.hyjf.admin.utils.ConvertUtils.convertParamMapToDropDown(registPlat);
        userManagerInitResponseBean.setRegistPlat(listRegistPlat);
        // 注册渠道
        RegistRcordRequest registerRcordeRequest = new RegistRcordRequest();
        RegistRecordResponse registRecordResponse = registRecordService.findUtmAll(registerRcordeRequest);
        userManagerInitResponseBean.setUtmPlatList(registRecordResponse.getUtmPlatVOList());
        return new AdminResult<UserManagerInitResponseBean>(userManagerInitResponseBean);
    }

    //会员管理列表查询
    @ApiOperation(value = "注册记录列表查询", notes = "注册记录列表查询")
    @PostMapping(value = "/registRecordList")
    @ResponseBody
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<ListResult<RegistRecordCustomizeVO>> selectRegistRecordList(HttpServletRequest request, @RequestBody RegistRcordRequestBean registRcordRequestBean) {
        // 获取该角色 权限列表
        List<String> perm = (List<String>) request.getSession().getAttribute("permission");
        //判断权限
        boolean isShow = false;
        for (String string : perm) {
            if (string.equals(PERMISSIONS + ":" + ShiroConstants.PERMISSION_HIDDEN_SHOW)) {
                isShow = true;
            }
        }
        RegistRcordRequest registerRcordeRequest = new RegistRcordRequest();
        BeanUtils.copyProperties(registRcordRequestBean, registerRcordeRequest);
        RegistRecordResponse registRecordResponse = registRecordService.findRegistRecordList(registerRcordeRequest);
        if (registRecordResponse == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(registRecordResponse)) {
            return new AdminResult<>(FAIL, registRecordResponse.getMessage());
        }
        List<RegistRecordCustomizeVO> registRecordCustomizeVO = new ArrayList<RegistRecordCustomizeVO>();
        if (null != registRecordResponse.getResultList() && registRecordResponse.getResultList().size() > 0) {
            if (!isShow) {
                //如果没有查看脱敏权限,显示加星
                for (RegistRecordVO registRecordVO:registRecordResponse.getResultList()){
                    registRecordVO.setMobile(AsteriskProcessUtil.getAsteriskedMobile(registRecordVO.getMobile()));
                }
            }
            registRecordCustomizeVO = CommonUtils.convertBeanList(registRecordResponse.getResultList(), RegistRecordCustomizeVO.class);
        }
        return new AdminResult<ListResult<RegistRecordCustomizeVO>>(ListResult.build(registRecordCustomizeVO, registRecordResponse.getCount()));
    }

    /**
     * 修改注册用户渠道信息： 加载渠道修改页面详细信息
     * 规则说明：
     * <p>
     * 1、用户名、用户类型、用户属性、注册时间、注册平台：系统带出，不可修改
     * <p>
     * 2、注册渠道：下拉框，默认显示当前渠道名称（无可不显示），可以输入渠道名称修改，输入名称必须在推广中心-渠道列表中存在。
     *
     * @param userId
     */
    @ApiOperation(value = "渠道详细信息", notes = "渠道详细信息")
    @GetMapping(value = "/registRecordUtmEditStr/{userId}", produces = "application/json; charset=utf-8")
    @ResponseBody
    @AuthorityAnnotation(key = PERMISSIONS, value = {ShiroConstants.PERMISSION_UPDATE})
    public AdminResult<RegistRecordCustomizeVO> findRegistRecordOne(@PathVariable String userId) {
        RegistRcordRequest registerRcordeRequest = new RegistRcordRequest();
        registerRcordeRequest.setUserId(userId);
        RegistRecordResponse registRecordResponse = registRecordService.findRegistRecordOne(registerRcordeRequest);
        if (registRecordResponse == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(registRecordResponse)) {
            return new AdminResult<>(FAIL, registRecordResponse.getMessage());
        }
        // 详细信息 注册渠道（source_id）
        RegistRecordCustomizeVO registRecordCustomizeVO = new RegistRecordCustomizeVO();
        if (null != registRecordResponse.getResult()) {
            RegistRecordVO registRecordVO = registRecordResponse.getResult();
            registRecordVO.setMobile(AsteriskProcessUtil.getAsteriskedValue(registRecordVO.getMobile()));
            registRecordCustomizeVO = CommonUtils.convertBean(registRecordResponse.getResult(), RegistRecordCustomizeVO.class);
        }
        // 注册渠道（source_id）
        RegistRecordResponse registRecordResponseUtmAll = registRecordService.findUtmAllSourcePc(registerRcordeRequest);
        registRecordCustomizeVO.setUtmPlatList(registRecordResponseUtmAll.getUtmPlatVOList());
        return new AdminResult<RegistRecordCustomizeVO>(registRecordCustomizeVO);
    }

    /**
     * 修改渠道信息
     * 3、注册渠道和修改原因必填
     * 4、点击确定：
     * 1）校验渠道值是否正确，校验成功后，修改用户注册渠道，更新为修改值，并且新增一条操作记录。
     *
     * @param registRcordRequestBean
     */
    @ApiOperation(value = "渠道修改确认", notes = "渠道修改确认")
    @PostMapping(value = "/registRecordUtmEdit")
    @ResponseBody
    @AuthorityAnnotation(key = PERMISSIONS, value = {ShiroConstants.PERMISSION_UPDATE})
    public AdminResult editRegistRecordOne(HttpServletRequest request , @RequestBody RegistRcordRequestBean registRcordRequestBean) {
        //获取登录用户Id
        AdminSystemVO adminSystemVO = this.getUser(request);
        registRcordRequestBean.setLoginUserId(adminSystemVO.getId());
        registRcordRequestBean.setLoginUserName(adminSystemVO.getUsername());
        boolean registRecordResponse = registRecordService.editRegistRecordOne(registRcordRequestBean);
        if (!registRecordResponse) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        return new AdminResult<>();
    }

    /**
     * 同步客户信息到wbs财富系统
     * 客户修改完渠道信息，点击同步按钮，推送客户信息到mq，有wbs模块消费 推送到wbs系统
     *
     * @author wangxd
     * @date 2019/6/14 15:18
     */
    @ApiOperation(value = "推送客户信息到wbs", notes = "推送客户信息到wbs")
    @PostMapping(value = "/syncAccountWbs")
    @ResponseBody
    public AdminResult syncAccountWbs(@RequestParam(value = "userId") String userId) {

        if (StringUtils.isNotBlank(userId)) {
            String jsonStr = "";
            try {
                JSONObject jsonData = new JSONObject();
                jsonData.put("userId", userId);
                jsonStr = JSON.toJSONString(jsonData);
                commonProducer.messageSend(new MessageContent(MQConstant.SYNC_ACCOUNT_TOPIC, UUID.randomUUID().toString(), jsonStr));
                logger.info("=====手动发送账户信息到[{}]到mq——wbs [成功]=====", jsonStr);
            } catch (Exception e) {
                logger.error("=====手动发送账户信息[{}]到mq－wbs [失败!!]=====", jsonStr);
                return new AdminResult<>(FAIL, FAIL_DESC);
            }
            return new AdminResult<>();
        } else {
            return new AdminResult<>(FAIL, "用户id不能为空！");
        }
    }

    /**
     * 根据业务需求导出相应的表格 此处暂时为可用情况 缺陷：
     * 1.无法指定相应的列的顺序，
     * 2.无法配置，excel文件名，excel sheet名称
     * 3.目前只能导出一个sheet 4.列的宽度的自适应，中文存在一定问题
     * 5.根据导出的业务需求最好可以在导出的时候输入起止页码，因为在大数据量的情况下容易造成卡顿
     *
     * @param registRcordRequestBean
     * @param response
     * @throws Exception
     */
    //@ApiOperation(value = "注册记录列表导出", notes = "注册记录列表导出")
    //@PostMapping(value = "/exportregist")
    /*public void exportExcel(HttpServletResponse response, @RequestBody RegistRcordRequestBean registRcordRequestBean) throws Exception {

        // 表格sheet名称
        String sheetName = "注册记录";
        // 文件名称
        String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;

        // 需要输出的结果列表
        RegistRcordRequest registerRcordeRequest = new RegistRcordRequest();
        BeanUtils.copyProperties(registRcordRequestBean,registerRcordeRequest);
        registerRcordeRequest.setLimitFlg(true);
        List<RegistRecordVO> listRgistRecord = new ArrayList<RegistRecordVO>();
        RegistRecordResponse registRecordResponse = registRecordService.findRegistRecordList(registerRcordeRequest);
        if(null!=registRecordResponse){
            listRgistRecord = registRecordResponse.getResultList();
        }
        String[] titles = new String[] { "序号", "用户名", "手机号", "推荐人", "注册渠道", "注册平台", "注册IP", "注册时间" };
        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格
        HSSFSheet sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles, sheetName + "_第1页");

        if (listRgistRecord != null && listRgistRecord.size() > 0) {

            int sheetCount = 1;
            int rowNum = 0;

            for (int i = 0; i < listRgistRecord.size(); i++) {
                rowNum++;
                if (i != 0 && i % 60000 == 0) {
                    sheetCount++;
                    sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles, (sheetName + "_第" + sheetCount + "页"));
                    rowNum = 1;
                }

                // 新建一行
                Row row = sheet.createRow(rowNum);
                // 循环数据
                for (int celLength = 0; celLength < titles.length; celLength++) {
                    RegistRecordVO user = listRgistRecord.get(i);
                    // 创建相应的单元格
                    Cell cell = row.createCell(celLength);
                    if (celLength == 0) {// 序号
                        cell.setCellValue(i + 1);
                    } else if (celLength == 1) {// 用户名
                        cell.setCellValue(user.getUserName());
                    } else if (celLength == 2) {// 手机号
                        cell.setCellValue(AsteriskProcessUtil.getAsteriskedValue(user.getMobile(),3,7));
                    } else if (celLength == 3) {// 推荐人
                        cell.setCellValue(user.getRecommendName());
                    } else if (celLength == 4) {// 注册渠道
                        cell.setCellValue(user.getSourceName());
                    } else if (celLength == 5) {// 注册平台
                        cell.setCellValue(user.getRegistPlat());
                    } else if (celLength == 6) {// 注册IP
                        cell.setCellValue(user.getRegIP());
                    } else if (celLength == 7) {// 注册时间
                        cell.setCellValue(user.getRegTime());
                    }
                }
            }
        }
        // 导出
        ExportExcel.writeExcelFile(response, workbook, titles, fileName);
    }*/

    /**
     * 导出excel
     *
     * @param registRcordRequestBean
     * @param request
     * @param response
     * @throws Exception
     */
    @ApiOperation(value = "注册记录列表导出", notes = "注册记录列表导出")
    @PostMapping(value = "/exportregist")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_EXPORT)
    public void exportExcelGist(HttpServletResponse response, HttpServletRequest request, @RequestBody RegistRcordRequestBean registRcordRequestBean) throws Exception {
        // 封装查询条件
        RegistRcordRequest registerRcordeRequest = new RegistRcordRequest();
        // 获取该角色 权限列表
        List<String> perm = (List<String>) request.getSession().getAttribute("permission");
        //判断权限
        boolean isShow = false;
        for (String string : perm) {
            if (string.equals(PERMISSIONS + ":" + ShiroConstants.PERMISSION_HIDDEN_SHOW)) {
                isShow = true;
            }
        }
        BeanUtils.copyProperties(registRcordRequestBean, registerRcordeRequest);
        //sheet默认最大行数
        int defaultRowMaxCount = Integer.valueOf(systemConfig.getDefaultRowMaxCount());
        // 表格sheet名称
        String sheetName = "注册记录";
        // 文件名称
        String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + ".xlsx";
        // 声明一个工作薄
        SXSSFWorkbook workbook = new SXSSFWorkbook(SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
        DataSet2ExcelSXSSFHelper helper = new DataSet2ExcelSXSSFHelper();
        registerRcordeRequest.setLimitFlg(false);
        //请求第一页5000条
        registerRcordeRequest.setPageSize(defaultRowMaxCount);
        registerRcordeRequest.setCurrPage(1);
        // 需要输出的结果列表
        RegistRecordResponse registRecordResponse = registRecordService.findRegistRecordList(registerRcordeRequest);
        Integer totalCount = registRecordResponse.getCount();
        int sheetCount = (totalCount % defaultRowMaxCount) == 0 ? totalCount / defaultRowMaxCount : totalCount / defaultRowMaxCount + 1;
        Map<String, String> beanPropertyColumnMap = buildMap();
        Map<String, IValueFormatter> mapValueAdapter = buildValueAdapter();
        if (!isShow) {
            mapValueAdapter = buildValueAdaptertAsterisked();
        }
        String sheetNameTmp = sheetName + "_第1页";
        if (totalCount == 0) {
            helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, new ArrayList());
        } else {
            helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, registRecordResponse.getResultList());
        }
        for (int i = 1; i < sheetCount; i++) {
            registerRcordeRequest.setPageSize(defaultRowMaxCount);
            registerRcordeRequest.setCurrPage(i + 1);
            RegistRecordResponse registRecordResponse2 = registRecordService.findRegistRecordList(registerRcordeRequest);
            if (registRecordResponse2 != null && registRecordResponse2.getResultList().size() > 0) {
                sheetNameTmp = sheetName + "_第" + (i + 1) + "页";
                helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, registRecordResponse2.getResultList());
            } else {
                break;
            }
        }
        DataSet2ExcelSXSSFHelper.write2Response(request, response, fileName, workbook);
    }

    private Map<String, String> buildMap() {
        Map<String, String> map = Maps.newLinkedHashMap();
        map.put("userName", "用户名");
        map.put("mobile", "手机号");
        map.put("recommendName", "推荐人");
        map.put("sourceName", "注册渠道");
        map.put("registPlat", "注册平台");
        map.put("regIP", "注册IP");
        map.put("regTime", "注册时间");
        return map;
    }

    private Map<String, IValueFormatter> buildValueAdapter() {
        Map<String, IValueFormatter> mapAdapter = Maps.newHashMap();
        return mapAdapter;
    }

    private Map<String, IValueFormatter> buildValueAdaptertAsterisked() {
        Map<String, IValueFormatter> mapAdapter = Maps.newHashMap();
        IValueFormatter mobileAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                String mobile = (String) object;
                return AsteriskProcessUtil.getAsteriskedMobile(mobile);
            }
        };

        mapAdapter.put("mobile", mobileAdapter);
        return mapAdapter;
    }
}
