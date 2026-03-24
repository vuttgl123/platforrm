package com.meta_forge_platform.definition.application.usecase.module;

import com.meta_forge_platform.definition.application.dto.module.command.CreateModuleCommand;
import com.meta_forge_platform.definition.application.dto.module.response.ModuleResponse;
import com.meta_forge_platform.definition.domain.model.module.ModuleDefinition;
import com.meta_forge_platform.definition.infrastructure.persistence.module.ModuleDefinitionRepository;
import com.meta_forge_platform.definition.interfaces.mapper.module.ModuleDefinitionMapper;
import com.meta_forge_platform.shared.domain.exception.ConflictException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateModuleUseCase {

    private final ModuleDefinitionRepository moduleRepository;
    private final ModuleDefinitionMapper moduleMapper;

    @Transactional
    public ModuleResponse execute(CreateModuleCommand command) {
        if (moduleRepository.existsByCode(command.code())) {
            throw new ConflictException("Module code already exists: " + command.code());
        }

        var module = new ModuleDefinition(
                command.code(),
                command.name(),
                command.description()
        );

        var saved = moduleRepository.save(module);
        return moduleMapper.toResponse(saved);
    }
}
