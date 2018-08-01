package com.hyjf.admin.client.impl;

import com.hyjf.admin.client.BorrowFlowClient;
import com.hyjf.am.response.admin.AdminBorrowFlowResponse;
import com.hyjf.am.resquest.admin.AdminBorrowFlowRequest;
import com.hyjf.am.vo.admin.HjhAssetTypeVO;
import com.hyjf.am.vo.admin.coupon.ParamName;
import com.hyjf.am.vo.config.ParamNameVO;
import com.hyjf.am.vo.trade.borrow.BorrowProjectTypeVO;
import com.hyjf.am.vo.user.HjhInstConfigVO;
import com.hyjf.common.util.CommonUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author by xiehuili on 2018/7/30.
 */
@Service
public class BorrowFlowClientImpl implements BorrowFlowClient {
    @Autowired
    private RestTemplate restTemplate;
    /**
     * 项目类型
     *
     * @return
     */
    @Override
    public List<BorrowProjectTypeVO> borrowProjectTypeList(String borrowTypeCd){
        return restTemplate.getForEntity("http://AM-TRADE/am-trade/config/borrowflow/borrowProjectTypeList/"+borrowTypeCd,List.class)
                .getBody();
    }

    /**
     * 资金来源
     * @param instCode
     * @return
     */
    @Override
    public List<HjhInstConfigVO> hjhInstConfigList(String instCode){
        return restTemplate.getForEntity("http://AM-TRADE/am-trade/config/borrowflow/hjhInstConfigList/"+instCode,List.class)
                .getBody();
    }

    /**
     * 根据表的类型,期数,项目类型检索管理费件数
     * @author liubin
     * @param instCode assetType
     * @return
     */
    @Override
    public int countRecordByPK(String instCode, Integer assetType){
        return restTemplate.getForEntity("http://AM-TRADE/am-trade/config/borrowflow/hjhInstConfigList/"+instCode+"/"+assetType,int.class)
                .getBody();
    }
    /**
     * 根据资金来源查询产品类型
     * @param instCode
     * @return
     */
    @Override
    public List<HjhAssetTypeVO> hjhAssetTypeList(String instCode){
        return restTemplate.getForEntity("http://AM-TRADE/am-trade/config/borrowflow/hjhAssetTypeList/"+instCode,List.class)
                .getBody();
    }
    /**
     * 分页查询
     * @param adminRequest
     * @return
     */
    @Override
    public AdminBorrowFlowResponse selectBorrowFlowList(AdminBorrowFlowRequest adminRequest){
        List<ParamName> paramNameS = restTemplate.getForEntity("http://AM-CONFIG/am-config/accountconfig/getNameCd/"+"FLOW_STATUS", List.class)
                .getBody();
        List<ParamNameVO> paramNameVOS = CommonUtils.convertBeanList(paramNameS,ParamNameVO.class);
        if( !CollectionUtils.isEmpty(paramNameVOS)){
            adminRequest.setParamNameVOS(paramNameVOS);
        }
        return restTemplate.postForEntity("http://AM-TRADE/am-trade/config/borrowflow/selectBorrowFlowList",adminRequest,AdminBorrowFlowResponse.class)
                .getBody();
    }
    /**
     * 详情
     * @param adminRequest
     * @return
     */
    @Override
    public AdminBorrowFlowResponse selectBorrowFlowInfo(AdminBorrowFlowRequest adminRequest){
        return restTemplate.postForEntity("http://AM-TRADE/am-trade/config/borrowflow/info",adminRequest,AdminBorrowFlowResponse.class)
                .getBody();
    }

    /**
     * 添加
     * @param adminRequest
     * @return
     */
    @Override
    public void insertRecord(AdminBorrowFlowRequest adminRequest){
        restTemplate.postForEntity("http://AM-TRADE/am-trade/config/borrowflow/insertRecord",adminRequest,AdminBorrowFlowResponse.class)
                .getBody();
    }
    /**
     * 修改
     * @param adminRequest
     * @return
     */
    @Override
    public void updateRecord(AdminBorrowFlowRequest adminRequest){
        restTemplate.postForEntity("http://AM-TRADE/am-trade/config/borrowflow/updateRecord",adminRequest,AdminBorrowFlowResponse.class)
                .getBody();
    }
    /**
     * 删除
     * @param adminRequest
     * @return
     */
    @Override
    public void deleteRecord(AdminBorrowFlowRequest adminRequest){
        restTemplate.postForEntity("http://AM-TRADE/am-trade/config/borrowflow/deleteRecord",adminRequest,AdminBorrowFlowResponse.class)
                .getBody();
    }
}
