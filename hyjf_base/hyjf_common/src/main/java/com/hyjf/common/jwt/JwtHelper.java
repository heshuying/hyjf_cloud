package com.hyjf.common.jwt;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.hyjf.common.bean.AccessToken;
import org.apache.commons.lang3.time.DateUtils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

/**
 * token生成验证工具
 */
public class JwtHelper {
	private static final Logger logger = LoggerFactory.getLogger(JwtHelper.class);
	/** 公钥 */
	private static final String SECRET = "hyjf_secret";
	/** 发行者 */
	private static final String ISSUER = "hyjf";

	/**
	 * 生成token
	 * 
	 * @param claims
	 *            参数集合 userId username ts
	 * @return
	 */
	public static String generatorToken(AccessToken accessToken) {
		try {
			// 过期时间
			Date expiresDate = DateUtils.addDays(new Date(), 1);
			Algorithm algorithm = Algorithm.HMAC256(SECRET);
			JWTCreator.Builder builder = JWT.create().withIssuer(ISSUER).withExpiresAt(expiresDate)
					.withClaim("userId", accessToken.getUserId()).withClaim("username", accessToken.getUsername())
					.withClaim("ts", accessToken.getTs());
			return builder.sign(algorithm);
		} catch (IllegalArgumentException | UnsupportedEncodingException e) {
			logger.error("jwt generator token fail...", e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 验证token
	 * 
	 * @param token
	 * @return
	 */
	public static AccessToken parseToken(String token) {
		Algorithm algorithm = null;
		try {
			algorithm = Algorithm.HMAC256(SECRET);
		} catch (IllegalArgumentException | UnsupportedEncodingException e) {
            logger.error("jwt parse token fail...", e);
			throw new RuntimeException(e);
		}
		JWTVerifier verifier = JWT.require(algorithm).withIssuer(ISSUER).build();
		DecodedJWT jwt = verifier.verify(token);
		Map<String, Claim> map = jwt.getClaims();
		AccessToken accessToken = null;

		if (!CollectionUtils.isEmpty(map)) {
			accessToken = new AccessToken(token);
			accessToken.setTs(map.get("ts").asLong());
			accessToken.setUserId(map.get("userId").asInt());
			accessToken.setUsername(map.get("username").asString());
		}
		return accessToken;
	}

}
