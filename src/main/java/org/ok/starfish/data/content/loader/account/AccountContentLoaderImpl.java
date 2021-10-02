package org.ok.starfish.data.content.loader.account;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.IterableUtils;
import org.ok.starfish.data.content.provider.account.AccountContentProvider;
import org.ok.starfish.data.content.verifier.account.AccountContentVerifier;
import org.ok.starfish.data.service.account.AccountService;
import org.ok.starfish.model.account.Account;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;

@Service
@Slf4j
public class AccountContentLoaderImpl implements AccountContentLoader {

    private final AccountContentProvider accountContentProvider;
    private final AccountContentVerifier accountContentVerifier;
    private final AccountService accountService;

    public AccountContentLoaderImpl(AccountContentProvider accountContentProvider, AccountContentVerifier accountContentVerifier, AccountService accountService) {
        this.accountContentProvider = accountContentProvider;
        this.accountContentVerifier = accountContentVerifier;
        this.accountService = accountService;
    }

    @Override
    public @NotNull Iterable<Account> ensureContentLoaded() {
        List<Account> content = accountContentProvider.get(100);
        Iterable<Account> unloadedContent = accountContentVerifier.findNotLoaded(content);
        if(!IterableUtils.isEmpty(unloadedContent)) {
            Iterable<Account> saved = accountService.saveAll(unloadedContent);
            log.info("{} accounts saved", IterableUtils.size(saved));
        }
        log.info("{} accounts ensured loaded", content.size());
        return content;
    }
}
