package ru.checkdev.notification.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.Calendar;
import java.util.List;

/**
 * DTO модель класса Person сервиса Auth.
 *
 * @author parsentev
 * @since 25.09.2016
 */

@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class PersonDTO {

    private String email;

    private String fullname;

    private String password;

    @JsonIgnore
    private boolean privacy;

    private List<RoleDTO> roles;

    private Calendar created;

    public PersonDTO(String email,
                     String password,
                     boolean privacy,
                     List<RoleDTO> roles,
                     Calendar created) {
        this.email = email;
        this.password = password;
        this.privacy = privacy;
        this.roles = roles;
        this.created = Calendar.getInstance();
    }

    public PersonDTO(
            String email) {
        this.email = email;
    }

}
