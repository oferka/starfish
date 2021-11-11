package org.ok.starfish.model.application;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.ZonedDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.validator.constraints.URL;
import org.ok.starfish.model.applicaton_category.ApplicationCategory;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.ZonedDateTime;
import java.util.List;

import static org.springframework.data.elasticsearch.annotations.FieldType.Keyword;

@ToString
@AllArgsConstructor
@Document(indexName = "starfish_application")
public class Application {

    @Id
    @Getter
    @NotNull
    @Size(min = 2, max = 64)
    @NotBlank
    @Field(type = Keyword)
    private final String id;

    @Getter
    @NotNull
    @Size(min = 2, max = 64)
    @NotBlank
    @Field(type = Keyword)
    private final String name;

    @Getter
    @NotNull
    @Size(min = 2, max = 512)
    @NotBlank
    @URL
    @Field(type = Keyword)
    private final String logo;

    @JsonSerialize(using = ZonedDateTimeSerializer.class)
    @Getter
    @NotNull
    @Past
    @Field(type = FieldType.Date, format = DateFormat.date_time)
    private ZonedDateTime createdDate;

    @Getter
    @NotNull
    @Field(type = FieldType.Object)
    private List<ApplicationCategory> applicationCategories;
}
