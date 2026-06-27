package com.example.ejerciciohyrule.services.impl;

import com.example.ejerciciohyrule.dto.request.CreateSpecimenRequest;
import com.example.ejerciciohyrule.dto.request.UpdateSpecimenRequest;
import com.example.ejerciciohyrule.dto.response.PageableResponse;
import com.example.ejerciciohyrule.dto.response.specimen.SpecimenResponse;
import com.example.ejerciciohyrule.entity.Specimen;
import com.example.ejerciciohyrule.exception.ResourceNotFoundException;
import com.example.ejerciciohyrule.mapper.SpecimenMapper;
import com.example.ejerciciohyrule.repository.SpecimenRepository;
import com.example.ejerciciohyrule.services.SpecimenService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SpecimenServiceImpl implements SpecimenService {
    private final SpecimenRepository specimenRepository;
    private final SpecimenMapper specimenMapper;

    @Override
    @Transactional
    public SpecimenResponse createSpecimen(CreateSpecimenRequest request) {
        return specimenMapper.toDto(
                specimenRepository.save(specimenMapper.toEntityCreate(request))
        );
    }

    @Override
    public PageableResponse<SpecimenResponse> getAllSpecimens(
            int page,
            int size,
            String sortBy,
            String sortOrder
    ) {

        Sort sort = sortOrder.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Specimen> specimens =
                specimenRepository.findAll(pageable);

        if (specimens.isEmpty()) {
            throw new ResourceNotFoundException(
                    "No specimens are registered in Hyrule"
            );
        }

        Page<SpecimenResponse> responsePage =
                specimenMapper.toDtoPage(specimens);

        return PageableResponse.<SpecimenResponse>builder()
                .content(responsePage.getContent())
                .page(responsePage.getNumber())
                .size(responsePage.getSize())
                .totalElements(responsePage.getTotalElements())
                .totalPages(responsePage.getTotalPages())
                .last(responsePage.isLast())
                .build();
    }

    @Override
    public SpecimenResponse getSpecimenById(UUID id) {
        return specimenMapper.toDto(specimenRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Specimen not found in Hyrule Records"))
        );
    }

    @Override
    @Transactional
    public SpecimenResponse updateSpecimen(UUID id, UpdateSpecimenRequest request) {
        this.getSpecimenById(id);
        return specimenMapper.toDto(specimenRepository.save(specimenMapper.toEntityUpdate(request, id)));
    }

    @Override
    @Transactional
    public SpecimenResponse deleteSpecimen(UUID id) {
        SpecimenResponse existSpecimen = this.getSpecimenById(id);
        specimenRepository.deleteById(id);
        return existSpecimen;
    }
}

