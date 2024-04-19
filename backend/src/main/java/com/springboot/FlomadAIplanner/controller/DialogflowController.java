package com.springboot.FlomadAIplanner.controller;

// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RestController;
// import org.springframework.http.ResponseEntity;

// @RestController
// public class DialogflowController {

//     @PostMapping("/dialogflowWebhook")
//     public ResponseEntity<?> dialogflowWebhook(@RequestBody String requestBody) {
//         // TODO: 解析 requestBody 并处理 Dialogflow CX 请求
//         System.out.println("Received: " + requestBody);
//         // 简化示例，直接返回一个简单响应
//         String jsonResponse = """
//         {
//             "fulfillment_response": {
//                 "messages": [
//                     {
//                         "text": {
//                             "text": ["Echo: " + requestBody]
//                         }
//                     }
//                 ]
//             }
//         }
//         """;
//         return ResponseEntity.ok(jsonResponse);
//     }
// }

import java.io.BufferedWriter;

import com.google.cloud.functions.HttpFunction;
import com.google.cloud.functions.HttpRequest;
import com.google.cloud.functions.HttpResponse;
import java.io.BufferedWriter;
import java.io.IOException;

import com.google.cloud.dialogflow.cx.v3.DetectIntentRequest;
import com.google.cloud.dialogflow.cx.v3.DetectIntentResponse;
import com.google.cloud.dialogflow.cx.v3.QueryInput;
import com.google.cloud.dialogflow.cx.v3.QueryResult;
import com.google.cloud.dialogflow.cx.v3.ResponseMessage;
import com.google.cloud.dialogflow.cx.v3.SessionName;
import com.google.cloud.dialogflow.cx.v3.SessionsClient;
import com.google.cloud.dialogflow.cx.v3.TextInput;
import com.google.cloud.dialogflow.cx.v3.SessionsSettings;
import java.util.logging.Logger;
import java.util.stream.Collectors;




public class DialogflowController implements HttpFunction {
    private static final Logger logger = Logger.getLogger(DialogflowController.class.getName());
  @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) throws Exception {
        // deal with location ID problem
        // String locationId = "your-location-id"; // 确保这里使用了正确的locationId
        // SessionsSettings.Builder sessionsSettingsBuilder = SessionsSettings.newBuilder();
        // String endpoint = locationId + "-dialogflow.googleapis.com";
        // sessionsSettingsBuilder.setEndpoint(endpoint);
        // deal with CORs problem
        logger.info("1");
        httpResponse.appendHeader("Access-Control-Allow-Origin", "*");
        httpResponse.appendHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        httpResponse.appendHeader("Access-Control-Allow-Headers", "Content-Type");
        
        if (httpRequest.getMethod().equals("OPTIONS")) {
            httpResponse.setStatusCode(204);
            return;
        }
        // Dialogflow CX 参数
        String projectId = "flomadaiplanner";
        String locationId = "us-central1";
        String agentId = "7fb1e868-d836-46d8-b57a-91fd6c0e6d34";
        String sessionId = "unique-session-id";
        // String endpoint = locationId + "-dialogflow.googleapis.com";
        String endpoint = "us-central1-dialogflow.googleapis.com:443";
        SessionsSettings.Builder sessionsSettingsBuilder = SessionsSettings.newBuilder();
        sessionsSettingsBuilder.setEndpoint(endpoint);
        String userInput = httpRequest.getReader().lines().collect(Collectors.joining());
        // 初始化 SessionsClient
        try (SessionsClient sessionsClient = SessionsClient.create(sessionsSettingsBuilder.build())) {
            // 构造会话路径
            String sessionPath = SessionName.of(projectId, locationId, agentId, sessionId).toString();

            // 创建文本输入
            TextInput.Builder textInputBuilder = TextInput.newBuilder().setText(userInput);

            // 构造查询输入
            QueryInput queryInput = QueryInput.newBuilder().setText(textInputBuilder).setLanguageCode("en-US").build();
            logger.info("2");
            // logger.info("queryInput"+ queryInput.toString());
            // 构造检测意图请求
            DetectIntentRequest detectIntentRequest = DetectIntentRequest.newBuilder()
                    .setSession(sessionPath)
                    .setQueryInput(queryInput)
                    .build();
            logger.info("3");
            // logger.info("detectIntentRequest"+ detectIntentRequest.toString());
            // 发送检测意图请求

            DetectIntentResponse response = sessionsClient.detectIntent(detectIntentRequest);
            logger.info("4");
            QueryResult queryResult = response.getQueryResult();
            logger.info("5");
            // 提取所有文本回复
            StringBuilder fulfillmentTextBuilder = new StringBuilder();
            logger.info("6");
            for (ResponseMessage message : queryResult.getResponseMessagesList()) {
                if (message.hasText()) {
                    for (String textSegment : message.getText().getTextList()) {
                        fulfillmentTextBuilder.append(textSegment).append("\n");
                    }
                }
            }

            // 将充分回复文本写入HTTP响应
            writeResponse(httpResponse, fulfillmentTextBuilder.toString());
        }

        
    }
    private void writeResponse(HttpResponse httpResponse, String responseText) throws IOException {
        httpResponse.setContentType("application/json; charset=UTF-8");
        BufferedWriter writer = httpResponse.getWriter();
        // 确保返回的是有效的JSON格式
        String jsonResponse = "{\"message\": \"" + responseText.trim().replaceAll("\n", "\\n") + "\"}";
        writer.write(jsonResponse);
    }

    // private void writeResponse(HttpResponse httpResponse, String responseText) throws IOException {
    //     httpResponse.setContentType("text/plain; charset=UTF-8");
    //     BufferedWriter writer = httpResponse.getWriter();
    //     writer.write(responseText);
    // }
}






// @Override
// public void service(HttpRequest request, HttpResponse response) throws Exception {
//     String projectId = "flomadaiplanner";
//     String locationId = "us-central1";
//     String agentId = "7fb1e868-d836-46d8-b57a-91fd6c0e6d34"; // 使用实际的 Agent ID
//     String sessionId = "unique-session-id"; // 实际应用中应为每个用户/会话生成唯一的 ID
    
//     try (SessionsClient sessionsClient = SessionsClient.create()) {
//         String sessionPath = SessionsClient.sessionPath(projectId, locationId, agentId, sessionId);

//         String sessionPath = SessionsClient.formatSessionName(projectId, locationId, agentId, sessionId);
//         TextInput.Builder textInput = TextInput.newBuilder().setText("").setLanguageCode("en");
//         QueryInput queryInput = QueryInput.newBuilder().setText(textInput).build();
        
//         DetectIntentRequest detectIntentRequest = DetectIntentRequest.newBuilder()
//                 .setSession(sessionPath)
//                 .setQueryInput(queryInput)
//                 .build();
//         DetectIntentResponse responseDialogflow = sessionsClient.detectIntent(detectIntentRequest);
//         String fulfillmentText = responseDialogflow.getQueryResult().getFulfillmentResponse().getMessagesList().get(0).getText().getText(0);
//         response.getWriter().write(fulfillmentText);
//     }
// }


    // @Override
    // public void service(HttpRequest httpRequest, HttpResponse httpResponse) throws Exception {
    //     String projectId = "your-project-id";
    //     String locationId = "your-location-id";
    //     String agentId = "your-agent-id";
    //     String sessionId = "your-session-id";

    //     try (SessionsClient sessionsClient = SessionsClient.create()) {
    //         SessionName session = SessionName.of(projectId, locationId, agentId, sessionId);
    //         TextInput.Builder textInputBuilder = TextInput.newBuilder().setText("");
    //         QueryInput queryInput = QueryInput.newBuilder()
    //                 .setText(textInputBuilder)
    //                 .setLanguageCode("en") // 正确设置语言代码的位置
    //                 .build();

    //         DetectIntentRequest request = DetectIntentRequest.newBuilder()
    //                 .setSession(session.toString())
    //                 .setQueryInput(queryInput)
    //                 .build();

    //         DetectIntentResponse response = sessionsClient.detectIntent(request);
    //         QueryResult queryResult = response.getQueryResult();
    //         String fulfillmentText = queryResult.getFulfillmentText();

    //         BufferedWriter writer = httpResponse.getWriter();
    //         writer.write(fulfillmentText);
    //     }
    // }