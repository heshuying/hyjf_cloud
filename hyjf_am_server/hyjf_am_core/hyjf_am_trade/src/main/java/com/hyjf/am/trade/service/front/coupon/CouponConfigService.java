/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.front.coupon;

import com.hyjf.am.resquest.admin.CouponConfigRequest;
import com.hyjf.am.trade.dao.model.auto.CouponConfig;
import com.hyjf.am.trade.dao.model.customize.CouponConfigCustomize;
import com.hyjf.am.trade.dao.model.customize.CouponConfigExportCustomize;
import com.hyjf.am.vo.admin.TransferExceptionLogVO;
import com.hyjf.am.vo.admin.coupon.CouponRecoverVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.coupon.CouponConfigVO;
import com.hyjf.am.vo.trade.coupon.CouponTenderCustomizeVO;

import java.util.List;
import java.util.Map;

/**
 * @author yaoy
 * @version CouponConfigService, v0.1 2018/6/19 19:25
 */
public interface CouponConfigService {
    /**
     * 根据优惠券编号查找优惠券配置
     * @param couponCode
     * @return
     */
    CouponConfig selectCouponConfig(String couponCode);

    /**
     * 获取记录数
     * @param mapParam
     * @return
     */
    int countRecord(Map<String, Object> mapParam);

    /**
     * 获取列表
     * @param mapParam
     * @param offset
     * @param limit
     * @return
     */
    List<CouponConfigCustomize> getRecordList(Map<String, Object> mapParam, int offset, int limit);

    /**
     * 获取编辑页面
     * @param id
     * @return
     */
    CouponConfig getCouponConfig(int id);

    /**
     * 保存编辑信息
     * @param couponConfig
     * @return
     */
    int saveCouponConfig(CouponConfig couponConfig);

    /**
     * 添加发行优惠券信息
     * @param couponConfig
     * @return
     */
    int insertAction(CouponConfig couponConfig);

    /**
     *删除发行信息
     * @param id
     * @return
     */
    int deleteCouponConfig(int id);

    /**
     * 查询优惠券已发行数量
     * @param couponCode
     * @return
     */
    int checkCouponSendExcess(String couponCode);

    CouponConfigVO getCouponConfigByOrderId(String ordId);

    /**
     * @Author walter.limeng
     * @Description   根据tenderNid查询放款优惠券总数
     * @Date 14:00 2018/7/17
     * @Param tenderNid
     * @return
     */
    Integer countByTenderId(String tenderNid);

    /**
     * @Author walter.limeng
     * @Description  更新还款期
     * @Date 14:15 2018/7/17
     * @Param tenderNid 投资订单编号
     * @Param currentRecoverFlg 0:非还款期，1;还款期
     * @Param period 期数
     * @return
     */
    Integer crRecoverPeriod(String tenderNid, Integer currentRecoverFlg, Integer period);

    /**
     * @Author walter.limeng
     * @Description  根据nid 取得体验金收益期限
     * @Date 14:33 2018/7/17
     * @Param tenderNid
     * @return
     */
    Integer getCouponProfitTime(String tenderNid);

    /**
     * @Author walter.limeng
     * @Description  保存CouponRecover
     * @Date 14:45 2018/7/17
     * @Param CouponRecoverVO
     * @return
     */
    Integer insertCouponRecover(CouponRecoverVO cr);

    /**
     * @Author walter.limeng
     * @Description  保存CouponRecover
     * @Date 14:49 2018/7/17
     * @Param account
     * @return
     */
    Integer updateOfLoansTenderHjh(AccountVO account);

    /**
     * @Author walter.limeng
     * @Description  根据orderId查询所有待还款优惠券
     * @Date 17:02 2018/7/17
     * @Param orderId
     * @return
     */
    List<CouponTenderCustomizeVO> getCouponTenderListHjh(String orderId);

    /**
     * @Author walter.limeng
     * @Description  更新couponRecover对象
     * @Date 9:36 2018/7/18
     * @Param CouponRecoverVO
     * @return
     */
    void updateCouponRecover(CouponRecoverVO cr);

    /**
     * @Author walter.limeng
     * @Description  根据recoverId查询交易记录
     * @Date 9:45 2018/7/18
     * @Param recoverId
     * @return
     */
    List<TransferExceptionLogVO> selectByRecoverId(Integer recoverId);

    /**
     * @Author walter.limeng
     * @Description  新增交易日志数据
     * @Date 9:56 2018/7/18
     * @Param transferExceptionLog
     * @return
     */
    Integer insertTransferExLog(TransferExceptionLogVO transferExceptionLog);

    /**
     * @Author walter.limeng
     * @Description  根据borrowNid查询所有优惠券散标还款
     * @Date 16:48 2018/7/18
     * @Param borrowNid
     * @return
     */
    List<CouponTenderCustomizeVO> getCouponTenderList(String borrowNid);

    /**
     * @Author walter.limeng
     * @Description  更新还款期
     * @Date 17:11 2018/7/18
     * @Param tenderNid
     * @Param period
     * @return
     */
    Integer updateRecoverPeriod(String tenderNid, Integer period);

    String selectCouponInterestTotal(Integer userId);

    /**
     * 获取某用户优惠券累计收益总和
     * @param userId
     * @return
     */
    String selectCouponReceivedInterestTotal(Integer userId);

    /**
     * 获取后台优惠券发行配置列表
     * @param request
     * @return
     */
    List<CouponConfigCustomize> getCouponConfigList(CouponConfigRequest request);

    /**
     * 加载优惠券配置列表
     * @param couponConfigCustomize
     * @return
     */
    List<CouponConfigCustomize> getConfigCustomizeList(CouponConfigCustomize couponConfigCustomize);

    /**
     * 查询导出列表
     * @param configCustomize
     * @return
     */
    List<CouponConfigExportCustomize> exoportRecordList(CouponConfigCustomize configCustomize);
}
