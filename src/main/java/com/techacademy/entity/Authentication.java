package com.techacademy.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "authentication")
public class Authentication {

    /** 社員番号 */
    @Id
    @Column(length = 20)
    private String code;

    /** パスワード */
    @Column(length = 255)
    private String password;

    /** 権限 */
    @Column(length = 10)
    private String role;

    /** 従業員テーブルのID */
    @OneToOne
    @JoinColumn(name="employee_id", referencedColumnName="id")
    private Employee employee;







}
