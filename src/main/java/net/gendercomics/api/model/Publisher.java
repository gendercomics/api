package net.gendercomics.api.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document("publishers")
public class Publisher {

    private String id;
    private String name;
    private String url;
    private MetaData metaData;
}
