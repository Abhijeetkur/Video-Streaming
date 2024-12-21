import React, { useEffect, useRef } from "react";
import videojs from "video.js";
import Hls from "hls.js";
import "video.js/dist/video-js.css";
import toast from "react-hot-toast";

function VideoPlayer({ src }) {
  const videoRef = useRef(null);
  const playerRef = useRef(null);

  useEffect(() => {
    // Initialize video player
    playerRef.current = videojs(videoRef.current, {
      controls: true,
      autoplay: true,
      muted: true,
      preload: "auto",
      download: true,
    });

    if (Hls.isSupported()) {
      const hls = new Hls();
      hls.loadSource(src);
      hls.attachMedia(videoRef.current);
      hls.on(Hls.Events.MANIFEST_PARSED, () => {
        videoRef.current.play();
      });
    } else if (videoRef.current.canPlayType("application/vnd.apple.mpegurl")) {
      videoRef.current.src = src;
      videoRef.current.addEventListener("canplay", () => {
        videoRef.current.play();
      });
    } else {
      console.log("video format not supported");
      toast.error("Video format not supported");
    }

    // Full screen listener
    const handleFullScreen = () => {
      if (document.fullscreenElement) {
        // Set to full screen size
        videoRef.current.style.width = "100vw";
        videoRef.current.style.height = "100vh";
      } else {
        // Reset size when exiting full screen
        videoRef.current.style.width = "100%";
        videoRef.current.style.height = "500px";
      }
    };

    // Attach full screen event listeners
    document.addEventListener("fullscreenchange", handleFullScreen);
    document.addEventListener("webkitfullscreenchange", handleFullScreen);
    document.addEventListener("mozfullscreenchange", handleFullScreen);
    document.addEventListener("MSFullscreenChange", handleFullScreen);

    // Cleanup event listeners when component is unmounted
    return () => {
      document.removeEventListener("fullscreenchange", handleFullScreen);
      document.removeEventListener("webkitfullscreenchange", handleFullScreen);
      document.removeEventListener("mozfullscreenchange", handleFullScreen);
      document.removeEventListener("MSFullscreenChange", handleFullScreen);
    };
  }, [src]);

  return (
    <div>
      <div data-vjs-player>
        <video
          ref={videoRef}
          className="video-js vjs-control-bar"
          style={{
            width: "100%",
            height: "500px",
          }}
        ></video>
      </div>
    </div>
  );
}

export default VideoPlayer;
