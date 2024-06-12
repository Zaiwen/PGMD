package com.example.swinedatebaseproject.controller.data.impl;

import com.example.swinedatebaseproject.response.ResponseResult;
import com.example.swinedatebaseproject.service.NutrientCompositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/nutrient",method = {RequestMethod.DELETE,RequestMethod.GET,RequestMethod.POST})
public class NutrientController {
    @Autowired
    private NutrientCompositionService service;


    @PostMapping("/search/{Bio_Project}")
    public ResponseResult searchNutrientComposition(@PathVariable String Bio_Project) {
        return service.searchNutrientComposition(Bio_Project);
    }
}
