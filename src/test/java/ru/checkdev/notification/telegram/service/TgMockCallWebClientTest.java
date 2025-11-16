package ru.checkdev.notification.telegram.service;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.checkdev.notification.domain.PersonDTO;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TgMockCallWebClientTest {

    private static final String URL = "http://testurl:15000";
    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.RequestBodyUriSpec requestBodyUriSpec;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    private WebClient.RequestBodySpec requestBodySpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    private TgMockCallWebClient tgMockCallWebClient;

    @BeforeEach
    void setUp() {
        tgMockCallWebClient = new TgMockCallWebClient(URL);
        tgMockCallWebClient.setMockWebClient(webClient);
    }

    @Test
    void whenDoPostToBindThenAPersonIsBound() {

        var accountEmail = "senior@pomidor.ru";

        var personDto = PersonDTO.builder().email(accountEmail).build();
        when(webClient.post()).thenReturn(requestBodyUriSpec);

        when(requestBodyUriSpec.uri("/bind")).thenReturn(requestBodySpec);

        when(requestBodySpec.header(any(), any())).thenReturn(requestBodySpec);

        when(requestBodySpec.accept(any())).thenReturn(requestBodySpec);

        when(requestBodySpec.body(any())).thenReturn(requestHeadersSpec);

        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);

        when(responseSpec.bodyToMono(PersonDTO.class)).thenReturn(Mono.just(personDto));

        var actual = tgMockCallWebClient.doPostToBind("/bind", accountEmail).block();

        assertThat(actual).isEqualTo(personDto);

    }

    @Test
    void whenDoPostToUnBindThenAPersonIsUnBound() {

        var accountEmail = "senior@pomidor.ru";

        when(webClient.post()).thenReturn(requestBodyUriSpec);

        when(requestBodyUriSpec.uri("/unbind")).thenReturn(requestBodySpec);

        when(requestBodySpec.header(any(), any())).thenReturn(requestBodySpec);

        when(requestBodySpec.accept(any())).thenReturn(requestBodySpec);

        when(requestBodySpec.body(any())).thenReturn(requestHeadersSpec);

        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);

        when(responseSpec.bodyToMono(Void.class)).thenReturn(Mono.empty());

        var actual = tgMockCallWebClient.doPostToUnbind("/unbind", accountEmail).block();

        assertThat(actual).isEqualTo(null);
    }
}