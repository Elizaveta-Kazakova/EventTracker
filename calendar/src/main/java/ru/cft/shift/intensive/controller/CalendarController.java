package ru.cft.shift.intensive.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.cft.shift.intensive.dto.EventDto;
import ru.cft.shift.intensive.dto.EventIdDto;
import ru.cft.shift.intensive.service.EventService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@Slf4j
@RequestMapping(value = "calendar", produces = MediaType.APPLICATION_JSON_VALUE)
public class CalendarController {
    private final EventService service;

    @Autowired
    public CalendarController(EventService service) {
        this.service = service;
    }

    @GetMapping(value = "events", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EventDto>> getAllEvents(@RequestParam Sort.Direction sortType) {
        List<EventDto> events = service.list(sortType);
        return ResponseEntity.ok(events);
    }

    @GetMapping(value = "events/in-period", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EventDto>> getAllEventsInPeriod(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                               @RequestParam LocalDate startDate,
                                                               @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
                                                               @RequestParam LocalTime startTime,
                                                               @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                               @RequestParam LocalDate endDate,
                                                               @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
                                                               @RequestParam LocalTime endTime,
                                                               @RequestParam Sort.Direction sortType) {
        List<EventDto> events = service.listInPeriod(startDate, startTime, endDate, endTime, sortType);
        return ResponseEntity.ok(events);
    }

    @PostMapping(value = "events", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EventIdDto> createEvent(@RequestBody EventDto event) {
        log.info("Create new event : " + event.getName());
        return ResponseEntity.ok(service.create(event));
    }

//    @PutMapping(value = "events/update/{id} ", consumes = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<Void> updateEvent(@RequestBody EventDto event, @PathVariable Long id) {
//        service.update(event, id);
//        return ResponseEntity.ok().build();
//    }

    @DeleteMapping(value = "events/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "events/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EventDto> getEvent(@PathVariable Long id) {
        return ResponseEntity.ok(service.findEventById(id));

    }
}
