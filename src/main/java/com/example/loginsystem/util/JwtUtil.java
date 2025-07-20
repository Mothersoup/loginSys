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
    /**
     * 簽名密鑰
     */
    @Value("${security.jwt.secret-key}")
    private String secretKey;

    //過期時間：以分鐘為單位

    @Value("${security.jwt.expiration-time}")
    private long jwtExpiration;

    /**
     *
    禁止new 對象
     */
    private JwtUtil(){
    }


    @PostConstruct
    public void validateConfig() {
        if (jwtExpiration < 5) {
            throw new ServiceException(ServerCode.ERR_JWT_INVALID, "JWT expiration time must be at least 5 minutes (300,000 milliseconds)");
        }
    }


    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }


    public Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
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
    }
}
