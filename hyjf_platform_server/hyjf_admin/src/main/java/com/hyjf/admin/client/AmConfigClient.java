package com.hyjf.admin.client;

import com.hyjf.am.response.admin.JxBankConfigResponse;
import com.hyjf.am.response.config.*;
import com.hyjf.am.response.user.MspApplytResponse;
import com.hyjf.am.response.user.MspResponse;
import com.hyjf.am.resquest.user.MspApplytRequest;
import com.hyjf.am.resquest.user.MspRequest;
import com.hyjf.am.vo.config.AdminSystemVO;
import com.hyjf.am.vo.config.ParamNameVO;
import com.hyjf.am.vo.config.SiteSettingsVO;
import com.hyjf.am.vo.config.SmsMailTemplateVO;
import com.hyjf.am.vo.trade.BankConfigVO;
import com.hyjf.am.vo.trade.BankReturnCodeConfigVO;

import java.util.List;

/**
 * @author zhangqingqing
 * @version AmConfigClient, v0.1 2018/4/23 20:00
 */
public interface AmConfigClient {
    /**
     * 用loginUserId去am-config查询登录的用户信息
     * @auth sunpeikai
     * @param loginUserId 登录用户id
     * @return
     */
    AdminSystemVO getUserInfoById(Integer loginUserId);

    /**
     * 获取数据字典表的下拉列表
     * @param nameClass
     * @return
     */
    List<ParamNameVO> getParamNameList(String nameClass);

    /**
     * 查询邮件配置
     * @auth sunpeikai
     * @param
     * @return
     */
    SiteSettingsVO findSiteSetting();

    /**
     * 查询邮件模板
     * @auth sunpeikai
     * @param
     * @return
     */
    SmsMailTemplateVO findSmsMailTemplateByCode(String mailCode);

    /**
     * 根据银行错误码查询错误信息
     * @auth sunpeikai
     * @param retCode 错误码
     * @return
     */
    String getBankRetMsg(String retCode);

    /**
     * 获取银行返回码
     *
     * @param retCode
     * @return
     */
    BankReturnCodeConfigVO getBankReturnCodeConfig(String retCode);
    /**
     * 获取银行列表
     * @author nixiaoling
     * @return
     */
    List<BankConfigVO> selectBankConfigList();

    /**
     * 根据银行卡号获取bankId
     * @auth nxl
     * @param cardNo
     * @return
     */
    String queryBankIdByCardNo(String cardNo);
    /**
     * 根据bankId查找江西银行的银行卡配置表
     * @auther: nxl
     * @param bankId
     * @return
     */
    JxBankConfigResponse getJXbankConfigByBankId(int bankId);
    /**
   	 * 合作机构
   	 * @return
   	 */
   	LinkResponse getLinks();
	/**
	 * 子账户类型 查询
	 */
	ParamNameResponse getNameCd(String code);

   	AdminSystemResponse isExistsApplicant(String applicant);
   	public MspApplytResponse getRecordList(MspApplytRequest mspApplytRequest);
	public MspApplytResponse infoAction();
	public MspApplytResponse insertAction(MspApplytRequest mspApplytRequest);
	public MspApplytResponse updateAction(MspApplytRequest mspApplytRequest);
	public MspApplytResponse deleteRecordAction(MspApplytRequest mspApplytRequest);
	public MspApplytResponse validateBeforeAction(MspApplytRequest mspApplytRequest);
	public MspApplytResponse applyInfo(MspApplytRequest mspApplytRequest);
	public MspApplytResponse shareUser(MspApplytRequest mspApplytRequest);
	public MspApplytResponse download(MspApplytRequest mspApplytRequest);
	public MspResponse searchAction(MspRequest mspRequest);
	public MspResponse infoAction(MspRequest mspRequest);
	public MspResponse insertAction(MspRequest mspRequest);
	public MspResponse updateAction(MspRequest mspRequest);
	public MspResponse configureNameError(MspRequest mspRequest);
	public MspResponse deleteAction(MspRequest mspRequest);
	public MspResponse checkAction(MspRequest mspRequest);
	/**
	 * 获取所有问题
	 * @return
	 */
	QuestionResponse getAllQuestion();
	/**
	 * 获取所有回答
	 * @return
	 */
	AnswerResponse getAllAnswer();

}
