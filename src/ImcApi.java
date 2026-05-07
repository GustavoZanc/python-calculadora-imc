import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ImcApi {
    private static final int PORT = 8080;

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);

        server.createContext("/health", ImcApi::handleHealth);
        server.createContext("/imc", ImcApi::handleImc);

        server.start();
        System.out.println("API de IMC rodando em http://localhost:" + PORT);
    }

    private static void handleHealth(HttpExchange exchange) throws IOException {
        if (!exchange.getRequestMethod().equals("GET")) {
            sendJson(exchange, 405, "{\"erro\":\"Método não permitido\"}");
            return;
        }

        sendJson(exchange, 200, "{\"status\":\"ok\",\"service\":\"api-imc-java\"}");
    }

    private static void handleImc(HttpExchange exchange) throws IOException {
        if (!exchange.getRequestMethod().equals("POST")) {
            sendJson(exchange, 405, "{\"erro\":\"Método não permitido\"}");
            return;
        }

        String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
        Double peso = extractNumber(body, "peso");
        Double altura = extractNumber(body, "altura");

        if (peso == null || altura == null || peso <= 0 || altura <= 0) {
            sendJson(exchange, 400, "{\"erro\":\"Informe peso e altura maiores que zero\"}");
            return;
        }

        double imc = peso / (altura * altura);
        String classificacao = classificar(imc);
        String response = String.format(
            "{\"imc\":%.2f,\"classificacao\":\"%s\"}",
            imc,
            classificacao
        );

        sendJson(exchange, 200, response);
    }

    private static Double extractNumber(String json, String fieldName) {
        Pattern pattern = Pattern.compile("\\\"" + fieldName + "\\\"\\s*:\\s*([0-9]+(?:\\.[0-9]+)?)");
        Matcher matcher = pattern.matcher(json);

        if (!matcher.find()) {
            return null;
        }

        return Double.parseDouble(matcher.group(1));
    }

    private static String classificar(double imc) {
        if (imc < 18.5) return "Abaixo do peso";
        if (imc < 25) return "Peso normal";
        if (imc < 30) return "Sobrepeso";
        return "Obesidade";
    }

    private static void sendJson(HttpExchange exchange, int statusCode, String json) throws IOException {
        byte[] response = json.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().add("Content-Type", "application/json; charset=utf-8");
        exchange.sendResponseHeaders(statusCode, response.length);

        try (OutputStream output = exchange.getResponseBody()) {
            output.write(response);
        }
    }
}
