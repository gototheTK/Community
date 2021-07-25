package com.community.communityback.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.data.jpa.repository.Query;

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
public class Board {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false, length = 50)
    private String title;
    
    @Lob
    private String content;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"password","role", "createDate", "provider"})
    @JoinColumn(name="userId")
    private User user;

    @OneToMany(mappedBy="board", fetch=FetchType.LAZY, cascade=CascadeType.REMOVE)
    @JsonIgnoreProperties({"id","board","user","content","createDate"})
    @OrderBy("Id desc")
    private List<Reply> replys;

    @OneToMany(mappedBy="board", fetch=FetchType.LAZY, cascade=CascadeType.REMOVE)
    @JsonIgnoreProperties({"board"})
    @OrderBy("Id desc")
    private List<BoardFile> boardFiles;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"founder","manager"})
    @JoinColumn(name="interestId")
    private Interest interest;

    private Long count;

    private int recommend;

    @CreationTimestamp
    private Timestamp createTime;

}
