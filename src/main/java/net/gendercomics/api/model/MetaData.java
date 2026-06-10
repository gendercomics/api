package net.gendercomics.api.model;

import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Metadata for database entities")
public class MetaData {

    @Schema(description = "Date on which the entry was created", required = true)
    private Date createdOn;
    @Schema(description = "User who created the entry", required = true)
    private String createdBy;
    @Schema(description = "Date on which the entry was modified")
    private Date changedOn;
    @Schema(description = "User who modified the entry")
    private String changedBy;
    @Schema(description = "Status of entry: DRAFT|REVIEW|FINAL, default value = DRAFT")
    private Status status = Status.DRAFT;
}
