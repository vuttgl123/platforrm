package com.meta_forge_platform.definition.interfaces.rest.module;

import com.meta_forge_platform.definition.application.dto.module.request.CreateModuleRequest;
import com.meta_forge_platform.definition.application.dto.module.response.ModuleResponse;
import com.meta_forge_platform.definition.application.usecase.module.CreateModuleUseCase;
import com.meta_forge_platform.definition.interfaces.mapper.module.ModuleDefinitionMapper;
import com.meta_forge_platform.shared.application.dto.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/definition/modules")
@RequiredArgsConstructor
public class ModuleDefinitionController {

    private final CreateModuleUseCase createModuleUseCase;
    private final ModuleDefinitionMapper moduleMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<ModuleResponse> create(@Valid @RequestBody CreateModuleRequest request) {
        var command = moduleMapper.toCreateCommand(request);
        var response = createModuleUseCase.execute(command);
        return ApiResponse.success(response);
    }
}
