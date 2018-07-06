package com.hyjf.cs.user.client;

import com.hyjf.am.resquest.user.AnswerRequest;
import com.hyjf.am.vo.config.ParamNameVO;
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

    List<QuestionCustomizeVO> getNewQuestionList();

    int countScore(AnswerRequest answerList);

    BankReturnCodeConfigVO getBankReturnCodeConfig(String retCode);

    String queryBankIdByCardNo(String cardNo);

    BanksConfigVO getBanksConfigByBankId(String bankId);

    /**
     * 获取数据字典表的下拉列表
     * @param nameClass
     * @return
     */
    List<ParamNameVO> getParamNameList(String nameClass);
}
