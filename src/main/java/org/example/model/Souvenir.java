package org.example.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Souvenir {
    private String name;
    private Producer producer;
    private Integer year;
    private Double price;
}
