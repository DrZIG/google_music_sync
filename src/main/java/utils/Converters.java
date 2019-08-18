package utils;

import google.GoogleTrack;
import local.LocalTrack;

public class Converters {

    public static TracksToStringConverter converterForDeleting = tracks -> {
        StringBuilder builder = new StringBuilder();
        tracks.stream()
                .filter(GoogleTrack.class::isInstance)
                .map(track -> (GoogleTrack) track)
                .map(GoogleTrack::getID)
                .forEach(track -> {
                    builder.append(track);
                    builder.append("\n");
                });
        return builder.toString();
    };

    public static TracksToStringConverter converterForUploading = tracks -> {
        StringBuilder builder = new StringBuilder();
        tracks.stream()
                .filter(LocalTrack.class::isInstance)
                .map(track -> (LocalTrack) track)
                .map(LocalTrack::getPath)
                .forEach(path -> {
                    builder.append(path);
                    builder.append("\n");
                });
        return builder.toString();
    };
}
