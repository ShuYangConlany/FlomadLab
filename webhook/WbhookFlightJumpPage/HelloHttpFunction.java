package gcfv2;

import java.io.BufferedWriter;

import com.google.cloud.functions.HttpFunction;
import com.google.cloud.functions.HttpRequest;
import com.google.cloud.functions.HttpResponse;
import java.io.IOException;
import com.google.cloud.dialogflow.cx.v3beta1.WebhookResponse;
import com.google.protobuf.Struct;
import com.google.protobuf.Value;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.Map;
import java.util.HashMap;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.protobuf.util.JsonFormat;

public class HelloHttpFunction implements HttpFunction {
  private static final Logger logger = Logger.getLogger(HelloHttpFunction.class.getName());
  public void service(final HttpRequest request, final HttpResponse response) throws Exception {
    logger.info("进来了哦");
    final BufferedWriter writer = response.getWriter();
    // writer.write("Hello world!");

    String requestBody = request.getReader().lines().collect(Collectors.joining());
    logger.info("Request Body: " + requestBody);
    // 解析JSON字符串为JsonObject
    JsonObject json = JsonParser.parseString(requestBody).getAsJsonObject();
    logger.info("JSON Object: " + json.toString());
    String currentPage = json.getAsJsonObject("pageInfo").get("currentPage").getAsString();
    logger.info("currentPage:"+currentPage);
    // 假设Dialogflow发送的参数在一个名为'parameters'的JSON对象中
    JsonObject intentInfo = json.getAsJsonObject("intentInfo");
    JsonObject parametersJson = null;
    Map<String, Value> parameters = new HashMap<>();
    JsonObject sessionInfo = json.getAsJsonObject("sessionInfo");
    if (sessionInfo != null && sessionInfo.has("parameters")) {
        JsonObject sessionParametersJson = sessionInfo.getAsJsonObject("parameters");
        logger.info("Session Parameters JSON: " + sessionParametersJson.toString());
        for (String key : sessionParametersJson.keySet()) {
            JsonElement value = sessionParametersJson.get(key);
            // 同样，简化处理：仅处理字符串类型的值
            if (value.isJsonPrimitive() && value.getAsJsonPrimitive().isString()) {
                // 如果 intent parameters 已经设置了某个 key，这里会覆盖它
                parameters.put(key, Value.newBuilder().setStringValue(value.getAsString()).build());
            }
        }
    }
    if (intentInfo != null && intentInfo.get("parameters") != null) {
        parametersJson = intentInfo.getAsJsonObject("parameters");
        logger.info("Parameters JSON: " + parametersJson.toString());
        for (String key : parametersJson.keySet()) {
            JsonElement value = parametersJson.get(key);
            // 这里简化处理：仅处理字符串类型的值
            if (value.isJsonPrimitive() && value.getAsJsonPrimitive().isString()) {
                parameters.put(key, Value.newBuilder().setStringValue(value.getAsString()).build());
            }
        }
    }  
    logger.info("parameters" + parameters.toString());
    WebhookResponse webhookResponse = handleWebhookRequest(parameters,currentPage);
    String webhookResponseJson = JsonFormat.printer().print(webhookResponse);
    writer.write(webhookResponseJson);
  }

    public WebhookResponse handleWebhookRequest(Map<String, Value> parameters, String currentPage) {
      // 检查参数A是否存在或满足某个条件
        boolean shouldGoToPageFlightOffers = false; // 假设这是判断条件
        boolean shouldGoToPageLocationReq = false;
        boolean shouldGoToPageDestinationReq = false;
        boolean shouldGoToPageDepTimeReq = false;
        boolean shouldGoToPageReturnTimeReq = false;
        boolean shouldGoToPageReturnAccompanyNumReq = false;
        boolean shouldGoToPageB = false;
        String header = "projects/flomadaiplanner/locations/us-central1/agents/7fb1e868-d836-46d8-b57a-91fd6c0e6d34/flows/00000000-0000-0000-0000-000000000000/pages/";
        String PageFlightOffers=header+"6e7a3853-4000-4ba4-8ff9-f81f0392e280";
        String PageLocationReq= header+"4076f3ef-73dd-425a-9b27-817fb7e77629";
        String PageDestinationReq= header+"edeb0351-814c-44e1-ad47-ca3dc9084c2c";
        String PageDepTimeReq= header+"f98078a1-7e34-4ee9-9618-b9c5727e9acb";
        String PageReturnTimeReq= header+"0820e90e-472b-4778-9655-575c400e095a";
        String PageReturnAccompanyNumReq= header+"4650c5b5-44d9-4e33-a1cc-37c7832027de";
        String PageGetFlight=header+"958c6bea-cadb-4ed9-ae37-a30b61bea928";



        logger.info("Alfffffffff");
        if (parameters.containsKey("isroundtrip")) {
            
            Value isRoundTrip = parameters.get("isroundtrip");
        } else {
            shouldGoToPageFlightOffers = true;
        }
        if (parameters.containsKey("departure_city")) {
            Value departure_city = parameters.get("departure_city");
        } else{
            shouldGoToPageLocationReq = true;
        }
        if (parameters.containsKey("destination_city")) {
            Value destination_city = parameters.get("destination_city");
        } else {
            shouldGoToPageDestinationReq = true;
        }
        if (parameters.containsKey("dep_date")) {
            Value dep_date = parameters.get("dep_date");
        } else {
            shouldGoToPageDepTimeReq = true; 
        }
        if (parameters.containsKey("return_date")) {
            Value return_date = parameters.get("return_date");
        } else {
            shouldGoToPageReturnTimeReq = true;
        }
        if (parameters.containsKey("ticketnum")) {
            Value ticketNum = parameters.get("ticketnum");
        } else {
            shouldGoToPageReturnAccompanyNumReq = true;
        }

        // 构建响应
        WebhookResponse.Builder responseBuilder = WebhookResponse.newBuilder();

        if (shouldGoToPageFlightOffers) {
            logger.info("All 111");
            if (!currentPage.equals(PageFlightOffers)){
                responseBuilder.setTargetPage(PageFlightOffers);
            }
        } else if(shouldGoToPageLocationReq){
            logger.info("All 222");
            logger.info("currentPage:"+currentPage);
            logger.info("PageLocationReq:"+PageLocationReq);
            if (!currentPage.equals(PageLocationReq)){
                logger.info("!!!!!!!!!!!!!!!!!!!!");
                responseBuilder.setTargetPage(PageLocationReq);
            }
        } else if(shouldGoToPageDestinationReq){
            logger.info("All 333");
            if (!currentPage.equals(PageDestinationReq)){
                responseBuilder.setTargetPage(PageDestinationReq);
            }
        } else if(shouldGoToPageDepTimeReq){
            logger.info("All 444");
            if (!currentPage.equals(PageDepTimeReq)){
                responseBuilder.setTargetPage(PageDepTimeReq);
            }

        } else if(shouldGoToPageReturnTimeReq){
            logger.info("All 555");
            if (!currentPage.equals(PageReturnTimeReq)){
                responseBuilder.setTargetPage(PageReturnTimeReq);
            }
        } else if(shouldGoToPageReturnAccompanyNumReq){
            logger.info("All 666");
            if (!currentPage.equals(PageReturnAccompanyNumReq)){
                responseBuilder.setTargetPage(PageReturnAccompanyNumReq);
            }
        } else {
            logger.info("All information collected");
            if (!currentPage.equals(PageGetFlight)){
                responseBuilder.setTargetPage(PageGetFlight);
            }
        }

        // 返回构建的响应
        return responseBuilder.build();
    }
}

