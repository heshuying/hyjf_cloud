package com.hyjf.admin.api;

import com.hyjf.am.response.config.GatewayApiConfigResponse;
import com.hyjf.am.resquest.user.UserRequest;
import com.hyjf.am.vo.user.UserVO;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * @author xiasq
 * @version ApiUtilExample, v0.1 2018/5/24 16:53
 * ApiUtil 适应案例
 */
public class ApiUtilExample {

    /***
     * post方式调用例子1  对请求头有处理
     */
    public void testPostForEntity(){
        String url = "www.baidu.com";
        HttpHeaders headers = new HttpHeaders();
        headers.setAcceptCharset(Arrays.asList(StandardCharsets.UTF_8));
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);


        LinkedMultiValueMap<String,String> map = new LinkedMultiValueMap<>(3);
        map.add("mobilephone", "15311062331");
        map.add("idCard", "333333333333333333333");
        HttpEntity<LinkedMultiValueMap<String,String>> httpEntity = new HttpEntity<>(map, headers);

        String response1 = ApiUtil.postForEntity(url, httpEntity, String.class);

    }

    /***
     * post方式调用例子2  普通使用
     */
    public void testPostForEntityAsJson(){
        String url = "www.baidu.com";
        UserRequest request= new UserRequest();
        String response1 = ApiUtil.postForEntityAsJson(url, request, String.class);

    }

    /***
     * get方式调用例子
     */
    public void testGetForEntity(){
        String url = "http://AM-USER/am-user/user/findUserByMobile/{mobile}";
        UserVO response = ApiUtil.getForEntity(url, UserVO.class, "15311062331");

    }
}
