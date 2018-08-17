package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.AmConfigClient;
import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.client.BorrowProjectTypeClient;
import com.hyjf.admin.service.BorrowProjectTypeService;
import com.hyjf.am.response.trade.BorrowProjectTypeResponse;
import com.hyjf.am.resquest.trade.BorrowProjectTypeRequest;
import com.hyjf.am.vo.config.ParamNameVO;
import com.hyjf.am.vo.trade.borrow.BorrowProjectRepayVO;
import com.hyjf.am.vo.trade.borrow.BorrowProjectTypeVO;
import com.hyjf.am.vo.trade.borrow.BorrowStyleVO;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author by xiehuili on 2018/7/27.
 */
@Service
public class BorrowProjectTypeServiceImpl implements BorrowProjectTypeService {

    @Autowired
    private AmTradeClient amTradeClient;
    @Autowired
    private AmConfigClient amConfigClient;
    /**
     * 查询列表
     * @param adminRequest
     * @return
     */
    @Override
   public BorrowProjectTypeResponse selectProjectTypeList(BorrowProjectTypeRequest adminRequest){
        BorrowProjectTypeResponse  result=new BorrowProjectTypeResponse();
        List<ParamNameVO> paramNameVO =amConfigClient.selectProjectTypeParamList();
        if(!CollectionUtils.isEmpty(paramNameVO)){
            adminRequest.setParamNameVO(paramNameVO);
            result=amTradeClient.selectProjectTypeList(adminRequest);
        }
        return  result;
    }


    /**
     * 查询项目类型 详情
     * @param adminRequest
     * @return
     */
    @Override
    public BorrowProjectTypeResponse selectProjectTypeRecord(BorrowProjectTypeRequest adminRequest){
        return amTradeClient.selectProjectTypeRecord(adminRequest);
    }
    /**
     * 根据主键判断汇直投项目类型维护中数据是否存在
     * @return
     */
    @Override
    public boolean isExistsRecord(BorrowProjectTypeVO record){
        return amTradeClient.isExistsRecord(record);
    }

    /**
     * 获取单个汇直投项目类型维护
     * @return
     */
    @Override
    public BorrowProjectTypeVO getRecord(BorrowProjectTypeVO record){
        return amTradeClient.getRecord(record);
    }

    /**
     * 根据项目编号查询还款方式
     * @param str
     */
    @Override
    public List<BorrowProjectRepayVO> selectRepay(String str){
        return amTradeClient.selectRepay(str);
    }
    /**
     * 查询类型表
     */
    @Override
    public List<BorrowStyleVO> selectStyles(){
        return amTradeClient.selectStyles();
    }
    /**
     * 获取数据字典表的下拉列表
     *
     * @return
     */
    @Override
    public List<ParamNameVO> getParamNameList(String nameClass){
        return amConfigClient.getParamNameList(nameClass);
    }
    /**
     * 汇直投项目类型维护插入
     *
     * @param record
     */
    @Override
    public BorrowProjectTypeResponse insertRecord(BorrowProjectTypeRequest record){
        return amTradeClient.insertRecord(record);
    }
    /**
     * 汇直投项目类型维护修改
     *
     * @param record
     */
    @Override
    public void updateRecord(BorrowProjectTypeRequest record){
        amTradeClient.updateRecord(record);
    }
    /**
     *  汇直投项目类型维护删除
     * @param adminRequest
     */
    @Override
    public BorrowProjectTypeResponse deleteProjectType( BorrowProjectTypeRequest adminRequest){
        return amTradeClient.deleteProjectType(adminRequest);
    }
    /**
     * 检查项目名称唯一性
     * @param borrowCd
     * @return
     */
    @Override
    public int borrowCdIsExists(BorrowProjectTypeRequest borrowCd){
        return amTradeClient.borrowCdIsExists(borrowCd);
    }
}
