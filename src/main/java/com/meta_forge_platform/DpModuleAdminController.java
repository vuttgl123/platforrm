//package com.meta_forge_platform;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@Controller
//@RequestMapping("/admin/modules")
//public class DpModuleAdminController {
//
//    @GetMapping
//    public String list(Model model) {
//        model.addAttribute("modules", List.of(
//                ModuleVm.builder().id(1L).code("core").name("Core").active("true").createdAt("2026").build(),
//                ModuleVm.builder().id(2L).code("crm").name("CRM").active("true").createdAt("2026").build()
//        ));
//
//        model.addAttribute("pageTitle", "Modules");
//        model.addAttribute("pageSubtitle", "Manage modules");
//        model.addAttribute("contentTemplate", "../module/list.ftl");
//        return "admin/layout/base";
//    }
//
//    @GetMapping("/create")
//    public String create(Model model) {
//        model.addAttribute("module", new ModuleVm());
//        model.addAttribute("pageTitle", "Create Module");
//        model.addAttribute("contentTemplate", "../module/create.ftl");
//        return "admin/layout/base";
//    }
//
//    @GetMapping("/{id}")
//    public String detail(@PathVariable Long id, Model model) {
//        model.addAttribute("module",
//                ModuleVm.builder().id(id).code("core").name("Core").active("true").build());
//
//        model.addAttribute("pageTitle", "Module Detail");
//        model.addAttribute("contentTemplate", "../module/detail.ftl");
//        return "admin/layout/base";
//    }
//
//    @GetMapping("/{id}/edit")
//    public String edit(@PathVariable Long id, Model model) {
//        model.addAttribute("module",
//                ModuleVm.builder().id(id).code("core").name("Core").active("true").build());
//
//        model.addAttribute("pageTitle", "Edit Module");
//        model.addAttribute("contentTemplate", "../module/edit.ftl");
//        return "admin/layout/base";
//    }
//}