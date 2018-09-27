package com.hyjf.am.trade.controller.front.hjh;

import com.hyjf.am.response.user.HjhInstConfigResponse;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.auto.HjhInstConfig;
import com.hyjf.am.trade.service.front.hjh.HjhInstConfigService;
import com.hyjf.am.vo.user.HjhInstConfigVO;
import com.hyjf.common.util.CommonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author xiasq
 * @version HjhInstConfigController, v0.1 2018/6/15 17:30
 */
@Api(value = "根据机构编号检索机构信息")
@RestController
@RequestMapping("/am-trade/hjhInstConfig")
public class HjhInstConfigController extends BaseController {

	@Autowired
	HjhInstConfigService hjhInstConfigService;

	/**
	 * @Author: zhangqingqing
	 * @Desc :根据机构编号检索机构信息
	 * @Param: * @param instCode
	 * @Date: 9:00 2018/5/31
	 * @Return: com.hyjf.am.response.user.HjhInstConfigResponse
	 */
	@ApiOperation(value = " 根据机构编号检索机构信息")
	@GetMapping("/selectInstConfigByInstCode/{instCode}")
	public HjhInstConfigResponse selectInstConfigByInstCode(@PathVariable(value = "instCode") String instCode) {
		HjhInstConfigResponse response = new HjhInstConfigResponse();
		HjhInstConfig hjhInstConfig = hjhInstConfigService.selectInstConfigByInstCode(instCode);
		if (null != hjhInstConfig) {
			HjhInstConfigVO hjhInstConfigVO = new HjhInstConfigVO();
			BeanUtils.copyProperties(hjhInstConfig, hjhInstConfigVO);
			response.setResult(hjhInstConfigVO);
		}
		return response;
	}

    /**
     * 获取所有的机构信息
     * @return
     */
	@ApiOperation(value = " 根据机构编号检索机构列表")
	@GetMapping("/selectInstConfigAll")
	public HjhInstConfigResponse selectInstConfigListByInstCode(){
        HjhInstConfigResponse response = new HjhInstConfigResponse();
		String returnCode = "0";//代表成功
        List<HjhInstConfig> hjhInstConfigList = hjhInstConfigService.selectInstConfigAll();
        if (null!=hjhInstConfigList&&hjhInstConfigList.size()>0) {
            List<HjhInstConfigVO> hjhInstConfigVO = CommonUtils.convertBeanList(hjhInstConfigList,HjhInstConfigVO.class);
            response.setResultList(hjhInstConfigVO);
			response.setRtn(returnCode);
        }
        return response;
    }

}
