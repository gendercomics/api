package net.gendercomics.api.model;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(description = "meta data")
public class MetaData {

    private Date createdOn;
    private String createdBy;
    private Date changedOn;
    private String changedBy;
}
