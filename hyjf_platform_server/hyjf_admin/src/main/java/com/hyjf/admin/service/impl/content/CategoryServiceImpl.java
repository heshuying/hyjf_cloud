package com.hyjf.admin.service.impl.content;

import com.hyjf.admin.client.ContentCategoryClient;
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
    private ContentCategoryClient contentCategoryClient;

    @Override
    public CategoryResponse getCategoryPage(CategoryBeanRequest categoryBeanRequest) {
        return contentCategoryClient.getCategoryPage(categoryBeanRequest);
    }

    @Override
    public CategoryResponse changeSubTypeAction(CategoryBeanRequest categoryBeanRequest) {
        return contentCategoryClient.changeSubTypeAction(categoryBeanRequest);
    }

    @Override
    public CategoryResponse infoTypeAction(Integer id) {
        return contentCategoryClient.infoTypeAction(id);
    }

    @Override
    public CategoryResponse infoSubTypeAction(CategoryBeanRequest categoryBeanRequest) {
        return contentCategoryClient.infoSubTypeAction(categoryBeanRequest);
    }

    @Override
    public Integer insertCategory(CategoryVO categoryVO) {
        return contentCategoryClient.insertCategory(categoryVO);
    }

    @Override
    public Integer updateAction(CategoryVO categoryVO) {
        return contentCategoryClient.updateAction(categoryVO);
    }

    @Override
    public CategoryResponse getCategoryCount(CategoryVO record) {
        return contentCategoryClient.getCategoryCount(record);
    }

    @Override
    public Integer getCountByPcateIdAndcateId(Integer pid, Integer cid) {
        return contentCategoryClient.getCountByPcateIdAndcateId(pid,cid);
    }

    @Override
    public List<ContentHelpVO> getListByPcateIdAndcateId(Integer pid, Integer cid) {
        return contentCategoryClient.getListByPcateIdAndcateId(pid,cid);
    }

    @Override
    public Integer delContentHelp(Integer id) {
        return contentCategoryClient.delContentHelp(id);
    }

    @Override
    public Integer delCategory(Integer id) {
        return contentCategoryClient.delCategory(id);
    }

    @Override
    public Integer updateContentHelp(ContentHelpVO contentHelpVO) {
        return contentCategoryClient.updateContentHelp(contentHelpVO);
    }

    @Override
    public CategoryResponse getHelpPage(ContentHelpBeanRequest contentHelpBeanRequest) {
        return contentCategoryClient.getHelpPage(contentHelpBeanRequest);
    }

    @Override
    public CategoryResponse getHelpInfo(ContentHelpBeanRequest contentHelpBeanRequest) {
        return contentCategoryClient.getHelpInfo(contentHelpBeanRequest);
    }

    @Override
    public CategoryResponse insertHelpInfo(ContentHelpBeanRequest contentHelpBeanRequest) {
        return contentCategoryClient.insertHelpInfo(contentHelpBeanRequest);
    }

    @Override
    public CategoryResponse updateHelpAction(ContentHelpBeanRequest contentHelpBeanRequest) {
        return contentCategoryClient.updateHelpAction(contentHelpBeanRequest);
    }

    @Override
    public Integer chanContentHelp(Integer contentId, Integer status, Integer zhiChiStatus) {
        return contentCategoryClient.chanContentHelp(contentId,status,zhiChiStatus);
    }

    @Override
    public CategoryResponse getOftenInitPage(ContentHelpBeanRequest contentHelpBeanRequest) {
        return contentCategoryClient.getOftenInitPage(contentHelpBeanRequest);
    }
}
