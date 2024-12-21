package com.stream.app.spring_stream_backend.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "yt_course")
public class Course {
    @Id
    private String id;
    private String title;
//    @OneToMany(mappedBy = "course") // but we don't want these right now so commented for now
//    private List<Video> list = new ArrayList<>();
}
