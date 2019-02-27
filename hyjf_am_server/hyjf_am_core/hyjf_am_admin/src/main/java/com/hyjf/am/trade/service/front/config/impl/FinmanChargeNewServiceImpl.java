package com.hyjf.am.trade.service.front.config.impl;

import com.hyjf.am.resquest.admin.FinmanChargeNewRequest;
import com.hyjf.am.trade.dao.mapper.auto.BorrowFinmanNewChargeMapper;
import com.hyjf.am.trade.dao.mapper.auto.BorrowProjectTypeMapper;
import com.hyjf.am.trade.dao.mapper.customize.FinmanNewChargeCustomizeMapper;
import com.hyjf.am.trade.dao.model.auto.BorrowFinmanNewCharge;
import com.hyjf.am.trade.dao.model.auto.BorrowFinmanNewChargeExample;
import com.hyjf.am.trade.dao.model.auto.BorrowProjectType;
import com.hyjf.am.trade.dao.model.auto.BorrowProjectTypeExample;
import com.hyjf.am.trade.service.front.config.FinmanChargeNewService;
import com.hyjf.am.vo.trade.borrow.BorrowFinmanNewChargeVO;
import com.hyjf.common.util.GetDate;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author xiehuili on 2018/8/14.
 */
@Service
public class FinmanChargeNewServiceImpl implements FinmanChargeNewService {

    @Autowired
    private FinmanNewChargeCustomizeMapper finmanNewChargeCustomizeMapper;
    @Autowired
    private BorrowFinmanNewChargeMapper borrowFinmanNewChargeMapper;
    @Autowired
    private BorrowProjectTypeMapper borrowProjectTypeMapper;

    @Override
    public  int countRecordTotal(FinmanChargeNewRequest adminRequest){
        return finmanNewChargeCustomizeMapper.countRecordTotal(adminRequest);
    }
    @Override
    public List<BorrowFinmanNewChargeVO> getRecordList(FinmanChargeNewRequest adminRequest, int limitStart, int limitEnd){
        if(limitStart > -1){
            adminRequest.setLimitStart(limitStart);
            adminRequest.setLimitEnd(limitEnd);
        }
        return finmanNewChargeCustomizeMapper.getRecordList(adminRequest);
    }

    /**
     * 查询费率配置
     *  @author xiehuili
     * @return
     */
    @Override
    public BorrowFinmanNewCharge getRecordInfo(String manChargeCd){
        return borrowFinmanNewChargeMapper.selectByPrimaryKey(manChargeCd);
    }
    /**
     * 添加费率配置
     *  @author xiehuili
     * @return
     */
    @Override
    public int insertFinmanChargeNew(FinmanChargeNewRequest adminRequest){
        BorrowFinmanNewCharge record = new BorrowFinmanNewCharge();
        BeanUtils.copyProperties(adminRequest, record);
        int nowTime = GetDate.getNowTime10();
        record.setManChargeCd(UUID.randomUUID().toString());
        if ("endday".equals(adminRequest.getManChargeTimeType())) {
            // 天标
            record.setManChargeType("天标");
        } else if ("month".equals(adminRequest.getManChargeTimeType())) {
            // 月标
            record.setManChargeType("月标");
        }
        record.setCreateTime(new Date());
        return borrowFinmanNewChargeMapper.insertSelective(record);
    }
    /**
     * 修改费率配置
     *  @author xiehuili
     * @return
     */
    @Override
    public int updateFinmanChargeNew(FinmanChargeNewRequest adminRequest){
        if(StringUtils.isBlank(adminRequest.getManChargeCd())){
            //ManChargeCd为必填字段
            return 0;
        }
        BorrowFinmanNewCharge record = new BorrowFinmanNewCharge();
        BeanUtils.copyProperties(adminRequest, record);
        // 更新时间
        record.setUpdateTime(new Date());
        return borrowFinmanNewChargeMapper.updateByPrimaryKeySelective(record);
    }
    /**
     * 删除费率配置
     *  @author xiehuili
     * @return
     */
    @Override
    public int deleteFinmanChargeNew(FinmanChargeNewRequest adminRequest){
        if(StringUtils.isBlank(adminRequest.getManChargeCd())){
            //ManChargeCd为必填字段
            return 0;
        }
        return borrowFinmanNewChargeMapper.deleteByPrimaryKey(adminRequest.getManChargeCd());
    }
    /**
     *
     * 根据表的类型,期数,项目类型检索管理费件数
     * @author xiehuili
     * @param adminRequest
     * @return
     */
    @Override
    public int countRecordByProjectType(FinmanChargeNewRequest adminRequest){
        BorrowFinmanNewChargeExample example = new BorrowFinmanNewChargeExample();
        BorrowFinmanNewChargeExample.Criteria cra = example.createCriteria();
        cra.andManChargeTimeTypeEqualTo(adminRequest.getManChargeTimeType());
        cra.andManChargeTimeEqualTo(adminRequest.getManChargeTime());
        cra.andAssetTypeEqualTo(adminRequest.getAssetType());
        cra.andInstCodeEqualTo(adminRequest.getInstCode());
        return borrowFinmanNewChargeMapper.countByExample(example);
    }

    /**
     *
     * 根据borrowClass查询borrowClass是否存在
     * @author xiehuili
     * @return
     */
    @Override
    public String getBorrowClass(String borrowClass){
        String borrowClassValue="";
        BorrowProjectTypeExample example=new BorrowProjectTypeExample();
        example.createCriteria().andBorrowClassEqualTo(borrowClass);
        List<BorrowProjectType>  list = borrowProjectTypeMapper.selectByExample(example);
        if(!CollectionUtils.isEmpty(list)){
            borrowClassValue=list.get(0).getBorrowClass();
        }
        return borrowClassValue;
    }
}