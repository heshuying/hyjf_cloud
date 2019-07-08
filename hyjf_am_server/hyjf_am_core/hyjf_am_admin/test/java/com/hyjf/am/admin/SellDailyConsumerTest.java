package com.hyjf.am.admin;

import java.util.Date;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.MockitoRule;

import com.hyjf.am.admin.mq.consumer.SellDailyDataDto;
import com.hyjf.am.admin.mq.consumer.SellDailyDataHandler;
import com.hyjf.am.market.service.SellDailyService;
import com.hyjf.am.vo.market.SellDailyVO;

/**
 * @author xiasq
 * @version SellDailyConsumerTest, v0.1 2019-04-09 9:42
 */
@RunWith(MockitoJUnitRunner.class)
public class SellDailyConsumerTest {

	@Mock
	SellDailyDataHandler handler ;
	// = Mockito.mock(SellDailyDataHandler.class);
	//@Rule
	//public MockitoRule mockitoRule = MockitoJUnit.rule();

	@Mock
	SellDailyService sellDailyService;

	@Test()
	public void testRedis() {
		Date start = new Date();
		SellDailyDataDto dto = new SellDailyDataDto();
		dto.setQlSellDaily(new SellDailyVO());
		Mockito.when(handler.doHandler(1, start, start)).thenReturn(dto);

		SellDailyDataDto dto1 = handler.doHandler(1, start, start);

		Mockito.when(handler.doHandler(1, start, start)).thenThrow(new RuntimeException("error"));
		SellDailyDataDto dto2 = handler.doHandler(1, start, start);
		Mockito.doNothing().when(sellDailyService).update(new SellDailyVO());
		sellDailyService.update(new SellDailyVO());
	}
}
