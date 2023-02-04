package ru.cft.shift.intensive.service;


import ru.cft.shift.intensive.dto.AccountDetails;

import java.util.List;

/**
 * Сервис для работы с пользователями/аутентификацией пользователей
 */
public interface UserDetailsService {
    /**
     * Поиск пользователя по логину
     *
     * @param username логин
     * @return пользователь
     */
    AccountDetails findUserByUsername(String username);

    /**
     * Список всех пользователей
     *
     * @return список пользователей
     */
    List<AccountDetails> findAll();

    /**
     * Добавление пользователя
     *
     * @param username        логин
     * @return добавленный пользователь
     */
    AccountDetails save(String username);
}
