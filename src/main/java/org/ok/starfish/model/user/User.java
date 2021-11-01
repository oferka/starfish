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

import javax.validation.constraints.*;
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
    private final String title;

    @Getter
    @NotNull
    @Size(min = 2, max = 64)
    @NotBlank
    @Field(type = Keyword)
    private final String firstName;

    @Getter
    @NotNull
    @Size(min = 2, max = 64)
    @NotBlank
    @Field(type = Keyword)
    private final String lastName;

    @Getter
    @Field(type = FieldType.Integer)
    @Positive
    private final int streetNumber;

    @Getter
    @NotNull
    @Size(min = 2, max = 64)
    @NotBlank
    @Field(type = Keyword)
    private final String streetName;

    @Getter
    @NotNull
    @Size(min = 2, max = 64)
    @NotBlank
    @Field(type = Keyword)
    private final String city;

    @Getter
    @NotNull
    @Size(min = 2, max = 64)
    @NotBlank
    @Field(type = Keyword)
    private final String state;

    @Getter
    @NotNull
    @Size(min = 2, max = 64)
    @NotBlank
    @Field(type = Keyword)
    private final String country;

    @Getter
    @NotNull
    @Size(min = 2, max = 64)
    @NotBlank
    @Field(type = Keyword)
    private final String postcode;

    @Getter
    @Field(type = FieldType.Double)
    private final double latitude;

    @Getter
    @Field(type = FieldType.Double)
    private final double longitude;

    @Getter
    @NotNull
    @Size(min = 2, max = 64)
    @NotBlank
    @Field(type = Keyword)
    private final String timezoneOffset;

    @Getter
    @NotNull
    @Size(min = 2, max = 64)
    @NotBlank
    @Field(type = Keyword)
    private final String timezoneDescription;

    @Getter
    @NotNull
    @Size(min = 2, max = 64)
    @NotBlank
    @Email
    @Field(type = Keyword)
    private final String email;

    @JsonSerialize(using = ZonedDateTimeSerializer.class)
    @Getter
    @NotNull
    @Past
    @Field(type = FieldType.Date, format = DateFormat.date_time)
    private ZonedDateTime dateOfBirth;

    @JsonSerialize(using = ZonedDateTimeSerializer.class)
    @Getter
    @NotNull
    @Past
    @Field(type = FieldType.Date, format = DateFormat.date_time)
    private ZonedDateTime dateOfRegistration;

    @Getter
    @NotNull
    @Size(min = 2, max = 64)
    @NotBlank
    @Field(type = Keyword)
    private final String phone;

    @Getter
    @NotNull
    @Size(min = 2, max = 64)
    @NotBlank
    @Field(type = Keyword)
    private final String cell;

    @Getter
    @NotNull
    @Size(min = 2, max = 512)
    @NotBlank
    @Field(type = Keyword)
    private final String largePicture;

    @Getter
    @NotNull
    @Size(min = 2, max = 512)
    @NotBlank
    @Field(type = Keyword)
    private final String mediumPicture;

    @Getter
    @NotNull
    @Size(min = 2, max = 512)
    @NotBlank
    @Field(type = Keyword)
    private final String thumbnailPicture;

    @Getter
    @NotNull
    @Size(min = 2, max = 64)
    @NotBlank
    @Field(type = Keyword)
    private final String nationality;

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
