package com.hyjf.am.config.service.impl;

import com.hyjf.am.config.dao.mapper.auto.ParamNameMapper;
import com.hyjf.am.config.dao.mapper.customize.SubmissionsCustomizeMapper;
import com.hyjf.am.config.dao.model.auto.ParamName;
import com.hyjf.am.config.dao.model.auto.ParamNameExample;
import com.hyjf.am.config.dao.model.customize.SubmissionsWithBLOBs;
import com.hyjf.am.config.service.SubmissionsService;
import com.hyjf.am.resquest.config.SubmissionsRequest;
import com.hyjf.am.vo.config.SubmissionsCustomizeVO;
import com.netflix.discovery.converters.Auto;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.crypto.tls.UserMappingType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lisheng
 * @version SubmissionsServiceImpl, v0.1 2018/7/13 16:20
 */
@Service
public class SubmissionsServiceImpl implements SubmissionsService {
    @Autowired
    ParamNameMapper paramNameMapper;

    @Autowired
    SubmissionsCustomizeMapper submissionsCustomizeMapper;

    /**
     * 获取数据字典表的下拉列表
     *
     * @return
     */
    @Override
    public List<ParamName> getParamNameList(String nameClass) {
        ParamNameExample example = new ParamNameExample();
        ParamNameExample.Criteria cra = example.createCriteria();
        cra.andNameClassEqualTo(nameClass);
        cra.andDelFlagEqualTo(0);
        example.setOrderByClause(" sort ASC ");
        return paramNameMapper.selectByExample(example);
    }


    /**
     * 条件查询
     * @param form
     * @param limitStart
     * @param limitEnd
     * @return
     */
    @Override
    public List<SubmissionsCustomizeVO> queryRecordList(SubmissionsRequest form, int limitStart, int limitEnd) {
        // 封装查询条件
        Map<String, Object> searchCon = new HashMap<String, Object>();
        // 用户名
        String userName = StringUtils.isNotEmpty(form.getUserName()) ? form.getUserName() : null;


        searchCon.put("userName", userName);
        form.setUserName(userName);
        // 系统类别
        String sysType = form.getSysType();
        searchCon.put("sysType", sysType);
        // 操作系统版本号
		/*String sysVersion = form.getSysVersion();
		searchCon.put("sysVersion",sysVersion);*/
        // 平台版本号
        String platformVersion = form.getPlatformVersion();
        searchCon.put("platformVersion", platformVersion);
        // 手机型号
        String phoneType = form.getPhoneType();
        searchCon.put("phoneType", phoneType);
        // 反馈内容
		/*String content = form.getContent();
		searchCon.put("content", content);*/
        // 添加时间-开始
        String addTimeStart = form.getAddTimeStart();
        if(StringUtils.isNotEmpty(addTimeStart)){
            searchCon.put("addTimeStart", addTimeStart);
        }
        // 添加时间-结束
        String addTimeEnd = form.getAddTimeEnd();
        if(StringUtils.isNotEmpty(addTimeEnd)){
            searchCon.put("addTimeEnd", addTimeEnd);
        }

        if(limitEnd>=0&&limitStart>=0){
            searchCon.put("limitStart",limitStart);
            searchCon.put("limitEnd",limitEnd);
        }

        return submissionsCustomizeMapper.queryRecordList(searchCon);
    }

    /**
     * 查询总条数
     * @param form
     * @return
     */
    @Override
    public int queryTotal(SubmissionsRequest form) {
        // 封装查询条件
        Map<String, Object> searchCon = new HashMap<String, Object>();
        // 用户名
        String userName = StringUtils.isNotEmpty(form.getUserName()) ? form.getUserName() : null;

        searchCon.put("userName", userName);
        form.setUserName(userName);
        // 系统类别
        String sysType = form.getSysType();
        searchCon.put("sysType", sysType);
        // 操作系统版本号
		/*String sysVersion = form.getSysVersion();
		searchCon.put("sysVersion",sysVersion);*/
        // 平台版本号
        String platformVersion = form.getPlatformVersion();
        searchCon.put("platformVersion", platformVersion);
        // 手机型号
        String phoneType = form.getPhoneType();
        searchCon.put("phoneType", phoneType);
        // 反馈内容
		/*String content = form.getContent();
		searchCon.put("content", content);*/
        // 添加时间-开始
        String addTimeStart = form.getAddTimeStart();
        if(StringUtils.isNotEmpty(addTimeStart)){
            searchCon.put("addTimeStart", addTimeStart);
        }
        // 添加时间-结束
        String addTimeEnd = form.getAddTimeEnd();
        if(StringUtils.isNotEmpty(addTimeEnd)){
            searchCon.put("addTimeEnd", addTimeEnd);
        }
        return submissionsCustomizeMapper.countRecordTotal(searchCon);
    }

    /**
     * 更新意见反馈
     * @param submissions
     * @author Administrator
     */
    @Override
    public boolean updateSubmissions(SubmissionsWithBLOBs submissions) {
       return submissionsCustomizeMapper.updateByPrimaryKeySelective(submissions)>0?true:false;
    }
}
