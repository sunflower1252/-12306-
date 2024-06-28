package com.sanzuniao.common;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.crypto.GlobalBouncyCastleProvider;
import cn.hutool.json.JSONObject;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTPayload;
import cn.hutool.jwt.JWTUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * JWT工具类，用于生成、验证和解析JWT令牌。
 */
public class JwtUtil {
    // 日志记录器
    private static final Logger LOG = LoggerFactory.getLogger(JwtUtil.class);
    // JWT令牌的密钥，用于签名和验证JWT令牌
    /**
     * 盐值很重要，不能泄漏，且每个项目都应该不一样，可以放到配置文件中
     */
    private static final String key = "sanzuniao12306";

    /**
     * 生成JWT令牌。
     *
     * @param id     用户ID
     * @param mobile 用户手机号
     * @return token 生成的JWT令牌字符串
     */
    public static String createToken(Long id, String mobile) {
        LOG.info("开始生成JWT token，id：{}，mobile：{}", id, mobile);
        // Hutool在处理加密相关操作时不要使用Bouncy Castle作为安全提供者
        GlobalBouncyCastleProvider.setUseBouncyCastle(false);

        // 获取当前时间
        DateTime now = DateTime.now();

        // 计算过期时间
        /*
          参数一：时间字段
          参数二：偏移量（增加的时间）
         */
        DateTime expTime = now.offsetNew(DateField.HOUR, 24);

        //设置Payload
        Map<String, Object> payload = new HashMap<>();

        // 设置JWT载荷信息，包括签发时间、过期时间、生效时间和用户ID、手机号
        // 签发时间
        payload.put(JWTPayload.ISSUED_AT, now);
        // 过期时间
        payload.put(JWTPayload.EXPIRES_AT, expTime);
        // 生效时间
        payload.put(JWTPayload.NOT_BEFORE, now);
        // 内容
        payload.put("id", id);
        payload.put("mobile", mobile);

        //根据payload和key生成token
        String token = JWTUtil.createToken(payload, key.getBytes());
        LOG.info("生成JWT token：{}", token);

        return token;
    }

    /**
     * 验证JWT令牌的有效性。
     *
     * @param token 待验证的JWT令牌字符串
     * @return 如果令牌有效返回true，否则返回false
     */
    public static boolean validate(String token) {
        LOG.info("开始JWT token校验，token：{}", token);
        GlobalBouncyCastleProvider.setUseBouncyCastle(false);
        JWT jwt = JWTUtil.parseToken(token).setKey(key.getBytes());
        // 验证JWT令牌的有效性
        // validate包含了verify
        boolean validate = jwt.validate(0);
        LOG.info("JWT token校验结果：{}", validate);
        return validate;
    }

    /**
     * 解析JWT令牌并返回载荷中的信息。
     *
     * @param token JWT令牌字符串
     * @return 解析后的JWT载荷信息
     */
    public static JSONObject getJSONObject(String token) {
        GlobalBouncyCastleProvider.setUseBouncyCastle(false);
        // 根据key获取token内容
        JWT jwt = JWTUtil.parseToken(token).setKey(key.getBytes());
        JSONObject payloads = jwt.getPayloads();
        // 移除JWT标准字段，只返回自定义字段
        payloads.remove(JWTPayload.ISSUED_AT);
        payloads.remove(JWTPayload.EXPIRES_AT);
        payloads.remove(JWTPayload.NOT_BEFORE);
        LOG.info("根据token获取原始内容：{}", payloads);
        return payloads;
    }

    public static void main(String[] args) {
        // 示例调用：生成令牌
        createToken(1L, "123");
        // 示例调用：验证令牌
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJuYmYiOjE2NzY4OTk4MjcsIm1vYmlsZSI6IjEyMyIsImlkIjoxLCJleHAiOjE2NzY4OTk4MzcsImlhdCI6MTY3Njg5OTgyN30.JbFfdeNHhxKhAeag63kifw9pgYhnNXISJM5bL6hM8eU";
        validate(token);
        // 示例调用：获取令牌中的信息
        getJSONObject(token);
    }
}
