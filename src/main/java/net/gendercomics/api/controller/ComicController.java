package net.gendercomics.api.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
import net.gendercomics.api.data.service.ComicService;
import net.gendercomics.api.model.Comic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comics")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ComicController {

    private final ComicService _comicService;

    @GetMapping
    public List<Comic> getAllComics() {
        return _comicService.findAll();
    }

}
