package com.meta_forge_platform.runtime.application.service;

import com.meta_forge_platform.runtime.application.dto.record.*;
import com.meta_forge_platform.shared.application.BaseService;
import java.util.List;

public interface AppRecordService
        extends BaseService<AppRecordDto, CreateAppRecordCmd, UpdateAppRecordCmd, Long> {

    /** Lấy danh sách record con của một record cha */
    List<AppRecordSummaryDto> findChildren(Long parentRecordId);

    /** Lấy toàn bộ cây record theo root */
    List<AppRecordSummaryDto> findByRootRecord(Long rootRecordId);

    /** Chuyển trạng thái workflow */
    AppRecordDto transition(Long recordId, TransitionAppRecordCmd cmd);

    /** Lấy các transition khả dụng từ trạng thái hiện tại */
    List<AvailableTransitionDto> getAvailableTransitions(Long recordId);
}