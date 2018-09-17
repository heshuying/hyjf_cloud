package com.hyjf.cs.trade.service.repay;

import com.hyjf.am.resquest.admin.BatchBorrowRecoverRequest;
import com.hyjf.am.vo.admin.BatchBorrowRecoverVo;
import com.hyjf.am.vo.trade.repay.WebUserRepayProjectListCustomizeVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.cs.common.service.BaseService;
import com.hyjf.cs.trade.bean.repay.ProjectBean;
import com.hyjf.cs.trade.bean.repay.RepayProjectListBean;

import java.text.ParseException;
import java.util.List;


/**
 * @version RepayService, v0.1 2018/8/27 15:39
 * @Author: Zha Daojian
 */
public interface RepayService extends BaseService {

    /**
     * 获取批次放款列表
     * @author Zha Daojian
     * @date 2018/8/27 15:37
     * @param request
     * @return java.util.List<com.hyjf.am.vo.admin.BatchBorrowRecoverVo>
     **/
    List<BatchBorrowRecoverVo> getBatchBorrowRecoverList(BatchBorrowRecoverRequest request);


    /**
     * 获取批次放款列表条数
    * @author Zha Daojian
    * @date 2018/8/27 15:57
    * @param request
    * @return
    **/
    Integer countBatchCenter(BatchBorrowRecoverRequest request);

	/**
	 * 获取用户还款列表
	 *
	 * @param form
	 * @param offset
	 * @param limit
	 * @return
	 */
	List<WebUserRepayProjectListCustomizeVO> searchUserRepayList(RepayProjectListBean form, int offset, int limit);

	/**
	 * 查询用户还款详情
	 *
	 * @param form
	 * @return
	 * @throws ParseException
	 * @throws NumberFormatException
	 */
	ProjectBean searchRepayProjectDetail(ProjectBean form) throws Exception;

	/**
	 * 获取开户信息
	 *
	 * @param accountId
	 * @return
	 */
	public BankOpenAccountVO getBankOpenAccount(String accountId);

}
