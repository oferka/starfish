package org.ok.starfish.data.content.verifier.user;

import lombok.extern.slf4j.Slf4j;
import org.ok.starfish.data.service.user.UserService;
import org.ok.starfish.model.user.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UserContentVerifierImpl implements UserContentVerifier {

    private final UserService userService;

    public UserContentVerifierImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Iterable<User> findLoaded(Iterable<User> users) {
        List<User> result = new ArrayList<>();
        for(User user : users) {
            if(userService.exists(user)) {
                result.add(user);
            }
        }
        log.info("{} users found as loaded", result.size());
        return result;
    }

    @Override
    public Iterable<User> findNotLoaded(Iterable<User> users) {
        List<User> result = new ArrayList<>();
        for(User user : users) {
            if(!userService.exists(user)) {
                result.add(user);
            }
        }
        log.info("{} users found as unloaded", result.size());
        return result;
    }
}
