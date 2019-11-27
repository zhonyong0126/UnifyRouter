package com.feture.learnfilter.filter;

import com.feture.learnfilter.consts.RequestConst;
import com.feture.learnfilter.consts.ResponseSubCode;
import com.feture.learnfilter.exception.InvalidArgumentException;
import com.feture.learnfilter.model.OpenApiRequest;
import com.feture.learnfilter.service.RequestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${openapi_unify_request_switch}")
    private String openApiUnifyRequestSwitch;

    @Autowired
    private RequestService requestService;

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        OpenApiRequest openApiRequest = null;
        String requestUrl = ((HttpServletRequest) request).getRequestURI();

        /**
         * 非OpenApi的请求走正常流程
         */
        if (requestUrl.indexOf(RequestConst.RouteDispatchPrefix) < 0) {
            chain.doFilter(request, response);
            return;
        }
        /**
         * 统一请求开关未开走正常流程
         */
        if (!"true".equalsIgnoreCase(openApiUnifyRequestSwitch)) {
            chain.doFilter(request, response);
            return;
        }

        try {
            if (!requestUrl.equals(RequestConst.RouteDispatchPrefix))
                throw new IllegalArgumentException("请采用统一请求");

            if (!HttpMethod.POST.toString().equals(((HttpServletRequest) request).getMethod())) {
                throw new InvalidArgumentException(ResponseSubCode.INVALID_HTTP_METHOD);
            }

            RewriteHttpBodyRequest requestWrapper = new RewriteHttpBodyRequest((HttpServletRequest) request);
            openApiRequest = requestWrapper.getOpenApiRequest();
            requestService.verifyRequestBody(openApiRequest);
            request.getRequestDispatcher(RequestConst.RouteDispatchPrefix + "/" + requestWrapper.getOpenApiRequest().getMethod().replace(".", "/")).forward(requestWrapper, response);

        } catch (Exception e) {
            logger.error("统一路由错误：请求Url：{}，请求参数：{},错误信息：{}，堆栈：", requestUrl, null == openApiRequest ? "无" : openApiRequest.toString(), e.getMessage(), e);
            requestService.mockErrorResponse(response, e.getMessage());
        }

    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
    }

}
