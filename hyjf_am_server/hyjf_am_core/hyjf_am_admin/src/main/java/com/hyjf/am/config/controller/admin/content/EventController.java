/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.controller.admin.content;

import com.hyjf.am.config.controller.BaseConfigController;
import com.hyjf.am.config.dao.model.auto.Event;
import com.hyjf.am.config.service.EventService;
import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.response.config.EventResponse;
import com.hyjf.am.resquest.admin.EventsRequest;
import com.hyjf.am.vo.config.EventVO;
import com.hyjf.common.util.CommonUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 公司纪事
 * 
 * @author fuqiang
 * @version EventController, v0.1 2018/7/12 9:07
 */
@RestController
@RequestMapping("/am-config/content/contentevent")
public class EventController extends BaseConfigController {
	@Autowired
	private EventService eventService;

	/**
	 * 根据条件查询公司管理-公司纪事
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/searchaction")
	public EventResponse searchAction(@RequestBody EventsRequest request) {
		logger.info("查询公司管理-公司纪事开始......");
		EventResponse response = new EventResponse();
		List<Event> list = eventService.searchAction(request);
		if (!CollectionUtils.isEmpty(list)) {
			List<EventVO> voList = CommonUtils.convertBeanList(list, EventVO.class);
			response.setResultList(voList);
		}
		// 查询符合条件的条数
		int count = eventService.selectCount(request);
		response.setCount(count);
		return response;
	}

	/**
	 * 添加公司管理-公司纪事
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/insertaction")
	public IntegerResponse insertAction(@RequestBody EventsRequest request) {
		int num = eventService.insertAction(request);
		return new IntegerResponse(num);
	}

	/**
	 * 修改公司管理-公司纪事
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/updateaction")
	public IntegerResponse updateAction(@RequestBody EventsRequest request) {
		int num = eventService.updateAction(request);
		return new IntegerResponse(num);
	}

	/**
	 * 根据id查询公司管理-公司纪事
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping("/getrecord/{id}")
	public EventResponse getRecord(@PathVariable Integer id) {
		EventResponse response = new EventResponse();
		Event event = eventService.getRecord(id);
		if (event != null) {
			EventVO vo = new EventVO();
			BeanUtils.copyProperties(event, vo);
			response.setResult(vo);
		}
		return response;
	}

	/**
	 * 根据id查询公司管理-公司纪事
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete/{id}")
	public IntegerResponse delete(@PathVariable Integer id) {
		int num = eventService.deleteById(id);
		return new IntegerResponse(num);
	}

	/**
	 * 获取公司纪事
	 * @param begin
	 * @param end
	 * @param year
	 * @return
	 */
	@GetMapping("/getEvents/{begin}/{end}/{year}")
	public EventResponse getEvents(@PathVariable int begin, @PathVariable int end, @PathVariable int year){
		EventResponse eventResponse = new EventResponse();
		List<Event> events = eventService.getEvents(begin, end, year);
		List<EventVO> eventsVO = CommonUtils.convertBeanList(events, EventVO.class);
		eventResponse.setResultList(eventsVO);
		return eventResponse;
	}

	/**
	 * 根据纪事ID 获取纪事内容
	 * @param id
	 * @return
	 * @Author : huanghui
	 */
	@ApiOperation(value = "根据ID获取公司纪事详情", notes = "信息披露 - 公司历程")
	@RequestMapping(value = "/getEventDetail/{id}", method = RequestMethod.GET)
	public EventResponse getEventDetail(@PathVariable Integer id) {
		EventResponse response = new EventResponse();
		Event event = eventService.getRecord(id);

		// 数据 存在
        if (event != null) {
            EventVO eventVO = new EventVO();
            BeanUtils.copyProperties(event, eventVO);
            //设置返回结果
            response.setResult(eventVO);
        }
        return response;
	}


}
