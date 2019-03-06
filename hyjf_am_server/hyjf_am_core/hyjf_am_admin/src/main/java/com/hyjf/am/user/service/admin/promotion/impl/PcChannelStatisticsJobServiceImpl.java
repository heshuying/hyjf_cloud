package com.hyjf.am.user.service.admin.promotion.impl;

import com.hyjf.am.resquest.admin.ChannelReconciliationRequest;
import com.hyjf.am.user.dao.model.auto.*;
import com.hyjf.am.user.service.admin.promotion.PcChannelStatisticsJobService;
import com.hyjf.am.user.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.admin.UtmVO;
import com.hyjf.am.vo.admin.promotion.channel.ChannelCustomizeVO;
import com.hyjf.am.vo.admin.promotion.channel.ChannelReconciliationVO;
import com.hyjf.am.vo.admin.promotion.channel.UtmChannelVO;
import com.hyjf.am.vo.user.UtmPlatVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author walter.limeng
 * @version UtmController, v0.1 2018/7/02 11:17
 * 渠道管理接口
 */
@Service
public class PcChannelStatisticsJobServiceImpl extends BaseServiceImpl implements PcChannelStatisticsJobService {

    private static final Logger logger = LoggerFactory.getLogger(PcChannelStatisticsJobServiceImpl.class);
    @Override
    public List<UtmVO> getByPageList(Map<String, Object> map) {
        List<UtmVO> list = pcChannelStatisticsJobCustomizeMapper.getByPageList(map);
        return list;
    }

    @Override
    public Integer getCountByParam(Map<String, Object> map) {
        return pcChannelStatisticsJobCustomizeMapper.getCountByParam(map);
    }

    @Override
    public List<UtmPlatVO> getUtmPlat(String sourceId) {
        Map<String, Object> map = new HashMap<>();
        map.put("sourceId",sourceId);
        map.put("delFlag",CustomConstants.FLAG_NORMAL);
        map.put("sourceType",0);
        return pcChannelStatisticsJobCustomizeMapper.getUtmPlat(map);
    }

    @Override
    public UtmChannelVO getUtmByUtmId(String utmId) {
        return pcChannelStatisticsJobCustomizeMapper.getUtmByUtmId(utmId);
    }

    @Override
    public Utm insertOrUpdateUtm(ChannelCustomizeVO channelCustomizeVO) {
        Utm utm = new Utm();
        if(StringUtils.isNotEmpty(channelCustomizeVO.getUtmId())){
            //执行更新操作
            utm = changeUtm(utm,channelCustomizeVO);
            utm.setUtmId(Integer.parseInt(channelCustomizeVO.getUtmId()));
            utmMapper.updateByPrimaryKeySelective(utm);
        }else{
            //执行修改操作
            utm = changeUtm(utm,channelCustomizeVO);
            utmMapper.insertSelective(utm);
        }
        return utm;
    }

    @Override
    public Utm deleteUtm(ChannelCustomizeVO channelCustomizeVO) {
        utmMapper.deleteByPrimaryKey(Integer.parseInt(channelCustomizeVO.getUtmId()));
        return new Utm();
    }

    @Override
    public UtmPlatVO getUtmPlatById(Integer id) {
        UtmPlat utmPlat = utmPlatMapper.selectByPrimaryKey(id);
        UtmPlatVO utmPlatVO = new UtmPlatVO();
        utmPlatVO = (UtmPlatVO)convertBean2Bean(utmPlat,utmPlatVO);
        return utmPlatVO;
    }

    @Override
    public Integer sourceNameIsExists(String sourceName, Integer sourceId) {
        UtmPlatExample example = new UtmPlatExample();
        UtmPlatExample.Criteria cra = example.createCriteria();
        cra.andSourceNameEqualTo(sourceName);
        if (StringUtils.isNotEmpty(sourceId+"")) {
            cra.andSourceIdNotEqualTo(sourceId);
        }
        List<UtmPlat> utmPlatList = this.utmPlatMapper.selectByExample(example);
        if (utmPlatList != null && utmPlatList.size() > 0) {
            return 1;
        }
        return 0;
    }

    @Override
    public UtmPlat insertOrUpdateUtmPlat(UtmPlatVO utmPlatVO) {
        UtmPlat utmPlat = new UtmPlat();
        utmPlat = convertUtmPlat(utmPlat,utmPlatVO);
        if(StringUtils.isNotEmpty(utmPlatVO.getId()+"")){
            utmPlat.setId(Integer.valueOf(utmPlatVO.getId()));
            utmPlatMapper.updateByPrimaryKeySelective(utmPlat);
        }else{
            utmPlatMapper.insertSelective(utmPlat);
        }
        return utmPlat;
    }

    @Override
    public UtmPlat deleteUtmPlat(UtmPlatVO utmPlatVO) {
        utmPlatMapper.deleteByPrimaryKey(utmPlatVO.getId());
        return new UtmPlat();
    }

    @Override
    public Integer getAccessNumber(Integer sourceId) {
        String dayStart = GetDate.getDayStart(GetDate.date2Str(GetDate.date_sdf));
        String dayEnd = GetDate.getDayEnd(GetDate.date2Str(GetDate.date_sdf));
        return pcChannelStatisticsJobCustomizeMapper.getAccessNumber(sourceId, dayStart, dayEnd);
    }

    @Override
    public Integer getRegistNumber(Integer sourceId) {
        String dayStart = GetDate.getDayStart(GetDate.date2Str(GetDate.date_sdf));
        String dayEnd = GetDate.getDayEnd(GetDate.date2Str(GetDate.date_sdf));
        return pcChannelStatisticsJobCustomizeMapper.getRegistNumber(sourceId, dayStart, dayEnd);
    }

    @Override
    public Integer getOpenAccountNumber(Integer sourceId, String type) {
        String dayStart = GetDate.getDayStart(GetDate.date2Str(GetDate.date_sdf));
        String dayEnd = GetDate.getDayEnd(GetDate.date2Str(GetDate.date_sdf));
        return pcChannelStatisticsJobCustomizeMapper.getOpenAccountNumber(sourceId, dayStart, dayEnd, type);
    }

    @Override
    public Integer getTenderNumber(Integer sourceId, String type) {
        String dayStart = GetDate.getDayStart(GetDate.date2Str(GetDate.date_sdf));
        String dayEnd = GetDate.getDayEnd(GetDate.date2Str(GetDate.date_sdf));
        return pcChannelStatisticsJobCustomizeMapper.getTenderNumber(sourceId, dayStart, dayEnd, type);
    }

    @Override
    public BigDecimal getCumulativeRecharge(Integer sourceId, String type) {
        String dayStart = GetDate.getDayStart(GetDate.date2Str(GetDate.date_sdf));
        String dayEnd = GetDate.getDayEnd(GetDate.date2Str(GetDate.date_sdf));
        return pcChannelStatisticsJobCustomizeMapper.getCumulativeRecharge(sourceId, dayStart, dayEnd, type);
    }
    @Override
    public BigDecimal getHztTenderPrice(Integer sourceId, String type) {
        String dayStart = GetDate.getDayStart(GetDate.date2Str(GetDate.date_sdf));
        String dayEnd = GetDate.getDayEnd(GetDate.date2Str(GetDate.date_sdf));
        return pcChannelStatisticsJobCustomizeMapper.getHztTenderPrice(sourceId, dayStart, dayEnd, type);
    }
    @Override
    public BigDecimal getHxfTenderPrice(Integer sourceId, String type) {
        String dayStart = GetDate.getDayStart(GetDate.date2Str(GetDate.date_sdf));
        String dayEnd = GetDate.getDayEnd(GetDate.date2Str(GetDate.date_sdf));
        return pcChannelStatisticsJobCustomizeMapper.getHxfTenderPrice(sourceId, dayStart, dayEnd, type);
    }
    @Override
    public BigDecimal getHtlTenderPrice(Integer sourceId, String type) {
        String dayStart = GetDate.getDayStart(GetDate.date2Str(GetDate.date_sdf));
        String dayEnd = GetDate.getDayEnd(GetDate.date2Str(GetDate.date_sdf));
        return pcChannelStatisticsJobCustomizeMapper.getHtlTenderPrice(sourceId, dayStart, dayEnd, type);
    }

    @Override
    public BigDecimal getHtjTenderPrice(Integer sourceId, String type) {
        String dayStart = GetDate.getDayStart(GetDate.date2Str(GetDate.date_sdf));
        String dayEnd = GetDate.getDayEnd(GetDate.date2Str(GetDate.date_sdf));
        return pcChannelStatisticsJobCustomizeMapper.getHtjTenderPrice(sourceId, dayStart, dayEnd, type);
    }
    @Override
    public BigDecimal getRtbTenderPrice(Integer sourceId, String type) {
        String dayStart = GetDate.getDayStart(GetDate.date2Str(GetDate.date_sdf));
        String dayEnd = GetDate.getDayEnd(GetDate.date2Str(GetDate.date_sdf));
        return pcChannelStatisticsJobCustomizeMapper.getRtbTenderPrice(sourceId, dayStart, dayEnd, type);
    }


    @Override
    public BigDecimal getHzrTenderPrice(Integer sourceId, String type) {
        String dayStart = GetDate.getDayStart(GetDate.date2Str(GetDate.date_sdf));
        String dayEnd = GetDate.getDayEnd(GetDate.date2Str(GetDate.date_sdf));
        return pcChannelStatisticsJobCustomizeMapper.getHzrTenderPrice(sourceId, dayStart, dayEnd, type);
    }

    @Override
    public List<ChannelReconciliationVO> selectPcChannelReconciliationRecord(ChannelReconciliationRequest request) {
        return pcChannelStatisticsJobCustomizeMapper.selectPcChannelReconciliationRecord(request);
    }

    @Override
    public List<ChannelReconciliationVO> selectPcChannelReconciliationRecordHjh(ChannelReconciliationRequest request) {
        return pcChannelStatisticsJobCustomizeMapper.selectPcChannelReconciliationRecordHjh(request);
    }

    @Override
    public int selectPcChannelReconciliationCount(ChannelReconciliationRequest request) {
        request.setCurrPage(0);
        return pcChannelStatisticsJobCustomizeMapper.selectPcChannelReconciliationRecordCount(request);
    }

    @Override
    public int selectPcChannelReconciliationHjhCount(ChannelReconciliationRequest request) {
        request.setCurrPage(0);
        return pcChannelStatisticsJobCustomizeMapper.selectPcChannelReconciliationRecordHjhCount(request);
    }

    @Override
    public int selectAppChannelReconciliationCount(ChannelReconciliationRequest request) {
        request.setCurrPage(0);
        return pcChannelStatisticsJobCustomizeMapper.selectAppChannelReconciliationRecordCount(request);
    }

    @Override
    public int selectAppChannelReconciliationHjhCount(ChannelReconciliationRequest request) {
        request.setCurrPage(0);
        return pcChannelStatisticsJobCustomizeMapper.selectAppChannelReconciliationRecordHjhCount(request);
    }

    @Override
    public List<ChannelReconciliationVO> selectAppChannelReconciliationRecord(ChannelReconciliationRequest request) {
        return pcChannelStatisticsJobCustomizeMapper.selectAppChannelReconciliationRecord(request);
    }

    @Override
    public List<ChannelReconciliationVO> selectAppChannelReconciliationRecordHjh(ChannelReconciliationRequest request) {
        return pcChannelStatisticsJobCustomizeMapper.selectAppChannelReconciliationRecordHjh(request);
    }

    /**
     * @Author walter.limeng
     * @Description  转换utmplat对象
     * @Date 11:36 2018/7/16
     * @Param record
     * @Param utmBean
     * @return
     */
    public UtmPlat convertUtmPlat(UtmPlat record,UtmPlatVO utmBean){
        String nowDate = GetDate.getServerDateTime(6, new Date());
        record.setSourceId(Integer.valueOf(utmBean.getSourceId()));
        record.setSourceName(utmBean.getSourceName());
        record.setDelFlag(utmBean.getDelFlag());
        record.setSourceType(utmBean.getSourceType());
        record.setAttornFlag(utmBean.getAttornFlag());
        if (StringUtils.isNotEmpty(utmBean.getRemark())) {
            record.setRemark(utmBean.getRemark());
        } else {
            record.setRemark(StringUtils.EMPTY);
        }

        record.setUpdateTime(new Date());
        return record;
    }

    /**
     * @Author walter.limeng
     * @Description  把JavaBean的from的值自动set给to，省略了自己从from中get然后再set给to
     * @Date 11:08 2018/7/16
     * @Param from 赋值来源对象
     * @Param to 需要赋值对象
     * @return
     */
    public static Object convertBean2Bean(Object from, Object to) {
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(to.getClass());
            PropertyDescriptor[] ps = beanInfo.getPropertyDescriptors();

            for (PropertyDescriptor p : ps) {
                Method getMethod = p.getReadMethod();
                Method setMethod = p.getWriteMethod();
                if (getMethod != null && setMethod != null) {
                    try {
                        Object result = getMethod.invoke(from);
                        setMethod.invoke(to, result);
                    } catch (Exception e) {
                        // 如果from没有此属性的get方法，跳过
                        continue;
                    }
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return to;
    }

    /**
     * @Author walter.limeng
     * @Description  转换对象
     * @Date 10:14 2018/7/16
     * @Param
     * @return
     */
    public Utm changeUtm(Utm record,ChannelCustomizeVO channelCustomizeVO){
        int nowDate = GetDate.getNowTime10();
        record.setSourceId(Integer.valueOf(channelCustomizeVO.getSourceId()));
        if (StringUtils.isNotEmpty(channelCustomizeVO.getSourceId())) {
            List<UtmPlatVO> utmPlatList = this.getUtmPlat(channelCustomizeVO.getSourceId());
            if (utmPlatList != null && utmPlatList.size() > 0) {
                UtmPlatVO utmPlat = utmPlatList.get(0);
                record.setUtmSource(utmPlat.getSourceName());
            }
        }
        record.setUtmMedium(channelCustomizeVO.getUtmMedium());
        record.setUtmTerm(channelCustomizeVO.getUtmTerm());
        record.setUtmContent(channelCustomizeVO.getUtmContent());
        record.setUtmCampaign(channelCustomizeVO.getUtmCampaign());

        if (StringUtils.isNotEmpty(channelCustomizeVO.getUtmReferrer())) {
            User user = this.getUser(channelCustomizeVO.getUtmReferrer(), StringUtils.EMPTY);
            record.setUtmReferrer(user.getUserId());
        }
        if(StringUtils.isNotEmpty(channelCustomizeVO.getLinkAddress())){
            record.setLinkAddress(channelCustomizeVO.getLinkAddress());
        }else {
            record.setLinkAddress("www.hyjf.com");
        }
        record.setStatus(Integer.valueOf(channelCustomizeVO.getStatus()));

        if (StringUtils.isNotEmpty(channelCustomizeVO.getRemark())) {
            record.setRemark(channelCustomizeVO.getRemark());
        } else {
            record.setRemark(StringUtils.EMPTY);
        }

        record.setCreateTime(new Date());

        return record;
    }

    /**
     * 获取用户
     *
     * @param utmReferrer
     * @return
     */
    public User getUser(String utmReferrer, String userId) {
        UserExample example = new UserExample();
        UserExample.Criteria cra = example.createCriteria();
        if (StringUtils.isNotEmpty(utmReferrer)) {
            cra.andUsernameEqualTo(utmReferrer);
        }
        if (StringUtils.isNotEmpty(userId)) {
            cra.andUserIdEqualTo(Integer.valueOf(userId));
        }
        List<User> userList = this.userMapper.selectByExample(example);
        if (userList != null && userList.size() > 0) {
            return userList.get(0);
        }

        return new User();
    }
}
