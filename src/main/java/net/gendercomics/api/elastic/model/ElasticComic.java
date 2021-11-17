package net.gendercomics.api.elastic.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import net.gendercomics.api.model.ComicType;
import net.gendercomics.api.model.MetaData;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "gendercomics-dev.comics")
public class ElasticComic {

    @Id
    private String id;

    /*
    @ApiModelProperty(value = "metadata", required = true)
    @EqualsAndHashCode.Exclude
    private MetaData metaData;
     */

    @ApiModelProperty(value = "comic book title", required = true)
    private String title;

    @ApiModelProperty(value = "comic book subtitle")
    private String subTitle;

    @ApiModelProperty(value = "comic book type (comic, magazine, anthology, webcomic, comic-series, publishing-series)", required = true)
    private ComicType type;

}
