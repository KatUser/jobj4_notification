package ru.checkdev.notification.telegram.action;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.checkdev.notification.domain.PersonDTO;
import ru.checkdev.notification.telegram.service.TgAuthCallWebClient;
import ru.checkdev.notification.telegram.service.TgMockCallWebClient;

@AllArgsConstructor
@Slf4j
public class BindAction implements Action {
    private static final String URL_PERSON_BY_EMAIL = "/person/byEmail/";
    private static final String URL_BIND = "/bind";
    private final TgAuthCallWebClient authCallWebClient;
    private final TgMockCallWebClient mockCallWebClient;
    private final String urlSiteAuth;

    @Override
    public BotApiMethod<Message> handle(Message message) {
        var chatId = message.getChatId().toString();
        var textLogin = "Введите емейл и пароль через пробел, "
                + "чтобы привязать аккаунт telegram к платформе CheckDev:";
        return new SendMessage(chatId, textLogin);
    }

    @Override
    public BotApiMethod<Message> callback(Message message) {
        var chatId = message.getChatId().toString();
        var credentials = message.getText();
        var email = credentials.split(" ")[0];
        var password = credentials.split(" ")[1];
        var text = "";
        var sl = System.lineSeparator();

        PersonDTO result;

        try {
            result = authCallWebClient.doGetSinglePerson(
                    URL_PERSON_BY_EMAIL + email + "/" + password
            )
                    .block();

        } catch (Exception e) {
            log.error("WebClient doGet error: {}", e.getMessage());
            text = "Сервис не доступен попробуйте позже" + sl
                    + "/bind";
            return new SendMessage(chatId, text);
        }
        if (result == null) {
            text = "Такой пользователь не зарегистрирован";
            return new SendMessage(chatId, text);
        }
        if (result.getPassword().equals(password) || result.getEmail().equals(email)) {

            try {
                mockCallWebClient
                        .doPostToBind(URL_BIND, email)
                        .block();
                text = "Ваш аккаунт привязан к платформе CheckDev!";
                return new SendMessage(chatId, text);
            } catch (Exception e) {
                log.error("WebClient doPost error: {}", e.getMessage());
            }
        }

        return new SendMessage(chatId, "shit");

    }

}

