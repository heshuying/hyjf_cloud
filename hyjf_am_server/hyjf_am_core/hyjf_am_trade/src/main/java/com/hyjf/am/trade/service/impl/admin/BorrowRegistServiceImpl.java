/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.impl.admin;

import com.hyjf.am.resquest.admin.BorrowRegistListRequest;
import com.hyjf.am.resquest.trade.BorrowRegistRequest;
import com.hyjf.am.trade.dao.mapper.auto.BorrowMapper;
import com.hyjf.am.trade.dao.mapper.auto.BorrowProjectTypeMapper;
import com.hyjf.am.trade.dao.mapper.auto.BorrowStyleMapper;
import com.hyjf.am.trade.dao.mapper.auto.StzhWhiteListMapper;
import com.hyjf.am.trade.dao.mapper.customize.admin.BorrowRegistCustomizeMapper;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.dao.model.customize.trade.BorrowRegistCustomize;
import com.hyjf.am.trade.service.admin.BorrowRegistService;
import com.hyjf.am.vo.trade.borrow.BorrowVO;
import com.hyjf.common.cache.CacheUtil;
import com.hyjf.common.util.CustomConstants;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

/**
 * @author wangjun
 * @version BorrowRegistServiceImpl, v0.1 2018/6/29 16:54
 */
@Service
public class BorrowRegistServiceImpl implements BorrowRegistService {
    @Autowired
    private BorrowProjectTypeMapper borrowProjectTypeMapper;

    @Autowired
    private BorrowStyleMapper borrowStyleMapper;

    @Autowired
    private BorrowRegistCustomizeMapper borrowRegistCustomizeMapper;

    @Autowired
    private StzhWhiteListMapper stzhWhiteListMapper;

    @Autowired
    private BorrowMapper borrowMapper;

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

    /**
     * 统计总额
     * @param borrowRegistListRequest
     * @return
     */
    @Override
    public String sumBorrowRegistAccount(BorrowRegistListRequest borrowRegistListRequest){
        return borrowRegistCustomizeMapper.sumBorrowRegistAccount(borrowRegistListRequest);
    }

    @Override
    public StzhWhiteList selectStzfWhiteList(String instCode, String entrustedAccountId) {
        StzhWhiteListExample example = new StzhWhiteListExample();
        StzhWhiteListExample.Criteria crt = example.createCriteria();
        crt.andStAccountidEqualTo(entrustedAccountId);
        crt.andInstcodeEqualTo(instCode);
        crt.andDelFlagEqualTo(0);
        crt.andStateEqualTo(1);
        List<StzhWhiteList> list = this.stzhWhiteListMapper.selectByExample(example);
        if(list.size() > 0){
            return list.get(0);
        }else{
            return null;
        }
    }

    /**
     * 更新相应的标的信息
     * @param request
     * @return
     */
    @Override
    public int updateBorrowRegistFromList(BorrowRegistRequest request){
        BorrowVO borrowVO = request.getBorrowVO();
        BorrowExample example = new BorrowExample();
        example.createCriteria().andIdEqualTo(borrowVO.getId()).andStatusEqualTo(borrowVO.getStatus()).andRegistStatusEqualTo(borrowVO.getRegistStatus());
        Borrow borrow = new Borrow();
        BeanUtils.copyProperties(borrowVO, borrow);
        return borrowMapper.updateByExampleSelective(borrow, example);
    }

    /**
     * 更新标的信息(受托支付备案)
     * @param request
     * @return
     */
    @Override
    public int updateEntrustedBorrowRegist(BorrowRegistRequest request){
        BorrowVO borrowVO = request.getBorrowVO();
        BorrowExample example = new BorrowExample();
        example.createCriteria().andIdEqualTo(borrowVO.getId());
        Borrow borrow = new Borrow();
        BeanUtils.copyProperties(borrowVO, borrow);
        return borrowMapper.updateByExampleSelective(borrow, example);
    }
}
