/**
 *
 */
package ru.checkdev.notification.service;

import lombok.AllArgsConstructor;
import org.glassfish.jersey.internal.guava.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.checkdev.notification.model.Notify;
import ru.checkdev.notification.model.Template;
import ru.checkdev.notification.repository.TemplateRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author olegbelov
 * @since 24.12.2016
 */
@Service
@AllArgsConstructor
public class TemplateService {

    @Autowired
    private TemplateRepository templates;

    public List<Template> findAll() {
        return Lists.newArrayList(this.templates.findAll());
    }


    public Template save(Template template) {
        return this.templates.save(template);
    }


    public Template getById(int id) {
        Optional<Template> result = this.templates.findById(id);
        return result.orElseGet(Template::new);
    }

    public Template findById(int id) {
        return this.templates.findById(id).get();
    }

    public boolean delete(int id) {
        var template = new Template();
        template.setId(id);
        this.templates.delete(template);
        return true;
    }

    public Notify send(Notify notify) {
        Template template = this.templates.findByType(notify.getTemplate());
        SimpleGenerator generator = new SimpleGenerator();
        String subject = generator.generate(template.getSubject(), notify.getKeys());
        String body = new SimpleGenerator().generate(template.getBody(), notify.getKeys());
//        new MailConfiguration().send(
//                subject, body, notify.getEmail(),
//                Lists.newArrayList(this.settings.findAll())
//        );
        return notify;
    }

    public void deleteAll() {
        this.templates.deleteAll();
    }
}
