package com.hyjf.admin.service.impl.content;

import com.hyjf.admin.client.AmConfigClient;
import com.hyjf.admin.service.content.CategoryService;
import com.hyjf.am.response.admin.CategoryResponse;
import com.hyjf.am.resquest.admin.CategoryBeanRequest;
import com.hyjf.am.resquest.admin.ContentHelpBeanRequest;
import com.hyjf.am.vo.admin.CategoryVO;
import com.hyjf.am.vo.admin.ContentHelpVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Auther: walter.limeng
 * @Date: 2018/7/20 15:14
 * @Description: CategoryServiceImpl
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    private static final Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);
    @Resource
    private AmConfigClient amConfigClient;

    @Override
    public CategoryResponse getCategoryPage(CategoryBeanRequest categoryBeanRequest) {
        return amConfigClient.getCategoryPage(categoryBeanRequest);
    }

    @Override
    public CategoryResponse changeSubTypeAction(CategoryBeanRequest categoryBeanRequest) {
        return amConfigClient.changeSubTypeAction(categoryBeanRequest);
    }

    @Override
    public CategoryResponse infoTypeAction(Integer id) {
        return amConfigClient.infoTypeAction(id);
    }

    @Override
    public CategoryResponse infoSubTypeAction(CategoryBeanRequest categoryBeanRequest) {
        return amConfigClient.infoSubTypeAction(categoryBeanRequest);
    }

    @Override
    public Integer insertCategory(CategoryVO categoryVO) {
        return amConfigClient.insertCategory(categoryVO);
    }

    @Override
    public Integer updateAction(CategoryVO categoryVO) {
        return amConfigClient.updateAction(categoryVO);
    }

    @Override
    public CategoryResponse getCategoryCount(CategoryVO record) {
        return amConfigClient.getCategoryCount(record);
    }

    @Override
    public Integer getCountByPcateIdAndcateId(Integer pid, Integer cid) {
        return amConfigClient.getCountByPcateIdAndcateId(pid,cid);
    }

    @Override
    public List<ContentHelpVO> getListByPcateIdAndcateId(CategoryVO categoryVO) {
        return amConfigClient.getListByPcateIdAndcateId(categoryVO);
    }

    @Override
    public Integer delContentHelp(Integer id) {
        return amConfigClient.delContentHelp(id);
    }

    @Override
    public Integer delCategory(Integer id) {
        return amConfigClient.delCategory(id);
    }

    @Override
    public Integer updateContentHelp(ContentHelpVO contentHelpVO) {
        return amConfigClient.updateContentHelp(contentHelpVO);
    }

    @Override
    public CategoryResponse getHelpPage(ContentHelpBeanRequest contentHelpBeanRequest) {
        return amConfigClient.getHelpPage(contentHelpBeanRequest);
    }

    @Override
    public CategoryResponse getHelpInfo(ContentHelpBeanRequest contentHelpBeanRequest) {
        return amConfigClient.getHelpInfo(contentHelpBeanRequest);
    }

    @Override
    public CategoryResponse insertHelpInfo(ContentHelpBeanRequest contentHelpBeanRequest) {
        return amConfigClient.insertHelpInfo(contentHelpBeanRequest);
    }

    @Override
    public CategoryResponse updateHelpAction(ContentHelpBeanRequest contentHelpBeanRequest) {
        return amConfigClient.updateHelpAction(contentHelpBeanRequest);
    }

    @Override
    public Integer chanContentHelp(Integer contentId, Integer status, Integer zhiChiStatus) {
        return amConfigClient.chanContentHelp(contentId,status,zhiChiStatus);
    }

    @Override
    public CategoryResponse getOftenInitPage(ContentHelpBeanRequest contentHelpBeanRequest) {
        return amConfigClient.getOftenInitPage(contentHelpBeanRequest);
    }
    @Override
    public CategoryResponse getZhiChiInit(ContentHelpBeanRequest contentHelpBeanRequest){
        return amConfigClient.getZhiChiInit(contentHelpBeanRequest);

    }

}
