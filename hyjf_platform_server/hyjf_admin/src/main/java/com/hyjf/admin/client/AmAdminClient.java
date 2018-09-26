package com.hyjf.admin.client;

import com.hyjf.admin.beans.request.DadaCenterCouponRequestBean;
import com.hyjf.admin.beans.request.PlatformCountRequestBean;
import com.hyjf.admin.beans.request.STZHWhiteListRequestBean;
import com.hyjf.am.response.BooleanResponse;
import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.response.admin.AdminSubConfigResponse;
import com.hyjf.am.response.admin.BatchBorrowRecoverReponse;
import com.hyjf.am.response.admin.HjhDebtCreditReponse;
import com.hyjf.am.response.admin.PlatformCountCustomizeResponse;
import com.hyjf.am.response.admin.locked.LockedUserMgrResponse;
import com.hyjf.am.response.admin.promotion.PlatformUserCountCustomizeResponse;
import com.hyjf.am.response.trade.BorrowApicronResponse;
import com.hyjf.am.response.trade.STZHWhiteListResponse;
import com.hyjf.am.response.user.ChannelStatisticsDetailResponse;
import com.hyjf.am.resquest.admin.*;
import com.hyjf.am.resquest.admin.locked.LockedeUserListRequest;
import com.hyjf.am.resquest.user.ChannelStatisticsDetailRequest;
import com.hyjf.am.vo.admin.AdminPermissionsVO;
import com.hyjf.am.vo.admin.PoundageCustomizeVO;
import com.hyjf.am.vo.admin.PoundageDetailVO;
import com.hyjf.am.vo.admin.PoundageLedgerVO;
import com.hyjf.am.vo.admin.coupon.DataCenterCouponCustomizeVO;
import com.hyjf.am.vo.admin.locked.LockedUserInfoVO;
import com.hyjf.am.vo.config.ParamNameVO;
import com.hyjf.am.vo.trade.borrow.BorrowStyleVO;
import com.hyjf.am.vo.user.HjhInstConfigVO;
import com.hyjf.am.vo.user.UtmPlatVO;

import java.util.List;

/**
 * @author tanyy
 * @version AmAdminClient, v0.1 2018/8/19 12:44
 */
public interface AmAdminClient {

    /**
     * count PC统计明细列表
     *
     * @return
     */
     IntegerResponse countList(ChannelStatisticsDetailRequest request);


    /**
     * 根据条件查询PC统计明细
     *
     * @param request
     * @return
     */
    ChannelStatisticsDetailResponse searchAction(ChannelStatisticsDetailRequest request);

    /**
     * 获取app渠道列表
     */
    public List<UtmPlatVO> getPCUtm();

    /**
     * 获取用户/机构信息
     * @param requestBean
     * @return
     */
    STZHWhiteListResponse getUserByUserName(STZHWhiteListRequestBean requestBean);

    /**
     * 还款方式下拉列表
     *
     * @param
     * @return
     * @author wangjun
     * yangchangwei 迁移至amadminClient
     */
    List<BorrowStyleVO> selectCommonBorrowStyleList();


    /**
     * 查询汇计划转让列表
     *
     * @param request
     * @return
     * yangchangwei
     */
    HjhDebtCreditReponse queryHjhDebtCreditList(HjhDebtCreditListRequest request);

    /**
     * 查询手续费分账count
     * @auth sunpeikai
     * @param
     * @return
     */
    int getPoundageCount(PoundageListRequest request);
    /**
     * 查询手续费分账list
     * @auth sunpeikai
     * @param
     * @return
     */
    List<PoundageCustomizeVO> searchPoundageList(PoundageListRequest request);

    /**
     * 根据用户名查询分账名单是否存在
     * @author xiehuili
     * @param adminRequest
     * @return
     */
    public AdminSubConfigResponse subconfig(AdminSubConfigRequest adminRequest);

    /**
     * 获取手续费分账数额总计
     * @auth sunpeikai
     * @param
     * @return
     */
    PoundageCustomizeVO getPoundageSum(PoundageListRequest request);

    /**
     * 根据id查询手续费分账信息
     * @auth sunpeikai
     * @param
     * @return
     */
    PoundageCustomizeVO getPoundageById(Integer id);

    /**
     * 审核-更新poundage表
     * @auth sunpeikai
     * @param
     * @return
     */
    Integer updatePoundage(PoundageCustomizeVO poundageCustomizeVO);


    /**
     * 查询批次中心-批次放款列表
     * @param request
     * @return
     */
    BatchBorrowRecoverReponse getBatchBorrowRecoverList(BatchBorrowRecoverRequest request);

    /**
     * 查询批次中心的批次列表求和
     *
     * @param request
     * @return
     */
    BatchBorrowRecoverReponse getBatchBorrowCenterListSum(BatchBorrowRecoverRequest request);

    /**
     * yangchangwei
     * 根据id 获取放款任务表
     *
     * @param apicronID
     * @return
     */
    BorrowApicronResponse getBorrowApicronByID(String apicronID);

    /**
     * 查询权限数量
     * @auth sunpeikai
     * @param
     * @return
     */
    int getPermissionsCount(AdminPermissionsRequest request);

    /**
     * 查询权限列表
     * @auth sunpeikai
     * @param
     * @return
     */
    List<AdminPermissionsVO> searchPermissionsList(AdminPermissionsRequest request);

    /**
     * 检查数据库是否已存在该权限
     * @auth sunpeikai
     * @param
     * @return
     */
    boolean isExistsPermission(AdminPermissionsVO adminPermissionsVO);

    /**
     * 插入权限
     * @auth sunpeikai
     * @param
     * @return
     */
    int insertPermission(AdminPermissionsVO adminPermissionsVO);

    /**
     * 修改权限
     * @auth sunpeikai
     * @param
     * @return
     */
    int updatePermission(AdminPermissionsVO adminPermissionsVO);

    /**
     * 根据uuid查询权限
     * @auth sunpeikai
     * @param
     * @return
     */
    AdminPermissionsVO searchPermissionByUuid(String uuid);

    /**
     * 删除权限
     * @auth sunpeikai
     * @param
     * @return
     */
    int deletePermission(String uuid);

    /**
     * 资产来源
     * yangchangwei
     * @return
     */
    List<HjhInstConfigVO> selectHjhInstConfigList();

    /**
     * 查询数据字典总数
     * @auth sunpeikai
     * @param
     * @return
     */
    int getParamNamesCount(AdminParamNameRequest request);

    /**
     * 查询数据字典列表
     * @auth sunpeikai
     * @param
     * @return
     */
    List<ParamNameVO> searchParamNamesList(AdminParamNameRequest request);

    /**
     * 检查paramName是否存在
     * @auth sunpeikai
     * @param
     * @return
     */
    boolean isExistsParamName(ParamNameVO paramNameVO);

    /**
     * 添加数据字典
     * @auth sunpeikai
     * @param
     * @return
     */
    int insertParamName(ParamNameVO paramNameVO);

    /**
     * 更新数据字典
     * @auth sunpeikai
     * @param
     * @return
     */
    int updateParamName(ParamNameVO paramNameVO);

    /**
     * 根据联合主键查询数据字典
     * @auth sunpeikai
     * @param
     * @return
     */
    ParamNameVO searchParamNameByKey(ParamNameVO paramNameVO);

    /**
     * 删除数据字典
     * @auth sunpeikai
     * @param
     * @return
     */
    int deleteParamName(ParamNameVO paramNameVO);

    /**
     * 查询手续费分账配置
     * @auth sunpeikai
     * @param
     * @return
     */
    PoundageLedgerVO getPoundageLedgerById(int id);

    /**
     * 手续费分账详细信息总数
     * @auth sunpeikai
     * @param
     * @return
     */
    int getPoundageDetailCount(AdminPoundageDetailRequest request);

    /**
     * 手续费分账详细信息列表
     * @auth sunpeikai
     * @param
     * @return
     */
    List<PoundageDetailVO> searchPoundageDetailList(AdminPoundageDetailRequest request);


	List<DataCenterCouponCustomizeVO> getRecordListDJ(DataCenterCouponCustomizeVO dataCenterCouponCustomize);


	List<DataCenterCouponCustomizeVO> getRecordListJX(DataCenterCouponCustomizeVO dataCenterCouponCustomize);


	String getActivityTitle(Integer activityId);


	List<DataCenterCouponCustomizeVO> getDataCenterCouponList(DadaCenterCouponRequestBean requestBean, String type);


	PlatformCountCustomizeResponse searchAction(PlatformCountRequestBean requestBean);


    PlatformUserCountCustomizeResponse searchRegistAcount(PlatformCountRequestBean requestBean);

    /**
     * 获取锁定账户列表
     * @auth cuiguangqiang
     * @param request
     * @param isFront：是否前台
     * @return
     */
    LockedUserMgrResponse getLockedUserList(LockedeUserListRequest request, boolean isFront);

    /**
     * 解锁账户
     * @param vo
     * @param isFront
     * @return
     */
    BooleanResponse unlock(LockedUserInfoVO vo, boolean isFront);
}
