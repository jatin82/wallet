package com.poc.wallet.service;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.poc.wallet.model.Token;
import com.poc.wallet.model.db.User;
import com.poc.wallet.util.Constants;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


@Service
public class JWTService {

	
	private static final Logger log = LoggerFactory.getLogger(JWTService.class);

	/** The secret. */
    @Value("${jwt.secret.decoded}")
    private String secret;
    
    @Value("${jwt.token.expire:1800000}") // 30 min token
    private long expireIn;

    public User parseAuthToken(Token token) {
        try {
            Claims body = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token.getToken())
                    .getBody();

            User user = new User();
            user.setUserId((Integer) body.get(Constants.USERID));
            user.setEmail((String) body.get(Constants.EMAIL));
            user.setName((String) body.get(Constants.NAME));
            
            return user;

        } catch (JwtException | ClassCastException e) {
        	log.error("Cannot parse Token");
            return null;
        }
    }

    
    /**
     * Generate token.
     *
     * @param user the user
     * @return the string
     */
    public Token generateAuthToken(User user,String type) {
        Claims claims = Jwts.claims();
        claims.put(Constants.USERID,user.getUserId());
        claims.put(Constants.NAME, user.getName());
        claims.put(Constants.EMAIL, user.getEmail());
        
        Token token = new Token();
        token.setToken(Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secret)
                .setExpiration(new Date(expireIn))
                .setIssuedAt(new Date())
                .compact());
        token.setType(type);
        return token;
    }
    
    public String encryptToken(String key,String type) {
        Claims claims = Jwts.claims();
        claims.put(type, key);
        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }
    
    public String decryptToken(String token,String type) {
    	try {
            Claims body = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();

            String key = body.get(type).toString();
            return key;
        } catch (JwtException | ClassCastException e) {
        	log.error("Cannot parse Token");
        	throw e;
        }
    }

}
