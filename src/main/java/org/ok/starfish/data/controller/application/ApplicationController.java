package org.ok.starfish.data.controller.application;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.ok.starfish.data.service.application.ApplicationService;
import org.ok.starfish.model.application.Application;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.ok.starfish.data.controller.Paths.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping(path = APPLICATION_PATH)
public class ApplicationController {

    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @GetMapping
    @Operation(summary = "Find all applications")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Applications successfully found", content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Application.class)))}),
            @ApiResponse(responseCode = "400", description = "Failed to find applications", content = @Content) }
    )
    public @NotNull ResponseEntity<List<Application>> findAll() {
        List<Application> items = applicationService.findAll();
        return ResponseEntity.ok(items);
    }

    @Operation(summary = "Find an application by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Application successfully found by id", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Application.class))}),
            @ApiResponse(responseCode = "404", description = "Application with specified id was not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Failed to find application by id", content = @Content) })
    @GetMapping(value = "{id}")
    public @NotNull ResponseEntity<Application> findById(@Parameter(description = "The id of the application to be found") @PathVariable("id") @NotNull String id) {
        Optional<Application> item = applicationService.findById(id);
        return item.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Find a random application")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Random application successfully found", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Application.class))}),
            @ApiResponse(responseCode = "404", description = "Random application was not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Failed to find a random application", content = @Content) })
    @GetMapping(path = RANDOM_PATH)
    public @NotNull ResponseEntity<Application> findRandom() {
        Optional<Application> item = applicationService.findRandom();
        return item.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create an application")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Application created successfully", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Application.class))}),
            @ApiResponse(responseCode = "400", description = "Failed to create an application", content = @Content) })
    @PostMapping
    public @NotNull ResponseEntity<Application> save(@Parameter(description = "Application to be saved") @RequestBody @Valid @NotNull Application application) {
        HttpHeaders httpHeaders = new HttpHeaders();
        URI location = linkTo(ApplicationCategoryController.class).slash(application.getId()).toUri();
        httpHeaders.setLocation(location);
        Application saved = applicationService.save(application);
        return new ResponseEntity<>(saved, httpHeaders, HttpStatus.CREATED);
    }

    @Operation(summary = "Delete an application by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Application successfully deleted by id"),
            @ApiResponse(responseCode = "400", description = "Failed to delete application by id", content = @Content) })
    @DeleteMapping("/{id}")
    public @NotNull ResponseEntity<Void> deleteById(@Parameter(description = "The id of the application to be deleted") @PathVariable("id") @NotNull String id) {
        applicationService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Update an application by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Application updated successfully", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Application.class))}),
            @ApiResponse(responseCode = "404", description = "Application with specified id was not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Failed to update an application with specified id", content = @Content) })
    @PutMapping("/{id}")
    public @NotNull ResponseEntity<Application> update(@Parameter(description = "The id of the application to be updated") @PathVariable("id") @NotNull String id, @Parameter(description = "Application to be updated") @RequestBody @NotNull @Valid Application application) {
        Optional<Application> updated = applicationService.update(id, application);
        return updated.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Return the number of existing applications")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Applications counted successfully", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Long.class))}),
            @ApiResponse(responseCode = "400", description = "Failed to count applications", content = @Content) })
    @GetMapping(path = COUNT_PATH)
    public @NotNull ResponseEntity<Long> count() {
        long count = applicationService.count();
        return ResponseEntity.ok(count);
    }
}
