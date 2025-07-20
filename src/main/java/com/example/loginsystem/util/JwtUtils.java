package com.example.loginsystem.util;


import com.example.loginsystem.ex.ServiceException;
import com.example.loginsystem.web.ServerCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
<<<<<<<< HEAD:src/main/java/com/example/loginsystem/util/JwtUtil.java
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import static com.mysql.cj.conf.PropertyKey.logger;

@Slf4j
@Service
 public class JwtUtil {
========
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;


@Component
public class JwtUtils {
>>>>>>>> 44cc6f89f907b34ea580ca5525ff27e69c424180:src/main/java/com/example/loginsystem/util/JwtUtils.java
    /**
     * 簽名密鑰
     */
    @Value("${security.jwt.secret-key}")
    private static String secretKey;

    //過期時間：以分鐘為單位

    @Value("${security.jwt.expiration-time}")
    private static  long jwtExpiration;

    /**
     *
    禁止new 對象
     */
<<<<<<<< HEAD:src/main/java/com/example/loginsystem/util/JwtUtil.java
    private JwtUtil(){
    }


    @PostConstruct
    public void validateConfig() {
        if (jwtExpiration < 5) {
            throw new ServiceException(ServerCode.ERR_JWT_INVALID, "JWT expiration time must be at least 5 minutes (300,000 milliseconds)");
        }
========
    private JwtUtils(){
>>>>>>>> 44cc6f89f907b34ea580ca5525ff27e69c424180:src/main/java/com/example/loginsystem/util/JwtUtils.java
    }


    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String extractEmail( String token ){
        return extractClaim(token, Claims::getSubject);
    }


    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }


<<<<<<<< HEAD:src/main/java/com/example/loginsystem/util/JwtUtil.java
    public Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
========
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
>>>>>>>> 44cc6f89f907b34ea580ca5525ff27e69c424180:src/main/java/com/example/loginsystem/util/JwtUtils.java
    }





    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    // get expire time and compare with now
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }


    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

<<<<<<<< HEAD:src/main/java/com/example/loginsystem/util/JwtUtil.java
    public String generateToken(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        List<String> roles = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)  // Convert GrantedAuthority to String
                .collect(Collectors.toList());
        return Jwts.builder()
                .setSubject( userDetails.getUsername())
                .claim("roles", roles )
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration * 60 * 1000))
                .signWith(getSignInKey(), SignatureAlgorithm.HS512)
                .compact();
========
    public static String generateToken(Map<String, Object> claims ) {
        // 過期時間 1 day
        Date expirationDate = new Date(System.currentTimeMillis() + jwtExpiration * 60 * 1000);

        // JWT組成部分:
        // (Header)頭
        // 用於配置演算法與結果資料類型
        // (Payload)載重
        // 用於配置需要封裝到JWT中的數據
        // (Signature)簽名
        // 用於指定演算法與金鑰(鹽值)

        // 驗證邏輯不要跟這耦合
        //UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return Jwts.builder()
                // header
                .setHeaderParam("typ", "jwt")
                .setHeaderParam("alg", "HS256")
                // Payload
                .setClaims(claims)
                .setExpiration(expirationDate)
                .setIssuedAt(new Date())

                // signature
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();// 打包
>>>>>>>> 44cc6f89f907b34ea580ca5525ff27e69c424180:src/main/java/com/example/loginsystem/util/JwtUtils.java
    }
}
