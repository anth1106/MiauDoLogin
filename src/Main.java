import com.sun.net.httpserver.HttpServer;
import controller.LoginController;
import service.AuthService;

void main() throws Exception {
    AuthService authService = new AuthService();
    authService.register("admin", "1234"); // ejemplo

    HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
    server.createContext("/login", new LoginController(authService));
    server.start();

    IO.println("Servidor iniciado en http://localhost:8080/login");
}