package org.ok.starfish.data.controller.application;

import org.ok.starfish.data.service.application.ApplicationCategoryService;
import org.ok.starfish.model.application.ApplicationCategory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ApplicationCategoryController {

    private final ApplicationCategoryService applicationCategoryService;

    public ApplicationCategoryController(ApplicationCategoryService applicationCategoryService) {
        this.applicationCategoryService = applicationCategoryService;
    }

    @GetMapping
    public ResponseEntity<List<ApplicationCategory>> findAll() {
        List<ApplicationCategory> allItems = applicationCategoryService.findAll();
        return ResponseEntity.ok(allItems);
    }
}
