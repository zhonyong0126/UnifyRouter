package com.feture.learnfilter.filter;

import com.feture.learnfilter.consts.RequestConst;
import com.feture.learnfilter.model.OpenApiRequest;
import com.feture.learnfilter.service.RequestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
@Order(1)
public class UnifyRouteFilter implements Filter {

    private static Logger logger = LoggerFactory.getLogger(UnifyRouteFilter.class);

    @Autowired
    private RequestService requestService;

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        OpenApiRequest openApiRequest = null;
        try {
            if (request instanceof HttpServletRequest && ((HttpServletRequest) request).getRequestURI().contains(RequestConst.RouteDispatchPrefix) && HttpMethod.POST.toString().equals(((HttpServletRequest) request).getMethod())) {
                RewriteHttpBodyRequest requestWrapper = new RewriteHttpBodyRequest((HttpServletRequest) request);
                openApiRequest = requestWrapper.getOpenApiRequest();
                requestService.verifyRequestBody(openApiRequest);
                request.getRequestDispatcher(RequestConst.RouteDispatchPrefix + "/" + requestWrapper.getOpenApiRequest().getMethod().replace(".", "/")).forward(requestWrapper, response);
            } else {
                chain.doFilter(request, response);
            }


        } catch (Exception e) {
            logger.error("统一路由错误：请求参数：{},错误信息：{}，堆栈：", null == openApiRequest ? "无" : openApiRequest.toString(), e.getMessage(), e);
            requestService.mockErrorResponse(response, e.getMessage());
        }

    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
    }

}
