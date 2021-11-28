package org.ok.starfish.data.controller.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.ok.starfish.data.service.user.UserService;
import org.ok.starfish.model.user.User;
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
@RequestMapping(path = USER_PATH)
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @Operation(summary = "Find all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users successfully found", content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = User.class)))}),
            @ApiResponse(responseCode = "400", description = "Failed to find users", content = @Content) }
    )
    public @NotNull ResponseEntity<List<User>> findAll() {
        List<User> items = userService.findAll();
        return ResponseEntity.ok(items);
    }

    @Operation(summary = "Find a user by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User successfully found by id", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "404", description = "User with specified id was not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Failed to find user by id", content = @Content) })
    @GetMapping(value = "{id}")
    public @NotNull ResponseEntity<User> findById(@Parameter(description = "The id of the user to be found") @PathVariable("id") @NotNull String id) {
        Optional<User> item = userService.findById(id);
        return item.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Find a random user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Random user successfully found", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "404", description = "Random user was not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Failed to find a user", content = @Content) })
    @GetMapping(path = RANDOM_PATH)
    public @NotNull ResponseEntity<User> findRandom() {
        Optional<User> item = userService.findRandom();
        return item.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "400", description = "Failed to create a user", content = @Content) })
    @PostMapping
    public @NotNull ResponseEntity<User> save(@Parameter(description = "User to be saved") @RequestBody @Valid @NotNull User user) {
        HttpHeaders httpHeaders = new HttpHeaders();
        URI location = linkTo(UserController.class).slash(user.getId()).toUri();
        httpHeaders.setLocation(location);
        User saved = userService.save(user);
        return new ResponseEntity<>(saved, httpHeaders, HttpStatus.CREATED);
    }

    @Operation(summary = "Delete a user by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User successfully deleted by id"),
            @ApiResponse(responseCode = "400", description = "Failed to delete user by id", content = @Content) })
    @DeleteMapping("/{id}")
    public @NotNull ResponseEntity<Void> deleteById(@Parameter(description = "The id of the user to be deleted") @PathVariable("id") @NotNull String id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Update a user by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "404", description = "User with specified id was not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Failed to update a user with specified id", content = @Content) })
    @PutMapping("/{id}")
    public @NotNull ResponseEntity<User> update(@Parameter(description = "The id of the user to be updated") @PathVariable("id") @NotNull String id, @Parameter(description = "User to be updated") @RequestBody @NotNull @Valid User user) {
        Optional<User> updated = userService.update(id, user);
        return updated.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Return the number of existing users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users counted successfully", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Long.class))}),
            @ApiResponse(responseCode = "400", description = "Failed to count users", content = @Content) })
    @GetMapping(path = COUNT_PATH)
    public @NotNull ResponseEntity<Long> count() {
        long count = userService.count();
        return ResponseEntity.ok(count);
    }
}
