package com.example.swinedatebaseproject.controller.data.impl;

import com.example.swinedatebaseproject.response.ResponseResult;
import com.example.swinedatebaseproject.service.LefseSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/Lsfse",method = {RequestMethod.DELETE,RequestMethod.GET,RequestMethod.POST})
public class LefseController {

    @Autowired
    private LefseSevice service;

    @PostMapping("/search_lefse/{name}")
    public ResponseResult searchLefse(@PathVariable String name) {
        return service.searchLefse(name);
    }
}
