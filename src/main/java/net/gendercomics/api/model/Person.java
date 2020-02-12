package net.gendercomics.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.util.StringUtils;

@Getter
@Setter
@Document("persons")
public class Person {

    private String id;
    private String firstName;
    private String lastName;
    private String wikiData;
    private MetaData metaData;

    @Override
    public String toString() {
        return StringUtils.trimWhitespace(firstName + " " + lastName);
    }

}
