package ru.cft.shift.intensive.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.cft.shift.intensive.dto.AccountDto;
import ru.cft.shift.intensive.dto.AccountDetails;
import ru.cft.shift.intensive.service.UserDetailsService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * API для работы с пользователями
 */
@RestController
@Slf4j
@RequestMapping(value = "calendar/accounts", produces = MediaType.APPLICATION_JSON_VALUE)
public class AccountController {
    private final UserDetailsService userDetailsService;

    @Autowired
    public AccountController(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @GetMapping
    public ResponseEntity<List<String>> list() {
        List<String> accounts = userDetailsService.findAll()
                .stream()
                .map(AccountDetails::getUsername)
                .collect(Collectors.toList());
        return ResponseEntity.ok(accounts);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> create(@Valid @RequestBody AccountDto account) {
        userDetailsService.save(account.getUsername());
        log.info("create new account with name " + account.getUsername());
        return ResponseEntity.ok().build();
    }

    @GetMapping("{username}")
    public ResponseEntity<AccountDetails> get(@PathVariable String username) {
        var accountDetails = userDetailsService.findUserByUsername(username);
        return ResponseEntity.ok(accountDetails);
    }
}
