package ru.arlabs.taskservice.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.arlabs.taskservice.common.properties.ApplicationProperties;
import ru.arlabs.taskservice.user.model.User;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;

/**
 * @author Jeb
 */
@Component
public class JwtUtil {

    private final String secret;

    @Autowired
    public JwtUtil(ApplicationProperties config) {
        this.secret = config.getJwtSecret();
    }

    public static final long JWT_TOKEN_VALIDITY = 2 * 7 * 24;


    public Optional<Claims> parseToken(String token) {
        try {
            Claims body = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
            return Optional.of(body);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public String generateToken(User user) {
        Claims claims = Jwts.claims().setSubject(user.getEmail());
        claims.put("userId", user.getId());
        LocalDateTime now = LocalDateTime.now();
        Date expirationDate = Date.from(
                now.plus(JWT_TOKEN_VALIDITY, ChronoUnit.HOURS)
                        .atZone(ZoneId.systemDefault())
                        .toInstant()
        );
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }



    public boolean validateToken(Claims claims, User userDetails) {
        return claims.getSubject().equals(userDetails.getEmail());
    }

    public boolean isTokenExpired(Claims claims) {
        Date expiration = claims.getExpiration();
        return expiration.before(new Date());
    }
}
