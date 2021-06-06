package com.dhxxn.untilwhen.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity(name = "dday")
public class Dday {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String content;
    @Column(nullable = false)
    private Date startDate;
    @Column(nullable = false)
    private Date finishDate;

    @ManyToOne(cascade = CascadeType.ALL)
    private User user;

    private LocalDateTime createTime;

    @PrePersist
    public void createTime() {
        this.createTime = LocalDateTime.now();
    }
}
