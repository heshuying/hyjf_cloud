package com.hyjf.admin.service.impl.promotion;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.client.UtmClient;
import com.hyjf.admin.service.promotion.UtmService;
import com.hyjf.am.response.admin.UtmResponse;
import com.hyjf.am.vo.admin.UtmVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author walter.limeng
 * @version UtmController, v0.1 2018/7/02 11:17
 */
@Service
public class UtmServiceImpl implements UtmService {
    private Logger logger = LoggerFactory.getLogger(UtmServiceImpl.class);
    @Autowired
    private UtmClient utmClient;
    @Override
    public JSONObject getByPageList(Map<String,Object> map, Integer currPage, Integer pageSize){
        JSONObject jsonObject = new JSONObject();
        Object object =map.get("recordTotal");
        Integer recodeTotal = 0;
        //处理前端传递到后台总条数，如为空或为0，则重新查询总条数
        if(null != object){
            recodeTotal = Integer.parseInt(object.toString());
        }
        if(0 == recodeTotal){
            UtmResponse utmResponse = utmClient.getCountByParam(map);
            if(null != utmResponse){
                recodeTotal = Integer.parseInt(utmResponse.getRecordTotal());
            }
        }
        //查询当前页数据
        map.put("limitStart",(currPage -1) * pageSize);
        map.put("limitEnd",currPage * pageSize);
        UtmResponse utmResponse = utmClient.getByPageList(map);
        List<UtmVo> list = new ArrayList<UtmVo>();
        if(null != utmResponse){
            list = utmResponse.getResultList();
        }
        jsonObject.put("currPage",currPage);
        jsonObject.put("pageSize",pageSize);
        jsonObject.put("resultList",list);
        jsonObject.put("recordTotal",recodeTotal);
        jsonObject.put("status", "00");
        jsonObject.put("msg", "成功");
        return jsonObject;
    }
}
