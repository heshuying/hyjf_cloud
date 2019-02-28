package com.hyjf.zuul.provider;

import com.netflix.hystrix.exception.HystrixTimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 网关容错处理
 * @author dxj
 *
 */
@Component
public class ApiFallbackProvider implements FallbackProvider {
	
	private Logger logger = LoggerFactory.getLogger(ApiFallbackProvider.class);

	/**、
	 * 针对服务级别设置容错，这里是针对全部服务
	 */
	@Override
	public String getRoute() {
		return "*";
	}

	/**
	 * 容错的消息返回，这里区分是超时，还是服务挂掉了
	 */
	@Override
	public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
		logger.warn(String.format("route:%s,exceptionType:%s,message:%s", route, cause.getClass().getName(), cause.getMessage()));
        String message = "";
        if (cause instanceof HystrixTimeoutException) {
            message = "Timeout";
        } else {
            message = "Service exception";
        }
        return fallbackResponse(message);
	}
	
	public ClientHttpResponse fallbackResponse(String message) {

        return new ClientHttpResponse() {
            @Override
            public HttpStatus getStatusCode() throws IOException {
                return HttpStatus.OK;
            }

            @Override
            public int getRawStatusCode() throws IOException {
                return 200;
            }

            @Override
            public String getStatusText() throws IOException {
                return "OK";
            }

            @Override
            public void close() {

            }

            @Override
            public InputStream getBody() throws IOException {
                String bodyText = String.format("{\"status\": 99,\"statusDesc\": \"Service unavailable:%s\"}", message);
                return new ByteArrayInputStream(bodyText.getBytes());
            }

            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                return headers;
            }
        };
	}

}
