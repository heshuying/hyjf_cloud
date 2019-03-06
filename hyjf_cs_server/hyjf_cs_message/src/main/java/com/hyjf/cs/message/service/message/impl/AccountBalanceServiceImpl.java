package com.hyjf.cs.message.service.message.impl;

import com.hyjf.am.resquest.admin.HjhAccountBalanceRequest;
import com.hyjf.am.vo.admin.HjhAccountBalanceVO;
import com.hyjf.common.util.GetDate;
import com.hyjf.cs.message.bean.ic.HjhAccountBalance;
import com.hyjf.cs.message.mongo.ic.HjhAccountBalanceDao;
import com.hyjf.cs.message.service.message.AccountBalanceService;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author：yinhui
 * @Date: 2018/11/6  10:49
 */
@Service
public class AccountBalanceServiceImpl implements AccountBalanceService {

    private static final Logger logger = LoggerFactory.getLogger(AccountBalanceServiceImpl.class);
    @Autowired
    private HjhAccountBalanceDao hjhAccountBalanceDao;

    @Override
    public List<HjhAccountBalanceVO> getHjhAccountBalanceVOByMonth(HjhAccountBalanceRequest request,Boolean flag){
        String timeStartSrch = request.getAddTimeStart();
        String timeEndSrch = request.getAddTimeEnd();

        DBObject obj = new BasicDBObject();
        DBObject object = new BasicDBObject();

        try{
            //开始时间查询
            if(StringUtils.isNotBlank(timeStartSrch)){
                timeStartSrch = GetDate.getDayStart(timeStartSrch);
                object.put("$gte", GetDate.stringToDate(timeStartSrch));
                obj.put("date",object);
            }

            //结束时间查询
            if(StringUtils.isNotBlank(timeEndSrch)){
                timeEndSrch = GetDate.getDayEnd(timeEndSrch);
                object.put("$lte", GetDate.stringToDate(timeEndSrch));
                obj.put("date",object);
            }

            Query query = new BasicQuery(obj.toString());
//            if(flag){
////                int skip = pageSize * (pageNum - 1);
//                query.limit(request.getLimitEnd()).skip(request.getLimitStart());
//            }

            List<HjhAccountBalance> list = hjhAccountBalanceDao.getHjhAccountBalance(query);
            List<HjhAccountBalanceVO> appAccesStatisticsVO = transfromByMonth(list,flag,request);

            return appAccesStatisticsVO;

        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return null;
    }

    public List<HjhAccountBalanceVO> transfromByMonth(List<HjhAccountBalance> list,
                                                      boolean flag,HjhAccountBalanceRequest request){

        Map<String,HjhAccountBalance> map = new HashMap<>();
        for(HjhAccountBalance vo: list){
            String key = GetDate.date2Str(vo.getDate(),GetDate.yyyyMMdf);
            if(map.containsKey(key)){

                HjhAccountBalance temp = map.get(key);
                temp.setInvestAccount(temp.getInvestAccount().add(vo.getInvestAccount()));
                temp.setCreditAccount(temp.getCreditAccount().add(vo.getCreditAccount()));
                temp.setReinvestAccount(temp.getReinvestAccount().add(vo.getReinvestAccount()));
                temp.setAddAccount(temp.getAddAccount().add(vo.getAddAccount()));
            }else{
                map.put(key,vo);
            }
        }

        List<HjhAccountBalanceVO> listvo = new ArrayList<>();
        Set set = map.entrySet();
        Iterator it = set.iterator();
        Map.Entry entry = null;
        Object key = null;
        HjhAccountBalance value = null;
        while(it.hasNext()){
            entry = (Map.Entry)it.next();
            key = entry.getKey();
            value = (HjhAccountBalance) entry.getValue();
            if(value != null){
                HjhAccountBalanceVO vo = new HjhAccountBalanceVO();
                vo.setDataFormt(String.valueOf(key));
                vo.setInvestAccount(value.getInvestAccount());
                vo.setCreditAccount(value.getCreditAccount());
                vo.setReinvestAccount(value.getReinvestAccount());
                vo.setAddAccount(value.getAddAccount());
                listvo.add(vo);
            }
        }

        if(flag){
            int current=request.getCurrPage(); //页码
            int pageSize=request.getPageSize(); //每页显示的数量
            int totalCount=listvo.size();
            int pageCount = (totalCount / pageSize) + ((totalCount % pageSize > 0) ? 1 : 0);

            if(current < 1){
                current = 1;
            }
            int start=(current-1) * pageSize;
            int end = Math.min(totalCount, current * pageSize);

            if(pageCount >= current){
                listvo=listvo.subList(start,end);
            }else{
                listvo = new ArrayList<>();
            }
        }

        return listvo;
    }

    @Override
    public List<HjhAccountBalanceVO> getHjhAccountBalanceVOByDay(HjhAccountBalanceRequest request,Boolean flag){
        String timeStartSrch = request.getAddTimeStart();
        String timeEndSrch = request.getAddTimeEnd();

        DBObject obj = new BasicDBObject();
        DBObject object = new BasicDBObject();

        try{
            //开始时间查询
            if(StringUtils.isNotBlank(timeStartSrch)){
                timeStartSrch = GetDate.getDayStart(timeStartSrch);
                object.put("$gte", GetDate.stringToDate(timeStartSrch));
                obj.put("date",object);
            }

            //结束时间查询
            if(StringUtils.isNotBlank(timeEndSrch)){
                timeEndSrch = GetDate.getDayEnd(timeEndSrch);
                object.put("$lte", GetDate.stringToDate(timeEndSrch));
                obj.put("date",object);
            }

            Query query = new BasicQuery(obj.toString());
            if(flag){
//                int skip = pageSize * (pageNum - 1);
                query.limit(request.getLimitEnd()).skip(request.getLimitStart());
            }
            List<HjhAccountBalance> list = hjhAccountBalanceDao.getHjhAccountBalance(query);
            List<HjhAccountBalanceVO> appAccesStatisticsVO = transfromByDay(list);

            return appAccesStatisticsVO;

        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return null;
    }

    public List<HjhAccountBalanceVO> transfromByDay(List<HjhAccountBalance> list){
        List<HjhAccountBalanceVO> listvo = new ArrayList<>();
        for(HjhAccountBalance vo: list){
            String key = GetDate.date2Str(vo.getDate(),GetDate.date_sdf);

            HjhAccountBalanceVO temp = new HjhAccountBalanceVO();
            temp.setRptDate(vo.getDate());
            temp.setDataFormt(key);
            temp.setInvestAccount(vo.getInvestAccount());
            temp.setCreditAccount(vo.getCreditAccount());
            temp.setReinvestAccount(vo.getReinvestAccount());
            temp.setAddAccount(vo.getAddAccount());
            listvo.add(temp);
        }
        return listvo;
    }
}
