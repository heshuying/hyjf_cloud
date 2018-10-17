/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.admin.membercentre.impl;

import com.hyjf.am.trade.dao.mapper.auto.CreditTenderMapper;
import com.hyjf.am.trade.dao.mapper.customize.UserRepayListCustomizeMapper;
import com.hyjf.am.user.dao.mapper.auto.HjhUserAuthMapper;
import com.hyjf.am.user.dao.mapper.customize.UserPayAuthCustomizeMapper;
import com.hyjf.am.user.dao.model.auto.HjhUserAuth;
import com.hyjf.am.user.dao.model.auto.HjhUserAuthExample;
import com.hyjf.am.user.dao.model.auto.HjhUserAuthLog;
import com.hyjf.am.user.dao.model.auto.User;
import com.hyjf.am.user.dao.model.customize.AdminUserPayAuthCustomize;
import com.hyjf.am.user.service.admin.membercentre.UserPayAuthService;
import com.hyjf.am.user.service.impl.BaseServiceImpl;
import com.hyjf.common.util.GetDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author nxl
 * @version UserManagerServiceImpl, v0.1 2018/6/20 9:49
 *          后台管理系统 ：会员中心->会员管理 接口实现
 */
@Service
public class UserPayAuthServiceImpl extends BaseServiceImpl implements UserPayAuthService {
    private static Logger logger = LoggerFactory.getLogger(UserPayAuthServiceImpl.class);
    @Autowired
    private UserPayAuthCustomizeMapper userPayAuthCustomizeMapper;
    @Autowired
    private HjhUserAuthMapper hjhUserAuthMapper;
    /**
     * 根据筛选条件查找会员列表
     *
     * @param mapParam 筛选条件
     * @return
     */
    @Override
    public List<AdminUserPayAuthCustomize> selectUserPayAuthList(Map<String, Object> mapParam, int limitStart, int limitEnd) {
        // 封装查询条件
        if (limitStart == 0 || limitStart > 0) {
            mapParam.put("limitStart", limitStart);
        }
        if (limitEnd > 0) {
            mapParam.put("limitEnd", limitEnd);
        }
        List<AdminUserPayAuthCustomize> adminUserPayAuthCustomizeList = userPayAuthCustomizeMapper.selectUserPayAuthList(mapParam);
        return adminUserPayAuthCustomizeList;
    }

    /**
     * 根据条件获取用户列表总数
     *
     * @return
     */
    @Override
    public int countRecordTotalPay(Map<String, Object> mapParam) {
        int integerCount = userPayAuthCustomizeMapper.countRecordTotalPay(mapParam);
        return integerCount;
    }

    /**
     * 根据用户id查询用户签约授权信息
     *
     * @param userId
     * @return
     */
    @Override
    public HjhUserAuth selectHjhUserAuthByUserId(Integer userId) {
        HjhUserAuthExample example = new HjhUserAuthExample();
        example.createCriteria().andUserIdEqualTo(userId);
        List<HjhUserAuth> list = hjhUserAuthMapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }
    /**
     * 缴费授权解约
     * @param userId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateCancelPayAuth(int userId){
        HjhUserAuth hjhUserAuth = this.selectHjhUserAuthByUserId(userId);
        hjhUserAuth.setAutoPaymentStatus(0);
        hjhUserAuth.setPaymentCancelTime(GetDate.date2Str(new Date(),GetDate.yyyyMMdd));

        User user = this.findUserByUserId(userId);
        user.setPaymentAuthStatus(0);

        boolean isUpd =hjhUserAuthMapper.updateByPrimaryKeySelective(hjhUserAuth)>0?true:false;
        // 更新user表状态为授权成功
        boolean instFlg =usersMapper.updateByPrimaryKeySelective(user)>0?true:false;
        if(isUpd&&instFlg){
            return true;
        }else{
            throw new RuntimeException("缴费授权解更新异常");
        }
    }

    /**
     * 插入授权记录表
     * @param hjhUserAuthLogRequest
     * @return
     */
    @Override
    public boolean insertUserAuthLog2(HjhUserAuthLog hjhUserAuthLogRequest){
        boolean instFlg = hjhUserAuthLogMapper.insertSelective(hjhUserAuthLogRequest)>0?true:false;
        if(!instFlg){
            throw new RuntimeException("插入授权记录表更新异常");
        }
        return instFlg;
    }
}
