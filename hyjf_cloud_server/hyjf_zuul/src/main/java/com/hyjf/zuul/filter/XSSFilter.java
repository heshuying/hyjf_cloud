package com.hyjf.zuul.filter;

import com.hyjf.zuul.contant.GatewayConstant;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.netflix.zuul.http.ServletInputStreamWrapper;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.MimeHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.util.StreamUtils;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.Enumeration;

/**
 * @author xiasq
 * @version XSSFilter, v0.1 2018/11/9 14:25
 */
public class XSSFilter extends ZuulFilter {
    private static Logger logger = LoggerFactory.getLogger(XSSFilter.class);

    @Override
    public String filterType() {
        return GatewayConstant.PRE;
    }

    @Override
    public int filterOrder() {
        return FilterConstants.PRE_DECORATION_FILTER_ORDER + 3;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();


        // xss只针对web请求
        String originalRequestPath = ctx.get(FilterConstants.REQUEST_URI_KEY).toString();
        if (originalRequestPath.contains(GatewayConstant.WEB_CHANNEL)) {
            logger.info("xss过滤开始, request uri is : {}", originalRequestPath);
            try {
                InputStream in = request.getInputStream();
                String body = StreamUtils.copyToString(in, Charset.forName("UTF-8"));

                // 过滤body属性名和属性值
                if (StringUtils.isBlank(body)) {
                    body = "{}";
                } else {
                    body = this.cleanXSS(URLDecoder.decode(body, "UTF-8"));
                }

                // 过滤header属性名和属性值
                Enumeration<String> headers = request.getHeaderNames();
                while (headers.hasMoreElements()) {
                    String headerName = cleanXSS(headers.nextElement());
                    String headerValue = cleanXSS(request.getHeader(headerName));
                    this.reflectSetParam(request, headerName, headerValue);
                }

                final byte[] reqBodyBytes = body.getBytes();
                ctx.setRequest(new XssHttpServletRequestWrapper(request, reqBodyBytes));
            } catch (IOException e) {
                logger.error("xss过滤失败...", e);
            }
        }
        return null;
    }

    /**
     * 修改header信息，key-value键值对儿加入到header中
     *
     * @param request
     * @param key
     * @param value
     */
    private void reflectSetParam(HttpServletRequest request, String key, String value) {
        Class<? extends HttpServletRequest> requestClass = request.getClass();
        try {
            // logger.info("request实现类: {}", requestClass.getName());
            // org.springframework.cloud.netflix.zuul.filters.pre.Servlet30RequestWrapper
            Field request1 = requestClass.getDeclaredField("request");
            request1.setAccessible(true);
            Object o1 = request1.get(request);

            // logger.info("request1实现类: {}", o1.getClass().getName());
            // org.apache.catalina.connector.RequestFacade
            Field request2 = o1.getClass().getDeclaredField("request");
            request2.setAccessible(true);
            Object o2 = request2.get(o1);

            // logger.info("request2实现类: {}", o2.getClass().getName());
            Field coyoteRequest = o2.getClass().getDeclaredField("coyoteRequest");
            coyoteRequest.setAccessible(true);
            Object o3 = coyoteRequest.get(o2);

            // logger.info("coyoteRequest实现类=" + o3.getClass().getName());
            Field headers = o3.getClass().getDeclaredField("headers");
            headers.setAccessible(true);
            MimeHeaders mimeHeaders = (MimeHeaders) headers.get(o3);
            mimeHeaders.setValue(key).setString(value);
        } catch (Exception e) {
            logger.error("xss反射赋值错误...", e);
        }
    }

    /**
     * 滤器过滤内部类
     */
    private class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {

        private byte[] reqBodyBytes;

        // 构造方法
        public XssHttpServletRequestWrapper(HttpServletRequest servletRequest, byte[] reqBodyBytes) {
            super(servletRequest);
            this.reqBodyBytes = reqBodyBytes;
        }

        @Override
        public ServletInputStream getInputStream() throws IOException {
            return new ServletInputStreamWrapper(reqBodyBytes);
        }

        @Override
        public int getContentLength() {
            return reqBodyBytes.length;
        }

        @Override
        public long getContentLengthLong() {
            return reqBodyBytes.length;
        }
    }


    /**
     * 替换相应的非法字符
     *
     * @param value
     * @return
     */
    private String cleanXSS(String value) {

        if (StringUtils.isBlank(value)) {
            return value;
        }

        // 采用spring的StringEscapeUtils工具类实现
        StringEscapeUtils.escapeHtml(value);
        StringEscapeUtils.escapeJavaScript(value);
        StringEscapeUtils.escapeSql(value);

        //原来XSS处理方式， 保留
        value = value.replaceAll("<", "").replaceAll(">", "");
        value = value.replaceAll("&lt;", "").replaceAll("&gt;", "");
        value = value.replaceAll("& lt;", "").replaceAll("& gt;", "");
        value = value.replaceAll("\\(", "").replaceAll("\\)", "");
        value = value.replaceAll("&#40;", "").replaceAll("&#41;", "");
        value = value.replaceAll("& #40;", "").replaceAll("& #41;", "");
        value = value.replaceAll("'", "");
        value = value.replaceAll("&#39;", "");
        value = value.replaceAll("& #39;", "");
        value = value.replaceAll("eval\\((.*)\\)", "");
        value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "");
        value = value.replaceAll("script", "");
        value = value.replaceAll("<(no)?script[^>]*>.*?</(no)?script>", "");
        value = value.replaceAll("eval\\((.*?)\\)", "");
        value = value.replaceAll("expression\\((.*?)\\)", "");
        value = value.replaceAll("(javascript:|vbscript:|view-source:)*", "");
        value = value.replaceAll("<(\"[^\"]*\"|\'[^\']*\'|[^\'\">])*>", "");
        value = value.replaceAll("(window\\.location|window\\.|\\.location|document\\.cookie|document\\.|alert\\(.*?\\)|window\\.open\\()*", "");
        value = value.replaceAll(
                "<+\\s*\\w*\\s*(oncontrolselect|oncopy|oncut|ondataavailable|ondatasetchanged|ondatasetcomplete|ondblclick|ondeactivate|ondrag|ondragend"
                        + "|ondragenter|ondragleave|ondragover|ondragstart|ondrop|onerror=|onerroupdate|onfilterchange|onfinish|onfocus|onfocusin|onfocusout|onhelp"
                        + "|onkeydown|onkeypress|onkeyup|onlayoutcomplete|onload|onlosecapture|onmousedown|onmouseenter|onmouseleave|onmousemove|onmousout|onmouseover"
                        + "|onmouseup|onmousewheel|onmove|onmoveend|onmovestart|onabort|onactivate|onafterprint|onafterupdate|onbefore|onbeforeactivate|onbeforecopy"
                        + "|onbeforecut|onbeforedeactivate|onbeforeeditocus|onbeforepaste|onbeforeprint|onbeforeunload|onbeforeupdate|onblur|onbounce|oncellchange"
                        + "|onchange|onclick|oncontextmenu|onpaste|onpropertychange|onreadystatechange|onreset|onresize|onresizend|onresizestart|onrowenter|onrowexit"
                        + "|onrowsdelete|onrowsinserted|onscroll|onselect|onselectionchange|onselectstart|onstart|onstop|onsubmit|onunload)+\\s*=+",
                "");

        return value;
    }
}
