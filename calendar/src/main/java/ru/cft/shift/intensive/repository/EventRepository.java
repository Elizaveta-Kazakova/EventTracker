package ru.cft.shift.intensive.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.cft.shift.intensive.repository.entity.Event;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findAllByOrderByDateAscTimeAsc();
    List<Event> findAllByOrderByDateAscTimeDesc();
    List<Event> findByDateBetweenOrderByDateAscTimeAsc(LocalDate date, LocalDate endDate2);
    List<Event> findByDateBetweenOrderByDateAscTimeDesc(LocalDate date, LocalDate endDate2);
    List<Event> findByDateAndTime(LocalDate date, LocalTime time);
    void deleteAllById(Long id);

}

