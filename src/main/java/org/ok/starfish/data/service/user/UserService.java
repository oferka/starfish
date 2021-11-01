package org.ok.starfish.data.service.user;

import org.ok.starfish.model.user.User;

import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

public interface UserService {

    @NotNull List<User> findAll();

    @NotNull Optional<User> findById(@NotNull String id);

    @NotNull List<User> findByGender(@NotNull String gender);

    @NotNull List<User> findByTitle(@NotNull String title);

    @NotNull List<User> findByFirstName(@NotNull String firstName);

    @NotNull List<User> findByLastName(@NotNull String lastName);

    @NotNull List<User> findByStreetNumber(int streetNumber);

    List<User> findByStreetName(String streetName);

    List<User> findByCity(String city);

    List<User> findByState(String state);

    List<User> findByCountry(String country);

    List<User> findByPostcode(String postcode);

    List<User> findByLatitude(double latitude);

    List<User> findByLongitude(double longitude);

    List<User> findByTimezoneOffset(String timezoneOffset);

    List<User> findByTimezoneDescription(String timezoneDescription);

    List<User> findByEmail(String email);

    List<User> findByDateOfBirth(ZonedDateTime dateOfBirth);

    List<User> findByDateOfRegistration(ZonedDateTime dateOfRegistration);

    List<User> findByPhone(String phone);

    List<User> findByCell(String cell);

    List<User> findByLargePicture(String largePicture);

    List<User> findByMediumPicture(String mediumPicture);

    List<User> findByThumbnailPicture(String thumbnailPicture);

    List<User> findByNationality(String nationality);

    @NotNull List<User> findByCreatedDate(@NotNull ZonedDateTime createdDate);

    @NotNull Optional<User> findRandom();

    long count();

    @NotNull User save(@NotNull User user);

    @NotNull Iterable<User> saveAll(@NotNull Iterable<User> users);

    @NotNull Optional<User> update(@NotNull String id, @NotNull User user);

    void deleteById(@NotNull String id);

    boolean existsById(@NotNull String id);

    boolean exists(@NotNull @NotNull User user);
}
