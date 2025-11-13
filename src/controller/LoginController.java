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
    private AuthService authService;

    public LoginController(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if ("POST".equals(exchange.getRequestMethod())) {
            // Leer el body de la petición
            InputStream inputStream = exchange.getRequestBody();
            String body = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

            // Parsear JSON: {"username":"admin","password":"1234"}
            JSONObject json = new JSONObject(body);
            String username = json.getString("username");
            String password = json.getString("password");

            // Validar login
            boolean ok = authService.login(username, password);

            // Respuesta en JSON
            JSONObject responseJson = new JSONObject();
            responseJson.put("success", ok);
            responseJson.put("message", ok ? "Login OK" : "Login Failed");

            byte[] responseBytes = responseJson.toString().getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().add("Content-Type", "application/json");
            exchange.sendResponseHeaders(ok ? 200 : 401, responseBytes.length);

            OutputStream os = exchange.getResponseBody();
            os.write(responseBytes);
            os.close();
        } else {
            // Si no es POST, devolver error
            String response = "Método no permitido";
            exchange.sendResponseHeaders(405, response.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}
