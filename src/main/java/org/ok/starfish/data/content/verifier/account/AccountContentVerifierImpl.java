package org.ok.starfish.data.content.verifier.account;

import lombok.extern.slf4j.Slf4j;
import org.ok.starfish.data.service.account.AccountService;
import org.ok.starfish.model.account.Account;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class AccountContentVerifierImpl implements AccountContentVerifier {

    private final AccountService accountService;

    public AccountContentVerifierImpl(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public Iterable<Account> findLoaded(Iterable<Account> accounts) {
        List<Account> result = new ArrayList<>();
        for(Account account : accounts) {
            if(accountService.exists(account)) {
                result.add(account);
            }
        }
        log.info("{} accounts found as loaded", result.size());
        return result;
    }

    @Override
    public Iterable<Account> findNotLoaded(Iterable<Account> accounts) {
        List<Account> result = new ArrayList<>();
        for(Account account : accounts) {
            if(!accountService.exists(account)) {
                result.add(account);
            }
        }
        log.info("{} accounts found as unloaded", result.size());
        return result;
    }
}
