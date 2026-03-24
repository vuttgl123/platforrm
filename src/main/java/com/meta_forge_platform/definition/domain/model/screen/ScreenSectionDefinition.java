package com.meta_forge_platform.definition.domain.model.screen;

import com.meta_forge_platform.shared.domain.base.BaseSortableEntity;
import com.meta_forge_platform.shared.domain.exception.ValidationException;
import com.meta_forge_platform.shared.infrastructure.converter.JsonMapConverter;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(
        name = "dp_screen_section",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_dp_screen_section_code", columnNames = {"screen_id", "section_code"})
        }
)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class ScreenSectionDefinition extends BaseSortableEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "screen_id", nullable = false, foreignKey = @ForeignKey(name = "fk_dp_screen_section_screen"))
    private ScreenDefinition screen;

    @Column(name = "section_code", nullable = false, length = 100)
    private String code;

    @Column(name = "section_name", nullable = false, length = 255)
    private String name;

    @Column(name = "section_type", nullable = false, length = 50)
    private String sectionType;

    @Column(name = "columns_count", nullable = false)
    private Integer columnsCount = 1;

    @Convert(converter = JsonMapConverter.class)
    @Column(name = "config_json", columnDefinition = "json")
    private Map<String, Object> config = new LinkedHashMap<>();

    public ScreenSectionDefinition(ScreenDefinition screen, String code, String name, String sectionType) {
        changeScreen(screen);
        changeCode(code);
        changeName(name);
        changeSectionType(sectionType);
    }

    public void changeScreen(ScreenDefinition screen) {
        if (screen == null) {
            throw new ValidationException("Screen must not be null");
        }
        this.screen = screen;
    }

    public void changeCode(String code) {
        if (code == null || code.isBlank()) {
            throw new ValidationException("Section code must not be blank");
        }
        this.code = code.trim();
    }

    public void changeName(String name) {
        if (name == null || name.isBlank()) {
            throw new ValidationException("Section name must not be blank");
        }
        this.name = name.trim();
    }

    public void changeSectionType(String sectionType) {
        if (sectionType == null || sectionType.isBlank()) {
            throw new ValidationException("Section type must not be blank");
        }
        this.sectionType = sectionType.trim();
    }

    public void changeColumnsCount(Integer columnsCount) {
        if (columnsCount == null || columnsCount <= 0) {
            throw new ValidationException("Columns count must be > 0");
        }
        this.columnsCount = columnsCount;
    }

    public void changeConfig(Map<String, Object> config) {
        this.config = config == null ? new LinkedHashMap<>() : new LinkedHashMap<>(config);
    }
}
