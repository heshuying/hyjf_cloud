/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.user;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.beans.request.AdminUserRecommendRequestBean;
import com.hyjf.admin.beans.request.UserManagerRequestBean;
import com.hyjf.admin.beans.request.UserManagerUpdateRequestBean;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ExportExcel;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.UserCenterService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.user.UserManagerResponse;
import com.hyjf.am.resquest.user.AdminUserRecommendRequest;
import com.hyjf.am.resquest.user.UserChangeLogRequest;
import com.hyjf.am.resquest.user.UserManagerRequest;
import com.hyjf.am.resquest.user.UserManagerUpdateRequest;
import com.hyjf.am.vo.config.AdminSystemVO;
import com.hyjf.am.vo.trade.CorpOpenAccountRecordVO;
import com.hyjf.am.vo.user.*;
import com.hyjf.common.util.AsteriskProcessUtil;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.StringPool;
import com.hyjf.common.validator.Validator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author nxl
 * @version UserCenterController, v0.1 2018/6/19 15:08
 */

@Api(value = "会员管理接口")
@RestController
@RequestMapping("/hyjf-admin/usersManager")
public class UserCenterController extends BaseController {

    public static final String PERMISSIONS = "userslist";
    private static final Integer CHANGELOG_TYPE_RECOMMEND = 1;
    @Autowired
    private UserCenterService userCenterService;

    @ApiOperation(value = "会员管理", notes = "会员管理页面初始化")
    @PostMapping(value = "/usersInit")
    @ResponseBody
    public JSONObject userManagerInit() {
        JSONObject jsonObject =userCenterService.initUserManaget();
        return jsonObject;

    }

    //会员管理列表查询
    @ApiOperation(value = "会员管理", notes = "会员管理列表查询")
    @PostMapping(value = "/userslist")
    @ResponseBody
    public AdminResult<ListResult<UserManagerVO>> getUserslist(HttpServletRequest request, HttpServletResponse response, @RequestBody UserManagerRequestBean userManagerRequestBean) {
        UserManagerRequest managerRequest = new UserManagerRequest();
        BeanUtils.copyProperties(userManagerRequestBean,managerRequest);
        UserManagerResponse userManagerResponse = userCenterService.selectUserMemberList(managerRequest);
        if(userManagerResponse==null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(userManagerResponse)) {
            return new AdminResult<>(FAIL, userManagerResponse.getMessage());
        }
        return new AdminResult<ListResult<UserManagerVO>>(ListResult.build(userManagerResponse.getResultList(), userManagerResponse.getCount())) ;
    }

    //会员详情
    @ApiOperation(value = "会员管理", notes = "会员详情")
    @PostMapping(value = "/getUserdetail")
    @ResponseBody
    public JSONObject getUserdetail(HttpServletRequest request, HttpServletResponse response, @RequestBody String userId) {
        JSONObject jsonObject = new JSONObject();
        UserManagerDetailVO userManagerDetailVO = userCenterService.selectUserDetail(userId);
        jsonObject.put("userDetailInfo", userManagerDetailVO);
        //vip

        // 获取测评信息
        UserEvalationResultVO userEvalationResultInfo = userCenterService.getUserEvalationResult(userId);
        if (null != userEvalationResultInfo && null != userEvalationResultInfo.getCreateTime()) {
            //获取评测时间加一年的毫秒数18.2.2评测 19.2.2
            Long lCreate = GetDate.countDate(userEvalationResultInfo.getCreateTime(), 1, 1).getTime();
            //获取当前时间加一天的毫秒数 19.2.1以后需要再评测19.2.2
            Long lNow = GetDate.countDate(new Date(), 5, 1).getTime();
            if (lCreate <= lNow) {
                //已过期需要重新评测2已过期、1有效
                jsonObject.put("isEvalation", "2");
            } else {
                jsonObject.put("isEvalation", "1");
            }
        }
        jsonObject.put("userEvalationResult", userEvalationResultInfo);
        //用户开户信息
        UserBankOpenAccountVO userBankOpenAccountVO = userCenterService.selectBankOpenAccountByUserId(userId);
        jsonObject.put("userBankOpenAccount", userBankOpenAccountVO);
        //公司信息
        CorpOpenAccountRecordVO corpOpenAccountRecordVO = userCenterService.selectCorpOpenAccountRecordByUserId(userId);
        jsonObject.put("enterpriseInformation", corpOpenAccountRecordVO);
        //第三方平台绑定信息
        BindUserVo bindUserVo = userCenterService.selectBindeUserByUserI(userId);
        jsonObject.put("bindUsers", bindUserVo);
        //电子签章
        CertificateAuthorityVO certificateAuthorityVO = userCenterService.selectCertificateAuthorityByUserId(userId);
        jsonObject.put("certificateAuthority", certificateAuthorityVO);
        //文件服务器

        //
        return jsonObject;
    }


    @ApiOperation(value = "会员管理", notes = "获取用户编辑初始信息")
//    @RequestMapping(value = "/initUserUpdate")
    @PostMapping(value = "/initUserUpdate")
    @ResponseBody
    public JSONObject initUserUpdate(HttpServletRequest request, HttpServletResponse response, @RequestBody String userId) {

        String status = Response.FAIL;
        JSONObject jsonObject = new JSONObject();
        // 用户角色
        /*Map<String, String> userRoles = CacheUtil.getParamNameMap("USER_ROLE");
        // 用户状态
        Map<String, String> userStatus = CacheUtil.getParamNameMap("USER_STATUS");
        //借款人类型
        Map<String, String> borrowerType = CacheUtil.getParamNameMap("BORROWER_TYPE");
        jsonObject.put("userRoles", userRoles);
        jsonObject.put("userStatus", userStatus);
        jsonObject.put("borrowerType", borrowerType);*/

        // 根据用户id查询用户详情信息
        UserManagerUpdateVO userManagerUpdateVo = userCenterService.selectUserUpdateInfoByUserId(userId);

        if(null!=userManagerUpdateVo){
            status = Response.SUCCESS;
            jsonObject.put("usersUpdateForm", userManagerUpdateVo);
        }
        // 加载修改日志 userChageLog
        UserChangeLogRequest userChangeLogRequest = new UserChangeLogRequest();
        if(StringUtils.isNotEmpty(userId)){
            int intUserId = Integer.parseInt(userId);
            userChangeLogRequest.setUserId(intUserId);
            userChangeLogRequest.setChangeType(CHANGELOG_TYPE_RECOMMEND);
            List<UserChangeLogVO> userChangeLogVOList = userCenterService.selectUserChageLog(userChangeLogRequest);
            jsonObject.put("usersChangeLogForm", userChangeLogVOList);
        }
        jsonObject.put("status", status);
        return jsonObject;
    }

    @ApiOperation(value = "修改更新用户信息", notes = "会员管理")
    @PostMapping(value = "/updateUser")
    @ResponseBody
    public JSONObject updataUserInfo(HttpServletRequest request, HttpServletResponse response, @RequestBody UserManagerUpdateRequestBean requestBean) {
        JSONObject jsonObject = new JSONObject();
        UserManagerUpdateRequest userRequest = new UserManagerUpdateRequest();
        BeanUtils.copyProperties(requestBean, request);
        AdminSystemVO adminSystemVO = this.getUser(request);
        requestBean.setLoginUserName(adminSystemVO.getUsername());
        requestBean.setLogingUserId(adminSystemVO.getId());
        //1代表更新成功，0为失败
        int intUpdFlg = userCenterService.updataUserInfo(userRequest);
        //todo 修改手机号后 发送更新客户信息
        if (intUpdFlg == 1) {
            jsonObject.put("status", Response.SUCCESS);
        } else {
            jsonObject.put("status", Response.FAIL);
        }
        return jsonObject;
    }

    @ApiOperation(value = "会员管理", notes = "获取推荐人信息")
    @PostMapping(value = "/initModifyre")
    @ResponseBody
    public JSONObject initModifyre(HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, String> map) {
        JSONObject jsonObject = new JSONObject();
        String userIdStr = map.get("userId");
        //推荐人信息
        UserRecommendCustomizeVO userRecommendVO = userCenterService.selectUserRecommendByUserId(userIdStr);
        jsonObject.put("modifyReForm", userRecommendVO);
        //加载修改日志 userChageLog
        UserChangeLogRequest userChangeLogRequest = new UserChangeLogRequest();
        if(StringUtils.isNotEmpty(userIdStr)){
            int intUserId = Integer.parseInt(userIdStr);
            userChangeLogRequest.setUserId(intUserId);
            List<UserChangeLogVO> userChangeLogVOList = userCenterService.selectUserChageLog(userChangeLogRequest);
            jsonObject.put("usersChangeLogForm", userChangeLogVOList);
        }

        return jsonObject;
    }

    @ApiOperation(value = "会员管理", notes = "修改用户推荐人")
    @RequestMapping(value = "/updateModifyre")
    @ResponseBody
    public AdminResult updateRe(HttpServletRequest request, HttpServletResponse response, @RequestBody AdminUserRecommendRequestBean requestBean) {
        AdminUserRecommendRequest adminUserRecommendRequest = new AdminUserRecommendRequest();
        BeanUtils.copyProperties(requestBean, adminUserRecommendRequest);
        AdminSystemVO adminSystemVO = this.getUser(request);
        if(null!=adminSystemVO){
            //当前登陆用户
            adminUserRecommendRequest.setLoginUserId(adminSystemVO.getId());
            adminUserRecommendRequest.setLoginUserName(adminSystemVO.getUsername());
        }
        int updFlg = userCenterService.updateUserRecommend(adminUserRecommendRequest);
        if(updFlg<=0){
            return new AdminResult<>(FAIL, "修改用户推荐人失败");
        }
        return new AdminResult<>();
    }

    /**
     * 获取用户身份证信息
     *
     * @param request
     * @return 进入用户详情页面
     */
    @ApiOperation(value = "会员管理", notes = "获取用户身份证信息")
    @RequestMapping(value = "/searchIdCard")
    @ResponseBody
    public JSONObject searchIdCard(HttpServletRequest request, HttpServletResponse response, @RequestBody String userId) {
        JSONObject jsonObject = new JSONObject();
        //根据用户id查询用户详情信息
        UserRecommendCustomizeVO userRecommendVO = userCenterService.selectUserRecommendByUserId(userId);
        jsonObject.put("modifyReForm", userRecommendVO);
        //加载修改日志 userChageLog
        UserChangeLogRequest userChangeLogRequest = new UserChangeLogRequest();
        if(StringUtils.isNotEmpty(userId)){
            int intUserId = Integer.parseInt(userId);
            userChangeLogRequest.setUserId(intUserId);
            List<UserChangeLogVO> userChangeLogVOList = userCenterService.selectUserChageLog(userChangeLogRequest);
            jsonObject.put("usersChangeLogForm", userChangeLogVOList);
        }
        return jsonObject;
    }

    /**
     * 修改用户身份证
     *
     * @param request
     * @return 进入用户详情页面
     */
    @ApiOperation(value = "会员管理", notes = "修改用户身份证")
    @RequestMapping(value = "/modifyIdCard")
    @ResponseBody
    public AdminResult modifyIdCard(HttpServletRequest request, HttpServletResponse response, @RequestBody AdminUserRecommendRequestBean requestBean) {
        AdminUserRecommendRequest adminUserRecommendRequest = new AdminUserRecommendRequest();
        BeanUtils.copyProperties(requestBean, adminUserRecommendRequest);
        AdminSystemVO adminSystemVO = this.getUser(request);
        if(null!=adminSystemVO){
            //当前登陆用户
            adminUserRecommendRequest.setLoginUserId(adminSystemVO.getId());
            adminUserRecommendRequest.setLoginUserName(adminSystemVO.getUsername());
        }
        int updFlg = userCenterService.updateUserIdCard(adminUserRecommendRequest);
        if(updFlg<=0){
            return new AdminResult<>(FAIL, "修改用户身份证失败");
        }
        //todo 修改身份证号后 发送更新客户信息MQ
        return new AdminResult<>();
    }

    /**
     * 检查手机号码或用户名唯一性
     *
     * @param request
     * @return
     */
    @PostMapping(value = "/checkReAction")
    @ResponseBody
    @ApiOperation(value = "会员管理", notes = "校验推荐人")
    public JSONObject checkReAction(HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, String> map) {
        JSONObject jsonObject = new JSONObject();
        String userId = map.get("userId");
        String param = map.get("userName");
        String flg =  Response.SUCCESS;
        String msg = "";
        //校验推荐人
        if (Validator.isNotNull(userId)) {
            if (StringUtils.isNotEmpty(param)) {
                int recomFlag = userCenterService.checkRecommend(userId, param);
                if (recomFlag == 2) {// 推荐人不能是自己
                    msg = "不能将推荐人设置为自己";
                    flg =  Response.FAIL;
                } else if (recomFlag == 1) {// 推荐人不存在
                    msg = "推荐人不存在";
                    flg =  Response.FAIL;
                }
            }
        }
        jsonObject.put("info", msg);
        jsonObject.put("status", flg);
        return jsonObject;
    }



    /**
     * 检查手机号码或用户名唯一性
     *
     * @param request
     * @return
     */
    @PostMapping(value = "/checkAction")
    @ResponseBody
    @ApiOperation(value = "会员管理", notes = "校验手机号")
    public JSONObject checkAction(HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, String> map) {
        JSONObject jsonObject = new JSONObject();
        String userId = map.get("userId");
        String param = map.get("mobile");
        String flg = Response.SUCCESS;
        String msg = "";
        // 检查手机号码唯一性
        int cnt = userCenterService.countUserByMobile(Integer.parseInt(userId), param);
        if (cnt > 0) {
            msg = "手机号已经存在！";
            flg =  Response.FAIL;
        }
        jsonObject.put("info", msg);
        jsonObject.put("status", flg);
        return jsonObject;
    }

    /**
     * 根据业务需求导出相应的表格 此处暂时为可用情况 缺陷： 1.无法指定相应的列的顺序， 2.无法配置，excel文件名，excel sheet名称
     * 3.目前只能导出一个sheet 4.列的宽度的自适应，中文存在一定问题
     * 5.根据导出的业务需求最好可以在导出的时候输入起止页码，因为在大数据量的情况下容易造成卡顿
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/exportusers")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response,@RequestBody UserManagerRequestBean userManagerRequestBean) throws Exception {
        // 表格sheet名称
        String sheetName = "会员列表";
        // 文件名称
        String fileName = sheetName + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + ".xls";
        String[] titles = new String[] { "序号", "分公司", "分部", "团队", "用户来源", "用户名", "姓名", "性别", "年龄", "生日","身份证号", "户籍所在地", "手机号码", "会员类型", "用户角色", "用户属性", "推荐人", "51老用户", "用户状态","银行开户状态","银行开户时间","汇付开户状态", "注册平台", "注册时间", "注册所在地" };
        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格
        HSSFSheet sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles, sheetName + "_第1页");
        UserManagerRequest managerRequest = new UserManagerRequest();
        BeanUtils.copyProperties(userManagerRequestBean,managerRequest);
        UserManagerResponse userManagerResponse = userCenterService.selectUserMemberList(managerRequest);
        if(null!=userManagerResponse){
            List<UserManagerVO>recordList = userManagerResponse.getResultList();
            if (recordList != null && recordList.size() > 0) {
                int sheetCount = 1;
                int rowNum = 0;
                for (int i = 0; i < recordList.size(); i++) {
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
                        UserManagerVO user = recordList.get(i);
                        // 创建相应的单元格
                        Cell cell = row.createCell(celLength);
                        if (celLength == 0) {// 序号
                            cell.setCellValue(i + 1);
                        } else if (celLength == 1) { // 大区
                            cell.setCellValue(user.getRegionName());
                        } else if (celLength == 2) { // 分公司
                            cell.setCellValue(user.getBranchName());
                        } else if (celLength == 3) { // 团队
                            cell.setCellValue(user.getDepartmentName());
                        } else if (celLength == 4) { // 用户来源
                            cell.setCellValue(user.getInstName());
                        } else if (celLength == 5) {// 用户名
                            cell.setCellValue(user.getUserName());
                        } else if (celLength == 6) {// 姓名
                            cell.setCellValue(user.getRealName());
                        } else if (celLength == 7) {// 性别
                            if ("1".equals(user.getSex())) {
                                cell.setCellValue("男");
                            } else if ("2".equals(user.getSex())) {
                                cell.setCellValue("女");
                            } else {
                                cell.setCellValue("未知");
                            }
                        } else if (celLength == 8) {// 年龄
                            cell.setCellValue(this.getAge(user.getBirthday()));
                        } else if (celLength == 9) {// 生日
                            cell.setCellValue(user.getBirthday());
                        } else if (celLength == 10) {// 身份证号
                            cell.setCellValue(AsteriskProcessUtil.getAsteriskedValue(user.getIdcard(),7));
                        } else if (celLength == 11) {// 户籍所在地
//                            cell.setCellValue(usersService.getAreaByIdCard(user.getIdcard()));
                        } else if (celLength == 12) {// 手机号码
                            cell.setCellValue(AsteriskProcessUtil.getAsteriskedValue(user.getMobile(),3));
                        } else if (celLength == 13) {// 会员类型
//                            cell.setCellValue(user.getVipType());
                        } else if (celLength == 14) {// 用户角色
                            cell.setCellValue(user.getUserRole());
                        } else if (celLength == 15) {// 用户属性
                            cell.setCellValue(user.getUserProperty());
                        } else if (celLength == 16) {// 推荐人
                            cell.setCellValue(user.getRecommendName());
                        } else if (celLength == 17) {// 51老用户
//                            cell.setCellValue(user.getIs51());
                        } else if (celLength == 18) {// 用户状态
                            cell.setCellValue(user.getUserStatus());
                        } else if (celLength == 19) {// 银行开户状态
                            cell.setCellValue("1".equals(user.getBankOpenAccount())?"已开户":"未开户");
                        } else if (celLength == 20) {// 银行开户时间
                            cell.setCellValue(user.getBankOpenTime());
                        } else if (celLength == 21) {// 开户状态
                            cell.setCellValue("1".equals(user.getOpenAccount())?"已开户":"未开户");
                        } else if (celLength == 22) {// 注册平台
                            cell.setCellValue(user.getRegistPlat());
                        } else if (celLength == 23) {// 注册时间
                            cell.setCellValue(user.getRegTime());
                        } else if (celLength == 24) {// 注册所在地
                            cell.setCellValue(user.getRegistArea());
                        }
                    }
                }
            }
        }
         // 导出
        ExportExcel.writeExcelFile(response, workbook, titles, fileName);
    }

    public String getAge(String birthday) {
        if (StringUtils.isBlank(birthday)) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        Date date = new Date();
        String formatDate = sdf.format(date);
        int age = Integer.parseInt(formatDate) - Integer.parseInt(birthday.substring(0, 4));
        return String.valueOf(age);
    }
    /**
     * 企业用户信息补录
     *
     * @param request
     * @param response
     * @return
     */

    @PostMapping(value = "/initCompanyInfo")
    @ResponseBody
    @ApiOperation(value = "会员管理", notes = "初始化企业用户信息")
    public JSONObject insertCompanyInfo(HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, String> map) {
        JSONObject jsonObject = new JSONObject();
        String userId = map.get("userId");
        if (StringUtils.isNotBlank(userId)) {
            UserVO user = userCenterService.selectUserByUserId(userId);
            UserBankOpenAccountVO userBankOpenAccountVO = userCenterService.selectBankOpenAccountByUserId(userId);
            CompanyInfoVO companyInfo = userCenterService.selectCompanyInfoByUserId(userId);
            jsonObject.put("bankOpenAccount", userBankOpenAccountVO);
            if (companyInfo != null) {
                jsonObject.put("companyInfo", companyInfo);
            }
            jsonObject.put("user", user);
            return jsonObject;
        }
        return jsonObject;
    }

    /**
     * 查询企业开户信息
     *
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/serchCompanyInfo")
    @ApiOperation(value = "会员管理", notes = "查询企业开户信息")
    public JSONObject serchCompanyInfo(@RequestBody Map<String, String> map) {
        JSONObject ret = new JSONObject();
        String accountId = map.get("accountId");
        String userId = map.get("userId");
        if (StringUtils.isBlank(userId)) {
            ret.put("status", "error");
            ret.put("result", "请先选择用户再进行操作!");
            return ret;
        }
        if (StringUtils.isBlank(accountId)) {
            ret.put("status", "error");
            ret.put("result", "请输入正确的电子账号!");
            return ret;
        }
        //根据accountid调用接口查找企业信息
        if (StringUtils.isNotEmpty(userId)) {
            int intUserId = Integer.parseInt(userId);
            CompanyInfoVO infoVO = userCenterService.queryCompanyInfoByAccoutnId(intUserId, accountId);
            if (null != infoVO) {
                ret.put("status", "error");
                ret.put("result", "请输入正确的电子账号!");
                return ret;
            }
            ret.put("status", "success");
            ret.put("result", "查询成功!");
            ret.put("company", infoVO);
            UserVO userVO = userCenterService.selectUserByUserId(userId);
            Integer bankFlag = userVO.getBankOpenAccount();
            ret.put("isOpenAccount", bankFlag);
        }
        return ret;
    }

    /**
     * 保存企业开户信息
     *
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/saveCompanyInfo")
    @ApiOperation(value = "会员管理", notes = "保存企业开户信息")
    public JSONObject saveCompanyInfo(@RequestBody Map<String, String> map) {
        JSONObject ret = new JSONObject();
        ret = userCenterService.saveCompanyInfo(map);
        return ret;
    }

}
