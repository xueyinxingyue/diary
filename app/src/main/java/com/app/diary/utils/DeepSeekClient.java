package com.app.diary.utils;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.*;

/**
 * 用于访问DeepSeek的API接口工具类
 * 作者：孔立斌 15602384971
 */
public class DeepSeekClient {
    private static final String API_KEY = "sk-a2ce892853ff4330bb9879338979e44a"; // 替换为你的 DeepSeek API Key
    private static final String API_URL = "https://api.deepseek.com/v1/chat/completions";
    private static final String MODEL = "deepseek-chat";    // DeepSeek-V3
    // private final String MODEL = "deepseek-reasoner";    // DeepSeek-R1
    private static final OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .build();

    private static final List<JSONObject> messages = new ArrayList<>(); // 存储对话上下文

    /**
     * 发送消息到 DeepSeek API，并返回 AI 的回复
     *
     * @param userInput 用户输入的内容
     * @return AI 返回的消息（包含成功或失败信息）
     */
    public static String sendMessage(String userInput) {
        try {
            messages.add(new JSONObject().put("role", "user").put("content", userInput));

            // 构造请求体
            JSONObject requestBody = new JSONObject();
            requestBody.put("model", MODEL);
            requestBody.put("messages", new JSONArray(messages));
            requestBody.put("max_tokens", 1024);
            requestBody.put("temperature", 0.7);

            RequestBody body = RequestBody.create(requestBody.toString(), MediaType.get("application/json"));
            Request request = new Request.Builder()
                    .url(API_URL)
                    .post(body)
                    .addHeader("Authorization", "Bearer " + API_KEY)
                    .addHeader("Content-Type", "application/json")
                    .build();

            // 发送请求并获取响应
            Response response = client.newCall(request).execute();
            if (response.isSuccessful() && response.body() != null) {
                String responseData = response.body().string();
                JSONObject jsonResponse = new JSONObject(responseData);
                String reply = jsonResponse.getJSONArray("choices")
                        .getJSONObject(0)
                        .getJSONObject("message")
                        .getString("content");

                // 记录 AI 回复到对话历史
                messages.add(new JSONObject().put("role", "assistant").put("content", reply));

                return reply; // 返回 AI 回复
            } else {
                return "请求失败，错误代码: " + response.code();
            }
        } catch (Exception e) {
            Log.e("DeepSeekClient", e.toString());
            return "服务器繁忙，请稍后再试。";
        }
    }
}
