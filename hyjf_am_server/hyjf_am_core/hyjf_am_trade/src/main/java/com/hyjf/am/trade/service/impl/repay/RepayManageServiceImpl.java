package com.hyjf.am.trade.service.impl.repay;

import com.hyjf.am.resquest.trade.RepayListRequest;
import com.hyjf.am.trade.dao.mapper.auto.BorrowTenderMapper;
import com.hyjf.am.trade.dao.model.auto.BorrowTender;
import com.hyjf.am.trade.dao.model.auto.BorrowTenderExample;
import com.hyjf.am.trade.dao.model.auto.TenderAgreement;
import com.hyjf.am.trade.dao.model.auto.TenderAgreementExample;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.trade.service.repay.RepayManageService;
import com.hyjf.am.vo.trade.coupon.MyCouponListCustomizeVO;
import com.hyjf.am.vo.trade.repay.RepayWaitListCustomizeVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 还款管理
 * @author hesy
 * @version RepayManageServiceImpl, v0.1 2018/6/26 18:15
 */
@Service
public class RepayManageServiceImpl extends BaseServiceImpl implements RepayManageService {

    /**
     * 检索待还款列表
     * @param requestBean
     * @return
     */
    public List<RepayWaitListCustomizeVO> selectRepayWaitList(RepayListRequest requestBean){
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("userId", requestBean.getUserId());
        param.put("startDate", requestBean.getStartDate());
        param.put("endDate", requestBean.getEndDate());
        param.put("repayTimeOrder", requestBean.getRepayTimeOrder());
        param.put("checkTimeOrder", requestBean.getCheckTimeOrder());
        param.put("borrowNid", requestBean.getBorrowNid());

        if (requestBean.getLimitStart() != null) {
            param.put("limitStart", requestBean.getLimitStart());
        }else {
            param.put("limitStart", -1);
        }
        if (requestBean.getLimitEnd() != null) {
            param.put("limitEnd", requestBean.getLimitEnd());
        }else {
            param.put("limitEnd", -1);
        }

        List<RepayWaitListCustomizeVO> list = repayManageCustomizeMapper.selectRepayWaitList(param);
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                BigDecimal accountFee = BigDecimal.ZERO;
                BigDecimal borrowTotal = BigDecimal.ZERO;
                BigDecimal realAccountTotal = BigDecimal.ZERO;
                BigDecimal allAccountFee = BigDecimal.ZERO;
                BigDecimal serviceFee = BigDecimal.ZERO;
                if (StringUtils.isNotBlank(list.get(i).getRepayFee())) {
                    accountFee = new BigDecimal(list.get(i).getRepayFee());
                }
                if (StringUtils.isNotBlank(list.get(i).getBorrowTotal())) {
                    borrowTotal = new BigDecimal(list.get(i).getBorrowTotal());
                }
                if (StringUtils.isNotBlank(list.get(i).getRealAccountYes())) {
                    realAccountTotal = new BigDecimal(list.get(i).getRealAccountYes());
                }
                if (StringUtils.isNotBlank(list.get(i).getAllRepayFee())) {
                    allAccountFee = new BigDecimal(list.get(i).getAllRepayFee());
                }
                if (StringUtils.isNotBlank(list.get(i).getServiceFee())) {
                    serviceFee = new BigDecimal(list.get(i).getServiceFee());
                }
                BigDecimal oldYesAccount = new BigDecimal(list.get(i).getYesAccount());
                BigDecimal yesAccount = oldYesAccount.subtract(serviceFee);
                list.get(i).setYesAccount(yesAccount.toString());
                list.get(i).setBorrowTotal(borrowTotal.add(allAccountFee).toString());
                list.get(i).setRealAccountYes(realAccountTotal.add(accountFee).toString());
            }
        }

        for(RepayWaitListCustomizeVO record : list){
            List<BorrowTender> tenderList = this.getBorrowTender(record.getBorrowNid());
            for(BorrowTender tender : tenderList){
                List<TenderAgreement> agreementList = this.getTenderAgreement(tender.getNid());
                if(agreementList !=null && !agreementList.isEmpty()){
                    TenderAgreement tenderAgreement = agreementList.get(0);
                    Integer fddStatus = tenderAgreement.getStatus();
                    //法大大协议生成状态：0:初始,1:成功,2:失败，3下载成功
                    if(fddStatus.equals(3)){
                        record.setFddStatus(1);
                    }else {
                        //隐藏下载按钮
                        record.setFddStatus(0);
                    }
                }else {
                    //下载老版本协议
                    record.setFddStatus(1);
                }
            }
        }

        return list;
    }

    /**
     * 获取borrowTender列表
     */
    private List<BorrowTender> getBorrowTender(String borrowNid){
        BorrowTenderExample example = new BorrowTenderExample();
        example.createCriteria().andBorrowNidEqualTo(borrowNid);
        List<BorrowTender> resultList = borrowTenderMapper.selectByExample(example);

        return resultList;
    }

    public List<TenderAgreement> getTenderAgreement(String tenderNid) {
        TenderAgreementExample example = new TenderAgreementExample();
        example.createCriteria().andTenderNidEqualTo(tenderNid);
        List<TenderAgreement> tenderAgreements= this.tenderAgreementMapper.selectByExample(example);

        if(tenderAgreements != null && tenderAgreements.size()>0){
            return tenderAgreements;
        }
        return null;
    }
}
