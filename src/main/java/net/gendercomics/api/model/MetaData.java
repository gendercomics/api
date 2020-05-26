package net.gendercomics.api.model;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;

@Getter
@Setter
@ApiModel(description = "Metadata for database entities")
public class MetaData {

    @ApiModelProperty(value = "Date on which the entry was created", required = true)
    private Date createdOn;
    @ApiModelProperty(value = "User who created the entry", required = true)
    private String createdBy;
    @ApiModelProperty(value = "Date on which the entry was modified")
    private Date changedOn;
    @ApiModelProperty(value = "User who modified the entry")
    private String changedBy;
    @ApiModelProperty(value = "Status of entry: DRAFT|REVIEW|FINAL, default value = DRAFT")
    private Status status = Status.DRAFT;
}
