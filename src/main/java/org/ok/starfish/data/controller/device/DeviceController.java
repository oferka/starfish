package org.ok.starfish.data.controller.device;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.ok.starfish.data.controller.applicaton_category.ApplicationCategoryController;
import org.ok.starfish.data.service.device.DeviceService;
import org.ok.starfish.model.device.Device;
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
@RequestMapping(path = DEVICE_PATH)
public class DeviceController {

    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @GetMapping
    @Operation(summary = "Find all devices")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Devices successfully found", content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Device.class)))}),
            @ApiResponse(responseCode = "400", description = "Failed to find devices", content = @Content) }
    )
    public @NotNull ResponseEntity<List<Device>> findAll() {
        List<Device> items = deviceService.findAll();
        return ResponseEntity.ok(items);
    }

    @Operation(summary = "Find a device by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Device successfully found by id", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Device.class))}),
            @ApiResponse(responseCode = "404", description = "Device with specified id was not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Failed to find device by id", content = @Content) })
    @GetMapping(value = "{id}")
    public @NotNull ResponseEntity<Device> findById(@Parameter(description = "The id of the device to be found") @PathVariable("id") @NotNull String id) {
        Optional<Device> item = deviceService.findById(id);
        return item.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Find a random device")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Random device successfully found", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Device.class))}),
            @ApiResponse(responseCode = "404", description = "Random device was not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Failed to find a device", content = @Content) })
    @GetMapping(path = RANDOM_PATH)
    public @NotNull ResponseEntity<Device> findRandom() {
        Optional<Device> item = deviceService.findRandom();
        return item.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create a device")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Device created successfully", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Device.class))}),
            @ApiResponse(responseCode = "400", description = "Failed to create a device", content = @Content) })
    @PostMapping
    public @NotNull ResponseEntity<Device> save(@Parameter(description = "Device to be saved") @RequestBody @Valid @NotNull Device device) {
        HttpHeaders httpHeaders = new HttpHeaders();
        URI location = linkTo(ApplicationCategoryController.class).slash(device.getId()).toUri();
        httpHeaders.setLocation(location);
        Device saved = deviceService.save(device);
        return new ResponseEntity<>(saved, httpHeaders, HttpStatus.CREATED);
    }

    @Operation(summary = "Delete a device by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Device successfully deleted by id"),
            @ApiResponse(responseCode = "400", description = "Failed to delete device by id", content = @Content) })
    @DeleteMapping("/{id}")
    public @NotNull ResponseEntity<Void> deleteById(@Parameter(description = "The id of the device to be deleted") @PathVariable("id") @NotNull String id) {
        deviceService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Update a device by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Device updated successfully", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Device.class))}),
            @ApiResponse(responseCode = "404", description = "Device with specified id was not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Failed to update a device with specified id", content = @Content) })
    @PutMapping("/{id}")
    public @NotNull ResponseEntity<Device> update(@Parameter(description = "The id of the device to be updated") @PathVariable("id") @NotNull String id, @Parameter(description = "Device to be updated") @RequestBody @NotNull @Valid Device device) {
        Optional<Device> updated = deviceService.update(id, device);
        return updated.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Return the number of existing devices")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Devices counted successfully", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Long.class))}),
            @ApiResponse(responseCode = "400", description = "Failed to count devices", content = @Content) })
    @GetMapping(path = COUNT_PATH)
    public @NotNull ResponseEntity<Long> count() {
        long count = deviceService.count();
        return ResponseEntity.ok(count);
    }
}
