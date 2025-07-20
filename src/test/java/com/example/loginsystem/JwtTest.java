package com.example.loginsystem;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.boot.test.context.SpringBootTest;
import java.security.Key;
import java.util.Date;


@SpringBootTest
public class JwtTest {
    // 模擬從 application.properties 取得的 Base64 secret key
    private static final String BASE64_SECRET = "2jW7Z2yTC61xdEFySlV8y04i5dpudJUMdekwc0V0SZzdpu5kN+mEZua5COs1W84RyJR8DJuQIYkXQSfYRbxOSg==";

    // 設定有效時間（毫秒）
    private static final long EXPIRATION_MS = 1000 * 60 * 10; // 10 分鐘

    public static void main(String[] args) {
        Key signingKey = getSigningKey(BASE64_SECRET);

        // Step 1: 產生 token
        String token = generateJwtToken("testuser", signingKey);
        System.out.println("✅ Generated JWT token:\n" + token);

        // Step 2: 驗證 token 並印出 username
        validateAndPrintUsername(token, signingKey);
    }

    private static Key getSigningKey(String base64Secret) {
        byte[] keyBytes = Decoders.BASE64.decode(base64Secret);
        System.out.println("🔐 Decoded secret length = " + keyBytes.length + " bytes");
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private static String generateJwtToken(String username, Key signingKey) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_MS))
                .signWith(signingKey, SignatureAlgorithm.HS512)
                .compact();
    }

    private static void validateAndPrintUsername(String token, Key signingKey) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(signingKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            String username = claims.getSubject();
            Date expiration = claims.getExpiration();

            System.out.println("✅ Token valid.");
            System.out.println("👤 Username: " + username);
            System.out.println("⏰ Expires: " + expiration);

        } catch (ExpiredJwtException e) {
            System.out.println("❌ Token 已過期");
        } catch (SignatureException e) {
            System.out.println("❌ Token 簽章無效");
        } catch (JwtException e) {
            System.out.println("❌ Token 其他錯誤：" + e.getMessage());
        }
    }
}
