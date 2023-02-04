package ru.cft.shift.intensive.service;

import org.springframework.data.domain.Sort;
import ru.cft.shift.intensive.dto.EventDto;
import ru.cft.shift.intensive.dto.EventIdDto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface EventService {

    List<EventDto> list(Sort.Direction sortType);    // get

    EventDto findEventById(Long id); // get

    List<EventDto> listInPeriod(LocalDate startDate, LocalTime startTime, LocalDate endDate,
                                LocalTime endTime, Sort.Direction sortType); // get

    void update(EventDto eventDto, Long id);               // put

    void delete(Long id);                         // delete

    EventIdDto create(EventDto eventDto);         // post


}
