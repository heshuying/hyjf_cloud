package com.hyjf.am.user.service.admin.membercentre.impl;

import com.hyjf.am.user.dao.model.auto.*;
import com.hyjf.am.user.dao.model.customize.AdminPreRegistListCustomize;
import com.hyjf.am.user.service.admin.membercentre.PreRegistService;
import com.hyjf.am.user.service.impl.BaseServiceImpl;
import com.hyjf.common.util.GetDate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;



@Service
public class PreRegistServiceImpl extends BaseServiceImpl implements PreRegistService {

    /**
     * 获取预注册条数
     * 
     * @param
     * @return
     * @author Administrator
     */

    @Override
    public int countRecordTotal(Map<String, Object> registUser) {
        return adminPreRegistCustomizeMapper.countRecordTotal(registUser);

    }
    
	/**
	 * 获取预注册数据列表
	 * 
	 * @return
	 */
	@Override
    public List<AdminPreRegistListCustomize> getRecordList(Map<String, Object> preRegistUserMap, int limitStart, int limitEnd) {
		// 封装查询条件
		if (limitStart == 0 || limitStart > 0) {
		    preRegistUserMap.put("limitStart", limitStart);
		}
		if (limitEnd > 0) {
		    preRegistUserMap.put("limitEnd", limitEnd);
		}
		// 查询用户列表
		List<AdminPreRegistListCustomize> preRegistUser = adminPreRegistCustomizeMapper.selectPreRegistList(preRegistUserMap);
		return preRegistUser;
	}
	
	/**
     * 获取预注册页面信息
     * 
     * @return
     */
	@Override
    public AdminPreRegistListCustomize getPreRegist(Integer id) {
	    Map<String, Object> preRegistUserMap = new HashMap<String, Object>();
	    preRegistUserMap.put("id", id);
        List<AdminPreRegistListCustomize> preRegist = adminPreRegistCustomizeMapper.selectPreRegistList(preRegistUserMap);
        if(preRegist!=null && preRegist.size()>0){
            AdminPreRegistListCustomize preRegist2 = preRegist.get(0);
            return preRegist2;
        }
        return null;
    }
	
    /**
     * 编辑保存预注册信息
     * 
     * @return
     */
    @Override
    public Map<String,Object> savePreRegist(AdminPreRegistListCustomize form) {
        Map<String,Object> resultMap = new HashMap<String,Object>();
        try{
            //String userId = ShiroUtil.getLoginUserId();
            List<User> usersList = null;
            if(!StringUtils.isEmpty(form.getId())){
                //校验推荐人
                if(!StringUtils.isEmpty(form.getReferrer())){
                    UserExample example = new UserExample();
                    UserExample.Criteria cra = example.createCriteria();
                    cra.andUsernameEqualTo(form.getReferrer());
                    usersList = userMapper.selectByExample(example);
                    if(usersList!=null && usersList.size()>0){
                        form.setReferrer(String.valueOf(usersList.get(0).getUserId()));
                    }else{
                        resultMap.put("success", false);
                        resultMap.put("msg", "推荐人不存在");
                        return resultMap;
                    }
                }
                //校验渠道
                if(!StringUtils.isEmpty(form.getSource())){
                    UtmPlatExample example = new UtmPlatExample();
                    UtmPlatExample.Criteria cra = example.createCriteria();
                    cra.andSourceNameEqualTo(form.getSource());
                    List<UtmPlat> utmPlatList = utmPlatMapper.selectByExample(example);
                    if(utmPlatList==null || utmPlatList.size()<1){
                        resultMap.put("success", false);
                        resultMap.put("msg", "渠道不存在");
                        return resultMap;
                    }
                }
                //校验关键字
                Utm utm = null;
                if(!StringUtils.isEmpty(form.getUtm())){
                    UtmExample example = new UtmExample();
                    UtmExample.Criteria cra = example.createCriteria();
                    cra.andUtmTermEqualTo(form.getUtm()).andUtmSourceEqualTo(form.getSource());
                    List<Utm> utmList = utmMapper.selectByExample(example);
                    if(utmList!=null && utmList.size()>0){
                        utm = utmList.get(0);
                    }else{
                        resultMap.put("success", false);
                        resultMap.put("msg", "渠道关键字不存在");
                        return resultMap;
                    }
                }
                PreRegist preRegist = new PreRegist();
                preRegist.setId(Integer.parseInt(form.getId()));
                preRegist.setReferrer(form.getReferrer());
                preRegist.setUtmId(utm.getUtmId()!=null?utm.getUtmId():null);
                preRegist.setSourceId(utm.getSourceId()!=null?utm.getSourceId():null);
                preRegist.setRemark(form.getRemark());
               // preRegist.setUpdateBy(Integer.parseInt(userId));
                preRegist.setUpdateTime(GetDate.getNowTime());
                preRegistMapper.updateByPrimaryKeySelective(preRegist);
                AdminPreRegistListCustomize preRegistCus = new AdminPreRegistListCustomize();
                preRegistCus.setId(form.getId());
                preRegistCus.setMobile(form.getMobile());
                preRegistCus.setReferrer(usersList!=null && usersList.size()>0?usersList.get(0).getUsername():null);
                preRegistCus.setUtm(utm.getUtmTerm());
                preRegistCus.setSource(utm.getUtmSource());
                preRegistCus.setRemark(form.getRemark());
                resultMap.put("preRegist", preRegistCus);
                resultMap.put("success", true);
                resultMap.put("msg", "保存完成");
            }else{
                resultMap.put("success", false);
                resultMap.put("msg", "无需要编辑的信息");
            }
        }catch(Exception e){
            logger.error(e.getMessage());
        }

        return resultMap;
    }

    /**
	 * 执行前每个方法前需要添加BusinessDesc描述
	 * 
	 * @return
	 * @author Administrator
	 */
	@Override
	public List<UtmPlat> getUtmPlagList() {
		List<UtmPlat> utmPlats = utmPlatMapper.selectByExample(new UtmPlatExample());
		return utmPlats;

	}


}
