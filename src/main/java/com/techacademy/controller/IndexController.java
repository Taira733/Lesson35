package com.techacademy.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.techacademy.entity.Report;
import com.techacademy.service.EmployeeDetails;
import com.techacademy.service.ReportService;

@Controller
public class IndexController {

    private final ReportService service;

    public IndexController(ReportService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String getIndex(@AuthenticationPrincipal EmployeeDetails user, Model model, Integer id) {
        List<Report> reportlist = service.findByEmployee(user);
        model.addAttribute("reportlist", reportlist);
        model.addAttribute("reportcount", reportlist.size());
        // index.htmlに画面遷移
        return "report/index";
    }
}
