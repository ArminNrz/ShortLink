package com.neshan.shortLink.security;

import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
@Slf4j
@RequiredArgsConstructor
public class SecurityUtil {

    private final Environment environment;

    public void handleUnAuthorizeException(Exception exception, HttpServletResponse response) throws IOException {

        log.error("There is an error: {}", exception.getMessage());
        try {
            throw exception;
        }
        catch (AccessDeniedException accessDeniedException) {
            throw Problem.valueOf(Status.FORBIDDEN, accessDeniedException.getMessage());
        }
        catch (SignatureVerificationException | TokenExpiredException signatureVerificationException) {
            handleAuthException(response, signatureVerificationException, HttpStatus.FORBIDDEN);
            throw Problem.valueOf(Status.NOT_ACCEPTABLE, signatureVerificationException.getMessage());

        } catch (Exception e) {
            throw Problem.valueOf(Status.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    public void handleAuthException(HttpServletResponse response, RuntimeException failed, HttpStatus status) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        response.setStatus(status.value());
        response.setContentType(APPLICATION_JSON_VALUE);
        Map<String, Object> data = buildResponseError(failed.getMessage(), status);

        response.getOutputStream()
                .println(objectMapper.writeValueAsString(data));
    }

    private Map<String, Object> buildResponseError(String message, HttpStatus status) {
        Map<String, Object> data = new HashMap<>();
        data.put(
                "title",
                status.getReasonPhrase());
        data.put(
                "status",
                status.value());
        data.put(
                "detail",
                message);
        return data;
    }

    public String getSecretKey() {
        return environment.getProperty("neshan.secret");
    }
}
