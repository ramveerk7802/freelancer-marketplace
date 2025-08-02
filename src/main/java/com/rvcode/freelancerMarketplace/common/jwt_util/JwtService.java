package com.rvcode.freelancerMarketplace.common.jwt_util;


import com.rvcode.freelancerMarketplace.user.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${application.jwt.secret-key}")
    private String SECRET_KEY;

    @Value("${application.jwt.expiration-time}")
    private long EXPIRE_TIME;


    public String generateToken(User user){
        Map<String,Object> extraClaims = new HashMap<>();
        extraClaims.put("role",user.getRole().name());
        return Jwts.builder()
                .claims(extraClaims)
                .subject(user.getUsername())
                .issuer("rvcode")
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+EXPIRE_TIME))
                .signWith(getSecretKey())
                .compact();

    }

    public String extractUsername(String token){
        return extractClaims(token,Claims::getSubject);

    }

    public String extractRole(String token){
        Claims claims = extractAllClaims(token);
        return claims.get("role", String.class);
    }

    public <T> T extractClaims(String token,Function<Claims,T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);

    }


    private Claims extractAllClaims(String token){
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }


    public boolean isTokenValid(String token, UserDetails userDetails){
        return extractUsername(token).equals(userDetails.getUsername()) && !isTokenExpired(token);
    }


    public boolean isTokenExpired(String token){
        return extractClaims(token,Claims::getExpiration).before(new Date());
    }





    private SecretKey getSecretKey(){
        byte[] decode = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(decode);
    }


}
