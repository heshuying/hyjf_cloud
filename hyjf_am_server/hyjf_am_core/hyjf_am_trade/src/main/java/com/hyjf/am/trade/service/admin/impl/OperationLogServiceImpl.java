package com.hyjf.am.trade.service.admin.impl;

import com.hyjf.am.resquest.admin.AdminOperationLogRequest;
import com.hyjf.am.trade.dao.mapper.auto.HjhAssetTypeMapper;
import com.hyjf.am.trade.dao.mapper.customize.admin.OperationLogCustomizeMapper;
import com.hyjf.am.trade.dao.model.auto.HjhAssetType;
import com.hyjf.am.trade.dao.model.auto.HjhAssetTypeExample;
import com.hyjf.am.trade.dao.model.auto.HjhInstConfig;
import com.hyjf.am.trade.service.admin.OperationLogService;
import com.hyjf.am.vo.admin.FeerateModifyLogVO;
import com.hyjf.am.vo.admin.HjhAssetTypeVO;
import com.hyjf.common.util.CommonUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author by xiehuili on 2018/7/17.
 */
@Service
public class OperationLogServiceImpl implements OperationLogService {

    @Autowired
    private HjhAssetTypeMapper hjhAssetTypeMapper;
    @Autowired
    private OperationLogCustomizeMapper operationLogCustomizeMapper;

    /**
     * 产品类型
     * @return
     */
    @Override
    public List<HjhAssetTypeVO> getHjhAssetType(){
        // TODO Auto-generated method stub
        HjhAssetTypeExample hjhAssetTypeExample =new HjhAssetTypeExample();
        List<HjhAssetType> hjhAssetTypes=hjhAssetTypeMapper.selectByExample(hjhAssetTypeExample);
        List<HjhAssetTypeVO> list= CommonUtils.convertBeanList(hjhAssetTypes,HjhAssetTypeVO.class);
        return list;
    }
    /**
     * 查询 资产来源 instCode 和 assetType的值
     * @return
     */
    @Override
    public List<FeerateModifyLogVO> selectInstAndAssertType(AdminOperationLogRequest adminRequest){
        List<FeerateModifyLogVO> list = new ArrayList<FeerateModifyLogVO>();
        List<HjhInstConfig> instList= operationLogCustomizeMapper.selectInstCodeList(adminRequest);
        List<HjhAssetType> assertTypeList= operationLogCustomizeMapper.selectAssertTypeList(adminRequest);
        int size =0;
       if(instList.size()>0||assertTypeList.size()>0){
           if(instList.size()>=assertTypeList.size()){
               size=instList.size();
           }else {
               size=assertTypeList.size();
           }
           for(int i=0; i<size;i++){
               FeerateModifyLogVO vo= new FeerateModifyLogVO();
               if(!CollectionUtils.isEmpty(instList)){
                   vo.setInstName(instList.get(i).getInstName());
                   vo.setInstCode(instList.get(i).getInstCode());
               }
               if(!CollectionUtils.isEmpty(assertTypeList)){
                   if(vo.getInstCode().equals(assertTypeList.get(i).getInstCode())){
                       vo.setAssetTypeName(assertTypeList.get(i).getAssetTypeName());
                       vo.setAssetType(assertTypeList.get(i).getAssetType());
                   }
               }
               list.add(vo);
               vo=null;
           }
       }
         return list;
    }
}