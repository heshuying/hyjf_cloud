package com.hyjf.am.market.controller;

import com.hyjf.am.market.service.DuibaOrderListService;
import com.hyjf.am.vo.admin.DuibaOrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wenxin
 * @version DduibaOrderListController, v0.1 2019/6/10
 */

@RestController
@RequestMapping("/am-market/duiba")
public class DduibaOrderListController {
	@Autowired
	private DuibaOrderListService duibaOrderListService;

	@RequestMapping("/selectOrderByOrderId/{duibaOrderId}")
	public DuibaOrderVO selectOrderByOrderId(@PathVariable String duibaOrderId) {
		return duibaOrderListService.selectOrderByOrderId(duibaOrderId);
	}

	@RequestMapping("/updateOneOrderByPrimaryKey")
	public Integer updateOneOrderByPrimaryKey(@RequestBody DuibaOrderVO duibaOrderVO) {
		return duibaOrderListService.updateOneOrderByPrimaryKey(duibaOrderVO);
	}

}
