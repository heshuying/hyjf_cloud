package com.hyjf.am.trade.service.admin.qianle.impl;

import com.hyjf.am.resquest.trade.DataSearchRequest;
import com.hyjf.am.trade.dao.mapper.customize.QianleMapper;
import com.hyjf.am.trade.service.admin.qianle.QianleService;
import com.hyjf.am.vo.trade.DataSearchCustomizeVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lisheng
 * @version QianleServiceImpl, v0.1 2018/8/30 16:10
 */
@Service
public class QianleServiceImpl implements QianleService {
    @Autowired
    QianleMapper qianleMapper;

    /**
     * 查询千乐渠道的散标
     * @param dataSearchRequest
     * @return
     */
    @Override
    public List<DataSearchCustomizeVO> querySanList(DataSearchRequest dataSearchRequest, Integer offset, Integer limit) {
        HashMap<String, Object> hashMap = new HashMap<>();
        if (StringUtils.isNotBlank(dataSearchRequest.getAddTimeStart())) {
            hashMap.put("addTimeStart",dataSearchRequest.getAddTimeStart());
        }
        if (StringUtils.isNotBlank(dataSearchRequest.getAddTimeEnd())) {
            hashMap.put("addTimeEnd",dataSearchRequest.getAddTimeEnd());
        }
        if (StringUtils.isNotBlank(dataSearchRequest.getRegTimeStart())) {
            hashMap.put("regTimeStart",dataSearchRequest.getRegTimeStart());
        }
        if (StringUtils.isNotBlank(dataSearchRequest.getRegTimeEnd())) {
            hashMap.put("regTimeEnd",dataSearchRequest.getRegTimeEnd());
        }
        if (offset != null && limit != null) {
            hashMap.put("limitStart", offset);
            hashMap.put("limitEnd", limit);
        }

        List< DataSearchCustomizeVO> resultList= qianleMapper.querySanList(hashMap);
        return resultList;
    }

    /**
     * 查询千乐渠道的计划
     * @param dataSearchRequest
     * @return
     */
    @Override
    public List<DataSearchCustomizeVO> queryPlanList(DataSearchRequest dataSearchRequest,Integer offset,Integer limit) {
        HashMap<String, Object> hashMap = new HashMap<>();
        if (StringUtils.isNotBlank(dataSearchRequest.getAddTimeStart())) {
            hashMap.put("addTimeStart",dataSearchRequest.getAddTimeStart());
        }
        if (StringUtils.isNotBlank(dataSearchRequest.getAddTimeEnd())) {
            hashMap.put("addTimeEnd",dataSearchRequest.getAddTimeEnd());
        }
        if (StringUtils.isNotBlank(dataSearchRequest.getRegTimeStart())) {
            hashMap.put("regTimeStart",dataSearchRequest.getRegTimeStart());
        }
        if (StringUtils.isNotBlank(dataSearchRequest.getRegTimeEnd())) {
            hashMap.put("regTimeEnd",dataSearchRequest.getRegTimeEnd());
        }
        if (offset != null && limit != null) {
            hashMap.put("limitStart", offset);
            hashMap.put("limitEnd", limit);
        }
        List<DataSearchCustomizeVO> resultList = qianleMapper.queryPlanList(hashMap);
        return resultList;
    }

    @Override
    public List<DataSearchCustomizeVO> queryList(DataSearchRequest dataSearchRequest, Integer offset, Integer limit) {
        HashMap<String, Object> hashMap = new HashMap<>();
        if (StringUtils.isNotBlank(dataSearchRequest.getAddTimeStart())) {
            hashMap.put("addTimeStart",dataSearchRequest.getAddTimeStart());
        }
        if (StringUtils.isNotBlank(dataSearchRequest.getAddTimeEnd())) {
            hashMap.put("addTimeEnd",dataSearchRequest.getAddTimeEnd());
        }
        if (StringUtils.isNotBlank(dataSearchRequest.getRegTimeStart())) {
            hashMap.put("regTimeStart",dataSearchRequest.getRegTimeStart());
        }
        if (StringUtils.isNotBlank(dataSearchRequest.getRegTimeEnd())) {
            hashMap.put("regTimeEnd",dataSearchRequest.getRegTimeEnd());
        }
        if (offset != null && limit != null) {
            hashMap.put("limitStart", offset);
            hashMap.put("limitEnd", limit);
        }
        return qianleMapper.queryList(hashMap);
    }

    /**
     * 查询计划数量
     * @param dataSearchRequest
     * @return
     */
    @Override
    public Integer queryPlanCount(DataSearchRequest dataSearchRequest) {
        HashMap<String, Object> hashMap = new HashMap<>();
        if (StringUtils.isNotBlank(dataSearchRequest.getAddTimeStart())) {
            hashMap.put("addTimeStart",dataSearchRequest.getAddTimeStart());
        }
        if (StringUtils.isNotBlank(dataSearchRequest.getAddTimeEnd())) {
            hashMap.put("addTimeEnd",dataSearchRequest.getAddTimeEnd());
        }
        if (StringUtils.isNotBlank(dataSearchRequest.getRegTimeStart())) {
            hashMap.put("regTimeStart",dataSearchRequest.getRegTimeStart());
        }
        if (StringUtils.isNotBlank(dataSearchRequest.getRegTimeEnd())) {
            hashMap.put("regTimeEnd",dataSearchRequest.getRegTimeEnd());
        }
        return qianleMapper.queryPlanCount(hashMap);
    }


    /**
     * 查询散标数量
     * @param dataSearchRequest
     * @return
     */
    @Override
    public Integer querySanCount(DataSearchRequest dataSearchRequest) {
        HashMap<String, Object> hashMap = new HashMap<>();
        if (StringUtils.isNotBlank(dataSearchRequest.getAddTimeStart())) {
            hashMap.put("addTimeStart",dataSearchRequest.getAddTimeStart());
        }
        if (StringUtils.isNotBlank(dataSearchRequest.getAddTimeEnd())) {
            hashMap.put("addTimeEnd",dataSearchRequest.getAddTimeEnd());
        }
        if (StringUtils.isNotBlank(dataSearchRequest.getRegTimeStart())) {
            hashMap.put("regTimeStart",dataSearchRequest.getRegTimeStart());
        }
        if (StringUtils.isNotBlank(dataSearchRequest.getRegTimeEnd())) {
            hashMap.put("regTimeEnd",dataSearchRequest.getRegTimeEnd());
        }
        return qianleMapper.querySanCount(hashMap);
    }

    /**
     * 查询数量
     * @param dataSearchRequest
     * @return
     */
    @Override
    public Integer queryCount(DataSearchRequest dataSearchRequest) {
        HashMap<String, Object> hashMap = new HashMap<>();
        if (StringUtils.isNotBlank(dataSearchRequest.getAddTimeStart())) {
            hashMap.put("addTimeStart",dataSearchRequest.getAddTimeStart());
        }
        if (StringUtils.isNotBlank(dataSearchRequest.getAddTimeEnd())) {
            hashMap.put("addTimeEnd",dataSearchRequest.getAddTimeEnd());
        }
        if (StringUtils.isNotBlank(dataSearchRequest.getRegTimeStart())) {
            hashMap.put("regTimeStart",dataSearchRequest.getRegTimeStart());
        }
        if (StringUtils.isNotBlank(dataSearchRequest.getRegTimeEnd())) {
            hashMap.put("regTimeEnd",dataSearchRequest.getRegTimeEnd());
        }
        return qianleMapper.queryCount(hashMap);
    }

    /**
     * 查询散标金额
     * @param dataSearchRequest
     * @return
     */
    @Override
    public Map<String, Object> querySanMoney(DataSearchRequest dataSearchRequest) {
        HashMap<String, Object> hashMap = new HashMap<>();
        if (StringUtils.isNotBlank(dataSearchRequest.getAddTimeStart())) {
            hashMap.put("addTimeStart",dataSearchRequest.getAddTimeStart());
        }
        if (StringUtils.isNotBlank(dataSearchRequest.getAddTimeEnd())) {
            hashMap.put("addTimeEnd",dataSearchRequest.getAddTimeEnd());
        }
        if (StringUtils.isNotBlank(dataSearchRequest.getRegTimeStart())) {
            hashMap.put("regTimeStart",dataSearchRequest.getRegTimeStart());
        }
        if (StringUtils.isNotBlank(dataSearchRequest.getRegTimeEnd())) {
            hashMap.put("regTimeEnd",dataSearchRequest.getRegTimeEnd());
        }
        return qianleMapper.querySanMoney(hashMap);
    }

    /**
     * 查询计划金额
     * @param dataSearchRequest
     * @return
     */
    @Override
    public Map<String, Object> queryPlanMoney(DataSearchRequest dataSearchRequest) {
        HashMap<String, Object> hashMap = new HashMap<>();
        if (StringUtils.isNotBlank(dataSearchRequest.getAddTimeStart())) {
            hashMap.put("addTimeStart",dataSearchRequest.getAddTimeStart());
        }
        if (StringUtils.isNotBlank(dataSearchRequest.getAddTimeEnd())) {
            hashMap.put("addTimeEnd",dataSearchRequest.getAddTimeEnd());
        }
        if (StringUtils.isNotBlank(dataSearchRequest.getRegTimeStart())) {
            hashMap.put("regTimeStart",dataSearchRequest.getRegTimeStart());
        }
        if (StringUtils.isNotBlank(dataSearchRequest.getRegTimeEnd())) {
            hashMap.put("regTimeEnd",dataSearchRequest.getRegTimeEnd());
        }
        return qianleMapper.queryPlanMoney(hashMap);
    }

    /**
     *查询首投nid
     * @param userId
     * @return
     */
    @Override
    public Map<String, Object> queryFirstTender(Integer userId) {
        HashMap<String, Object> req = new HashMap<>();
        req.put("userId", userId);
        Map<String, Object> map = qianleMapper.queryFirstTender(req);
        return map;
    }
}
