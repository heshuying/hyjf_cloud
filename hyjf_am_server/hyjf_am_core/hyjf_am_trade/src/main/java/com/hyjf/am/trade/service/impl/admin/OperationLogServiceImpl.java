package com.hyjf.am.trade.service.impl.admin;

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
        List<FeerateModifyLogVO> list = null;
        List<HjhInstConfig> instList= operationLogCustomizeMapper.selectInstCodeList(adminRequest);
        List<HjhAssetType> assertTypeList= operationLogCustomizeMapper.selectAssertTypeList(adminRequest);
        int size =0;
       if(instList.size()>0||assertTypeList.size()>0){
           if(instList.size()>=assertTypeList.size()){
               size=instList.size();
           }else {
               size=assertTypeList.size();
           }
           list =new ArrayList<FeerateModifyLogVO>(size);
           for(int i=0; i<size;i++){
               if(!CollectionUtils.isEmpty(instList)){
                   String inst=instList.get(i).getInstName();
                   String dd =instList.get(i).getInstCode();
                   list.get(i).setInstName(instList.get(i).getInstName());
                   list.get(i).setInstCode(instList.get(i).getInstCode());
               }
               if(!CollectionUtils.isEmpty(assertTypeList)){
                   if(list.get(i).getInstCode().equals(assertTypeList.get(i).getInstCode())){
                       list.get(i).setAssetTypeName(assertTypeList.get(i).getAssetTypeName());
                       list.get(i).setAssetType(assertTypeList.get(i).getAssetType());
                   }
               }
           }
       }
         return list;
    }
}
