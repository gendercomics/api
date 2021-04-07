package net.gendercomics.api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import net.gendercomics.api.data.service.TextService;
import net.gendercomics.api.model.Text;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.security.Principal;

@Api(tags = {"texts"})
@RestController
@CrossOrigin
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TextController {

    private final TextService _textService;

    /*** admin endpoints - secured, only authorized access allowed ***/

    @ApiOperation("insert a text")
    @PostMapping(path = "/texts")
    public Text insertText(@ApiIgnore Principal principal, @ApiParam(required = true) @RequestBody String value) {
        Text txt = new Text();
        txt.setValue(value);
        return _textService.save(txt, principal.getName());
    }

    @ApiOperation("update a text")
    @PutMapping(path = "/texts/{id}")
    public Text updateText(@ApiIgnore Principal principal, @ApiParam(required = true) @RequestBody Text text) {
        return _textService.save(text, principal.getName());
    }

    @ApiOperation("delete a text")
    @DeleteMapping(path = "/texts/{id}")
    public void deleteText(@ApiParam(required = true) @PathVariable String id) {
        _textService.delete(id);
    }
}
