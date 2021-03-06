package com.hyjf.am.trade.controller.admin.hjhplan;

import com.hyjf.am.response.admin.HjhAssetTypeResponse;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.auto.HjhAssetType;
import com.hyjf.am.trade.service.admin.hjhplan.HjhAssetTypeService;
import com.hyjf.am.vo.admin.HjhAssetTypeVO;
import com.hyjf.common.util.CommonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author libin
 * @version HjhAssetTypeController
 */
@Api(value = "根据机构编号检索产品类型")
@RestController
@RequestMapping("/am-trade/hjhAssetType")
public class HjhAssetTypeController extends BaseController {
	
	@Autowired
	HjhAssetTypeService hjhAssetTypeService;
	
	/**
	 * @Author: libin
	 * @Desc :根据机构编号检索产品类型
	 */
	@ApiOperation(value = " 根据机构编号检索产品类型")
	@GetMapping("/selectAssetTypeAll/{instCodeSrch}")
	public HjhAssetTypeResponse selectAssetTypeAll(@PathVariable(value = "instCodeSrch") String instCodeSrch) {
		HjhAssetTypeResponse response = new HjhAssetTypeResponse();
		//代表成功
		String returnCode = "0";
		List<HjhAssetType> hjhAssetTypeList = hjhAssetTypeService.selectAssetTypeAll(instCodeSrch);
        if (null!=hjhAssetTypeList&&hjhAssetTypeList.size()>0) {
            List<HjhAssetTypeVO> hjhAssetTypeVO = CommonUtils.convertBeanList(hjhAssetTypeList,HjhAssetTypeVO.class);
            response.setResultList(hjhAssetTypeVO);
			response.setRtn(returnCode);
        }
        return response;
	}
}
