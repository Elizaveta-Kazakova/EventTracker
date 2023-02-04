package ru.cft.shift.intensive.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventDto {

    private Long id;
    private String name;
    private String date;
    private String time;
    private String description;
    private String account;
    private String importanceOfEvent;
}
