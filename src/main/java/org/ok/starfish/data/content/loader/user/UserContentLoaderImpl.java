package org.ok.starfish.data.content.loader.user;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.IterableUtils;
import org.ok.starfish.data.content.provider.user.UserContentProvider;
import org.ok.starfish.data.content.verifier.user.UserContentVerifier;
import org.ok.starfish.data.service.user.UserService;
import org.ok.starfish.model.user.User;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;

@Service
@Slf4j
public class UserContentLoaderImpl implements UserContentLoader {

    private final UserContentProvider userContentProvider;
    private final UserContentVerifier userContentVerifier;
    private final UserService userService;

    public UserContentLoaderImpl(UserContentProvider userContentProvider, UserContentVerifier userContentVerifier, UserService userService) {
        this.userContentProvider = userContentProvider;
        this.userContentVerifier = userContentVerifier;
        this.userService = userService;
    }

    @Override
    public @NotNull Iterable<User> ensureContentLoaded() {
        List<User> content = userContentProvider.get(100);
        Iterable<User> unloadedContent = userContentVerifier.findNotLoaded(content);
        if(!IterableUtils.isEmpty(unloadedContent)) {
            Iterable<User> saved = userService.saveAll(unloadedContent);
            log.info("{} users saved", IterableUtils.size(saved));
        }
        log.info("{} users ensured loaded", content.size());
        return content;
    }
}
