package net.gendercomics.api.model;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document("persons")
@CompoundIndexes(value = {
        @CompoundIndex(name = "person_fullname_index", def = "{'lastName':1, 'firstName':1}", unique = true, sparse = true)
})
@ApiModel(description = "persons involved in the process of creating comics")
public class Person {

    private String id;

    @DBRef
    @ApiModelProperty(value = "list of names", required = true)
    private List<Name> names;

    @Indexed(name = "wikidata_index", unique = true, sparse = true)
    @ApiModelProperty(value = "wikidata")
    private String wikiData;

    @Deprecated
    @ApiModelProperty(value = "first name")
    private String firstName;

    @Deprecated
    @ApiModelProperty(value = "last name")
    private String lastName;

    @Deprecated
    @ApiModelProperty(value = "pseudonym")
    private String pseudonym;

    @ApiModelProperty(value = "metadata", required = true)
    private MetaData metaData;

}
