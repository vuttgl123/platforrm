//package com.meta_forge_platform;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//@Controller
//@RequestMapping("/admin/permissions")
//public class AdminPermissionController {
//
//    @GetMapping
//    public String list(Model model) {
//        model.addAttribute("pageTitle", "Permissions");
//        model.addAttribute("contentTemplate", "../permission/list.ftl");
//        return "admin/layout/base";
//    }
//}