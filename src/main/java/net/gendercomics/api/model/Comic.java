package net.gendercomics.api.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "comics")
//@JsonTypeName("comic")
//@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
//@JsonInclude(Include.NON_NULL)
@ApiModel(description = "comic book model")
public class Comic implements Comparable<Comic> {

    private String id;
    @ApiModelProperty(value = "metadata", required = true)
    private MetaData metaData;

    @Indexed(name = "comic_book_title_index", direction = IndexDirection.ASCENDING, unique = true)
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
    private Integer year;

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
    @JsonInclude(JsonInclude.Include.ALWAYS)
    private List<Keyword> genres;

    @ApiModelProperty(value = "list of keywords")
    @DBRef
    private List<Keyword> keywords;

    @Override
    public int compareTo(Comic o) {
        return this.title.compareToIgnoreCase(o.title);
    }
}
