package com.hyjf.am.trade.service.impl.repay;

import com.hyjf.am.resquest.trade.BorrowAuthRequest;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.trade.service.repay.BorrowAuthService;
import com.hyjf.am.vo.trade.repay.BorrowAuthCustomizeVO;
import com.hyjf.common.util.GetDate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 借款人受托支付
 * @author hesy
 * @version BorrowAuthService, v0.1 2018/7/6 10:33
 */
@Service
public class BorrowAuthServiceImpl extends BaseServiceImpl implements BorrowAuthService {
    /**标的状态7 */
    public static final Integer BORROW_STATUS_WITE_AUTHORIZATION = 7;
    /**是否受托支付 0否1 是 */
    public static final Integer ENTRUSTED_FLG = 1;

    /**
     * 获取借款人待授权总记录数
     * @param requestBean
     * @return
     */
    @Override
    public int countBorrowNeedAuthRecordTotal(BorrowAuthRequest requestBean) {
        // 条件  ：申请人用户ID   项目编号  项目添加时间
        Map<String, Object> params = getParams(requestBean);
        int total = borrowAuthCustomizeMapper.countBorrowNeedAuthRecordTotal(params);

        return total;
    }

    /**
     * 获取借款人待授权列表
     * @param requestBean
     * @return
     */
    @Override
    public List<BorrowAuthCustomizeVO> searchBorrowNeedAuthList(BorrowAuthRequest requestBean) {
        Map<String, Object> params = getParams(requestBean);
        params.put("limitStart", requestBean.getLimitStart());
        params.put("limitEnd", requestBean.getLimitEnd());

        return borrowAuthCustomizeMapper.searchBorrowNeedAuthList(params);
    }

    /**
     * 获取借款人已授权总记录数
     * @param requestBean
     * @return
     */
    @Override
    public int countBorrowAuthedRecordTotal(BorrowAuthRequest requestBean) {
        // 条件  ：申请人用户ID   项目编号  项目添加时间
        Map<String, Object> params = getParams(requestBean);
        return borrowAuthCustomizeMapper.countBorrowAuthedRecordTotal(params);
    }

    /**
     * 获取借款人已授权列表
     * @param requestBean
     * @return
     */
    @Override
    public List<BorrowAuthCustomizeVO> searchBorrowAuthedList(BorrowAuthRequest requestBean) {
        Map<String, Object> params = getParams(requestBean);
        params.put("limitStart", requestBean.getLimitStart());
        params.put("limitEnd", requestBean.getLimitEnd());

        return borrowAuthCustomizeMapper.searchBorrowAuthedList(params);
    }

    private Map<String, Object> getParams(BorrowAuthRequest requestBean) {
        Map<String, Object> params = new HashMap<String, Object>();
        String startDate = StringUtils.isNotEmpty(requestBean.getStartDate()) ? requestBean.getStartDate() : null;
        String endDate = StringUtils.isNotEmpty(requestBean.getEndDate()) ? requestBean.getEndDate() : null;
        String borrowNid = StringUtils.isNotEmpty(requestBean.getBorrowNid()) ? requestBean.getBorrowNid() : null;
        params.put("user_id", requestBean.getUserId());// 用户ID
        params.put("borrowNid", borrowNid); // 标的编号
        params.put("status", BorrowAuthServiceImpl.BORROW_STATUS_WITE_AUTHORIZATION); // 标的状态
        params.put("entrusted_flg", BorrowAuthServiceImpl.ENTRUSTED_FLG); // 是否受托支付

        if (StringUtils.isNotBlank(endDate)) {
            // 转化为时间戳
            endDate = GetDate.getSearchEndTime(endDate);
        }
        if (StringUtils.isNotBlank(startDate)) {
            // 转化为时间戳
            startDate = GetDate.getSearchStartTime(startDate);
        }
        System.out.println(startDate+"===="+endDate);
        params.put("startDate", startDate);
        params.put("endDate", endDate);

        return params;
    }

    /**
     * 受托支付申请回调更新
     * @param nid
     * @return
     */
    @Override
    public Integer updateTrusteePaySuccess(String nid) {
        // 修改huiyingdai_borrow表状态
        Borrow borrow = new Borrow();
        borrow.setBorrowNid(nid);
        borrow.setStatus(1);
        BorrowExample example = new BorrowExample();
        example.createCriteria().andBorrowNidEqualTo(nid).andStatusEqualTo(7);
        borrowMapper.updateByExampleSelective(borrow, example);

        // 更新受托支付申请完成时间
        BorrowInfo borrowInfo = new BorrowInfo();
        borrowInfo.setTrusteePayTime(GetDate.getNowTime10());// 受托支付完成时间
        BorrowInfoExample exampleInfo = new BorrowInfoExample();
        exampleInfo.createCriteria().andBorrowNidEqualTo(nid);
        borrowInfoMapper.updateByExample(borrowInfo,exampleInfo);

        // 修改hyjf_hjh_plan_asset
        HjhPlanAsset hp = new HjhPlanAsset();
        hp.setStatus(5);
        hp.setBorrowNid(nid);
        HjhPlanAssetExample hpexp = new HjhPlanAssetExample();
        hpexp.createCriteria().andBorrowNidEqualTo(nid);
        hjhPlanAssetMapper.updateByExampleSelective(hp, hpexp);
        return 1;
    }

    /**
     * 受托支付白名单查询
     * @auther: hesy
     * @date: 2018/7/6
     */
    @Override
    public StzhWhiteList getSTZHWhiteListByUserID(Integer userId, Integer stzUserId) {
        StzhWhiteListExample example = new StzhWhiteListExample();
        example.createCriteria().andStUserIdEqualTo(stzUserId).andUserIdEqualTo(userId);
        List<StzhWhiteList> lists = this.sTZHWhiteListMapper.selectByExample(example);
        if (lists != null && lists.size() > 0) {
            return lists.get(0);

        }
        return null;
    }


}
