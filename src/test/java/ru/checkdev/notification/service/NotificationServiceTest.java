/**
 *
 */
package ru.checkdev.notification.service;

import org.junit.Ignore;
import org.junit.Test;
import ru.checkdev.notification.domain.Notify;


/**
 * @author olegbelov
 *
 */
@Ignore
public class NotificationServiceTest {

    @Test
    public void whenReadQueue() {
        TemplateService templates = new TemplateService(null, null) {
            @Override
            public Notify sendNotification(Notify user) {
                System.out.println(user.getEmail());
                System.out.println(user.getTemplate());
                return user;
            }
        };
    }


}
