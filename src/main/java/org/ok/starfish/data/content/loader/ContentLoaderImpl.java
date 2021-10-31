package org.ok.starfish.data.content.loader;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.IterableUtils;
import org.ok.starfish.data.content.loader.account.AccountContentLoader;
import org.ok.starfish.data.content.loader.application.ApplicationContentLoader;
import org.ok.starfish.data.content.loader.applicaton_category.ApplicationCategoryContentLoader;
import org.ok.starfish.data.content.loader.device.DeviceContentLoader;
import org.ok.starfish.data.content.loader.user.UserContentLoader;
import org.ok.starfish.model.account.Account;
import org.ok.starfish.model.application.Application;
import org.ok.starfish.model.applicaton_category.ApplicationCategory;
import org.ok.starfish.model.device.Device;
import org.ok.starfish.model.user.User;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ContentLoaderImpl implements ContentLoader {

    private final ApplicationCategoryContentLoader applicationCategoryContentLoader;
    private final ApplicationContentLoader applicationContentLoader;
    private final AccountContentLoader accountContentLoader;
    private final UserContentLoader userContentLoader;
    private final DeviceContentLoader deviceContentLoader;

    public ContentLoaderImpl(ApplicationCategoryContentLoader applicationCategoryContentLoader, ApplicationContentLoader applicationContentLoader, AccountContentLoader accountContentLoader, UserContentLoader userContentLoader, DeviceContentLoader deviceContentLoader) {
        this.applicationCategoryContentLoader = applicationCategoryContentLoader;
        this.applicationContentLoader = applicationContentLoader;
        this.accountContentLoader = accountContentLoader;
        this.userContentLoader = userContentLoader;
        this.deviceContentLoader = deviceContentLoader;
    }

    @Override
    public void ensureContentLoaded() {
        Iterable<ApplicationCategory> applicationCategories = applicationCategoryContentLoader.ensureContentLoaded();
        Iterable<Application> applications = applicationContentLoader.ensureContentLoaded();
        Iterable<Account> accounts = accountContentLoader.ensureContentLoaded();
        Iterable<User> users = userContentLoader.ensureContentLoaded();
        Iterable<Device> devices = deviceContentLoader.ensureContentLoaded();
        log.info("{} content entities ensured loaded", IterableUtils.size(applicationCategories) + IterableUtils.size(applications) + IterableUtils.size(accounts) + IterableUtils.size(users) + IterableUtils.size(devices));
    }
}
