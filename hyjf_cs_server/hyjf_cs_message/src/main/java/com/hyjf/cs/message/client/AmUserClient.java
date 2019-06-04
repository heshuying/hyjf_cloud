package com.hyjf.cs.message.client;

import com.hyjf.am.response.app.AppUtmRegResponse;
import com.hyjf.am.resquest.admin.AppChannelStatisticsRequest;
import com.hyjf.am.resquest.admin.SmsCodeUserRequest;
import com.hyjf.am.vo.admin.UtmVO;
import com.hyjf.am.vo.datacollect.AppUtmRegVO;
import com.hyjf.am.vo.trade.OperationReportJobVO;
import com.hyjf.am.vo.user.*;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author xiasq
 * @version AmUserClient, v0.1 2018/4/19 12:44
 */
public interface AmUserClient {

	/**
	 * 根据手机号查询用户
	 * 
	 * @param mobile
	 * @return
	 */
	UserVO findUserByMobile(final String mobile);

	/**
	 * 出借人按照年龄分布
	 *
	 * @param date 上个月的最后一天
	 * @param firstAge  年龄下限
	 * @param endAge	年龄上限
	 * @return
	 */
	int getTenderAgeByRange(Date date, int firstAge, int endAge, List<OperationReportJobVO> ageRangeUserIds);

	/**
	 * 用户分析 - 年龄分布拆分
	 *
	 * @param list 用户ids
	 * @return
	 */
	List<OperationReportJobVO> getAgeCount(List<OperationReportJobVO> list);

	/**
	 * 十大出借人拆分
	 *
	 * @param list 多个用户id
	 * @return
	 */
	List<OperationReportJobVO> getUserNames( List<OperationReportJobVO> list);
	/**
	 * 通过用户ID查询 用户年龄，用户地区
	 *
	 * @param userId 用户ID
	 * @return
	 */
	OperationReportJobVO getUserAgeAndArea(Integer userId);
	/**
	 * 通过时间统计平台注册人数
	 * @param
	 * @return
	 *
	 */
	int countRegistUser();

	/**
	 * 用户分析 - 性别分布拆分
	 *
	 * @param list 用户ids
	 * @return
	 */
	List<OperationReportJobVO> getSexCount( List<OperationReportJobVO> list);

	/**
	 * 根据userId查询用户
	 * 
	 * @param userId
	 * @return
	 */
	UserVO findUserById(final int userId);
	/**
	 * 根据userId查询用户信息
	 *
	 * @param userId
	 * @return
	 */
	UserInfoVO findUsersInfoById(int userId);

	/**
	 * 根据手机号查询推送别名
	 * 
	 * @param mobile
	 * @return
	 */
	UserAliasVO findAliasByMobile(final String mobile);

	/**
	 * 根据手机号查询推送别名 - 批量
	 * 
	 * @param mobiles
	 * @return
	 */
	List<UserAliasVO> findAliasesByMobiles(final List<String> mobiles);

	/**
	 * 根据设备类型统计用户人数
	 * 
	 * @param clientAndroid
	 * @return
	 */
	int countAliasByClient(String clientAndroid);

	/**
	 * 查看用户详情
	 * @param userId
	 * @return
	 */
	 UserInfoCustomizeVO queryUserInfoCustomizeByUserId(Integer userId);


	/**
	 * 获取用户表总记录数
	 *
	 * @return
	 */
	Integer countAllUser();

	/**
	 * 查询所有app渠道
	 * @param type
	 * @return
	 */
    List<UtmVO> selectUtmPlatList(String type);

	/**
	 * 获取注册数
	 * @param request
	 * @return
	 */
	int getAppChannelStatisticsDetailVO(AppChannelStatisticsRequest request);

    /**
     * 获取注册数
     * @param request
     * @return
     */
    List<AppUtmRegVO> getAppChannelStatisticsDetailVOList(AppChannelStatisticsRequest request);

	/**
	 * 查询相应的app渠道无主单开户数
	 * @param request
	 * @return
	 */
	int getOpenAccountAttrCount(AppChannelStatisticsRequest request);

    List<Integer> getUsersList(String source);

    List<Integer> getUsersInfoList();

	/**
	 * 查询app渠道相关统计信息
	 * @param request
	 * @return
	 */
	AppUtmRegResponse getAppUtmRegResponse(AppChannelStatisticsRequest request);

	List<String> queryUserByBirthday(SmsCodeUserRequest request);

	/**
	 * 通过用户id查询 ca客户编号
	 * @param request
	 * @return
	 */
	List<CertificateAuthorityVO> queryCustomerId(Set<Integer> request);
}
