package ru.checkdev.notification.service;

import lombok.Data;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.checkdev.notification.model.Template;
import ru.checkdev.notification.repository.TemplateRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author olegbelov
 */
@ExtendWith(MockitoExtension.class)
@SpringBootTest
@Data
@ActiveProfiles("test")
public class TemplateServiceTest {

    @Autowired
    private TemplateService templateService;

    @Autowired
    private TemplateRepository templateRepository;

    @Test
    public void whenGetAllTemplatesReturnContainsValue() {
        Template template = this.templateService.save(new Template("TestSubject", "TestBody"));
        List<Template> result = this.templateService.findAll();
        assertTrue(result.contains(template));
    }

    @Test
    public void requestByIDReturnCorrectValue() {
        Template template = this.templateService.save(new Template("TestSubjectByID", "TestBodyByID"));
        Template result = this.templateService.getById(template.getId());
        assertEquals(result, template);
    }

    @Test
    public void whenDeleteTemplateItIsNotExist() {
        Template template = this.templateService.save(new Template("TestSubjectForDelete", "TestBodyForDelete"));
        this.templateService.delete(template.getId());
        List<Template> result = this.templateService.findAll();
        assertFalse(result.contains(template));
    }

}
