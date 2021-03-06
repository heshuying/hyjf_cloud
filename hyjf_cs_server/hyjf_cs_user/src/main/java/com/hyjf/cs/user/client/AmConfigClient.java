package com.hyjf.cs.user.client;

import com.hyjf.am.resquest.config.MsgPushTemplateRequest;
import com.hyjf.am.resquest.user.AnswerRequest;
import com.hyjf.am.vo.app.recharge.AppRechargeRuleVO;
import com.hyjf.am.vo.config.*;
import com.hyjf.am.vo.market.ShareNewsBeanVO;
import com.hyjf.am.vo.trade.BankConfigVO;
import com.hyjf.am.vo.trade.BankReturnCodeConfigVO;
import com.hyjf.am.vo.trade.JxBankConfigVO;
import com.hyjf.am.vo.user.QuestionCustomizeVO;

import java.util.List;

/**
 * @author xiasq
 * @version AmConfigClient, v0.1 2018/4/23 20:00
 */
public interface AmConfigClient {
    /**
     * 获取短信配置表-最大量，有效时间等 配置只有一条
     * @return
     */
    SmsConfigVO findSmsConfig();

    /**
     * @Description 根据银行卡号查询bankId
     * @Author sunss
     * @Version v0.1
     * @Date 2018/6/5 14:57
     */
    String getBankIdByCardNo(String cardNo);

    /**
     * 测评问题列表
     * @return
     */
    List<QuestionCustomizeVO> getNewQuestionList();

    List<NewAppQuestionCustomizeVO> getNewAppQuestionList();

    /**
     * 计算测评得分
     * @param answerList
     * @return
     */
    int countScore(AnswerRequest answerList);

    SiteSettingsVO selectSiteSetting();

    /**
     * 根据返回码获取返回信息
     * @param retCode
     * @return
     */
    String getBankRetMsg(String retCode);

    /**
     * 根据返回码获取返回码配置
     * @param retCode
     * @return
     */
    BankReturnCodeConfigVO getBankReturnCodeConfig(String retCode);

    /**
     * 根据银行卡号查询银行id
     * @param cardNo
     * @return
     */
    String queryBankIdByCardNo(String cardNo);

    /**
     * 根据银行id查询银行配置
     * @param bankId
     * @return
     */
    JxBankConfigVO getJxBankConfigByBankId(String bankId);

    /**
     * 获取数据字典表的下拉列表
     * @param nameClass
     * @return
     */
    List<ParamNameVO> getParamNameList(String nameClass);

    /**
     * 获取版本信息
     * @param type
     * @return
     */
    VersionVO getNewVersionByType(Integer type);

    /**
     * 获取强制更新版本号
     * @param type
     * @param isupdate
     * @param versionStr
     * @return
     */
    VersionVO getUpdateversion(Integer type, Integer isupdate, String versionStr);

    /**
     * 根据银行code查询银行配置
     * @auth sunpeikai
     * @param code 银行code,例如：招商银行,code是CMB
     * @return
     */
    BankConfigVO getBankConfigByCode(String code);

    /**
     * 根据银行id查询江西银行配置
     * @auth sunpeikai
     * @param id 银行id
     * @return
     */
    JxBankConfigVO getJxBankConfigById(Integer id);

    /**
     * 判断江西银行绑卡使用新
     * @param type
     * @return
     */
    Integer getBankInterfaceFlagByType(String type);

    /**
     * 根据设备唯一标识获取用户角标
     * @auth sunpeikai
     * @param sign 设备唯一标识
     * @return
     */
    UserCornerVO getUserCornerBySign(String sign);

    /**
     * 更新用户角标数据
     * @auth sunpeikai
     * @param userCornerVO 用户角标数据
     * @return
     */
    Integer updateUserCorner(UserCornerVO userCornerVO);

    /**
     * 插入一条新的用户角标数据
     * @auth sunpeikai
     * @param userCornerVO 用户角标数据
     * @return
     */
    Integer insertUserCorner(UserCornerVO userCornerVO);

    /**
     *查询平台的公告信息
     * @param request
     * @return
     */
    List<MessagePushTemplateVO> searchList(MsgPushTemplateRequest request);

    /**
     * app获取充值规则
     * @return
     */
    List<AppRechargeRuleVO> getRechargeRule();

    /**
     * app获取充值限额说明
     * @return
     */
    List getRechargeLimit();

    /**
     * 获取分享信息
     * @author wgx
     * @date 2019/05/09
     */
    ShareNewsBeanVO queryShareNews();

    /**
     * 获取客组配置
     *
     * @return
     */
    List<CustomerServiceGroupConfigVO> selectCustomerServiceGroupConfigList();

    /**
     * 获取客组类型为新客组的坐席配置
     *
     * @return
     */
    List<CustomerServiceRepresentiveConfigVO> selectCustomerServiceRepresentiveConfig();

    /**
     * 根据sourceId查询该渠道是否被禁用
     *
     * @param sourceId
     * @return
     */
    CustomerServiceChannelVO selectCustomerServiceChannelBySourceId(Integer sourceId);

    /**
     * 根据当前拥有人姓名查询坐席配置
     *
     * @param currentOwner
     * @return
     */
    CustomerServiceRepresentiveConfigVO selectCustomerServiceRepresentiveConfigByUserName(String currentOwner);

    /**
     * 获取状态为开启的坐席配置
     * @return
     */
    List<CustomerServiceRepresentiveConfigVO> selectRepresentiveConfig();
}
