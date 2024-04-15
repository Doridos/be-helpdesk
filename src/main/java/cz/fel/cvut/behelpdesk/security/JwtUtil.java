package cz.fel.cvut.behelpdesk.security;

import cz.fel.cvut.behelpdesk.dto.DetailEmployeeDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtil {
    @Value("${token.signing.key}")
    private String jwtSigningKey;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(jwtSigningKey).build().parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(DetailEmployeeDto userDetails) {

        Map<String, Object> claims = new HashMap<>();
        claims.put("role", userDetails.userRole());
        claims.put("categories", userDetails.categories());
        claims.put("forename", userDetails.forename());
        claims.put("surname", userDetails.surname());
        System.out.println(claims);
        return createToken(claims, userDetails);
    }

    private String createToken(Map<String, Object> claims, DetailEmployeeDto subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject.username()).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, jwtSigningKey).compact();
    }

    public Boolean validateToken(String token, DetailEmployeeDto userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.username()) && !isTokenExpired(token));
    }
}