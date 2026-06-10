package net.gendercomics.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.beans.Transient;

@Getter
@Setter
@Document("names")
@CompoundIndexes(value = {
        @CompoundIndex(name = "individual_name_index", def = "{'lastName':1, 'firstName':1}", unique = true, sparse = true)
})
public class Name implements Comparable<Name> {

    private String id;

    @Schema(description = "first name")
    private String firstName;

    @Schema(description = "last name")
    private String lastName;

    @Indexed(name = "name_index", unique = true, sparse = true)
    @Schema(description = "name value")
    private String name;

    @Schema(description = "true if name is a pseudonym")
    private boolean pseudonym;

    @Schema(description = "true if name entity shall be searchable (e.g. to be referenced as comic creator)")
    private boolean searchable;

    @Transient
    private String getComparableName() {
        return name != null ? name : lastName + ' ' + firstName;
    }

    @Transient
    public String getHarvardName() {
        return name != null ? name : lastName + ", " + firstName;
    }

    @Transient
    public void setEmptyStringsNull() {
        if (getName() != null && getName().isEmpty()) {
            setName(null);
        }
        if (getFirstName() != null && getFirstName().isEmpty()) {
            setFirstName(null);
        }
        if (getLastName() != null && getLastName().isEmpty()) {
            setLastName(null);
        }
    }

    @Override
    public int compareTo(Name o) {
        return this.getComparableName().compareToIgnoreCase(o.getComparableName());
    }
}
