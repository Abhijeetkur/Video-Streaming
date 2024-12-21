import { useState } from "react";
import reactLogo from "./assets/react.svg";
import viteLogo from "/vite.svg";
import "./App.css";
import VideoUpload from "./components/VideoUpload";
import VideoPlayer from "./components/VideoPlayer";

function App() {
  const [videoId, setVideoId] = useState("");
  const [videoTitle, setVideoTitle] = useState("");
  const [useTitle, setUseTitle] = useState(false); // Toggle between ID and Title
  const [resolution, setResolution] = useState("1080p"); // Track selected resolution

  // Function to handle input change for video ID
  const handleVideoIdChange = (e) => {
    setVideoId(e.target.value);
    setUseTitle(false); // Switch to ID-based streaming
  };

  // Function to handle input change for video Title
  const handleVideoTitleChange = (e) => {
    setVideoTitle(e.target.value);
    setUseTitle(true); // Switch to Title-based streaming
  };

  // Function to handle resolution change
  const handleResolutionChange = (e) => {
    setResolution(e.target.value);
  };
  

  // Determine video source based on toggle and resolution
  const videoSrc = resolution === "auto"
  ? `http://localhost:8080/api/v1/videos/${useTitle ? videoTitle : videoId}/master.m3u8`
  : `http://localhost:8080/api/v1/videos/${useTitle ? videoTitle : videoId}/${resolution}/playlist.m3u8`;


  return (
    <>
      <div className="flex flex-col items-center space-y-5 justify-center py-9">
        <h1 className="text-3xl font-bold text-gray-700 dark:text-gray-100">
          Video Streaming Application
        </h1>

        <div className="flex mt-14 w-full space-x-10 justify-around">
          <div>
            <h1 className="text-white text-center mt-2">Playing Video</h1>
            <div>
              <VideoPlayer src={videoSrc}></VideoPlayer>
            </div>

            {/* Resolution Dropdown */}
            <select
              id="resolution-selector"
              value={resolution}
              onChange={handleResolutionChange}
              className="mt-4 px-2 py-1 border rounded"
            >
              <option value="auto">auto</option>
              <option value="stream_2">360p</option>
              <option value="stream_1">720p</option>
              <option value="stream_0">1080p</option>
            </select>

            {/* Input field to take Video ID */}
            <div className="mt-5">
              <label
                htmlFor="videoId"
                className="text-lg font-medium text-gray-600 dark:text-gray-300"
              >
                Enter Video ID:
              </label>
              <input
                id="videoId"
                type="text"
                value={videoId}
                onChange={handleVideoIdChange}
                className="ml-3 px-3 py-2 border rounded-md text-gray-700 dark:text-gray-300 dark:bg-gray-800"
                placeholder="Enter video ID"
              />
            </div>

            {/* Input field to take Video Title */}
            {/* <div className="mt-5">
              <label
                htmlFor="videoTitle"
                className="text-lg font-medium text-gray-600 dark:text-gray-300"
              >
                Enter Video Title:
              </label>
              <input
                id="videoTitle"
                type="text"
                value={videoTitle}
                onChange={handleVideoTitleChange}
                className="ml-3 px-3 py-2 border rounded-md text-gray-700 dark:text-gray-300 dark:bg-gray-800"
                placeholder="Enter video title"
              />
            </div> */}
          </div>

          <VideoUpload />
        </div>
      </div>
    </>
  );
}

export default App;
