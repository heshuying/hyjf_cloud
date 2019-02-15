package com.hyjf.admin.service;

import com.hyjf.am.response.admin.BorrowCommonResponse;
import com.hyjf.am.response.admin.BorrowCustomizeResponse;
import com.hyjf.am.response.config.AdminSystemResponse;
import com.hyjf.am.response.config.LinkResponse;
import com.hyjf.am.resquest.admin.BorrowBeanRequest;
import com.hyjf.am.resquest.admin.BorrowCommonRequest;
import com.hyjf.am.vo.config.ParamNameVO;
import com.hyjf.am.vo.task.autoreview.BorrowCommonCustomizeVO;
import com.hyjf.am.vo.trade.borrow.BorrowCommonVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import org.springframework.web.bind.annotation.PathVariable;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @author GOGTZ-Z
 * @version V1.0  
 * @package com.hyjf.admin.maintenance.AdminPermissions
 * @date 2015/07/09 17:00
 */

public interface BorrowCommonService {

	/**
	 * 迁移到详细画面
	 *
	 * @param request
	 * @param form
	 * @return
	 */
	BorrowCommonResponse moveToInfoAction(BorrowCommonRequest borrowCommonRequest);

	/**
	 * 添加信息
	 *
	 * @param request
	 * @param form
	 * @return
	 * @throws Exception
	 */
	BorrowCommonResponse insertAction(BorrowCommonRequest borrowCommonRequest) throws Exception;

	LinkResponse getLinks();

	/**
	 * 用户是否存在
	 *
	 * @param request
	 * @return
	 */
	int isExistsUser(String userId);

	/**
	 * 项目申请人是否存在
	 *
	 * @param request
	 * @return
	 */

	AdminSystemResponse isExistsApplicant(String applicant);

	/**
	 * 获取最新的借款预编码
	 *
	 * @param request
	 * @return
	 */

	String getBorrowPreNid();

	/**
	 * 获取现金贷的借款预编号
	 *
	 * @param request
	 * @return
	 */
	String getXJDBorrowPreNid();

	/**
	 * 借款预编码是否存在
	 *
	 * @param request
	 * @return
	 */
	boolean isExistsBorrowPreNidRecord(String borrowPreNid);

	/**
	 * 获取放款服务费率 & 还款服务费率
	 *
	 * @param request
	 * @return
	 */
	BorrowCommonVO getBorrowServiceScale(BorrowCommonRequest borrowCommonRequest);

	/**
	 * 根据资产编号查询该资产下面的产品类型
	 *
	 * @param request
	 * @param attr
	 * @param instCode
	 * @return
	 */
	BorrowCommonResponse getProductTypeAction(String instCode);

	/**
	 * 受托用户是否存在
	 *
	 * @param request
	 * @return
	 */

	int isEntrustedExistsUser(String userName);

	List<UserVO> selectUserByUsername(String repayOrgName);

	UserInfoVO findUserInfoById(@PathVariable int userId);

	boolean isBorrowUserCACheck(String name);

	boolean isCAIdNoCheck(String param, String name);
	BorrowCustomizeResponse init(BorrowBeanRequest form);
	/**
	 * 列表导出
	 * 
	 * @param borrowCustomize
	 * @return
	 */
	BorrowCustomizeResponse exportBorrowList(BorrowBeanRequest borrowCommonCustomize);
	UserVO getUserByUserName(String userName);

	List<BorrowCommonCustomizeVO> paging(BorrowBeanRequest request, List<BorrowCommonCustomizeVO> result);

	/**
	 * 获取标的投资的等级
	 *
	 * @param borrowLevel
	 * @return
	 */
	String getBorrowLevelAction(@Valid String borrowLevel);

	/**
	 * map转ParamNameVO
	 *
	 * @param map
	 * @return
	 */
    List<ParamNameVO> mapToParamNameVO(Map<String,String> map);
}