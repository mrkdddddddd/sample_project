package com.example.back.controller;

import com.example.back.dto.AirData;
import com.example.back.service.DataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class DataController {

    private final DataService service;
    
    // 데이터 조회 API
    @GetMapping("/air/{station}")
    public ResponseEntity<List<AirData>> getData(@PathVariable String station) {
        return ResponseEntity.ok(service.fetchAndSave(station));
    }
}
