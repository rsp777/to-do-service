package com.pawar.todo.app.jwtfilter;

import java.security.SignatureException;

import org.springframework.stereotype.Service;

import ch.qos.logback.classic.Logger;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;

@Service
public class JwtTokenProvider {

//	 @Value("${app.jwt.secret}")
	    private String jwtSecret="secretKey";

	    public boolean validateToken(String token) {
	        try {
	            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
	            return true;
	        } catch (MalformedJwtException ex) {
	            System.out.println("Invalid JWT token");
	        } catch (ExpiredJwtException ex) {
	            System.out.println("Expired JWT token");
	        } catch (UnsupportedJwtException ex) {
	            System.out.println("Unsupported JWT token");
	        } catch (IllegalArgumentException ex) {
	            System.out.println("JWT claims string is empty.");
	        }
	        return false;
	    }

	    public String getUsernameFromJWT(String token) {
	        Claims claims = Jwts.parser()
	                .setSigningKey(jwtSecret)
	                .parseClaimsJws(token)
	                .getBody();
	        System.out.println(claims.getSubject());
	        return claims.getSubject();
	    }
	
}
