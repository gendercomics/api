package net.gendercomics.api.data.service;

import net.gendercomics.api.data.service.impl.SearchServiceImpl;
import net.gendercomics.api.model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SearchServiceImpl.class})
public class SearchServiceTest {

    @Autowired
    private SearchService _searchService;

    @MockBean
    private MongoTemplate _mongoTemplate;

    @MockBean
    private ComicService _comicService;

    @MockBean
    private KeywordService _keywordService;

    @Test
    public void whenSearchByTerm_thenComicsReturned() {
        Comic comic = new Comic();
        comic.setId("id1");
        comic.setTitle("Wonderwoman");

        when(_mongoTemplate.find(any(), eq(Comic.class))).thenReturn(List.of(comic));
        when(_mongoTemplate.find(any(), eq(Name.class))).thenReturn(Collections.emptyList());
        when(_mongoTemplate.find(any(), eq(Publisher.class))).thenReturn(Collections.emptyList());
        when(_comicService.getBySeries(any())).thenReturn(Collections.emptyList());
        when(_comicService.getByPartOf(any())).thenReturn(Collections.emptyList());
        when(_comicService.getByCreatorNames(any())).thenReturn(Collections.emptyList());
        when(_comicService.getByPublisherNames(any())).thenReturn(Collections.emptyList());

        SearchResult result = _searchService.search("Wonderwoman");

        assertNotNull(result);
        assertNotNull(result.getComics());
        assertEquals(1, result.getComics().size());
        assertEquals("Wonderwoman", result.getComics().get(0).getTitle());
    }

    @Test
    public void whenSearchWithComicsFilterDisabled_thenNoComicsFromDirectSearch() {
        SearchInput input = new SearchInput("test", new SearchFilter(false, false, false, false), "de");

        when(_mongoTemplate.find(any(), eq(Comic.class))).thenReturn(Collections.emptyList());
        when(_mongoTemplate.find(any(), eq(Name.class))).thenReturn(Collections.emptyList());
        when(_mongoTemplate.find(any(), eq(Publisher.class))).thenReturn(Collections.emptyList());
        when(_comicService.getByCreatorNames(any())).thenReturn(Collections.emptyList());
        when(_comicService.getByPublisherNames(any())).thenReturn(Collections.emptyList());
        when(_keywordService.findBySearchTerm(any(), any())).thenReturn(Collections.emptyList());
        when(_comicService.findByKeywords(any())).thenReturn(Collections.emptyList());

        SearchResult result = _searchService.search(input);

        assertNotNull(result);
        assertNull(result.getComics());
        verify(_mongoTemplate, never()).find(any(), eq(Comic.class));
    }

    @Test
    public void whenSearchWithKeywords_thenComicsByKeywordIncluded() {
        Comic comic = new Comic();
        comic.setId("kw_comic");
        comic.setTitle("Keyword Comic");
        MetaData meta = new MetaData();
        meta.setStatus(Status.FINAL);
        comic.setMetaData(meta);

        SearchInput input = new SearchInput("gender", new SearchFilter(false, false, false, true), "de");

        when(_keywordService.findBySearchTerm(eq("gender"), eq(Language.de))).thenReturn(List.of(new Keyword()));
        when(_comicService.findByKeywords(any())).thenReturn(List.of(comic));
        when(_comicService.getByCreatorNames(any())).thenReturn(Collections.emptyList());
        when(_comicService.getByPublisherNames(any())).thenReturn(Collections.emptyList());

        SearchResult result = _searchService.search(input);

        assertNotNull(result);
        assertNotNull(result.getComics());
        assertEquals(1, result.getComics().size());
        assertEquals("kw_comic", result.getComics().get(0).getId());
    }

    @Test
    public void whenSearchReturnsNonFinalComics_thenFiltered() {
        Comic draftComic = new Comic();
        draftComic.setId("draft");
        draftComic.setTitle("Draft Comic");
        MetaData meta = new MetaData();
        meta.setStatus(Status.DRAFT);
        draftComic.setMetaData(meta);

        SearchInput input = new SearchInput("test", new SearchFilter(true, false, false, false), "de");

        when(_mongoTemplate.find(any(), eq(Comic.class))).thenReturn(List.of(draftComic));
        when(_comicService.getBySeries(any())).thenReturn(Collections.emptyList());
        when(_comicService.getByPartOf(any())).thenReturn(Collections.emptyList());
        when(_comicService.getByCreatorNames(any())).thenReturn(Collections.emptyList());
        when(_comicService.getByPublisherNames(any())).thenReturn(Collections.emptyList());
        when(_keywordService.findBySearchTerm(any(), any())).thenReturn(Collections.emptyList());
        when(_comicService.findByKeywords(any())).thenReturn(Collections.emptyList());

        SearchResult result = _searchService.search(input);

        assertNotNull(result.getComics());
        assertTrue(result.getComics().isEmpty());
    }

    @Test
    public void whenConvertResultToHarvard_thenDelegatesToComicService() {
        Comic comic = new Comic();
        comic.setId("id1");

        when(_comicService.toHarvard(comic)).thenReturn("Harvard citation. ");

        String result = _searchService.convertResultToHarvard(List.of(comic));

        assertEquals("Harvard citation. ", result);
        verify(_comicService).toHarvard(comic);
    }

    @Test
    public void whenSearchAndReturnComics_thenReturnsFlatList() {
        Comic comic = new Comic();
        comic.setId("id1");
        comic.setTitle("Test");

        when(_mongoTemplate.find(any(), eq(Comic.class))).thenReturn(List.of(comic));
        when(_mongoTemplate.find(any(), eq(Name.class))).thenReturn(Collections.emptyList());
        when(_mongoTemplate.find(any(), eq(Publisher.class))).thenReturn(Collections.emptyList());
        when(_comicService.getBySeries(any())).thenReturn(Collections.emptyList());
        when(_comicService.getByPartOf(any())).thenReturn(Collections.emptyList());
        when(_comicService.getByCreatorNames(any())).thenReturn(Collections.emptyList());
        when(_comicService.getByPublisherNames(any())).thenReturn(Collections.emptyList());

        List<Comic> result = _searchService.searchAndReturnComics("Test");

        assertNotNull(result);
        assertFalse(result.isEmpty());
    }
}
