package ru.checkdev.notification.telegram.action;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.checkdev.notification.telegram.config.TgConfig;
import ru.checkdev.notification.telegram.service.TgAuthCallWebClient;


/**
 * 3. Мидл
 * Класс реализует пункт меню поиска ФИО пользователя, почту по его аккаунту в телеграм бот
 *
 * @author Qatya
 * @since 2025
 */
@AllArgsConstructor
@Slf4j
public class CheckAction implements Action {
    private static final String URL_PERSON = "/person/byUserName/";
    private final TgConfig tgConfig = new TgConfig("tg", 8);
    private final TgAuthCallWebClient authCallWebClient;
    private final String urlSiteAuth;

    @Override
    public BotApiMethod<Message> handle(Message message) {
        var chatId = message.getChatId().toString();
        var text = "Введите username пользователя для поиска его ФИО и email: ";
        return new SendMessage(chatId, text);
    }

    /**
     * Метод формирует ответ пользователю.
     * Весь метод разбит на 4 этапа проверки.
     * 2. Отправка данных в сервис Auth и если сервис не доступен сообщаем
     * 3. Если сервис доступен, получаем от него ответ и обрабатываем его.
     *
     * @param message Message
     * @return BotApiMethod<Message>
     */
    @Override
    public BotApiMethod<Message> callback(Message message) throws JsonProcessingException {
        var chatId = message.getChatId().toString();
        var accountName = message.getText();
        var text = "";
        var sl = System.lineSeparator();

        Object result;
        try {
            result = authCallWebClient.doGet(URL_PERSON + accountName).block();

        } catch (Exception e) {
            log.error("WebClient doGet error: {}", e.getMessage());
            text = "Сервис не доступен попробуйте позже" + sl
                    + "/check";
            return new SendMessage(chatId, text);
        }
        var resultPerson = tgConfig.mapObjectToJson(result);

        var resultWithoutBrackets = resultPerson.substring(1, resultPerson.length() - 1);

        if (resultWithoutBrackets.isEmpty()) {
            text = "Такой пользователь не зарегистрирован";
            return new SendMessage(chatId, text);
        }
        text = String.format("Пользователь/и с username : %s - %s",
                accountName, resultWithoutBrackets);
        return new SendMessage(chatId, text);
    }
}
