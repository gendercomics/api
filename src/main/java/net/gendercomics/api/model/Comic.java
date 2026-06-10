package net.gendercomics.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Document(collection = "comics")
@Schema(description = "comic book model")
@CompoundIndexes(value = {
        @CompoundIndex(name = "comic_title_issue_index", def = "{'title':1, 'issue':1}", sparse = true)
})
public class Comic implements Comparable<Comic>, DisplayName, DisplayNameI18n {

    @EqualsAndHashCode.Include
    private String id;

    @Schema(description = "metadata", required = true)
    private MetaData metaData;

    @Schema(description = "comic book title", required = true)
    @EqualsAndHashCode.Include
    @Indexed(name = "comic_title_index")
    @TextIndexed
    private String title;

    @Schema(description = "comic book subtitle")
    @EqualsAndHashCode.Include
    @TextIndexed
    private String subTitle;

    @Schema(description = "magazine issue")
    private String issue;

    @Schema(description = "magazine issue title")
    private String issueTitle;

    @Schema(description = "comic book type (comic, magazine, anthology, webcomic, comic-series, publishing-series)", required = true)
    private ComicType type;

    @Schema(description = "list of creators")
    private List<Creator> creators;

    @Schema(description = "list of publishers")
    @DBRef
    private List<Publisher> publishers;

    @Schema(description = "list of location changes for publishers")
    private Map<String, String> publisherOverrides;

    @Schema(description = "printer")
    private String printer;

    @Schema(description = "year of publication")
    private String year;

    @Schema(description = "edition")
    private String edition;

    @Schema(description = "list of hyperlinks (url, last accessed")
    private List<HyperLink> hyperLinks;

    @Schema(description = "isbn")
    private String isbn;

    @Schema(description = "list part of publishing or comic series")
    private List<Series> seriesList;

    @Schema(description = "part of publication (comic)")
    private PartOf partOf;

    @Schema(description = "list of genres (keywords)")
    @DBRef
    private List<Keyword> genres;

    @Schema(description = "list of keywords")
    @DBRef
    private List<Keyword> keywords;

    @Schema(description = "list of comments")
    @DBRef
    private List<Text> comments;

    @Schema(description = "cover image file name")
    private String cover;

    @Override
    public int compareTo(Comic o) {
        return this.getComparableNameForWebAppList().compareToIgnoreCase(o.getComparableNameForWebAppList());
    }

    @Transient
    @Override
    public String getNameForWebAppList() {
        String value = this.title;
        if (this.issue != null) {
            value += ", ";
            value += this.issue;
        }
        if (this.issueTitle != null) {
            value += ": ";
            value += this.issueTitle;
        }
        return value;
    }

    @Transient
    @Override
    public String getComparableNameForWebAppList() {
        String comparableName = "";
        if (this.seriesList != null && !seriesList.isEmpty()) {
            List<Series> seriesList = this.seriesList.stream()
                    .filter(series -> series != null && series.getComic() != null)
                    .filter(series -> ComicType.comic_series.equals(series.getComic().getType()))
                    .collect(Collectors.toList());
            if (!seriesList.isEmpty()) {
                comparableName += seriesList.get(0).getComic().getTitle();
                if (seriesList.get(0).getVolume() != null) {
                    comparableName += " " + seriesList.get(0).getVolume();
                }
            }
        }

        if (comparableName.length() > 0) {
            comparableName += ": ";
        }

        return comparableName + getNameForWebAppList();
    }

    @Override
    public Map<Language, String> getDisplayNames() {
        Map<Language, String> map = new HashMap<>();
        map.put(Language.de, this.getNameForWebAppList());
        map.put(Language.en, this.getNameForWebAppList());
        return map;
    }

    @Transient
    @JsonIgnore
    public Map<String, Series> getSeriesAsMap() {
        return (this.getSeriesList() == null || this.getSeriesList().isEmpty()) ? Collections.emptyMap() : this.getSeriesList().stream()
                .collect(Collectors.toMap(Series::getComicId, Function.identity()));
    }
}
