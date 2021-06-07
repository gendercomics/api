package net.gendercomics.api.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(description = "migration result model")
public class MigrationResult {

    public static final String OK = "OK";
    public static final String FAILED = "FAILED";

    private String status = "OK";

    private List<Object> source;

    private List<Object> result;
}
