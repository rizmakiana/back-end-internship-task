package com.unindra.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@Table(name = "regencies")
public class Regency {

    @Id @Column(length = 4)
    private String id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "province_id", nullable = false, foreignKey = @ForeignKey(name = "fk_province"))
    private Province province;

    @OneToMany(mappedBy = "regency", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<District> districts;
    
    @OneToMany(mappedBy = "birthplace")
    private List<User> users;
    
}
