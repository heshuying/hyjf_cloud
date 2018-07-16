package com.hyjf.am.config.service.impl;

import com.hyjf.am.config.dao.mapper.auto.AppBorrowImageMapper;
import com.hyjf.am.config.dao.model.auto.AppBorrowImage;
import com.hyjf.am.config.dao.model.auto.AppBorrowImageExample;
import com.hyjf.am.config.service.AppBorrowImageService;
import com.hyjf.common.file.UploadFileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Date;
import java.util.List;

/**
 * @author lisheng
 * @version AppBorrowImageServiceImpl, v0.1 2018/7/12 17:32
 */
@Service
public class AppBorrowImageServiceImpl implements AppBorrowImageService {
    @Autowired
    AppBorrowImageMapper appBorrowImageMapper;

    /**
     * 获取列表
     *
     * @return
     */
    public List<AppBorrowImage> getRecordList(AppBorrowImage config, int limitStart, int limitEnd) {
        AppBorrowImageExample example = new AppBorrowImageExample();
        AppBorrowImageExample.Criteria crt =example.createCriteria();
        if (config.getStatus()!=null ) {
            crt.andIdEqualTo(config.getStatus());
        }
        if (config.getStatus()!=null ) {
            crt.andPageTypeEqualTo(config.getPageType());
        }
        if (config.getVersion()!=null ) {
            crt.andVersionEqualTo(config.getVersion());
        }
        if (config.getVersionMax()!=null ) {
            crt.andVersionMaxEqualTo(config.getVersionMax());
        }
        if (config.getId()!=null ) {
            crt.andIdEqualTo(config.getId());
        }

        if (limitStart != -1) {
            example.setLimitStart(limitStart);
            example.setLimitEnd(limitEnd);
        }
        return appBorrowImageMapper.selectByExample(example);
    }

    @Override
    public AppBorrowImage getRecord(Integer id) {
        AppBorrowImageExample example = new AppBorrowImageExample();
        AppBorrowImageExample.Criteria crt =example.createCriteria();
        crt.andIdEqualTo(id);
        List<AppBorrowImage> borrowImages =this.appBorrowImageMapper.selectByExample(example);
        if(borrowImages.size()>0){
            return borrowImages.get(0);
        }
        return null;


    }

    /**
     * 插入
     * @param record
     * @throws Exception
     */
    @Override
    public void insertRecord(AppBorrowImage record) throws Exception {
        //int nowTime = GetDate.getNowTime10();
        //record.setAddtime(nowTime);
        record.setCreateTime(new Date());
        record.setBorrowImageUrl(this.moveUploadImage(record));
        appBorrowImageMapper.insertSelective(record);
    }

    /**
     * 更新
     *
     * @param record
     * @throws Exception
     */
    public void updateRecord(AppBorrowImage record) throws Exception {
        //int updatetime = GetDate.getNowTime10();
        record.setUpdateTime(new Date());
        if (record.getStatus()==null) {
            record.setBorrowImageUrl(this.moveUploadImage(record));
        }
        appBorrowImageMapper.updateByPrimaryKeySelective(record);
    }


    /**
     * 删除
     **/
    public boolean deleteRecord(Integer id) {
        AppBorrowImageExample example = new AppBorrowImageExample();
        AppBorrowImageExample.Criteria crt =example.createCriteria();
        crt.andIdEqualTo(id);
        return appBorrowImageMapper.deleteByExample(example)>0?true:false;
    }

    /**
     * 上传图片的信息
     * @return
     * @throws Exception
     */
    private String moveUploadImage(AppBorrowImage record) throws Exception {
        // 保存的物理路径
        // TODO PropUtils.getSystem("file.physical.path")
        String filePhysicalPath = UploadFileUtils.getDoPath("");
        // 正式保存的路径
        //TODO PropUtils.getSystem("file.upload.real.path")) + "borrowImage/" + UploadFileUtils.getDoPath(record.getBorrowImage()
        String fileUploadRealPath = UploadFileUtils.getDoPath("");
        // 临时文件夹
        // TODO PropUtils.getSystem("file.upload.real.path")) + "borrowImage/" + UploadFileUtils.getDoPath(record.getBorrowImage()
        String fileUploadTempPath = UploadFileUtils.getDoPath("");

        File file = new File(filePhysicalPath + fileUploadRealPath + record.getBorrowImageRealname());
        if (!file.exists()) {
            UploadFileUtils.removeFile4Dir(filePhysicalPath + fileUploadRealPath);
            UploadFileUtils.upload4CopyFile(filePhysicalPath + fileUploadTempPath + record.getBorrowImageRealname(), filePhysicalPath + fileUploadRealPath);
        }
        return fileUploadRealPath + record.getBorrowImageRealname();
    }
}
