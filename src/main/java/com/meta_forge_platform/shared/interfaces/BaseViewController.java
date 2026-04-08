//package com.meta_forge_platform.shared.interfaces;
//
//import com.meta_forge_platform.shared.application.BaseService;
//import com.meta_forge_platform.shared.domain.query.FilterQuery;
//import com.meta_forge_platform.shared.domain.query.PageQuery;
//import com.meta_forge_platform.shared.domain.query.SortQuery;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.Page;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//import java.util.List;
//
//@RequiredArgsConstructor
//public abstract class BaseViewController<T, C, U, ID> {
//
//    protected final BaseService<T, C, U, ID> service;
//
//    protected final String viewPrefix;
//
//    @GetMapping
//    public String list(
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "20") int size,
//            @RequestParam(required = false) List<String> sort,
//            @RequestParam(required = false) List<String> filter,
//            @RequestParam(required = false) String keyword,
//            Model model
//    ) {
//        PageQuery query = buildPageQuery(page, size, sort, filter, keyword);
//        Page<T> result = service.findAll(query);
//
//        model.addAttribute("items", result.getContent());
//        model.addAttribute("meta", PageMeta.from(result));
//        model.addAttribute("currentPage", page);
//        model.addAttribute("pageSize", size);
//        model.addAttribute("keyword", keyword);
//
//        onListModel(model, query, result);
//        return viewPrefix + "/list";
//    }
//
//    @GetMapping("/{id}")
//    public String detail(@PathVariable ID id, Model model) {
//        T item = service.getById(id);
//        model.addAttribute("item", item);
//        onDetailModel(model, id, item);
//        return viewPrefix + "/detail";
//    }
//
//    @GetMapping("/new")
//    public String createForm(Model model) {
//        onCreateFormModel(model);
//        return viewPrefix + "/form";
//    }
//
//    @GetMapping("/{id}/edit")
//    public String editForm(@PathVariable ID id, Model model) {
//        T item = service.getById(id);
//        model.addAttribute("item", item);
//        model.addAttribute("editMode", true);
//        onEditFormModel(model, id, item);
//        return viewPrefix + "/form";
//    }
//
//    @PostMapping
//    public String create(
//            @ModelAttribute C command,
//            RedirectAttributes redirectAttributes
//    ) {
//        try {
//            T created = service.create(command);
//            redirectAttributes.addFlashAttribute("successMessage", "Tạo mới thành công");
//            return "redirect:/" + viewPrefix + "/" + resolveId(created);
//        } catch (Exception ex) {
//            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
//            return "redirect:/" + viewPrefix + "/new";
//        }
//    }
//
//    @PostMapping("/{id}/edit")
//    public String update(
//            @PathVariable ID id,
//            @ModelAttribute U command,
//            RedirectAttributes redirectAttributes
//    ) {
//        try {
//            service.update(id, command);
//            redirectAttributes.addFlashAttribute("successMessage", "Cập nhật thành công");
//            return "redirect:/" + viewPrefix + "/" + id;
//        } catch (Exception ex) {
//            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
//            return "redirect:/" + viewPrefix + "/" + id + "/edit";
//        }
//    }
//
//    @PostMapping("/{id}/delete")
//    public String delete(
//            @PathVariable ID id,
//            RedirectAttributes redirectAttributes
//    ) {
//        try {
//            service.deleteById(id);
//            redirectAttributes.addFlashAttribute("successMessage", "Xóa thành công");
//        } catch (Exception ex) {
//            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
//        }
//        return "redirect:/" + viewPrefix;
//    }
//
//
//    protected void onListModel(Model model, PageQuery query, Page<T> result) {}
//
//    protected void onDetailModel(Model model, ID id, T item) {}
//
//    protected void onCreateFormModel(Model model) {}
//
//    protected void onEditFormModel(Model model, ID id, T item) {}
//
//
//    protected Object resolveId(T item) {
//        try {
//            return item.getClass().getMethod("getId").invoke(item);
//        } catch (Exception e) {
//            return "";
//        }
//    }
//
//    protected PageQuery buildPageQuery(int page, int size,
//                                       List<String> sort, List<String> filter,
//                                       String keyword) {
//        List<SortQuery> sorts = sort == null ? List.of()
//                : sort.stream().map(SortQuery::parse).toList();
//        List<FilterQuery> filters = filter == null ? List.of()
//                : filter.stream().map(FilterQuery::parse).toList();
//        return PageQuery.builder()
//                .page(page).size(size)
//                .sorts(sorts).filters(filters)
//                .keyword(keyword)
//                .build();
//    }
//}