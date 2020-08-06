package com.example.reviewMovie.util;


import com.example.reviewMovie.bean.TokenInfo;
import com.google.common.collect.Lists;
import com.google.gson.JsonObject;


import lombok.extern.slf4j.Slf4j;
import net.oauth.jsontoken.JsonToken;
import net.oauth.jsontoken.JsonTokenParser;
import net.oauth.jsontoken.crypto.HmacSHA256Signer;
import net.oauth.jsontoken.crypto.HmacSHA256Verifier;
import net.oauth.jsontoken.crypto.SignatureAlgorithm;
import net.oauth.jsontoken.crypto.Verifier;
import net.oauth.jsontoken.discovery.VerifierProvider;
import net.oauth.jsontoken.discovery.VerifierProviders;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import java.security.InvalidKeyException;
import java.security.SignatureException;
import java.util.Calendar;
import java.util.List;



/* Usage: 
 * public class App {
	public static void main(String[] args) {

		// Create a token!
		Auth auth = new Auth();
		String token = auth.createJsonWebToken("sushma@gmail.com");
		System.out.println("Token =>" + token);

		// Validate a good token!
		if (auth.isValidToken("sushma@gmail.com", token)) {
			System.out.println("Yes, its avalid token");
		} else {
			System.out.println("No, Not a valid token");
		}

		// Validate a bad token!
		if (auth.isValidToken("sushma@gmail.com", token.concat("a"))) {
			System.out.println("Yes, its a valid token");
		} else {
			System.out.println("No, Not a valid token");
		}

	}

}
 */


/*
 * Provides static methods for creating and verifying access tokens.
 * 
 * @author davidm
 *
 */
@Slf4j
public class AuthUtil {

    private static final String AUDIENCE = "";
    private static final String ISSUER = "LENSKART, LOCAL-FITTING ADMINDASHBOARD";
    private static final String SIGNING_KEY = "Long@^($%AndHardToGuess@^($%ValueWithSpecial@^($%Characters@^($%*$%";
    private static final Long durationhours = (long) 24 * 1; // Make it for 30 days!

    /**
     * Creates a json web token which is a digitally signed token that contains a
     * payload (e.g. userId to identify the user). The signing key is secret. That
     * ensures that the token is authentic and has not been modified. Using a jwt
     * eliminates the need to store authentication session information in a
     * database.
     * 
     * @param userId
     * @return
     */
    public static String createJsonWebToken(String userId) {
        // Current time and signing algorithm
        Calendar cal = Calendar.getInstance();
        HmacSHA256Signer signer;
        try {
            signer = new HmacSHA256Signer(ISSUER, null, SIGNING_KEY.getBytes());
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }

        // Configure JSON token
        JsonToken token = new net.oauth.jsontoken.JsonToken(signer);
        token.setAudience(AUDIENCE);

        token.setIssuedAt(new org.joda.time.Instant(cal.getTimeInMillis()));
        token.setExpiration(new org.joda.time.Instant(cal.getTimeInMillis() + (durationhours * 60 * 1000)));

        // Configure request object, which provides information of the item
        JsonObject request = new JsonObject();
        request.addProperty("userId", userId);
        request.addProperty("issueTime", cal.getTimeInMillis());
        request.addProperty("duration", durationhours);

        JsonObject payload = token.getPayloadAsJsonObject();
        payload.add("info", request);

        try {
            return token.serializeAndSign();
        } catch (SignatureException e) {
            throw new RuntimeException(e);
        }
    }

    /*
     * Verifies a json web token's validity and extracts the user id and other
     * information from it.
     * 
     * @param token
     * 
     * @return
     * 
     * @throws SignatureException
     * 
     * @throws InvalidKeyException
     */
    public TokenInfo verifyToken(String token) {
        try {
            final Verifier hmacVerifier = new HmacSHA256Verifier(SIGNING_KEY.getBytes());

            VerifierProvider hmacLocator = new VerifierProvider() {

                public List < Verifier > findVerifier(String id, String key) {
                    return Lists.newArrayList(hmacVerifier);
                }
            };

            VerifierProviders locators = new VerifierProviders();
            locators.setVerifierProvider(SignatureAlgorithm.HS256, hmacLocator);

            net.oauth.jsontoken.Checker checker = new net.oauth.jsontoken.Checker() {
                public void check(JsonObject payload) throws SignatureException {
                    // don't throw - allow anything
                }
            };

            // Ignore Audience does not mean that the Signature is ignored
            JsonTokenParser parser = new JsonTokenParser(locators, checker);
            JsonToken jt;
            try {
                jt = parser.verifyAndDeserialize(token);
            } catch (SignatureException e) {
                throw new RuntimeException(e);
            }

            JsonObject payload = jt.getPayloadAsJsonObject();
            log.info("JSON Payload :" + payload.toString());
            TokenInfo t = new TokenInfo();
            String issuer = payload.getAsJsonPrimitive("iss").getAsString();
            String userIdString = payload.getAsJsonObject("info").getAsJsonPrimitive("userId").getAsString();

            log.info("hello");
            log.info("issuer:" ,issuer);
            log.info("userid:" ,userIdString);

            if (issuer.equals(ISSUER) && !StringUtils.isBlank(userIdString)) {
                t.setUserId(userIdString);
                t.setIssued(new DateTime(payload.getAsJsonPrimitive("iat").getAsLong()));
                t.setExpires(new DateTime(payload.getAsJsonPrimitive("exp").getAsLong()));
                t.setIssueTime(payload.getAsJsonObject("info").getAsJsonPrimitive("issueTime").getAsLong());
                t.setDuration(payload.getAsJsonObject("info").getAsJsonPrimitive("duration").getAsInt());
                
                return t;
            } else {
                return null;
            }

        } catch (InvalidKeyException e1) {
            throw new RuntimeException(e1);
        }
    }

    /*
     * Verify if the token is still valid for a given user.
     */
    public Boolean isValidToken(String user, String token) {

        try {
            TokenInfo tokenInfo = verifyToken(token);

            System.out.println(
                tokenInfo.getUserId() + ":" + tokenInfo.getIssued() + ":" + tokenInfo.getExpires().toString());
            System.out.println(tokenInfo.getIssueTime() + ":" + tokenInfo.getDuration());
            if (tokenInfo.getUserId().toLowerCase().equals(user.toLowerCase())) {
                
                Long issueTime = tokenInfo.getIssueTime();
                Calendar cal = Calendar.getInstance();
                Long currentTime = cal.getTimeInMillis();
                Long duration = (long)(durationhours * 60 * 1000); // duration in millsec
                if ((currentTime - issueTime) < duration) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (Exception e) {
            System.out.println("Exception : " + e.getMessage());
        }
        return false;
    }
}
