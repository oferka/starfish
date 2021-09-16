package org.ok.starfish.data.controller.application;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.ok.starfish.data.service.application.ApplicationCategoryService;
import org.ok.starfish.model.application.ApplicationCategory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import static org.ok.starfish.data.controller.Paths.APPLICATION_CATEGORY_PATH;

@RestController
@RequestMapping(path = APPLICATION_CATEGORY_PATH)
public class ApplicationCategoryController {

    private final ApplicationCategoryService applicationCategoryService;

    public ApplicationCategoryController(ApplicationCategoryService applicationCategoryService) {
        this.applicationCategoryService = applicationCategoryService;
    }

    @GetMapping
    @Operation(summary = "Find all application categories")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Application categories successfully found", content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ApplicationCategory.class)))}),
            @ApiResponse(responseCode = "400", description = "Failed to find application categories", content = @Content) }
    )
    public ResponseEntity<List<ApplicationCategory>> findAll() {
        List<ApplicationCategory> allItems = applicationCategoryService.findAll();
        return ResponseEntity.ok(allItems);
    }

    @Operation(summary = "Find an application category by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Application category successfully found by id", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ApplicationCategory.class))}),
            @ApiResponse(responseCode = "404", description = "Application category with specified id was not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Failed to find application category by id", content = @Content) })
    @GetMapping(value = "{id}")
    public ResponseEntity<ApplicationCategory> findById(@Parameter(description = "The id of the application category to be found") @PathVariable("id") String id) {
        Optional<ApplicationCategory> applicationCategory = applicationCategoryService.findById(id);
        return applicationCategory.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
