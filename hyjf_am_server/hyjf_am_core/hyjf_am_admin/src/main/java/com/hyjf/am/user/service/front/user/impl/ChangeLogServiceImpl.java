package com.hyjf.am.user.service.front.user.impl;

import com.hyjf.am.user.dao.model.auto.UserChangeLog;
import com.hyjf.am.user.dao.model.auto.UserChangeLogExample;
import com.hyjf.am.user.dao.model.customize.ChangeLogCustomize;
import com.hyjf.am.user.dao.model.customize.UserInfoForLogCustomize;
import com.hyjf.am.user.service.front.user.ChangeLogService;
import com.hyjf.am.user.service.impl.BaseServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ChangeLogServiceImpl extends BaseServiceImpl implements ChangeLogService {

    /**
     * 获取用户信息修改列表
     * 
     * @return
     */
    @Override
    public List<UserChangeLog> getRecordList(ChangeLogCustomize userChangeLog, int limitStart, int limitEnd) {
        UserChangeLogExample example = new UserChangeLogExample();
        example.setOrderByClause(" change_time desc ");
        if (limitStart != -1) {
            example.setLimitStart(limitStart);
            example.setLimitEnd(limitEnd);
        }
        UserChangeLogExample.Criteria criteria = example.createCriteria();
        // 条件查询
        if (StringUtils.isNotEmpty(userChangeLog.getUsername())) {
            criteria.andUsernameEqualTo(userChangeLog.getUsername());
        }
        if (StringUtils.isNotEmpty(userChangeLog.getRealName())) {
            criteria.andRealNameEqualTo(userChangeLog.getRealName());
        }
        if (StringUtils.isNotEmpty(userChangeLog.getMobile())) {
            criteria.andMobileEqualTo(userChangeLog.getMobile());
        }
        if (StringUtils.isNotEmpty(userChangeLog.getRecommendUser())) {
            criteria.andRecommendUserEqualTo(userChangeLog.getRecommendUser());
        }
        if (userChangeLog.getAttribute() != null) {
            criteria.andAttributeEqualTo(Integer.parseInt(userChangeLog.getAttribute()));
        }
        if (userChangeLog.getIs51() != null) {
            criteria.andIs51EqualTo(userChangeLog.getIs51());
        }
        return userChangeLogMapper.selectByExample(example);
    }

    /**
     * 获取用户信息修改列表
     * @param userChangeLog
     * @return
     */
    @Override
    public List<ChangeLogCustomize> getChangeLogList(ChangeLogCustomize userChangeLog) {
        List<ChangeLogCustomize> changeLogs = changeLogCustomizeMapper.queryChangeLogList(userChangeLog);
        return changeLogs;
    }
    
    /**
     * 
     * 获取某一用户的信息修改列表
     * @param userChangeLog
     * @param limitStart
     * @param limitEnd
     * @return
     */
    @Override
    public List<ChangeLogCustomize> getUserRecordList(ChangeLogCustomize userChangeLog, int limitStart, int limitEnd) {
        if (limitStart != -1) {
            userChangeLog.setLimitStart(limitStart);
            userChangeLog.setLimitEnd(limitEnd);
           
        }
        return changeLogCustomizeMapper.queryChangeLogList(userChangeLog);
    }
    
    /**
     * 获取用户信息修改记录数
     * 
     * @param
     * @return
     */

    @Override
    public int countRecordTotal(ChangeLogCustomize userChangeLog) {
        return changeLogCustomizeMapper.queryChangeLogCount(userChangeLog);
    }

    /**
     * 新增用户信息修改记录数
     *
     * @param userChangeLog
     * @return
     */
    @Override
    public boolean insertSelective(ChangeLogCustomize userChangeLog) {
        if (userChangeLog!=null) {
            if(userChangeLog.getUserId()==null||userChangeLog.getUtmName()==null){
                logger.info("用户信息修改日志保存失败! 数据为空：UserId：【"+userChangeLog.getUserId()+"】  UtmName：【"+userChangeLog.getUtmName()+"】");
                return false;
            }
            // 插入操作日志表
            ChangeLogCustomize changeLog = new ChangeLogCustomize();
            List<UserInfoForLogCustomize> users = userManagerCustomizeMapper.selectUserInfoByUserId(userChangeLog.getUserId());
            UserInfoForLogCustomize logRecord = users.get(0);
            changeLog.setUserId(logRecord.getUserId());
            changeLog.setUsername(logRecord.getUserName());
            changeLog.setAttribute(String.valueOf(logRecord.getAttribute()));
            changeLog.setIs51(logRecord.getIs51());
            changeLog.setRealName(logRecord.getRealName());
            changeLog.setRecommendUser(logRecord.getRecommendName());
            changeLog.setUpdateType(2);//2用户信息修改
            changeLog.setMobile(logRecord.getMobile());
            changeLog.setRole(logRecord.getUserRole());
            changeLog.setStatus(logRecord.getUserStatus());
            changeLog.setIdcard(logRecord.getIdCard());
            changeLog.setUtmName(userChangeLog.getUtmName());
            changeLog.setRemark(userChangeLog.getRemark());
            changeLog.setUtmType(userChangeLog.getUtmType());
            changeLog.setUtmSourceId(userChangeLog.getUtmSourceId());
            if (users != null && !users.isEmpty()) {
                changeLog.setRemark("渠道修改");
                changeLog.setUpdateUser(userChangeLog.getLoginUserName());
                changeLog.setUpdateUserid(userChangeLog.getLoginUserId());
                changeLog.setUpdateTime(new Date());
                ChangeLogCustomize changeLogByUser = new ChangeLogCustomize();
                changeLogByUser.setUserId(logRecord.getUserId());
                // 判断是否为第一次修改
                int userLogCount =  changeLogCustomizeMapper.queryChangeLogByUserIdCount(changeLogByUser);
                // 修改渠道
                int userLogFlg =  changeLogCustomizeMapper.insertSelective(changeLog);
                if(userLogCount < 1 && !("NoChannelInformation").equals(userChangeLog.getSourceIdWasName())){
                    // 原渠道记录
                    changeLog.setRemark("该记录为用户原渠道记录");
                    changeLog.setUtmName(userChangeLog.getSourceIdWasName());
                    int userLogFlgWas =  changeLogCustomizeMapper.insertSelective(changeLog);
                    if (userLogFlgWas > 0) {
                        logger.info("==================用户信息修改日志保存成功!（原渠道信息新增）======");
                    } else {
                        throw new RuntimeException("============用户信息修改日志保存失败!========");
                    }
                }
                if (userLogFlg > 0) {
                    logger.info("==================用户信息修改日志保存成功!======");
                } else {
                    throw new RuntimeException("============用户信息修改日志保存失败!========");
                }
            }
        }
       return true;
    }
}
