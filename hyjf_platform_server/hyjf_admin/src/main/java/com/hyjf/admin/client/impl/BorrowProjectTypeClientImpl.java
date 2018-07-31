package com.hyjf.admin.client.impl;

import com.hyjf.admin.client.BorrowProjectTypeClient;
import com.hyjf.am.response.trade.BorrowProjectTypeResponse;
import com.hyjf.am.resquest.trade.BorrowProjectTypeRequest;
import com.hyjf.am.vo.admin.coupon.ParamName;
import com.hyjf.am.vo.config.ParamNameVO;
import com.hyjf.am.vo.trade.borrow.BorrowProjectRepayVO;
import com.hyjf.am.vo.trade.borrow.BorrowProjectTypeVO;
import com.hyjf.am.vo.trade.borrow.BorrowStyleVO;
import com.hyjf.common.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author by xiehuili on 2018/7/27.
 */
@Service
public class BorrowProjectTypeClientImpl implements BorrowProjectTypeClient {
    @Autowired
    private RestTemplate restTemplate;

    /**
     * 查询列表
     * @param adminRequest
     * @return
     */
    @Override
    public BorrowProjectTypeResponse selectProjectTypeList(BorrowProjectTypeRequest adminRequest){
        return restTemplate.postForEntity("http://AM-TRADE/am-trade/config/projecttype/selectProjectTypeList",adminRequest, BorrowProjectTypeResponse.class)
                .getBody();
    }
    /**
     *（条件）列表查询--其他相关字段
     * @return
     */
    @Override
    public List<ParamNameVO>  selectProjectTypeParamList(){
        return restTemplate.postForEntity("http://AM-CONFIG/am-config/accountconfig/selectProjectTypeParamList",null, List.class)
                .getBody();
    }
    /**
     * 查询项目类型 详情
     * @param adminRequest
     * @return
     */
    @Override
    public BorrowProjectTypeResponse selectProjectTypeRecord(BorrowProjectTypeRequest adminRequest){
        return restTemplate.postForEntity("http://AM-TRADE/am-trade/config/projecttype/selectProjectTypeRecord",adminRequest, BorrowProjectTypeResponse.class)
                .getBody();
    }
    /**
     * 根据主键判断汇直投项目类型维护中数据是否存在
     * @return
     */
    @Override
    public boolean isExistsRecord(BorrowProjectTypeVO record){
        return restTemplate.postForEntity("http://AM-TRADE/am-trade/config/projecttype/isExistsRecord",record, boolean.class)
                .getBody();
    }

    /**
     * 获取单个汇直投项目类型维护
     * @return
     */
    @Override
    public BorrowProjectTypeVO getRecord(BorrowProjectTypeVO record){
        return restTemplate.postForEntity("http://AM-TRADE/am-trade/config/projecttype/getRecord",record, BorrowProjectTypeVO.class)
                .getBody();
    }

    /**
     * 根据项目编号查询还款方式
     * @param str
     */
    @Override
    public List<BorrowProjectRepayVO> selectRepay(String str){
        return restTemplate.postForEntity("http://AM-TRADE/am-trade/config/projecttype/selectRepay",str, List.class)
                .getBody();
    }
    /**
     * 查询类型表
     */
    @Override
    public List<BorrowStyleVO> selectStyles(){
        return restTemplate.getForEntity("http://AM-TRADE/am-trade/config/projecttype/selectStyles",List.class)
                .getBody();
    }
    /**
     * 获取数据字典表的下拉列表
     *
     * @return
     */
    @Override
    public List<ParamNameVO> getParamNameList(String nameClass){
        List<ParamName> paramNameS = restTemplate.getForEntity("http://AM-CONFIG/am-config/accountconfig/getParamNameList/"+nameClass, List.class)
                .getBody();
        List<ParamNameVO> paramNameVOS = CommonUtils.convertBeanList(paramNameS,ParamNameVO.class);
        return paramNameVOS;
    }
    /**
     * 汇直投项目类型维护插入
     *
     * @param record
     */
    @Override
    public  void insertRecord(BorrowProjectTypeRequest record){
         restTemplate.postForEntity("http://AM-TRADE/am-trade/config/projecttype/insertRecord",record,null)
                .getBody();
    }
    /**
     * 汇直投项目类型维护插入
     *
     * @param record
     */
    @Override
    public  void updateRecord(BorrowProjectTypeRequest record){
        restTemplate.postForEntity("http://AM-TRADE/am-trade/config/projecttype/updateRecord",record,null)
                .getBody();
    }
    /**
     *  汇直投项目类型维护删除
     * @param adminRequest
     */
    @Override
    public BorrowProjectTypeResponse deleteProjectType( BorrowProjectTypeRequest adminRequest){
        return  restTemplate.postForEntity("http://AM-TRADE/am-trade/config/projecttype/deleteProjectType",adminRequest,BorrowProjectTypeResponse.class)
                .getBody();
    }
    /**
     * 检查项目名称唯一性
     * @param borrowCd
     * @return
     */
    @Override
    public int borrowCdIsExists(String borrowCd){
        return  restTemplate.postForEntity("http://AM-TRADE/am-trade/config/projecttype/borrowCdIsExists",borrowCd,int.class)
                .getBody();
    }
}
