package com.community.communityback.model;

import java.time.LocalDateTime;
import java.util.List;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Converter;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.hibernate.annotations.CollectionId;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Formula;
import org.hibernate.boot.model.source.spi.ColumnBindingDefaults;
import org.hibernate.mapping.IdGenerator;
import org.springframework.boot.context.properties.bind.DefaultValue;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder.Default;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Reply {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    private Long groupId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"board, parent"})
    @JoinColumn(name="parentId")
    private Reply parent;


    @ColumnDefault("0")
    private int stepNum;

    @ColumnDefault("0")
    private int rankNum;
    
    @ColumnDefault("0")
    private int answerNum;
    

    @Column(nullable = false, length = 200)
    private String content;

    @ManyToOne
    @JsonIgnoreProperties({"password", "provider", "role", "createDate", "id"})
    @JoinColumn(name="userId")
    private User user;
   
    @ManyToOne
    @JsonIgnoreProperties({"replys","user"})
    @JoinColumn(name="boardId")
    private Board board;

    @CreationTimestamp
    private LocalDateTime createDate;


    @Override
    public String toString(){
         return "Reply = [id" + id + ", content=" + content + ", board=" + board +", user=" + user +", timestamp= "+ createDate +"]";
    }

}
