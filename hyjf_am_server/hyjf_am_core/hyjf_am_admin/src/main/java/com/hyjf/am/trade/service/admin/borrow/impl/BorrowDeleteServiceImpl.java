/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin.borrow.impl;

import com.hyjf.am.admin.mq.base.CommonProducer;
import com.hyjf.am.response.Response;
import com.hyjf.am.resquest.admin.BailConfigAddRequest;
import com.hyjf.am.resquest.admin.BorrowRegistUpdateRequest;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.service.admin.borrow.BorrowDeleteService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.admin.BorrowDeleteConfirmCustomizeVO;
import com.hyjf.common.util.GetDate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 标的删除
 * @author hesy
 */
@Service
public class BorrowDeleteServiceImpl extends BaseServiceImpl implements BorrowDeleteService {

    @Resource
    private CommonProducer commonProducer;

    /**
     * 标的删除确认页面数据获取
     * @param borrowNid
     * @return
     */
    @Override
    public BorrowDeleteConfirmCustomizeVO selectDeleteConfirm(String borrowNid){
        return borrowDeleteCustomizeMapper.selectDeleteConfirm(borrowNid);
    }

    /**
     * 标的删除成功后删除对应标的数据
     */
    @Override
    public Response deleteBorrow(BorrowRegistUpdateRequest requestBean) {
        BorrowInfoWithBLOBs borrowInfo = this.getBorrowInfoByNid(requestBean.getBorrowNid());
        Borrow borrow = this.getBorrow(requestBean.getBorrowNid());

        // 删除borrow表标的数据
        BorrowExample borrowExample = new BorrowExample();
        borrowExample.createCriteria().andBorrowNidEqualTo(requestBean.getBorrowNid());
        borrowMapper.deleteByExample(borrowExample);

        // 删除borrow_info表标的数据
        BorrowInfoExample borrowInfoExample = new BorrowInfoExample();
        borrowInfoExample.createCriteria().andBorrowNidEqualTo(requestBean.getBorrowNid());
        borrowInfoMapper.deleteByExample(borrowInfoExample);

        // 删除borrow_maninfo表标的数据
        BorrowManinfoExample borrowManinfoExample = new BorrowManinfoExample();
        borrowManinfoExample.createCriteria().andBorrowNidEqualTo(requestBean.getBorrowNid());
        borrowManinfoMapper.deleteByExample(borrowManinfoExample);

        // 删除borrow_user表标的数据
        BorrowUserExample borrowUserExample = new BorrowUserExample();
        borrowUserExample.createCriteria().andBorrowNidEqualTo(requestBean.getBorrowNid());
        borrowUserMapper.deleteByExample(borrowUserExample);

        // 删除borrow_carinfo表标的数据
        BorrowCarinfoExample borrowCarinfoExample = new BorrowCarinfoExample();
        borrowCarinfoExample.createCriteria().andBorrowNidEqualTo(requestBean.getBorrowNid());
        borrowCarinfoMapper.deleteByExample(borrowCarinfoExample);

        // 删除borrow_houses表标的数据
        BorrowHousesExample borrowHousesExample = new BorrowHousesExample();
        borrowHousesExample.createCriteria().andBorrowNidEqualTo(requestBean.getBorrowNid());
        borrowHousesMapper.deleteByExample(borrowHousesExample);

        // 删除borrow_company_authen表标的数据
        BorrowCompanyAuthenExample borrowCompanyAuthenExample = new BorrowCompanyAuthenExample();
        borrowCompanyAuthenExample.createCriteria().andBorrowNidEqualTo(requestBean.getBorrowNid());
        borrowCompanyAuthenMapper.deleteByExample(borrowCompanyAuthenExample);

        // 添加标的修改日志
        BorrowLog borrowLog = new BorrowLog();
        borrowLog.setBorrowNid(requestBean.getBorrowNid());
//                    String statusNameString = getBorrowStatusName(borrowBean.getStatus());
//                    borrowLog.setBorrowStatus(statusNameString);
        borrowLog.setBorrowStatusCd(borrow.getStatus());
        borrowLog.setBorrowStatus(getBorrowStatusName(borrow.getStatus()));
        borrowLog.setType("删除");
        borrowLog.setCreateTime(new Date());
        borrowLog.setCreateUserId(Integer.parseInt(requestBean.getCurrUserId()));
        borrowLog.setCreateUserName(requestBean.getCurrUserName());
        borrowLogMapper.insertSelective(borrowLog);

        // 更新保证金相关
        if(StringUtils.isNotBlank(borrowInfo.getInstCode())){
            // 获取该机构保证金配置
            HjhBailConfig hjhBailConfig = selectHjhBailConfigByInstCode(borrowInfo.getInstCode());
            if (null != hjhBailConfig) {
                // 插入异常标的
                // 异常标的信息
                BorrowDelete borrowDelete = new BorrowDelete();
                borrowDelete.setInstCode(borrowInfo.getInstCode());
                borrowDelete.setBorrowNid(borrow.getBorrowNid());
                borrowDelete.setAccount(borrow.getAccount());
                borrowDelete.setExceptionType(1);
                borrowDelete.setExceptionRemark("");
                borrowDelete.setStatus(borrow.getStatus());
                borrowDelete.setExceptionTime(GetDate.formatTime(new Date()));
                borrowDelete.setCreateUserId(Integer.parseInt(requestBean.getCurrUserId()));
                borrowDelete.setCreateTime(new Date());
                borrowDeleteMapper.insertSelective(borrowDelete);

                // 发标已发额度
                hjhBailConfig.setLoanMarkLine(hjhBailConfig.getLoanMarkLine().subtract(borrow.getAccount()));
                // loan_balance在贷余额
                hjhBailConfig.setLoanBalance(hjhBailConfig.getLoanBalance().subtract(borrow.getAccount()));
                // 发标额度余额remain_mark_line
                hjhBailConfig.setRemainMarkLine(hjhBailConfig.getRemainMarkLine().add(borrow.getAccount()));

                // 周期内发标已发额度
                BigDecimal sendedAccountByCycBD = BigDecimal.ZERO;
                String sendedAccountByCyc = this.selectSendedAccountByCyc(borrowInfo.getInstCode(), hjhBailConfig.getTimestart(), hjhBailConfig.getTimeend());
                if (StringUtils.isNotBlank(sendedAccountByCyc)) {
                    sendedAccountByCycBD = new BigDecimal(sendedAccountByCyc);
                }
                hjhBailConfig.setCycLoanTotal(sendedAccountByCycBD);

                this.hjhBailConfigMapper.updateByPrimaryKey(hjhBailConfig);
            }

        }

        return new Response();
    }

    /**
     * 标的状态值转化为文字描述
     * @param status
     * @return
     */
    private String getBorrowStatusName(Integer status) {
        String statusName = "";
        if(status == null){
            return statusName;
        }
        switch (status) {
            case 0:
                statusName = "备案中";
                break;
            case 1:
                statusName = "初审中";
                break;
            case 2:
                statusName = "投资中";
                break;
            case 3:
                statusName = "复审中";
                break;
            case 4:
                statusName = "还款中";
                break;
            case 5:
                statusName = "已还款";
                break;
            case 6:
                statusName = "流标";
                break;
            default:
                statusName = "";
        }

        return statusName;
    }

    /**
     * 根据资产来源获取保证金详情
     *
     * @param instCode
     * @return
     */
    private HjhBailConfig selectHjhBailConfigByInstCode(String instCode) {
        HjhBailConfigExample example = new HjhBailConfigExample();
        example.createCriteria().andInstCodeEqualTo(instCode).andDelFlgEqualTo(0);
        List<HjhBailConfig> hjhBailConfigList = this.hjhBailConfigMapper.selectByExample(example);
        if (null != hjhBailConfigList && hjhBailConfigList.size() > 0) {
            return hjhBailConfigList.get(0);
        }
        return null;
    }

    /**
     * 获取周期内发标已发额度
     *
     * @param instCode
     * @return
     */
    private String selectSendedAccountByCyc(String instCode, String timeStart, String timeEnd) {
        BailConfigAddRequest bailConfigAddRequest = new BailConfigAddRequest();
        bailConfigAddRequest.setInstCode(instCode);
        bailConfigAddRequest.setTimestart(timeStart);
        bailConfigAddRequest.setTimeend(timeEnd);
        return this.hjhBailConfigCustomizeMapper.selectSendedAccountByCyc(bailConfigAddRequest);
    }
}
