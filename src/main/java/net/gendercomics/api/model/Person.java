package net.gendercomics.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.util.StringUtils;

@Getter
@Setter
@Document("persons")
@CompoundIndexes(value = {
        @CompoundIndex(name = "person_fullname_index", def = "{'lastName':1, 'firstName':1}", unique = true)
})
@ApiModel(description = "persons involved in the process of creating comics")
public class Person implements Comparable<Person> {

    private String id;

    @ApiModelProperty(value = "first name")
    private String firstName;

    @ApiModelProperty(value = "last name")
    private String lastName;

    @ApiModelProperty(value = "wikidata")
    private String wikiData;

    @ApiModelProperty(value = "pseudonym")
    private String pseudonym;

    @ApiModelProperty(value = "metadata", required = true)
    private MetaData metaData;

    private String getLastNameOrPseudonym() {
        if (this.lastName != null) {
            return this.lastName;
        }
        return this.pseudonym;
    }

    @Override
    public String toString() {
        return StringUtils.trimWhitespace(firstName + " " + lastName);
    }

    @Override
    public int compareTo(Person o) {
        int last = 0;
        if (getLastNameOrPseudonym() != null) {
            last = this.getLastNameOrPseudonym().compareToIgnoreCase(o.getLastNameOrPseudonym());
        }
        return last == 0 ? this.firstName.compareToIgnoreCase(o.firstName) : last;
    }
}
