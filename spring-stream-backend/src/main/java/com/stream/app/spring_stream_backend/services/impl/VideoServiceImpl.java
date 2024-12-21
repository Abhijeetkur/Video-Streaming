package com.stream.app.spring_stream_backend.services.impl;

import com.stream.app.spring_stream_backend.entities.Video;
import com.stream.app.spring_stream_backend.repositories.VideoRepository;
import com.stream.app.spring_stream_backend.services.VideoService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service  // this class is a fixed component so we use Service annotation
public class VideoServiceImpl implements VideoService {
    @Value("${files.video}")
    String DIR;

    @Value("${files.video.hls}")
    String HLS_DIR;

    @Autowired
    private VideoRepository videoRepository;


    @PostConstruct
    public void init() {
        File file = new File(DIR);


        try {
            Files.createDirectories(Paths.get(HLS_DIR));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (!file.exists()) {
            file.mkdir();
            System.out.println("Folder Created:");
        } else {
            System.out.println("Folder already created");
        }
    }

    @Override
    public Video save(Video video, MultipartFile file) {
        try {
            // original file name
            String filename = file.getOriginalFilename();
            // Content type of the file
            String contentType = file.getContentType();
            InputStream inputStream = file.getInputStream();
            // folder path to save the file : create
            String cleanFileName = StringUtils.cleanPath(filename);
            String cleanFolder = StringUtils.cleanPath(DIR);
            // folder path with filename
            Path path = Paths.get(cleanFolder, cleanFileName);
            System.out.println(contentType);
            System.out.println(path);

            // copy file to the folder
            Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
            // video metadata
            video.setContentType(contentType);
            video.setFilePath(path.toString());
            // metadata save
            Video savedVideo = videoRepository.save(video);
            //processing video
            processVideo(savedVideo.getVideoId());
            //delete actual video file and database entry  if exception

            // metadata save
            return savedVideo;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Video get(String videoId) {
        Video video = videoRepository.findById(videoId).orElseThrow(() -> new RuntimeException("video not found"));
        return video;
    }

    @Override
    public Video getByTitle(String title) {
        Video video = videoRepository.findByTitle(title).orElseThrow(() -> new RuntimeException("video not found"));
        return video;
    }

    @Override
    public List<Video> getAll() {
        return videoRepository.findAll();
    }

    @Override
    public String processVideo(String videoId) {
        Video video = this.get(videoId);
        String filePath = video.getFilePath();

        // Path where to store data:
        Path videoPath = Paths.get(filePath);

        try {
            // FFmpeg command
            Path outputPath = Paths.get(HLS_DIR, videoId);
            System.out.println("Video Path: " + videoPath);
            System.out.println("Output Path: " + outputPath);

            Files.createDirectories(outputPath);

            // Build the FFmpeg command for HLS processing
//            String ffmpegCommand = String.format(
//                    "ffmpeg -i %s " +
//                            "-filter_complex \"[0:v]split=3[v1][v2][v3]; " +
//                            "[v1]scale=w=1920:h=1080[v1out]; " +
//                            "[v2]scale=w=1280:h=720[v2out]; " +
//                            "[v3]scale=w=854:h=480[v3out]\" " +
//                            "-map \"[v1out]\" -c:v:0 libx264 -b:v:0 5000k -maxrate:v:0 5350k -bufsize:v:0 7500k " +
//                            "-map \"[v2out]\" -c:v:1 libx264 -b:v:1 2800k -maxrate:v:1 2996k -bufsize:v:1 4200k " +
//                            "-map \"[v3out]\" -c:v:2 libx264 -b:v:2 1400k -maxrate:v:2 1498k -bufsize:v:2 2100k " +
//                            "-map a:0 -c:a aac -b:a:0 192k -ac 2 " +
//                            "-map a:0 -c:a aac -b:a:1 128k -ac 2 " +
//                            "-map a:0 -c:a aac -b:a:2 96k -ac 2 " +
//                            "-f hls " +
//                            "-hls_time 10 " +
//                            "-hls_playlist_type vod " +
//                            "-hls_flags independent_segments " +
//                            "-hls_segment_type mpegts " +
//                            "-hls_segment_filename %s/stream_%%v/data%%03d.ts " +
//                            "-master_pl_name %s/master.m3u8 " +
//                            "-var_stream_map \"v:0,a:0 v:1,a:1 v:2,a:2\" " +
//                            "%s/stream_%%v/playlist.m3u8",
//                    videoPath, outputPath, outputPath, outputPath
//            );


            String ffmpegCommand = String.format(
                    "ffmpeg -i %s " +
                            "-filter_complex \"[0:v]split=3[v1][v2][v3]; " +
                            "[v1]scale=w=1920:h=1080[v1out]; " +
                            "[v2]scale=w=1280:h=720[v2out]; " +
                            "[v3]scale=w=854:h=480[v3out]\" " +
                            "-map \"[v1out]\" -c:v:0 libx264 -b:v:0 5000k -maxrate:v:0 5350k -bufsize:v:0 7500k " +
                            "-map \"[v2out]\" -c:v:1 libx264 -b:v:1 2800k -maxrate:v:1 2996k -bufsize:v:1 4200k " +
                            "-map \"[v3out]\" -c:v:2 libx264 -b:v:2 1400k -maxrate:v:2 1498k -bufsize:v:2 2100k " +
                            "-map a:0 -c:a aac -b:a:0 192k -ac 2 " +
                            "-map a:0 -c:a aac -b:a:1 128k -ac 2 " +
                            "-map a:0 -c:a aac -b:a:2 96k -ac 2 " +
                            "-f hls " +
                            "-hls_time 10 " +
                            "-hls_playlist_type vod " +
                            "-hls_flags independent_segments " +
                            "-hls_segment_type mpegts " +
                            "-hls_segment_filename %s/stream_%%v/data%%03d.ts " +
                            "-master_pl_name master.m3u8 " +
                            "-var_stream_map \"v:0,a:0 v:1,a:1 v:2,a:2\" " +
                            "%s/stream_%%v/playlist.m3u8",
                    videoPath, outputPath, outputPath, outputPath
            );



            System.out.println(ffmpegCommand);
            // Use ProcessBuilder to execute the command
            ProcessBuilder processBuilder = new ProcessBuilder("cmd", "/c", ffmpegCommand);
            // Redirect error stream to output for combined logging
            processBuilder.redirectErrorStream(true);

            // Start the process
            Process process = processBuilder.start();

            // Capture and print the output
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            // Wait for the process to finish and check the exit code
            int exitCode = process.waitFor();
            System.out.println("Process exited with code: " + exitCode);
            return videoId;
        } catch (IOException | InterruptedException ex) {
            throw new RuntimeException("Video processing failed!", ex);
        }
    }
}
