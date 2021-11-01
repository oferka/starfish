package org.ok.starfish.data.service.user;

import org.apache.commons.lang3.RandomUtils;
import org.ok.starfish.data.repository.es.user.UserElasticsearchRepository;
import org.ok.starfish.model.user.User;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserServiceImpl implements UserService {

    private final UserElasticsearchRepository userElasticsearchRepository;

    public UserServiceImpl(UserElasticsearchRepository userElasticsearchRepository) {
        this.userElasticsearchRepository = userElasticsearchRepository;
    }

    @Override
    public @NotNull List<User> findAll() {
        Iterable<User> items = userElasticsearchRepository.findAll();
        return StreamSupport
                .stream(items.spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public @NotNull Optional<User> findById(@NotNull String id) {
        return userElasticsearchRepository.findById(id);
    }

    @Override
    public @NotNull List<User> findByGender(@NotNull String gender) {
        return userElasticsearchRepository.findByGender(gender);
    }

    @Override
    public @NotNull List<User> findByTitle(@NotNull String title) {
        return userElasticsearchRepository.findByTitle(title);
    }

    @Override
    public @NotNull List<User> findByFirstName(@NotNull String firstName) {
        return userElasticsearchRepository.findByFirstName(firstName);
    }

    @Override
    public @NotNull List<User> findByLastName(@NotNull String lastName) {
        return userElasticsearchRepository.findByLastName(lastName);
    }

    @Override
    public @NotNull List<User> findByStreetNumber(int streetNumber) {
        return userElasticsearchRepository.findByStreetNumber(streetNumber);
    }

    @Override
    public List<User> findByStreetName(String streetName) {
        return userElasticsearchRepository.findByStreetName(streetName);
    }

    @Override
    public List<User> findByCity(String city) {
        return userElasticsearchRepository.findByCity(city);
    }

    @Override
    public List<User> findByState(String state) {
        return userElasticsearchRepository.findByState(state);
    }

    @Override
    public List<User> findByCountry(String country) {
        return userElasticsearchRepository.findByCountry(country);
    }

    @Override
    public List<User> findByPostcode(String postcode) {
        return userElasticsearchRepository.findByPostcode(postcode);
    }

    @Override
    public List<User> findByLatitude(double latitude) {
        return userElasticsearchRepository.findByLatitude(latitude);
    }

    @Override
    public List<User> findByLongitude(double longitude) {
        return userElasticsearchRepository.findByLongitude(longitude);
    }

    @Override
    public List<User> findByTimezoneOffset(String timezoneOffset) {
        return userElasticsearchRepository.findByTimezoneOffset(timezoneOffset);
    }

    @Override
    public List<User> findByTimezoneDescription(String timezoneDescription) {
        return userElasticsearchRepository.findByTimezoneDescription(timezoneDescription);
    }

    @Override
    public List<User> findByEmail(String email) {
        return userElasticsearchRepository.findByEmail(email);
    }

    @Override
    public List<User> findByDateOfBirth(ZonedDateTime dateOfBirth) {
        return userElasticsearchRepository.findByDateOfBirth(dateOfBirth);
    }

    @Override
    public List<User> findByDateOfRegistration(ZonedDateTime dateOfRegistration) {
        return userElasticsearchRepository.findByDateOfRegistration(dateOfRegistration);
    }

    @Override
    public List<User> findByPhone(String phone) {
        return userElasticsearchRepository.findByPhone(phone);
    }

    @Override
    public List<User> findByCell(String cell) {
        return userElasticsearchRepository.findByCell(cell);
    }

    @Override
    public List<User> findByLargePicture(String largePicture) {
        return userElasticsearchRepository.findByLargePicture(largePicture);
    }

    @Override
    public List<User> findByMediumPicture(String mediumPicture) {
        return userElasticsearchRepository.findByMediumPicture(mediumPicture);
    }

    @Override
    public List<User> findByThumbnailPicture(String thumbnailPicture) {
        return userElasticsearchRepository.findByThumbnailPicture(thumbnailPicture);
    }

    @Override
    public List<User> findByNationality(String nationality) {
        return userElasticsearchRepository.findByNationality(nationality);
    }

    @Override
    public List<User> findByCreatedDate(ZonedDateTime createdDate) {
        return userElasticsearchRepository.findByCreatedDate(createdDate);
    }

    @Override
    public Optional<User> findRandom() {
        List<User> items = findAll();
        if(items.isEmpty()) {
            return Optional.empty();
        }
        User item = items.get(RandomUtils.nextInt(0, items.size()));
        return Optional.of(item);
    }

    @Override
    public @NotNull User save(@NotNull User user) {
        return userElasticsearchRepository.save(user);
    }

    @Override
    public @NotNull Iterable<User> saveAll(@NotNull Iterable<User> users) {
        return userElasticsearchRepository.saveAll(users);
    }

    @Override
    public Optional<User> update(@NotNull String id, @NotNull User user) {
        Optional<User> result = Optional.empty();
        if(existsById(id)) {
            result = Optional.of(save(user));
        }
        return result;
    }

    @Override
    public void deleteById(@NotNull String id) {
        userElasticsearchRepository.deleteById(id);
    }

    @Override
    public long count() {
        return userElasticsearchRepository.count();
    }

    @Override
    public boolean existsById(@NotNull String id) {
        return userElasticsearchRepository.existsById(id);
    }

    @Override
    public boolean exists(@NotNull User user) {
        List<User> users = userElasticsearchRepository.findByFirstName(user.getFirstName());
        return (!users.isEmpty());
    }
}
