/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.user;

import com.hyjf.admin.beans.request.AdminUserRecommendRequestBean;
import com.hyjf.admin.beans.request.CompanyInfoInstRequesetBean;
import com.hyjf.admin.beans.request.UserManagerRequestBean;
import com.hyjf.admin.beans.request.UserManagerUpdateRequestBean;
import com.hyjf.admin.beans.response.*;
import com.hyjf.admin.beans.vo.*;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ExportExcel;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.UserCenterService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.user.UserManagerResponse;
import com.hyjf.am.resquest.user.*;
import com.hyjf.am.vo.config.AdminSystemVO;
import com.hyjf.am.vo.trade.CorpOpenAccountRecordVO;
import com.hyjf.am.vo.user.*;
import com.hyjf.common.util.*;
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
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author nxl
 * @version UserCenterController, v0.1 2018/6/19 15:08
 */

@Api(value = "会员中心-会员管理接口",tags = "会员中心-会员管理接口")
@RestController
@RequestMapping("/hyjf-admin/usersManager")
public class UserCenterController extends BaseController {

    public static final String PERMISSIONS = "userslist";
//    private static final Integer CHANGELOG_TYPE_RECOMMEND = 1;
    @Autowired
    private UserCenterService userCenterService;

    @ApiOperation(value = "会员管理页面初始化(下拉列表)", notes = "会员管理页面初始化")
    @PostMapping(value = "/usersInit")
    @ResponseBody
    public  AdminResult<UserManagerInitResponseBean>  userManagerInit() {
        UserManagerInitResponseBean userManagerInitResponseBean =userCenterService.initUserManaget();
        return new AdminResult<UserManagerInitResponseBean>(userManagerInitResponseBean);

    }

    //会员管理列表查询
    @ApiOperation(value = "会员管理列表查询", notes = "会员管理列表查询")
    @PostMapping(value = "/userslist")
    @ResponseBody
    public AdminResult<ListResult<UserManagerCustomizeVO>> getUserslist(@RequestBody UserManagerRequestBean userManagerRequestBean) {
        UserManagerRequest managerRequest = new UserManagerRequest();
        BeanUtils.copyProperties(userManagerRequestBean,managerRequest);
        UserManagerResponse userManagerResponse = userCenterService.selectUserMemberList(managerRequest);
        if(userManagerResponse==null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        List<UserManagerVO> listUserManagetVO = userManagerResponse.getResultList();
        List<UserManagerCustomizeVO> userManagerCustomizeList = new ArrayList<UserManagerCustomizeVO>();
        if(null!=listUserManagetVO&&listUserManagetVO.size()>0){
            userManagerCustomizeList = CommonUtils.convertBeanList(listUserManagetVO, UserManagerCustomizeVO.class);
        }
        if (!Response.isSuccess(userManagerResponse)) {
            return new AdminResult<>(FAIL, userManagerResponse.getMessage());
        }
        return new AdminResult<ListResult<UserManagerCustomizeVO>>(ListResult.build(userManagerCustomizeList,userManagerResponse.getCount())) ;
    }

    //会员详情
    @ApiOperation(value = "会员详情", notes = "会员详情")
    @PostMapping(value = "/getUserdetail")
    @ResponseBody
    public  AdminResult<UserDetailInfoResponseBean>  getUserdetail(@RequestBody String userId) {
        UserDetailInfoResponseBean userDetailInfoResponseBean = new UserDetailInfoResponseBean();
        UserManagerDetailVO userManagerDetailVO = userCenterService.selectUserDetail(userId);
        UserManagerDetailCustomizeVO userManagerDetailCustomizeVO = new UserManagerDetailCustomizeVO();
        if(null!=userManagerDetailVO){
            BeanUtils.copyProperties(userManagerDetailVO, userManagerDetailCustomizeVO);
        }
        userDetailInfoResponseBean.setUserManagerDetailVO(userManagerDetailCustomizeVO);
        //todo vip user 表里没有vip字段

        // 获取测评信息
        UserEvalationResultVO userEvalationResultInfo = userCenterService.getUserEvalationResult(userId);
        UserEvalationResultShowVO userEvalationResultShowVO = new UserEvalationResultShowVO();
        if (null != userEvalationResultInfo && null != userEvalationResultInfo.getCreateTime()) {
            //获取评测时间加一年的毫秒数18.2.2评测 19.2.2
            Long lCreate = GetDate.countDate(userEvalationResultInfo.getCreateTime(), 1, 1).getTime();
            //获取当前时间加一天的毫秒数 19.2.1以后需要再评测19.2.2
            Long lNow = GetDate.countDate(new Date(), 5, 1).getTime();
            if (lCreate <= lNow) {
                //已过期需要重新评测2已过期、1有效
                userDetailInfoResponseBean.setIsEvalation("2");
            } else {
                userDetailInfoResponseBean.setIsEvalation("1");
            }
            BeanUtils.copyProperties(userEvalationResultInfo, userEvalationResultShowVO);
        }
        userDetailInfoResponseBean.setUserEvalationResultInfo(userEvalationResultShowVO);
        //用户开户信息
        UserBankOpenAccountVO userBankOpenAccountVO = userCenterService.selectBankOpenAccountByUserId(userId);
        UserBankOpenAccountCustomizeVO userBankOpenAccountCustomizeVO = new UserBankOpenAccountCustomizeVO();
        if(null!=userBankOpenAccountVO){
            BeanUtils.copyProperties(userBankOpenAccountVO, userBankOpenAccountCustomizeVO);
        }
        userDetailInfoResponseBean.setUserBankOpenAccountVO(userBankOpenAccountCustomizeVO);
        //公司信息
        CorpOpenAccountRecordVO corpOpenAccountRecordVO = userCenterService.selectCorpOpenAccountRecordByUserId(userId);
        CorpOpenAccountRecordCustomizeVO corpOpenAccountRecordCustomizeVO = new CorpOpenAccountRecordCustomizeVO();
        if(null!=corpOpenAccountRecordVO){
            BeanUtils.copyProperties(corpOpenAccountRecordVO, corpOpenAccountRecordCustomizeVO);
        }
        userDetailInfoResponseBean.setEnterpriseInformation(corpOpenAccountRecordCustomizeVO);
        //第三方平台绑定信息
        BindUserVo bindUserVo = userCenterService.selectBindeUserByUserI(userId);
        BindUserCustomizeVO bindUserCustomizeVO = new BindUserCustomizeVO();
        if(null!=bindUserVo){
            BeanUtils.copyProperties(bindUserVo,bindUserCustomizeVO);
        }
        userDetailInfoResponseBean.setBindUserVo(bindUserCustomizeVO);
        //电子签章
        CertificateAuthorityVO certificateAuthorityVO = userCenterService.selectCertificateAuthorityByUserId(userId);
        CertificateAuthorityCustomizeVO certificateAuthorityCustomizeVO = new CertificateAuthorityCustomizeVO();
        if(null!=certificateAuthorityVO){
            BeanUtils.copyProperties(certificateAuthorityVO, certificateAuthorityCustomizeVO);
        }
        userDetailInfoResponseBean.setCertificateAuthorityVO(certificateAuthorityCustomizeVO);
        // todo 文件服务器

        return new AdminResult<UserDetailInfoResponseBean>(userDetailInfoResponseBean);
    }


    @ApiOperation(value = "获取用户编辑初始信息", notes = "获取用户编辑初始信息")
    @PostMapping(value = "/initUserUpdate")
    @ResponseBody
    public AdminResult<InitUserUpdResponseBean> initUserUpdate(@RequestBody String userId) {

        InitUserUpdResponseBean initUserUpdResponseBean = new InitUserUpdResponseBean();
        // 根据用户id查询用户详情信息
        UserManagerUpdateVO userManagerUpdateVo = userCenterService.selectUserUpdateInfoByUserId(userId);
        UserManagerUpdateCustomizeVO userManagerUpdateCustomizeVO = new UserManagerUpdateCustomizeVO();
        if(null!=userManagerUpdateVo){
            BeanUtils.copyProperties(userManagerUpdateVo, userManagerUpdateCustomizeVO);
        }
        initUserUpdResponseBean.setUsersUpdateForm(userManagerUpdateCustomizeVO);
        // 加载修改日志 userChageLog
        UserChangeLogRequest userChangeLogRequest = new UserChangeLogRequest();
        if(StringUtils.isNotEmpty(userId)){
            int intUserId = Integer.parseInt(userId);
            userChangeLogRequest.setUserId(intUserId);
            userChangeLogRequest.setChangeType(2);
            List<UserChangeLogVO> userChangeLogVOList = userCenterService.selectUserChageLog(userChangeLogRequest);
            List<UserChangeLogCustomizeVO> userChangeLogCustomizeVOList = new ArrayList<UserChangeLogCustomizeVO>();
            if(null!=userChangeLogVOList&&userChangeLogVOList.size()>0){
                userChangeLogCustomizeVOList = CommonUtils.convertBeanList(userChangeLogVOList, UserChangeLogCustomizeVO.class);
            }
            initUserUpdResponseBean.setUsersChangeLogForm(userChangeLogCustomizeVOList);
        }
        return new AdminResult<InitUserUpdResponseBean>(initUserUpdResponseBean);
    }

    @ApiOperation(value = "修改更新用户信息", notes = "修改更新用户信息")
    @PostMapping(value = "/updateUser")
    @ResponseBody
    public AdminResult updataUserInfo(HttpServletRequest request, @RequestBody UserManagerUpdateRequestBean requestBean) {
        UserManagerUpdateRequest userRequest = new UserManagerUpdateRequest();
        BeanUtils.copyProperties(requestBean, userRequest);
        AdminSystemVO adminSystemVO = this.getUser(request);
        userRequest.setLoginUserName(adminSystemVO.getUsername());
        userRequest.setLogingUserId(adminSystemVO.getId());
        //1代表更新成功，0为失败
        int intUpdFlg = userCenterService.updataUserInfo(userRequest);
        //todo 修改手机号后 发送更新客户信息

        if (intUpdFlg<=0) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        return new AdminResult<>();
    }

    @ApiOperation(value = "获取推荐人信息", notes = "获取推荐人信息")
    @PostMapping(value = "/initModifyre")
    @ResponseBody
    public AdminResult<InitModifyreResponseBean> initModifyre(@RequestBody String userId) {
        InitModifyreResponseBean initModifyreResponseBean = new InitModifyreResponseBean();
        //推荐人信息
        UserRecommendCustomizeVO userRecommendVO = userCenterService.selectUserRecommendByUserId(userId);
        UserRecommendCustomizeShowVO userRecommendCustomizeShowVO = new UserRecommendCustomizeShowVO();
        if(null!=userRecommendVO){
            BeanUtils.copyProperties(userRecommendVO,userRecommendCustomizeShowVO);
        }
        initModifyreResponseBean.setModifyReForm(userRecommendCustomizeShowVO);
        //加载修改日志 userChageLog
        UserChangeLogRequest userChangeLogRequest = new UserChangeLogRequest();
        if(StringUtils.isNotEmpty(userId)){
            int intUserId = Integer.parseInt(userId);
            userChangeLogRequest.setUserId(intUserId);
            //修改类型 2用户信息修改  1推荐人修改
            userChangeLogRequest.setChangeType(1);
            List<UserChangeLogVO> userChangeLogVOList = userCenterService.selectUserChageLog(userChangeLogRequest);
            List<UserChangeLogCustomizeVO> userChangeLogCustomizeVOList = new ArrayList<UserChangeLogCustomizeVO>();
            if(null!=userChangeLogVOList&&userChangeLogVOList.size()>0){
                userChangeLogCustomizeVOList = CommonUtils.convertBeanList(userChangeLogVOList, UserChangeLogCustomizeVO.class);
            }
            initModifyreResponseBean.setUsersChangeLogForm(userChangeLogCustomizeVOList);
        }
        return new AdminResult<InitModifyreResponseBean>(initModifyreResponseBean);
    }

    @ApiOperation(value = "修改用户推荐人", notes = "修改用户推荐人")
    @PostMapping(value = "/updateModifyre")
    @ResponseBody
    public AdminResult updateRe(HttpServletRequest request, @RequestBody AdminUserRecommendRequestBean requestBean) {
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
    @ApiOperation(value = "获取用户身份证信息", notes = "获取用户身份证信息")
    @PostMapping(value = "/searchIdCard")
    @ResponseBody
    public  AdminResult<InitModifyreResponseBean> searchIdCard(@RequestBody String userId) {
        InitModifyreResponseBean initModifyreResponseBean = new InitModifyreResponseBean();
        //根据用户id查询用户详情信息
        UserRecommendCustomizeShowVO userRecommendCustomizeShowVO = new UserRecommendCustomizeShowVO();
        UserRecommendCustomizeVO userRecommendVO = userCenterService.selectUserRecommendByUserId(userId);
        if(null!=userRecommendVO){
            BeanUtils.copyProperties(userRecommendVO,userRecommendCustomizeShowVO);
        }
        initModifyreResponseBean.setModifyReForm(userRecommendCustomizeShowVO);
        //加载修改日志 userChageLog
        UserChangeLogRequest userChangeLogRequest = new UserChangeLogRequest();
        if(StringUtils.isNotEmpty(userId)){
            int intUserId = Integer.parseInt(userId);
            userChangeLogRequest.setUserId(intUserId);
            //根据源代码 设置为3
            userChangeLogRequest.setChangeType(3);
            List<UserChangeLogVO> userChangeLogVOList = userCenterService.selectUserChageLog(userChangeLogRequest);
            List<UserChangeLogCustomizeVO> userChangeLogCustomizeVOList = new ArrayList<UserChangeLogCustomizeVO>();
            if(null!=userChangeLogVOList&&userChangeLogVOList.size()>0){
                userChangeLogCustomizeVOList = CommonUtils.convertBeanList(userChangeLogVOList, UserChangeLogCustomizeVO.class);
            }
            initModifyreResponseBean.setUsersChangeLogForm(userChangeLogCustomizeVOList);
        }
        return new AdminResult<InitModifyreResponseBean>(initModifyreResponseBean);
    }

    /**
     * 修改用户身份证
     *
     * @param request
     * @return 进入用户详情页面
     */
    @ApiOperation(value = "修改用户身份证", notes = "修改用户身份证")
    @PostMapping(value = "/modifyIdCard")
    @ResponseBody
    public AdminResult modifyIdCard(HttpServletRequest request, @RequestBody AdminUserRecommendRequestBean requestBean) {
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
     * @param userId
     * @param userName
     * @return
     */
    @PostMapping(value = "/checkReAction")
    @ResponseBody
    @ApiOperation(value = "校验推荐人", notes = "校验推荐人")
    public AdminResult checkReAction(@RequestHeader(value = "userId") String userId,@RequestBody String userName) {
        //校验推荐人
        if (Validator.isNotNull(userId)) {
            if (StringUtils.isNotEmpty(userName)) {
                int recomFlag = userCenterService.checkRecommend(userId, userName);
                if (recomFlag == 2) {// 推荐人不能是自己
                    return new AdminResult<>(FAIL, "不能将推荐人设置为自己");
                } else if (recomFlag == 1) {// 推荐人不存在
                    return new AdminResult<>(FAIL, "推荐人不存在");

                }
            }
        }
        return new AdminResult<>();
    }



    /**
     * 检查手机号码或用户名唯一性
     *
     * @param userId
     * @param mobile
     * @return
     */
    @PostMapping(value = "/checkAction")
    @ResponseBody
    @ApiOperation(value = "校验手机号", notes = "校验手机号")
    public AdminResult checkAction(@RequestHeader(value = "userId") String userId,  @RequestBody String mobile) {
        // 检查手机号码唯一性
        int cnt = userCenterService.countUserByMobile(Integer.parseInt(userId), mobile);
        if (cnt > 0) {
            return new AdminResult<>(FAIL, "手机号已经存在！");
        }
        return new AdminResult<>();
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
    @ApiOperation(value = "导出会员管理列表", notes = "导出会员管理列表")
    @PostMapping(value = "/exportusers")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response,@RequestBody UserManagerRequestBean userManagerRequestBean) throws Exception {
        // 表格sheet名称
        String sheetName = "会员列表";
        // 文件名称
        String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + ".xls";

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
                            // todo 户籍所在地表 hyjf_idcard_area 不存在
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
     * @param userId
     * @return
     */
    @PostMapping(value = "/initCompanyInfo")
    @ResponseBody
    @ApiOperation(value = "初始化企业用户信息", notes = "初始化企业用户信息")
    public  AdminResult<InitCompanyInfoResponseBean> initCompanyInfo(@RequestBody String userId) {
        InitCompanyInfoResponseBean initCompanyInfoResponseBean = new InitCompanyInfoResponseBean();
        if (StringUtils.isNotBlank(userId)) {
            UserVO user = userCenterService.selectUserByUserId(userId);
            UserBankOpenAccountVO userBankOpenAccountVO = userCenterService.selectBankOpenAccountByUserId(userId);
            CompanyInfoVO companyInfo = userCenterService.selectCompanyInfoByUserId(userId);
            CompanyInfoCompanyInfoVO companyInfoCompanyInfoVO = new CompanyInfoCompanyInfoVO();
            UserBankOpenAccountCustomizeVO userBankOpenAccountCustomizeVO = new UserBankOpenAccountCustomizeVO();
            UserCustomizeShowVO userCustomizeShowVO = new UserCustomizeShowVO();
            if(null!=companyInfo){
                BeanUtils.copyProperties(companyInfo,companyInfoCompanyInfoVO);
            }
            if(null!=userBankOpenAccountVO){
                BeanUtils.copyProperties(userBankOpenAccountVO, userBankOpenAccountCustomizeVO);
            }
            if(null!=user){
                BeanUtils.copyProperties(user, userCustomizeShowVO);
            }
            initCompanyInfoResponseBean.setBankOpenAccount(userBankOpenAccountCustomizeVO);
            initCompanyInfoResponseBean.setCompanyInfo(companyInfoCompanyInfoVO);
            initCompanyInfoResponseBean.setUserVO(userCustomizeShowVO);
        }
        return new AdminResult<InitCompanyInfoResponseBean>(initCompanyInfoResponseBean);
    }

    /**
     * 查询企业开户信息
     *
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/serchCompanyInfo")
    @ApiOperation(value = "查询企业开户信息", notes = "查询企业开户信息")
    public AdminResult<SearchCompanyInfoResponseBean> serchCompanyInfo(@RequestHeader(value = "userId") String userId,@RequestBody String accountId) {
        SearchCompanyInfoResponseBean  searchCompanyInfoResponseBean = new  SearchCompanyInfoResponseBean();
        if (StringUtils.isBlank(userId)) {
            return new AdminResult<>(FAIL, "请先选择用户再进行操作!");
        }
        if (StringUtils.isBlank(accountId)) {
            return new AdminResult<>(FAIL, "请输入正确的电子账号!");
        }
        //根据accountid调用接口查找企业信息
        if (StringUtils.isNotEmpty(userId)) {
            int intUserId = Integer.parseInt(userId);
            CompanyInfoSearchResponseBean companyInfoSearchResponseBean = userCenterService.queryCompanyInfoByAccoutnId(intUserId, accountId);
            CompanyInfoCompanyInfoVO companyInfoCompanyInfoVO = new CompanyInfoCompanyInfoVO();
            if(null!=companyInfoSearchResponseBean&& "00".equals(companyInfoSearchResponseBean.getReturnCode())){
                //代表成功
                CompanyInfoVO infoVO = companyInfoSearchResponseBean.getCompanyInfoVO();
                if(null==infoVO){
                    return new AdminResult<>(FAIL, "请输入正确的电子账号!");
                }
                BeanUtils.copyProperties(infoVO,companyInfoCompanyInfoVO);
                searchCompanyInfoResponseBean.setCompany(companyInfoCompanyInfoVO);
                UserVO userVO = userCenterService.selectUserByUserId(userId);
                Integer bankFlag = userVO.getBankOpenAccount();
                searchCompanyInfoResponseBean.setIsOpenAccount(bankFlag);
                return new AdminResult<SearchCompanyInfoResponseBean>(searchCompanyInfoResponseBean);
            }
            if(null!=companyInfoSearchResponseBean&& "99".equals(companyInfoSearchResponseBean.getReturnCode())){
                return new AdminResult<>(FAIL, companyInfoSearchResponseBean.getReturnMsg());
            }
        }
        return new AdminResult<>(FAIL, "请输入正确的电子账号!");
    }

    /**
     * 保存企业开户信息
     *
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/saveCompanyInfo")
    @ApiOperation(value = "保存企业开户信息", notes = "保存企业开户信息")
    public AdminResult<Response> saveCompanyInfo(@RequestBody CompanyInfoInstRequesetBean companyInfoInstRequesetBean) {
        UpdCompanyRequest updCompanyRequest = new UpdCompanyRequest();
        BeanUtils.copyProperties(companyInfoInstRequesetBean,updCompanyRequest);
        Response response = userCenterService.saveCompanyInfo(updCompanyRequest);
        return new AdminResult<Response>(response);
    }

}
