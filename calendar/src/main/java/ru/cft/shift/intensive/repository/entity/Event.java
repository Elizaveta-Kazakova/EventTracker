package ru.cft.shift.intensive.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.cft.shift.intensive.dto.ImportanceOfEvent;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "event")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private LocalDate date;
    @Column
    private LocalTime time;
    @Column
    private String description;
    @ManyToOne
    @JoinColumn(name = "account")
    private Account account;
    @Column
    private ImportanceOfEvent importanceOfEvent;
}
