package com.hyjf.admin.api;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.hyjf.admin.constant.ApiError;
import com.hyjf.common.exception.ServiceException;
import com.hyjf.ribbon.EurekaInvokeClient;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

/**
 * @author xiasq
 * @version ApiUtil, v0.1 2018/5/22 11:27
 */
public class ApiUtil {
	private static final Logger logger = LoggerFactory.getLogger(ApiUtil.class);
	private static RestTemplate restTemplate = EurekaInvokeClient.getInstance().buildRestTemplate();

	/**
	 * 微服务 post请求，如果请求失败，则抛出微服务请求异常，否则返回BODY
	 *
	 * @param url
	 * @param request
	 * @param responseType
	 * @param uriVariables
	 * @return
	 */
	public static <T> T postForEntity(String url, Object request, Class<T> responseType, Object... uriVariables) {
		ResponseEntity<T> response = null;
		try {
			response = restTemplate.postForEntity(url, request, responseType, uriVariables);
		} catch (RestClientException e) {
			logger.error(e.getMessage(), e);
			throw new ServiceException(ApiError.API_CALL_ERROR, e);
		}
		logger.debug(response.toString());
		if (response.getStatusCodeValue() == HttpStatus.OK.value()) {
			return response.getBody();
		} else {
			HttpStatus httpStatus = response.getStatusCode();
			logger.error("微服务 {} 调用异常,http status = {}:{}", url, httpStatus.value(), httpStatus.getReasonPhrase());
			throw new ServiceException(ApiError.API_CALL_ERROR);
		}
	}

	/**
	 * 微服务 post请求，如果请求失败，则抛出微服务请求异常，否则返回BODY
	 *
	 * @param url
	 * @param request
	 * @param responseType
	 * @param uriVariables
	 * @return
	 */
	public static <T> T postForEntityAsJson(String url, Object request, Class<T> responseType, Object... uriVariables) {
		HttpEntity<String> httpEntity = buildJsonHttpEntity(request);
		ResponseEntity<T> response = null;
		try {
			response = restTemplate.postForEntity(url, httpEntity, responseType, uriVariables);
		} catch (RestClientException e) {
			logger.error("微服务{}调用异常", url);
			logger.error(e.getMessage(), e);
			throw new ServiceException(ApiError.API_CALL_ERROR, e);
		}
		logger.debug(response.toString());
		if (response.getStatusCodeValue() == HttpStatus.OK.value()) {
			return response.getBody();
		} else {
			HttpStatus httpStatus = response.getStatusCode();
			logger.error("微服务 {} 调用异常,http status = {}:{}", url, httpStatus.value(), httpStatus.getReasonPhrase());
			throw new ServiceException(ApiError.API_CALL_ERROR);
		}
	}

	/**
	 * 微服务 GET请求，如果请求失败，则抛出微服务请求异常，否则返回BODY
	 *
	 * @param url
	 * @param responseType
	 * @param uriVariables
	 * @return
	 */
	public static <T> T getForEntity(String url, Class<T> responseType, Object... uriVariables) {
		ResponseEntity<T> response = null;
		try {
			response = restTemplate.getForEntity(url, responseType, uriVariables);
		} catch (RestClientException e) {
			logger.error("微服务{}调用异常", url);
			logger.error(e.getMessage(), e);
			throw new ServiceException(ApiError.API_CALL_ERROR, e);
		}
		logger.debug(response.toString());
		if (response.getStatusCodeValue() == HttpStatus.OK.value()) {
			return response.getBody();
		} else {
			HttpStatus httpStatus = response.getStatusCode();
			logger.error("微服务 {} 调用异常,http status = {}:{}", url, httpStatus.value(), httpStatus.getReasonPhrase());
			throw new ServiceException(ApiError.API_CALL_ERROR);
		}
	}

	/**
	 * 构建一个JSON http请求对象
	 *
	 * @param pojo
	 * @return
	 */
	private static HttpEntity<String> buildJsonHttpEntity(Object pojo) {
		HttpHeaders headers = new HttpHeaders();
		MediaType type = MediaType.parseMediaType(MediaType.APPLICATION_JSON_UTF8_VALUE);

		List<Charset> list = Arrays.asList(Charset.forName("utf-8"));
		headers.setContentType(type);
		headers.setAcceptCharset(list);
		headers.add("Accept", MediaType.APPLICATION_JSON.toString());

		String json = JSON.toJSONString(pojo);

		HttpEntity<String> httpEntity = new HttpEntity<String>(json, headers);
		return httpEntity;
	}

}
