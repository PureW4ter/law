package com.jfzy.service.impl;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jfzf.core.Constants;
import com.jfzf.core.HttpClientUtils;
import com.jfzy.service.AccessTokenService;
import com.jfzy.service.bo.AccessTokenRespDto;
import com.jfzy.service.repository.RedisRepository;

@Service
public class AccessTokenServiceImpl implements AccessTokenService {

	private static Logger logger = LoggerFactory.getLogger(AccessTokenServiceImpl.class);

	private static final String REDIS_KEY_TOKEN = "ACCESS_TOKEN";
	private static final String VALUE_FORMAT = "%s|%s";
	private static final int EXPIRE_INTERVAL = 120000;

	@Autowired
	private RedisRepository redisRepo;

	private AccessToken token;

	private static final class AccessToken {
		private String token;
		private long expireTime;

		private boolean isExpiring() {
			return System.currentTimeMillis() > expireTime + EXPIRE_INTERVAL;
		}

		private static AccessToken fromString(String tokenString) {
			String[] result = StringUtils.split(tokenString, '|');
			if (result != null && result.length == 2 && StringUtils.isNumeric(result[1])) {
				AccessToken tmpToken = new AccessToken();
				tmpToken.expireTime = Long.valueOf(result[1]);
				tmpToken.token = result[0];
				return tmpToken;
			} else {
				return null;
			}
		}

		public String toString() {
			return String.format(VALUE_FORMAT, this.token, expireTime);
		}
	}

	public void refreshAccessToken() {
		AccessToken tmpToken = this.token;
		if (tmpToken == null) {
			String tokenString = (String) redisRepo.get(REDIS_KEY_TOKEN);
			if (StringUtils.isNoneBlank(tokenString)) {
				tmpToken = AccessToken.fromString(tokenString);
			}
		}

		if (tmpToken == null || tmpToken.isExpiring()) {
			try {
				logger.info("==========get access token==========");
				String response = HttpClientUtils.get(Constants.ACCESS_TOKEN_URL);
				ObjectMapper mapper = new ObjectMapper();
				AccessTokenRespDto dto = mapper.readValue(response, AccessTokenRespDto.class);
				if (dto != null && StringUtils.isBlank(dto.getErrCode())) {
					tmpToken = new AccessToken();
					tmpToken.expireTime = System.currentTimeMillis() + 1000 * dto.getExpiresIn();
					tmpToken.token = dto.getAccessToken();
					this.token = tmpToken;
					this.redisRepo.set(REDIS_KEY_TOKEN, tmpToken.toString());
				} else {
					logger.error(String.format("QUERY ACCESS TOKEN FAILED with error code:", response));
				}

			} catch (IOException e) {
				logger.error("QUERY ACCESS TOKEN FAILED:", e);
			}
		}
	}

	@Override
	public String getAccessToken() {
		return this.token == null ? null : token.token;
	}

}
