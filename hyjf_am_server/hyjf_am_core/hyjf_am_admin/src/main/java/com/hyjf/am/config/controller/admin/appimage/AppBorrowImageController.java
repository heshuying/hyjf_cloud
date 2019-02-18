package com.hyjf.am.config.controller.admin.appimage;

import com.hyjf.am.config.dao.model.auto.AppBorrowImage;
import com.hyjf.am.config.service.AppBorrowImageService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.config.AppBorrowImageResponse;
import com.hyjf.am.resquest.admin.Paginator;
import com.hyjf.am.resquest.config.AppBorrowImageRequest;
import com.hyjf.am.vo.config.AppBorrowImageVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author lisheng
 * @version AppBorrowImageController, v0.1 2018/7/12 17:52
 */
@RestController
@RequestMapping("/am-config/appborrow")
public class AppBorrowImageController {
    @Autowired
    AppBorrowImageService appBorrowImageService;

    @Value("${file.domain.url}")
    private String DOMAIN_URL;

    /**
     * 查询列表
     *
     * @return
     */
    @PostMapping("/getRecordList")
    public AppBorrowImageResponse findAppBannerData(@RequestBody AppBorrowImageRequest form) {
        AppBorrowImageResponse response = new AppBorrowImageResponse();
        Integer count = appBorrowImageService.getCount();
        if (count >0) {
            String filePhysicalPath = DOMAIN_URL;
            Paginator paginator = new Paginator(form.getCurrPage(), count, form.getPageSize());
            List<AppBorrowImage> recordList = appBorrowImageService.getRecordList(paginator.getOffset(), paginator.getLimit());
            List<AppBorrowImageVO> resultList = CommonUtils.convertBeanList(recordList, AppBorrowImageVO.class);
            for (AppBorrowImage appBorrowImage : recordList) {
                appBorrowImage.setBorrowImageUrl(filePhysicalPath + appBorrowImage.getBorrowImageUrl());
            }
            response.setResultList(resultList);
            response.setRecordTotal(count);
        }
        return response;
    }


    /**
     * 详细画面
     *
     * @param form
     * @return
     */
    @PostMapping("/infoAction")
    public AppBorrowImageResponse moveToInfoAction(@RequestBody AppBorrowImageRequest form) {
            AppBorrowImageResponse response = new AppBorrowImageResponse();
        AppBorrowImage record = new AppBorrowImage();
        record.setId(form.getId());
        if (Validator.isNotNull(record.getId())) {
            // 根据主键检索数据
            String filePhysicalPath = DOMAIN_URL;
            record = appBorrowImageService.getRecord(form.getId());
            if (record != null) {
                record.setBorrowImageUrl(record.getBorrowImageUrl());
            }
        }
        AppBorrowImageVO appBorrowImageVO = CommonUtils.convertBean(record, AppBorrowImageVO.class);
        response.setResult(appBorrowImageVO);
        return response;
    }



    /**
     * 添加维护信息
     *
     * @param form
     * @return
     * @throws Exception
     */
    @PostMapping("/insertAction")
    public AppBorrowImageResponse insertAction(@RequestBody AppBorrowImageRequest form) throws Exception {
        AppBorrowImageResponse response = new AppBorrowImageResponse();
        if (form.getPageType()!=null&&!"".equals(form.getPageType())
                && "0".equals(form.getPageType())) {
            if (form.getJumpName()!=null&&form.getJumpName().contains(",")) {
                if (form.getJumpName().split(",")[0]==null|| "".equals(form.getJumpName().split(",")[0])) {
                    form.setJumpName(form.getJumpName().split(",")[form.getJumpName().split(",").length-1]);
                }else {
                    form.setJumpName(form.getJumpName().split(",")[0]);
                }
            }
        }else {
            form.setJumpName("");
        }
       /* AppBorrowImage record = new AppBorrowImage();
        record.setBorrowImageTitle(form.getBorrowImageTitle());
        record.setBorrowImage(form.getBorrowImage());
        record.setBorrowImageName(form.getBorrowImageName());
        record.setBorrowImageUrl(form.getBorrowImageUrl());
        record.setBorrowImageRealname(form.getBorrowImageRealname());
        record.setNotes(form.getNotes());
        record.setSort(form.getSort());
        record.setPageUrl(form.getPageUrl());
        record.setPageType(form.getPageType());
        record.setVersion(form.getVersion());
        record.setStatus(form.getStatus());
        record.setVersionMax(form.getVersionMax());
        record.setJumpName(form.getJumpName());*/
        AppBorrowImage record = CommonUtils.convertBean(form, AppBorrowImage.class);
        if (Validator.isNotNull(form.getBorrowImage())) {
            appBorrowImageService.insertRecord(record);
        }
        return response;
    }

    /**
     * 修改维护信息
     *
     * @param form
     * @return
     * @throws Exception
     */
    @PostMapping("/updateAction")
    public AppBorrowImageResponse updateAction(@RequestBody AppBorrowImageRequest form) throws Exception {
        AppBorrowImageResponse response = new AppBorrowImageResponse();
        if (form.getPageType() != null && !"".equals(form.getPageType())
                && "0".equals(form.getPageType())) {
            if (form.getJumpName() != null && form.getJumpName().contains(",")) {
                if (form.getJumpName().split(",")[0] == null || "".equals(form.getJumpName().split(",")[0])) {
                    form.setJumpName(form.getJumpName().split(",")[form.getJumpName().split(",").length - 1]);
                } else {
                    form.setJumpName(form.getJumpName().split(",")[0]);
                }
            }
        } else {
            form.setJumpName("");
        }
        if (Validator.isNotNull(form.getBorrowImage())) {
            /*AppBorrowImage record = new AppBorrowImage();
            record.setId(form.getId());
            record.setBorrowImageTitle(form.getBorrowImageTitle());
            record.setBorrowImage(form.getBorrowImage());
            record.setBorrowImageName(form.getBorrowImageName());
            record.setBorrowImageUrl(form.getBorrowImageUrl());
            record.setBorrowImageRealname(form.getBorrowImageRealname());
            record.setNotes(form.getNotes());
            record.setSort(form.getSort());
            record.setPageUrl(form.getPageUrl());
            record.setPageType(form.getPageType());
            record.setVersion(form.getVersion());
            record.setStatus(form.getStatus());
            record.setVersionMax(form.getVersionMax());
            record.setJumpName(form.getJumpName());*/
            AppBorrowImage record = CommonUtils.convertBean(form, AppBorrowImage.class);
            appBorrowImageService.updateRecord(record);
        } else {
            AppBorrowImage record = new AppBorrowImage();
            record.setId(form.getId());
            record.setStatus(form.getStatus());
            appBorrowImageService.updateRecord(record);
        }
        if (form.getStatus() != null) {
            response.setRtn(Response.SUCCESS);
            return response;
        } else {
            response.setRtn(Response.FAIL);
            return response;
        }
    }


    /**
     * 删除信息
     * @param
     * @param form
     * @return
     */
    @PostMapping("/deleteAction")
    public AppBorrowImageResponse deleteRecordAction(@RequestBody AppBorrowImageRequest form) {
        AppBorrowImageResponse response = new AppBorrowImageResponse();
        boolean result = appBorrowImageService.deleteRecord(form.getId());
        if(result){
            response.setRtn(Response.SUCCESS);
        }else{
            response.setRtn(Response.FAIL);
        }
        return response;
    }

}
