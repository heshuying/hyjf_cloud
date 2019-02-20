package com.hyjf.admin.controller.mobileclient;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.beans.request.AppPushManageRequestBean;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.mobileclient.AppPushManageService;
import com.hyjf.am.bean.commonimage.BorrowCommonImage;
import com.hyjf.am.response.AppPushManageResponse;
import com.hyjf.am.response.Response;
import com.hyjf.am.vo.admin.AppPushManageVO;
import com.hyjf.common.file.UploadFileUtils;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.GetDate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * App 推送管理
 * @Author : huanghui
 */
@Api(tags = "移动客户端-App推送管理")
@RestController
@RequestMapping("/hyjf-admin/app/pushmanage")
public class AppPushManageController extends BaseController {

    @Value("${file.domain.url}")
    private String url;
    @Value("${file.physical.path}")
    private String physical;
    @Value("${file.upload.temp.path}")
    private String temppath;

    @Autowired
    AppPushManageService appPushManageService;

    // 权限名称
    public static final String PERMISSIONS = "apppushmanage";

    /**
     *
     * @param requestBean
     * @return
     */
    @ApiOperation(value = "App 推送管理", notes = "App 推送管理")
    @PostMapping(value = "/init")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<ListResult<AppPushManageVO>> init(@RequestBody AppPushManageRequestBean requestBean) {

        AppPushManageResponse pushManageResponse = appPushManageService.getAppPushManageList(requestBean);
        // 初始化 返回List
        List<AppPushManageVO> returnList = new ArrayList<>();

//        if (pushManageResponse == null){
//            return new AdminResult<>(FAIL, FAIL_DESC);
//        }

//        if (!Response.isSuccess(pushManageResponse)){
//            return new AdminResult<>(FAIL, "获取列表失败!");
//        }
        if (CollectionUtils.isNotEmpty(pushManageResponse.getResultList())){
            returnList = CommonUtils.convertBeanList(pushManageResponse.getResultList(), AppPushManageVO.class);
            for (AppPushManageVO re : returnList) {
                re.setTimeStartStr(GetDate.formatDate(re.getTimeStart()));
                re.setTimeEndStr(GetDate.formatDate(re.getTimeEnd()));
                re.setCreateTimeStr(GetDate.getDateTimeMyTimeInMillis(re.getCreateTime()));
            }
            return new AdminResult<ListResult<AppPushManageVO>>(ListResult.build(returnList, pushManageResponse.getCount()));
        }else {
            return new AdminResult<ListResult<AppPushManageVO>>(ListResult.build(returnList, 0));
        }
    }

    /**
     * 单条写入数据
     * @param requestBean
     * @return
     */
    @ApiOperation(value = "App 推送管理添加", notes = "App 推送管理添加")
    @PostMapping(value = "/add")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_ADD)
    public AdminResult<AppPushManageVO> add(@RequestBody AppPushManageRequestBean requestBean){

        JSONObject jsonObject = new JSONObject();

        jsonObject = this.validatorFieldCheck(jsonObject, requestBean);
        // 判断是否为空
        if (jsonObject.size() > 0){
            return new AdminResult<>(FAIL, jsonObject.get("code").toString());
        }

        AppPushManageResponse response = this.appPushManageService.insertPushManage(requestBean);

        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        return new AdminResult<>();

    }

    /**
     * 更新
     * @param requestBean
     * @return
     */
    @ApiOperation(value = "App 推送管理更新", notes = "App 推送管理更新")
    @PostMapping(value = "/update")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_MODIFY)
    public AdminResult<AppPushManageVO> update(@RequestBody AppPushManageRequestBean requestBean){

        JSONObject jsonObject = new JSONObject();

        jsonObject = this.validatorFieldCheck(jsonObject, requestBean);
        // 判断是否为空
        if (jsonObject.size() > 0){
            return new AdminResult<>(FAIL, jsonObject.get("code").toString());
        }

        boolean response = this.appPushManageService.updatePushManage(requestBean);

        if (response) {
            return new AdminResult<>(SUCCESS, SUCCESS_DESC);
        }
        return new AdminResult<>(FAIL, "更新失败!");
    }

    /**
     * 获取单条记录详情
     * @param requestBean
     * @return
     */
    @ApiOperation(value = "获取单条记录内容", notes = "获取单条记录内容")
    @PostMapping(value = "/getPushManageById")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult getPushManageById(@RequestBody AppPushManageRequestBean requestBean){

        // ID必须不为空
        if (StringUtils.isEmpty(requestBean.getIds())){
            return new AdminResult<>(FAIL, "ID必须不为空!");
        }
        if (!this.isNumeric(requestBean.getIds())){
            return new AdminResult(FAIL, "ID必须为数字");
        }

        Integer ids = Integer.valueOf(requestBean.getIds());

        AppPushManageResponse pushManageResponse = appPushManageService.getAppPushManageInfoById(ids);

        if (!Response.isSuccess(pushManageResponse)) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        return new AdminResult<>(pushManageResponse.getResult());
    }

    /**
     * 更新单条记录的状态
     * @param requestBean
     * @return
     */
    @ApiOperation(value = "根据ID 更新 单条信息状态", notes = "根据ID 更新 单条信息状态")
    @PostMapping("/updatePushManageStatusById")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_UPDATE)
    public AdminResult<AppPushManageVO> updatePushManageStatusById(@RequestBody AppPushManageRequestBean requestBean){

        // ID必须不为空
        if (requestBean.getIds() == null){
            return new AdminResult<>(FAIL, "ID必须不为空!");
        }

        Integer ids = Integer.valueOf(requestBean.getIds());

        logger.info("updatePushManageStatusById:更新但单条推送数据状态的ID:" + ids);

        boolean pushManageResponse = appPushManageService.updatePushManageStatusById(ids);

        if (pushManageResponse){
            return new AdminResult<>(SUCCESS, SUCCESS_DESC);
        }
        return new AdminResult<>(FAIL, FAIL_DESC);
    }

    /**
     * 删除
     * @return
     */
    @ApiOperation(value = "App 推送管理删除", notes = "App 推送管理删除")
    @PostMapping(value = "/delete")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_DELETE)
    public AdminResult<AppPushManageVO> delete(@RequestBody AppPushManageRequestBean requestBean){
        // ID必须不为空
        if (StringUtils.isEmpty(requestBean.getIds())){
            return new AdminResult<>(FAIL, "ID必须不为空!");
        }

        Integer ids = Integer.valueOf(requestBean.getIds());

        boolean checkCode = this.appPushManageService.deletePushManage(ids);
        if (checkCode){
            return new AdminResult<>(SUCCESS, SUCCESS_DESC);
        }
        return new AdminResult<>(FAIL, "删除失败!");

    }

    @ApiOperation(value = "图片上传", notes = "文件上传")
    @PostMapping("/uploadFile")
    public AdminResult<LinkedList<BorrowCommonImage>> uploadFile(HttpServletRequest request){
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
        String fileDomainUrl = UploadFileUtils.getDoPath(url);
        String filePhysicalPath = UploadFileUtils.getDoPath(physical);
        String fileUploadTempPath = UploadFileUtils.getDoPath(temppath);
        String logoRealPathDir = filePhysicalPath + fileUploadTempPath;

        File logoSaveFile = new File(logoRealPathDir);
        if (!logoSaveFile.exists()) {
            logoSaveFile.mkdirs();
        }

        com.hyjf.am.bean.commonimage.BorrowCommonImage fileMeta = null;
        LinkedList<com.hyjf.am.bean.commonimage.BorrowCommonImage> files = new LinkedList<com.hyjf.am.bean.commonimage.BorrowCommonImage>();

        Iterator<String> itr = multipartRequest.getFileNames();
        MultipartFile multipartFile = null;

        try {
            while (itr.hasNext()) {
                multipartFile = multipartRequest.getFile(itr.next());
                String fileRealName = String.valueOf(System.currentTimeMillis());
                String originalFilename = multipartFile.getOriginalFilename();
                fileRealName = fileRealName + UploadFileUtils.getSuffix(multipartFile.getOriginalFilename());

                // 文件大小
                String errorMessage = UploadFileUtils.upload4Stream(fileRealName, logoRealPathDir, multipartFile.getInputStream(), 5000000L);

                fileMeta = new com.hyjf.am.bean.commonimage.BorrowCommonImage();
                int index = originalFilename.lastIndexOf(".");
                if (index != -1) {
                    fileMeta.setImageName(originalFilename.substring(0, index));
                } else {
                    fileMeta.setImageName(originalFilename);
                }

                fileMeta.setImageRealName(fileRealName);
                fileMeta.setImageSize(multipartFile.getSize() / 1024 + "");// KB
                fileMeta.setImageType(multipartFile.getContentType());
                fileMeta.setErrorMessage(errorMessage);
                // 获取文件路径
                fileMeta.setImagePath(fileUploadTempPath + fileRealName);
                fileMeta.setImageSrc(fileDomainUrl + fileUploadTempPath + fileRealName);
                files.add(fileMeta);
            }
            return new AdminResult<LinkedList<com.hyjf.am.bean.commonimage.BorrowCommonImage>>(files);
        } catch (Exception e) {
            return new AdminResult<>(FAIL, "上传图片失败");
        }

    }

    /**
     * 字段验证
     * @param jsonObject
     * @param requestBean
     * @return
     */
    private JSONObject validatorFieldCheck(JSONObject jsonObject, AppPushManageRequestBean requestBean){

        // 标题名称不能为空
        if (StringUtils.isBlank(requestBean.getTitle())){
            jsonObject.put("code", "标题名称不能为空!");
            return jsonObject;
        }

//        if (StringUtils.isNumeric(requestBean.getOrderId().toString())){
//            jsonObject.put("code", "排序必须为数字!");
//            return jsonObject;
//        }

        if (StringUtils.isBlank(requestBean.getTimeStartDiy())){
            jsonObject.put("code", "起始时间不能为空!");
            return jsonObject;
        }

        if (StringUtils.isBlank(requestBean.getTimeEndDiy())){
            jsonObject.put("code", "结束时间不能为空!");
            return jsonObject;
        }

        return jsonObject;
    }

    /**
     * 利用正则表达式判断字符串是否是数字
     * @param str
     * @return
     */
    private boolean isNumeric(String str){
        String reEx = "[0-9]*";
        Pattern pattern = Pattern.compile(reEx);
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }
}
