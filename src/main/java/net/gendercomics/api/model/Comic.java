package net.gendercomics.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@Document(collection = "comics")
@ApiModel(description = "comic book model")
@CompoundIndexes(value = {
        @CompoundIndex(name = "comic_title_issue_index", def = "{'title':1, 'issue':1}", sparse = true)
})
public class Comic implements Comparable<Comic>, DisplayName {

    private String id;

    @ApiModelProperty(value = "metadata", required = true)
    @EqualsAndHashCode.Exclude
    private MetaData metaData;

    @ApiModelProperty(value = "comic book title", required = true)
    @Indexed(name = "comic_title_index")
    private String title;

    @ApiModelProperty(value = "comic book subtitle")
    private String subTitle;

    @ApiModelProperty(value = "magazine issue")
    private String issue;

    @ApiModelProperty(value = "magazine issue title")
    private String issueTitle;

    @ApiModelProperty(value = "comic book type (comic, magazine, anthology, webcomic, comic-series, publishing-series)", required = true)
    private ComicType type;

    @ApiModelProperty(value = "list of creators")
    private List<Creator> creators;

    @Deprecated
    @ApiModelProperty(value = "publisher")
    @DBRef
    private Publisher publisher;

    @ApiModelProperty(value = "list of publishers")
    @DBRef
    private List<Publisher> publishers;

    @ApiModelProperty(value = "printer")
    private String printer;

    @ApiModelProperty(value = "year of publication")
    private String year;

    @ApiModelProperty(value = "edition")
    private String edition;

    @Deprecated
    @ApiModelProperty(value = "hyperlink object (url, last accessed")
    private HyperLink hyperLink;

    @ApiModelProperty(value = "list of hyperlinks (url, last accessed")
    private List<HyperLink> hyperLinks;

    @ApiModelProperty(value = "isbn")
    private String isbn;

    @ApiModelProperty(value = "part of series (comic)")
    private Series series;

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

    @Override
    public int compareTo(Comic o) {
        return this.getNameForWebAppList().compareToIgnoreCase(o.getNameForWebAppList());
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
}
