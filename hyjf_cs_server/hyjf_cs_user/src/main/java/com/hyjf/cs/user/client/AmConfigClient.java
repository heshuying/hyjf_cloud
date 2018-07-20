package com.hyjf.cs.user.client;

import com.hyjf.am.resquest.user.AnswerRequest;
import com.hyjf.am.vo.config.ParamNameVO;
import com.hyjf.am.vo.config.VersionVO;
import com.hyjf.am.vo.trade.BankReturnCodeConfigVO;
import com.hyjf.am.vo.trade.BanksConfigVO;
import com.hyjf.am.vo.config.SmsConfigVO;
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
     * @Description 根据bankId查询所属银行
     * @Author sunss
     * @Version v0.1
     * @Date 2018/6/5 15:13
     */
    BanksConfigVO getBankNameByBankId(String bankId);

    /**
     * 测评问题列表
     * @return
     */
    List<QuestionCustomizeVO> getNewQuestionList();

    /**
     * 计算测评得分
     * @param answerList
     * @return
     */
    int countScore(AnswerRequest answerList);
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
    BanksConfigVO getBanksConfigByBankId(String bankId);

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
}
