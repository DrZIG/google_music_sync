package local;

import common.Constants;
import common.Track;
import common.FileConverting;
import utils.Tools;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LocalLibraryController {

    private static LocalLibraryController apiController;


    private LocalLibraryController() {}

    public static LocalLibraryController getController() {
        if (apiController == null) {
            apiController = new LocalLibraryController();
        }

        return apiController;
    }

    public List<Path> getPathsListOfMusicFiles(String folder) {
        List<Path> fileList = new ArrayList<>();
        try (Stream<Path> paths = Files.walk(Paths.get(folder))) {
            fileList = paths
                    .filter(Files::isRegularFile)
                    .filter(Tools::isMusicFile)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileList;
    }

    public List<Track> getTracksFromListOfPaths(List<Path> listOfPaths) {
        if (Boolean.parseBoolean(Constants.DEBUG.get())) {
            List<Track> trackList = new ArrayList<>();
            for (Path path : listOfPaths) {
                File file = path.toFile();
                common.File commonFile = Tools.convertFileToLibraryFile(file);
                Track track = null;
                try {
                    track = commonFile.convertToTrack();
                } catch (Exception exc) {
                    exc.getMessage();
                }
                trackList.add(track);
            }
            return trackList;
        }

        return listOfPaths.stream()
                .map(Path::toFile)
                .map(Tools::convertFileToLibraryFile)
                .filter(Objects::nonNull)
                .map(FileConverting::convertToTrack)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
