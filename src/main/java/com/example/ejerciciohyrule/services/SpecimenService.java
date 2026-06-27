package com.example.ejerciciohyrule.services;

import com.example.ejerciciohyrule.dto.request.CreateSpecimenRequest;
import com.example.ejerciciohyrule.dto.request.UpdateSpecimenRequest;
import com.example.ejerciciohyrule.dto.response.PageableResponse;
import com.example.ejerciciohyrule.dto.response.specimen.SpecimenResponse;

import java.util.UUID;

public interface SpecimenService {

    SpecimenResponse createSpecimen(CreateSpecimenRequest request);

    PageableResponse<SpecimenResponse> getAllSpecimens(
            int page,
            int size,
            String sortBy,
            String sortOrder
    );

    SpecimenResponse getSpecimenById(UUID id);

    SpecimenResponse updateSpecimen(UUID id, UpdateSpecimenRequest request);

    SpecimenResponse deleteSpecimen(UUID id);
}


