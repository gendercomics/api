package net.gendercomics.api.model;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@ApiModel(description = "keyword")
public class HyperLink {

    String url;
    Date lastAccess;

}
