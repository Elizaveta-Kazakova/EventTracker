package ru.cft.shift.intensive.repository.entity;

import lombok.*;
import ru.cft.shift.intensive.dto.AccountDetails;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

/**
 * Таблица пользователей
 */

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "account")
public class Account implements AccountDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String username;
    @OneToMany(mappedBy = "account")
    private List<Event> events;
//    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    private List<Event> userEvents;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return id.equals(account.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username);
    }
}
