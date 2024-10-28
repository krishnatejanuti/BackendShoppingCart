package com.usermicroservice.jwt;


import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.usermicroservice.entity.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService 
{

    private static final String SECRET = "TmV3U2VjcmV0S2V5Rm9ySldUU2lnbmluZ1B1cnBvc2VzMTIzNDU2Nzg=\r\n";

    public String generateToken(User user) 
    {

		Map<String, Object> claims = new HashMap<>();
		claims.put("userName", user.getUserName());
		claims.put("userId", user.getUserId());
		claims.put("userEmail", user.getEmail());
		claims.put("role", user.getRole());

		return Jwts.builder().setSubject(user.getUserName()).setClaims(claims)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30)).signWith(getKey())
//                .signWith(SECRET, SignatureAlgorithm.HS256)
				.compact();

    }

    private Key getKey() 
    {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
