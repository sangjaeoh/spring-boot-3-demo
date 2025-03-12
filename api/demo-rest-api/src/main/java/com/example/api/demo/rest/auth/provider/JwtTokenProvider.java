package com.example.api.demo.rest.auth.provider;

import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class JwtTokenProvider {

	private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

	@Value("${jwt.secret-key}")
	private String secretKey;

	@Value("${jwt.access-token-expiration}")
	private long accessTokenExpiration;

	@Value("${jwt.refresh-token-expiration}")
	private long refreshTokenExpiration;

	// Secret Key Base64 Decode
	private SecretKey getSigningKey() {
		//        // secretKey 문자열 생성
		//        SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
		//        String base64EncodedSecretKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());
		//        System.out.println("Base64 Encoded Secret Key: " + base64EncodedSecretKey);
		byte[] keyBytes = Decoders.BASE64.decode(this.secretKey);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	// Access Token 생성
	public String generateAccessToken(UserDetails userDetails) {
		return generateToken(userDetails, this.accessTokenExpiration);
	}

	// Refresh Token 생성
	public String generateRefreshToken(UserDetails userDetails) {
		return generateToken(userDetails, refreshTokenExpiration);
	}

	// 토큰 생성 (공통)
	public String generateToken(UserDetails userDetails, long expirationMs) {
		return Jwts.builder().subject(userDetails.getUsername()) // Subject (사용자 식별 정보)
			.issuedAt(new Date()) // 발행 시간
			.expiration(new Date(System.currentTimeMillis() + expirationMs)) // 만료 시간
			.signWith(getSigningKey()) // 서명 알고리즘, Secret Key 설정
			.compact(); // JWT Compact
	}

	// 토큰 유효성 검증
	public boolean validateToken(String token) {
		try {
			Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token);
			return true;
		} catch (MalformedJwtException e) {
			logger.error("잘못된 JWT 서명입니다: {}", e.getMessage());
		} catch (ExpiredJwtException e) {
			logger.error("만료된 JWT 토큰입니다: 만료 시간 - {}, 현재 시간 - {}", e.getClaims().getExpiration(), new Date());
		} catch (UnsupportedJwtException e) {
			logger.error("지원되지 않는 JWT 토큰 형식입니다: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error("JWT claims 문자열이 비어있습니다: {}", e.getMessage());
		} catch (SignatureException e) {
			logger.error("JWT 서명 검증에 실패했습니다: {}, 사용된 키 - {}, 알고리즘 - {}", e.getMessage(), getSigningKey().getAlgorithm(),
				getSigningKey().getAlgorithm());
		} catch (JwtException e) {
			logger.error("JWT 처리 중 예외 발생: ", e);
		}
		return false;
	}

	// 토큰에서 사용자 이름 (Subject) 추출
	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	// 토큰 만료 시간 추출
	public long getExpiration(String token) {
		return getClaimFromToken(token, claims -> claims.getExpiration().getTime());
	}

	// 토큰에서 Claim 추출
	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	// 토큰에서 모든 Claim 추출
	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload();
	}

	// Request Header 에서 토큰 추출 (Authorization: Bearer <token>) - 변경 없음
	public String resolveToken(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}
		return null;
	}
}