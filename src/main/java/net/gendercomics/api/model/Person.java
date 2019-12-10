package net.gendercomics.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document("persons")
public class Person {

    private String id;
    private String firstname;
    private String lastname;

    @JsonIgnore
    public String getName() {
        return getFirstname() + " " + getLastname();
    }

}
