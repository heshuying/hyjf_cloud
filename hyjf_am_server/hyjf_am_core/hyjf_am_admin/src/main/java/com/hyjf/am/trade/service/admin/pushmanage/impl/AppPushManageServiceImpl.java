package com.hyjf.am.trade.service.admin.pushmanage.impl;

import com.hyjf.am.resquest.admin.AppPushManageRequest;
import com.hyjf.am.trade.dao.mapper.auto.AppPushManageMapper;
import com.hyjf.am.trade.dao.model.auto.AppPushManage;
import com.hyjf.am.trade.dao.model.auto.AppPushManageExample;
import com.hyjf.am.trade.service.admin.pushmanage.AppPushManageService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * 移动客户端 App 推送管理
 * @Author : huanghui
 */
@Service
public class AppPushManageServiceImpl implements AppPushManageService {

    @Autowired
    private AppPushManageMapper appPushManageMapper;

    /**
     * 列表总条数
     * @param pushManageRequest
     * @return
     */
    @Override
    public Integer getCountList(AppPushManageRequest pushManageRequest) {
        AppPushManageExample example = new AppPushManageExample();
        AppPushManageExample.Criteria criteria = example.createCriteria();

        // 标的不为空
        if (StringUtils.isNotBlank(pushManageRequest.getTitle())){
            criteria.andTitleLike(pushManageRequest.getTitle());
        }

        // 状态不为空
//        if (pushManageRequest.getStatus().toString() != "" && pushManageRequest.getStatus() != null){
//            criteria.andStatusEqualTo(pushManageRequest.getStatus());
//        }


        if (StringUtils.isNotBlank(pushManageRequest.getTimeStart())){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                criteria.andTimeStartGreaterThanOrEqualTo(sdf.parse(pushManageRequest.getTimeStart()));
                criteria.andTimeEndLessThanOrEqualTo(sdf.parse(pushManageRequest.getTimeEnd()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return appPushManageMapper.countByExample(example);
    }

    /**
     * 获取所有的数据列表
     * @param pushManageRequest
     * @return
     */
    @Override
    public List<AppPushManage> getAllList(AppPushManageRequest pushManageRequest) {
        AppPushManageExample example = new AppPushManageExample();
        AppPushManageExample.Criteria criteria = example.createCriteria();

        // 标的不为空
        if (StringUtils.isNotBlank(pushManageRequest.getTitle())){
            criteria.andTitleLike(pushManageRequest.getTitle());
        }

        // 状态不为空
        if (pushManageRequest.getStatus() != null){
            criteria.andStatusEqualTo(pushManageRequest.getStatus());
        }

        if (StringUtils.isNotBlank(pushManageRequest.getTimeStart())){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                criteria.andTimeStartGreaterThanOrEqualTo(sdf.parse(pushManageRequest.getTimeStart()));
                criteria.andTimeEndLessThanOrEqualTo(sdf.parse(pushManageRequest.getTimeEnd()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return appPushManageMapper.selectByExample(example);
    }

    /**
     * 单条数据写入
     * @param pushManageRequest
     * @return
     */
    @Override
    public int insertPushManage(AppPushManageRequest pushManageRequest) {

        AppPushManage pushManage = new AppPushManage();

        BeanUtils.copyProperties(pushManageRequest, pushManage);
        if (StringUtils.isNotBlank(pushManageRequest.getTimeStart()) && StringUtils.isNotBlank(pushManageRequest.getTimeEnd())){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                pushManage.setTimeStart(sdf.parse(pushManageRequest.getTimeStart()));
                pushManage.setTimeEnd(sdf.parse(pushManageRequest.getTimeEnd()));
            }catch (ParseException e){
                e.printStackTrace();
            }
        }
        return appPushManageMapper.insertSelective(pushManage);
    }

    /**
     * 单条更新
     * @param pushManageRequest
     * @return
     */
    @Override
    public int updatePushManage(AppPushManageRequest pushManageRequest) {
        AppPushManage pushManage = new AppPushManage();

        BeanUtils.copyProperties(pushManageRequest, pushManage);
        if (StringUtils.isNotBlank(pushManageRequest.getTimeStart()) && StringUtils.isNotBlank(pushManageRequest.getTimeEnd())){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                pushManage.setTimeStart(sdf.parse(pushManageRequest.getTimeStart()));
                pushManage.setTimeEnd(sdf.parse(pushManageRequest.getTimeEnd()));
            }catch (ParseException e){
                e.printStackTrace();
            }
        }
        return appPushManageMapper.updateByPrimaryKeySelective(pushManage);
    }

    /**
     * 删除指定的记录
     * @param id
     * @return
     */
    @Override
    public int deletePushManage(Integer id) {
        return appPushManageMapper.deleteByPrimaryKey(id);
    }

    /**
     * 获取单条详细信息
     * @param id
     * @return
     */
    @Override
    public AppPushManage getAppPushManageInfoById(Integer id) {
        return appPushManageMapper.selectByPrimaryKey(id);
    }
}
