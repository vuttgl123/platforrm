package com.meta_forge_platform.platform.application.service.impl;

import com.meta_forge_platform.platform.application.dto.menu.*;
import com.meta_forge_platform.platform.application.mapper.DpMenuMapper;
import com.meta_forge_platform.platform.application.service.DpMenuService;
import com.meta_forge_platform.platform.domain.entity.DpMenu;
import com.meta_forge_platform.platform.infrastructure.repository.DpMenuRepository;
import com.meta_forge_platform.platform.infrastructure.repository.DpModuleRepository;
import com.meta_forge_platform.platform.infrastructure.repository.DpScreenRepository;
import com.meta_forge_platform.shared.application.BaseServiceImpl;
import com.meta_forge_platform.shared.domain.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

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

    @Override protected DpMenuDto toDto(DpMenu e) { return mapper.toDto(e); }
    @Override protected DpMenu toEntity(CreateDpMenuCmd c) { return mapper.toEntity(c); }
    @Override protected void updateEntity(DpMenu e, UpdateDpMenuCmd c) { mapper.updateEntity(e, c); }

    @Override
    protected void afterToEntity(DpMenu entity, CreateDpMenuCmd cmd) {
        entity.setModule(moduleRepository.findActiveById(cmd.getModuleId())
                .orElseThrow(() -> AppException.notFound("Module", cmd.getModuleId())));
        if (cmd.getParentMenuId() != null) {
            entity.setParentMenu(menuRepository.findActiveById(cmd.getParentMenuId())
                    .orElseThrow(() -> AppException.notFound("ParentMenu", cmd.getParentMenuId())));
        }
        if (cmd.getScreenId() != null) {
            entity.setScreen(screenRepository.findActiveById(cmd.getScreenId())
                    .orElseThrow(() -> AppException.notFound("Screen", cmd.getScreenId())));
        }
    }

    @Override
    protected void beforeCreate(CreateDpMenuCmd cmd) {
        if (menuRepository.existsByModule_IdAndMenuCodeAndIsDeletedFalse(
                cmd.getModuleId(), cmd.getMenuCode()))
            throw AppException.conflict("Menu code đã tồn tại: " + cmd.getMenuCode());
    }

    @Override
    protected Specification<DpMenu> buildKeywordSpec(String kw) {
        String p = "%" + kw.toLowerCase() + "%";
        return (root, q, cb) -> cb.or(
                cb.like(cb.lower(root.get("menuCode")), p),
                cb.like(cb.lower(root.get("menuName")), p)
        );
    }

    @Override
    public List<DpMenuSummaryDto> findRootMenusByModule(Long moduleId) {
        return mapper.toSummaryDtoList(
                menuRepository.findAllByModule_IdAndParentMenuIsNullAndIsDeletedFalseOrderBySortOrderAsc(moduleId));
    }

    @Override
    public List<DpMenuSummaryDto> findChildMenus(Long parentMenuId) {
        return mapper.toSummaryDtoList(
                menuRepository.findAllByParentMenu_IdAndIsDeletedFalseOrderBySortOrderAsc(parentMenuId));
    }

    @Override
    public List<DpMenuSummaryDto> findActiveMenusByModule(Long moduleId) {
        return mapper.toSummaryDtoList(
                menuRepository.findAllByModule_IdAndIsActiveTrueAndIsDeletedFalseOrderBySortOrderAsc(moduleId));
    }
}