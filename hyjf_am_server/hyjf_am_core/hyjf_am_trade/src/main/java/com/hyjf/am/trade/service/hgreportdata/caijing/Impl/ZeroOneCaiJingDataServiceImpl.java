package com.hyjf.am.trade.service.hgreportdata.caijing.Impl;

import com.hyjf.am.trade.dao.mapper.customize.hgreportdata.caijing.ZeroOneCustomizeMapper;
import com.hyjf.am.trade.dao.model.customize.ZeroOneCaiJingCustomize;
import com.hyjf.am.trade.service.hgreportdata.caijing.ZeroOneCaiJingDataService;
import com.hyjf.am.vo.hgreportdata.caijing.ZeroOneDataVO;
import com.hyjf.common.cache.CacheUtil;
import com.hyjf.common.util.CustomUtil;
import com.hyjf.common.util.calculate.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
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



    /**
     * 查询指定日期的出借记录
     * @param map
     * @return
     */
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
                zeroOneDataVO.setId(CustomUtil.nidSign(vo.getBorrowNid()));
                //标的链接
                if("tender".equals(vo.getFlag())){
                    zeroOneDataVO.setLink("borrow/borrowList");
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
                zeroOneDataVO.setBid_source(clientMap.getOrDefault(vo.getClient(), null));

                dataVOList.add(zeroOneDataVO);
            }

        }
        return dataVOList;
    }

    /**
     * 查找提前还款记录
     * @param map
     * @return
     */
    @Override
    public List<ZeroOneDataVO> queryAdvancedRepaySub(Map<String,Object> map) {
        List<ZeroOneCaiJingCustomize> list = customizeMapper.queryAdvancedRepaySub(map);
        List<ZeroOneDataVO> dataVOList = new ArrayList<>(list.size());
        if(list.size() > 0) {
            for (ZeroOneCaiJingCustomize vo : list) {
                if(StringUtils.isBlank(vo.getFreezeOrderId())){
                    continue;
                }
                ZeroOneDataVO zeroOneDataVO = new ZeroOneDataVO();

                zeroOneDataVO.setRepay_id(vo.getFreezeOrderId());
                zeroOneDataVO.setId(CustomUtil.nidSign(vo.getBorrowNid()));
                zeroOneDataVO.setAdvanced_time(vo.getRepayTime());
                //实际借款天数
                zeroOneDataVO.setActual_period(vo.getSpentday());
                zeroOneDataVO.setAdvanced_amount(vo.getAccount());
                dataVOList.add(zeroOneDataVO);
            }
        }
        return dataVOList;
    }
}
