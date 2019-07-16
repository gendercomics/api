package net.gendercomics.api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import net.gendercomics.api.model.ApiInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"common endpoints"})
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CommonController {

    private final ApiInfo _apiInfo;

    @ApiOperation("retreive API information")
    @GetMapping(path = "/info")
    public ApiInfo getInfo() {
        return _apiInfo;
    }

}
