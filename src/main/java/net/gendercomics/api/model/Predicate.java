package net.gendercomics.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Getter
@Setter
@Document("predicates")
public class Predicate implements Comparable<Predicate> {

    private String id;

    @Indexed(name = "predicate_name_index", unique = true, sparse = true)
    @ApiModelProperty(value = "name value")
    private String name;

    @ApiModelProperty(value = "list of predicate values (one list entry per language)", required = true)
    private Map<Language, String> values;


    @Override
    public int compareTo(Predicate o) {
        return this.getName().compareTo(o.getName());
    }
}
