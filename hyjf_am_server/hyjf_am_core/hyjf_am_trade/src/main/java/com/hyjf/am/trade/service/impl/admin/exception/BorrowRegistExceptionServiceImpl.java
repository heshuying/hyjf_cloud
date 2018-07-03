/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.impl.admin.exception;

import com.hyjf.am.resquest.admin.BorrowRegistListRequest;
import com.hyjf.am.trade.dao.mapper.auto.BorrowProjectTypeMapper;
import com.hyjf.am.trade.dao.mapper.auto.BorrowStyleMapper;
import com.hyjf.am.trade.dao.mapper.customize.admin.BorrowRegistCustomizeMapper;
import com.hyjf.am.trade.dao.model.auto.BorrowProjectType;
import com.hyjf.am.trade.dao.model.auto.BorrowProjectTypeExample;
import com.hyjf.am.trade.dao.model.auto.BorrowStyle;
import com.hyjf.am.trade.dao.model.auto.BorrowStyleExample;
import com.hyjf.am.trade.dao.model.customize.trade.BorrowRegistCustomize;
import com.hyjf.am.trade.service.admin.exception.BorrowRegistExceptionService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.common.cache.CacheUtil;
import com.hyjf.common.util.CustomConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

/**
 * @author: sunpeikai
 * @version: BorrowRegistExceptionServiceImpl, v0.1 2018/7/3 15:07
 */
@Service
public class BorrowRegistExceptionServiceImpl extends BaseServiceImpl implements BorrowRegistExceptionService {
    @Autowired
    private BorrowProjectTypeMapper borrowProjectTypeMapper;

    @Autowired
    private BorrowStyleMapper borrowStyleMapper;

    @Autowired
    private BorrowRegistCustomizeMapper borrowRegistCustomizeMapper;

    /**
     * 获取项目类型
     * @return
     */
    @Override
    public List<BorrowProjectType> selectBorrowProjectList() {
        BorrowProjectTypeExample example = new BorrowProjectTypeExample();
        BorrowProjectTypeExample.Criteria cra = example.createCriteria();
        cra.andStatusEqualTo(Integer.valueOf(CustomConstants.FLAG_NORMAL));
        // 不查询融通宝相关
        cra.andBorrowNameNotEqualTo(CustomConstants.RTB);

        return borrowProjectTypeMapper.selectByExample(example);
    }

    /**
     * 获取还款方式
     * @return
     */
    @Override
    public List<BorrowStyle> selectBorrowStyleList(){
        BorrowStyleExample example = new BorrowStyleExample();
        BorrowStyleExample.Criteria cra = example.createCriteria();
        cra.andStatusEqualTo(Integer.valueOf(CustomConstants.FLAG_NORMAL));

        return borrowStyleMapper.selectByExample(example);
    }

    /**
     * 获取标的备案列表count
     * @param borrowRegistListRequest
     * @return
     */
    @Override
    public Integer getRegistCount(BorrowRegistListRequest borrowRegistListRequest){
        return borrowRegistCustomizeMapper.getRegistCount(borrowRegistListRequest);
    }

    /**
     * 获取标的备案列表
     * @param borrowRegistListRequest
     * @return
     */
    @Override
    public List<BorrowRegistCustomize> selectBorrowRegistList(BorrowRegistListRequest borrowRegistListRequest){
        List<BorrowRegistCustomize> list = borrowRegistCustomizeMapper.selectBorrowRegistList(borrowRegistListRequest);
        if(!CollectionUtils.isEmpty(list)){
            //处理标的备案状态
            Map<String, String> map = CacheUtil.getParamNameMap("REGIST_STATUS");
            if(!CollectionUtils.isEmpty(map)){
                for(BorrowRegistCustomize borrowRegistCustomize : list){
                    borrowRegistCustomize.setRegistStatusName(map.getOrDefault(borrowRegistCustomize.getRegistStatus(),null));
                }
            }
        }
        return borrowRegistCustomizeMapper.selectBorrowRegistList(borrowRegistListRequest);
    }

}
