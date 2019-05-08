/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller;

import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.service.LoginService;
import com.hyjf.admin.utils.PictureInitUtil;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.config.AdminSystemResponse;
import com.hyjf.am.resquest.config.AdminSystemRequest;
import com.hyjf.am.vo.config.AdminSystemVO;
import com.hyjf.am.vo.config.TreeVO;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;
import java.util.List;

/**
 * @author DongZeShan
 * @version LoginController.java, v0.1 2018年6月15日 上午9:32:30
 */
@Api(value = "admin登陆相关",tags ="admin登陆相关")
@RestController
@RequestMapping("/hyjf-admin/login")
public class LoginController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	@Autowired
	private LoginService loginService;

	private static final String key="key";

	@Value("${file.domain.url}")
	private String FILEDOMAINURL;

	@Value("${hyjf.web.pdf.host}")
	private String WEBURL;

    @Value("${hyjf.env.test}")
    private boolean hyjfEnvTest;

    /**
     * @Author: dongzeshan
     * @Desc :admin登陆验证密码
     * @Param: * @param map
     * @Date: 16:43 2018/6/15
     * @Return: JSONObject
     */
    @ApiOperation(value = "admin登陆验证密码", notes = "admin登陆验证密码")
    @PostMapping(value = "/login")
	@ResponseBody
//	@AuthorityAnnotation(key=key, value = ShiroConstants.PERMISSION_AUTH)
	public AdminResult<Map<String,Object>> login(HttpServletRequest request, @ApiParam(required = true, value = "{\"username\": \"\",\"password\":\"\"}") @RequestBody Map<String, String> map) {
		String username=map.get("username");
		logger.info("登陆开始用户:"+username);
		String password=map.get("password");
        String captcha =map.get("code");
        Long loginCaptcha = (Long) request.getSession().getAttribute("LoginCaptcha");
        // 测试环境不进行验证
        if (!hyjfEnvTest) {
            if (captcha == null || captcha.isEmpty() || loginCaptcha == null
                    || !loginCaptcha.toString().equals(captcha)) {
                return new AdminResult<>(FAIL, "验证码错误");
            }
        }
		AdminSystemRequest adminSystemRequest=new AdminSystemRequest();
		adminSystemRequest.setUsername(username);
		adminSystemRequest.setPassword(password);
		AdminSystemResponse prs = loginService.getUserInfo(adminSystemRequest);
		if(!Response.isSuccess(prs)) {
			return new AdminResult<>(FAIL, prs.getMessage());
		}
		String uuid=UUID.randomUUID().toString();
		// 1. 登录成功将登陆密码错误次数的key删除
		RedisUtils.del(RedisConstants.PASSWORD_ERR_COUNT_ADMIN + username);
		if (!hyjfEnvTest) {
			// 生产环境   单点登录
			RedisUtils.set(RedisConstants.ADMIN_UNIQUE_ID+username, uuid, 3600);
		}
		this.setUser(request, prs.getResult());
		Map<String,Object> result =new HashMap<String, Object>();;
		result.put("uuid", uuid);
		result.put("user", prs.getResult());
		result.put("webUrl", WEBURL);
		result.put("fileUrl", FILEDOMAINURL);
		return new AdminResult<Map<String,Object>>(result);
	}
    /**
     * @Author: dongzeshan
     * @Desc :获取菜单
     * @Param: * @param map
     * @Date: 16:43 2018/6/15
     * @Return: JSONObject
     */
    @ApiOperation(value = "admin获取菜单", notes = "admin获取菜单")
    @PostMapping(value = "/getMenuTree")
	@ResponseBody
	public AdminResult<ListResult<TreeVO>> getMenuTree(HttpServletRequest request) {
		List<TreeVO> prs = loginService.selectLeftMenuTree2(this.getUser(request).getId());
		return new  AdminResult<ListResult<TreeVO>>(ListResult.build(prs, 0));
	}
    /**
     * @Author: dongzeshan
     * @Desc :获取权限列表
     * @Param: * 
     * @Date: 16:43 2018/6/15
     * @Return: JSONObject
     */
    @ApiOperation(value = "admin获取权限", notes = "admin获取权限")
    @PostMapping(value = "/getUserPermission")
	@ResponseBody
	public AdminResult<Map<String,Object>> getUserPermission(HttpServletRequest request, HttpServletResponse response) {
    	Map<String,Object> permission =new HashMap<String, Object>();
		 List<AdminSystemVO> prs = loginService.getUserPermission(this.getUser(request).getUsername());
		 List<String> perm = new ArrayList<String>();
		 String key=null;
		 StringBuilder val=new StringBuilder();
		 for (AdminSystemVO adminSystemVO : prs) {
				if (adminSystemVO != null) {
					if(key==null) {
						key=adminSystemVO.getMenuCtrl();
						val.append(adminSystemVO.getPermission());
					}
					if(key.equals(adminSystemVO.getMenuCtrl())) {
						val.append(",");
						val.append(adminSystemVO.getPermission());
					}
					if(!key.equals(adminSystemVO.getMenuCtrl())) {
						permission.put(key, val.toString().split(","));
			
						key=adminSystemVO.getMenuCtrl();
						val=new StringBuilder();
						val.append(adminSystemVO.getPermission());
					}
					perm.add(adminSystemVO.getMenuCtrl()+":"+adminSystemVO.getPermission());
				}
		}
		 if(key!=null) {
				permission.put(key, val.toString().split(",")); 
		 }
		request.getSession().setAttribute("permission", perm);
		return new AdminResult<Map<String,Object>>(permission);
	}
    /**
     * 验证码
     * @param response
     * @param request
     */
    @ApiOperation(value = "admin获取验证码", notes = "admin获取验证码")
    @GetMapping(value = "/getPicture")
	@ResponseBody
    public void getPicture(HttpServletResponse response, HttpServletRequest request) {
        // 设置不缓存图片
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "No-cache");
        response.setDateHeader("Expires", 0);
        // 指定生成的相应图片
        response.setContentType("image/jpeg;charset=UTF-8");
        PictureInitUtil pu = new PictureInitUtil();
        BufferedImage image = new BufferedImage(pu.getWidth(), pu.getHeight(),
                BufferedImage.TYPE_INT_BGR);
        Graphics2D g = image.createGraphics();
        // 定义字体样式
        Font myFont = new Font("Times New Roman", Font.BOLD, 16);
        // 设置字体
        g.setFont(myFont);

        g.setColor(pu.getRandomColor(200, 250));
        // 绘制背景
        g.fillRect(0, 0, pu.getWidth(), pu.getHeight());

        g.setColor(pu.getRandomColor(180, 200));
        pu.drawRandomLines(g, 160);
        // 将验证码放入会话中
        Long r = pu.drawRandomLong(10L, g);
        HttpSession session = request.getSession();
        session.setAttribute("LoginCaptcha", r);
        g.dispose();
        try {
            ImageIO.write(image, "JPEG", response.getOutputStream());
        } catch (IOException e) {
        }
    }
    /**
     * @Author: dongzeshan
     * @Desc :admin登出
     * @Param: * @param map
     * @Date: 16:43 2018/6/15
     * @Return: JSONObject
     */
    @ApiOperation(value = "admin登出", notes = "admin登出")
    @PostMapping(value = "/loginOut")
	@ResponseBody
	public AdminResult loginOut(HttpServletRequest request) {
		request.getSession().removeAttribute(BaseController.USER);
		return new AdminResult<>();
		
    }
    /**
     * @Author: dongzeshan
     * @Desc :修改密码
     * @Param: * @param map
     * @Date: 16:43 2018/6/15
     * @Return: JSONObject
     */
    @ApiOperation(value = "修改密码", notes = "修改密码")
    @PostMapping(value = "/updatePassword")
	@ResponseBody
	public AdminResult updatePasswordAction(@RequestBody AdminSystemRequest map) {
    	 AdminSystemResponse ar = loginService.updatePasswordAction(map);
    	 if(!AdminSystemResponse.isSuccess(ar)) {
    		 return new AdminResult<>(BaseController.FAIL, ar.getMessage());
    	 }
		return new AdminResult<>();
		
    }
}
