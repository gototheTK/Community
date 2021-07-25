package com.community.communityback.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, updatable = false, nullable = false, length = 50)
    private String username;

    @Column(nullable = false, length = 200)
    private String password;

    @Column(name="provider")
    @Enumerated(EnumType.STRING)
    @ColumnDefault("'DEFAULT'")
    private ProviderType provider;

    @Column(name="role")
    @Enumerated(EnumType.STRING)
    @ColumnDefault("'ROLE_USER'")
    private RoleType role;

    @CreationTimestamp
    private Timestamp createDate;

    @Builder
    public User(Long id, String username, String password, ProviderType provider, RoleType role){
        this.id = id;
        this.username = username;
        this.password = password;
        this.provider = provider;
        this.role = role;
    }


}
