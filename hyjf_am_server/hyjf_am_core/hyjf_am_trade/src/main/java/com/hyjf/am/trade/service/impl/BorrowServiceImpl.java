/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.impl;

import com.alibaba.fastjson.JSON;
import com.hyjf.am.resquest.trade.BorrowRegistRequest;
import com.hyjf.am.resquest.trade.TenderRequest;
import com.hyjf.am.resquest.user.BorrowFinmanNewChargeRequest;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.service.BorrowService;
import com.hyjf.am.vo.trade.ProjectCompanyDetailVO;
import com.hyjf.am.vo.trade.ProjectCustomeDetailVO;
import com.hyjf.am.vo.trade.WebProjectPersonDetailVO;
import com.hyjf.am.vo.trade.borrow.BorrowVO;
import com.hyjf.common.util.GetDate;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author fuqiang
 * @version BorrowServiceImpl, v0.1 2018/6/13 18:53
 */
@Service
public class BorrowServiceImpl extends BaseServiceImpl implements BorrowService {

    @Override
    public BorrowFinmanNewCharge selectBorrowApr(BorrowFinmanNewChargeRequest request) {
        BorrowFinmanNewChargeExample example = new BorrowFinmanNewChargeExample();
        BorrowFinmanNewChargeExample.Criteria cra = example.createCriteria();
        cra.andProjectTypeEqualTo(request.getBorrowClass());
        cra.andInstCodeEqualTo(request.getInstCode());
        cra.andAssetTypeEqualTo(request.getAssetType());
        cra.andManChargeTimeTypeEqualTo(request.getQueryBorrowStyle());
        cra.andManChargeTimeEqualTo(request.getBorrowPeriod());
        cra.andStatusEqualTo(0);

        List<BorrowFinmanNewCharge> list = this.borrowFinmanNewChargeMapper.selectByExample(example);

        if (!CollectionUtils.isEmpty(list)) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public BorrowStyle getborrowStyleByNid(String borrowStyle) {
        BorrowStyleExample example = new BorrowStyleExample();
        BorrowStyleExample.Criteria cri = example.createCriteria();
        cri.andNidEqualTo(borrowStyle);
        List<BorrowStyle> style = borrowStyleMapper.selectByExample(example);
        return style.get(0);
    }

    public BorrowConfig getBorrowConfigByConfigCd(String configCd) {
        BorrowConfig borrowConfig = this.borrowConfigMapper.selectByPrimaryKey(configCd);
        return borrowConfig;
    }

    @Override
    public int insertBorrow(Borrow borrow) {
        return borrowMapper.insertSelective(borrow);
    }

    @Override
    public int insertBorrowManinfo(BorrowManinfo borrowManinfo) {
         return borrowManinfoMapper.insertSelective(borrowManinfo);
    }

    @Override
    public int updateBorrowRegist(BorrowRegistRequest request) {
        BorrowVO borrowVO = request.getBorrowVO();
        int status = request.getStatus();
        int registStatus = request.getRegistStatus();
        Date nowDate = new Date();
//		AdminSystem adminSystem = (AdminSystem) SessionUtils.getSession(CustomConstants.LOGIN_USER_INFO);
        BorrowExample example = new BorrowExample();
        example.createCriteria().andIdEqualTo(borrowVO.getId()).andStatusEqualTo(borrowVO.getStatus()).andRegistStatusEqualTo(borrowVO.getRegistStatus());
        borrowVO.setRegistStatus(registStatus);
        borrowVO.setStatus(status);
        borrowVO.setRegistUserId(1);//TODO:id写死1
        borrowVO.setRegistUserName("AutoRecord");
        borrowVO.setRegistTime(nowDate);
        Borrow borrow = new Borrow();
        BeanUtils.copyProperties(borrowVO, borrow);
        return borrowMapper.updateByExampleSelective(borrow, example);
    }

    /**
     * 检索正在还款中的标的
     * @return
     */
    @Override
    public List<Borrow> selectBorrowList() {
        BorrowExample example = new BorrowExample();
        BorrowExample.Criteria cra = example.createCriteria();
        cra.andStatusEqualTo(3);
        cra.andRepayFullStatusEqualTo(0);
        List<Borrow> list = this.borrowMapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            return list;
        }
        return null;
    }

    /**
     * 检索逾期的还款标的
     */
	@Override
	public List<Borrow> selectOverdueBorrowList() {
		BorrowExample example = new BorrowExample();
    	example.createCriteria().andRepayLastTimeLessThanOrEqualTo(GetDate.getDayEnd10(GetDate.getTodayBeforeOrAfter(-1))).andStatusEqualTo(4).andPlanNidIsNull();
    	List<Borrow> borrows = borrowMapper.selectByExample(example);
    	if(CollectionUtils.isNotEmpty(borrows)){
    		return borrows;
    	}
    	return null;
	}

    /**
     * 投资之前插入tmp表
     *
     * @param tenderRequest
     * @return
     */
    @Override
    public int insertBeforeTender(TenderRequest tenderRequest) {
        Integer userId = tenderRequest.getUser().getUserId();
        BorrowTenderTmp temp = new BorrowTenderTmp();
        temp.setUserId(userId);
        temp.setBorrowNid(tenderRequest.getBorrowNid());
        temp.setNid(tenderRequest.getOrderId());
        temp.setAccount(new BigDecimal(tenderRequest.getAccount()));
        temp.setAddIp(tenderRequest.getIp());
       /* temp.setChangeStatus(0);
        temp.setChangeUserid(0);
        temp.setChangePeriod(0);
        temp.setTenderStatus(0);
        temp.setTenderNid(borrowNid);
        temp.setTenderAwardAccount(new BigDecimal(0));*/
        temp.setRecoverFullStatus(0);
        temp.setRecoverFee(new BigDecimal(0));
        /*temp.setRecoverType("");*/
        temp.setRecoverAdvanceFee(new BigDecimal(0));
        temp.setRecoverLateFee(new BigDecimal(0));
        /*temp.setTenderAwardFee(new BigDecimal(0));
        temp.setContents("");
        temp.setAutoStatus(0);
        temp.setWebStatus(0);
        temp.setPeriodStatus(0);
        temp.setWeb(0);*/
        temp.setIsBankTender(1);
        Integer couponGrantId = tenderRequest.getCouponGrantId();
        if (couponGrantId==null) {
            couponGrantId = 0;
        }
        temp.setCouponGrantId(couponGrantId);// 为投资完全掉单优惠券投资时修复做记录
        boolean tenderTmpFlag = borrowTenderTmpMapper.insertSelective(temp) > 0 ? true : false;
        if (!tenderTmpFlag) {
            throw new RuntimeException("插入borrowTenderTmp表失败，投资订单号：" + tenderRequest.getOrderId());
        }
        BorrowTenderTmpinfo info = new BorrowTenderTmpinfo();
        info.setOrdid(tenderRequest.getOrderId());
        Map<String, String> map = new HashMap<String, String>();
        map.put("borrow_nid", tenderRequest.getBorrowNid());
        map.put("user_id", userId + "");
        map.put("account", tenderRequest.getAccount() + "");
        map.put("status", "0");
        map.put("nid", tenderRequest.getOrderId());
        map.put("addtime", (new Date().getTime() / 1000) + "");
        map.put("addip", tenderRequest.getIp());
        String array = JSON.toJSONString(map);
        info.setTmpArray(array);
        Boolean tenderTmpInfoFlag = borrowTenderTmpinfoMapper.insertSelective(info) > 0 ? true : false;
        if (!tenderTmpInfoFlag) {
            throw new RuntimeException("插入borrowTenderTmpInfo表失败，投资订单号：" + tenderRequest.getOrderId());
        }
        return 1;
    }

    @Override
    public ProjectCustomeDetailVO getProjectDetail(String borrowNid) {
        return borrowCustomizeMapper.getProjectDetail(borrowNid);
    }

    @Override
    public ProjectCompanyDetailVO getProjectCompany(String borrowNid) {
        return borrowCustomizeMapper.getProjectCompanyDetail(borrowNid);
    }

    @Override
    public WebProjectPersonDetailVO getProjectPerson(String borrowNid) {
        return borrowCustomizeMapper.getProjectPsersonDetail(borrowNid);
    }
}
