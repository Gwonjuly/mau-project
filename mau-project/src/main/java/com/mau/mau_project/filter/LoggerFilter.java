package com.mau.mau_project.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;

@Component
@Slf4j
public class LoggerFilter implements Filter {


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        var req = new ContentCachingRequestWrapper((HttpServletRequest) servletRequest);
        var res = new ContentCachingResponseWrapper((HttpServletResponse) servletResponse);

        log.info("INIT URI: {}", req.getRequestURI());

        filterChain.doFilter(req, res);

        var headerNames = req.getHeaderNames();
        var headerValues = new StringBuilder();

        headerNames.asIterator().forEachRemaining(headerKey -> {
            var headerValue = req.getHeader(headerKey);
            headerValues.append("(").append(headerKey).append(":").append(headerValue).append(")").append(",");
        });

        var requestBody = new String(req.getContentAsByteArray());
        var uri = req.getRequestURI();
        var method = req.getMethod();

        log.info(">>>>> uri: {}, method: {},header: {}, body:{}", uri, method, headerValues, requestBody);

        var responseHeaderValues = new StringBuilder();

        res.getHeaderNames().forEach(headerKey->{
            var headerValue=res.getHeader(headerKey);
            responseHeaderValues.append("(").append(headerKey).append(":").append(headerValue).append(")").append(",");
        });

        var responseBody=new String(res.getContentAsByteArray());
        log.info("<<<<< uri:{} , method: {}, header:{},body:{}", uri, method, responseHeaderValues,responseBody);

        res.copyBodyToResponse();
    }
}
