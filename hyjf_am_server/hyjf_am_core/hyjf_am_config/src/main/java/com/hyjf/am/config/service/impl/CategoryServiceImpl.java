package com.hyjf.am.config.service.impl;

import com.hyjf.am.config.dao.mapper.auto.CategoryMapper;
import com.hyjf.am.config.dao.mapper.auto.ContentHelpMapper;
import com.hyjf.am.config.dao.mapper.customize.HelpCustomizeMapper;
import com.hyjf.am.config.dao.model.auto.Category;
import com.hyjf.am.config.dao.model.auto.CategoryExample;
import com.hyjf.am.config.dao.model.auto.ContentHelp;
import com.hyjf.am.config.dao.model.auto.ContentHelpExample;
import com.hyjf.am.config.dao.model.customize.HelpCategoryCustomize;
import com.hyjf.am.config.service.CategoryService;
import com.hyjf.am.resquest.admin.CategoryBeanRequest;
import com.hyjf.am.resquest.admin.ContentHelpBeanRequest;
import com.hyjf.am.vo.admin.CategoryVO;
import com.hyjf.am.vo.admin.ContentHelpVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.GetDate;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: walter.limeng
 * @Date: 2018/7/20 16:37
 * @Description: CategoryServiceImpl
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    private static final Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);
    @Resource
    private CategoryMapper categoryMapper;
    @Resource
    protected ContentHelpMapper contentHelpMapper;
    @Resource
    protected HelpCustomizeMapper helpCustomizeMapper;

    @Override
    public List<Category> searchParentCategorys(CategoryBeanRequest categoryBeanRequest, boolean showHide) {
        CategoryExample example = new CategoryExample();
        CategoryExample.Criteria criteria = example.createCriteria();
        criteria.andLevelEqualTo(0);
        criteria.andGroupEqualTo("help");
        if (!showHide) {
            criteria.andHideEqualTo(0);
        }
        List<Category> parentCategorys = categoryMapper.selectByExample(example);
        return  parentCategorys;
    }

    @Override
    public List<Category> searchChildCategorys(CategoryBeanRequest categoryBeanRequest, boolean showHide) {
        CategoryExample example = new CategoryExample();
        CategoryExample.Criteria criteria = example.createCriteria();
        if (categoryBeanRequest.getPid() != null) {
            criteria.andPidEqualTo(categoryBeanRequest.getPid());
            criteria.andLevelEqualTo(1);
            criteria.andGroupEqualTo("help");
            if (!showHide) {
                criteria.andHideEqualTo(0);
            }
            List<Category> childCategorys = categoryMapper.selectByExample(example);
            return childCategorys;
        }
        return null;
    }

    @Override
    public Integer getCategoryCount(CategoryBeanRequest categoryBeanRequest) {
        // 第二步:获取列表
        // 拼装查询条件
        CategoryExample exam = dealParam(categoryBeanRequest);
        return categoryMapper.countByExample(exam);
    }

    @Override
    public List<Category> getCategoryList(CategoryBeanRequest categoryBeanRequest) {
        // 拼装查询条件
        CategoryExample exam = dealParam(categoryBeanRequest);
        exam.setLimitStart((categoryBeanRequest.getCurrPage()-1) * categoryBeanRequest.getPageSize());
        exam.setLimitEnd((categoryBeanRequest.getCurrPage()) * categoryBeanRequest.getPageSize());
        return categoryMapper.selectByExample(exam);
    }

    @Override
    public Integer getConNum(Integer pcateId, Integer cateId) {
        ContentHelpExample con = new ContentHelpExample();
        ContentHelpExample.Criteria conCriteria = con.createCriteria();
        if(null != pcateId){
            conCriteria.andPcateIdEqualTo(pcateId);
        }
        if(null != cateId){
            conCriteria.andCateIdEqualTo(cateId);
        }

        return contentHelpMapper.countByExample(con);
    }

    @Override
    public Category infoTypeAction(Integer id) {
        return categoryMapper.selectByPrimaryKey(id);
    }

    @Override
    public Integer insertCategory(CategoryVO categoryVO) {
        return categoryMapper.insertSelective(CommonUtils.convertBean(categoryVO,Category.class));
    }

    @Override
    public Integer updateAction(CategoryVO categoryVO) {
        return categoryMapper.updateByPrimaryKey(CommonUtils.convertBean(categoryVO,Category.class));
    }

    @Override
    public Integer getCategoryTotal(CategoryVO categoryVO) {
        CategoryExample example = new CategoryExample();
        CategoryExample.Criteria criteria = example.createCriteria();
        criteria.andPidEqualTo(categoryVO.getId());
        criteria.andLevelEqualTo(1);
        int childSize = categoryMapper.countByExample(example);
        return childSize;
    }

    @Override
    public List<ContentHelpVO> getListByPcateIdAndcateId(Integer pid, Integer cid) {
        ContentHelpExample example = new ContentHelpExample();
        ContentHelpExample.Criteria criteria = example.createCriteria();
        if(null != pid){
            criteria.andPcateIdEqualTo(pid);
        }
        if(null != cid){
            criteria.andCateIdEqualTo(cid);
        }
        return CommonUtils.convertBeanList(contentHelpMapper.selectByExample(example),ContentHelpVO.class);
    }

    @Override
    public Integer delContentHelp(Integer id) {
        return contentHelpMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Integer delCategory(Integer id) {
        return categoryMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Integer updateContentHelp(ContentHelpVO contentHelpVO) {
        return contentHelpMapper.updateByPrimaryKeySelective(CommonUtils.convertBean(contentHelpVO,ContentHelp.class));
    }

    @Override
    public Integer getContentHelpCount(ContentHelpBeanRequest contentHelpBeanRequest) {
        ContentHelpExample con = dealContentHelpParam(contentHelpBeanRequest);
        return contentHelpMapper.countByExample(con);
    }

    @Override
    public List<ContentHelp> getContentHelpList(ContentHelpBeanRequest contentHelpBeanRequest) {
        ContentHelpExample con = dealContentHelpParam(contentHelpBeanRequest);
        con.setLimitStart((contentHelpBeanRequest.getCurrPage()-1) * contentHelpBeanRequest.getPageSize());
        con.setLimitEnd((contentHelpBeanRequest.getCurrPage()) * contentHelpBeanRequest.getPageSize());
        return contentHelpMapper.selectByExample(con);
    }

    @Override
    public List<Integer> getConType() {
        List<Integer> ids = new ArrayList<>();
        List <HelpCategoryCustomize> list = helpCustomizeMapper.selectCategory("help");
        for(HelpCategoryCustomize customize:list){
            ids.add(customize.getId());
        }
        return ids;
    }

    @Override
    public List<CategoryVO> getCategoryListByParam(Integer isHide) {
        CategoryExample cate = new CategoryExample();
        if(null != isHide){
            cate.createCriteria().andHideEqualTo(isHide);
        }
        List<Category> cates = this.categoryMapper.selectByExample(cate);
        return CommonUtils.convertBeanList(cates,CategoryVO.class);
    }

    @Override
    public ContentHelp queryContentById(Integer id) {
        return contentHelpMapper.selectByPrimaryKey(id);
    }

    @Override
    public Integer addContentHelp(ContentHelp con) {
        return contentHelpMapper.insertSelective(con);
    }

    @Override
    public Integer updateHelpAction(ContentHelp con) {
        return contentHelpMapper.updateByPrimaryKey(con);
    }

    /**
     * @Author walter.limeng
     * @user walter.limeng
     * @Description  处理问题列表参数
     * @Date 11:53 2018/7/24
     * @Param
     * @return
     */
    public ContentHelpExample dealContentHelpParam(ContentHelpBeanRequest contentHelpBeanRequest){
        ContentHelpExample con = new ContentHelpExample();
        ContentHelpExample.Criteria conCriteria = con.createCriteria();
        if (contentHelpBeanRequest.getPcateId() != null) {
            conCriteria.andPcateIdEqualTo(contentHelpBeanRequest.getPcateId());
        }
        if (contentHelpBeanRequest.getCateId() != null) {
            conCriteria.andCateIdEqualTo(contentHelpBeanRequest.getCateId());
        }
        if (StringUtils.isNotEmpty(contentHelpBeanRequest.getTitle())) {
            conCriteria.andTitleLike(contentHelpBeanRequest.getTitle());
        }

        if (contentHelpBeanRequest.getStatus() != null) {
            conCriteria.andStatusEqualTo(contentHelpBeanRequest.getStatus());
        }

        if (StringUtils.isNotEmpty(contentHelpBeanRequest.getPost_time_begin())) {
            conCriteria.andCreateTimeGreaterThanOrEqualTo(GetDate.str2Date(contentHelpBeanRequest.getPost_time_begin(),GetDate.datetimeFormat));
        }

        if (StringUtils.isNotEmpty(contentHelpBeanRequest.getPost_time_end())) {
            conCriteria.andCreateTimeLessThanOrEqualTo(GetDate.str2Date(contentHelpBeanRequest.getPost_time_end(),GetDate.datetimeFormat));
        }
        return con;
    }

    /**
     * @Author walter.limeng
     * @user walter.limeng
     * @Description  处理封装查询条件
     * @Date 19:09 2018/7/23
     * @Param categoryBeanRequest
     * @return
     */
    public CategoryExample dealParam(CategoryBeanRequest categoryBeanRequest){
        CategoryExample exam = new CategoryExample();
        CategoryExample.Criteria crit = exam.createCriteria();
        if (categoryBeanRequest.getPid() != null) {
            if (categoryBeanRequest.getId() != null) {
                // 当子ID不为空时,表示是父子联查
                crit.andPidEqualTo(categoryBeanRequest.getPid());
            } else {
                // 当子ID为空时,表示查父和所有的子
                // 获取子
                List<Category> childCategorys = searchChildCategorys(categoryBeanRequest,true);
                List<Integer> ids = new ArrayList<Integer>();
                ids.add(categoryBeanRequest.getPid());
                if (childCategorys != null && childCategorys.size() > 0) {
                    for (Category cat : childCategorys) {
                        ids.add(cat.getId());
                    }
                }
                crit.andIdIn(ids);
            }
        }
        if (categoryBeanRequest.getId() != null) {
            crit.andIdEqualTo(categoryBeanRequest.getId());
        }

        if (categoryBeanRequest.getHide() != null) {
            crit.andHideEqualTo(categoryBeanRequest.getHide());
        }
        {
            crit.andGroupEqualTo("help");
        }
        return exam;
    }
}
