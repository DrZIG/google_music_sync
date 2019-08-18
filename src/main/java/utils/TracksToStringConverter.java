package utils;

import common.Track;

import java.util.List;

@FunctionalInterface
public interface TracksToStringConverter {
    String convert(List<Track> tracks);
}
