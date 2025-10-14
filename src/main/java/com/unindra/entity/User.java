package com.unindra.entity;

import java.time.LocalDate;

import com.unindra.model.util.Gender;
import com.unindra.model.util.Role;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@Inheritance(strategy =InheritanceType.JOINED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;

    private Gender gender;

    @ManyToOne @JoinColumn(name = "birthplace_id")
    private Regency birthplace;
    
    private LocalDate birthDate;
    
    @ManyToOne @JoinColumn(name = "district_address_id")
    private District districtAddress;

    private String address;

    private String username;

    private String email;

    private String phoneNumber;

    private String password;

    private Role role;

}