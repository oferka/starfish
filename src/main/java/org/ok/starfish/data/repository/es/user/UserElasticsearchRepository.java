package org.ok.starfish.data.repository.es.user;

import org.ok.starfish.model.user.User;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public interface UserElasticsearchRepository extends ElasticsearchRepository<User, String> {

    List<User> findByGender(String gender);

    List<User> findByTitle(String title);

    List<User> findByFirstName(String firstName);

    List<User> findByLastName(String lastName);

    List<User> findByStreetNumber(int streetNumber);

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

    List<User> findByCreatedDate(ZonedDateTime createdDate);
}
