package org.example.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SouvenirDto {
    private String name;
    private Integer producer;
    private Integer year;
    private Double price;
}
