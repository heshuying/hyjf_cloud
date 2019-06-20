/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin.hjhplan.impl;

import java.text.SimpleDateFormat;
import java.util.List;

import com.hyjf.am.trade.dao.model.auto.HjhAccede;
import com.hyjf.am.trade.dao.model.auto.HjhAccedeExample;
import com.hyjf.am.trade.dao.model.auto.TenderUtmChangeLog;
import com.hyjf.am.trade.service.admin.hjhplan.AdminHjhPlanChangeUtmService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.user.dao.model.auto.UtmPlat;
import com.hyjf.am.user.service.front.user.UtmPlatService;
import com.hyjf.am.vo.trade.hjh.HjhPlanAccedeCustomizeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author cui
 * @version AdminHjhPlanChangeUtmServiceImpl, v0.1 2019/6/18 17:11
 */
@Service
public class AdminHjhPlanChangeUtmServiceImpl extends BaseServiceImpl implements AdminHjhPlanChangeUtmService {

    @Autowired
    private UtmPlatService utmPlatService;

    @Override
    public HjhPlanAccedeCustomizeVO getHjhPlanTender(String planOrderId) {

        HjhPlanAccedeCustomizeVO vo=new HjhPlanAccedeCustomizeVO();

        HjhAccedeExample example=new HjhAccedeExample();
        example.or().andAccedeOrderIdEqualTo(planOrderId);
        List<HjhAccede> lstAccede=hjhAccedeMapper.selectByExample(example);
        if(lstAccede.size() ==1){
             HjhAccede hjhAccede=lstAccede.get(0);
             buildObject(vo,hjhAccede);
             return vo;
        }else{
            throw new IllegalArgumentException("未找到订单号=【"+planOrderId+"】的计划订单！");
        }
    }

    private void buildObject(HjhPlanAccedeCustomizeVO vo, HjhAccede hjhAccede) {

        vo.setDebtPlanNid(hjhAccede.getPlanNid());
        vo.setPlanOrderId(hjhAccede.getAccedeOrderId());
        vo.setAccedeAccount(String.valueOf(hjhAccede.getAccedeAccount()));
        if(hjhAccede.getCreateTime()!=null){
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            vo.setCreateTime(sdf.format(hjhAccede.getCreateTime()));
        }
        vo.setUserName(hjhAccede.getUserName());
        vo.setRefereeUserName(hjhAccede.getInviteUserName());
        vo.setInviteUserRegionname(hjhAccede.getInviteUserRegionname());
        vo.setInviteUserBranchname(hjhAccede.getInviteUserBranchname());
        vo.setInviteUserDepartmentname(hjhAccede.getInviteUserDepartmentname());

        Integer tenderUserUtmId=hjhAccede.getTenderUserUtmId();
        if(null!=tenderUserUtmId){
            UtmPlat utmPlat =utmPlatService.getUtmPlat(tenderUserUtmId);

            if(utmPlat!=null){
                vo.setUtmName(utmPlat.getSourceName());
            }else{
                throw new IllegalArgumentException(String.format("不存在渠道utmId=【%s】",tenderUserUtmId));
            }
        }

        UtmPlat utmPlatt=utmPlatService.getUtmByUserId(hjhAccede.getUserId());

        if(utmPlatt!=null){
            vo.setUtmNameNow(utmPlatt.getSourceName());
        }
    }

    @Override
    public int updateTenderUtm(TenderUtmChangeLog log) {
        HjhAccede hjhAccede=new HjhAccede();
        hjhAccede.setAccedeOrderId(log.getNid());
        hjhAccede.setTenderUserUtmId(log.getTenderUtmId());

        HjhAccedeExample example=new HjhAccedeExample();
        example.or().andAccedeOrderIdEqualTo(log.getNid());
        hjhAccedeMapper.updateByExampleSelective(hjhAccede,example);

        return tenderUtmChangeLogMapper.insertSelective(log);
    }
}
