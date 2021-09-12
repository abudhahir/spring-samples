package com.cleveloper.sihttp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.http.dsl.Http;

@Configuration
public class HttpConfiguration {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpConfiguration.class);

    @Bean
    public IntegrationFlow inbound() {
        return IntegrationFlows.from(Http.inboundChannelAdapter("/foo")
                        .requestMapping(m -> m.methods(HttpMethod.POST))
                        .requestPayloadType(String.class))
                .wireTap(i -> i.handle(message ->
                        {
                            LOGGER.info("Message wiretap: [{}]", message.getPayload());
                        }
                ))
                .handle(message -> LOGGER.info("Message handle: [{}]", message.getPayload()))
                .get();
    }
}
