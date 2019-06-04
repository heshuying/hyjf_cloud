package com.hyjf.cs.message.service.hgreportdata.caijing.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.hyjf.am.vo.hgreportdata.caijing.ZeroOneDataVO;
import com.hyjf.am.vo.user.AccountMobileSynchVO;
import com.hyjf.am.vo.user.CertificateAuthorityVO;
import com.hyjf.common.http.HttpDeal;
import com.hyjf.common.security.util.MD5;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.cs.message.bean.hgreportdata.caijing.ZeroOneDataEntity;
import com.hyjf.cs.message.bean.hgreportdata.caijing.ZeroOneResponse;
import com.hyjf.cs.message.client.AmTradeClient;
import com.hyjf.cs.message.client.AmUserClient;
import com.hyjf.cs.message.service.hgreportdata.caijing.ZeroOneCaiJingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

/**
 * @Author: yinhui
 * @Date: 2019/6/3 10:33
 * @Version 1.0
 */
@Service
public class ZeroOneCaiJingServiceImpl implements ZeroOneCaiJingService {

    private final Logger logger = LoggerFactory.getLogger(ZeroOneCaiJingServiceImpl.class);

    @Autowired
    private AmTradeClient amTradeClient;

    @Autowired
    private AmUserClient amUserClient;

    /**
     * 借款记录接口报送
     */
    @Override
    public void investRecordSub(){
        logger.info("借款记录接口报送开始");
//        String date = "2019-02-02";
        String date = "2018-4-24";

        List<ZeroOneDataVO> zeroOneDataVOList = amTradeClient.queryInvestRecordSub(date,date);

        if(zeroOneDataVOList == null || zeroOneDataVOList.size() == 0){
            return;
        }
        logger.info("借款记录接口报送条数="+zeroOneDataVOList.size());

        Set<Integer> listUserId = new HashSet<>();
        for(ZeroOneDataVO voList : zeroOneDataVOList){
            listUserId.add(voList.getUserIds());
        }

        Map<Integer,String> mapUserId =queryCustomerId(listUserId);
        if(mapUserId == null || mapUserId.size() == 0){
            logger.info("借款记录接口报送结束,报送用户id为空");
            return;
        }

        for(ZeroOneDataVO voList : zeroOneDataVOList){
            if(mapUserId.get(voList.getUserIds()) != null){
                voList.setUsername(mapUserId.get(voList.getUserIds()));
                voList.setUserid(mapUserId.get(voList.getUserIds()));
            }else{
                logger.info("借款记录接口报送 当前用户编号为空,userId="+voList.getUserIds());
                voList.setUserid(null);
                voList.setUsername(null);
            }

        }

        List<ZeroOneDataEntity> list = CommonUtils.convertBeanList(zeroOneDataVOList, ZeroOneDataEntity.class);

        Boolean sendStatus = sendDataReport(list);
        if(sendStatus){
            //报送成功
            logger.info("借款记录接口报送成功");
        }
        logger.info("借款记录接口报送结束");
    }

    /**
     * 获得用户编号
     * @param userId
     * @return
     */
    private Map<Integer,String> queryCustomerId(Set<Integer> userId){
        if(userId == null || userId.size() == 0){
            return null;
        }
        Map<Integer,String> mapUserId = new HashMap<>();

        List<CertificateAuthorityVO> voList = amUserClient.queryCustomerId(userId);
        for(CertificateAuthorityVO vo : voList){
            mapUserId.put(vo.getUserId(),vo.getCustomerId());
        }
        return mapUserId;
    }

    /**
     * 数据报送
     * @return
     */
    private Boolean sendDataReport(List<ZeroOneDataEntity> dataVOList){

        String url = "http://data.01caijing.com/hub/p2p/invest/commit.json";

        Map<String,String> sendMap = new HashMap<>();

        try{
            String json = String.valueOf(JSONObject.toJSON(dataVOList));

            sendMap.put("visit_key","frontweb1.hyjf.com");
            sendMap.put("time",String.valueOf(System.currentTimeMillis()));
            sendMap.put("data",json);

            //Sige md5(密钥&visit_key=域名&time=时间戳&data=data字符串的byte长度)
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("5A25DD85BD76829D537C7E59AA5DA766");
            stringBuilder.append("&visit_key=");
            stringBuilder.append(sendMap.get("visit_key"));
            stringBuilder.append("&time=");
            stringBuilder.append(sendMap.get("time"));
            stringBuilder.append("&data=");
            stringBuilder.append(json.getBytes().length);

            String sign = MD5.toMD5CodeCaps(stringBuilder.toString());

            sendMap.put("sign",sign);

            //Post请求
            String response = HttpDeal.post(url,sendMap);

            ZeroOneResponse zeroOneResponse = JSONObject.parseObject(response,ZeroOneResponse.class);

            if(zeroOneResponse != null && zeroOneResponse.result_code == 1){
                return true;
            }

        }catch (Exception e){
            logger.error("零壹财经数据报送错误 error:",e);
        }

        return false;
    }
}
