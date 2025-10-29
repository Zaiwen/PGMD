package com.example.swinedatebaseproject.controller;

import com.example.swinedatebaseproject.domain.Counter;
import com.example.swinedatebaseproject.service.CounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/counter")
public class CounterController {
    @Autowired
    private CounterService counterService;

    @GetMapping("/{page}")
    public Counter getCounterByPage(@PathVariable String page) {
        return counterService.getCounterByPage(page);
    }

    @PostMapping("/{page}")
    public void updateCounterByPage(@PathVariable String page) {
        counterService.updateCounterByPage(page);
    }
}
