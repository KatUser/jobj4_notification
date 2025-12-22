/**
 *
 */
package ru.checkdev.notification.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.checkdev.notification.model.Template;

/**
 * @author olegbelov
 * @since 24.12.2016
 */
@Repository
public interface TemplateRepository extends CrudRepository<Template, Integer> {
    Template findByType(String key);
}
