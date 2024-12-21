## Video Streaming Web Application 

I developed a video streaming website that incorporates a robust backend built with Spring and Spring Boot, and  using ReactJS using Nodejs version(v20.18.1) for npm and Tailwind CSS. The website leverages Video.js and HLS.js for seamless video streaming, ensuring a smooth user experience. To support various video resolutions and enhance streaming performance, I utilized FFmpeg to process video data into multiple formats. The integration of these technologies ensures efficient video delivery. 

I am still working on it to make its frontend good and user friendly and dynamically change resolutions, increase security of videos like netflix and many more.

## For any queries or suggestions, kindly refer to the Issues section to inform me.

## Run Locally

Clone the project

```bash
  git clone https://github.com/Abhijeetkur/Video-Streaming.git
```
### Backend setup

Go to the spring-stream-backend directory

```bash
set up application.properies for mysql

# MySQL Database Configuration
spring.datasource.url=jdbc:mysql:xyz
spring.datasource.username=your_user_name
spring.datasource.password=your_password

To Start the server
run SpringStreamBackendApplication.java
```
### Frontend setup
Install dependencies
```bash
    Go to the stream-front-end directory

    cd stream-front-end
```

```bash
  npm install
```

Start the server

```bash
  npm run dev
```

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

#### HTTP Live Streaming(HLS) Packaging using FFmpeg – Easy Step-by-Step Tutorial
  https://ottverse.com/hls-packaging-using-ffmpeg-live-vod/


## Screenshots

![App Screenshot](https://github.com/Abhijeetkur/Video-Streaming/blob/main/Screenshot%202024-12-21%20152339.png)

On the left side of the UI, you can play videos, and on the right side, you can upload videos. Currently, videos can only be played by entering the video ID.

