package com.challenge.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CasasDTO {

    private String id;
    private String name;
    private String headOfHouse;
    private List<String> values;
    private List<String> colors;
    private String school;
    private String mascot;
    private String houseGhost;
    private String founder;
}
