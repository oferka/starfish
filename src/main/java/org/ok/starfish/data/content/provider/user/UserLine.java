package org.ok.starfish.data.content.provider.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.ZonedDateTime;

@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserLine {

    @Getter
    private String gender;

    @Getter
    private String title;

    @Getter
    private String firstName;

    @Getter
    private String lastName;

    @Getter
    private int streetNumber;

    @Getter
    private String streetName;

    @Getter
    private String city;

    @Getter
    private String state;

    @Getter
    private String country;

    @Getter
    private int postcode;

    @Getter
    private double latitude;

    @Getter
    private double longitude;

    @Getter
    private String timezoneOffset;

    @Getter
    private String timezoneDescription;

    @Getter
    private String email;

    @Getter
    private ZonedDateTime dateOfBirth;

    @Getter
    private ZonedDateTime dateOfRegistration;

    @Getter
    private String phone;

    @Getter
    private String cell;

    @Getter
    private String largePicture;

    @Getter
    private String mediumPicture;

    @Getter
    private String thumbnailPicture;

    @Getter
    private String nationality;
}
