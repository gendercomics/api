package net.gendercomics.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document("names")
@CompoundIndexes(value = {
        @CompoundIndex(name = "individual_name_index", def = "{'lastName':1, 'firstName':1}", unique = true, sparse = true)
})
public class Name implements Comparable<Name> {

    private String id;

    @ApiModelProperty(value = "first name")
    private String firstName;

    @ApiModelProperty(value = "last name")
    private String lastName;

    @ApiModelProperty(value = "name value (firstname + lastname or single name value")
    private String name;

    @ApiModelProperty(value = "true if name is a pseudonym")
    private boolean pseudonym;

    @ApiModelProperty(value = "true if name entity shall be searchable (e.g. to be referenced as comic creator)")
    private boolean searchable;

    @Override
    public int compareTo(Name o) {
        int last = 0;
        last = this.lastName.compareToIgnoreCase(o.lastName);
        return last == 0 ? this.firstName.compareToIgnoreCase(o.firstName) : last;

        // TODO implement comparison logic
    }
}
