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
    public @NotNull List<User> findByName(@NotNull String name) {
        return userElasticsearchRepository.findByName(name);
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
        List<User> users = userElasticsearchRepository.findByName(user.getName());
        return (!users.isEmpty());
    }
}
