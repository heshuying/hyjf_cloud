/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.front.borrow;

import com.hyjf.am.resquest.trade.BatchCenterCustomizeRequest;
import com.hyjf.am.resquest.trade.BorrowRegistRequest;
import com.hyjf.am.resquest.trade.TenderRequest;
import com.hyjf.am.resquest.user.BorrowFinmanNewChargeRequest;
import com.hyjf.am.trade.bean.repay.ProjectBean;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.service.BaseService;
import com.hyjf.am.vo.admin.BorrowCustomizeVO;
import com.hyjf.am.vo.task.autoreview.BorrowCommonCustomizeVO;
import com.hyjf.am.vo.trade.ProjectCompanyDetailVO;
import com.hyjf.am.vo.trade.ProjectCustomeDetailVO;
import com.hyjf.am.vo.trade.WebProjectPersonDetailVO;
import com.hyjf.am.vo.trade.borrow.TenderBgVO;
import com.hyjf.am.vo.trade.borrow.TenderRetMsg;
import com.hyjf.am.vo.trade.repay.WebUserRepayProjectListCustomizeVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * @author fuqiang
 * @version BorrowService, v0.1 2018/6/13 18:52
 */
public interface BorrowService extends BaseService {

    BorrowStyle getborrowStyleByNid(String borrowStyle);

    /**
     * 根据项目类型，期限，获取借款利率
     * @param request
     * @return
     */
    BorrowFinmanNewCharge selectBorrowApr(BorrowFinmanNewChargeRequest request);

    /**
     * 获取系统配置
     * @param configCd
     * @return
     */
    BorrowConfig getBorrowConfigByConfigCd(String configCd);

    /**
     * 借款表插入
     * @param borrow
     */
    int insertBorrow(Borrow borrow);

    int insertBorrowManinfo(BorrowManinfo borrowManinfo);

    /**
     * 更新相应的标的状态为备案中
     * @param request
     * @return
     */
    int updateBorrowRegist(BorrowRegistRequest request);

    /**
     * 检索正在还款中的标的
     * @return
     */
    List<Borrow> selectBorrowList();

    /**
     * 投资之前插入tmp表
     * @param tenderRequest
     * @return
     */
    int insertBeforeTender(TenderRequest tenderRequest);

    /**
     * 獲取預期的還款標的
     */
    List<Borrow> selectOverdueBorrowList();

    /**
     * 获取项目详情
     * @author zhangyk
     * @date 2018/6/26 14:02
     */
    ProjectCustomeDetailVO getProjectDetail(String borrowNid);

    /**
     * 获取标的公司信息
     * @author zhangyk
     * @date 2018/6/26 15:30
     */
    ProjectCompanyDetailVO getProjectCompany(String borrowNid);

    /**
     * 获取个人项目详情
     * @author zhangyk
     * @date 2018/6/26 16:10
     */
    WebProjectPersonDetailVO getProjectPerson(String borrowNid);

    /**
     * 投资异步修改表
     * @param tenderBg
     */
    void updateTenderAfter(TenderBgVO tenderBg);

    /**
     * 修改散标投资异步返回结果
     * @param tenderRetMsg
     */
    void updateTenderResult(TenderRetMsg tenderRetMsg);

    /**
     * 获取散标投资异步返回结果
     * @param userId
     * @param logOrdId
     * @param borrowNid
     * @return
     */
    String getBorrowTenderResult(Integer userId, String logOrdId, String borrowNid);

    /**
     * 根据userId获取用户总投资笔数
     * @author zhangyk
     * @date 2018/7/5 17:35
     */
    Integer getTotalInverestCount(Integer userId);
    
	/**
	 * 合计
	 * 
	 * @return
	 */
	public Long countBorrow(BorrowCommonCustomizeVO borrowCommonCustomizeVO);


	/**
	 * 借款列表
	 * 
	 * @return
	 */
	public List<BorrowCustomizeVO> selectBorrowList(BorrowCommonCustomizeVO borrowCommonCustomizeVO);

	/**
	 * 放款列表
	 */
	public List<AccountBorrow> getAccountBorrowList(String borrowNid);
	
	/**  
	 * 查询订单风控信息
	 * @author zhangyk
	 * @date 2018/8/10 15:40
	 */  
	public BorrowInfoWithBLOBs getBorrowInfoWithBLOBs(String borrowNid);


	/**
	 * 查询计划还款日前一天，处于投资中和复审中的原始标的，发送邮件预警
	 * @author zhangyk
	 * @date 2018/8/20 16:25
	 */
    List<BorrowCustomizeVO> selectUnDealBorrowBeforeLiquidate();
	/**
	 * 列表导出
	 * 
	 * @param borrowCustomize
	 * @return
	 */
	public List<BorrowCommonCustomizeVO> exportBorrowList(BorrowCommonCustomizeVO borrowCommonCustomizeVO);

	/**
	 * 根据Nid和当前时间获取borrow
	 * @author zhangyk
	 * @date 2018/9/3 17:25
	 */
	public Borrow selectBorrowByNidAndNowTime(String borrowNid, Integer nowtime);

	public Long countBatchCenter(BatchCenterCustomizeRequest batchCenterCustomize);

	public List<BatchCenterCustomize> selectBatchCenterList(BatchCenterCustomizeRequest batchCenterCustomize);

	public String getborrowIdByProductId(Map<String, Object> params);

	public List<WebUserRepayProjectListCustomizeVO> selectOrgRepayProjectList(Map<String, Object> params);

	public List<WebUserRepayProjectListCustomizeVO> selectUserRepayProjectList(Map<String, Object> params);

	public ProjectBean searchRepayProjectDetail(ProjectBean form) throws Exception;

	public BankOpenAccountVO getBankOpenAccount(String bankAccount);

}
