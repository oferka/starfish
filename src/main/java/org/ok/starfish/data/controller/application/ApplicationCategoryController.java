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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.ok.starfish.data.controller.Paths.APPLICATION_CATEGORY_PATH;
import static org.ok.starfish.data.controller.Paths.COUNT_PATH;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

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
    public @NotNull ResponseEntity<List<ApplicationCategory>> findAll() {
        List<ApplicationCategory> allItems = applicationCategoryService.findAll();
        return ResponseEntity.ok(allItems);
    }

    @Operation(summary = "Find an application category by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Application category successfully found by id", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ApplicationCategory.class))}),
            @ApiResponse(responseCode = "404", description = "Application category with specified id was not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Failed to find application category by id", content = @Content) })
    @GetMapping(value = "{id}")
    public @NotNull ResponseEntity<ApplicationCategory> findById(@Parameter(description = "The id of the application category to be found") @PathVariable("id") @NotNull String id) {
        Optional<ApplicationCategory> applicationCategory = applicationCategoryService.findById(id);
        return applicationCategory.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create an application category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Application category created successfully", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ApplicationCategory.class))}),
            @ApiResponse(responseCode = "400", description = "Failed to create an application category", content = @Content) })
    @PostMapping
    public @NotNull ResponseEntity<ApplicationCategory> save(@Parameter(description = "Application category to be saved") @RequestBody @Valid @NotNull ApplicationCategory applicationCategory) {
        HttpHeaders httpHeaders = new HttpHeaders();
        URI location = linkTo(ApplicationCategoryController.class).slash(applicationCategory.getId()).toUri();
        httpHeaders.setLocation(location);
        ApplicationCategory saved = applicationCategoryService.save(applicationCategory);
        return new ResponseEntity<>(saved, httpHeaders, HttpStatus.CREATED);
    }

    @Operation(summary = "Delete an application category by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Application category successfully deleted by id"),
            @ApiResponse(responseCode = "400", description = "Failed to delete application category by id", content = @Content) })
    @DeleteMapping("/{id}")
    public @NotNull ResponseEntity<Void> deleteById(@Parameter(description = "The id of the application category to be deleted") @PathVariable("id") @NotNull String id) {
        applicationCategoryService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Update an application category by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Application category updated successfully", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ApplicationCategory.class))}),
            @ApiResponse(responseCode = "404", description = "Application category with specified id was not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Failed to update an application category with specified id", content = @Content) })
    @PutMapping("/{id}")
    public @NotNull ResponseEntity<ApplicationCategory> update(@Parameter(description = "The id of the application category to be updated") @PathVariable("id") @NotNull String id, @Parameter(description = "Application category to be updated") @RequestBody @NotNull @Valid ApplicationCategory applicationCategory) {
        ApplicationCategory updated = applicationCategoryService.update(id, applicationCategory);
        if(updated != null) {
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Return the number of existing application categories")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Application category counted successfully", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Long.class))}),
            @ApiResponse(responseCode = "400", description = "Failed to count application categories", content = @Content) })
    @GetMapping(path = COUNT_PATH)
    public @NotNull ResponseEntity<Long> count() {
        long count = applicationCategoryService.count();
        return ResponseEntity.ok(count);
    }
}
