package net.gendercomics.api.controller;

import lombok.RequiredArgsConstructor;
import net.gendercomics.api.model.ApiInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CommonController {

    private final ApiInfo _apiInfo;

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public ApiInfo getInfo() {
        return _apiInfo;
    }

}
