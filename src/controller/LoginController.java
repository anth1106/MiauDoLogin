package controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONObject;
import service.AuthService;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class LoginController implements HttpHandler {
    private static AuthService authService;

    public LoginController(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if ("POST".equals(exchange.getRequestMethod())) {
            buildLoginResponse(exchange);
        } else {
            buildMethodNotAllowResponse(exchange);
        }
    }

    private static void buildMethodNotAllowResponse(HttpExchange exchange) throws IOException {
        String response = "Method not allowed";
        exchange.sendResponseHeaders(405, response.getBytes().length);
        sendResponse(exchange, response.getBytes());
    }

    private static void buildLoginResponse(HttpExchange exchange) throws IOException {
        boolean ok = loginVerification(exchange);

        JSONObject responseJson = new JSONObject();
        responseJson.put("success", ok);
        responseJson.put("message", ok ? "Login OK" : "Login Failed");

        byte[] responseBytes = responseJson.toString().getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().add("Content-Type", "application/json");
        exchange.sendResponseHeaders(ok ? 200 : 401, responseBytes.length);

        sendResponse(exchange, responseBytes);
    }

    private static void sendResponse(HttpExchange exchange, byte[] responseBytes) throws IOException {
        OutputStream os = exchange.getResponseBody();
        os.write(responseBytes);
        os.close();
    }

    private static boolean loginVerification(HttpExchange exchange) throws IOException {
        InputStream inputStream = exchange.getRequestBody();
        String body = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

        JSONObject json = new JSONObject(body);
        String username = json.getString("username");
        String password = json.getString("password");

        return authService.login(username, password);
    }
}
