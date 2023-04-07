package com.techacademy.controller;

import java.time.LocalDateTime;
import java.util.List;

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
import com.techacademy.service.EmployeeService;

@Controller
@RequestMapping("employee")
public class EmployeeController {
    private final EmployeeService service;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    /** 一覧画面を表示 */
    @GetMapping("/list")
    public String getList(Model model) {
        // 全件検索結果をModelに登録
        List<Employee> emplist = service.getEmployeeList();
        model.addAttribute("employeelist", emplist);
        model.addAttribute("employeecount", emplist.size());
        // employee/list.htmlに画面遷移
        return "employee/list";

    }

    /** Employee登録画面を表示 */
    @GetMapping("/register")
    public String getRegister(@ModelAttribute Employee employee) {
        // Employee登録画面に遷移
        return "employee/register";
    }

    /** Employee登録処理 */
    @PostMapping("/register")
    public String postRegister(Employee employee) {
        LocalDateTime dateTime = LocalDateTime.now();
        employee.setCreated_at(dateTime);
        employee.setUpdated_at(dateTime);
        employee.setDelete_flag(0);
        // Employee登録
        service.saveEmployee(employee);
        // 一覧画面にリダイレクト
        return "redirect:/employee/list";
    }

    @GetMapping("/detail/{id}/")
    public String getDetail(@PathVariable("id") Integer id, Model model) {
        // Modelに登録
        model.addAttribute("employee", service.getEmployee(id));
        // Employee詳細画面に遷移
        return "employee/detail";
    }

    /** Employee更新画面 */
    @GetMapping("/update/{id}/")
    public String getEmployee(@PathVariable("id") Integer id, Model model) {
        // Modelに登録
        model.addAttribute("employee", service.getEmployee(id));
        // User更新画面に遷移
        return "employee/update";

    }

    /** Employee更新処理 */
    @PostMapping("/update/")
    public String postEmployee(Employee employee, @RequestParam("newpass") String newpass) {
        LocalDateTime dateTime = LocalDateTime.now();
        employee.setUpdated_at(dateTime);
        employee.setDelete_flag(0);

        Authentication authentication = employee.getAuthentication();
        authentication.setEmployee(employee);
        if (!newpass.equals("")) {
            authentication.setPassword(newpass);
        } else {
            // パスワードが入力されていない場合
            Employee emp = service.getEmployee(employee.getId());
            authentication.setPassword(emp.getAuthentication().getPassword());

        }
            Employee emp = service.getEmployee(employee.getId());
            employee.setCreated_at(emp.getCreated_at());

        // Employee登録
        service.saveEmployee(employee);
        // 一覧画面にリダイレクト
        return "redirect:/employee/list";
    }

    /** Employee削除処理 */
    @GetMapping("/delete/{id}/")
    public String deleteEmployee(@PathVariable("id") Integer id, Model model) {
        Employee emp = service.getEmployee(id);
        emp.setUpdated_at(LocalDateTime.now());
        emp.setDelete_flag(1);
        service.saveEmployee(emp);

        // 一覧画面にリダイレクト
        return "redirect:/employee/list";
    }

}
