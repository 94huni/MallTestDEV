package com.springboot.Teamproject.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
public class ShopBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bno; //게시판번호

    @Column(nullable = false)
    @NotBlank
    private String title;   //제목

    @Column(nullable = false)
    @NotBlank
    private String content; //내용

    private String imagePath;   //이미지 경로

    private String createDate;  //생성 날짜

    @OneToMany(mappedBy = "board")
    @ToString.Exclude
    private List<Comment> commentList;  //댓글 일대다 관계


    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private String userprofile;   //유저정보 다대일 관계

    public ShopBoard(int bno, String title, String content, String createDate) {
        this.bno = bno;
        this.title = title;
        this.content=content;
        this.createDate=createDate;
    }
}
