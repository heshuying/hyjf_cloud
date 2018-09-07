package com.hyjf.admin.controller.content;

import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.content.CategoryService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.CategoryResponse;
import com.hyjf.am.resquest.admin.CategoryBeanRequest;
import com.hyjf.am.resquest.admin.ContentHelpBeanRequest;
import com.hyjf.am.vo.admin.CategoryVO;
import com.hyjf.am.vo.admin.ContentHelpVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Auther: walter.limeng
 * @Date: 2018/7/20 15:08
 * @Description: CategoryController
 */
@Api(tags = "内容中心-帮助中心")
@RestController
@RequestMapping("/hyjf-admin/content/help")
public class CategoryController extends BaseController {

	@Autowired
	private CategoryService categoryService;

	@ApiOperation(value = "内容中心-帮助中心", notes = "帮助中心-条件列表查询")
	@RequestMapping(value = "/init", method = RequestMethod.POST)
	public CategoryResponse searchAction(@RequestBody CategoryBeanRequest categoryBeanRequest) {
		logger.info("查询内容中心-帮助中心-条件列表查询开始......");
		CategoryResponse response = categoryService.getCategoryPage(categoryBeanRequest);
		return response;
	}

	@ApiOperation(value = "内容中心-帮助中心-二级菜单联动", notes = "帮助中心-二级菜单联动")
	@RequestMapping(value = "/changesubtypeaction", method = RequestMethod.POST)
	public CategoryResponse changeSubTypeAction(@RequestBody CategoryBeanRequest categoryBeanRequest) {
		logger.info("查询内容中心-帮助中心-二级菜单联动......");
		categoryBeanRequest.setIsShow(true);
		CategoryResponse response = categoryService.changeSubTypeAction(categoryBeanRequest);
		return response;
	}

	@ApiOperation(value = "内容中心-帮助中心-二级菜单联动2,只显示可用的二级菜单", notes = "帮助中心-二级菜单联动2,只显示可用的二级菜单")
	@RequestMapping(value = "/changesubtypeaction2", method = RequestMethod.POST)
	public CategoryResponse changeSubTypeAction2(@RequestBody CategoryBeanRequest categoryBeanRequest) {
		logger.info("查询内容中心-帮助中心-二级菜单联动2,只显示可用的二级菜单......");
		categoryBeanRequest.setIsShow(false);
		CategoryResponse response = categoryService.changeSubTypeAction(categoryBeanRequest);
		return response;
	}

	@ApiOperation(value = "内容中心-帮助中心-跳转到添加分类的页面", notes = "帮助中心-跳转到添加分类的页面")
	@RequestMapping(value = "/infotypeaction", method = RequestMethod.POST)
	public CategoryResponse infoTypeAction(@RequestBody CategoryBeanRequest categoryBeanRequest) {
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
	@RequestMapping(value = "/infosubtypeaction", method = RequestMethod.POST)
	public CategoryResponse infoSubTypeAction(@RequestBody CategoryBeanRequest categoryBeanRequest) {
		logger.info("查询内容中心-帮助中心-跳转到添加子分类的页面......");
		CategoryResponse response = new CategoryResponse();
		response = categoryService.infoSubTypeAction(categoryBeanRequest);
		response.setCategoryLevel("2");
		return response;
	}

	@ApiOperation(value = "内容中心-帮助中心-添加分类", notes = "帮助中心-添加分类")
	@RequestMapping(value = "/addaction", method = RequestMethod.POST)
	public CategoryResponse addCate(@RequestBody CategoryBeanRequest categoryBeanRequest) {
		logger.info("查询内容中心-帮助中心-添加分类......");
		CategoryResponse response = new CategoryResponse();
		CategoryVO categoryVO = new CategoryVO();
		BeanUtils.copyProperties(categoryBeanRequest, categoryVO);
		categoryVO.setGroup("help");
		categoryService.insertCategory(categoryVO);
		response = categoryService.getCategoryPage(categoryBeanRequest);
		return response;
	}

	@ApiOperation(value = "内容中心-帮助中心-修改分类", notes = "帮助中心-修改分类")
	@RequestMapping(value = "/updateaction", method = RequestMethod.POST)
	public CategoryResponse updateAction(@RequestBody CategoryBeanRequest categoryBeanRequest) {
		logger.info("查询内容中心-帮助中心-修改分类......");
		CategoryResponse response = new CategoryResponse();
		if (null != categoryBeanRequest.getId()) {
			CategoryVO categoryVO = new CategoryVO();
			BeanUtils.copyProperties(categoryBeanRequest, categoryVO);
			categoryVO.setGroup("help");
			categoryService.updateAction(categoryVO);
			response = categoryService.getCategoryPage(categoryBeanRequest);
		} else {
			response.setRtn(Response.ERROR);
			response.setMessage("更新异常，主键ID为空！");
		}
		return response;
	}

	@ApiOperation(value = "内容中心-帮助中心-关闭模板", notes = "帮助中心-关闭模板")
	@RequestMapping(value = "/closeaction", method = RequestMethod.POST)
	public CategoryResponse closeAction(@RequestBody CategoryBeanRequest categoryBeanRequest) {
		logger.info("查询内容中心-帮助中心-关闭模板......");
		CategoryResponse response = new CategoryResponse();
		if (null != categoryBeanRequest.getId()) {
			CategoryResponse responses = categoryService.infoTypeAction(categoryBeanRequest.getId());
			CategoryVO categoryVO = (CategoryVO) responses.getData();
			if (categoryVO != null) {
				categoryVO.setHide(1);
				// 更新
				this.categoryService.updateAction(categoryVO);
			}
			response = categoryService.getCategoryPage(categoryBeanRequest);
		} else {
			response.setRtn(Response.ERROR);
			response.setMessage("关闭模板失败，请选择模板！");
		}
		return response;
	}

	@ApiOperation(value = "内容中心-帮助中心-启用模板", notes = "帮助中心-启用模板")
	@RequestMapping(value = "/openaction", method = RequestMethod.POST)
	public CategoryResponse openAction(@RequestBody CategoryBeanRequest categoryBeanRequest) {
		logger.info("查询内容中心-帮助中心-启用模板......");
		CategoryResponse response = new CategoryResponse();
		if (null != categoryBeanRequest.getId()) {
			CategoryResponse responses = categoryService.infoTypeAction(categoryBeanRequest.getId());
			CategoryVO categoryVO = (CategoryVO) responses.getData();
			if (categoryVO != null) {
				categoryVO.setHide(0);
				// 更新
				this.categoryService.updateAction(categoryVO);
			}
			response = categoryService.getCategoryPage(categoryBeanRequest);
		} else {
			response.setRtn(Response.ERROR);
			response.setMessage("关闭模板失败，请选择模板！");
		}
		return response;
	}

	@ApiOperation(value = "内容中心-帮助中心-删除前的验证", notes = "帮助中心-删除前的验证")
	@RequestMapping(value = "/beforedelinfoaction", method = RequestMethod.POST)
	public CategoryResponse beforeDelInfoAction(@RequestBody CategoryBeanRequest categoryBeanRequest) {
		logger.info("查询内容中心-帮助中心-删除前的验证......");
		CategoryResponse response = new CategoryResponse();
		if (null != categoryBeanRequest.getId()) {
			response = categoryService.getCategoryPage(categoryBeanRequest);

			Integer id = categoryBeanRequest.getId();
			CategoryResponse<CategoryVO> responses = categoryService.infoTypeAction(id);
			CategoryVO record = responses.getData();

			if (record.getLevel() == 0) {
				// 一级菜单等于ca.id即满足条件
				Integer tip = categoryService.getCountByPcateIdAndcateId(record.getId(), null);
				record.setTip(tip + "");
			} else if (record.getLevel() == 1) {
				// 二级菜单没有验证
				Integer tip = categoryService.getCountByPcateIdAndcateId(record.getPid(), record.getId());
				record.setTip(tip + "");
			}
			response.setData(record);
		} else {
			response.setRtn(Response.ERROR);
			response.setMessage("删除ID不可为空！");
		}
		return response;
	}

	@ApiOperation(value = "内容中心-帮助中心-删除分类", notes = "帮助中心-删除分类")
	@RequestMapping(value = "/delaction", method = RequestMethod.POST)
	public CategoryResponse delAction(@RequestBody CategoryBeanRequest categoryBeanRequest) {
		logger.info("查询内容中心-帮助中心-删除分类......");
		CategoryResponse response = new CategoryResponse();
		if (null != categoryBeanRequest.getId()) {
			response = categoryService.getCategoryPage(categoryBeanRequest);
			Integer id = categoryBeanRequest.getId();
			CategoryResponse responses = categoryService.infoTypeAction(id);
			CategoryVO category = (CategoryVO) responses.getData();
			List<ContentHelpVO> contentHelpVOList = null;
			if (category.getPid() == 0) {
				// 如果PID=0表示是一级菜单
                CategoryVO request = new CategoryVO();
                request.setPid(category.getPid());
				contentHelpVOList = categoryService.getListByPcateIdAndcateId(request);
			} else {
				contentHelpVOList = categoryService.getListByPcateIdAndcateId(category);
			}

			if (categoryBeanRequest.getDelType() == null || categoryBeanRequest.getDelType() == 0) {
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
		} else {
			response.setRtn(Response.ERROR);
			response.setMessage("删除ID不可为空！");
		}
		return response;
	}

	@ApiOperation(value = "内容中心-帮助中心-问题列表", notes = "帮助中心-问题列表查询")
	@RequestMapping(value = "/helpinit", method = RequestMethod.POST)
	public CategoryResponse helpInit(@RequestBody ContentHelpBeanRequest contentHelpBeanRequest) {
		logger.info("查询内容中心-帮助中心-问题列表查询开始......");
		CategoryResponse response = categoryService.getHelpPage(contentHelpBeanRequest);
		return response;
	}

	@ApiOperation(value = "内容中心-帮助中心-列表页跳转详情页(含有id更新，不含有id添加)", notes = "帮助中心-问题列表页跳转详情页(含有id更新，不含有id添加)")
	@RequestMapping(value = "/helpinfoaction", method = RequestMethod.POST)
	public CategoryResponse helpInfo(@RequestBody ContentHelpBeanRequest contentHelpBeanRequest) {
		logger.info("查询内容中心-帮助中心-列表页跳转详情页(含有id更新，不含有id添加)查询开始......");
		CategoryResponse response = categoryService.getHelpInfo(contentHelpBeanRequest);
		return response;
	}

	@ApiOperation(value = "内容中心-帮助中心-添加问题", notes = "帮助中心-添加问题")
	@RequestMapping(value = "/helpaddaction", method = RequestMethod.POST)
	public CategoryResponse addHelp(@RequestBody ContentHelpBeanRequest contentHelpBeanRequest) {
		logger.info("查询内容中心-帮助中心-添加问题开始......");
		CategoryResponse response = categoryService.insertHelpInfo(contentHelpBeanRequest);
		return response;
	}

	@ApiOperation(value = "内容中心-帮助中心-修改问题", notes = "帮助中心-修改问题")
	@RequestMapping(value = "/helpupdateaction", method = RequestMethod.POST)
	public CategoryResponse updateHelpAction(@RequestBody ContentHelpBeanRequest contentHelpBeanRequest) {
		logger.info("查询内容中心-帮助中心-修改问题开始......");
		CategoryResponse response = categoryService.updateHelpAction(contentHelpBeanRequest);
		return response;
	}

	@ApiOperation(value = "内容中心-帮助中心-关闭问题", notes = "帮助中心-关闭问题")
	@RequestMapping(value = "/closehelpaction", method = RequestMethod.POST)
	public CategoryResponse closeHelpAction(@RequestBody ContentHelpBeanRequest contentHelpBeanRequest) {
		logger.info("查询内容中心-帮助中心-关闭问题开始......");
		CategoryResponse response = new CategoryResponse();
		if (null != contentHelpBeanRequest.getId()) {

			categoryService.chanContentHelp(contentHelpBeanRequest.getId(), 0, null);
			response = categoryService.getHelpPage(contentHelpBeanRequest);
		} else {
			response.setRtn(Response.ERROR);
			response.setMessage("请选择关闭的问题！");
		}
		return response;
	}

	@ApiOperation(value = "内容中心-帮助中心-开启问题", notes = "帮助中心-开启问题")
	@RequestMapping(value = "/openhelpaction", method = RequestMethod.POST)
	public CategoryResponse openHelpAction(@RequestBody ContentHelpBeanRequest contentHelpBeanRequest) {
		logger.info("查询内容中心-帮助中心-开启问题开始......");
		CategoryResponse response = new CategoryResponse();
		if (null != contentHelpBeanRequest.getId()) {

			categoryService.chanContentHelp(contentHelpBeanRequest.getId(), 1, null);
			response = categoryService.getHelpPage(contentHelpBeanRequest);
		} else {
			response.setRtn(Response.ERROR);
			response.setMessage("请选择开启的问题！");
		}
		return response;
	}

	@ApiOperation(value = "内容中心-帮助中心-删除问题", notes = "帮助中心-删除问题")
	@RequestMapping(value = "/delhelpaction", method = RequestMethod.POST)
	public CategoryResponse delHelpAction(@RequestBody ContentHelpBeanRequest contentHelpBeanRequest) {
		logger.info("查询内容中心-帮助中心-删除问题开始......");
		CategoryResponse response = new CategoryResponse();
		if (null != contentHelpBeanRequest.getId()) {

			categoryService.delContentHelp(contentHelpBeanRequest.getId());
			response = categoryService.getHelpPage(contentHelpBeanRequest);
		} else {
			response.setRtn(Response.ERROR);
			response.setMessage("请选择删除的问题！");
		}
		return response;
	}

	@ApiOperation(value = "内容中心-帮助中心-启用智齿常见问题", notes = "帮助中心-启用智齿常见问题")
	@RequestMapping(value = "/movezhichiaction", method = RequestMethod.POST)
	public CategoryResponse moveZhiChiAction(@RequestBody ContentHelpBeanRequest contentHelpBeanRequest) {
		logger.info("查询内容中心-帮助中心-启用智齿常见问题开始......");
		CategoryResponse response = new CategoryResponse();
		if (null != contentHelpBeanRequest.getId()) {
			categoryService.chanContentHelp(contentHelpBeanRequest.getId(), 1, 1);
			response = categoryService.getHelpPage(contentHelpBeanRequest);
		} else {
			response.setRtn(Response.ERROR);
			response.setMessage("请选择启用智齿常见问题！");
		}
		return response;
	}

	@ApiOperation(value = "内容中心-帮助中心-开启常见问题", notes = "帮助中心-开启常见问题")
	@RequestMapping(value = "/moveoftenaction", method = RequestMethod.POST)
	public CategoryResponse moveOftenAction(@RequestBody ContentHelpBeanRequest contentHelpBeanRequest) {
		logger.info("查询内容中心-帮助中心-开启常见问题开始......");
		CategoryResponse response = new CategoryResponse();
		if (null != contentHelpBeanRequest.getId()) {

			categoryService.chanContentHelp(contentHelpBeanRequest.getId(), 2, null);
			response = categoryService.getHelpPage(contentHelpBeanRequest);
		} else {
			response.setRtn(Response.ERROR);
			response.setMessage("请选择开启常见问题！");
		}
		return response;
	}

	@ApiOperation(value = "内容中心-帮助中心-问题列表", notes = "帮助中心-问题列表查询")
	@RequestMapping(value = "/ofteninit", method = RequestMethod.POST)
	public CategoryResponse oftenInit(@RequestBody ContentHelpBeanRequest contentHelpBeanRequest) {
		logger.info("查询内容中心-帮助中心-问题列表查询开始......");
		CategoryResponse response = categoryService.getOftenInitPage(contentHelpBeanRequest);
		return response;
	}
}
