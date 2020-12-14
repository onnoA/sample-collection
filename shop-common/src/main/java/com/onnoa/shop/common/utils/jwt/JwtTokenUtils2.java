package com.onnoa.shop.common.utils.jwt;

import com.onnoa.shop.common.utils.JsonUtil;
import com.onnoa.shop.common.utils.UuidUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: JWT 工具类
 * @Author: onnoA
 * @Date: 2019/11/29 10:19
 */
public class JwtTokenUtils2 {

    /**
     * 创建jwt
     * Header: JSON 对象
     * {
     * "alg": "HS256",
     * "typ": "JWT"
     * }
     * Payload : JSON 对象      JWT 规定了7个官方字段，供选用
     * iss (issuer)：签发人
     * exp (expiration time)：过期时间
     * sub (subject)：主题
     * aud (audience)：受众
     * nbf (Not Before)：生效时间
     * iat (Issued At)：签发时间
     * jti (JWT ID)：编号
     * 除了官方字段，你还可以在这个部分定义私有字段，下面就是一个例子 ==>  如果有私有声明，一定要先设置这个自己创建的私有的声明
     * {
     * "sub": "1234567890",
     * "name": "John Doe",
     * "admin": true
     * }
     *
     * @param jwtId     是JWT的唯一标识，根据业务需要，这个可以设置为一个不重复的值，主要用来作为一次性token,从而回避重放攻击。
     * @param subject   代表这个JWT的主体，即它的所有人，这个是一个json格式的字符串，可以存放什么userid，roldid之类的，作为什么用户的唯一标志。
     * @param ttlMillis 过期的时间长度 以秒为单位
     * @param secret    密文 secret是保存在服务器端的，jwt的签发生成也是在服务器端的，secret就是用来进行jwt的签发和jwt的验证，所以，它就是你服务端的私钥，在任何场景都不应该流露出去。一旦客户端得知这个secret, 那就意味着客户端是可以自我签发jwt了。
     * @return token 返回token
     * @throws Exception
     */
    public static String createJWT(JWtObj obj, String jwtId, String subject, String secret, long ttlMillis) {
        Map<String, Object> headerMap = new HashMap<>();
        //指定签名的时候使用的签名算法，也就是header那部分，jjwt已经将这部分内容封装好了。
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        headerMap.put("alg", SignatureAlgorithm.HS256.getValue());
        headerMap.put("typ", "JWT");
        //生成JWT的时间
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        //创建payload的私有声明（根据特定的业务需要添加，如果要拿这个做验证，一般是需要和jwt的接收方提前沟通好验证方式的）
        Map<String, Object> claims = new HashMap<>();
        claims.put(JWTConstant.NAME, obj.getUsername());
        claims.put(JWTConstant.UID, obj.getId());
        //生成签名的时候使用的秘钥secret,这个方法本地封装了的，一般可以从本地配置文件中读取，切记这个秘钥不能外露哦。它就是你服务端的私钥，在任何场景都不应该流露出去。一旦客户端得知这个secret, 那就意味着客户端是可以自我签发jwt了。
        SecretKey key = generateKey(secret);
        //下面就是在为payload添加各种标准声明和私有声明了
        //这里其实就是new一个JwtBuilder，设置jwt的body
        JwtBuilder builder = Jwts.builder()
                //如果有私有声明，一定要先设置这个自己创建的私有的声明，这个是给builder的claim赋值，一旦写在标准的声明赋值之后，就是覆盖了那些标准的声明的
                .setClaims(claims)
                // 生成JWT头部信息
                .setHeader(headerMap)
                //设置jti(JWT ID)：是JWT的唯一标识，根据业务需要，这个可以设置为一个不重复的值，主要用来作为一次性token,从而回避重放攻击。
                .setId(jwtId)
                //iat: jwt的签发时间
                .setIssuedAt(now)
                //sub(Subject)：代表这个JWT的主体，即它的所有人，这个是一个json格式的字符串，可以存放什么userid，roldid之类的，作为什么用户的唯一标志。
                .setSubject(subject)
                //设置签名使用的签名算法和签名使用的秘钥
                .signWith(signatureAlgorithm, key);
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            //设置过期时间
            builder.setExpiration(exp);
        }
        //就开始压缩为 xxxxxxxxxxxxxx.xxxxxxxxxxxxxxx.xxxxxxxxxxxxx 这样的jwt
        return builder.compact();
    }


    /**
     * 解密jwt
     *
     * @param jwt Json Web Token
     * @return
     * @throws Exception
     */
    public static Jws<Claims> resolveJWT(String jwt, String secret) {
        //签名秘钥，和生成的签名的秘钥一模一样
        SecretKey key = generateKey(secret);
        //得到DefaultJwtParser
        Jws<Claims> claims = Jwts.parser()
                //设置签名的秘钥
                .setSigningKey(key)
                //设置需要解析的jwt
                .parseClaimsJws(jwt);
        return claims;
    }

    /**
     * 功能描述:
     *
     * @param accessToken 加密后的jwt
     * @param secret      密钥secret
     * @return jwt 自定义对象
     * @date 2019/11/30 12:12
     */
    public static JWtObj tranJWTObj(String accessToken, String secret) {
        Jws<Claims> claimsJws = resolveJWT(accessToken, secret);
        String subject = claimsJws.getBody().getSubject();
        return JsonUtil.fromJson(subject, JWtObj.class);
    }


    /**
     * 由字符串生成加密key
     * 根据密钥secret生成签名
     *
     * @return 加密key
     */
    public static SecretKey generateKey(String secret) {
        byte[] encodedKey = Base64.decodeBase64(secret);
        // 根据给定的字节数组使用AES加密算法构造一个密钥，使用 encodedKey中的始于且包含 0 到前 leng 个字节这是当然是所有。（后面的文章中马上回推出讲解Java加密和解密的一些算法）
        return new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
    }


    /**
     * 获取accessToken的过期时间
     *
     * @param minutes
     * @return
     */
    public static long getTokenFailureTime(Integer minutes) {
        minutes = (minutes == null || minutes == 0) ? 30 : minutes;
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(Calendar.MINUTE, minutes);
        return nowTime.getTimeInMillis();
    }

    public static void main(String[] args) {

        for (int i = 0; i < 10; i++) {
            String s = UuidUtil.genUuidNoLine();
            System.out.println("uuid:" + s);
        }


        /*String enPwd = MD5Util.generate("123456");
        System.out.println("加密后的密码：" + enPwd);*/



        /*JWtObj obj = new JWtObj().setId(4).setUsername("ls");

        String ab = createJWT(new JWtObj().setId(1).setUsername("zh"), UuidUtil.genUuid(), JsonUtil.obj2Json(obj), JWTConstant.JWT_SECRET, 6000000);
        System.out.println(ab);
        JWtObj jWtObj = tranJWTObj(ab, JWTConstant.JWT_SECRET);
        Map<String, Object> userMap = BeanUtils.beanToMapInFields(obj, Arrays.asList("username"));
        userMap.put("token", ab);
        Map<String, Object> map = BeanUtils.beanToMap(jWtObj);
        System.out.println(map.values());
        //注意：如果jwt已经过期了，这里会抛出jwt过期异常。
        Jws<Claims> claimsJws = resolveJWT(ab, JWTConstant.JWT_SECRET);
        // 第三部分: 签证（signature)
        System.out.println("signature:" + claimsJws.getSignature());
        // 第一部分:头部（header)
        System.out.println("header" + claimsJws.getHeader());
        // 第二部分 : 载荷（payload, 类似于飞机上承载的物品)
        Claims body = claimsJws.getBody();
        System.out.println("subject:" + body.getSubject());
        System.out.println("id:" + body.getId());
        //jwt
        //Mon Feb 05 20:50:49 CST 2018
        System.out.println(body.getIssuedAt());
        //{id:100,name:xiaohong}
        System.out.println(body.getSubject());
        //null
        System.out.println(body.getIssuer());
        //DSSFAWDWADAS...
        System.out.println(body.get("uid", Integer.class));
        long tokenFailureTime = getTokenFailureTime(60 * 24);
        System.out.println(tokenFailureTime);*/
    }


}
