package com.hyjf.admin.controller.mobileclient;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.beans.BorrowCommonImage;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.mobileclient.AppBorrowImageService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.config.AppBorrowImageResponse;
import com.hyjf.am.resquest.config.AppBorrowImageRequest;
import com.hyjf.am.vo.config.AppBorrowImageVO;
import com.hyjf.common.file.UploadFileUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import netscape.javascript.JSObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * 产品图片
 * @author lisheng
 * @version AppBorrowImageController, v0.1 2018/7/11 11:26
 */
@Api(tags = "admin移动客户端-产品图片")
@RestController
@RequestMapping("/hyjf-admin/app/maintenance/borrowimage")
public class AppBorrowImageController extends BaseController {
    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    AppBorrowImageService appBorrowImageService;

    @Value("${file.domain.url}")
    private String DOMAIN_URL;

    @Value("${file.physical.path}")
    private String PHYSICAL_PATH;

    @Value("${file.upload.temp.path}")
    private String TEMP_PATH;


    @ApiOperation(value = "产品图片:列表查询", notes = "产品图片:列表查询")
    @PostMapping(value = "/search")
    @ResponseBody
    public AdminResult<ListResult<AppBorrowImageVO>> search(@RequestBody  AppBorrowImageRequest request) {
        try {
            AppBorrowImageResponse recordList = appBorrowImageService.getRecordList(request);
            if (!Response.isSuccess(recordList)) {
                return new AdminResult<>(FAIL, FAIL_DESC);
            }
            return new AdminResult<ListResult<AppBorrowImageVO>>(ListResult.build(recordList.getResultList(), recordList.getRecordTotal()));
        } catch (Exception e) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
    }


    @ApiOperation(value = "产品图片:获取详细画面", notes = "产品图片:获取详细画面")
    @PostMapping(value = "/searchinfo")
    @ResponseBody
    public AdminResult<AppBorrowImageVO> searchinfo(@RequestBody AppBorrowImageRequest request) {
        try {
            AppBorrowImageResponse record = appBorrowImageService.getRecord(request);
            if (!Response.isSuccess(record)) {
                return new AdminResult<>(FAIL, FAIL_DESC);
            }
            return new AdminResult<AppBorrowImageVO>(record.getResult());
        }catch (Exception e) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
    }


    @ApiOperation(value = "产品图片添加维护信息", notes = "产品图片:添加维护信息")
    @PostMapping(value = "/insertinfo")
    @ResponseBody
    public AdminResult<AppBorrowImageVO> insertinfo(@RequestBody AppBorrowImageRequest request) throws Exception {
        try {
            AppBorrowImageResponse response = appBorrowImageService.insertRecord(request);
            if (!Response.isSuccess(response)) {
                return new AdminResult<>(FAIL, FAIL_DESC);
            }
            return new AdminResult<>(SUCCESS, SUCCESS_DESC);
        } catch (Exception e) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
    }

    @ApiOperation(value = "产品图片:修改维护信息", notes = "产品图片:修改维护信息")
    @PostMapping(value = "/updateinfo")
    @ResponseBody
    public AdminResult<AppBorrowImageVO> updateinfo(@RequestBody AppBorrowImageRequest request) throws Exception {
        try {
            AppBorrowImageResponse response = appBorrowImageService.updateRecord(request);
            if (!Response.isSuccess(response)) {
                return new AdminResult<>(FAIL, FAIL_DESC);
            }
            return new AdminResult<>(SUCCESS, SUCCESS_DESC);
        }catch (Exception e) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
    }

    @ApiOperation(value = "产品图片:刪除信息", notes = "产品图片:刪除信息")
    @PostMapping(value = "/deleteinfo")
    @ResponseBody
    public AdminResult<AppBorrowImageVO> deleteinfo(@RequestBody AppBorrowImageRequest request) throws Exception {
        try {
        AppBorrowImageResponse response = appBorrowImageService.deleteRecord(request);
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        return new AdminResult<>(SUCCESS, SUCCESS_DESC);
        }catch (Exception e) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
    }

    /**
     * 资料上传
     *
     * @param request
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "产品图片:资料上传", notes = "产品图片:资料上传")
    @PostMapping(value = "/uploadFile")
    @ResponseBody
    public JSONObject uploadFile(HttpServletRequest request) throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("statusDesc", SUCCESS_DESC);
        jsonObject.put("status", SUCCESS);

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        String fileDomainUrl = DOMAIN_URL;
        String filePhysicalPath = PHYSICAL_PATH;
        String fileUploadTempPath = TEMP_PATH;

        String logoRealPathDir = filePhysicalPath + fileUploadTempPath;

        File logoSaveFile = new File(logoRealPathDir);
        if (!logoSaveFile.exists()) {
            logoSaveFile.mkdirs();
        }

        BorrowCommonImage fileMeta = null;
        LinkedList<BorrowCommonImage> files = new LinkedList<BorrowCommonImage>();

        Iterator<String> itr = multipartRequest.getFileNames();
        MultipartFile multipartFile = null;

        while (itr.hasNext()) {
            multipartFile = multipartRequest.getFile(itr.next());
            String fileRealName = String.valueOf(System.currentTimeMillis());
            String originalFilename = multipartFile.getOriginalFilename();
            fileRealName = fileRealName + UploadFileUtils.getSuffix(multipartFile.getOriginalFilename());

            // 文件大小
            String errorMessage = UploadFileUtils.upload4Stream(fileRealName, logoRealPathDir, multipartFile.getInputStream(), 5000000L);

            fileMeta = new BorrowCommonImage();
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
            fileMeta.setImagePath(logoRealPathDir + fileRealName);
            fileMeta.setImageSrc(fileDomainUrl + logoRealPathDir + fileRealName);
            files.add(fileMeta);
        }
        jsonObject.put("data",files);
        return jsonObject;
    }


    /**
     * 信息验证
     *
     * @param mav
     */
    public void validatorFieldCheck(JSONObject mav, AppBorrowImageRequest form) {
        // 图片标识
        //ValidatorFieldCheckUtil.validateAlphaNumericAndMaxLength(mav, "borrowImage", form.getBorrowImage(), 20, true);
        // 图片名称
        // ValidatorFieldCheckUtil.validateMaxLength(mav, "borrowImageTitle", form.getBorrowImageTitle(), 100, true);
        // 图片
        //ValidatorFieldCheckUtil.validateRequired(mav, "borrowImageRealname", form.getBorrowImageRealname());
    }


}
