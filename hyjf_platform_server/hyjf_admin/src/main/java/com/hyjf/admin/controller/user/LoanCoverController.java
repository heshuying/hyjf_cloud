package com.hyjf.admin.controller.user;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.hyjf.admin.beans.request.LoanCoverUserRequestBean;
import com.hyjf.admin.beans.vo.LoanCoverUserCustomizeVO;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.config.SystemConfig;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.LoanCoverService;
import com.hyjf.admin.utils.exportutils.DataSet2ExcelSXSSFHelper;
import com.hyjf.admin.utils.exportutils.IValueFormatter;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.user.LoanCoverUserResponse;
import com.hyjf.am.resquest.user.LoanCoverUserRequest;
import com.hyjf.am.vo.config.AdminSystemVO;
import com.hyjf.am.vo.user.CertificateAuthorityVO;
import com.hyjf.am.vo.user.LoanCoverUserVO;
import com.hyjf.common.util.*;
import com.hyjf.common.validator.Validator;
import com.hyjf.pay.lib.fadada.bean.DzqzCallBean;
import com.hyjf.pay.lib.fadada.util.DzqzCallUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author nxl
 * @version UserCenterController, v0.1 2018/6/19 15:08
 */
@Api(value = "会员中心-借款盖章用户接口", tags = "会员中心-借款盖章用户接口")
@RestController
@RequestMapping("/hyjf-admin/usersLoancover")
public class LoanCoverController extends BaseController {

    @Autowired
    private LoanCoverService loanCoverService;
    @Autowired
    SystemConfig systemConfig;
    public static final String PERMISSIONS = "loancover";

    /**
     * 获取借款盖章用户列表
     *
     * @return
     */
    @ApiOperation(value = "获取借款盖章用户列表", notes = "获取借款盖章用户列表")
    @PostMapping(value = "/selectLoancoverList")
    @ResponseBody
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<ListResult<LoanCoverUserCustomizeVO>> selectLoancoverList(@RequestBody LoanCoverUserRequestBean loanCoverUserRequestBean) {
        LoanCoverUserRequest loanCoverUserRequest = new LoanCoverUserRequest();
        BeanUtils.copyProperties(loanCoverUserRequestBean, loanCoverUserRequest);
        LoanCoverUserResponse loanCoverUserVOList = loanCoverService.selectUserMemberList(loanCoverUserRequest);
        if (loanCoverUserVOList == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(loanCoverUserVOList)) {
            return new AdminResult<>(FAIL, loanCoverUserVOList.getMessage());
        }
        List<LoanCoverUserCustomizeVO> loanCoverUserCustomizeVOList= new ArrayList<LoanCoverUserCustomizeVO>();
        if(null!=loanCoverUserVOList.getResultList()&&loanCoverUserVOList.getResultList().size()>0){
            loanCoverUserCustomizeVOList = CommonUtils.convertBeanList(loanCoverUserVOList.getResultList(),LoanCoverUserCustomizeVO.class);
        }
        return new AdminResult<ListResult<LoanCoverUserCustomizeVO>>(ListResult.build(loanCoverUserCustomizeVOList, loanCoverUserVOList.getCount()));
    }

    /**
     * 初始化修改借款盖章用户
     *
     * @return
     */
    @ApiOperation(value = "初始化修改借款盖章用户", notes = "初始化修改借款盖章用户")
    @PostMapping(value = "/updateLoancoverInit")
    @ResponseBody
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_MODIFY)
    public AdminResult<LoanCoverUserCustomizeVO> updateLoancoverInit(HttpServletRequest request, HttpServletResponse response, @RequestBody String id) {
        LoanCoverUserResponse loanCoverUserResponse = loanCoverService.getLoanCoverUserById(id);
        if (null != loanCoverUserResponse) {
            LoanCoverUserVO loanCoverUserVO = loanCoverUserResponse.getResult();
            LoanCoverUserCustomizeVO loanCoverUserCustomizeVO = new LoanCoverUserCustomizeVO();
            if (null != loanCoverUserVO) {
                BeanUtils.copyProperties(loanCoverUserVO, loanCoverUserCustomizeVO);
                return new AdminResult<LoanCoverUserCustomizeVO>(loanCoverUserCustomizeVO);
            }
        }
        return new AdminResult<>(FAIL, FAIL_DESC);
    }

    /**
     * 添加借款盖章用户
     *
     * @return
     */
    @ApiOperation(value = "添加借款盖章用户", notes = "添加借款盖章用户")
    @PostMapping(value = "/insertLoancover")
    @ResponseBody
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_ADD)
    public AdminResult insertLoancover(HttpServletRequest request, HttpServletResponse response, @RequestBody LoanCoverUserRequestBean loanCoverUserRequestBean) {
        LoanCoverUserRequest loanCoverUserRequest = new LoanCoverUserRequest();
        BeanUtils.copyProperties(loanCoverUserRequestBean, loanCoverUserRequest);
        //获取登录用户Id
        AdminSystemVO adminSystemVO = this.getUser(request);
        loanCoverUserRequest.setCreateUserId(Integer.parseInt(adminSystemVO.getId()));
        loanCoverUserRequest.setCreateUserName(adminSystemVO.getUsername());
        //获取登录用户名
        // 调用校验
        if (!validatorParm(loanCoverUserRequest)) {
            // 失败返回
            return new AdminResult<>(FAIL, "参数错误");
        }
        if (!loanCoverService.selectIsExistsRecordByIdNo(loanCoverUserRequest.getIdNo(),loanCoverUserRequest.getName())) {
            return new AdminResult<>(FAIL, "数据重复,请检查后提交");
        }
        loanCoverUserRequest.setCreateTime(new Date());
        int intFlg = loanCoverService.insertLoanCoverUser(loanCoverUserRequest);

        if (intFlg <= 0) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        return new AdminResult<>();
    }

    private boolean validatorParm(LoanCoverUserRequest form) {
        if (Validator.isNull(form.getName())) {
            return false;
        }
        if (form.getName().length() > 50) {
            return false;
        }
        return true;
    }

    /**
     * 添加借款盖章用户
     *
     * @return
     */
    @ApiOperation(value = "修改借款盖章用户", notes = "修改借款盖章用户")
    @PostMapping(value = "/updateLoancover")
    @ResponseBody
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_MODIFY)
    public AdminResult updateLoancover(HttpServletRequest request, HttpServletResponse response, @RequestBody LoanCoverUserRequestBean loanCoverUserRequestBean) {
        //获取登录用户Id
        AdminSystemVO adminSystemVO = this.getUser(request);
        int loginUserId = Integer.parseInt(adminSystemVO.getId());
        String loginUserName = adminSystemVO.getUsername();
        return loanCoverService.updateLoanCoverUser(loanCoverUserRequestBean,loginUserId,loginUserName);
    }

    /*  *
       * 导出功能
       *
       * @param request
       * @param modelAndView
       * @param form*/
    //@ApiOperation(value = "导出借款盖章用户", notes = "导出借款盖章用户")
    //@PostMapping(value = "/exportLoancover")
    /*public void exportAction(HttpServletRequest request, HttpServletResponse response, @RequestBody LoanCoverUserRequestBean loanCoverUserRequestBean) throws Exception {
        LoanCoverUserRequest loanCoverUserRequest = new LoanCoverUserRequest();
        BeanUtils.copyProperties(loanCoverUserRequestBean, loanCoverUserRequest);
        loanCoverUserRequest.setLimitFlg(true);
        LoanCoverUserResponse loanCoverUserResponse = loanCoverService.selectUserMemberList(loanCoverUserRequest);
        List<LoanCoverUserVO> loanCoverUserVOList = new ArrayList<LoanCoverUserVO>();
        if (null != loanCoverUserRequest) {
            loanCoverUserVOList = loanCoverUserResponse.getResultList();
        }
        // 表格sheet名称
        String sheetName = "借款盖章用户查询";
        String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8)+ StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date())
                + CustomConstants.EXCEL_EXT;
        String[] titles = new String[]{"序号", "手机号", "名称", "证件号", "用户类型", "邮箱", "客户编号", "状态", "状态码", "添加时间", "申请时间"};
        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格
        HSSFSheet sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles, sheetName + "_第1页");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (loanCoverUserVOList != null && loanCoverUserVOList.size() > 0) {
            int sheetCount = 1;
            int rowNum = 0;
            for (int i = 0; i < loanCoverUserVOList.size(); i++) {
                rowNum++;
                if (i != 0 && i % 60000 == 0) {
                    sheetCount++;
                    sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles,
                            (sheetName + "_第" + sheetCount + "页"));
                    rowNum = 1;
                }
                // 新建一行
                Row row = sheet.createRow(rowNum);
                // 循环数据
                for (int celLength = 0; celLength < titles.length; celLength++) {
                    LoanCoverUserVO pInfo = loanCoverUserVOList.get(i);
                    // 创建相应的单元格
                    Cell cell = row.createCell(celLength);
                    // 序号
                    if (celLength == 0) {
                        cell.setCellValue(i + 1);
                    } else if (celLength == 1) {
                        cell.setCellValue(pInfo.getMobile());
                    } else if (celLength == 2) {
                        cell.setCellValue(pInfo.getName());
                    } else if (celLength == 3) {
                        cell.setCellValue(pInfo.getIdNo());
                    } else if (celLength == 4) {
                        if (pInfo.getIdType() == 0) {
                            cell.setCellValue("个人");
                        } else {
                            cell.setCellValue("企业");
                        }
                    } else if (celLength == 5) {
                        cell.setCellValue(pInfo.getEmail());
                    } else if (celLength == 6) {
                        cell.setCellValue(pInfo.getCustomerId());
                    } else if (celLength == 7) {
                        if(pInfo.getStatus() ==null){
                            cell.setCellValue("未认证");
                        } else if ("success".equals(pInfo.getStatus())) {
                            cell.setCellValue("认证成功");
                        } else if ("error".equals(pInfo.getStatus())) {
                            cell.setCellValue("认证失败");
                        } else {
                            cell.setCellValue(pInfo.getStatus());
                        }
                    } else if (celLength == 8) {
                        cell.setCellValue(pInfo.getCode());
                    } else if (celLength == 9) {
                        Date dateCreateTime = pInfo.getCreateTime();
                        String strCreateTime = format.format(dateCreateTime);
                        cell.setCellValue(strCreateTime);
                    } else if (celLength == 10) {
                        if (pInfo.getUpdateTime() != null) {
                            Date dateUpdateTime = pInfo.getUpdateTime();
                            String strUpdateTime = format.format(dateUpdateTime);
                            cell.setCellValue(strUpdateTime);
                        }
                    }
                }
            }
        }
        // 导出
        ExportExcel.writeExcelFile(response, workbook, titles, fileName);
        logger.info("=============导出借款盖章用户完成=============");
    }
*/
    /**
     * 导出excel
     *
     * @param loanCoverUserRequestBean
     * @param request
     * @param response
     * @throws Exception
     */
    @ApiOperation(value = "导出借款盖章用户", notes = "导出借款盖章用户")
    @PostMapping(value = "/exportLoancover")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_EXPORT)
    public void exportExcelAccount(HttpServletRequest request, HttpServletResponse response, @RequestBody LoanCoverUserRequestBean loanCoverUserRequestBean) throws Exception {
        // 封装查询条件
        LoanCoverUserRequest loanCoverUserRequest = new LoanCoverUserRequest();
        BeanUtils.copyProperties(loanCoverUserRequestBean, loanCoverUserRequest);
        //sheet默认最大行数
        int defaultRowMaxCount = Integer.valueOf(systemConfig.getDefaultRowMaxCount());
        // 表格sheet名称
        String sheetName = "借款盖章用户查询";
        // 文件名称
        String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + ".xlsx";
        // 声明一个工作薄
        SXSSFWorkbook workbook = new SXSSFWorkbook(SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
        DataSet2ExcelSXSSFHelper helper = new DataSet2ExcelSXSSFHelper();
        loanCoverUserRequest.setLimitFlg(false);
        //请求第一页5000条
        loanCoverUserRequest.setPageSize(defaultRowMaxCount);
        loanCoverUserRequest.setCurrPage(1);
        // 需要输出的结果列表
        LoanCoverUserResponse loanCoverUserResponse = loanCoverService.selectUserMemberList(loanCoverUserRequest);
        Integer totalCount = loanCoverUserResponse.getCount();
        int sheetCount = (totalCount % defaultRowMaxCount) == 0 ? totalCount / defaultRowMaxCount : totalCount / defaultRowMaxCount + 1;
        Map<String, String> beanPropertyColumnMap = buildMap();
        Map<String, IValueFormatter> mapValueAdapter = buildValueAdapter();
        String sheetNameTmp = sheetName + "_第1页";
        if (totalCount == 0) {
            helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, new ArrayList());
        }else{
            helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, loanCoverUserResponse.getResultList());
        }
        for (int i = 1; i < sheetCount; i++) {
            loanCoverUserRequest.setPageSize(defaultRowMaxCount);
            loanCoverUserRequest.setCurrPage(i+1);
            LoanCoverUserResponse loanCoverUserResponse2 = loanCoverService.selectUserMemberList(loanCoverUserRequest);
            if (loanCoverUserResponse2 != null && loanCoverUserResponse2.getResultList().size()> 0) {
                sheetNameTmp = sheetName + "_第" + (i + 1) + "页";
                helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter,  loanCoverUserResponse2.getResultList());
            } else {
                break;
            }
        }
        DataSet2ExcelSXSSFHelper.write2Response(request, response, fileName, workbook);
    }

    private Map<String, String> buildMap() {
        Map<String, String> map = Maps.newLinkedHashMap();
        map.put("mobile", "手机号");
        map.put("name", "名称");
        map.put("idNo", "证件号");
        map.put("idType", "用户类型");
        map.put("email", "邮箱");
        map.put("customerId", "客户编号");
        map.put("status", "状态");
        map.put("code", "状态码");
        map.put("createTime", "添加时间");
        map.put("updateTime", "申请时间");
        return map;
    }

    private Map<String, IValueFormatter> buildValueAdapter() {
        Map<String, IValueFormatter> mapAdapter = Maps.newHashMap();
        IValueFormatter idTypeAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                Integer idType = (Integer) object;
                if (idType == 0) {
                    return ("个人");
                } else {
                    return ("企业");
                }
            }
        };

        IValueFormatter statusAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                String status = (String) object;
                if(status ==null){
                    status = ("未认证");
                } else if ("success".equals(status)) {
                    status = ("认证成功");
                } else if ("error".equals(status)) {
                    status = ("认证失败");
                } else {
                    status = (status);
                }
                return status;
            }
        };

        IValueFormatter createTimeAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date createTime = (Date) object;
                String strCreateTime = format.format(createTime);
                return strCreateTime;
            }
        };

        IValueFormatter updateTimeAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date updateTime = (Date) object;
                String strUpdateTime = format.format(updateTime);
                return strUpdateTime;
            }
        };

        mapAdapter.put("idType", idTypeAdapter);
        mapAdapter.put("status", statusAdapter);
        mapAdapter.put("createTime", createTimeAdapter);
        mapAdapter.put("updateTime", updateTimeAdapter);
        return mapAdapter;
    }

    //认证
    @ApiOperation(value = "借款盖章用户认证", notes = "借款盖章用户认证")
    @PostMapping(value = "/shareUser")
    @ResponseBody
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_ADD)
    public AdminResult shareUser(HttpServletRequest request, HttpServletResponse response, @RequestBody String loanId) {
        LoanCoverUserResponse loanCoverUserResponse = loanCoverService.getLoanCoverUserById(loanId);
        if (null != loanCoverUserResponse) {
            LoanCoverUserVO ma = loanCoverUserResponse.getResult();
            CertificateAuthorityVO certificateAuthorityVO = loanCoverService.selectCertificateAuthorityByIdNoName(ma.getIdNo(), ma.getName());
            LoanCoverUserRequest loanCoverUserRequest = new LoanCoverUserRequest();
            if (null != certificateAuthorityVO) {
                ma.setCode("1000");
                ma.setCustomerId(certificateAuthorityVO.getCustomerId());
                ma.setStatus("success");
                ma.setUpdateTime(new Date());
                BeanUtils.copyProperties(ma, loanCoverUserRequest);
                loanCoverService.updateLoanCoverUserRecord(loanCoverUserRequest);
                return new AdminResult<>();
            }
            // 法大大开始
            DzqzCallBean bean = new DzqzCallBean();
            bean.setUserId(0);
            bean.setLogordid("0");
            /*bean.setApp_id(DzqzConstant.HYJF_FDD_APP_ID);
            bean.setV(DzqzConstant.HYJF_FDD_VERSION);*/
            bean.setApp_id(systemConfig.getFaaAppUrl());
            bean.setV(systemConfig.getFddVersion());
            bean.setSecret(systemConfig.getFddSecret());
            bean.setUrl(systemConfig.getFddUrl());

            bean.setTimestamp(GetDate.getDate("yyyyMMddHHmmss"));
            bean.setCustomer_name(ma.getName());// 客户姓名
            bean.setEmail(ma.getEmail());// 电子邮箱
            bean.setIdCard(ma.getIdNo());// 组织机构代码
            bean.setMobile(ma.getMobile());// 手机号
            if (ma.getIdType() == 0) {
                bean.setIdent_type("0");// 证件类型
                bean.setTxCode("syncPerson_auto");
            } else {
                bean.setTxCode("syncCompany_auto");
            }
            // 调用接口
            DzqzCallBean resultt = DzqzCallUtil.callApiBg(bean);
            logger.info("法大大返回报文" + JSONObject.toJSON(resultt));
            if (null!=resultt) {
                logger.info("CA认证成功:用户ID:[" + ma.getName() + "].");
                if ("success".equals(resultt.getResult())) {
                    ma.setCode(resultt.getCode());
                    ma.setCustomerId(resultt.getCustomer_id());
                    ma.setStatus("success");
                    ma.setUpdateTime(new Date());
                    BeanUtils.copyProperties(ma, loanCoverUserRequest);
                    loanCoverService.updateLoanCoverUserRecord(loanCoverUserRequest);
                    return new AdminResult<>();
                } else {
                    ma.setCode(resultt.getCode());
                    ma.setStatus("error");
                    ma.setUpdateTime(new Date());
                    BeanUtils.copyProperties(ma, loanCoverUserRequest);
                    loanCoverService.updateLoanCoverUserRecord(loanCoverUserRequest);
                    return new AdminResult<>(FAIL, resultt.getMsg());
                }
            }
            return new AdminResult<>(FAIL, "请求法大大失败");
        }
        return new AdminResult<>(FAIL, "没有查找到借款盖章用户");
    }
}
