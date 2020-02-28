package net.gendercomics.api.model;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MetaData {

    private Date createdOn;
    private String createdBy;
    private Date changedOn;
    private String changedBy;
    private Status status;
}
