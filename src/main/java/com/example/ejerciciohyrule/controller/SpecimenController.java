package com.example.ejerciciohyrule.controller;

import com.example.ejerciciohyrule.dto.request.CreateSpecimenRequest;
import com.example.ejerciciohyrule.dto.request.UpdateSpecimenRequest;
import com.example.ejerciciohyrule.dto.response.GeneralResponse;
import com.example.ejerciciohyrule.services.SpecimenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/api/specimens")
@RequiredArgsConstructor
public class SpecimenController {

    private final SpecimenService specimenService;

    @PostMapping
    public ResponseEntity<GeneralResponse> createSpecimen(
            @Valid @RequestBody CreateSpecimenRequest request
    ) {
        return buildResponse(
                "Specimen created successfully",
                HttpStatus.CREATED,
                specimenService.createSpecimen(request)
        );
    }

    @GetMapping
    public ResponseEntity<GeneralResponse> getAllSpecimens(

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size,

            @RequestParam(defaultValue = "id")
            String sortBy,

            @RequestParam(defaultValue = "asc")
            String sortOrder
    ) {

        return buildResponse(
                "Specimens retrieved successfully",
                HttpStatus.OK,
                specimenService.getAllSpecimens(
                        page,
                        size,
                        sortBy,
                        sortOrder
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<GeneralResponse> getSpecimenById(
            @PathVariable UUID id
    ) {

        return buildResponse(
                "Specimen found successfully",
                HttpStatus.OK,
                specimenService.getSpecimenById(id)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<GeneralResponse> updateSpecimen(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateSpecimenRequest request
    ) {

        return buildResponse(
                "Specimen updated successfully",
                HttpStatus.OK,
                specimenService.updateSpecimen(id, request)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GeneralResponse> deleteSpecimen(
            @PathVariable UUID id
    ) {

        return buildResponse(
                "Specimen deleted successfully",
                HttpStatus.OK,
                specimenService.deleteSpecimen(id)
        );
    }

    public ResponseEntity<GeneralResponse> buildResponse(String message, HttpStatus status, Object data) {
        String uri = ServletUriComponentsBuilder.fromCurrentRequestUri().build().getPath();
        return ResponseEntity
                .status(status)
                .body(GeneralResponse.builder()
                        .uri(uri)
                        .message(message)
                        .status(status.value())
                        .time(LocalDateTime.now())
                        .data(data)
                        .build()
                );
    }
}

