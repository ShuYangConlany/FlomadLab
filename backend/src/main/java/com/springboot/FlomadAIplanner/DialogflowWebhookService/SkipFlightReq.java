package com.springboot.FlomadAIplanner.DialogflowWebhookService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.slf4j.Logger;

@RestController
public class SkipFlightReq {

    // @PostMapping("/SkipFlightReq")
    // public ResponseEntity<?> handleDialogflowWebhook(@RequestBody String requestBody) {
    //     boolean fatherNamePresent = requestBody.contains("fatherName"); // 示例解析逻辑
    //     boolean motherNamePresent = requestBody.contains("motherName"); // 示例解析逻辑

    //     String jsonResponse;
    //     if (fatherNamePresent && motherNamePresent) {
    //         // 如果父母姓名都已提供，直接跳转到下一个逻辑步骤
    //         jsonResponse = constructResponseForNextStep();
    //     } else {
    //         // 如果某些信息缺失，构建包含询问缺失信息的响应
    //         jsonResponse = constructResponseForMissingInfo();
    //     }

    //     return ResponseEntity.ok(jsonResponse);
    // }

    private static final Logger logger = LoggerFactory.getLogger(SkipFlightReq.class);

    @PostMapping("/SkipFlightReq")
    public void handleDialogflowWebhook(@RequestBody String requestBody) {
        // 打印接收到的请求体
        logger.info("Received Dialogflow request: {}", requestBody);
    }

    // private String constructResponseForNextStep() {
    //     return """
    //     {
    //         "followupEventInput": {
    //             "name": "next_step_event",
    //             "languageCode": "en-US"
    //         }
    //     }
    //     """;
    // }

    // private String constructResponseForMissingInfo() {
    //     return """
    //     {
    //         "fulfillmentMessages": [
    //             {
    //                 "text": {
    //                     "text": ["Please provide more info about your parents."]
    //                 }
    //             }
    //         ]
    //     }
    //     """;
    // }
}