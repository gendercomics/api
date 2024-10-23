package net.gendercomics.api.data.service;

import net.gendercomics.api.model.Comic;
import net.gendercomics.api.model.SearchInput;
import net.gendercomics.api.model.SearchResult;

import java.util.List;

public interface SearchService {
    SearchResult search(String searchTerm);

    SearchResult search(SearchInput searchInput);

    List<Comic> searchAndReturnComics(String searchTerm);

    List<Comic> searchAndReturnComics(SearchInput searchInput);
}
