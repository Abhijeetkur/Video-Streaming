package com.stream.app.spring_stream_backend.services;

import com.stream.app.spring_stream_backend.entities.Video;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.View;
import java.util.List;

public interface VideoService {
    // all business logic of video will be written here
    // save video
    Video save(Video video, MultipartFile file);

    //getVideo
    Video get(String videoId);

    // getByTitle
    Video getByTitle(String title);

    // getAll
    List<Video> getAll();

    //video processing
    String processVideo(String videoId);
}
