import com.sun.net.httpserver.HttpServer;
import controller.LoginController;
import dao.UserDao;
import service.AuthService;

void main() throws Exception {
    UserDao userDao = startUserDao();
    AuthService authService = new AuthService(userDao);
    //authService.register("admin", "1234"); // example

    runServer(authService);
}

private static UserDao startUserDao() {
    String csvPath = System.getenv("USERS_CSV_PATH");
    if (csvPath == null) {
        throw new RuntimeException("environment variable USERS_CSV_PATH not defined");
    }
    return new UserDao(csvPath);
}

private static void runServer(AuthService authService) throws IOException {
    HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
    server.createContext("/login", new LoginController(authService));
    server.start();

    IO.println("Server started in http://localhost:8080");
}