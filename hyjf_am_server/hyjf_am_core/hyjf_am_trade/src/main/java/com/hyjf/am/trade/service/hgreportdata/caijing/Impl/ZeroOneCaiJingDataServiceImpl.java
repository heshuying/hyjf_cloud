package com.hyjf.am.trade.service.hgreportdata.caijing.Impl;

import com.hyjf.am.trade.dao.mapper.customize.hgreportdata.caijing.ZeroOneCustomizeMapper;
import com.hyjf.am.trade.dao.model.customize.ZeroOneCaiJingCustomize;
import com.hyjf.am.trade.service.hgreportdata.caijing.ZeroOneCaiJingDataService;
import com.hyjf.am.vo.hgreportdata.caijing.ZeroOneDataVO;
import com.hyjf.common.cache.CacheUtil;
import com.hyjf.common.util.calculate.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: yinhui
 * @Date: 2019/6/3 14:36
 * @Version 1.0
 */
@Service
public class ZeroOneCaiJingDataServiceImpl implements ZeroOneCaiJingDataService {

    @Autowired
    private ZeroOneCustomizeMapper customizeMapper;

    @Override
    public List<ZeroOneDataVO> queryInvestRecordSub(Map<String,Object> map){
        List<ZeroOneCaiJingCustomize> list = customizeMapper.queryInvestRecordSub(map);
        List<ZeroOneDataVO> dataVOList = new ArrayList<>(list.size());
        if(list.size() > 0){
            Map<String, String> clientMap = CacheUtil.getParamNameMap("CLIENT");
            for(ZeroOneCaiJingCustomize vo : list){
                ZeroOneDataVO zeroOneDataVO = new ZeroOneDataVO();

                zeroOneDataVO.setUserIds(vo.getUserIds());
                //表的主键
                zeroOneDataVO.setInvest_id(vo.getNid());
                //标编号
                zeroOneDataVO.setId(vo.getBorrowNid());
                //标的链接
                if("tender".equals(vo.getFlag())){
                    zeroOneDataVO.setLink("/borrow/borrowDetail?borrowNid="+vo.getBorrowNid());
                }else{
                    zeroOneDataVO.setLink("borrow/creditList");
                }

                //出借方式
                zeroOneDataVO.setType("手动");
                //出借金额
                zeroOneDataVO.setMoney(vo.getAccount());
                //有效金额
                zeroOneDataVO.setAccount(vo.getAccount());
                //出借状态
                zeroOneDataVO.setStatus("成功");
                //出借时间
                zeroOneDataVO.setAdd_time(vo.getCreateTime());
                //出借来源
                if("PC".equals(clientMap.getOrDefault(vo.getClient(), null))){
                    zeroOneDataVO.setBid_source("PC端");
                }else{
                    zeroOneDataVO.setBid_source("移动端");
                }

                dataVOList.add(zeroOneDataVO);
            }

        }
        return dataVOList;
    }
}
