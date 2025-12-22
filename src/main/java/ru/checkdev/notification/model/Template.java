package ru.checkdev.notification.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author olegbelov
 * @since 20.12.2016
 */
@Getter
@Setter
@Entity(name = "template")
@NoArgsConstructor
public class Template {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String type;

    private String subject;

    private String body;

    public Template(String subject, String body) {
        this.subject = subject;
        this.body = body;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Template template = (Template) o;

        return id == template.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
