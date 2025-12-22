package ru.checkdev.notification.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.checkdev.notification.model.Notify;
import ru.checkdev.notification.model.Template;
import ru.checkdev.notification.service.MessagingService;
import ru.checkdev.notification.service.TemplateService;

import java.util.List;

/**
 * @author Petr Arsentev (parsentev@yandex.ru)
 * @version $Id$
 * @since 0.1
 */
@RestController
@RequestMapping("/template")
public class TemplateController {

    private final TemplateService templates;

    private final MessagingService messagingService;

    private final String access;

    @Autowired
    public TemplateController(@Value("${access.key}") String access, final TemplateService templates, MessagingService messagingService) {
        this.templates = templates;
        this.messagingService = messagingService;
        this.access = access;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/")
    public List<Template> findAll() {
        return this.templates.findAll();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/find")
    public Template updateView(@RequestParam(required = false) Integer id) {
        return id != null ? templates.findById(id) : new Template();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/")
    public Template create(@RequestBody Template template) {
        return this.templates.save(template);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/")
    public boolean delete(@RequestParam int id) {
        return this.templates.delete(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/send")
    public Notify send(@RequestBody Notify notify) {
        return this.templates.send(notify);
    }

    @PostMapping("/queue")
    public Notify queue(@RequestParam("access") String access, @RequestBody Notify notify) {
        if (this.access.equals(access)) {
            this.messagingService.put(notify);
        }
        return notify;
    }

    @GetMapping("/ping")
    public String ping() {
        return "{}";
    }
}
