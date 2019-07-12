package com.hyjf.am.user.service.admin.promotion.impl;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.user.TemplateConfigResponse;
import com.hyjf.am.resquest.user.LandingManagerRequest;
import com.hyjf.am.user.dao.mapper.auto.TemplateDisposeMapper;
import com.hyjf.am.user.dao.model.auto.TemplateConfig;
import com.hyjf.am.user.dao.model.auto.TemplateConfigExample;
import com.hyjf.am.user.dao.model.auto.TemplateDisposeExample;
import com.hyjf.am.user.service.admin.promotion.LandingManagerService;
import com.hyjf.am.user.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.user.TemplateConfigVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author walter.nxl
 * @version LandingManagerServiceImpl, v0.1 2018/7/02 11:17
 */
@Service
public class LandingManagerServiceImpl extends BaseServiceImpl implements LandingManagerService {

    private static final Logger logger = LoggerFactory.getLogger(LandingManagerServiceImpl.class);
	@Autowired
	TemplateDisposeMapper templateDisposeMapper;
    /**
     * 根据查询条件查询着陆页列表
     *
     * @param request
     * @return
     */
    @Override
    public TemplateConfigResponse selectTempList(LandingManagerRequest request) {
        TemplateConfigResponse response = new TemplateConfigResponse();
        response.setMessage(Response.SUCCESS_MSG);
        response.setRtn(Response.SUCCESS);
        List<TemplateConfig> templateConfigList = new ArrayList<TemplateConfig>();
        List<TemplateConfigVO> templateConfigVOList = new ArrayList<TemplateConfigVO>();
        TemplateConfigExample example = new TemplateConfigExample();
        TemplateConfigExample.Criteria criteria = example.createCriteria();
        //状态
        if (request.getStatus() != null) {
            criteria.andStatusEqualTo(request.getStatus());
        }
        //模板名称
        if (StringUtils.isNotBlank(request.getTempName())) {
            criteria.andTempNameLike(request.getTempName() + "%");
        }
        //模板类型
        if (request.getTempType() != null) {
            criteria.andTempTypeEqualTo(1);
        }
        //着陆页title
        if (StringUtils.isNotBlank(request.getTempTitle())) {
            criteria.andTempTitleLike(request.getTempTitle() + "%");
        }
        //时间
        if (StringUtils.isNotBlank(request.getStartTime()) && StringUtils.isNotBlank(request.getEndTime())) {
            String strStartDate = request.getStartTime() + " 00:00:00";
            String strEndDate = request.getEndTime() + " 23:59:59";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                Date dateStart = sdf.parse(strStartDate);
                Date dateEnd = sdf.parse(strEndDate);
                criteria.andCreateTimeBetween(dateStart, dateEnd);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        int countList = templateConfigMapper.countByExample(example);
        Paginator paginator = new Paginator(request.getCurrPage(), countList, request.getPageSize());
        if (request.getPageSize() == 0) {
            paginator = new Paginator(request.getCurrPage(), countList);
        }

        int limitStart = paginator.getOffset();
        int limitEnd = paginator.getLimit();
        if (countList > 0) {
            example.setOrderByClause("`create_time` Desc");
            example.setLimitStart(limitStart);
            example.setLimitEnd(limitEnd);
            templateConfigList = templateConfigMapper.selectByExample(example);
            if (CollectionUtils.isEmpty(templateConfigList)) {
                response.setMessage(Response.FAIL_MSG);
                response.setRtn(Response.FAIL);
                return response;
            }
            templateConfigVOList = CommonUtils.convertBeanList(templateConfigList, TemplateConfigVO.class);
        }
        //
        response.setCount(countList);
        response.setResultList(templateConfigVOList);
        return response;
    }

    /**
     * 根据id查找着陆页配置
     *
     * @param intId
     * @return
     */
    @Override
    public TemplateConfig selectTemplateById(Integer intId) {
        TemplateConfigExample example = new TemplateConfigExample();
        TemplateConfigExample.Criteria criteria = example.createCriteria();
        TemplateConfig templateConfig = null;
        if (intId != null) {
            templateConfig = templateConfigMapper.selectByPrimaryKey(intId);
        }
        return templateConfig;
    }

    /**
     * 保存着陆页模板配置
     *
     * @param config
     * @return
     */
    @Override
    public int insertTemplate(TemplateConfig config) {
        int intsert = templateConfigMapper.insertSelective(config);
        if (intsert > 0) {
            logger.info("=============着陆页模板配置保存成功!=============");
        } else {
            logger.error("=============着陆页模板配置保存异常!!=============");
            throw new RuntimeException("着陆页模板配置保存异常!");
        }
        return intsert;
    }

    /**
     * 修改着陆页模板配置
     *
     * @param config
     * @return
     */
    @Override
    public int updateTemplate(TemplateConfig config) {
        int updateflg = templateConfigMapper.updateByPrimaryKeySelective(config);
        if (updateflg > 0) {
            logger.info("=============着陆页模板配置修改成功!=============");
        } else {
            logger.error("=============着陆页模板配置修改异常!!=============");
            throw new RuntimeException("着陆页模板配置修改异常!");
        }
        return updateflg;
    }

    /**
     * 删除着陆页配置
     * @param temId
     * @return
     */
    @Override
    public int deleteTemplate(int temId){
        templateConfigMapper.deleteByPrimaryKey(temId);
        return 1;
    }
    @Override
    public int deleteTemplateCount(int temId){
        TemplateDisposeExample example=new TemplateDisposeExample();
        example.or().andTempIdEqualTo(temId);
		return templateDisposeMapper.countByExample(example);
    }
    
}
