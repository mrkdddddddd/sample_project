package com.example.back.dto;

import lombok.Data;

@Data
public class AirData {
    private Long id;
    private String station;
    private String time;
    private Integer pm10;
    private Integer pm25;
}