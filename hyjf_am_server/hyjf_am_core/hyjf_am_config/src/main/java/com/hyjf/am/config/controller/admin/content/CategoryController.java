package com.hyjf.am.config.controller.admin.content;

import com.hyjf.am.config.controller.BaseConfigController;
import com.hyjf.am.config.dao.model.auto.Category;
import com.hyjf.am.config.dao.model.auto.ContentHelp;
import com.hyjf.am.config.dao.model.auto.ContentHelpExample;
import com.hyjf.am.config.service.CategoryService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.CategoryResponse;
import com.hyjf.am.resquest.admin.CategoryBeanRequest;
import com.hyjf.am.resquest.admin.ContentHelpBeanRequest;
import com.hyjf.am.vo.admin.CategoryVO;
import com.hyjf.am.vo.admin.ContentHelpCustomizeVO;
import com.hyjf.am.vo.admin.ContentHelpVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.GetDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Auther: walter.limeng
 * @Date: 2018/7/20 16:28
 * @Description: CategoryController
 */
@RestController
@RequestMapping("/am-config/content/help")
public class CategoryController extends BaseConfigController {
    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    private CategoryService categoryService;

    /**
     * @Author walter.limeng
     * @Description 分页查询帮助中心页面
     * @Date 18:29 2018/7/23
     * @Param categoryBeanRequest
     * @return
     */
    @RequestMapping("/searchaction")
    public CategoryResponse searchAction(@RequestBody CategoryBeanRequest categoryBeanRequest) {
        logger.info("查询帮助中心开始......");
        CategoryResponse response = new CategoryResponse();
        //父级菜单
        List<Category> parentCategoryList = categoryService.searchParentCategorys(categoryBeanRequest, true);
        response.setParentList(parentCategoryList);
        //子级菜单
        List<Category> childCategoryList = categoryService.searchChildCategorys(categoryBeanRequest, true);
        response.setChildList(childCategoryList);
        //查询总数
        Integer count = this.categoryService.getCategoryCount(categoryBeanRequest);
        response.setCount(count);
        if (count >= 0) {
            List<Category> recodeList = this.categoryService.getCategoryList(categoryBeanRequest);
            for (Category ca : recodeList) {
                if (ca.getLevel() == 0) {
                    // 一级菜单,Pid等于ca.id即满足条件
                    ca.setTip(categoryService.getConNum(ca.getId(),null) + "");// 废字段用来存储问题数量
                } else if (ca.getLevel() == 1) {
                    // 二级菜单
                    ContentHelpExample con = new ContentHelpExample();
                    ContentHelpExample.Criteria conCriteria = con.createCriteria();
                    conCriteria.andPcateIdEqualTo(ca.getPid());
                    conCriteria.andCateIdEqualTo(ca.getId());
                    ca.setTip(categoryService.getConNum(ca.getPid(),ca.getId()) + "");// 废字段用来存储问题数量
                }
            }
            response.setRecordList(CommonUtils.convertBeanList(recodeList,CategoryVO.class));
        }

        return response;
    }

    /**
     * @Author walter.limeng
     * @user walter.limeng
     * @Description  查询二级菜单联动
     * @Date 19:23 2018/7/23
     * @Param categoryBeanRequest
     * @return
     */
    @RequestMapping("/changesubtype")
    public CategoryResponse changeSubTypeAction(@RequestBody CategoryBeanRequest categoryBeanRequest) {
        logger.info("查询帮助中心开始......");
        CategoryResponse response = new CategoryResponse();
        //子级菜单
        List<Category> childCategoryList = categoryService.searchChildCategorys(categoryBeanRequest, categoryBeanRequest.getIsShow());
        response.setChildList(childCategoryList);
        return response;
    }

    /**
     * @Author walter.limeng
     * @user walter.limeng
     * @Description  根据主键ID获取数据
     * @Date 9:24 2018/7/24
     * @Param id
     * @return
     */
    @RequestMapping("/infotypeaction/{id}")
    public CategoryResponse infoTypeAction(@PathVariable Integer id) {
        CategoryResponse<CategoryVO> response = new CategoryResponse();
        Category category = categoryService.infoTypeAction(id);
        CategoryVO vo = new CategoryVO();
        BeanUtils.copyProperties(category, vo);
        response.setData(vo);
        return response;
    }

    /**
     * @Author walter.limeng
     * @user walter.limeng
     * @Description  跳转到添加子分类的页面
     * @Date 9:30 2018/7/24
     * @Param categoryBeanRequest
     * @return
     */
    @RequestMapping("/infosubtypeaction")
    public CategoryResponse infoSubTypeAction(@RequestBody CategoryBeanRequest categoryBeanRequest) {
        logger.info("查询帮助中心开始......");
        CategoryResponse response = new CategoryResponse();
        //子级菜单
        List<Category> parentCategoryList = categoryService.searchParentCategorys(categoryBeanRequest, false);
        response.setParentList(parentCategoryList);
        if (categoryBeanRequest.getId() != null) {
            Integer id = Integer.valueOf(categoryBeanRequest.getId());
            Category category = categoryService.infoTypeAction(id);
            response.setData(category);
        }
        return response;
    }

    /**
     * @Author walter.limeng
     * @user walter.limeng
     * @Description  新增分类
     * @Date 9:43 2018/7/24
     * @Param categoryVO
     * @return
     */
    @RequestMapping("/insertcategory")
    public CategoryResponse insertCategory(@RequestBody CategoryVO categoryVO) {
        logger.info("帮助中心新增分类......");
        CategoryResponse response = new CategoryResponse();
        Integer flag = categoryService.insertCategory(categoryVO);
        response.setFlag(flag);
        return response;
    }

    /**
     * @Author walter.limeng
     * @user walter.limeng
     * @Description  帮助中心修改分类
     * @Date 11:02 2018/7/24
     * @Param categoryVO
     * @return
     */
    @RequestMapping("/updateaction")
    public CategoryResponse updateAction(@RequestBody CategoryVO categoryVO) {
        logger.info("帮助中心修改分类......");
        CategoryResponse response = new CategoryResponse();
        Integer flag = categoryService.updateAction(categoryVO);
        response.setFlag(flag);
        return response;
    }

    /**
     * @Author walter.limeng
     * @user walter.limeng
     * @Description  帮助中心根据父级菜单查询子级菜单
     * @Date 11:02 2018/7/24
     * @Param categoryVO
     * @return
     */
    @RequestMapping("/getcategorycount")
    public CategoryResponse getCategoryCount(@RequestBody CategoryVO categoryVO) {
        logger.info("帮助中心根据父级菜单查询子级菜单......");
        CategoryResponse response = new CategoryResponse();
        Integer count = categoryService.getCategoryTotal(categoryVO);
        response.setCount(count);
        return response;
    }

    /**
     * @Author walter.limeng
     * @user walter.limeng
     * @Description  根据pCateId和cateId查询总数
     * @Date 11:02 2018/7/24
     * @Param pid
     * @Param cid
     * @return
     */
    @RequestMapping("/getbypcateidAandcateid")
    public CategoryResponse getCountByPcateIdAndcateId(@RequestBody CategoryVO request) {
        logger.info("根据pCateId和cateId查询总数......");
        CategoryResponse response = new CategoryResponse();
        Integer count = categoryService.getConNum(request.getPid(), request.getId());
        response.setCount(count);
        return response;
    }

    /**
     * @Author walter.limeng
     * @user walter.limeng
     * @Description  根据pCateId和cateId查询List
     * @Date 11:02 2018/7/24
     * @Param pid
     * @Param cid
     * @return
     */
    @RequestMapping("/getlistbypcateidandcateid/{pid}/{cid}")
    public CategoryResponse getListByPcateIdAndcateId(@PathVariable Integer pid,@PathVariable Integer cid) {
        logger.info("根据pCateId和cateId查询List......");
        CategoryResponse response = new CategoryResponse();
        List<ContentHelpVO> list = categoryService.getListByPcateIdAndcateId(pid,cid);
        response.setRecordList(list);
        return response;
    }

    /**
     * @Author walter.limeng
     * @user walter.limeng
     * @Description  删除ContentHelp
     * @Date 11:04 2018/7/24
     * @Param id
     * @return
     */
    @RequestMapping("/delcontenthelp/{id}")
    public CategoryResponse delContentHelp(@PathVariable Integer id) {
        logger.info(" 删除ContentHelp......");
        CategoryResponse response = new CategoryResponse();
        Integer flag = categoryService.delContentHelp(id);
        response.setFlag(flag);
        return response;
    }

    /**
     * @Author walter.limeng
     * @user walter.limeng
     * @Description  删除Catrgory
     * @Date 11:05 2018/7/24
     * @Param id
     * @return
     */
    @RequestMapping("/delcategory/{id}")
    public CategoryResponse delCategory(@PathVariable Integer id) {
        logger.info(" 删除Catrgory......");
        CategoryResponse response = new CategoryResponse();
        Integer flag = categoryService.delCategory(id);
        response.setFlag(flag);
        return response;
    }

    /**
     * @Author walter.limeng
     * @user walter.limeng
     * @Description  修改contentHelp
     * @Date 11:07 2018/7/24
     * @Param contentHelpVO
     * @return
     */
    @RequestMapping("/updatecontenthelp")
    public CategoryResponse updateContentHelp(@RequestBody ContentHelpVO contentHelpVO) {
        logger.info(" 修改contentHelp......");
        CategoryResponse response = new CategoryResponse();
        Integer flag = categoryService.updateContentHelp(contentHelpVO);
        response.setFlag(flag);
        return response;
    }

    /**
     * @Author walter.limeng
     * @user walter.limeng
     * @Description  问题列表查询
     * @Date 14:08 2018/7/24
     * @Param contentHelpBeanRequest
     * @return
     */
    @RequestMapping("/gethelppage")
    public CategoryResponse getHelpPage(@RequestBody ContentHelpBeanRequest contentHelpBeanRequest) {
        logger.info("查询帮助中心问题列表开始......");
        CategoryResponse response = new CategoryResponse();
        CategoryBeanRequest bean = new CategoryBeanRequest();
        bean.setPid(contentHelpBeanRequest.getPcateId());
        //父级菜单
        List<Category> parentCategoryList = categoryService.searchParentCategorys(bean, true);
        response.setParentList(parentCategoryList);
        //子级菜单
        List<Category> childCategoryList = categoryService.searchChildCategorys(bean, true);
        response.setChildList(childCategoryList);
        //查询总数
        Integer count = this.categoryService.getContentHelpCount(contentHelpBeanRequest);
        response.setCount(count);
        if (count >= 0) {
            List<ContentHelp> conhelp = this.categoryService.getContentHelpList(contentHelpBeanRequest);
            List<Integer> ids = categoryService.getConType();
            List<ContentHelpCustomizeVO> resultHelpList = new ArrayList<ContentHelpCustomizeVO>();
            for (ContentHelp help : conhelp) {
                ContentHelpCustomizeVO cc = new ContentHelpCustomizeVO();
                BeanUtils.copyProperties(help, cc);
                if (ids.contains(help.getPcateId())) {
                    cc.setIsZhiChi("true");
                }
                cc.setAdd_time(GetDate.date2Str(help.getCreateTime(), GetDate.datetimeFormat));
                resultHelpList.add(cc);
            }
            List<CategoryVO> categoryVOList = categoryService.getCategoryListByParam(null);
            response.setHelpList(resultHelpList);
            response.setRecordList(categoryVOList);
        }
        return response;
    }

    /**
     * @Author walter.limeng
     * @user walter.limeng
     * @Description  问题列表页跳转详情页
     * @Date 14:16 2018/7/24
     * @Param contentHelpBeanRequest
     * @return
     */
    @RequestMapping("/gethelpinfo")
    public CategoryResponse getHelpInfo(@RequestBody ContentHelpBeanRequest contentHelpBeanRequest) {
        logger.info("查询帮助中心问题列表页跳转详情页开始......");
        CategoryResponse response = new CategoryResponse();
        CategoryBeanRequest bean = new CategoryBeanRequest();
        bean.setPid(contentHelpBeanRequest.getPcateId());
        //父级菜单
        List<Category> parentCategoryList = categoryService.searchParentCategorys(bean, false);
        response.setParentList(parentCategoryList);
        if (contentHelpBeanRequest.getId() != null) {
            Integer id = Integer.valueOf(contentHelpBeanRequest.getId());
            ContentHelp record = this.categoryService.queryContentById(id);
            // 第一步:获取子分类
            List<Category> childCategoryList = categoryService.searchChildCategorys(bean, true);
            response.setChildList(childCategoryList);
            response.setData(record);
        }
        return response;
    }

    /**
     * @Author walter.limeng
     * @user walter.limeng
     * @Description  添加问题
     * @Date 14:16 2018/7/24
     * @Param contentHelpBeanRequest
     * @return
     */
    @RequestMapping("/inserthelpinfo")
    public CategoryResponse insertHelpInfo(@RequestBody ContentHelpBeanRequest contentHelpBeanRequest) {
        logger.info("查询帮助中心添加问题开始......");
        CategoryResponse response = new CategoryResponse();
        ContentHelp con = new ContentHelp();
        BeanUtils.copyProperties(contentHelpBeanRequest, con);
        con.setCreateTime(new Date());
        categoryService.addContentHelp(con);
        response = getHelpPage(contentHelpBeanRequest);
        return response;
    }

    /**
     * @Author walter.limeng
     * @user walter.limeng
     * @Description  修改问题
     * @Date 14:23 2018/7/24
     * @Param contentHelpBeanRequest
     * @return
     */
    @RequestMapping("/updatehelpaction")
    public CategoryResponse updateHelpAction(@RequestBody ContentHelpBeanRequest contentHelpBeanRequest) {
        logger.info("查询帮助中心添加问题开始......");
        CategoryResponse response = new CategoryResponse();
        if(null != contentHelpBeanRequest.getId()){
            ContentHelp con = new ContentHelp();
            BeanUtils.copyProperties(contentHelpBeanRequest, con);
            categoryService.updateHelpAction(con);
            response = getHelpPage(contentHelpBeanRequest);
        }else{
            response.setRtn(Response.ERROR);
            response.setMessage("请选择修改的问题！");
        }
        return response;
    }

    /**
     * @Author walter.limeng
     * @user walter.limeng
     * @Description  修改问题
     * @Date 14:36 2018/7/24
     * @Param contentId  问题ID
     * @Param status  0:关闭   1：开启
     * @Param zhiChiStatus  0:关闭   1：开启
     * @return
     */
    @RequestMapping("/chancontenthelp/{contentId}/{status}/{zhiChiStatus}")
    public CategoryResponse chanContentHelp(@PathVariable Integer contentId,@PathVariable Integer status,@PathVariable Integer zhiChiStatus) {
        logger.info("修改问题......");
        CategoryResponse response = new CategoryResponse();
        ContentHelp con = this.categoryService.queryContentById(contentId);
        con.setStatus(status);
        if(null != zhiChiStatus){
            //con.setZhiChiStatus(zhiChiStatus);
        }
        // 更新
        this.categoryService.updateHelpAction(con);
        return response;
    }

    /**
     * @Author walter.limeng
     * @user walter.limeng
     * @Description  查询常见问题列表分页数据
     * @Date 14:59 2018/7/24
     * @Param contentHelpBeanRequest
     * @return
     */
    @RequestMapping("/getofteninitpage")
    public CategoryResponse getOftenInitPage(@RequestBody ContentHelpBeanRequest contentHelpBeanRequest) {
        logger.info("查询帮助中心问题列表开始......");
        CategoryResponse response = new CategoryResponse();
        CategoryBeanRequest bean = new CategoryBeanRequest();
        bean.setPid(contentHelpBeanRequest.getPcateId());
        //父级菜单
        List<Category> parentCategoryList = categoryService.searchParentCategorys(bean, true);
        response.setParentList(parentCategoryList);
        //子级菜单
        List<Category> childCategoryList = categoryService.searchChildCategorys(bean, true);
        response.setChildList(childCategoryList);
        contentHelpBeanRequest.setStatus(2);
        //查询总数
        Integer count = this.categoryService.getContentHelpCount(contentHelpBeanRequest);
        response.setCount(count);
        if (count >= 0) {
            List<ContentHelp> conhelp = this.categoryService.getContentHelpList(contentHelpBeanRequest);
            List<Integer> ids = categoryService.getConType();
            List<ContentHelpCustomizeVO> resultHelpList = new ArrayList<ContentHelpCustomizeVO>();
            for (ContentHelp help : conhelp) {
                ContentHelpCustomizeVO cc = new ContentHelpCustomizeVO();
                BeanUtils.copyProperties(help, cc);
                if (ids.contains(help.getPcateId())) {
                    cc.setIsZhiChi("true");
                }
                cc.setAdd_time(GetDate.date2Str(help.getCreateTime(), GetDate.datetimeFormat));
                resultHelpList.add(cc);
            }
            List<CategoryVO> categoryVOList = categoryService.getCategoryListByParam(0);
            response.setHelpList(resultHelpList);
            response.setRecordList(categoryVOList);
        }
        return response;
    }
}
