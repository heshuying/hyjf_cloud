package com.hyjf.cs.trade.service.consumer.hgdatareport.cert.userinfo;

import com.alibaba.fastjson.JSONArray;
import com.hyjf.am.vo.trade.cert.CertSendUserVO;
import com.hyjf.am.vo.trade.cert.CertUserVO;
import com.hyjf.cs.trade.service.consumer.BaseHgCertReportService;

import java.util.List;
import java.util.Map;


/**
 * @author sss
 */

public interface CertUserInfoService extends BaseHgCertReportService {


    /**
     * 组装调用应急中心日志
     * @param user
     * @param borrowNid
     * @return
     */
    JSONArray getSendData(CertSendUserVO user, String borrowNid, List<CertUserVO> certUser) throws Exception;

    /**
     * 插入国家互联网应急中心已上送用户表
     * @param certUser
     */
    void insertCertUser(CertUserVO certUser);

    /**
     * 组装单个用户数据
     * @param item
     * @return
     */
    Map<String,Object> getUserData(CertSendUserVO item, String borrowNid, String userStatus) throws Exception;

    /**
     * 根据userId查询需要上报的用户信息
     * @param userId
     * @return
     */
    CertSendUserVO getCertSendUserByUserId(int userId);

    /**
     * 修改国家互联网应急中心已上送用户表
     * @param certUser
     */
    void updateCertUser(CertUserVO certUser);

    /**
     * 批量插入上报记录
     * @param certUsers
     */
    void insertCertUserByList(List<CertUserVO> certUsers);

    /**
     * 根据borrowNid userId查询
     * @param userId
     * @param borrowNid
     * @return
     */
    CertUserVO getCertUserByUserIdBorrowNid(int userId, String borrowNid);

}