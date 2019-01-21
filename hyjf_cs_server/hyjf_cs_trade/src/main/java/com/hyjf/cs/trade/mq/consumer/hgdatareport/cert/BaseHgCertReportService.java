package com.hyjf.cs.trade.mq.consumer.hgdatareport.cert;


import com.hyjf.bank.service.BaseService;
import com.hyjf.mongo.hgdatareport.entity.CertReportEntity;
import com.hyjf.mybatis.model.auto.*;
import com.hyjf.mybatis.model.customize.UserEvalationResultCustomize;

import java.util.List;

/**
 * 
 * //合规数据上报 CERT
 * @author sunss
 * @version hyjf 1.0
 * @since hyjf 1.0 2018年11月26日
 * @see 上午11:45:03
 */
public interface BaseHgCertReportService extends BaseService {

   /**
    * 调用sendPost接口并返回数据
    * @author sunss
    * @param bean
    * @return
    */
   CertReportEntity insertAndSendPost(CertReportEntity bean);

   /**
    * 获取企业户
    * @param userId
    * @return
    */
   CorpOpenAccountRecord getCorpOpenAccountRecord(Integer userId) ;

   /**
    * 根据借款编号取借款主体为企业用户的信息
    * @param borrowNid
    * @return
    */
   BorrowUsers getBorrowUsers(String borrowNid) ;

   /**
    * 根据标的号检索借款主体个人借款信息
    * @param borrowNid
    * @return
    */
   BorrowManinfo getBorrowManInfo(String borrowNid) ;

   /**
    * 根据用户ID获取用户测评结果对象
    * @param userId 用户ID
    * @return UserEvalationResult
    */
   UserEvalationResultCustomize getUserEvalationResult(Integer userId);

   /**
    * 根据用户Id,查询用户银行卡信息
    *
    * @param userId
    * @return
    */
   BankCard getBankCardById(Integer userId);

   /**
    * 查询是否已经上送了
    * @param userId
    * @return
    */
   CertUser getCertUserByUserId(Integer userId) ;

   /**
    * 获取项目类型
    * @param projectType
    * @return
    */
   BorrowProjectType getBorrowProjectType(String projectType) ;

   /**
    * 根据用户哈希值查询是否已经上报过了
    * @param userIdcardHash
    * @return
    */
   CertUser getCertUserByUserIdcardHash(String userIdcardHash) ;

   /**
    * 根据用户ID查询上报的标的
    * @param userId
    * @return
    */
   List<CertUser> getCertUsersByUserId(int userId);

   /**
    * 检查redis的值是否允许运行 允许返回true  不允许返回false
    * @return
    */
    boolean checkCanRun();
}

	