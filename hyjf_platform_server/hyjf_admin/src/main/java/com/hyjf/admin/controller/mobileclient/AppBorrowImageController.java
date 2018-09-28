package com.hyjf.admin.controller.mobileclient;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.mobileclient.AppBorrowImageService;
import com.hyjf.admin.utils.FileUpLoadUtil;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.config.AppBorrowImageResponse;
import com.hyjf.am.resquest.config.AppBorrowImageRequest;
import com.hyjf.am.vo.config.AppBorrowImageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 产品图片
 *
 * @author lisheng
 * @version AppBorrowImageController, v0.1 2018/7/11 11:26
 */
@Api(tags = "移动客户端-产品图片")
@RestController
@RequestMapping("/hyjf-admin/app/maintenance/borrowimage")
public class AppBorrowImageController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private AppBorrowImageService appBorrowImageService;
    @Autowired
    private FileUpLoadUtil fileUpLoadUtil;

    @ApiOperation(value = "产品图片:列表查询", notes = "产品图片:列表查询")
    @PostMapping(value = "/search")
    public AdminResult<ListResult<AppBorrowImageVO>> search(@RequestBody AppBorrowImageRequest request) {
        AppBorrowImageResponse recordList = appBorrowImageService.getRecordList(request);
        if (!Response.isSuccess(recordList)) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        return new AdminResult<>(ListResult.build(recordList.getResultList(), recordList.getRecordTotal()));

    }

    @ApiOperation(value = "产品图片:获取详细画面", notes = "产品图片:获取详细画面")
    @PostMapping(value = "/searchinfo")
    public AdminResult<AppBorrowImageVO> searchinfo(@RequestBody AppBorrowImageRequest request) {
        AppBorrowImageResponse record = appBorrowImageService.getRecord(request);
        if (!Response.isSuccess(record)) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        return new AdminResult<>(record.getResult());

    }


    @ApiOperation(value = "产品图片添加维护信息", notes = "产品图片:添加维护信息")
    @PostMapping(value = "/insertinfo")
    public AdminResult<AppBorrowImageVO> insertinfo(@RequestBody AppBorrowImageRequest request) {

        AppBorrowImageResponse response = appBorrowImageService.insertRecord(request);
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        return new AdminResult<>(SUCCESS, SUCCESS_DESC);

    }

    @ApiOperation(value = "产品图片:修改维护信息", notes = "产品图片:修改维护信息")
    @PostMapping(value = "/updateinfo")
    public AdminResult<AppBorrowImageVO> updateinfo(@RequestBody AppBorrowImageRequest request) {
        AppBorrowImageResponse response = appBorrowImageService.updateRecord(request);
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        return new AdminResult<>(SUCCESS, SUCCESS_DESC);

    }

    @ApiOperation(value = "产品图片:刪除信息", notes = "产品图片:刪除信息")
    @PostMapping(value = "/deleteinfo")
    public AdminResult<AppBorrowImageVO> deleteinfo(@RequestBody AppBorrowImageRequest request) {

        AppBorrowImageResponse response = appBorrowImageService.deleteRecord(request);
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        return new AdminResult<>(SUCCESS, SUCCESS_DESC);

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
    public JSONObject uploadFile(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("statusDesc", SUCCESS_DESC);
        jsonObject.put("status", SUCCESS);
        jsonObject.put("data", fileUpLoadUtil.upLoad(request));
        return jsonObject;
    }

}
