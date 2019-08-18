package google;

import com.github.felixgail.gplaymusic.api.GPlayMusic;
import com.github.felixgail.gplaymusic.util.TokenProvider;
import common.Track;
import org.apache.commons.io.IOUtils;
import svarzee.gps.gpsoauth.AuthToken;
import svarzee.gps.gpsoauth.Gpsoauth;
import utils.Tools;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static common.Constants.*;
import static java.util.stream.Collectors.toList;
import static utils.Converters.converterForDeleting;
import static utils.Converters.converterForUploading;

public class GoogleApiController {

    private static GoogleApiController apiController;

    private AuthToken token;

    private GoogleApiController() {
    }

    public static GoogleApiController getController() {
        if (apiController == null) {
            apiController = new GoogleApiController();
            apiController.provideToken();
        }

        return apiController;
    }

    private void provideToken() {
        try {
            token = TokenProvider.provideToken(USERNAME.get(), PASSWORD.get(), ANDROID_ID.get());
        } catch (IOException | Gpsoauth.TokenRequestFailed e) {
            e.printStackTrace();
            //throw some except to up
        }
    }

    public List<Track> loadTracksFromGoogle() {

        GPlayMusic api = new GPlayMusic.Builder()
                .setAuthToken(token)
                .build();

        List<Track> trackList = new ArrayList<>();
        try {
            trackList = api.getTrackApi().getLibraryTracks().stream()
                    .map(this::convertToTrack)
                    .collect(toList());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return trackList;
    }

    private Track convertToTrack(com.github.felixgail.gplaymusic.model.Track track) {
        return new GoogleTrack.Builder()
                .withDuration(track.getDurationMillis())
                .withTitle(track.getTitle())
                .withArtist(track.getArtist())
                .withAlbum(track.getAlbum())
                .withGenre(track.getGenre().orElse(""))
                .withComposer(track.getComposer())
                .withAlbumArtist(track.getAlbumArtist())
                .withYear(track.getYear().orElse(0))
                .withTrackNumber(track.getTrackNumber())
                .withComment(track.getComment().orElse(""))
                .withTotalTrackCount(track.getTotalTrackCount().orElse(0))
                .withID(track.getUuid().orElse(track.getID()))
                .build();
    }

    public void deleteTracks(List<Track> tracksToDelete) {

        if (tracksToDelete.isEmpty())
            return;

        String pathToFileWithTracks = Tools.saveTrackListToFile(converterForDeleting, tracksToDelete, "tracksToDelete.txt");
        List<String> command = new ArrayList<>();

        command.add("python"); // path to python interpreter
//        command.add("-u"); // unbuffered output
        command.add(Paths.get(System.getProperty("user.dir"),
                "\\src\\main",
                "\\python\\scripts\\google_interaction.py").toString()); // path to scripts

        command.add("delete");
        command.add(String.format("--username %s", USERNAME.get()));
        command.add(String.format("--password %s", PASSWORD.get()));
        command.add(String.format("--imei %s", ANDROID_ID2.get()));

        command.add(String.format("--tracks-to-delete %s", pathToFileWithTracks));

        ProcessBuilder processBuilder = new ProcessBuilder(command);

        try {
            Process process = processBuilder.start();
            String outputString = IOUtils.toString(process.getInputStream());
            process.waitFor();
            System.out.println(outputString);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

//        try {
//            boolean result = Files.deleteIfExists(Paths.get(pathToFileWithTracks));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return outputString;
    }

    public void uploadTracks(List<Track> tracksToUpload) {
        if (tracksToUpload.isEmpty())
            return;

        String pathToFileWithTracks = Tools.saveTrackListToFile(converterForUploading, tracksToUpload, "tracksToUpload.txt");
        List<String> command = new ArrayList<>();

        command.add("python"); // path to python interpreter
//        command.add("-u"); // unbuffered output
        command.add(Paths.get(System.getProperty("user.dir"),
                "\\src\\main",
                "\\python\\scripts\\google_interaction.py").toString()); // path to scripts

        command.add("upload");
        command.add(String.format("--username %s", USERNAME.get()));
        command.add(String.format("--password %s", PASSWORD.get()));
        command.add(String.format("--imei %s", ANDROID_ID2.get()));

        command.add(String.format("--tracks-to-upload %s", pathToFileWithTracks));

        ProcessBuilder processBuilder = new ProcessBuilder(command);

        try {
            Process process = processBuilder.start();
            String outputString = IOUtils.toString(process.getInputStream());
            process.waitFor();
            System.out.println(outputString);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
