package ru.cft.shift.intensive.dto;

import lombok.*;

import java.io.Serializable;

/**
 * Данные пользователя
 */

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto implements Serializable {
    private String username;

}
