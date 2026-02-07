package com.chat.server.service;

import com.chat.server.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class AIService {
    private final RestTemplate template;

    @Value("${groq.ai.key}")
    private String apiKey;

    @Value("${groq.ai.url}")
    private String baseUrl;

    public String getLegalAdvice(String content, User user){
        String url = baseUrl + "/chat/completions";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        String systemPrompt = """
                Siz professional yuridik yordamchisiz.
                Siz faqat yuridik savollarga javob berasiz.
                Agar savol qonunga aloqador bo'lmasa, xushmuomalalik bilan rad eting.
                Faqat umumiy yuridik ma'lumotlarni taqdim eting.
                Javoblarni faqat o'zbek tilida qaytaring.
                Javoblarni lex.uz, o'zbekiston qonunchiligiga asoslanib tavsiya bering
                """;
        Map<String, Object> body = new HashMap<>();
        body.put("model","llama-3.3-70b-versatile");
        List<Map<String,String>> messages = new ArrayList<>();

        messages.add(Map.of(
                "role","system",
                "content", systemPrompt
        ));
        messages.add(Map.of("role","user","content",content));

        body.put("messages",messages);

        HttpEntity<Map<String,Object>> request = new HttpEntity<>(body,headers);

        ResponseEntity<Map> response = template.postForEntity(url,request,Map.class);

        Map<String,Object> responseBody = response.getBody();

        List<Map<String, Object>> choices =
                (List<Map<String, Object>>) responseBody.get("choices");

        Map<String, Object> message =
                (Map<String, Object>) choices.get(0).get("message");

        return message.get("content").toString();
    }
}
