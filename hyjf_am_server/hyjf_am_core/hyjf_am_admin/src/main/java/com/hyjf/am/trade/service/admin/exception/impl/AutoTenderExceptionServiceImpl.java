/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin.exception.impl;

import com.hyjf.am.trade.dao.mapper.auto.HjhAccedeMapper;
import com.hyjf.am.trade.dao.mapper.auto.HjhPlanBorrowTmpMapper;
import com.hyjf.am.trade.dao.mapper.customize.AdminAutoTenderExceptionMapper;
import com.hyjf.am.trade.dao.model.auto.HjhAccede;
import com.hyjf.am.trade.dao.model.auto.HjhAccedeExample;
import com.hyjf.am.trade.dao.model.auto.HjhPlanBorrowTmp;
import com.hyjf.am.trade.dao.model.auto.HjhPlanBorrowTmpExample;
import com.hyjf.am.trade.dao.model.customize.AdminPlanAccedeListCustomize;
import com.hyjf.am.trade.service.admin.exception.AutoTenderExceptionService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author nxl
 * @version AutoTenderExceptionServiceImpl, v0.1 2018/7/12 10:30
 */
@Service
public class AutoTenderExceptionServiceImpl extends BaseServiceImpl implements AutoTenderExceptionService {
    @Autowired
    private AdminAutoTenderExceptionMapper adminAutoTenderExceptionMapper;
    @Autowired
    private HjhAccedeMapper hjhAccedeMapper;
    @Autowired
    private HjhPlanBorrowTmpMapper hjhPlanBorrowTmpMapper;


    private Logger logger = LoggerFactory.getLogger(AutoTenderExceptionServiceImpl.class);

    /**
     * 检索加入明细列表
     *
     * @Title selectAccedeRecordList
     * @param mapParam
     * @return
     */
    @Override
    public List<AdminPlanAccedeListCustomize> selectAccedeRecordList(Map<String,Object> mapParam){
        List<AdminPlanAccedeListCustomize> listCustomizes = adminAutoTenderExceptionMapper.selectAccedeExceptionList(mapParam);
        return listCustomizes;
    }

    /**
     * 检索加入明细件数
     *
     * @Title countAccedeRecord
     * @param mapParam
     * @return
     */
    @Override
    public int countAccedeRecord(Map<String,Object> mapParam){
        int intCount = adminAutoTenderExceptionMapper.countAccedeExceptionRecord(mapParam);
        return intCount;
    }



    /**
     * 查询计划加入明细
     * @param mapParam
     * @return
     */
    @Override
    public HjhAccede selectHjhAccede(Map<String,Object> mapParam) {
        String accedeOrderId = mapParam.get("planOrderId").toString();
        String planNid = mapParam.get("debtPlanNid").toString();
        String  strUserId = mapParam.get("userId").toString();
        int userId = Integer.parseInt(strUserId);
        HjhAccedeExample example = new HjhAccedeExample();
        HjhAccedeExample.Criteria crt = example.createCriteria();
        crt.andAccedeOrderIdEqualTo(accedeOrderId);
        crt.andPlanNidEqualTo(planNid);
        crt.andUserIdEqualTo(userId);
        List<HjhAccede> list = this.hjhAccedeMapper.selectByExample(example);
        if(list != null && list.size() >= 1){
            if(list.size() > 1){
                logger.info("加入明细表 "+accedeOrderId+" 记录大于1！");
            }
            return list.get(0);
        }else {
            return null;
        }
    }
    /**
     * 查询计划加入明细临时表
     * @param mapParam
     * @return
     */
    @Override
    public HjhPlanBorrowTmp selectBorrowJoinList(Map<String,Object> mapParam) {
        String accedeOrderId = mapParam.get("accedeOrderId").toString();
        String debtPlanNid = mapParam.get("debtPlanNid").toString();
        String  strUserId = mapParam.get("userId").toString();
        int userId = Integer.parseInt(strUserId);
        HjhPlanBorrowTmpExample example = new HjhPlanBorrowTmpExample();
        HjhPlanBorrowTmpExample.Criteria crt = example.createCriteria();
        crt.andAccedeOrderIdEqualTo(accedeOrderId);
        crt.andPlanNidEqualTo(debtPlanNid);
        crt.andUserIdEqualTo(userId);
        List<HjhPlanBorrowTmp> list = this.hjhPlanBorrowTmpMapper.selectByExample(example);
        if (list != null && list.size() >= 1) {
            if (list.size() > 1) {
                logger.info("加入明细临时表 " + accedeOrderId + " 记录大于1！");
            }
            return list.get(0);
        } else {
            return null;
        }
    }

    /**
     * 更改加入明细状态
     */
    @Override
    public boolean updateTender(Map<String,Object> mapParam){
        String strHjhAccedeId = mapParam.get("hjhAccedeId").toString();
        String strStatus = mapParam.get("status").toString();
        int status = Integer.parseInt(strStatus);
        int intAccedeId = Integer.parseInt(strHjhAccedeId);
        HjhAccede newHjhAccede = hjhAccedeMapper.selectByPrimaryKey(intAccedeId);
        newHjhAccede.setOrderStatus(status); // 加入计划成功
        newHjhAccede.setUpdateTime(new Date());
        newHjhAccede.setUpdateUser(1);
        newHjhAccede.setId(intAccedeId);
        boolean accedeOk = this.hjhAccedeMapper.updateByPrimaryKeySelective(newHjhAccede)>0?true:false;
        if (!accedeOk) {
            throw new RuntimeException("HjhAccede更改状态失败");
        }
        return accedeOk;
    }


}
