package net.gendercomics.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@Document(collection = "comics")
@CompoundIndexes(value = {
        @CompoundIndex(name = "comic_title_issue_index", def = "{'title':1, 'issue':1}", unique = true, sparse = true)
})
@ApiModel(description = "comic book model")
public class Comic implements Comparable<Comic> {

    private String id;

    @ApiModelProperty(value = "metadata", required = true)
    @EqualsAndHashCode.Exclude
    private MetaData metaData;

    @ApiModelProperty(value = "comic book title", required = true)
    private String title;

    @ApiModelProperty(value = "comic book subtitle")
    private String subTitle;

    @ApiModelProperty(value = "magazine issue")
    private String issue;

    @ApiModelProperty(value = "comic book type (comic, magazine, anthology, webcomic, series)", required = true)
    private ComicType type;

    @ApiModelProperty(value = "list of creators")
    private List<Creator> creators;

    @ApiModelProperty(value = "publisher")
    @DBRef
    private Publisher publisher;

    @ApiModelProperty(value = "year of publication")
    private String year;

    @ApiModelProperty(value = "edition")
    private String edition;

    @ApiModelProperty(value = "link")
    private String link;

    @ApiModelProperty(value = "isbn")
    private String isbn;

    @ApiModelProperty(value = "part of publication (comic)")
    private PartOf partOf;

    @ApiModelProperty(value = "list of genres (keywords)")
    @DBRef
    private List<Keyword> genres;

    @ApiModelProperty(value = "list of keywords")
    @DBRef
    private List<Keyword> keywords;

    @Deprecated
    @ApiModelProperty(value = "list of comments")
    @DBRef
    private List<Text> comments;

    @Override
    public int compareTo(Comic o) {
        return this.title.compareToIgnoreCase(o.title);
    }
}
