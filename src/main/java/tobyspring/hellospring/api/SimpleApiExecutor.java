package tobyspring.hellospring.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.stream.Collectors;

public class SimpleApiExecutor implements ApiExecutor{
    @Override
    public String execute(URI uri) throws IOException {

        HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();

        try ( BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            return bufferedReader.lines().collect(Collectors.joining());
        }
    }
}
