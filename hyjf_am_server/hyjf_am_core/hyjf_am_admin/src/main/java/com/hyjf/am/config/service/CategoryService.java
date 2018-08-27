package com.hyjf.am.config.service;

import java.util.List;

import com.hyjf.am.config.dao.model.auto.Category;
import com.hyjf.am.config.dao.model.auto.ContentHelp;
import com.hyjf.am.resquest.admin.CategoryBeanRequest;
import com.hyjf.am.resquest.admin.ContentHelpBeanRequest;
import com.hyjf.am.vo.admin.CategoryVO;
import com.hyjf.am.vo.admin.ContentHelpVO;

/**
 * @Auther: walter.limeng
 * @Date: 2018/7/20 16:37
 * @Description: CategoryService
 */
public interface CategoryService {
    /**
     * @Author walter.limeng
     * @Description  查询父级菜单
     * @Date 16:51 2018/7/20
     * @Param categoryBeanRequest
     * @Param showHide
     * @return
     */
    List<Category> searchParentCategorys(CategoryBeanRequest categoryBeanRequest, boolean showHide );

    /**
     * @Author walter.limeng
     * @user walter.limeng
     * @Description  查询子级菜单
     * @Date 18:56 2018/7/23
     * @Param categoryBeanRequest
     * @Param showHide
     * @return
     */
    List<Category> searchChildCategorys(CategoryBeanRequest categoryBeanRequest, boolean showHide);

    /**
     * @Author walter.limeng
     * @user walter.limeng
     * @Description  根据条件i查询总条数
     * @Date 18:59 2018/7/23
     * @Param categoryBeanRequest
     * @return
     */
    Integer getCategoryCount(CategoryBeanRequest categoryBeanRequest);

    /**
     * @Author walter.limeng
     * @user walter.limeng
     * @Description  分页获取数据
     * @Date 19:07 2018/7/23
     * @Param categoryBeanRequest
     * @return
     */
    List<Category> getCategoryList(CategoryBeanRequest categoryBeanRequest);

    /**
     * @Author walter.limeng
     * @user walter.limeng
     * @Description  根据查询条件查询数据
     * @Date 19:13 2018/7/23
     * @Param pcateId
     * @Param cateId
     * @return
     */
    Integer getConNum(Integer pcateId, Integer cateId);

    /**
     * @Author walter.limeng
     * @user walter.limeng
     * @Description  根据主键ID获取数据
     * @Date 9:24 2018/7/24
     * @Param id
     * @return
     */
    Category infoTypeAction(Integer id);

    /**
     * @Author walter.limeng
     * @user walter.limeng
     * @Description  新增分类
     * @Date 9:45 2018/7/24
     * @Param categoryVO
     * @return
     */
    Integer insertCategory(CategoryVO categoryVO);

    /**
     * @Author walter.limeng
     * @user walter.limeng
     * @Description  修改分类
     * @Date 9:57 2018/7/24
     * @Param categoryVO
     * @return Integer
     */
    Integer updateAction(CategoryVO categoryVO);

    /**
     * @Author walter.limeng
     * @user walter.limeng
     * @Description  根据父级菜单查询子级菜单
     * @Date 10:15 2018/7/24
     * @Param categoryVO
     * @return
     */
    Integer getCategoryTotal(CategoryVO categoryVO);

    /**
     * @Author walter.limeng
     * @user walter.limeng
     * @Description  根据pCateId和cateId查询列表
     * @Date 10:48 2018/7/24
     * @Param pid
     * @Param cid
     * @return
     */
    List<ContentHelpVO> getListByPcateIdAndcateId(Integer pid, Integer cid);

    /**
     * @Author walter.limeng
     * @user walter.limeng
     * @Description  删除ContentHelp
     * @Date 10:57 2018/7/24
     * @Param id
     * @return
     */
    Integer delContentHelp(Integer id);

    /**
     * @Author walter.limeng
     * @user walter.limeng
     * @Description  删除Category
     * @Date 10:58 2018/7/24
     * @Param id
     * @return
     */
    Integer delCategory(Integer id);

    /**
     * @Author walter.limeng
     * @user walter.limeng
     * @Description  修改contentHelp
     * @Date 10:58 2018/7/24
     * @Param help
     * @return
     */
    Integer updateContentHelp(ContentHelpVO contentHelpVO);

    /**
     * @Author walter.limeng
     * @user walter.limeng
     * @Description  查询问题列表总数
     * @Date 11:46 2018/7/24
     * @Param 
     * @return 
     */
    Integer getContentHelpCount(ContentHelpBeanRequest contentHelpBeanRequest);

    /**
     * @Author walter.limeng
     * @user walter.limeng
     * @Description  查询问题列表数据
     * @Date 11:46 2018/7/24
     * @Param 
     * @return 
     */
    List<ContentHelp> getContentHelpList(ContentHelpBeanRequest contentHelpBeanRequest);

    /**
     * @Author walter.limeng
     * @user walter.limeng
     * @Description  查询问题类型
     * @Date 11:46 2018/7/24
     * @Param 
     * @return 
     */
    List<Integer> getConType();

    /**
     * @Author walter.limeng
     * @user walter.limeng
     * @Description  查询所有的分类
     * @Date 14:01 2018/7/24
     * @Param isHide
     * @return
     */
    List<CategoryVO> getCategoryListByParam(Integer isHide);

    /**
     * @Author walter.limeng
     * @user walter.limeng
     * @Description  根据ID查询
     * @Date 14:10 2018/7/24
     * @Param id
     * @return
     */
    ContentHelp queryContentById(Integer id);

    /**
     * @Author walter.limeng
     * @user walter.limeng
     * @Description  添加分类
     * @Date 14:19 2018/7/24
     * @Param con
     * @return
     */
    Integer addContentHelp(ContentHelp con);

    /**
     * @Author walter.limeng
     * @user walter.limeng
     * @Description  修改问题
     * @Date 14:26 2018/7/24
     * @Param con
     * @return
     */
    Integer updateHelpAction(ContentHelp con);
}
