package ru.cft.shift.intensive.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.cft.shift.intensive.dto.EventDto;
import ru.cft.shift.intensive.dto.EventIdDto;
import ru.cft.shift.intensive.dto.ImportanceOfEvent;
import ru.cft.shift.intensive.exception.event.EventAlreadyExistException;
import ru.cft.shift.intensive.exception.event.EventNotFoundException;
import ru.cft.shift.intensive.repository.EventRepository;
import ru.cft.shift.intensive.repository.UserRepository;
import ru.cft.shift.intensive.repository.entity.Event;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@Slf4j
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    private EventDto toDto(Event event) {
        return new EventDto(event.getId(), event.getName(), event.getDate().toString(), event.getTime().toString(),
                event.getDescription(), event.getAccount().getUsername(),
                event.getImportanceOfEvent().toString());
    }


    @Autowired
    public EventServiceImpl(EventRepository eventRepository, UserRepository userRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void update(EventDto eventDto, Long id) {
        Event previousEvent = eventRepository.findById(id).orElseThrow(
                () ->  new EventNotFoundException("event with id = " + id + "not found"));

        eventRepository.deleteAllById(eventDto.getId());

        Event event = new Event(previousEvent.getId(), eventDto.getName(), LocalDate.parse(eventDto.getDate()),
                LocalTime.parse(eventDto.getTime()), eventDto.getDescription(),
                userRepository.findByUsername(eventDto.getAccount()),
                ImportanceOfEvent.valueOf(eventDto.getImportanceOfEvent()));

        eventRepository.save(event);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        try {
            eventRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new EventNotFoundException("event with id = " + id + "not found");
        }
    }

    @Override
    @Transactional
    public EventIdDto create(EventDto eventDto) {
        LocalDate localDate = LocalDate.parse(eventDto.getDate());
        LocalTime localTime = LocalTime.parse(eventDto.getTime());
        if (!eventRepository.findByDateAndTime(localDate, localTime).isEmpty()) {
            throw new EventAlreadyExistException("event in date:" + eventDto.getDate() + " and time: " + eventDto.getTime()
                            + "already exist");
        }
        final Event event = new Event(eventDto.getId(), eventDto.getName(), localDate,
                localTime, eventDto.getDescription(),
                userRepository.findByUsername(eventDto.getAccount()),
                ImportanceOfEvent.valueOf(eventDto.getImportanceOfEvent()));
        eventRepository.save(event);
        return new EventIdDto(event.getId());

    }

    @Override
    @Transactional(readOnly = true)
    public List<EventDto> listInPeriod(LocalDate startDate, LocalTime startTime, LocalDate endDate,
                                       LocalTime endTime, Sort.Direction sortType) {
        List<Event> list = sortType == Sort.Direction.ASC ?
                eventRepository.findByDateBetweenOrderByDateAscTimeAsc(startDate, endDate) :
                eventRepository.findByDateBetweenOrderByDateAscTimeDesc(startDate, endDate);
        return list.stream().map(this::toDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<EventDto> list(Sort.Direction sortType) {
        List<Event> list = sortType == Sort.Direction.ASC ? eventRepository.findAllByOrderByDateAscTimeAsc() :
                eventRepository.findAllByOrderByDateAscTimeDesc();
        return list.stream().map(this::toDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public EventDto findEventById(Long id) {
        return eventRepository.findById(id).map(this::toDto)
                .orElseThrow(() -> new EventNotFoundException("event with id = " + id + "not found"));
    }

}
