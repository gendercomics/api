package net.gendercomics.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Schema(description = "keyword")
public class HyperLink {

    String url;
    Date lastAccess;

}
