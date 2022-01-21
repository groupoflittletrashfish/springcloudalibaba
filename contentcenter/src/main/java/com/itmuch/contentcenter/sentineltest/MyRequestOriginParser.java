package com.itmuch.contentcenter.sentineltest;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.RequestOriginParser;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 这个代码的意思是检查请求的参数中是否存在origin这个参数，如果没有则报错。当然在实际的开发中可以放入请求头中。
 * 那么在后续的请求中这个origin的值将会代码它的来源
 * @author ：liwuming
 * @date ：Created in 2022/1/21 11:32
 * @description ：
 * @modified By：
 * @version:
 */

@Component
public class MyRequestOriginParser implements RequestOriginParser {
    @Override
    public String parseOrigin(HttpServletRequest httpServletRequest) {
        String origin = httpServletRequest.getParameter("origin");
        if (StringUtils.isBlank(origin)) {
            throw new IllegalArgumentException("origin can not be null");
        }
        return origin;
    }
}
