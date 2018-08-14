/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.common.chatbot.dd;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * 给钉钉发送信息
 * @author liubin
 * @version DDChatbot, v0.1 2018/7/25 14:36
 */
public class DDChatbot {
    // java线上异常实时警告群 _ 线上异常实时警告
    public static String JAVA_PRODUCTION_ERR  = "https://oapi.dingtalk.com/robot/send?access_token=744070b426b968aa5ad0a24a5bdb1c8f3e6af98eab9f171eb8758495b80ae66d";

    public static void chatbotSend(String url, String json) throws Exception{

        HttpClient httpclient = HttpClients.createDefault();

        HttpPost httppost = new HttpPost(url);
        httppost.addHeader("Content-Type", "application/json; charset=utf-8");

        String textMsg = json;
        StringEntity se = new StringEntity(textMsg, "utf-8");
        httppost.setEntity(se);

        HttpResponse response = httpclient.execute(httppost);
        if (response.getStatusLine().getStatusCode()== HttpStatus.SC_OK){
            String result= EntityUtils.toString(response.getEntity(), "utf-8");
            System.out.println(result);
        }
    }
}
