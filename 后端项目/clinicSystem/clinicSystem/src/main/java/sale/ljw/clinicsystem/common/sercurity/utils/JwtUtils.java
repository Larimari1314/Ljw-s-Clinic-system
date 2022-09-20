package sale.ljw.clinicsystem.common.sercurity.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtils {

    /*设置三十分钟后过期*/
    private static long EXPIRE_TIME;

    private static String TOKEN_SECRET;

    @Value(value = "${electronictariff.EXPIRE_TIME}")
    public void setExpireTime(long expireTime) {
        JwtUtils.EXPIRE_TIME = expireTime;
    }

    @Value(value = "${electronictariff.TOKEN_SECRET}")
    public void setTokenSecret(String tokenSecret) {
        JwtUtils.TOKEN_SECRET = tokenSecret;
    }

    public static String sign(String permission, String userId) {
        String token = "";
        try {
            Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
            //秘钥及加密算法
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            //设置头部信息
            Map<String, Object> header = new HashMap<>();
            header.put("typ", "JWT");
            header.put("alg", "HS256");
            //携带username，password信息，生成签名
            token = JWT.create().withHeader(header).withClaim("id", userId).withClaim("permission", permission).withExpiresAt(date).sign(algorithm);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return token;
    }

    public static boolean verify(String token) {
        /**
         * @desc 验证token，通过返回true
         * @params [token]需要校验的串
         **/
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (Exception e) {
//            e.printStackTrace();
            return false;
        }
    }

    public static String parseJWT(String token) {
        DecodedJWT decodeToken = JWT.decode(token);
        return decodeToken.getClaim("id").asString();
    }

    public static String parsePermission(String token) {
        DecodedJWT decodeToken = JWT.decode(token);
        return decodeToken.getClaim("permission").asString();
    }

    public static boolean isJwtExpired(String token) {
        try {
            DecodedJWT decodeToken = JWT.decode(token);
            return decodeToken.getExpiresAt().before(new Date());
        } catch (Exception e) {
            return true;
        }
    }
}

