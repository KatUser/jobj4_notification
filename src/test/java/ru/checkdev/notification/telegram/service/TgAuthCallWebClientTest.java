package ru.checkdev.notification.telegram.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.checkdev.notification.domain.PersonDTO;

import java.util.Calendar;
import java.util.List;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.when;

/**
 * Testing TgAuthCallWebClient
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 06.10.2023
 */
@ExtendWith(MockitoExtension.class)
class TgAuthCallWebClientTest {
    private static final String URL = "http://testurl:15000";
    @Mock
    private WebClient webClientMock;
    @Mock
    private WebClient.RequestHeadersSpec requestHeadersMock;
    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriMock;
    @Mock
    private WebClient.RequestBodySpec requestBodyMock;
    @Mock
    private WebClient.RequestBodyUriSpec requestBodyUriMock;
    @Mock
    private WebClient.ResponseSpec responseMock;

    private TgAuthCallWebClient tgAuthCallWebClient;

    @BeforeEach
    void setUp() {
        tgAuthCallWebClient = new TgAuthCallWebClient(URL);
        tgAuthCallWebClient.setWebClient(webClientMock);
    }


    @Test
    void whenDoGetThenByPersonIdReturnPersonDTOList() {
        int personId = 100;
        var created = new Calendar.Builder()
                .set(Calendar.DAY_OF_MONTH, 23)
                .set(Calendar.MONTH, Calendar.OCTOBER)
                .set(Calendar.YEAR, 2023)
                .build();
        var personDto = List.of(new PersonDTO("mail", "password", true, emptyList(), created));
        when(webClientMock.get()).thenReturn(requestHeadersUriMock);
        when(requestHeadersUriMock.uri("/person/" + personId)).thenReturn(requestHeadersMock);
        when(requestHeadersMock.retrieve()).thenReturn(responseMock);
        when(responseMock.bodyToMono(new ParameterizedTypeReference<List<PersonDTO>>() {})).
                thenReturn(Mono.just(personDto));
        var actual = tgAuthCallWebClient.doGet("/person/" + personId).block();
        assertThat(actual).isEqualTo(personDto);
    }

    @Test
    void whenDoGetByAccountNameThenReturnListOfPersonDTO() {
        var created1 = new Calendar.Builder()
                .set(Calendar.DAY_OF_MONTH, 23)
                .set(Calendar.MONTH, Calendar.OCTOBER)
                .set(Calendar.YEAR, 2023)
                .build();
        var created2 = new Calendar.Builder()
                .set(Calendar.DAY_OF_MONTH, 23)
                .set(Calendar.MONTH, Calendar.OCTOBER)
                .set(Calendar.YEAR, 2023)
                .build();

        var accountName = "senior";

        var personDto1 = new PersonDTO("mail", "password", true, emptyList(), created1);
        var personDto2 = new PersonDTO("email", "pass", true, emptyList(), created2);
        personDto1.setFullname("fullname1");
        personDto2.setFullname("fullname2");


        when(webClientMock.get()).thenReturn(requestHeadersUriMock);
        when(requestHeadersUriMock.uri("/person/" + accountName)).thenReturn(requestHeadersMock);
        when(requestHeadersMock.retrieve()).thenReturn(responseMock);
        when(responseMock.bodyToMono(new ParameterizedTypeReference<List<PersonDTO>>() {})).
                thenReturn(Mono.just(List.of(personDto1, personDto2)));
        var actual = tgAuthCallWebClient.doGet("/person/" + accountName).block();
        assertThat(actual).isEqualTo(List.of(personDto1, personDto2));
    }

    @Test
    void whenDoGetByAccountNameThenReturnEmptyListOfPersonDTO() {
        var accountName = "senior";

        when(webClientMock.get()).thenReturn(requestHeadersUriMock);
        when(requestHeadersUriMock.uri("/person/" + accountName)).thenReturn(requestHeadersMock);
        when(requestHeadersMock.retrieve()).thenReturn(responseMock);
        when(responseMock.bodyToMono(new ParameterizedTypeReference<List<PersonDTO>>() {})).
                thenReturn(Mono.just(List.of()));
        var actual = tgAuthCallWebClient.doGet("/person/" + accountName).block();
        assertThat(actual).isEqualTo(emptyList());
    }


    @Test
    void whenDoGetThenReturnExceptionError() {
        int personId = 100;
        when(webClientMock.get()).thenReturn(requestHeadersUriMock);
        when(requestHeadersUriMock.uri("/person/" + personId)).thenReturn(requestHeadersMock);
        when(requestHeadersMock.retrieve()).thenReturn(responseMock);
        when(responseMock.bodyToMono(PersonDTO.class)).thenReturn(Mono.error(new Throwable("Error")));
        assertThatThrownBy(() -> tgAuthCallWebClient.doGet("/person/" + personId).block())
                .isInstanceOf(Throwable.class)
                .hasMessageContaining("Error");
    }

    @Test
    void whenDoPostSavePersonThenReturnNewPerson() {
        var created = new Calendar.Builder()
                .set(Calendar.DAY_OF_MONTH, 23)
                .set(Calendar.MONTH, Calendar.OCTOBER)
                .set(Calendar.YEAR, 2023)
                .build();
        var personDto = new PersonDTO("mail", "password", true, null, created);
        when(webClientMock.post()).thenReturn(requestBodyUriMock);
        when(requestBodyUriMock.uri("/person/created")).thenReturn(requestBodyMock);
        when(requestBodyMock.bodyValue(personDto)).thenReturn(requestHeadersMock);
        when(requestHeadersMock.retrieve()).thenReturn(responseMock);
        when(responseMock.bodyToMono(Object.class)).thenReturn(Mono.just(personDto));
        Mono<Object> objectMono = tgAuthCallWebClient.doPost("/person/created", personDto);
        PersonDTO actual = (PersonDTO) objectMono.block();
        assertThat(actual).isEqualTo(personDto);
    }

    @Test
    void whenDoGetByEmailThenReturnPersonDTO() {
        var created1 = new Calendar.Builder()
                .set(Calendar.DAY_OF_MONTH, 23)
                .set(Calendar.MONTH, Calendar.OCTOBER)
                .set(Calendar.YEAR, 2023)
                .build();

        var accountEmail = "senior@pomidor.ru";
        var accountPassword = "password";

        var personDto1 = new PersonDTO(accountEmail, accountPassword, true, emptyList(), created1);

        when(webClientMock.get()).thenReturn(requestHeadersUriMock);
        when(requestHeadersUriMock.uri("/person/byEmail/" + accountEmail + "/" + accountPassword)).thenReturn(requestHeadersMock);
        when(requestHeadersMock.retrieve()).thenReturn(responseMock);
        when(responseMock.bodyToMono(PersonDTO.class)).thenReturn(Mono.just(personDto1));
        var actual = tgAuthCallWebClient.doGetSinglePerson("/person/byEmail/" + accountEmail + "/" + accountPassword).block();
        assertThat(actual).isEqualTo(personDto1);
    }

    @Test
    void whenDoGetByNonExistingEmailThenReturnNull() {

        var accountEmail = "senior@pomidor.ru";
        var accountPassword = "password";

        when(webClientMock.get()).thenReturn(requestHeadersUriMock);
        when(requestHeadersUriMock.uri("/person/byEmail/" + accountEmail + "/" + accountPassword)).thenReturn(requestHeadersMock);
        when(requestHeadersMock.retrieve()).thenReturn(responseMock);
        when(responseMock.bodyToMono(PersonDTO.class)).thenReturn(Mono.empty());
        var actual = tgAuthCallWebClient.doGetSinglePerson("/person/byEmail/" + accountEmail + "/" + accountPassword).block();
        assertThat(actual).isEqualTo(null);
    }





}