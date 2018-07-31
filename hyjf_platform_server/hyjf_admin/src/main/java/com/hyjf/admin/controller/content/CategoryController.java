package com.hyjf.admin.controller.content;

import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.content.CategoryService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.CategoryResponse;
import com.hyjf.am.resquest.admin.CategoryBeanRequest;
import com.hyjf.am.resquest.admin.ContentAdsRequest;
import com.hyjf.am.resquest.admin.ContentHelpBeanRequest;
import com.hyjf.am.vo.admin.CategoryVO;
import com.hyjf.am.vo.admin.ContentHelpVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Auther: walter.limeng
 * @Date: 2018/7/20 15:08
 * @Description: CategoryController
 */
@Api(value = "admin内容中心-帮助中心" ,description ="内容中心-帮助中心")
@RestController
@RequestMapping("/hyjf-admin/content/help")
public class CategoryController extends BaseController {

    @Autowired
    private CategoryService categoryService;

    @ApiOperation(value = "内容中心-帮助中心", notes = "帮助中心-条件列表查询")
    @RequestMapping("/init")
    public CategoryResponse searchAction(ContentAdsRequest request,CategoryBeanRequest categoryBeanRequest){
        logger.info("查询内容中心-帮助中心-条件列表查询开始......");
        CategoryResponse response = categoryService.getCategoryPage(categoryBeanRequest);
        return response;
    }

    @ApiOperation(value = "内容中心-帮助中心-二级菜单联动", notes = "帮助中心-二级菜单联动")
    @RequestMapping("/changesubtypeaction")
    public CategoryResponse changeSubTypeAction(ContentAdsRequest request,CategoryBeanRequest categoryBeanRequest){
        logger.info("查询内容中心-帮助中心-二级菜单联动......");
        categoryBeanRequest.setIsShow(true);
        CategoryResponse response = categoryService.changeSubTypeAction(categoryBeanRequest);
        return response;
    }

    @ApiOperation(value = "内容中心-帮助中心-二级菜单联动2,只显示可用的二级菜单", notes = "帮助中心-二级菜单联动2,只显示可用的二级菜单")
    @RequestMapping("/changesubtypeaction2")
    public CategoryResponse changeSubTypeAction2(ContentAdsRequest request,CategoryBeanRequest categoryBeanRequest){
        logger.info("查询内容中心-帮助中心-二级菜单联动2,只显示可用的二级菜单......");
        categoryBeanRequest.setIsShow(false);
        CategoryResponse response = categoryService.changeSubTypeAction(categoryBeanRequest);
        return response;
    }

    @ApiOperation(value = "内容中心-帮助中心-跳转到添加分类的页面", notes = "帮助中心-跳转到添加分类的页面")
    @RequestMapping("/infotypeaction")
    public CategoryResponse infoTypeAction(ContentAdsRequest request,CategoryBeanRequest categoryBeanRequest){
        logger.info("查询内容中心-帮助中心-跳转到添加分类的页面......");
        CategoryResponse response = new CategoryResponse();
        if (categoryBeanRequest.getId() != null) {
            Integer id = Integer.valueOf(categoryBeanRequest.getId());
            response = categoryService.infoTypeAction(id);
        }
        response.setCategoryLevel("1");
        return response;
    }

    @ApiOperation(value = "内容中心-帮助中心-跳转到添加子分类的页面", notes = "帮助中心-跳转到添加子分类的页面")
    @RequestMapping("/infosubtypeaction")
    public CategoryResponse infoSubTypeAction(ContentAdsRequest request,CategoryBeanRequest categoryBeanRequest){
        logger.info("查询内容中心-帮助中心-跳转到添加子分类的页面......");
        CategoryResponse response = new CategoryResponse();
        response = categoryService.infoSubTypeAction(categoryBeanRequest);
        response.setCategoryLevel("2");
        return response;
    }

    @ApiOperation(value = "内容中心-帮助中心-添加分类", notes = "帮助中心-添加分类")
    @RequestMapping("/addaction")
    public CategoryResponse addCate(ContentAdsRequest request,CategoryBeanRequest categoryBeanRequest){
        logger.info("查询内容中心-帮助中心-添加分类......");
        CategoryResponse response = new CategoryResponse();
        CategoryVO categoryVO = new CategoryVO();
        BeanUtils.copyProperties(categoryBeanRequest,categoryVO);
        categoryVO.setGroup("help");
        categoryService.insertCategory(categoryVO);
        response = categoryService.getCategoryPage(categoryBeanRequest);
        return response;
    }

    @ApiOperation(value = "内容中心-帮助中心-修改分类", notes = "帮助中心-修改分类")
    @RequestMapping("/updateaction")
    public CategoryResponse updateAction(ContentAdsRequest request,CategoryBeanRequest categoryBeanRequest){
        logger.info("查询内容中心-帮助中心-修改分类......");
        CategoryResponse response = new CategoryResponse();
        if(null != categoryBeanRequest.getId()){
            CategoryVO categoryVO = new CategoryVO();
            BeanUtils.copyProperties(categoryBeanRequest,categoryVO);
            categoryVO.setGroup("help");
            categoryService.updateAction(categoryVO);
            response = categoryService.getCategoryPage(categoryBeanRequest);
        }else{
            response.setRtn(Response.ERROR);
            response.setMessage("更新异常，主键ID为空！");
        }
        return response;
    }

    @ApiOperation(value = "内容中心-帮助中心-关闭模板", notes = "帮助中心-关闭模板")
    @RequestMapping("/closeaction")
    public CategoryResponse closeAction(ContentAdsRequest request,CategoryBeanRequest categoryBeanRequest){
        logger.info("查询内容中心-帮助中心-关闭模板......");
        CategoryResponse response = new CategoryResponse();
        if(null != categoryBeanRequest.getId()){
            if(null != categoryBeanRequest.getIds()){
                CategoryResponse responses = categoryService.infoTypeAction(Integer.parseInt(categoryBeanRequest.getIds()));
                CategoryVO categoryVO = (CategoryVO)responses.getData();
                if(categoryVO != null){
                    categoryVO.setHide(1);
                    // 更新
                    this.categoryService.updateAction(categoryVO);
                }
            }
            response = categoryService.getCategoryPage(categoryBeanRequest);
        }else{
            response.setRtn(Response.ERROR);
            response.setMessage("关闭模板失败，请选择模板！");
        }
        return response;
    }

    @ApiOperation(value = "内容中心-帮助中心-启用模板", notes = "帮助中心-启用模板")
    @RequestMapping("/openaction")
    public CategoryResponse openAction(ContentAdsRequest request,CategoryBeanRequest categoryBeanRequest){
        logger.info("查询内容中心-帮助中心-启用模板......");
        CategoryResponse response = new CategoryResponse();
        if(null != categoryBeanRequest.getId()){
            if(null != categoryBeanRequest.getIds()){
                CategoryResponse responses = categoryService.infoTypeAction(Integer.parseInt(categoryBeanRequest.getIds()));
                CategoryVO categoryVO = (CategoryVO)responses.getData();
                if(categoryVO != null){
                    categoryVO.setHide(0);
                    // 更新
                    this.categoryService.updateAction(categoryVO);
                }
            }
            response = categoryService.getCategoryPage(categoryBeanRequest);
        }else{
            response.setRtn(Response.ERROR);
            response.setMessage("关闭模板失败，请选择模板！");
        }
        return response;
    }

    @ApiOperation(value = "内容中心-帮助中心-删除前的验证", notes = "帮助中心-删除前的验证")
    @RequestMapping("/beforedelinfoaction")
    public CategoryResponse beforeDelInfoAction(ContentAdsRequest request,CategoryBeanRequest categoryBeanRequest){
        logger.info("查询内容中心-帮助中心-删除前的验证......");
        CategoryResponse response = new CategoryResponse();
        if(null != categoryBeanRequest.getId()){
            response = categoryService.getCategoryPage(categoryBeanRequest);

            Integer id = categoryBeanRequest.getId();
            CategoryResponse responses = categoryService.infoTypeAction(Integer.parseInt(categoryBeanRequest.getIds()));
            CategoryVO record = (CategoryVO)responses.getData();

            if (record.getLevel() == 0) {
                // 一级菜单等于ca.id即满足条件
                Integer tip = categoryService.getCountByPcateIdAndcateId(record.getId(),null);
                record.setTip(tip+"");
            } else if (record.getLevel() == 1) {
                // 二级菜单没有验证
                Integer tip = categoryService.getCountByPcateIdAndcateId(record.getPid(),record.getId());
                record.setTip(tip+"");
            }
            response.setData(record);
        }else{
            response.setRtn(Response.ERROR);
            response.setMessage("删除ID不可为空！");
        }
        return response;
    }

    @ApiOperation(value = "内容中心-帮助中心-删除分类", notes = "帮助中心-删除分类")
    @RequestMapping("/delaction")
    public CategoryResponse delAction(ContentAdsRequest request,CategoryBeanRequest categoryBeanRequest){
        logger.info("查询内容中心-帮助中心-删除分类......");
        CategoryResponse response = new CategoryResponse();
        if(null != categoryBeanRequest.getId()){
            response = categoryService.getCategoryPage(categoryBeanRequest);
            Integer id = categoryBeanRequest.getId();
            CategoryResponse responses = categoryService.infoTypeAction(Integer.parseInt(categoryBeanRequest.getIds()));
            CategoryVO category = (CategoryVO)responses.getData();
            List<ContentHelpVO> contentHelpVOList = null;
            if (category.getPid() == 0) {
                // 如果PID=0表示是一级菜单
                contentHelpVOList = categoryService.getListByPcateIdAndcateId(category.getId(),null);
            } else {
                contentHelpVOList = categoryService.getListByPcateIdAndcateId(category.getPid(),category.getId());
            }

            if (categoryBeanRequest.getDelType() == 0) {
                // 分类和问题全删除
                for (ContentHelpVO help : contentHelpVOList) {
                    categoryService.delContentHelp(help.getId());
                }
                categoryService.delCategory(category.getId());
            } else if (categoryBeanRequest.getDelType() == 1) {
                // 删除分类,问题转移
                for (ContentHelpVO help : contentHelpVOList) {
                    help.setPcateId(categoryBeanRequest.getNewpid());
                    help.setCateId(categoryBeanRequest.getNewid());
                    categoryService.updateContentHelp(help);
                }
                categoryService.delCategory(category.getId());
            }
        }else{
            response.setRtn(Response.ERROR);
            response.setMessage("删除ID不可为空！");
        }
        return response;
    }

    @ApiOperation(value = "内容中心-帮助中心-问题列表", notes = "帮助中心-问题列表查询")
    @RequestMapping("/helpinit")
    public CategoryResponse helpInit(ContentAdsRequest request,ContentHelpBeanRequest contentHelpBeanRequest){
        logger.info("查询内容中心-帮助中心-问题列表查询开始......");
        CategoryResponse response = categoryService.getHelpPage(contentHelpBeanRequest);
        return response;
    }

    @ApiOperation(value = "内容中心-帮助中心-列表页跳转详情页(含有id更新，不含有id添加)", notes = "帮助中心-问题列表页跳转详情页(含有id更新，不含有id添加)")
    @RequestMapping("/helpinfoaction")
    public CategoryResponse helpInfo(ContentAdsRequest request,ContentHelpBeanRequest contentHelpBeanRequest){
        logger.info("查询内容中心-帮助中心-列表页跳转详情页(含有id更新，不含有id添加)查询开始......");
        CategoryResponse response = categoryService.getHelpInfo(contentHelpBeanRequest);
        return response;
    }

    @ApiOperation(value = "内容中心-帮助中心-添加问题", notes = "帮助中心-添加问题")
    @RequestMapping("/helpaddaction")
    public CategoryResponse addHelp(ContentAdsRequest request,ContentHelpBeanRequest contentHelpBeanRequest){
        logger.info("查询内容中心-帮助中心-添加问题开始......");
        CategoryResponse response = categoryService.insertHelpInfo(contentHelpBeanRequest);
        return response;
    }

    @ApiOperation(value = "内容中心-帮助中心-修改问题", notes = "帮助中心-修改问题")
    @RequestMapping("/helpupdateaction")
    public CategoryResponse updateHelpAction(ContentAdsRequest request,ContentHelpBeanRequest contentHelpBeanRequest){
        logger.info("查询内容中心-帮助中心-修改问题开始......");
        CategoryResponse response = categoryService.updateHelpAction(contentHelpBeanRequest);
        return response;
    }

    @ApiOperation(value = "内容中心-帮助中心-关闭问题", notes = "帮助中心-关闭问题")
    @RequestMapping("/closehelpaction")
    public CategoryResponse closeHelpAction(ContentAdsRequest request,ContentHelpBeanRequest contentHelpBeanRequest){
        logger.info("查询内容中心-帮助中心-关闭问题开始......");
        CategoryResponse response = new CategoryResponse();
        if(null != contentHelpBeanRequest.getId()){

            categoryService.chanContentHelp(contentHelpBeanRequest.getId(),0,null);
            response = categoryService.getHelpPage(contentHelpBeanRequest);
        }else{
            response.setRtn(Response.ERROR);
            response.setMessage("请选择关闭的问题！");
        }
        return response;
    }

    @ApiOperation(value = "内容中心-帮助中心-开启问题", notes = "帮助中心-开启问题")
    @RequestMapping("/openhelpaction")
    public CategoryResponse openHelpAction(ContentAdsRequest request,ContentHelpBeanRequest contentHelpBeanRequest){
        logger.info("查询内容中心-帮助中心-开启问题开始......");
        CategoryResponse response = new CategoryResponse();
        if(null != contentHelpBeanRequest.getId()){

            categoryService.chanContentHelp(contentHelpBeanRequest.getId(),1,null);
            response = categoryService.getHelpPage(contentHelpBeanRequest);
        }else{
            response.setRtn(Response.ERROR);
            response.setMessage("请选择开启的问题！");
        }
        return response;
    }

    @ApiOperation(value = "内容中心-帮助中心-删除问题", notes = "帮助中心-删除问题")
    @RequestMapping("/delhelpaction")
    public CategoryResponse delHelpAction(ContentAdsRequest request,ContentHelpBeanRequest contentHelpBeanRequest){
        logger.info("查询内容中心-帮助中心-删除问题开始......");
        CategoryResponse response = new CategoryResponse();
        if(null != contentHelpBeanRequest.getId()){

            categoryService.delContentHelp(contentHelpBeanRequest.getId());
            response = categoryService.getHelpPage(contentHelpBeanRequest);
        }else{
            response.setRtn(Response.ERROR);
            response.setMessage("请选择删除的问题！");
        }
        return response;
    }

    @ApiOperation(value = "内容中心-帮助中心-启用智齿常见问题", notes = "帮助中心-启用智齿常见问题")
    @RequestMapping("/movezhichiaction")
    public CategoryResponse moveZhiChiAction(ContentAdsRequest request,ContentHelpBeanRequest contentHelpBeanRequest){
        logger.info("查询内容中心-帮助中心-启用智齿常见问题开始......");
        CategoryResponse response = new CategoryResponse();
        if(null != contentHelpBeanRequest.getId()){
            categoryService.chanContentHelp(contentHelpBeanRequest.getId(),1,1);
            response = categoryService.getHelpPage(contentHelpBeanRequest);
        }else{
            response.setRtn(Response.ERROR);
            response.setMessage("请选择启用智齿常见问题！");
        }
        return response;
    }


    @ApiOperation(value = "内容中心-帮助中心-开启常见问题", notes = "帮助中心-开启常见问题")
    @RequestMapping("/moveoftenaction")
    public CategoryResponse moveOftenAction(ContentAdsRequest request,ContentHelpBeanRequest contentHelpBeanRequest){
        logger.info("查询内容中心-帮助中心-开启常见问题开始......");
        CategoryResponse response = new CategoryResponse();
        if(null != contentHelpBeanRequest.getId()){

            categoryService.chanContentHelp(contentHelpBeanRequest.getId(),2,null);
            response = categoryService.getHelpPage(contentHelpBeanRequest);
        }else{
            response.setRtn(Response.ERROR);
            response.setMessage("请选择开启常见问题！");
        }
        return response;
    }


    @ApiOperation(value = "内容中心-帮助中心-问题列表", notes = "帮助中心-问题列表查询")
    @RequestMapping("/ofteninit")
    public CategoryResponse oftenInit(ContentAdsRequest request,ContentHelpBeanRequest contentHelpBeanRequest){
        logger.info("查询内容中心-帮助中心-问题列表查询开始......");
        CategoryResponse response = categoryService.getOftenInitPage(contentHelpBeanRequest);
        return response;
    }
}
