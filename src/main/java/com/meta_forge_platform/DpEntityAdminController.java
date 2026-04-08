//package com.meta_forge_platform;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@Controller
//@RequestMapping("/admin/entities")
//public class DpEntityAdminController {
//
//    @GetMapping
//    public String list(Model model) {
//        model.addAttribute("entities", List.of(
//                EntityVm.builder().id(10L).code("customer").name("Customer").moduleCode("crm").status("PUBLISHED").build(),
//                EntityVm.builder().id(11L).code("lead").name("Lead").moduleCode("crm").status("DRAFT").build()
//        ));
//
//        model.addAttribute("pageTitle", "Entities");
//        model.addAttribute("contentTemplate", "../entity/list.ftl");
//        return "admin/layout/base";
//    }
//
//    @GetMapping("/create")
//    public String create(Model model) {
//        model.addAttribute("entity", new EntityVm());
//        model.addAttribute("pageTitle", "Create Entity");
//        model.addAttribute("contentTemplate", "../entity/create.ftl");
//        return "admin/layout/base";
//    }
//
//    @GetMapping("/{id}")
//    public String detail(@PathVariable Long id, Model model) {
//        model.addAttribute("entity",
//                EntityVm.builder().id(id).code("customer").name("Customer").moduleCode("crm").status("PUBLISHED").build());
//
//        model.addAttribute("pageTitle", "Entity Detail");
//        model.addAttribute("contentTemplate", "../entity/detail.ftl");
//        return "admin/layout/base";
//    }
//
//    @GetMapping("/{id}/edit")
//    public String edit(@PathVariable Long id, Model model) {
//        model.addAttribute("entity",
//                EntityVm.builder().id(id).code("customer").name("Customer").moduleCode("crm").status("PUBLISHED").build());
//
//        model.addAttribute("pageTitle", "Edit Entity");
//        model.addAttribute("contentTemplate", "../entity/edit.ftl");
//        return "admin/layout/base";
//    }
//}