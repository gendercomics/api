package net.gendercomics.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document("publishers")
public class Publisher {

    private String id;
    private String name;
    private String url;
    private MetaData metaData;
}
