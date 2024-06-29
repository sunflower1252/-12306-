package com.sanzuniao.config;

import com.sanzuniao.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 全局过滤器实现，用于处理会员登录状态验证。
 * 该过滤器会检查请求头中的token，验证会员身份，并决定是否放行请求。
 *
 * @author yangguang
 */
@Slf4j
@Component
public class LoginMemberFilter implements Ordered, GlobalFilter {

    /**
     * 过滤器逻辑实现，用于处理所有经过网关的请求。
     *
     * @param exchange 当前的请求上下文，包含请求和响应信息。
     * @param chain    过滤器链，调用以传递到下一个过滤器。
     * @return Mono<Void> 表示异步操作结果，完成时无返回值。
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();

        // 判断当前请求是否需要被拦截（如登录、发送验证码等公开接口无需验证）
        if (path.contains("/admin")
                || path.contains("/MemberController/login")
                || path.contains("/MemberController/sendCode")
                || path.contains("/MemberController/register")) {
            log.info("不需要拦截的请求路径: {}", path);
            // 直接放行
            return chain.filter(exchange);
        } else {
            log.info("拦截到的请求路径：{}", path);
        }

        // 从请求头中获取token
        String token = exchange.getRequest().getHeaders().getFirst("token");
        log.info("开始验证会员登录状态，token：{}", token);

        // 检查token是否存在
        if (token == null || token.isEmpty()) {
            log.info("请求头中token为空，请求被拒绝");
            // 设置未授权状态码
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            // 结束响应，返回未授权信息
            return exchange.getResponse().setComplete();
        }

        // 验证token的有效性
        boolean isValid = JwtUtil.validate(token);
        if (isValid) {
            log.info("token验证通过，请求正常放行");
            // 放行请求到后续处理器
            return chain.filter(exchange);
        } else {
            log.warn("token无效，请求被拒绝");
            // 设置未授权状态码
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            // 结束响应，返回未授权信息
            return exchange.getResponse().setComplete();
        }
    }

    /**
     * 定义过滤器执行的优先级顺序。
     *
     * @return int 返回执行顺序，数字越小优先级越高。
     */
    @Override
    public int getOrder() {
        // 此处设置为0，表示该过滤器具有最高优先级
        return 0;
    }
}
