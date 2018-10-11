package com.hyjf.admin.service.impl.promotion;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.client.AmUserClient;
import com.hyjf.admin.service.promotion.UtmService;
import com.hyjf.am.response.admin.UtmResponse;
import com.hyjf.am.response.admin.promotion.UtmResultResponse;
import com.hyjf.am.response.user.UtmPlatResponse;
import com.hyjf.am.vo.admin.UtmVO;
import com.hyjf.am.vo.user.UtmPlatVO;
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
    private AmUserClient amUserClient;
    @Override
    public UtmResultResponse getByPageList(Map<String,Object> map, Integer currPage, Integer pageSize){
        UtmResultResponse utmResultResponse = new UtmResultResponse();
        Object object =map.get("recordTotal");
        Integer recodeTotal = 0;
        //处理前端传递到后台总条数，如为空或为0，则重新查询总条数
        if(null != object){
            recodeTotal = Integer.parseInt(object.toString());
        }
        if(0 == recodeTotal){
            UtmResponse utmResponse = amUserClient.getCountByParam(map);
            if(null != utmResponse){
                recodeTotal = utmResponse.getRecordTotal();
            }
        }
        //查询当前页数据
        map.put("limitStart",(currPage -1) * pageSize);
        map.put("limitEnd",currPage * pageSize);
        UtmResponse utmResponse = amUserClient.getByPageList(map);
        List<UtmVO> list = new ArrayList<UtmVO>();
        if(null != utmResponse){
            list = utmResponse.getResultList();
        }
        map.clear();
        UtmPlatResponse utmPlatResponse = amUserClient.getAllUtmPlat(map);
        List<UtmPlatVO> utmPlatVOList = new ArrayList<UtmPlatVO>();
        if(null != utmPlatResponse){
            utmPlatVOList = utmPlatResponse.getResultList();
        }
        utmResultResponse.setUtmPlatList(utmPlatVOList);
        utmResultResponse.setResultList(list);
        utmResultResponse.setTotal(recodeTotal);
        return utmResultResponse;
    }

    @Override
    public UtmPlatVO getDataById(Integer id) {
        return amUserClient.getDataById(id);
    }

    @Override
    public int sourceNameIsExists(String sourceName, Integer sourceId) {
        return amUserClient.sourceNameIsExists(sourceName,sourceId);
    }

    @Override
    public boolean insertOrUpdateUtmPlat(UtmPlatVO utmPlatVO) {
        return amUserClient.insertOrUpdateUtmPlat(utmPlatVO);
    }

    @Override
    public boolean deleteUtmPlatAction(UtmPlatVO utmPlatVO) {
        return amUserClient.utmClientdeleteUtmPlatAction(utmPlatVO);
    }
}
