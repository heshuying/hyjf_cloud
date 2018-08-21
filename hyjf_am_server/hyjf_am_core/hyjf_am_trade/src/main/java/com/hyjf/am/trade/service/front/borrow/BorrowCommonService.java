package com.hyjf.am.trade.service.front.borrow;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.admin.BorrowCommonRequest;
import com.hyjf.am.trade.bean.BorrowCommonBean;
import com.hyjf.am.trade.bean.BorrowWithBLOBs;
import com.hyjf.am.trade.dao.model.auto.BorrowProjectRepay;
import com.hyjf.am.trade.dao.model.auto.BorrowProjectType;
import com.hyjf.am.trade.dao.model.auto.BorrowStyle;
import com.hyjf.am.trade.dao.model.auto.HjhAssetType;
import com.hyjf.am.trade.dao.model.auto.HjhInstConfig;
import com.hyjf.am.trade.service.BaseService;
import com.hyjf.am.vo.trade.borrow.BorrowCommonVO;

public interface BorrowCommonService extends BaseService {

	/**
	 * 根据主键判断权限维护中数据是否存在
	 * @param borrowNid
	 * @param borrowPreNid
	 * @return
	 */
	public boolean isExistsRecord(String borrowNid, String borrowPreNid);

	/**
	 * 用户是否存在 放在用户am层
	 * 
	 * @param userId
	 * @return
	 */
	public int isExistsUser(String userId);
	/**
	 * 项目类型
	 * 
	 * @return
	 */
	public List<BorrowProjectType> borrowProjectTypeList(String borrowTypeCd);

	/**
	 * 还款方式
	 * 
	 * @return
	 */
	public List<BorrowStyle> borrowStyleList(String nid);

	/**
	 * 还款方式 关联 项目类型
	 * 
	 * @return
	 */
	public List<BorrowProjectRepay> borrowProjectRepayList();

	/**
	 * 借款数据获取
	 * @param borrowCommonBean
	 * @return
	 */
	public BorrowCommonBean getBorrow(BorrowCommonBean borrowCommonBean);

	/**
	 * 借款数据获取
	 * @param borrowBean
	 * @param borrowWithBLOBs
	 */
	public void getBorrowCommonFiled(BorrowCommonBean borrowBean, BorrowWithBLOBs borrowWithBLOBs);

	/**
	 * 借款人信息数据获取
	 * @param borrowBean
	 */
	public void getBorrowManinfo(BorrowCommonBean borrowBean);

	/**
	 * 车辆信息数据获取
	 * @param borrowBean
	 */
	public void getBorrowCarinfo(BorrowCommonBean borrowBean);

	/**
	 * 用户信息数据获取
	 * @param borrowBean
	 */
	public void getBorrowUsers(BorrowCommonBean borrowBean);

	/**
	 * 插入操作
	 * @param borrowBean
	 * @throws Exception
	 */
	public void insertRecord(BorrowCommonBean borrowBean,String adminUsername,int adminId) throws Exception;

	/**
	 * 借款表插入
	 * 
	 * @param borrowBean
	 * @param borrow
	 */
	public void insertBorrowCommonData(BorrowCommonBean borrowBean, BorrowWithBLOBs borrow, String borrowPreNid, String newBorrowPreNid, String borrowNid, String name, String account,String adminUsername)
			throws Exception;

	/**
	 * 更新操作
	 * @param borrowBean
	 * @throws Exception
	 */
	public void modifyRecord(BorrowCommonBean borrowBean,String adminUsername,int adminId) throws Exception;

	/**
	 * 借款表更新
	 * 
	 * @param borrowBean
	 * @param borrow
	 */
	public void updateBorrowCommonData(BorrowCommonBean borrowBean, BorrowWithBLOBs borrow, String borrowNid,String adminUsername,int adminId) throws Exception;

	/**
	 * 获取借款预编号
	 * 
	 * @return
	 */
	public String getBorrowPreNid();

	/**
	 * 获取借款预编号是否存在
	 * @param borrowPreNid
	 * @return
	 */
	public int isExistsBorrowPreNid(String borrowPreNid);

	/**
	 * 根据项目编号获取借款信息
	 * @param borrowNid
	 * @return
	 */
	public BorrowWithBLOBs getBorrowWithBLOBs(String borrowNid);

	/**
	 * 获取融资服务费率
	 * @param projectType
	 * @param chargeTimeType
	 * @param instCode
	 * @param chargeTime
	 * @return
	 */
	public String getBorrowServiceScale(Integer projectType, String chargeTimeType, String instCode, Integer chargeTime);

	/**
	 * 获取账户管理费
	 * @param projectType
	 * @param borrowStyle
	 * @param instCode
	 * @param borrowPeriod
	 * @return
	 */
	public String getBorrowManagerScale(Integer projectType, String borrowStyle, String instCode, Integer borrowPeriod);

	/**
	 * 收益差率
	 * @param projectType
	 * @param borrowStyle
	 * @param instCode
	 * @param borrowPeriod
	 * @return
	 */
	public String getBorrowReturnScale(Integer projectType, String borrowStyle, String instCode, Integer borrowPeriod);

	/**
	 * 上传图片的信息
	 * 
	 * @param borrowBean
	 * @return
	 * @throws Exception
	 */
	public String getUploadImage(BorrowCommonBean borrowBean, String files, String borrowNid) throws Exception;

	/**
	 * 车辆信息
	 * 
	 * @param borrowNid
	 * @param borrowBean
	 * @param borrow
	 * @return
	 */
	public int insertBorrowCarinfo(String borrowNid, BorrowCommonBean borrowBean, BorrowWithBLOBs borrow);

	/**
	 * 个人信息
	 * 
	 * @param borrowNid
	 * @param borrowBean
	 * @param borrow
	 * @return
	 */
	public int insertBorrowManinfo(String borrowNid, BorrowCommonBean borrowBean, BorrowWithBLOBs borrow);

	/**
	 * 公司信息
	 * 
	 * @param borrowNid
	 * @param borrowBean
	 * @param borrow
	 * @return
	 */
	public int insertBorrowUsers(String borrowNid, BorrowCommonBean borrowBean, BorrowWithBLOBs borrow);

	/**
	 * 房产信息
	 * 
	 * @param borrowNid
	 * @param borrowBean
	 * @param borrow
	 * @return
	 */
	public int insertBorrowHouses(String borrowNid, BorrowCommonBean borrowBean, BorrowWithBLOBs borrow);

	/**
	 * 认证信息
	 * 
	 * @param borrowNid
	 * @param borrowBean
	 * @param borrow
	 * @return
	 */
	public int insertBorrowCompanyAuthen(String borrowNid, BorrowCommonBean borrowBean, BorrowWithBLOBs borrow);

//	/**
//	 * 合作机构
//	 * @return
//	 */
//	public List<Links> getLinks();

	/**
	 * 信息验证
	 * @param mav
	 * @param borrowBean
	 * @param isExistsRecord
	 * @param HztOrHxf
	 */
//	public void validatorFieldCheck(ModelAndView mav, BorrowCommonBean borrowBean, boolean isExistsRecord, String HztOrHxf);

	/**
	 * 画面的值放到Bean中
	 * 
	 * @param form
	 * @param isExistsRecord
	 */
	public void setPageListInfo(BorrowCommonBean form, boolean isExistsRecord);

	/**
	 * 用户是否存在
	 * @param request
	 * @return
	 */
//	public String isExistsUser(HttpServletRequest request);

//	/**
//	 * 项目申请人是否存在
//	 * @param request
//	 * @return
//	 */TODO 放在配置am层
//	public String isExistsApplicant(HttpServletRequest request);

	/**
	 * 垫付机构是否存在
	 * @param request
	 * @return 
	 * TODO放在用户am层
	 */
//	public String isRepayOrgUser(HttpServletRequest request);

//	/**
//	 * 发标金额是否合法
//	 * TODO 放在admin项目
//	 * @param request
//	 * @return
//	 */
//	public String isAccountLegal(HttpServletRequest request);

	/**
	 * 获取融资服务费率 & 账户管理费率 & 收益差率
	 *
	 * @param borrowPeriod
	 * @param borrowStyle
	 * @param projectType
	 * @param instCode
	 * @return
	 */
	public BorrowCommonVO getBorrowServiceScale(BorrowCommonRequest borrowCommonRequest);

	/**
	 * 账户管理费率(最低，最高)
	 * @param projectType
	 * @param borrowStyle
	 * @param borrowPeriod
	 * @param jsonObject
	 * @return
	 */
	public JSONObject getBorrowManagerScale(Integer projectType, String borrowStyle, Integer borrowPeriod, JSONObject jsonObject);

	/**
	 * 资料上传
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	//public String uploadFile(HttpServletRequest request, HttpServletResponse response) throws Exception;

	/**
	 * 项目类型
	 * 
	 * @return
	 * @author Administrator
	 */
	public String getBorrowProjectClass(String borrowCd);

	/**
	 * 导出功能
	 * @param request
	 * @param response
	 * @param form
	 * @throws Exception
	 */
//	public void downloadCar(HttpServletRequest request, HttpServletResponse response, BorrowBean form) throws Exception;

	/**
	 * 资料上传
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
//	public String uploadCar(HttpServletRequest request, HttpServletResponse response) throws Exception;

	/**
	 * 导出功能
	 * @param request
	 * @param response
	 * @param form
	 * @throws Exception
	 */
//	public void downloadHouse(HttpServletRequest request, HttpServletResponse response, BorrowBean form) throws Exception;

	/**
	 * 资料上传
	 *
	 * @param request
	 * @return
	 * @throws Exception
	 */
//	public String uploadHouse(HttpServletRequest request, HttpServletResponse response) throws Exception;

	/**
	 * 导出功能
	 * @param request
	 * @param response
	 * @param form
	 * @throws Exception
	 */
	//public void downloadAuthen(HttpServletRequest request, HttpServletResponse response, BorrowBean form) throws Exception;

	/**
	 * 资料上传
	 *
	 * @param request
	 * @return
	 * @throws Exception
	 */
	//public String uploadAuthen(HttpServletRequest request, HttpServletResponse response) throws Exception;

	public BorrowWithBLOBs getRecordById(BorrowCommonBean borrowBean);

	/**
	 * 判断借款期限是否为0
	 * 
	 * @param request
	 * @return
	 */
//	public String isBorrowPeriodCheck(HttpServletRequest request);
	
	/**
	 * 借款内容填充
	 * 
	 * @param request
	 * @return
	 */
//	public String contentFill(HttpServletRequest request, HttpServletResponse response) throws Exception;

	/**
	 * 下载内容填充模板
	 * @param request
	 * @param response
	 * @throws Exception
	 */
//	public void downloadContentFill(HttpServletRequest request, HttpServletResponse response) throws Exception;

	/**
	 * 现金贷项目类型获取借款预编号
	 * @return
	 */
	public String getXJDBorrowPreNid();

	/**
	 * 获取机构资产列表
	 * @return
	 */
	List<HjhInstConfig> getInstList();
	
	/**
	 * 用户是否存在
	 * @param request
	 * @return
	 */
	//public String isEntrustedExistsUser(HttpServletRequest request);
	
	/**
	 * 根据主键查询判断此标的是否使用标的引擎
	 * @param borrowNid
	 * @param 
	 * @return
	 */
	public Integer isEngineUsed(String borrowNid);

	/**
	 * 根据借款主体,社会统一信用代码查询用户是否做过CA认证
	 * @param comName
	 * @param name
	 * @return
	 */
	//String isBorrowUserCACheck(String comName,String name);

	/**
	 * 根据社会统一信用代码或身份证号查询用户是否做过CA认证
	 * @param idNo
	 * @param name
	 * @return
	 */
	//String isCAIdNoCheck(String idNo, String name);
	
	/**
	 * 资金来源
	 * @param instCode
	 * @return
	 */
	public List<HjhInstConfig> hjhInstConfigList(String instCode);
	/**
	 * 获取系统配置
	 *
	 * @return
	 */
	@Override
    public String getBorrowConfig(String configCd);
	/**
	 * 根据资金来源查询产品类型
	 * @param instCode
	 * @return
	 */
	public List<HjhAssetType> hjhAssetTypeList(String instCode);
	public int isEntrustedExistsUser(String userName);

	/**
	 * 根据原始标的号拉取标的信息判断是否发送自动备案MQ消息队列
	 *
	 * @param borrowPreNid
	 */
	void isAutoRecord(String borrowPreNid);
}
