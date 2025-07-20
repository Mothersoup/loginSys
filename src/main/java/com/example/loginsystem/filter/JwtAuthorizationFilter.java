package com.example.loginsystem.filter;

import com.alibaba.fastjson2.JSON;
import com.example.loginsystem.security.LoginPrinciple;
import com.example.loginsystem.util.JwtUtil;
import com.example.loginsystem.web.JsonResult;
import com.example.loginsystem.web.ServerCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.stream.Collectors;


@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;

    public JwtAuthorizationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        logger.debug("exe JwtAuthorizationFilter.....");
        //clean security context
        SecurityContextHolder.clearContext();
        logger.debug("clean Security context holder context");

        //get jwt from the request
        String jwt = request.getHeader("Authorization");
        String authHeader = request.getHeader("Authorization");
        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7);
        }
        logger.debug("get jwt from request header"+ jwt);

        //judge if jwt is null or empty if not go through username_password filter
        if (!StringUtils.hasText(jwt)) {
            logger.debug("invalid jwt or jwt is empty, go through username_password filter");
            //go through username_password filter
            filterChain.doFilter(request, response);
            return;
        }

        // claims reference
        Claims claims;
        try {
            //if get a valid jwt
            claims = jwtUtil.extractAllClaims(jwt);
        } catch (ExpiredJwtException e) {
            logger.debug( String.format("parse JWT fail，JWT expired：class = {%s}，message = {%s}", e.getClass().getName(), e.getMessage()) );
            abnormal(response, "ExpiredJwtException");
            return;
        } catch (MalformedJwtException e) {
            logger.debug(String.format("parse  JWT failed,JWT data error{%s}，message = {%s}", e.getClass().getName(), e.getMessage()));
            abnormal(response, "MalformedJwtException");
            return;
        } catch (Throwable e) {
            logger.debug(String.format("parse JWT error {%s},{%s}", e.getClass().getName(), e.getMessage()));
            abnormal(response, "Throwable");
            return;
        }
        Object user_id = claims.get("sub");
        logger.debug(String.format("id parse from JWT :{%s}", user_id.toString()));

        LoginPrinciple loginPrinciple = new LoginPrinciple();
        loginPrinciple.setAccount(user_id.toString());

        Object rolesObject = claims.get("roles");
        List<String> roles = new ArrayList<>();
        if (rolesObject instanceof List<?>) {
            List<?> list = (List<?>) rolesObject;
            for (Object role : list) {
                if (role instanceof String) {
                    roles.add((String) role);
                }
            }
        }
        List<GrantedAuthority> authorities = roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(loginPrinciple, null, authorities);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);

    }


    private static void abnormal(HttpServletResponse response, String methodName) throws IOException {
        String message = "login_info expired, please login again";
        String message1 = "login_info is invalid, please login again";

        //設定字符集
        response.setContentType("application/json; charset=utf-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);



        if ("ExpiredJwtException".equals(methodName)) {
            response.getWriter().println(JSON.toJSONString(JsonResult.fail(ServerCode.ERR_JWT_EXPIRED, message)));
        } else if ("SignatureException".equals(methodName) || "MalformedJwtException".equals(methodName) || "Throwable".equals(methodName)) {
            response.getWriter().println(JSON.toJSONString(
                    JsonResult.fail(ServerCode.ERR_JWT_INVALID, message1)
            ));
        } else {
            response.getWriter().println(JSON.toJSONString(
                    JsonResult.fail(ServerCode.ERR_UNKNOWN, "unknown error ")
            ));
        }
    }
}
