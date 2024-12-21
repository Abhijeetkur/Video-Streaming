
Video Streaming Web Application 

I developed a video streaming website that incorporates a robust backend built with Spring and Spring Boot, and  using ReactJS and Tailwind CSS. The website leverages Video.js and HLS.js for seamless video streaming, ensuring a smooth user experience. To support various video resolutions and enhance streaming performance, I utilized FFmpeg to process video data into multiple formats. The integration of these technologies ensures efficient video delivery. 

I am still working on it to make its frontend good and user friendly and dynamically change resolutions, increase security of videos like netflix and many more.

## For any queries and suggestion please write in issue


## API Reference
###  All backend api present at \spring-stream-backend\src\main\java\com\stream\app\spring_stream_backend\controllers\VideoController.java
#### Get video
##### Resolution 1080p - stream_0, 720p - stream_1, 360p - stream_2
```http
  GET http://localhost:8080/api/v1/videos/{videoid}/{stream}/playlist.m3u8
```

#### Get vidoe at auto
##### master.m3u8 will call other Resolution automatically
```http
  GET http://localhost:8080/api/v1/videos/{videoid}/master.m3u8
```


## Screenshots

![App Screenshot](https://github.com/Abhijeetkur/Video-Streaming/blob/main/Screenshot%202024-12-21%20152339.png)

On left side of the ui you can play the video and on right you can upload the videos but you can play videos by entering videoid only for now.

