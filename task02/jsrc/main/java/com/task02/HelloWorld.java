package com.task02;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.syndicate.deployment.annotations.lambda.LambdaHandler;
import com.syndicate.deployment.model.RetentionSetting;

import java.util.HashMap;
import java.util.Map;

@LambdaHandler(
    lambdaName = "hello_world",
    roleName = "hello_world-role",
    isPublishVersion = true,
    aliasName = "${lambdas_alias_name}",
    logsExpiration = RetentionSetting.SYNDICATE_ALIASES_SPECIFIED
)
public class HelloWorld implements RequestHandler<Map<String, Object>, Map<String, Object>> {

    public Map<String, Object> handleRequest(Map<String, Object> event, Context context) {
        String path = (String) event.getOrDefault("rawPath", "");
        Map<String, Object> requestContext = (Map<String, Object>) event.get("requestContext");
        Map<String, Object> http = (requestContext != null) ? (Map<String, Object>) requestContext.get("http") : null;
        String method = (http != null) ? (String) http.get("method") : "";

        Map<String, Object> response = new HashMap<>();

        if ("/hello".equals(path) && "GET".equalsIgnoreCase(method)) {
            response.put("statusCode", 200);
            response.put("message", "Hello from Lambda");
        } else {
            response.put("statusCode", 400);
            response.put("message", "Bad request syntax or unsupported method. Request path: " + path + ". HTTP method: " + method);
        }

        return response;
    }
}
