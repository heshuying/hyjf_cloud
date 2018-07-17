package com.hyjf.am.trade.service.impl.admin;

import com.hyjf.am.trade.dao.mapper.auto.HjhAssetTypeMapper;
import com.hyjf.am.trade.dao.model.auto.HjhAssetType;
import com.hyjf.am.trade.dao.model.auto.HjhAssetTypeExample;
import com.hyjf.am.trade.service.admin.OperationLogService;
import com.hyjf.am.vo.admin.HjhAssetTypeVO;
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

    /**
     * 产品类型
     * @return
     */
    @Override
    public List<HjhAssetTypeVO> getHjhAssetType(){
        // TODO Auto-generated method stub
        HjhAssetTypeExample hjhAssetTypeExample =new HjhAssetTypeExample();
        List<HjhAssetType> hjhAssetTypes=hjhAssetTypeMapper.selectByExample(hjhAssetTypeExample);
        List<HjhAssetTypeVO> list=new ArrayList<HjhAssetTypeVO>();
        BeanUtils.copyProperties(hjhAssetTypes,list);
        return list;
    }
}
