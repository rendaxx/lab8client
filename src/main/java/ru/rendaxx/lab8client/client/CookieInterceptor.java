package ru.rendaxx.lab8client.client;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import ru.rendaxx.lab8client.util.UserSession;

import java.io.IOException;

@Getter
public class CookieInterceptor implements ClientHttpRequestInterceptor {

    private UserSession userSession;

    public CookieInterceptor(UserSession userSession) {
        this.userSession = userSession;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        if (userSession.getCookie() != null) {
            request.getHeaders().add(HttpHeaders.COOKIE, userSession.getCookie());
        }

        ClientHttpResponse response = execution.execute(request, body);

        if (userSession.getCookie() == null) {
            userSession.setCookie(response.getHeaders().getFirst(HttpHeaders.SET_COOKIE));
        }

        return response;
    }
}
