package com.example.restapispringsecurity.models;

import com.example.restapispringsecurity.dto.RoleName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private RoleName roleName;

    @ManyToMany(mappedBy = "roleSet", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<User> userSet;
}
