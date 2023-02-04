package ru.cft.shift.intensive.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.cft.shift.intensive.repository.entity.Account;

/**
 * Интерфейс для работы с пользователями на уровне БД
 */
public interface UserRepository extends JpaRepository<Account, Long> {
    /**
     * Поиск пользователя в БД по его логину
     *
     * @param username логин пользователя
     * @return сущность пользователя из БД
     */
    Account findByUsername(String username);
}
