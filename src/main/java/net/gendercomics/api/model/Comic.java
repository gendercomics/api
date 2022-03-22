package net.gendercomics.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Document(collection = "comics")
@ApiModel(description = "comic book model")
@CompoundIndexes(value = {
        @CompoundIndex(name = "comic_title_issue_index", def = "{'title':1, 'issue':1}", sparse = true)
})
public class Comic implements Comparable<Comic>, DisplayName {

    @EqualsAndHashCode.Include
    private String id;

    @ApiModelProperty(value = "metadata", required = true)
    private MetaData metaData;

    @ApiModelProperty(value = "comic book title", required = true)
    @EqualsAndHashCode.Include
    @Indexed(name = "comic_title_index")
    @TextIndexed
    private String title;

    @ApiModelProperty(value = "comic book subtitle")
    @EqualsAndHashCode.Include
    @TextIndexed
    private String subTitle;

    @ApiModelProperty(value = "magazine issue")
    private String issue;

    @ApiModelProperty(value = "magazine issue title")
    private String issueTitle;

    @ApiModelProperty(value = "comic book type (comic, magazine, anthology, webcomic, comic-series, publishing-series)", required = true)
    private ComicType type;

    @ApiModelProperty(value = "list of creators")
    private List<Creator> creators;

    @ApiModelProperty(value = "list of publishers")
    @DBRef
    private List<Publisher> publishers;

    @ApiModelProperty(value = "list of location changes for publishers")
    private Map<String, String> publisherOverrides;

    @ApiModelProperty(value = "printer")
    private String printer;

    @ApiModelProperty(value = "year of publication")
    private String year;

    @ApiModelProperty(value = "edition")
    private String edition;

    @ApiModelProperty(value = "list of hyperlinks (url, last accessed")
    private List<HyperLink> hyperLinks;

    @ApiModelProperty(value = "isbn")
    private String isbn;

    @ApiModelProperty(value = "list part of publishing or comic series")
    private List<Series> seriesList;

    @ApiModelProperty(value = "part of publication (comic)")
    private PartOf partOf;

    @ApiModelProperty(value = "list of genres (keywords)")
    @DBRef
    private List<Keyword> genres;

    @ApiModelProperty(value = "list of keywords")
    @DBRef
    private List<Keyword> keywords;

    @ApiModelProperty(value = "list of comments")
    @DBRef
    private List<Text> comments;

    @ApiModelProperty(value = "cover image file name")
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

    @Transient
    @JsonIgnore
    public Map<String, Series> getSeriesAsMap() {
        return (this.getSeriesList() == null || this.getSeriesList().isEmpty()) ? Collections.emptyMap() : this.getSeriesList().stream()
                .collect(Collectors.toMap(Series::getComicId, Function.identity()));
    }
}
