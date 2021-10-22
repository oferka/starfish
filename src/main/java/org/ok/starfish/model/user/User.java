package org.ok.starfish.model.user;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.ZonedDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.ok.starfish.model.account.Account;
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

import static org.springframework.data.elasticsearch.annotations.FieldType.Keyword;

@ToString
@AllArgsConstructor
@Document(indexName = "starfish_user")
public class User {

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
    private final String gender;

    @Getter
    @NotNull
    @Size(min = 2, max = 64)
    @NotBlank
    @Field(type = Keyword)
    private final String name;

    @JsonSerialize(using = ZonedDateTimeSerializer.class)
    @Getter
    @NotNull
    @Past
    @Field(type = FieldType.Date, format = DateFormat.date_time)
    private ZonedDateTime createdDate;

    @Getter
    @NotNull
    @Field(type = FieldType.Object)
    private Account account;
}
