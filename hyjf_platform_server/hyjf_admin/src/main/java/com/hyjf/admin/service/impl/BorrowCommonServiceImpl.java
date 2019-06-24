package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.AmConfigClient;
import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.client.AmUserClient;
import com.hyjf.admin.service.BorrowCommonService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.BorrowCommonResponse;
import com.hyjf.am.response.admin.BorrowCustomizeResponse;
import com.hyjf.am.response.config.AdminSystemResponse;
import com.hyjf.am.response.config.LinkResponse;
import com.hyjf.am.resquest.admin.BorrowBeanRequest;
import com.hyjf.am.resquest.admin.BorrowCommonRequest;
import com.hyjf.am.vo.config.ParamNameVO;
import com.hyjf.am.vo.task.autoreview.BorrowCommonCustomizeVO;
import com.hyjf.am.vo.trade.borrow.BorrowCommonVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author GOGTZ-Z
 * @version V1.0  
 * @package com.hyjf.admin.maintenance.AdminPermissions
 * @date 2015/07/09 17:00
 */
@Service
public class BorrowCommonServiceImpl implements BorrowCommonService{
    @Autowired
    private AmTradeClient amTradeClient;
    @Autowired
    private AmConfigClient amConfigClient;
    @Autowired
    private AmUserClient amUserClient;

	@Override
	public BorrowCommonResponse moveToInfoAction(BorrowCommonRequest borrowCommonRequest) {
		
		return amTradeClient.moveToInfoAction(borrowCommonRequest);
	}

	@Override
	public BorrowCommonResponse insertAction(BorrowCommonRequest borrowCommonRequest) throws Exception {
		return amTradeClient.insertAction(borrowCommonRequest);
	}

	@Override
	public LinkResponse getLinks() {
	
		return amConfigClient.getLinks();
	}

	@Override
	public int isExistsUser(String userId) {

		return amUserClient.isExistsUser(userId);
	}

	@Override
	public AdminSystemResponse isExistsApplicant(String applicant) {
		return amConfigClient.isExistsApplicant(applicant);
	}

	@Override
	public String getBorrowPreNid() {
		return amTradeClient.getBorrowPreNid();
	}

	@Override
	public String getXJDBorrowPreNid() {

		return amTradeClient.getXJDBorrowPreNid();
	}

	@Override
	public boolean isExistsBorrowPreNidRecord(String borrowPreNid) {
		return amTradeClient.isExistsBorrowPreNidRecord(borrowPreNid);
	}

	@Override
	public BorrowCommonVO getBorrowServiceScale(BorrowCommonRequest borrowCommonRequest) {

		return amTradeClient.getBorrowServiceScale(borrowCommonRequest);
	}

	@Override
	public BorrowCommonResponse getProductTypeAction(String instCode) {
		return amTradeClient.getProductTypeAction(instCode);
	}

	@Override
	public int isEntrustedExistsUser(String userName) {
		return amTradeClient.isEntrustedExistsUser(userName);
	}

	@Override
	public List<UserVO> selectUserByUsername(String repayOrgName) {
		return amUserClient.searchUserByUsername(repayOrgName);
	}

	@Override
	public UserInfoVO findUserInfoById(int userId) {
		return amUserClient.findUserInfoById(userId);
	}


	@Override
	public boolean isBorrowUserCACheck(String name) {
	
		return Response.isSuccess(amUserClient.selectCertificateAuthorityByCAName( name));
	}

	@Override
	public boolean isCAIdNoCheck(String param, String name) {
		return Response.isSuccess(amUserClient.isCAIdNoCheck(param, name));
	}

	@Override
	public BorrowCustomizeResponse init(BorrowBeanRequest form) {
		return amTradeClient.selectBorrowAllList(form);
	}

	@Override
	public BorrowCustomizeResponse exportBorrowList(BorrowBeanRequest borrowCommonCustomize) {
		return amTradeClient.exportBorrowList(borrowCommonCustomize);
	}

	@Override
	public UserVO getUserByUserName(String userName) {
		return amUserClient.getUserByUserName(userName);
	}

	/**
	 * list 分页
	 * @param request
	 * @param result
	 * @return
	 */
	@Override
	public List<BorrowCommonCustomizeVO> paging(BorrowBeanRequest request, List<BorrowCommonCustomizeVO> result){
		int current=request.getCurrPage(); //页码
		int pageSize=request.getPageSize(); //每页显示的数量
		int totalCount=result.size();
		int pageCount = (totalCount / pageSize) + ((totalCount % pageSize > 0) ? 1 : 0);

		if(current < 1){
			current = 1;
		}
		int start=(current-1) * pageSize;
		int end = Math.min(totalCount, current * pageSize);

		if(pageCount >= current){
			result=result.subList(start,end);
		}else{
			result = new ArrayList<>();
		}

		return result;
	}

	/**
	 * 获取标的投资风险测评等级
	 *
	 * @param borrowLevel
	 * @return
	 */
	@Override
	public String getBorrowLevelAction(@Valid String borrowLevel) {
		String investLevel = this.amTradeClient.getBorrowLevelAction(borrowLevel);
		return investLevel;
	}

	/**
	 * map转ParamNameVO
	 *
	 * @param map
	 * @return
	 */
	@Override
	public List<ParamNameVO> mapToParamNameVO(Map<String, String> map) {
		List<ParamNameVO> paramNameVOList = new ArrayList<>();
		//遍历map中的键
		for (String key : map.keySet()) {
			ParamNameVO paramNameVO = new ParamNameVO();
			paramNameVO.setNameCd(key);
			paramNameVO.setName(StringUtils.isNotBlank(map.get(key)) ? map.get(key) : "" );
			paramNameVOList.add(paramNameVO);
		}
		return paramNameVOList;
	}
}