package ru.cft.shift.intensive.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.cft.shift.intensive.dto.AccountDetails;
import ru.cft.shift.intensive.exception.account.UserAlreadyExistsException;
import ru.cft.shift.intensive.exception.account.UserNotFoundException;
import ru.cft.shift.intensive.repository.UserRepository;
import ru.cft.shift.intensive.repository.entity.Account;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public AccountDetails findUserByUsername(String username) {
        Account account = userRepository.findByUsername(username);
        if (account == null) {
            log.warn("User {} not found", username);
            throw new UserNotFoundException("User " + username + " not found");
        }
        return account;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountDetails> findAll() {
        return new ArrayList<>(userRepository.findAll());
    }

    @Override
    @Transactional
    public AccountDetails save(String username) {
        Account account = userRepository.findByUsername(username);
        if (account != null) {
            log.warn("User {} already exists", username);
            throw new UserAlreadyExistsException(String.format("User %s already exists", username));
        }

        account = new Account();
        account.setUsername(username);
        log.info("saved new account");
        return userRepository.save(account);
    }
}
