package net.gendercomics.api.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;

@Getter
@Setter
public class Creator {

    @DBRef
    private Name name;
    @DBRef
    private Role role;

}
