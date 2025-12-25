/**
 *
 */
package ru.checkdev.notification.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.checkdev.notification.model.Template;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author olegbelov
 * @since 20.12.2016
 */
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TemplateTest {

    @Test
    public void whenDefaultConstructorNotNull() {
        Template template = new Template();
        assertNotNull(template);
    }

    @Test
    public void whenFieldsConstructorNotNull() {
        Template template = new Template("TestSubject", "TestBody");
        assertNotNull(template);
    }

    @Test
    public void whenIDSetAndGetEquals() {
        Template template = new Template("TestSubject", "TestBody");
        template.setId(1);
        assertThat(1, is(template.getId()));
    }


    @Test
    public void whenSubjectTypeSetAndGetEquals() {
        Template template = new Template("TestSubject", "TestBody");
        template.setSubject("NewSubject");
        assertThat("NewSubject", is(template.getSubject()));
    }

    @Test
    public void whenBodyTypeSetAndGetEquals() {
        Template template = new Template("TestSubject", "TestBody");
        template.setBody("NewBody");
        assertThat("NewBody", is(template.getBody()));
    }
}
