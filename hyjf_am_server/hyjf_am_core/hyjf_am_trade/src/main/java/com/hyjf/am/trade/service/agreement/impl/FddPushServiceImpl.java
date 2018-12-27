package com.hyjf.am.trade.service.agreement.impl;

import com.hyjf.am.bean.fdd.FddGenerateContractBean;
import com.hyjf.am.resquest.admin.BorrowApicronRequest;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.mq.base.CommonProducer;
import com.hyjf.am.trade.mq.base.MessageContent;
import com.hyjf.am.trade.service.agreement.FddPushService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.trade.BorrowRecoverPlanVO;
import com.hyjf.am.vo.trade.borrow.BorrowApicronVO;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.GetDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @Auther:yangchangwei
 * @Date:2018/12/26
 * @Description:
 */
@Service
public class FddPushServiceImpl extends BaseServiceImpl implements FddPushService{

    @Autowired
    private CommonProducer commonProducer;

    Logger _log = LoggerFactory.getLogger(this.getClass());

    @Override
    public List<BorrowApicronVO> getFddPushBorrowList() {
        BorrowApicronExample example = new BorrowApicronExample();
        example.createCriteria().andApiTypeEqualTo(0).andStatusEqualTo(6).andAgreementStatusEqualTo(0);
        List<BorrowApicron> borrowApicrons = this.borrowApicronMapper.selectByExample(example);
        List<BorrowApicronVO> borrowApicronVOS = CommonUtils.convertBeanList(borrowApicrons, BorrowApicronVO.class);
        return borrowApicronVOS;
    }

    /**
     * 开始推送法大大协议
     * @param borrowApicron
     */
    @Override
    public void updateFddPush(BorrowApicronRequest borrowApicron) {
        String borrowNid = borrowApicron.getBorrowNid();
        _log.info("----------开始推送放款后的法大大协议，标的号：" + borrowNid + "-------");
        BorrowRecoverExample example = new BorrowRecoverExample();
        example.createCriteria().andBorrowNidEqualTo(borrowNid);
        List<BorrowRecover> borrowRecovers = this.borrowRecoverMapper.selectByExample(example);
        if(borrowRecovers != null && borrowRecovers.size() > 0){
            boolean falg = true;
            for (int i = 0; i < borrowRecovers.size(); i++) {
                BorrowRecover recover = borrowRecovers.get(i);
                boolean isRepeat = getIsRepeat(recover.getNid());
                if(isRepeat){
                    try {
                        Integer userId = recover.getUserId();
                        String signDate = GetDate.getDataString(GetDate.date_sdf);
                        FddGenerateContractBean bean = new FddGenerateContractBean();
                        bean.setTenderUserId(userId);
                        bean.setOrdid(recover.getNid());
                        bean.setTransType(1);
                        bean.setBorrowNid(borrowNid);
                        bean.setSignDate(signDate);
                        bean.setTenderType(0);
                        bean.setTenderInterest(recover.getRecoverInterest());
                        commonProducer.messageSend(new MessageContent(MQConstant.FDD_TOPIC,
                                MQConstant.FDD_GENERATE_CONTRACT_TAG, UUID.randomUUID().toString(), bean));
                    }catch (Exception e){
                        falg = false;
                        _log.info("-----------------生成居间服务协议失败，标的号：" + borrowNid + "ordid:" + recover.getNid() + ",异常信息：" + e.getMessage());
                    }
                }

            }
            if(falg){
                BorrowApicron record = new BorrowApicron();
                record.setId(borrowApicron.getId());
                record.setAgreementStatus(1);
                this.borrowApicronMapper.updateByPrimaryKeySelective(record);
            }

        }
        _log.info("-----------放款推送法大大协议完成，标的号：" + borrowNid + "---------");
    }

    @Override
    public BorrowRecoverPlanVO getBorrowRecoverPlanByNidandPeriod(BorrowRecoverPlanVO planinfo) {

       BorrowRecoverPlanExample example = new BorrowRecoverPlanExample();
		example.createCriteria().andBorrowNidEqualTo(planinfo.getBorrowNid()).andNidEqualTo(planinfo.getNid())
                .andRecoverPeriodEqualTo(planinfo.getRecoverPeriod());
		List<BorrowRecoverPlan> borrowRecoverPlans = this.borrowRecoverPlanMapper.selectByExample(example);
		if(borrowRecoverPlans != null && borrowRecoverPlans.size() > 0){
            BorrowRecoverPlan borrowRecoverPlan = borrowRecoverPlans.get(0);
            BorrowRecoverPlanVO borrowRecoverPlanVO = CommonUtils.convertBean(borrowRecoverPlan, BorrowRecoverPlanVO.class);
            return borrowRecoverPlanVO;
        }
        return null;
    }

    /**
     * 判断该条投资数据是否生成合同
     * @param nid
     * @return true 没有生成 false 已重复
     */
    private boolean getIsRepeat(String nid) {

        TenderAgreementExample example = new TenderAgreementExample();
        example.createCriteria().andTenderNidEqualTo(nid);
        List<TenderAgreement> tenderAgreements = this.tenderAgreementMapper.selectByExample(example);
        if(tenderAgreements != null && tenderAgreements.size() > 0){
            return false;
        }
        return true;
    }
}
