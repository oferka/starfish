package org.ok.starfish.data.controller.vendor;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.ok.starfish.data.controller.applicaton_category.ApplicationCategoryController;
import org.ok.starfish.data.service.vendor.VendorService;
import org.ok.starfish.model.vendor.Vendor;
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
@RequestMapping(path = VENDOR_PATH)
public class VendorController {

    private final VendorService vendorService;

    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @GetMapping
    @Operation(summary = "Find all vendors")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vendors successfully found", content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Vendor.class)))}),
            @ApiResponse(responseCode = "400", description = "Failed to find vendors", content = @Content) }
    )
    public @NotNull ResponseEntity<List<Vendor>> findAll() {
        List<Vendor> items = vendorService.findAll();
        return ResponseEntity.ok(items);
    }

    @Operation(summary = "Find an vendor by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vendor successfully found by id", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Vendor.class))}),
            @ApiResponse(responseCode = "404", description = "Vendor with specified id was not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Failed to find vendor by id", content = @Content) })
    @GetMapping(value = "{id}")
    public @NotNull ResponseEntity<Vendor> findById(@Parameter(description = "The id of the vendor to be found") @PathVariable("id") @NotNull String id) {
        Optional<Vendor> item = vendorService.findById(id);
        return item.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Find a random vendor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Random vendor successfully found", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Vendor.class))}),
            @ApiResponse(responseCode = "404", description = "Random vendor was not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Failed to find a random vendor", content = @Content) })
    @GetMapping(path = RANDOM_PATH)
    public @NotNull ResponseEntity<Vendor> findRandom() {
        Optional<Vendor> item = vendorService.findRandom();
        return item.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create an vendor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Vendor created successfully", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Vendor.class))}),
            @ApiResponse(responseCode = "400", description = "Failed to create an vendor", content = @Content) })
    @PostMapping
    public @NotNull ResponseEntity<Vendor> save(@Parameter(description = "Vendor to be saved") @RequestBody @Valid @NotNull Vendor vendor) {
        HttpHeaders httpHeaders = new HttpHeaders();
        URI location = linkTo(ApplicationCategoryController.class).slash(vendor.getId()).toUri();
        httpHeaders.setLocation(location);
        Vendor saved = vendorService.save(vendor);
        return new ResponseEntity<>(saved, httpHeaders, HttpStatus.CREATED);
    }

    @Operation(summary = "Delete an vendor by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Vendor successfully deleted by id"),
            @ApiResponse(responseCode = "400", description = "Failed to delete vendor by id", content = @Content) })
    @DeleteMapping("/{id}")
    public @NotNull ResponseEntity<Void> deleteById(@Parameter(description = "The id of the vendor to be deleted") @PathVariable("id") @NotNull String id) {
        vendorService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Update an vendor by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vendor updated successfully", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Vendor.class))}),
            @ApiResponse(responseCode = "404", description = "Vendor with specified id was not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Failed to update an vendor with specified id", content = @Content) })
    @PutMapping("/{id}")
    public @NotNull ResponseEntity<Vendor> update(@Parameter(description = "The id of the vendor to be updated") @PathVariable("id") @NotNull String id, @Parameter(description = "Vendor to be updated") @RequestBody @NotNull @Valid Vendor vendor) {
        Optional<Vendor> updated = vendorService.update(id, vendor);
        return updated.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Return the number of existing vendors")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vendors counted successfully", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Long.class))}),
            @ApiResponse(responseCode = "400", description = "Failed to count vendors", content = @Content) })
    @GetMapping(path = COUNT_PATH)
    public @NotNull ResponseEntity<Long> count() {
        long count = vendorService.count();
        return ResponseEntity.ok(count);
    }
}
