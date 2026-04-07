package com.meta_forge_platform;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/workflows")
public class AdminWorkflowController {

    @GetMapping
    public String list(Model model) {
        model.addAttribute("pageTitle", "Workflows");
        model.addAttribute("contentTemplate", "../workflow/list.ftl");
        return "admin/layout/base";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("pageTitle", "Create Workflow");
        model.addAttribute("contentTemplate", "../workflow/list.ftl");
        return "admin/layout/base";
    }
}