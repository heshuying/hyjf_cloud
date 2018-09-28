package com.hyjf.admin.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.hyjf.am.response.admin.BorrowCommonResponse;
import com.hyjf.am.response.admin.BorrowCustomizeResponse;
import com.hyjf.am.response.config.AdminSystemResponse;
import com.hyjf.am.response.config.LinkResponse;
import com.hyjf.am.response.user.UserInfoResponse;
import com.hyjf.am.response.user.UserResponse;
import com.hyjf.am.resquest.admin.BorrowBeanRequest;
import com.hyjf.am.resquest.admin.BorrowCommonRequest;
import com.hyjf.am.vo.task.autoreview.BorrowCommonCustomizeVO;
import com.hyjf.am.vo.trade.borrow.BorrowCommonVO;
import com.hyjf.am.vo.user.UserInfoCustomizeVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;

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
	public BorrowCommonResponse moveToInfoAction(BorrowCommonRequest borrowCommonRequest);

	/**
	 * 添加信息
	 *
	 * @param request
	 * @param form
	 * @return
	 * @throws Exception
	 */
	public BorrowCommonResponse insertAction(BorrowCommonRequest borrowCommonRequest) throws Exception;

	public LinkResponse getLinks();

	/**
	 * 用户是否存在
	 *
	 * @param request
	 * @return
	 */
	public int isExistsUser(String userId);

	/**
	 * 项目申请人是否存在
	 *
	 * @param request
	 * @return
	 */

	public AdminSystemResponse isExistsApplicant(String applicant);

	/**
	 * 获取最新的借款预编码
	 *
	 * @param request
	 * @return
	 */

	public String getBorrowPreNid();

	/**
	 * 获取现金贷的借款预编号
	 *
	 * @param request
	 * @return
	 */
	public String getXJDBorrowPreNid();

	/**
	 * 借款预编码是否存在
	 *
	 * @param request
	 * @return
	 */
	public boolean isExistsBorrowPreNidRecord(String borrowPreNid);

	/**
	 * 获取融资服务费率 & 账户管理费率
	 *
	 * @param request
	 * @return
	 */
	public BorrowCommonVO getBorrowServiceScale(BorrowCommonRequest borrowCommonRequest);

	/**
	 * 根据资产编号查询该资产下面的产品类型
	 *
	 * @param request
	 * @param attr
	 * @param instCode
	 * @return
	 */
	public BorrowCommonResponse getProductTypeAction(String instCode);

	/**
	 * 受托用户是否存在
	 *
	 * @param request
	 * @return
	 */

	public int isEntrustedExistsUser(String userName);

	public List<UserVO> selectUserByUsername(String repayOrgName);

	public UserInfoVO findUserInfoById(@PathVariable int userId);

	public boolean isBorrowUserCACheck(String name);

	public boolean isCAIdNoCheck(String param, String name);
	public BorrowCustomizeResponse init(BorrowBeanRequest form);
	/**
	 * 列表导出
	 * 
	 * @param borrowCustomize
	 * @return
	 */
	public List<BorrowCommonCustomizeVO> exportBorrowList(BorrowBeanRequest borrowCommonCustomize);
	public UserVO getUserByUserName(String userName);
}