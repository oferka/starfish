package org.ok.starfish.data.content.provider.user;

import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

@Service
public class UsersCsvLineReader {

    public @NotNull UserLine read(@NotNull String[] line) {
        String gender = line[0];
        String title = line[1];
        String firstName = line[2];
        String lastName = line[3];
        String streetNumber = line[4];
        String streetName = line[5];
        String city = line[6];
        String state = line[7];
        String country = line[8];
        String postcode = line[9];
        String latitude = line[10];
        String longitude = line[11];
        String timezoneOffset = line[12];
        String timezoneDescription = line[13];
        String email = line[14];
        String dateOfBirth = line[15];
        String age = line[16];
        String dateOfRegistration = line[17];
        String registrationAge = line[18];
        String phone = line[19];
        String cell = line[20];
        String largePicture = line[21];
        String mediumPicture = line[22];
        String thumbnailPicture = line[23];
        String nationality = line[24];
        return new UserLine(
                gender,
                title,
                firstName,
                lastName,
                Integer.parseInt(streetNumber),
                streetName,
                city,
                state,
                country,
                postcode,
                Double.parseDouble(latitude),
                Double.parseDouble(longitude),
                timezoneOffset,
                timezoneDescription,
                email,
                ZonedDateTime.parse(dateOfBirth),
                ZonedDateTime.parse(dateOfRegistration),
                phone,
                cell,
                largePicture,
                mediumPicture,
                thumbnailPicture,
                nationality
        );
    }
}
