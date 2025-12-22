/**
 *
 */
package ru.checkdev.notification.service;

import org.junit.jupiter.api.Test;
import ru.checkdev.notification.model.Notify;


/**
 * @author olegbelov
 *
 */
@Deprecated
public class NotificationServiceTest {

    @Test
    public void whenReadQueue() {
        TemplateService templates = new TemplateService(null) {
            @Override
            public Notify send(Notify user) {
                System.out.println(user.getEmail());
                System.out.println(user.getTemplate());
                return user;
            }
        };
    }


}
