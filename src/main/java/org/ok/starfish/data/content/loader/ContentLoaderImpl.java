package org.ok.starfish.data.content.loader;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.IterableUtils;
import org.ok.starfish.data.content.loader.account.AccountContentLoader;
import org.ok.starfish.data.content.loader.application.ApplicationCategoryContentLoader;
import org.ok.starfish.data.content.loader.application.ApplicationContentLoader;
import org.ok.starfish.data.content.loader.user.UserContentLoader;
import org.ok.starfish.model.account.Account;
import org.ok.starfish.model.application.Application;
import org.ok.starfish.model.application.ApplicationCategory;
import org.ok.starfish.model.user.User;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ContentLoaderImpl implements ContentLoader {

    private final ApplicationCategoryContentLoader applicationCategoryContentLoader;
    private final ApplicationContentLoader applicationContentLoader;
    private final AccountContentLoader accountContentLoader;
    private final UserContentLoader userContentLoader;

    public ContentLoaderImpl(ApplicationCategoryContentLoader applicationCategoryContentLoader, ApplicationContentLoader applicationContentLoader, AccountContentLoader accountContentLoader, UserContentLoader userContentLoader) {
        this.applicationCategoryContentLoader = applicationCategoryContentLoader;
        this.applicationContentLoader = applicationContentLoader;
        this.accountContentLoader = accountContentLoader;
        this.userContentLoader = userContentLoader;
    }

    @Override
    public void ensureContentLoaded() {
        Iterable<ApplicationCategory> applicationCategories = applicationCategoryContentLoader.ensureContentLoaded();
        Iterable<Application> applications = applicationContentLoader.ensureContentLoaded();
        Iterable<Account> accounts = accountContentLoader.ensureContentLoaded();
        Iterable<User> users = userContentLoader.ensureContentLoaded();
        log.info("{} content entities ensured loaded", IterableUtils.size(applicationCategories) + IterableUtils.size(applications) + IterableUtils.size(accounts) + IterableUtils.size(users));
    }
}
