package com.hyjf.am.user.service.admin.promotion.impl;

import com.hyjf.am.resquest.admin.ChannelReconciliationRequest;
import com.hyjf.am.resquest.admin.ChannelRequest;
import com.hyjf.am.user.dao.model.auto.*;
import com.hyjf.am.user.service.admin.promotion.UtmService;
import com.hyjf.am.user.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.admin.UtmVO;
import com.hyjf.am.vo.admin.promotion.channel.ChannelCustomizeVO;
import com.hyjf.am.vo.admin.promotion.channel.ChannelReconciliationVO;
import com.hyjf.am.vo.admin.promotion.channel.UtmChannelVO;
import com.hyjf.am.vo.user.UtmPlatVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author walter.limeng
 * @version UtmController, v0.1 2018/7/02 11:17
 * 渠道管理接口
 */
@Service
public class UtmServiceImpl extends BaseServiceImpl implements UtmService {

    @Override
    public List<UtmVO> getByPageList(Map<String, Object> map) {
        List<UtmVO> list = utmRegCustomizeMapper.getByPageList(map);
        return list;
    }

    @Override
    public Integer getCountByParam(Map<String, Object> map) {
        return utmRegCustomizeMapper.getCountByParam(map);
    }

    @Override
    public List<UtmPlatVO> getUtmPlat(String sourceId) {
        Map<String, Object> map = new HashMap<>();
        map.put("sourceId",sourceId);
        map.put("delFlag",CustomConstants.FLAG_NORMAL);
        map.put("sourceType",0);
        return utmRegCustomizeMapper.getUtmPlat(map);
    }

    @Override
    public List<UtmPlatVO> getMyUtmPlat() {
        Map<String, Object> map = new HashMap<>();
        map.put("delFlag",CustomConstants.FLAG_NORMAL);
        map.put("sourceType",0);
        return utmRegCustomizeMapper.getUtmPlat(map);
    }

    @Override
    public UtmChannelVO getUtmByUtmId(String utmId) {
        return utmRegCustomizeMapper.getUtmByUtmId(utmId);
    }

    @Override
    public Utm insertOrUpdateUtm(ChannelCustomizeVO channelCustomizeVO) {
        Utm utm = new Utm();
        if(StringUtils.isNotBlank(channelCustomizeVO.getUtmId())){
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
        utmPlatVO = CommonUtils.convertBean(utmPlat,UtmPlatVO.class);
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
        if(null != utmPlatVO.getId() && !"".equals(utmPlatVO.getId())){
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
        return utmRegCustomizeMapper.getAccessNumber(sourceId, dayStart, dayEnd);
    }

    @Override
    public Integer getRegistNumber(Integer sourceId) {
        String dayStart = GetDate.getDayStart(GetDate.date2Str(GetDate.date_sdf));
        String dayEnd = GetDate.getDayEnd(GetDate.date2Str(GetDate.date_sdf));
        return utmRegCustomizeMapper.getRegistNumber(sourceId, dayStart, dayEnd);
    }

    @Override
    public Integer getOpenAccountNumber(Integer sourceId, String type) {
        String dayStart = GetDate.getDayStart(GetDate.date2Str(GetDate.date_sdf));
        String dayEnd = GetDate.getDayEnd(GetDate.date2Str(GetDate.date_sdf));
        return utmRegCustomizeMapper.getOpenAccountNumber(sourceId, dayStart, dayEnd, type);
    }

    @Override
    public List<ChannelReconciliationVO> selectPcChannelReconciliationRecord(ChannelReconciliationRequest request) {
        if (request.getInvestStartTime() != null) {
            Date investStartTime = request.getInvestStartTime();
            investStartTime = GetDate.getSomeDayStart(investStartTime);
            request.setInvestStartTime(investStartTime);
        }
        if (request.getInvestEndTime() != null) {
            Date investEndTime = request.getInvestEndTime();
            investEndTime = GetDate.getSomeDayEnd(investEndTime);
            request.setInvestEndTime(investEndTime);
        }
        if (request.getRegistStartTime() != null) {
            Date registStartTime = request.getRegistStartTime();
            registStartTime = GetDate.getSomeDayStart(registStartTime);
            request.setRegistStartTime(registStartTime);
        }
        if (request.getRegistEndTime() != null) {
            Date registEndTime = request.getRegistEndTime();
            registEndTime = GetDate.getSomeDayEnd(registEndTime);
            request.setRegistEndTime(registEndTime);
        }

        return utmRegCustomizeMapper.selectPcChannelReconciliationRecord(request);
    }

    @Override
    public List<ChannelReconciliationVO> selectPcChannelReconciliationRecordHjh(ChannelReconciliationRequest request) {
        if (request.getInvestStartTime() != null) {
            Date investStartTime = request.getInvestStartTime();
            investStartTime = GetDate.getSomeDayStart(investStartTime);
            request.setInvestStartTime(investStartTime);
        }
        if (request.getInvestEndTime() != null) {
            Date investEndTime = request.getInvestEndTime();
            investEndTime = GetDate.getSomeDayEnd(investEndTime);
            request.setInvestEndTime(investEndTime);
        }
        if (request.getRegistStartTime() != null) {
            Date registStartTime = request.getRegistStartTime();
            registStartTime = GetDate.getSomeDayStart(registStartTime);
            request.setRegistStartTime(registStartTime);
        }
        if (request.getRegistEndTime() != null) {
            Date registEndTime = request.getRegistEndTime();
            registEndTime = GetDate.getSomeDayEnd(registEndTime);
            request.setRegistEndTime(registEndTime);
        }

        return utmRegCustomizeMapper.selectPcChannelReconciliationRecordHjh(request);
    }

    @Override
    public int selectPcChannelReconciliationCount(ChannelReconciliationRequest request) {
        if (request.getInvestStartTime() != null) {
            Date investStartTime = request.getInvestStartTime();
            investStartTime = GetDate.getSomeDayStart(investStartTime);
            request.setInvestStartTime(investStartTime);
        }
        if (request.getInvestEndTime() != null) {
            Date investEndTime = request.getInvestEndTime();
            investEndTime = GetDate.getSomeDayEnd(investEndTime);
            request.setInvestEndTime(investEndTime);
        }
        if (request.getRegistStartTime() != null) {
            Date registStartTime = request.getRegistStartTime();
            registStartTime = GetDate.getSomeDayStart(registStartTime);
            request.setRegistStartTime(registStartTime);
        }
        if (request.getRegistEndTime() != null) {
            Date registEndTime = request.getRegistEndTime();
            registEndTime = GetDate.getSomeDayEnd(registEndTime);
            request.setRegistEndTime(registEndTime);
        }
        Integer result = utmRegCustomizeMapper.selectPcChannelReconciliationRecordCount(request);
        return result==null?0:result;
    }

    @Override
    public int selectPcChannelReconciliationHjhCount(ChannelReconciliationRequest request) {
        if (request.getInvestStartTime() != null) {
            Date investStartTime = request.getInvestStartTime();
            investStartTime = GetDate.getSomeDayStart(investStartTime);
            request.setInvestStartTime(investStartTime);
        }
        if (request.getInvestEndTime() != null) {
            Date investEndTime = request.getInvestEndTime();
            investEndTime = GetDate.getSomeDayEnd(investEndTime);
            request.setInvestEndTime(investEndTime);
        }
        if (request.getRegistStartTime() != null) {
            Date registStartTime = request.getRegistStartTime();
            registStartTime = GetDate.getSomeDayStart(registStartTime);
            request.setRegistStartTime(registStartTime);
        }
        if (request.getRegistEndTime() != null) {
            Date registEndTime = request.getRegistEndTime();
            registEndTime = GetDate.getSomeDayEnd(registEndTime);
            request.setRegistEndTime(registEndTime);
        }
        Integer result = utmRegCustomizeMapper.selectPcChannelReconciliationRecordHjhCount(request);
        return result==null?0:result;
    }

    @Override
    public int selectAppChannelReconciliationCount(ChannelReconciliationRequest request) {
        if (request.getInvestStartTime() != null) {
            Date investStartTime = request.getInvestStartTime();
            investStartTime = GetDate.getSomeDayStart(investStartTime);
            request.setInvestStartTime(investStartTime);
        }
        if (request.getInvestEndTime() != null) {
            Date investEndTime = request.getInvestEndTime();
            investEndTime = GetDate.getSomeDayEnd(investEndTime);
            request.setInvestEndTime(investEndTime);
        }
        if (request.getRegistStartTime() != null) {
            Date registStartTime = request.getRegistStartTime();
            registStartTime = GetDate.getSomeDayStart(registStartTime);
            request.setRegistStartTime(registStartTime);
        }
        if (request.getRegistEndTime() != null) {
            Date registEndTime = request.getRegistEndTime();
            registEndTime = GetDate.getSomeDayEnd(registEndTime);
            request.setRegistEndTime(registEndTime);
        }
        Integer result = utmRegCustomizeMapper.selectAppChannelReconciliationRecordCount(request);
        return result == null? 0:result;
    }

    @Override
    public int selectAppChannelReconciliationHjhCount(ChannelReconciliationRequest request) {
        if (request.getInvestEndTime() != null) {
            Date investEndTime = request.getInvestEndTime();
            investEndTime = GetDate.getSomeDayEnd(investEndTime);
            request.setInvestEndTime(investEndTime);
        }
        if (request.getRegistStartTime() != null) {
            Date registStartTime = request.getRegistStartTime();
            registStartTime = GetDate.getSomeDayStart(registStartTime);
            request.setRegistStartTime(registStartTime);
        }
        if (request.getRegistEndTime() != null) {
            Date registEndTime = request.getRegistEndTime();
            registEndTime = GetDate.getSomeDayEnd(registEndTime);
            request.setRegistEndTime(registEndTime);
        }
        Integer result = utmRegCustomizeMapper.selectAppChannelReconciliationRecordHjhCount(request);
        return result==null?0:result;
    }

    @Override
    public List<ChannelReconciliationVO> selectAppChannelReconciliationRecord(ChannelReconciliationRequest request) {
        if (request.getCurrPage() > 0 && request.getPageSize() > 0) {
            request.setLimitStart((request.getCurrPage() - 1) * request.getPageSize());
            request.setLimitEnd(request.getPageSize());
        }
        if (request.getInvestStartTime() != null) {
            Date investStartTime = request.getInvestStartTime();
            investStartTime = GetDate.getSomeDayStart(investStartTime);
            request.setInvestStartTime(investStartTime);
        }
        if (request.getInvestEndTime() != null) {
            Date investEndTime = request.getInvestEndTime();
            investEndTime = GetDate.getSomeDayEnd(investEndTime);
            request.setInvestEndTime(investEndTime);
        }
        if (request.getRegistStartTime() != null) {
            Date registStartTime = request.getRegistStartTime();
            registStartTime = GetDate.getSomeDayStart(registStartTime);
            request.setRegistStartTime(registStartTime);
        }
        if (request.getRegistEndTime() != null) {
            Date registEndTime = request.getRegistEndTime();
            registEndTime = GetDate.getSomeDayEnd(registEndTime);
            request.setRegistEndTime(registEndTime);
        }
        return utmRegCustomizeMapper.selectAppChannelReconciliationRecord(request);
    }

    @Override
    public List<ChannelReconciliationVO> selectAppChannelReconciliationRecordHjh(ChannelReconciliationRequest request) {
        if (request.getCurrPage() > 0 && request.getPageSize() > 0) {
            request.setLimitStart((request.getCurrPage() - 1) * request.getPageSize());
            request.setLimitEnd(request.getPageSize());
        }
        if (request.getInvestStartTime() != null) {
            Date investStartTime = request.getInvestStartTime();
            investStartTime = GetDate.getSomeDayStart(investStartTime);
            request.setInvestStartTime(investStartTime);
        }
        if (request.getInvestEndTime() != null) {
            Date investEndTime = request.getInvestEndTime();
            investEndTime = GetDate.getSomeDayEnd(investEndTime);
            request.setInvestEndTime(investEndTime);
        }
        if (request.getRegistStartTime() != null) {
            Date registStartTime = request.getRegistStartTime();
            registStartTime = GetDate.getSomeDayStart(registStartTime);
            request.setRegistStartTime(registStartTime);
        }
        if (request.getRegistEndTime() != null) {
            Date registEndTime = request.getRegistEndTime();
            registEndTime = GetDate.getSomeDayEnd(registEndTime);
            request.setRegistEndTime(registEndTime);
        }
        return utmRegCustomizeMapper.selectAppChannelReconciliationRecordHjh(request);
    }

    @Override
    public List<UtmPlatVO> getUtmPlatByParam(Map<String, Object> map) {
        UtmPlatExample utmPlat = new UtmPlatExample();
        List<UtmPlat> list = utmPlatMapper.selectByExample(utmPlat);
        if(list == null){
            list = new ArrayList<UtmPlat>();
        }
        return CommonUtils.convertBeanList(list, UtmPlatVO.class);
    }

    @Override
    public void insertUtmList(ChannelRequest request) {
        List<ChannelCustomizeVO> list = request.getList();
        for (ChannelCustomizeVO vo : list) {
            Utm utm = changeUtm(new Utm(), vo);
            utm.setStatus(0);
            utmMapper.insertSelective(utm);
        }

    }

    /**
     *渠道管理检查编号唯一性
     * @author cwyang
     * @param sourceId
     * @return
     */
    @Override
    public Integer sourceIdIsExists(Integer sourceId) {
        UtmPlatExample example = new UtmPlatExample();
        UtmPlatExample.Criteria cra = example.createCriteria();
        cra.andSourceIdEqualTo(sourceId);
        List<UtmPlat> utmPlatList = this.utmPlatMapper.selectByExample(example);
        if (utmPlatList != null && utmPlatList.size() > 0) {
            return 1;
        }
        return 0;
    }

    @Override
    public Integer getBySourceIdAndTerm(String sourceId, String utmTerm,String utmId) {
        UtmExample example = new UtmExample();
        UtmExample.Criteria cra = example.createCriteria();
        cra.andSourceIdEqualTo(Integer.parseInt(sourceId));
        cra.andUtmTermEqualTo(utmTerm);
        Utm utm = null;
        List<Utm> utmList = this.utmMapper.selectByExample(example);
        if (utmList != null && utmList.size() > 0) {
            utm = utmList.get(0);
            if (utm != null) {
                if(StringUtils.isNotEmpty(utmId)){
                    if(utm.getUtmId() != Integer.parseInt(utmId)){
                        return 1;
                    }
                }else{
                    return 1;
                }
            }
        }
        return 0;
    }

    @Override
    public List<Integer> searchUserIdList(int sourceType) {
        return utmRegCustomizeMapper.searchUserIdList(sourceType);
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
        //0：启用；1：禁用
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
        if(StringUtils.isNotBlank(channelCustomizeVO.getStatus())){
            record.setStatus(Integer.valueOf(channelCustomizeVO.getStatus()));
        }

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
