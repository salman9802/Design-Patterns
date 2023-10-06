import java.util.ArrayList;
import java.util.HashMap;

// The library provides us with the video downloading class. However, it’s very inefficient. If the client application requests the same video multiple times, the library just downloads it over and over, instead of caching and reusing the first downloaded file.
// The proxy class implements the same interface as the original downloader and delegates it all the work. However, it keeps track of the downloaded files and returns the cached result when the app requests the same video multiple times.

// The interface of a remote service.
interface ThirdPartyYoutubeLib {
    ArrayList<String> listVideos();
    String getVideoInfo(int id);
    String downloadVideo(int id);
}

// The concrete implementation of a service connector. Methods
// of this class can request information from YouTube. The speed
// of the request depends on a user's internet connection as
// well as YouTube's. The application will slow down if a lot of
// requests are fired at the same time, even if they all request
// the same information.
class ThirdPartyYoutubeClass implements ThirdPartyYoutubeLib {

    @Override
    public ArrayList<String> listVideos() {
        // Send an API request to YouTube.
        System.out.println("Send an API request to YouTube for list of videos.");
        ArrayList<String> videoList = new ArrayList<>();
        videoList.add("Video(1)");
        videoList.add("Video(2)");
        videoList.add("Video(3)");
        return videoList;
    }

    @Override
    public String getVideoInfo(int id) {
        // Get metadata about some video.
        System.out.println("Getting metadata about video with id='" + id + "'");
        return "VideoInfo(" + id + ")";
    }

    @Override
    public String downloadVideo(int id) {
        // Download a video file from YouTube.
        System.out.println("Downloading a video file with id='" + id + "' from YouTube.");
        return "DownloadedVideo(" + id + ")";
    }
}

// To save some bandwidth, we can cache request results and keep
// them for some time. But it may be impossible to put such code
// directly into the service class. For example, it could have
// been provided as part of a third party library and/or defined
// as `final`. That's why we put the caching code into a new
// proxy class which implements the same interface as the
// service class. It delegates to the service object only when
// the real requests have to be sent.
class CachedYoutubeClass implements ThirdPartyYoutubeLib {
    private ThirdPartyYoutubeLib service;
    private ArrayList<String> listCache;
    private HashMap<Integer, String> videoDownloadCache = new HashMap<>();
    private String videoInfoCache;
    public boolean needReset = false;

    CachedYoutubeClass(ThirdPartyYoutubeLib service) { this.service = service; }

    @Override
    public ArrayList<String> listVideos() {
        if(this.listCache == null || this.needReset) this.listCache = service.listVideos();
        return this.listCache;
    }

    @Override
    public String getVideoInfo(int id) {
        if(this.videoInfoCache == null || this.needReset) this.videoInfoCache = service.getVideoInfo(id);
        return this.videoInfoCache;
    }

    @Override
    public String downloadVideo(int id) {
        // The proxy may use the parameters of requests as the cache keys.
        if(!this.videoDownloadCache.containsKey(id) || this.needReset) {
            this.videoDownloadCache.put(id, this.service.downloadVideo(id));
        }
        return this.videoDownloadCache.get(id);
    }
}

// The GUI class, which used to work directly with a service
// object, stays unchanged as long as it works with the service
// object through an interface. We can safely pass a proxy
// object instead of a real service object since they both
// implement the same interface.
class YoutubeManager {
    protected ThirdPartyYoutubeLib service;

    YoutubeManager(ThirdPartyYoutubeLib service) { this.service = service; }

    public void renderVideoPage(int id) {
        String info = this.service.getVideoInfo(id);
        // Render the video page.
        System.out.println("Rendering: " + info);
    }

    public void renderListPanel() {
        ArrayList<String> videos = this.service.listVideos();
        // Render the list of video thumbnails.
        System.out.println("Video List: " + videos);
    }

    public void reactOnUserInput() {
        this.renderListPanel();
    }
}


public class Proxy {
    public static void main(String[] args) {
        int id = 2;

        // The application can configure proxies on the fly.
        ThirdPartyYoutubeClass youtubeService = new ThirdPartyYoutubeClass();
        CachedYoutubeClass youtubeProxy = new CachedYoutubeClass(youtubeService);

        System.out.println("Downloading Video with id: " + id);

        // Without Caching Mechanism - Loss of bandwidth
        System.out.println("\nWithout Caching");
        System.out.println(youtubeService.downloadVideo(id));
        System.out.println(youtubeService.downloadVideo(id));
        System.out.println(youtubeService.downloadVideo(id));

        System.out.println();
        
        // With Caching mechanism - Effecient
        System.out.println("\nWith Caching");
        System.out.println(youtubeProxy.downloadVideo(id));
        System.out.println(youtubeProxy.downloadVideo(id));
        System.out.println(youtubeProxy.downloadVideo(id));

        YoutubeManager manager = new YoutubeManager(youtubeProxy);
        manager.reactOnUserInput();

        System.out.println("Rendering Video with id " + id);
        manager.renderVideoPage(id);
    }
}
