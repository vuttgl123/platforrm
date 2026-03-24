package com.meta_forge_platform.definition.interfaces.rest.entity;

import com.meta_forge_platform.definition.application.dto.entity.request.CreateEntityRequest;
import com.meta_forge_platform.definition.application.dto.entity.response.EntityResponse;
import com.meta_forge_platform.definition.application.usecase.entity.CreateEntityUseCase;
import com.meta_forge_platform.definition.interfaces.mapper.entity.EntityDefinitionMapper;
import com.meta_forge_platform.shared.application.dto.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/definition/entities")
@RequiredArgsConstructor
public class EntityDefinitionController {

    private final CreateEntityUseCase createEntityUseCase;
    private final EntityDefinitionMapper entityMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<EntityResponse> create(@Valid @RequestBody CreateEntityRequest request) {
        var command = entityMapper.toCreateCommand(request);
        var response = createEntityUseCase.execute(command);
        return ApiResponse.success(response);
    }
}
