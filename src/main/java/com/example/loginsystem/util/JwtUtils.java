package com.example.loginsystem.util;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;


@Component
public class JwtUtils {
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
    private JwtUtils(){
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


    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
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
    }
}
