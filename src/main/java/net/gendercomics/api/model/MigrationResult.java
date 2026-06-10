package net.gendercomics.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "migration result model")
public class MigrationResult {

    public static final String OK = "OK";
    public static final String FAILED = "FAILED";

    private String status = "OK";

    private List<Object> source;

    private List<Object> result;
}
