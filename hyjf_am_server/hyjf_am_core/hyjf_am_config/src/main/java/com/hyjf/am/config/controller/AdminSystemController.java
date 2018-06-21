package com.hyjf.am.config.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hyjf.am.config.dao.model.customize.AdminSystem;
import com.hyjf.am.config.dao.model.customize.Tree;
import com.hyjf.am.config.service.AdminSystemService;
import com.hyjf.am.response.config.AdminSystemResponse;
import com.hyjf.am.response.config.TreeResponse;
import com.hyjf.am.resquest.config.AdminSystemRequest;
import com.hyjf.am.vo.config.AdminSystemVO;
import com.hyjf.am.vo.config.TreeVO;
import com.hyjf.common.security.util.MD5;


@RestController
@RequestMapping("/am-config/adminSystem")
public class AdminSystemController {
	   @Autowired
	    private AdminSystemService adminSystemService;
    /**
     * 获取该用户菜单
     * @param userId
     * @return
     */
    @GetMapping("/selectLeftMenuTree/{userId}")
    public TreeResponse selectLeftMenuTree2(@PathVariable String userId){
    	
    	TreeResponse adminR=new TreeResponse();
        List<Tree> tree = adminSystemService.selectLeftMenuTree2(userId);
        if(null != tree){
        	List<TreeVO> treevo= new ArrayList<TreeVO>();
            BeanUtils.copyProperties(tree,treevo);
            adminR.setResultList(treevo);
        }
        return adminR;
    }
    /**
     * 登陆用户
     * @param AdminSystemRequest
     * @return
     */
    @GetMapping("/getuser")
    public AdminSystemResponse getuser(@RequestBody AdminSystemRequest adminSystemR){
    	AdminSystemResponse asr=new AdminSystemResponse();
		AdminSystem adminSystem = new AdminSystem();
		adminSystem.setUsername(adminSystemR.getUsername());
		adminSystem.setPassword(MD5.toMD5Code(adminSystemR.getPassword()));
		adminSystem.setState("NOT CHECK");
		adminSystem = adminSystemService.getUserInfo(adminSystem);
		if (adminSystem != null) {
			AdminSystemVO asv=new AdminSystemVO();
			//如果状态不可用
			if("1".equals(adminSystem.getState())) {
				asr.setMessage("该用户已禁用");
				return asr;
			}
			BeanUtils.copyProperties(adminSystem,asr);
			asr.setResult(asv);
			return asr;
		}else {
			asr.setMessage("用户名或者密码无效");
			return asr;
		}
         
    }
    /**
     * 登出
     * @param userId
     * @return
     */
    @GetMapping("/loginOut/{userId}")
	public AdminSystemResponse loginOut(@PathVariable String userId) {
		
		AdminSystemResponse asr=new AdminSystemResponse();
		return asr;
	}
    /**
     * 获取该用户权限
     * @param userId
     * @return
     */
    @GetMapping("/getpermissions/{userName}")
	public AdminSystemResponse getpermissions(@PathVariable String userName) {
		AdminSystem adminSystem = new AdminSystem();
        adminSystem.setUsername(userName);
		adminSystem = this.adminSystemService.getUserInfo(adminSystem);
		AdminSystemResponse asr=new AdminSystemResponse();
		List<AdminSystem> permissionsList = this.adminSystemService.getUserPermission(adminSystem);
		List<AdminSystemVO> adminVo= new ArrayList<AdminSystemVO>();
		BeanUtils.copyProperties(permissionsList,adminVo);
		asr.setResultList(adminVo);
		return asr;
	}
}
