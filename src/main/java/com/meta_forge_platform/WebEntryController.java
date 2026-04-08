package com.meta_forge_platform;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebEntryController {

    @GetMapping("/")
    public String dashboard() {
        return "admin/pages/dashboard";
    }

    @GetMapping("/entities")
    public String entityExplorer() {
        return "admin/pages/entity-explorer";
    }

    @GetMapping("/records")
    public String recordDetail() {
        return "admin/pages/record-detail";
    }

    @GetMapping("/builder")
    public String builder() {
        return "admin/pages/metadata-builder";
    }
}