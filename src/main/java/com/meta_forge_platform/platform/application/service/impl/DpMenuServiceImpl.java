package com.meta_forge_platform.platform.application.service.impl;

import com.meta_forge_platform.platform.application.dto.menu.*;
import com.meta_forge_platform.platform.application.mapper.DpMenuMapper;
import com.meta_forge_platform.platform.application.service.DpMenuService;
import com.meta_forge_platform.platform.domain.entity.DpMenu;
import com.meta_forge_platform.platform.domain.entity.DpModule;
import com.meta_forge_platform.platform.domain.entity.DpScreen;
import com.meta_forge_platform.platform.domain.enumeration.MenuType;
import com.meta_forge_platform.platform.infrastructure.repository.DpMenuRepository;
import com.meta_forge_platform.platform.infrastructure.repository.DpModuleRepository;
import com.meta_forge_platform.platform.infrastructure.repository.DpScreenRepository;
import com.meta_forge_platform.shared.application.BaseServiceImpl;
import com.meta_forge_platform.shared.domain.exception.AppException;
import com.meta_forge_platform.shared.domain.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Slf4j
@Service
@Transactional(readOnly = true)
public class DpMenuServiceImpl
        extends BaseServiceImpl<DpMenu, DpMenuDto, CreateDpMenuCmd, UpdateDpMenuCmd, Long>
        implements DpMenuService {

    private final DpMenuRepository menuRepository;
    private final DpModuleRepository moduleRepository;
    private final DpScreenRepository screenRepository;
    private final DpMenuMapper mapper;

    public DpMenuServiceImpl(DpMenuRepository menuRepository,
                             DpModuleRepository moduleRepository,
                             DpScreenRepository screenRepository,
                             DpMenuMapper mapper) {
        super(menuRepository);
        this.menuRepository = menuRepository;
        this.moduleRepository = moduleRepository;
        this.screenRepository = screenRepository;
        this.mapper = mapper;
    }

    @Override
    protected String getEntityName() {
        return "DpMenu";
    }

    @Override
    protected DpMenuDto toDto(DpMenu entity) {
        return mapper.toDto(entity);
    }

    @Override
    protected DpMenu toEntity(CreateDpMenuCmd command) {
        DpModule module = moduleRepository.findActiveById(command.getModuleId())
                .orElseThrow(() -> AppException.of(
                        ErrorCode.ENTITY_NOT_FOUND,
                        "DpModule",
                        command.getModuleId()
                ));

        DpMenu parentMenu = resolveParentMenu(command.getParentMenuId());
        DpScreen screen = resolveScreen(command.getScreenId());

        DpMenu menu = DpMenu.create(
                module,
                command.getMenuCode(),
                command.getMenuName(),
                toMenuType(command.getMenuType())
        );

        menu.applyMetadata(
                command.getMenuName(),
                parentMenu,
                toMenuType(command.getMenuType()),
                screen,
                command.getRoutePath(),
                command.getIcon(),
                command.getSortOrder(),
                command.getIsActive(),
                command.getConfigJson()
        );

        menu.applyHierarchy();

        return menu;
    }

    @Override
    protected void updateEntity(DpMenu entity, UpdateDpMenuCmd command) {
        DpMenu parentMenu = resolveParentMenu(command.getParentMenuId());
        DpScreen screen = resolveScreen(command.getScreenId());

        validateParentNotSelf(entity.getId(), parentMenu);

        entity.applyMetadata(
                command.getMenuName(),
                parentMenu,
                toMenuType(command.getMenuType()),
                screen,
                command.getRoutePath(),
                command.getIcon(),
                command.getSortOrder(),
                command.getIsActive(),
                command.getConfigJson()
        );

        entity.applyHierarchy();
    }

    @Override
    protected void validateCreateCommand(CreateDpMenuCmd command) {
        super.validateCreateCommand(command);
        validateNotNull(command.getModuleId(), "moduleId");
        validateNotNull(command.getMenuCode(), "menuCode");
        validateNotNull(command.getMenuName(), "menuName");
        validateNotNull(command.getMenuType(), "menuType");
        validateNotNull(command.getSortOrder(), "sortOrder");
        validateNotNull(command.getIsActive(), "isActive");
    }

    @Override
    protected void validateUpdateCommand(UpdateDpMenuCmd command) {
        super.validateUpdateCommand(command);
        validateNotNull(command.getMenuName(), "menuName");
        validateNotNull(command.getMenuType(), "menuType");
        validateNotNull(command.getSortOrder(), "sortOrder");
        validateNotNull(command.getIsActive(), "isActive");
        validateNotNull(command.getVersionNo(), "versionNo");
    }

    @Override
    protected void beforeCreate(CreateDpMenuCmd command) {
        if (menuRepository.existsByModule_IdAndMenuCodeAndIsDeletedFalse(
                command.getModuleId(),
                command.getMenuCode()
        )) {
            throw AppException.of(
                    ErrorCode.RECORD_DUPLICATE,
                    "DpMenu",
                    command.getMenuCode()
            );
        }
    }

    @Override
    protected void beforeUpdate(DpMenu entity, UpdateDpMenuCmd command) {
        if (!Objects.equals(entity.getVersionNo(), command.getVersionNo())) {
            throw AppException.of(
                    ErrorCode.OPTIMISTIC_LOCK,
                    "DpMenu",
                    entity.getId()
            );
        }
    }

    @Override
    protected Specification<DpMenu> buildKeywordSpec(String keyword) {
        String p = "%" + keyword.toLowerCase(Locale.ROOT) + "%";
        return (root, q, cb) -> cb.or(
                cb.like(cb.lower(root.get("menuCode")), p),
                cb.like(cb.lower(root.get("menuName")), p)
        );
    }

    @Override
    public List<DpMenuSummaryDto> findRootMenusByModule(Long moduleId) {
        return mapper.toSummaryDtoList(
                menuRepository.findAllByModule_IdAndParentMenuIsNullAndIsDeletedFalseOrderBySortOrderAsc(moduleId)
        );
    }

    @Override
    public List<DpMenuSummaryDto> findChildMenus(Long parentMenuId) {
        return mapper.toSummaryDtoList(
                menuRepository.findAllByParentMenu_IdAndIsDeletedFalseOrderBySortOrderAsc(parentMenuId)
        );
    }

    @Override
    public List<DpMenuSummaryDto> findActiveMenusByModule(Long moduleId) {
        return mapper.toSummaryDtoList(
                menuRepository.findAllByModule_IdAndIsActiveTrueAndIsDeletedFalseOrderBySortOrderAsc(moduleId)
        );
    }

    private DpMenu resolveParentMenu(Long id) {
        if (id == null) {
            return null;
        }
        return menuRepository.findActiveById(id)
                .orElseThrow(() -> AppException.of(
                        ErrorCode.ENTITY_NOT_FOUND,
                        "DpMenu",
                        id
                ));
    }

    private DpScreen resolveScreen(Long id) {
        if (id == null) {
            return null;
        }
        return screenRepository.findActiveById(id)
                .orElseThrow(() -> AppException.of(
                        ErrorCode.ENTITY_NOT_FOUND,
                        "DpScreen",
                        id
                ));
    }

    private void validateParentNotSelf(Long currentId, DpMenu parentMenu) {
        if (currentId != null && parentMenu != null && Objects.equals(currentId, parentMenu.getId())) {
            throw AppException.of(ErrorCode.BAD_REQUEST, "parentMenuId", currentId);
        }
    }

    private MenuType toMenuType(String raw) {
        try {
            return MenuType.valueOf(raw.trim().toUpperCase(Locale.ROOT));
        } catch (Exception e) {
            throw AppException.of(ErrorCode.BAD_REQUEST, "menuType", raw);
        }
    }
}