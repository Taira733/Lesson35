package com.techacademy.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.techacademy.entity.Authentication;
import com.techacademy.entity.Employee;
import com.techacademy.entity.Report;
import com.techacademy.service.EmployeeDetails;
import com.techacademy.service.ReportService;

@Controller
@RequestMapping("report")
public class ReportController {
    private final ReportService service;

    public ReportController(ReportService service) {
        this.service = service;
    }

    /** 一覧画面を表示 */
    @GetMapping("/list")
    public String getList(Model model) {
        // 全件検索結果をModelに登録
        List<Report> replist = service.getReportList();
        model.addAttribute("reportlist", replist);
        model.addAttribute("reportcount", replist.size());
        // report/list.htmlに画面遷移
        return "report/list";

    }

    /** Report登録画面を表示 */
    @GetMapping("/register")
    public String getRegister(@ModelAttribute Report report) {
        // Report登録画面に遷移
        return "report/register";
    }

    /** Report登録処理 */
    @PostMapping("/register")
    public String postRegister(Report report, @AuthenticationPrincipal EmployeeDetails user) {
        LocalDateTime dateTime = LocalDateTime.now();
        report.setCreated_at(dateTime);
        report.setUpdated_at(dateTime);
        report.setEmployee(user.getEmployee());
        // Report登録
        service.saveReport(report);
        // 一覧画面にリダイレクト
        return "redirect:/report/list";
    }

    /** Report詳細画面を表示 */
    @GetMapping("/detail/{id}/")
    public String getDetail(@PathVariable("id") Integer id, Model model) {

        // Modelに登録
        model.addAttribute("report", service.getReport(id));
        // Employee詳細画面に遷移
        return "report/detail";
    }

    /** Report更新画面 */
    @GetMapping("/update/{id}/")
    public String getReport(@PathVariable("id") Integer id, Model model) {
        // Modelに登録
        model.addAttribute("report", service.getReport(id));
        // User更新画面に遷移
        return "report/update";

    }

    /** Employee更新処理 */
    @PostMapping("/update/")
    public String postReport(Report rep, @AuthenticationPrincipal EmployeeDetails user) {
        LocalDateTime dateTime = LocalDateTime.now();
        rep.setUpdated_at(dateTime);
        rep.setEmployee(user.getEmployee());
        // 作成日時の取得
        Report report = service.getReport(user.getEmployee().getId());
        rep.setCreated_at(report.getCreated_at());

        // Report登録
        service.saveReport(rep);
        // 一覧画面にリダイレクト
        return "redirect:/report/list";
    }

}
