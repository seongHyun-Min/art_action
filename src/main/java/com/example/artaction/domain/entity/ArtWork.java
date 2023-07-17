package com.example.artaction.domain.entity;


import com.example.artaction.contant.CategoryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

@Slf4j
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "art_works")
public class ArtWork {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "art_work_id")
    private long id;

    @Column(name = "art_work_name")
    private String name;

    @Column(name = "art_work_description")
    private String description;

    @Column(name = "art_work_image")
    private String image;

    @Column(name = "art_category")
    @Enumerated(EnumType.STRING)
    private CategoryType category;

    //유저와 대다일 관계 설정
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;



}
