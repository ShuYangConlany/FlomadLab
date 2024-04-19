package com.springboot.FlomadAIplanner.controller;

import java.io.BufferedWriter;

import com.google.cloud.functions.HttpFunction;
import com.google.cloud.functions.HttpRequest;
import com.google.cloud.functions.HttpResponse;

import java.io.IOException;
import com.google.cloud.dialogflow.cx.v3beta1.WebhookResponse;
import com.google.protobuf.Struct;
import com.google.protobuf.Value;
import com.google.protobuf.util.JsonFormat;

import java.util.logging.Logger;
import java.util.stream.Collectors;


import java.util.Map;
import java.util.HashMap;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class webhookDecidePage implements HttpFunction {
  private static final Logger logger = Logger.getLogger(webhookDecidePage.class.getName());
  @Override
    public void service(final HttpRequest request, final HttpResponse response) throws Exception {
        // 将请求体解析为字符串
        String requestBody = request.getReader().lines().collect(Collectors.joining());
        // 解析JSON字符串为JsonObject
        JsonObject json = JsonParser.parseString(requestBody).getAsJsonObject();
        // 假设Dialogflow发送的参数在一个名为'parameters'的JSON对象中
        JsonObject parametersJson = json.getAsJsonObject("parameters");
        
        // 将JsonObject转换为Map<String, Value>
        Map<String, Value> parameters = new HashMap<>();
        for (String key : parametersJson.keySet()) {
            JsonElement value = parametersJson.get(key);
            // 这里简化处理：仅处理字符串类型的值
            if (value.isJsonPrimitive() && value.getAsJsonPrimitive().isString()) {
                parameters.put(key, Value.newBuilder().setStringValue(value.getAsString()).build());
            }
        }

        // 调用处理逻辑
        WebhookResponse webhookResponse = handleWebhookRequest(parameters);
        
        // 将WebhookResponse对象转换为JSON字符串
        String webhookResponseJson = JsonFormat.printer().print(webhookResponse);

        // 设置HTTP响应
        BufferedWriter writer = response.getWriter();
        writer.write(webhookResponseJson);
    }

    public WebhookResponse handleWebhookRequest(Map<String, Value> parameters) {
        // 检查参数A是否存在或满足某个条件
        boolean shouldGoToPageB = false; // 假设这是判断条件
        if (parameters.containsKey("A")) {
            // 基于参数A的值或存在性来修改条件
            Value paramA = parameters.get("A");
            // 这里添加你的逻辑，决定是否满足跳转到Page B的条件
            shouldGoToPageB = true; // 假设条件满足
        }

        // 构建响应
        WebhookResponse.Builder responseBuilder = WebhookResponse.newBuilder();

        if (shouldGoToPageB) {
            // 如果条件满足，设置跳转到Page B
            responseBuilder.setTargetPage("projects/your-project/locations/your-location/agents/your-agent/flows/your-flow/pages/PageB");
        } else {
            // 否则，设置跳转到Page C
            responseBuilder.setTargetPage("projects/your-project/locations/your-location/agents/your-agent/flows/your-flow/pages/PageC");
        }

        // 返回构建的响应
        return responseBuilder.build();
    }
}

// public class webhookDecidePage {
//     private static final Logger logger = Logger.getLogger(webhookDecidePage.class.getName());
//     public WebhookResponse handleWebhookRequest(Map<String, Value> parameters) {
//         // 检查参数A是否存在或满足某个条件
//         boolean shouldGoToPageB = false; // 假设这是判断条件
        
//         // logger.info(shouldGoToPageB)
//         if (parameters.containsKey("A")) {
//             // 基于参数A的值或存在性来修改条件
//             Value paramA = parameters.get("A");
//             // 这里添加你的逻辑，决定是否满足跳转到Page B的条件
//             shouldGoToPageB = true; // 假设条件满足
//         }

//         // 构建响应
//         WebhookResponse.Builder responseBuilder = WebhookResponse.newBuilder();

//         if (shouldGoToPageB) {
//             // 如果条件满足，设置跳转到Page B
//             responseBuilder.setTargetPage("projects/your-project/locations/your-location/agents/your-agent/flows/your-flow/pages/PageB");
//         } else {
//             // 否则，设置跳转到Page C
//             responseBuilder.setTargetPage("projects/your-project/locations/your-location/agents/your-agent/flows/your-flow/pages/PageC");
//         }

//         // 返回构建的响应
//         return responseBuilder.build();
//     }
// }