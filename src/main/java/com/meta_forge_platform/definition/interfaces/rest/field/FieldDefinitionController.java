package com.meta_forge_platform.definition.interfaces.rest.field;

import com.meta_forge_platform.definition.application.dto.field.request.CreateFieldRequest;
import com.meta_forge_platform.definition.application.dto.field.response.FieldResponse;
import com.meta_forge_platform.definition.application.usecase.field.CreateFieldUseCase;
import com.meta_forge_platform.definition.interfaces.mapper.field.FieldDefinitionMapper;
import com.meta_forge_platform.shared.application.dto.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/definition/fields")
@RequiredArgsConstructor
public class FieldDefinitionController {

    private final CreateFieldUseCase createFieldUseCase;
    private final FieldDefinitionMapper fieldMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<FieldResponse> create(@Valid @RequestBody CreateFieldRequest request) {
        var command = fieldMapper.toCreateCommand(request);
        var response = createFieldUseCase.execute(command);
        return ApiResponse.success(response);
    }
}
