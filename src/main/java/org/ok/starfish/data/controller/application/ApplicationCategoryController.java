package org.ok.starfish.data.controller.application;

import org.ok.starfish.data.service.application.ApplicationCategoryService;
import org.ok.starfish.model.application.ApplicationCategory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.ok.starfish.data.controller.Paths.APPLICATION_CATEGORY_PATH;

@RestController
@RequestMapping(path = APPLICATION_CATEGORY_PATH)
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
