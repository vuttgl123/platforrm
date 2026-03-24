package com.meta_forge_platform.definition.application.usecase.entity;

import com.meta_forge_platform.definition.application.dto.entity.command.CreateEntityCommand;
import com.meta_forge_platform.definition.application.dto.entity.response.EntityResponse;
import com.meta_forge_platform.definition.domain.model.entity.EntityDefinition;
import com.meta_forge_platform.definition.domain.model.module.ModuleDefinition;
import com.meta_forge_platform.definition.infrastructure.persistence.entity.EntityDefinitionRepository;
import com.meta_forge_platform.definition.infrastructure.persistence.module.ModuleDefinitionRepository;
import com.meta_forge_platform.definition.interfaces.mapper.entity.EntityDefinitionMapper;
import com.meta_forge_platform.shared.domain.exception.ConflictException;
import com.meta_forge_platform.shared.domain.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateEntityUseCase {

    private final ModuleDefinitionRepository moduleRepository;
    private final EntityDefinitionRepository entityRepository;
    private final EntityDefinitionMapper entityMapper;

    @Transactional
    public EntityResponse execute(CreateEntityCommand command) {
        ModuleDefinition module = moduleRepository.findById(command.moduleId())
                .orElseThrow(() -> new NotFoundException("Module not found: " + command.moduleId()));

        if (entityRepository.existsByModule_IdAndCode(module.getId(), command.code())) {
            throw new ConflictException("Entity code already exists in module: " + command.code());
        }

        var entity = new EntityDefinition(
                module,
                command.code(),
                command.name(),
                command.description()
        );

        if (command.tableStrategy() != null) {
            entity.changeTableStrategy(command.tableStrategy());
        }

        if (command.root() != null) {
            entity.setRoot(command.root());
        }

        if (command.active() != null) {
            entity.setActive(command.active());
        }

        entity.changeDisplayNamePattern(command.displayNamePattern());
        entity.changeDefaultSort(command.defaultSort());
        entity.changeConfig(command.config());

        var saved = entityRepository.save(entity);
        return entityMapper.toResponse(saved);
    }
}
