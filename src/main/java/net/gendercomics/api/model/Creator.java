package net.gendercomics.api.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.List;

@Getter
@Setter
public class Creator {

    @DBRef
    private Name name;
    @Deprecated
    @DBRef
    private Role role;
    @DBRef
    private List<Role> roles;

}
