package com.hyjf.cs.trade.service.aems.assetpush;

import com.hyjf.am.vo.trade.CorpOpenAccountRecordVO;
import com.hyjf.am.vo.trade.STZHWhiteListVO;
import com.hyjf.am.vo.trade.borrow.BorrowProjectRepayVO;
import com.hyjf.am.vo.trade.hjh.HjhAssetBorrowTypeVO;
import com.hyjf.am.vo.trade.hjh.HjhBailConfigVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanAssetVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.cs.common.service.BaseService;

import java.util.List;
import java.util.Map;

/**
 * 资产推送 jijun 20180914
 */
public interface AemsPushService extends BaseService {

	/**
	 * 插入资产表
	 *@param record
	 * @param params
	 * @return
	 */
	int insertAssert(HjhPlanAssetVO record, Map<String, String> params);
	
	/**
     *获取开户的数据
     */
	BankOpenAccountVO selectBankAccountById(int userId);
	
    /**
     *获取用户信息
     */
    UserInfoVO selectUserInfoByNameAndCard(String trueName, String idCrad);

    /**
     *获取用户的数据
     */
    UserVO selectUsersById(int userId);

    /**
     *获取机构信息
     */
    HjhAssetBorrowTypeVO selectAssetBorrowType(String instCode, int assetType);


    /**
     * 根据项目类型去还款方式
     * @param borrowcCd
     * @return
     */
    List<BorrowProjectRepayVO> selectProjectRepay(String borrowcCd);

    /**
     *获取受托支付电子账户列表
     */
    STZHWhiteListVO selectStzfWhiteList(String instCode, String stAccountid);
	
    /**
     * 推送消息到MQ
     */
    void sendToMQ(HjhPlanAssetVO hjhPlanAsset);

    /**
     *通过用户名获得用户的详细信息
     */
    UserVO selectUserInfoByUsername(String userName);

    /**
     *通过机构编号获得机构信息
     */
    CorpOpenAccountRecordVO selectUserBusiNameByUsername(String userName);

    /**
     * 通过用户id获得用户真实姓名和身份证号
     * @param userId
     * @return
     */
    UserInfoVO selectUserInfoByUserId(Integer userId);

    /**
     * 检查是否存在重复资产
     * @param assetId
     * @return
     */
    HjhPlanAssetVO checkDuplicateAssetId(String assetId);

    /***获取保证金配置
    * @author Zha Daojian
    * @date 2018/12/5 15:31
    * @param instCode
    * @return HjhBailConfigVO
    **/
    HjhBailConfigVO getBailConfig(String instCode);

    /**
     * 根据资产id获取资产count
     * @param assetId
     * @return
     */
    int selectAssertCountByAssetId(String assetId);
}
