package net.gendercomics.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.beans.Transient;
import java.util.List;

@Getter
@Setter
@Document("persons")
@Schema(description = "persons involved in the process of creating comics")
public class Person implements Comparable<Person> {

    private String id;

    @DBRef
    @Schema(description = "list of names", required = true)
    private List<Name> names;

    @Indexed(name = "wikidata_index", unique = true, sparse = true)
    @Schema(description = "wikidata")
    private String wikiData;

    @Schema(description = "metadata", required = true)
    private MetaData metaData;

    @Transient
    private Name getSortName() {
        if (names == null) {
            return new Name();
        }
        if (names.size() > 1) {
            for (Name name : names) {
                if (name.isSearchable()) {
                    return name;
                }
            }
        }
        return names.get(0);
    }

    @Override
    public int compareTo(Person o) {
        return this.getSortName().compareTo(o.getSortName());
    }
}
