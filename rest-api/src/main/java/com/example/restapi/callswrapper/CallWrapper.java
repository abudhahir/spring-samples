package com.example.restapi.callswrapper;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

/***
 * wrap the rest template, to make calls easier
 */
@Component
public class CallWrapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(CallWrapper.class);

    private RestTemplate restTemplate;
    private ObjectMapper objectMapper;

    @Autowired
    public CallWrapper(RestTemplateBuilder restTemplateBuilder, ObjectMapper objectMapper) {
        this.restTemplate = restTemplateBuilder.build();
        this.objectMapper = objectMapper;
    }

    /**
     *
     * @param clazz
     * @param url
     * @param uriVariables
     * @param <T>
     * @return
     */
    public <T> T getForEntity(Class<T> clazz, String url, Object... uriVariables) {
        try {
            ResponseEntity<String> responseAsString = restTemplate.getForEntity(url, String.class, uriVariables);
            JavaType javaType = objectMapper.getTypeFactory().constructType(clazz);
            return readValue(responseAsString, javaType);
        } catch (HttpClientErrorException exception) {
            if (exception.getStatusCode() == HttpStatus.NOT_FOUND) {
                LOGGER.info("No data found for {}", url);
            } else {
                LOGGER.info("Exception [{}] while invoking {}", exception.getMessage(), url);
            }
        }
        return null;
    }

    /**
     *
     * @param clazz
     * @param url
     * @param uriVariables
     * @param <T>
     * @return
     */
    public <T> List<T> getForList(Class<T> clazz, String url, Object... uriVariables) {
        try {
            ResponseEntity<String> responseAsString = restTemplate.getForEntity(url, String.class, uriVariables);
            CollectionType collectionType = objectMapper.getTypeFactory().constructCollectionType(List.class, clazz);
            return readValue(responseAsString, collectionType);
        } catch (HttpClientErrorException exception) {
            if (exception.getStatusCode() == HttpStatus.NOT_FOUND) {
                LOGGER.info("No data found for {}", url);
            } else {
                LOGGER.info("Exception [{}] while invoking {}", exception.getMessage(), url);
            }
        }
        return null;
    }

    /**
     *
     * @param clazz
     * @param url
     * @param body
     * @param uriVariables
     * @param <T>
     * @param <R>
     * @return
     */
    public <T, R> T postForEntity(Class<T> clazz, String url, R body, Object... uriVariables) {
        HttpEntity<R> request = new HttpEntity<>(body);
        ResponseEntity<String> responseAsString = restTemplate.postForEntity(url, request, String.class, uriVariables);
        JavaType javaType = objectMapper.getTypeFactory().constructType(clazz);
        return readValue(responseAsString, javaType);
    }

    /**
     *
     * @param clazz
     * @param url
     * @param body
     * @param uriVariables
     * @param <T>
     * @param <R>
     * @return
     */
    public <T, R> T putForEntity(Class<T> clazz, String url, R body, Object... uriVariables) {
        HttpEntity<R> request = new HttpEntity<>(body);
        ResponseEntity<String> responseAsString = restTemplate.exchange(url, HttpMethod.PUT, request, String.class, uriVariables);
        JavaType javaType = objectMapper.getTypeFactory().constructType(clazz);
        return readValue(responseAsString, javaType);
    }

    /**
     *
     * @param url
     * @param uriVariables
     */
    public void delete(String url, Object... uriVariables) {
        try {
            restTemplate.delete(url, uriVariables);

        } catch (RestClientException exception) {
            LOGGER.info(exception.getMessage());
        }

    }



    /**
     * helper method
     * @param response
     * @param javaType
     * @param <T>
     * @return
     */
    private <T> T readValue(ResponseEntity<String> response, JavaType javaType) {
        T result = null;
        if (response.getStatusCode() == HttpStatus.OK ||
                response.getStatusCode() == HttpStatus.CREATED) {
            try {
                result = objectMapper.readValue(response.getBody(), javaType);
            } catch (IOException e) {
                LOGGER.info(e.getMessage());
            }
        } else {
            LOGGER.info("No data found {}", response.getStatusCode());
        }
        return result;
    }

}
