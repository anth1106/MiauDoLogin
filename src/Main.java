import com.sun.net.httpserver.HttpServer;
import controller.LoginController;
import dao.UserDao;
import service.AuthService;

void main() throws Exception {
    UserDao userDao = startUserDao();
    AuthService authService = new AuthService(userDao);
    //authService.register("bea", "be4rul3s"); // example

    runServer(authService);
}

private static UserDao startUserDao() {
    String userCsvPath = System.getenv("USERS_CSV_PATH");
    if (userCsvPath == null) {
        throw new RuntimeException("environment variable USERS_CSV_PATH not defined");
    }
    return new UserDao(userCsvPath);
}

private static void runServer(AuthService authService) throws IOException {
    HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
    server.createContext("/login", new LoginController(authService));
    server.start();

    IO.println("Server started in http://localhost:8080");
}