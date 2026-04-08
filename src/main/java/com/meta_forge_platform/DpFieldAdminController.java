//package com.meta_forge_platform;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@Controller
//@RequestMapping("/admin/fields")
//public class DpFieldAdminController {
//
//    @GetMapping
//    public String list(Model model) {
//        model.addAttribute("fields", List.of(
//                FieldVm.builder().id(1L).code("name").label("Name").entityCode("customer").fieldType("TEXT").required("true").build(),
//                FieldVm.builder().id(2L).code("email").label("Email").entityCode("customer").fieldType("EMAIL").required("true").build()
//        ));
//
//        model.addAttribute("pageTitle", "Fields");
//        model.addAttribute("contentTemplate", "../field/list.ftl");
//        return "admin/layout/base";
//    }
//
//    @GetMapping("/create")
//    public String create(Model model) {
//        model.addAttribute("field", new FieldVm());
//        model.addAttribute("pageTitle", "Create Field");
//        model.addAttribute("contentTemplate", "../field/create.ftl");
//        return "admin/layout/base";
//    }
//
//    @GetMapping("/{id}")
//    public String detail(@PathVariable Long id, Model model) {
//        model.addAttribute("field",
//                FieldVm.builder().id(id).code("name").label("Name").entityCode("customer").build());
//
//        model.addAttribute("pageTitle", "Field Detail");
//        model.addAttribute("contentTemplate", "../field/detail.ftl");
//        return "admin/layout/base";
//    }
//
//    @GetMapping("/{id}/edit")
//    public String edit(@PathVariable Long id, Model model) {
//        model.addAttribute("field",
//                FieldVm.builder().id(id).code("name").label("Name").entityCode("customer").build());
//
//        model.addAttribute("pageTitle", "Edit Field");
//        model.addAttribute("contentTemplate", "../field/edit.ftl");
//        return "admin/layout/base";
//    }
//}