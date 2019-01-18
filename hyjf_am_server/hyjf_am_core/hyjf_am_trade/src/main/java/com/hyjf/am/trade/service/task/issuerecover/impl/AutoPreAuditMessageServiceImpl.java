package com.hyjf.am.trade.service.task.issuerecover.impl;

import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.trade.service.task.issuerecover.AutoPreAuditMessageService;
import com.hyjf.common.util.GetDate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * @Auther: walter.limeng
 * @Date: 2018/7/12 09:24
 * 自动初审消息
 * @Description: AutoPreAuditMessageServiceImpl
 */
@Service
public class AutoPreAuditMessageServiceImpl extends BaseServiceImpl implements AutoPreAuditMessageService {

    @Override
    public boolean updateRecordBorrow(Borrow borrow, BorrowInfo borrowInfo) {

        // 插入时间
        int systemNowDateLong = GetDate.getNowTime10();
        Date systemNowDate = GetDate.getDate(systemNowDateLong);
        if (borrow != null && borrow.getId() != null) {
            // 剩余的金额
            borrow.setBorrowAccountWait(borrow.getAccount());
            int time = systemNowDateLong;

            // 是否可以进行借款
            borrow.setBorrowStatus(1);
            // 初审时间
            borrow.setVerifyTime(GetDate.getNowTime10());
            // 发标的状态
            borrow.setVerifyStatus(Integer.valueOf(4));
            // 状态
            borrow.setStatus(2);
            // 借款到期时间
            borrow.setBorrowEndTime(String.valueOf(time + borrowInfo.getBorrowValidTime() * 86400));
            // 更新时间
            borrow.setUpdatetime(systemNowDate);
            this.borrowMapper.updateByPrimaryKey(borrow);

            return true;

        }

        return false;
    }

    @Override
	public HjhPlanAsset selectPlanAsset(String assetId, String instCode) {
		HjhPlanAssetExample example = new HjhPlanAssetExample();
		HjhPlanAssetExample.Criteria crt = example.createCriteria();
		if (StringUtils.isNotBlank(assetId)) {
			crt.andAssetIdEqualTo(assetId);
		}
		if (StringUtils.isNotBlank(instCode)) {
			crt.andInstCodeEqualTo(instCode);
		}
		List<HjhPlanAsset> list = hjhPlanAssetMapper.selectByExample(example);
		return CollectionUtils.isEmpty(list) ? null : list.get(0);
	}

    @Override
    public HjhAssetBorrowtype selectAssetBorrowType(HjhPlanAsset hjhPlanAsset) {
        HjhAssetBorrowtypeExample example = new HjhAssetBorrowtypeExample();
        HjhAssetBorrowtypeExample.Criteria cra = example.createCriteria();
        cra.andInstCodeEqualTo(hjhPlanAsset.getInstCode());
        cra.andAssetTypeEqualTo(hjhPlanAsset.getAssetType());
        cra.andIsOpenEqualTo(1);

        List<HjhAssetBorrowtype> list = this.hjhAssetBorrowTypeMapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public boolean updateRecordBorrow(HjhPlanAsset hjhPlanAsset, HjhAssetBorrowtype hjhAssetBorrowType) {

        //修改发标状态 更新资产表，更新borrow
        if(hjhAssetBorrowType.getAutoAudit() != null && hjhAssetBorrowType.getAutoAudit() == 1){

            updateOntimeRecord(hjhPlanAsset);

            HjhPlanAsset hjhPlanAssetnew = new HjhPlanAsset();
            hjhPlanAssetnew.setId(hjhPlanAsset.getId());
            hjhPlanAssetnew.setStatus(7);//出借中
            //获取当前时间
            hjhPlanAssetnew.setUpdateTime(new Date());
            hjhPlanAssetnew.setUpdateUserId(1);
            boolean borrowFlag = this.hjhPlanAssetMapper.updateByPrimaryKeySelective(hjhPlanAssetnew)>0?true:false;
            if(borrowFlag){
                return true;
            }
        }
        return false;
    }

    /**
     * 发标，更新状态
     *
     * @param hjhPlanAsset
     */
    private boolean updateOntimeRecord(HjhPlanAsset hjhPlanAsset) {
        String borrowNid = hjhPlanAsset.getBorrowNid();

        // 插入时间
        int systemNowDateLong = GetDate.getNowTime10();
        Date systemNowDate = GetDate.getDate(systemNowDateLong);

        Borrow borrow = getBorrowByNid(borrowNid);
        if (borrow!=null) {
            BorrowInfo borrowInfo = getBorrowInfoByNid(borrowNid);
            // 剩余的金额
            borrow.setBorrowAccountWait(borrow.getAccount());
            int time = systemNowDateLong;

            // 是否可以进行借款
            borrow.setBorrowStatus(1);
            // 初审时间
            borrow.setVerifyTime(GetDate.getNowTime10());
            // 发标的状态
            borrow.setVerifyStatus(Integer.valueOf(4));
            // 状态
            borrow.setStatus(2);
            // 借款到期时间
            borrow.setBorrowEndTime(String.valueOf(time + (borrowInfo==null?0:borrowInfo.getBorrowValidTime() * 86400)));
            // 更新时间
            borrow.setUpdatetime(systemNowDate);
            this.borrowMapper.updateByPrimaryKeySelective(borrow);
            return true;
        }

        return false;
    }

    /**
     * 获取系统配置
     *
     * @return
     */
    @Override
    public String getBorrowConfig(String configCd) {
        BorrowConfig borrowConfig = this.borrowConfigMapper.selectByPrimaryKey(configCd);
        return borrowConfig.getConfigValue();
    }
}
