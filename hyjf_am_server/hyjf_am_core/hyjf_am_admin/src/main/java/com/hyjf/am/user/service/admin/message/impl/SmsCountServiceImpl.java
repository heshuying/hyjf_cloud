/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.admin.message.impl;

import com.hyjf.am.resquest.admin.SmsCodeUserRequest;
import com.hyjf.am.resquest.user.SmsCountRequest;
import com.hyjf.am.user.dao.mapper.auto.SmsCountMapper;
import com.hyjf.am.user.dao.mapper.customize.SmsCountCustomizeMapper;
import com.hyjf.am.user.dao.model.customize.OADepartmentCustomize;
import com.hyjf.am.user.dao.model.customize.SmsCountCustomize;
import com.hyjf.am.user.service.admin.message.SmsCountService;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.util.GetDate;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author fq
 * @version SmsCountServiceImpl, v0.1 2018/8/20 16:28
 */
@Service
public class SmsCountServiceImpl implements SmsCountService {
    private static final Logger logger = LoggerFactory.getLogger(SmsCountServiceImpl.class);

    @Autowired
    private SmsCountCustomizeMapper smsCountCustomizeMapper;
    @Autowired
    private SmsCountMapper smsCountMapper;

    @Override
    public List<SmsCountCustomize> querySmsCountLlist(SmsCountRequest request) {
        //根据需求：2018年12月27日之前的按照0.042分钱算，之后按0.04分钱算
        List<SmsCountCustomize> list = smsCountCustomizeMapper.querySmsCountLlist(request);
//        String configMoney = CacheUtil.getParamName("SMS_COUNT_PRICE", "PRICE");
//        if (StringUtils.isEmpty(configMoney)) {
//            configMoney = "0.042";//短信单价（0.042元/条）
//        }
//        DecimalFormat decimalFormat = new DecimalFormat("0.000");
//        if (!CollectionUtils.isEmpty(list)) {
//            for (SmsCountCustomize sms: list) {
//                sms.setSmsMoney(decimalFormat.format(new BigDecimal(configMoney).multiply(new BigDecimal(sms.getSmsNumber()))));
//            }
//            return list;
//        }
        return list;
    }

    @Override
    public List<SmsCountCustomize> querySmsCountNumberTotal(SmsCountRequest request) {
        return smsCountCustomizeMapper.querySmsCountNumberTotal(request);
    }

    @Override
    public List<OADepartmentCustomize> queryDepartmentInfo() {
        return smsCountCustomizeMapper.queryDepartmentInfo();
    }

    @Override
    public int selectCount(SmsCountRequest request) {
        return smsCountCustomizeMapper.selectCount(request);
    }

    @Override
    public List<String> queryUser(SmsCodeUserRequest request) {
        Map<String, Object> params = new HashMap<>();
        if (request.getOpen_account() != null && request.getOpen_account() != 3) {
            params.put("open_account", request.getOpen_account());
        }
        if (org.apache.commons.lang3.StringUtils.isNotBlank(request.getRe_time_begin())) {
            params.put("re_time_begin", GetDate.dateString2Timestamp(GetDate.getDayStart(request.getRe_time_begin())));
        }
        if (org.apache.commons.lang3.StringUtils.isNotBlank(request.getRe_time_end())) {
            params.put("re_time_end", GetDate.dateString2Timestamp(GetDate.getDayEnd(request.getRe_time_end())));
        }
        return smsCountCustomizeMapper.queryUser(params);
    }

    /**
     * 计算短信条数
     * @param messageStr
     * @return
     */
    private Integer getcontentSize(String messageStr){
        //按照字数计算短信条数
        int  contentSize  = 0;
        int size = messageStr.length();
        if(size > 70){
            //算法计算字数规则  小于等于70算一条 ，大于70后每67字加一条
            if((size-70)%67 != 0){
                contentSize = contentSize+1;
            }
            contentSize = contentSize+ 1+(size-70)/67;
        }else {
            contentSize = 1;
        }

        return contentSize;
    }


    /**
     * 短信统计，将手机号码对应到分公司算短信发送数量，财务算账用
     * @param mobile
     * @param messageStr
     */
    @Override
    public Map<String, SmsCountCustomize> getSmsCount(String mobile, String messageStr){
        logger.info("【短信统计】开始插入sms_count");
        //过滤掉类型为空的数据
        if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(messageStr)) {
            logger.warn("【短信统计】手机号码或者短信内容为空 =========== mobile=" +mobile+",messageStr"+messageStr);
            return null;
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //插入map，因为get方法内不能做写操作，需要返回到Controller重新插入
        Map<String, SmsCountCustomize> uniqVO = new HashMap<>();

        //按照字数计算短信条数
        int  contentSize = getcontentSize(messageStr);

        //拆分手机号码
        String [] array = mobile.split(",");
        String postTime = simpleDateFormat.format(new Date());
        for(int i=0;i<array.length;i++){
            SmsCountCustomize smsCountCustomize = new SmsCountCustomize();
            Map<String,Object> map = smsCountCustomizeMapper.getDeptByMobile(array[i]);
            if( map==null ){
                //其他类型
                smsCountCustomize.setDepartmentId(0);
                smsCountCustomize.setDepartmentName("其他");
            }else {
                smsCountCustomize.setDepartmentId(Integer.valueOf(String.valueOf(map.get("deptId"))));
                smsCountCustomize.setDepartmentName(String.valueOf(map.get("deptName")));

            }
            smsCountCustomize.setPosttime(postTime);
            smsCountCustomize.setSmsNumber(contentSize);

            //查询是否已存在
            List<SmsCountCustomize> list = smsCountCustomizeMapper.querySms(smsCountCustomize);
            if(list.size() > 0){
                //通过部门和发送时间查询
                //只有一条
                smsCountCustomize =  list.get(0);
                smsCountCustomize.setSmsNumber(smsCountCustomize.getSmsNumber()+contentSize);
                logger.debug("更新sms_count條數============================="+(smsCountCustomize.getSmsNumber()+contentSize));
            }
            String key = smsCountCustomize.getDepartmentId()+smsCountCustomize.getDepartmentName();
            if(uniqVO.containsKey(key)){
                smsCountCustomize.setSmsNumber(uniqVO.get(key).getSmsNumber()+contentSize);
            }else{
                smsCountCustomize.setSmsNumber(smsCountCustomize.getSmsNumber());
            }
            uniqVO.put(key,smsCountCustomize);
        }

        return uniqVO;
    }

    /**
     * 批量保存or更新短信统计条数
     * @param paramMap
     */
    @Override
    public void addSmsCount(Map<String, SmsCountCustomize> paramMap){

        List<SmsCountCustomize> insertList = new ArrayList<>();
        for(SmsCountCustomize customize :paramMap.values()){
            insertList.add(customize);
        }
        smsCountCustomizeMapper.insertBatch(insertList);
    }

    /********** 短信统计  临时类需要的方法      ********/
    @Override
    public List<SmsCountCustomize> getuserIdAnddepartmentName(){
       return smsCountCustomizeMapper.getuserIdAnddepartmentName();
    }

    @Override
    public List<UserVO> selectUserListByMobile(List<String> list){
        return smsCountCustomizeMapper.selectUserListByMobile(list);
    }

    @Override
    public void insertBatchSmsCount( List<SmsCountCustomize> insertListsms){

        smsCountCustomizeMapper.insertBatchSmsCount(insertListsms);
    }

    /**
     * 修改and删除短信统计重复数据
     */
    @Override
    public void updateOrDelectRepeatData(){

        //查询所有重复数据
        Integer count = smsCountCustomizeMapper.selectRepeatSmsCount();
        if(count != null && count > 0){

            Integer loopCount = (count + 5000 - 1) / 5000;

            Map<String,Object> mapLimit = null;
            //分页
            for (int i = 0; i < loopCount; i++) {
                mapLimit = new HashMap<>();
                mapLimit.put("limitStart",i*5000);
                mapLimit.put("limitEnd",5000);

                List<SmsCountCustomize> listVo = smsCountCustomizeMapper.selectRepeatSmsCountData(mapLimit);
                if(CollectionUtils.isEmpty(listVo)){
                    continue;
                }

                List<SmsCountCustomize> updateVO = new ArrayList<>();
                StringBuilder delectBuilder = new StringBuilder();
                for(SmsCountCustomize vo: listVo){

                    String repeatId = vo.getListRepeatId();
                    if(StringUtils.isBlank(repeatId)){
                        continue;
                    }
                    String[] repeatIdArray = repeatId.split(",");
                    if(repeatIdArray == null || repeatIdArray.length < 2){
                        continue;
                    }
                    SmsCountCustomize smsCount = new SmsCountCustomize();
                    smsCount.setId(Integer.valueOf(repeatIdArray[0]));
                    smsCount.setSmsNumber(vo.getSmsNumber());

                    updateVO.add(smsCount);
                    delectBuilder.append(repeatId.substring
                            (repeatId.indexOf(","),vo.getListRepeatId().length()));
                }
                String newDel = delectBuilder.toString().substring(1,delectBuilder.length());
                int[] listId = StringToInt(newDel.split(","));
                smsCountCustomizeMapper.updateBatch(updateVO);
                smsCountCustomizeMapper.deleteBatch(listId);
            }

        }

    }

    public int[] StringToInt(String[] arrs){
        int[] ints = new int[arrs.length];
        for (int i =0 ;i<arrs.length;i++){
            ints[i] = Integer.valueOf(arrs[i]);
        }
        return ints;
    }

}
