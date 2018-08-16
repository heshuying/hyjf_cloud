package com.hyjf.admin.client.impl;

import com.hyjf.admin.client.BorrowFlowClient;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.AdminBorrowFlowResponse;
import com.hyjf.am.response.config.ParamNameResponse;
import com.hyjf.am.response.user.HjhInstConfigResponse;
import com.hyjf.am.resquest.admin.AdminBorrowFlowRequest;
import com.hyjf.am.vo.admin.HjhAssetTypeVO;
import com.hyjf.am.vo.config.ParamNameVO;
import com.hyjf.am.vo.trade.borrow.BorrowProjectTypeVO;
import com.hyjf.am.vo.trade.hjh.HjhAssetBorrowTypeVO;
import com.hyjf.am.vo.user.HjhInstConfigVO;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
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
        HjhInstConfigResponse response =restTemplate.getForEntity("http://AM-TRADE/am-trade/config/borrowflow/hjhInstConfigList",HjhInstConfigResponse.class)
                .getBody();
        if(response == null){
           return null;
        }
        return response.getResultList();
    }

    /**
     * 根据表的类型,期数,项目类型检索管理费件数
     * @param instCode assetType
     * @return
     */
    @Override
    public int countRecordByPK(String instCode, Integer assetType){
        return restTemplate.getForEntity("http://AM-TRADE/am-trade/config/borrowflow/countRecordByPK/"+instCode+"/"+assetType,int.class)
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
        List<HjhAssetBorrowTypeVO>  hjhAssetBorrowType=new ArrayList<>();
        ParamNameResponse amResponse = restTemplate.getForEntity("http://AM-CONFIG/am-config/accountconfig/getNameCd/"+"FLOW_STATUS", ParamNameResponse.class)
                .getBody();
        AdminBorrowFlowResponse response= restTemplate.postForEntity("http://AM-TRADE/am-trade/config/borrowflow/selectBorrowFlowList",adminRequest,AdminBorrowFlowResponse.class)
                .getBody();
        if( Response.isSuccess(amResponse)&& Response.isSuccess(response)){
            List<ParamNameVO>  paramNameS =amResponse.getResultList();
            List<HjhAssetBorrowTypeVO>  hjhAssetBorrowTypeVOS =response.getResultList();
            if( !CollectionUtils.isEmpty(hjhAssetBorrowTypeVOS)&&!CollectionUtils.isEmpty(paramNameS)){
                for(int i=0;i<paramNameS.size();i++){
                    for(int j=0;j<hjhAssetBorrowTypeVOS.size();j++){
                        if(paramNameS.get(i).getNameCd().equals(String.valueOf(hjhAssetBorrowTypeVOS.get(j).getIsOpen()))){
                            hjhAssetBorrowTypeVOS.get(j).setStatus(paramNameS.get(i).getName());
                            hjhAssetBorrowType.add(hjhAssetBorrowTypeVOS.get(j));
                        }
                    }
                }
            }
            response.setResultList(hjhAssetBorrowType);
            return response;
        }else{
            response.setRtn(Response.FAIL);
        }
        return response;
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
