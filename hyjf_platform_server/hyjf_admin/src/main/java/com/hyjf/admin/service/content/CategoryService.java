package com.hyjf.admin.service.content;

import com.hyjf.am.response.admin.CategoryResponse;
import com.hyjf.am.resquest.admin.CategoryBeanRequest;
import com.hyjf.am.resquest.admin.ContentHelpBeanRequest;
import com.hyjf.am.vo.admin.CategoryVO;
import com.hyjf.am.vo.admin.ContentHelpVO;

import java.util.List;

/**
 * @Auther: walter.limeng
 * @Date: 2018/7/20 15:12
 * 内容中心——帮助中心
 * @Description: CategoryService
 */
public interface CategoryService {
    /**
     * @Author walter.limeng
     * @Description  获取帮助中心数据
     * @Date 16:18 2018/7/20
     * @Param categoryBeanRequest
     * @return
     */
    CategoryResponse getCategoryPage(CategoryBeanRequest categoryBeanRequest);

    /**
     * @Author walter.limeng
     * @user walter.limeng
     * @Description  二级菜单联动
     * @Date 19:21 2018/7/23
     * @Param categoryBeanRequest
     * @return 
     */
    CategoryResponse changeSubTypeAction(CategoryBeanRequest categoryBeanRequest);

    /**
     * @Author walter.limeng
     * @user walter.limeng
     * @Description  根据ID获取数据
     * @Date 9:20 2018/7/24
     * @Param id 主键ID
     * @return
     */
    CategoryResponse infoTypeAction(Integer id);

    /**
     * @Author walter.limeng
     * @user walter.limeng
     * @Description  跳转到添加子分类的页面
     * @Date 9:28 2018/7/24
     * @Param categoryBeanRequest
     * @return
     */
    CategoryResponse infoSubTypeAction(CategoryBeanRequest categoryBeanRequest);

    /**
     * @Author walter.limeng
     * @user walter.limeng
     * @Description 添加分类
     * @Date 9:39 2018/7/24
     * @Param categoryVO
     * @return Integer
     */
    Integer insertCategory(CategoryVO categoryVO);

    /**
     * @Author walter.limeng
     * @user walter.limeng
     * @Description  修改分类
     * @Date 9:54 2018/7/24
     * @Param categoryVO
     * @return Integer
     */
    Integer updateAction(CategoryVO categoryVO);

    /**
     * @Author walter.limeng
     * @user walter.limeng
     * @Description  根据父级ID查询子级菜单
     * @Date 10:11 2018/7/24
     * @Param record
     * @return
     */
    CategoryResponse getCategoryCount(CategoryVO record);

    /**
     * @Author walter.limeng
     * @user walter.limeng
     * @Description  根据pCateId和cateId查询总数
     * @Date 10:23 2018/7/24
     * @Param pid
     * @Param cid
     * @return
     */
    Integer getCountByPcateIdAndcateId(Integer pid, Integer cid);

    /**
     * @Author walter.limeng
     * @user walter.limeng
     * @Description  根据pCateId和cateId查询列表
     * @Date 10:44 2018/7/24
     * @Param pid
     * @Param cid
     * @return
     */
    List<ContentHelpVO> getListByPcateIdAndcateId(CategoryVO categoryVO);

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
     * @Description  问题列表分页查询数据
     * @Date 11:24 2018/7/24
     * @Param ContentHelpBeanRequest
     * @return
     */
    CategoryResponse getHelpPage(ContentHelpBeanRequest contentHelpBeanRequest);

    /**
     * @Author walter.limeng
     * @user walter.limeng
     * @Description  列表页跳转详情页(含有id更新，不含有id添加)
     * @Date 14:06 2018/7/24
     * @Param contentHelpBeanRequest
     * @return 
     */
    CategoryResponse getHelpInfo(ContentHelpBeanRequest contentHelpBeanRequest);

    /**
     * @Author walter.limeng
     * @user walter.limeng
     * @Description  添加问题
     * @Date 14:14 2018/7/24
     * @Param contentHelpBeanRequest
     * @return
     */
    CategoryResponse insertHelpInfo(ContentHelpBeanRequest contentHelpBeanRequest);

    /**
     * @Author walter.limeng
     * @user walter.limeng
     * @Description  修改问题
     * @Date 14:22 2018/7/24
     * @Param contentHelpBeanRequest
     * @return
     */
    CategoryResponse updateHelpAction(ContentHelpBeanRequest contentHelpBeanRequest);

    /**
     * @Author walter.limeng
     * @user walter.limeng
     * @Description  修改问题状态
     * @Date 14:33 2018/7/24
     * @Param status  0:关闭   1：开启
     * @Param zhiChiStatus  0:关闭   1：开启
     * @return
     */
    Integer chanContentHelp(Integer contentId, Integer status, Integer zhiChiStatus);

    /**
     * @Author walter.limeng
     * @user walter.limeng
     * @Description  查询常见问题分页数据
     * @Date 14:57 2018/7/24
     * @Param contentHelpBeanRequest
     * @return
     */
    CategoryResponse getOftenInitPage(ContentHelpBeanRequest contentHelpBeanRequest);

    /**
     * @Author walter.tanyy
     * @user walter.tanyy
     * @Description  智齿客服初始化
     * @Date 14:57 2018/7/24
     * @Param contentHelpBeanRequest
     * @return
     */
    CategoryResponse getZhiChiInit(ContentHelpBeanRequest contentHelpBeanRequest);
}
