package net.gendercomics.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import net.gendercomics.api.data.service.TextService;
import net.gendercomics.api.model.Text;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Tag(name = "texts")
@RestController
@CrossOrigin
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TextController {

    private final TextService _textService;

    /*** admin endpoints - secured, only authorized access allowed ***/

    @Operation(summary = "insert a text")
    @PostMapping(path = "/texts")
    public Text insertText(@Parameter(hidden = true) Principal principal, @Parameter(required = true) @RequestBody String value) {
        Text txt = new Text();
        txt.setValue(value);
        return _textService.save(txt, principal.getName());
    }

    @Operation(summary = "update a text")
    @PutMapping(path = "/texts/{id}")
    public Text updateText(@Parameter(hidden = true) Principal principal, @Parameter(required = true) @RequestBody Text text) {
        return _textService.save(text, principal.getName());
    }

    @Operation(summary = "delete a text")
    @DeleteMapping(path = "/texts/{id}")
    public void deleteText(@Parameter(required = true) @PathVariable String id) {
        _textService.delete(id);
    }
}
