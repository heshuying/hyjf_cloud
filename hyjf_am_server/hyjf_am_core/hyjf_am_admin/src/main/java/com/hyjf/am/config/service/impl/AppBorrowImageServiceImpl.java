package com.hyjf.am.config.service.impl;

import com.hyjf.am.config.dao.mapper.auto.AppBorrowImageMapper;
import com.hyjf.am.config.dao.model.auto.AppBorrowImage;
import com.hyjf.am.config.dao.model.auto.AppBorrowImageExample;
import com.hyjf.am.config.service.AppBorrowImageService;
import com.hyjf.common.file.UploadFileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    @Value("${file.physical.path}")
    String PHYSICAL;
    @Value("${file.upload.real.path}")
    String REALPATH;
    @Value("${file.upload.temp.path}")
    String TEMPPATH;

    @Override
    public Integer getCount() {
        AppBorrowImageExample example = new AppBorrowImageExample();
        return appBorrowImageMapper.countByExample(example);
    }

    /**
     * 获取列表
     *
     * @return
     */
    @Override
    public List<AppBorrowImage> getRecordList( int limitStart, int limitEnd) {
        AppBorrowImageExample example = new AppBorrowImageExample();
        AppBorrowImageExample.Criteria crt =example.createCriteria();
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
        record.setBorrowImageUrl(record.getBorrowImageUrl());
        appBorrowImageMapper.insertSelective(record);
    }

    /**
     * 更新
     *
     * @param record
     * @throws Exception
     */
    @Override
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
    @Override
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
        String filePhysicalPath = UploadFileUtils.getDoPath(PHYSICAL);
        // 正式保存的路径
        String fileUploadRealPath = UploadFileUtils.getDoPath(REALPATH)+ "borrowImage/" + UploadFileUtils.getDoPath(record.getBorrowImage());
        // 临时文件夹
        String fileUploadTempPath = UploadFileUtils.getDoPath(TEMPPATH);

        File file = new File(filePhysicalPath + fileUploadRealPath + record.getBorrowImageRealname());
        if (!file.exists()) {
            UploadFileUtils.removeFile4Dir(filePhysicalPath + fileUploadRealPath);
            UploadFileUtils.upload4CopyFile(filePhysicalPath + fileUploadTempPath + record.getBorrowImageRealname(), filePhysicalPath + fileUploadRealPath);
        }
        return fileUploadRealPath + record.getBorrowImageRealname();
    }
}
