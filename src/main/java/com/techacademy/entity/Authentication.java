package com.techacademy.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.Data;

@Data
@Entity
@Table(name = "authentication")
public class Authentication {

        /** 権限 */
        public static enum Role {
            一般, 管理者
        }

        /** 社員番号 */
        @Id
        @Column(length = 20)
        private String code;

        /** パスワード */
        @Column (length = 255)
        private String password;

        /** 権限 */
        @Column(length = 10)
        @Enumerated(EnumType.STRING)
        private Role role;

        /** 従業員ID */
        @OneToOne
        @JoinColumn(name="employee_id", referencedColumnName="id")
        private Employee employee;


}

