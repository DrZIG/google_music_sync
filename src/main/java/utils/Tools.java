package utils;

import com.google.common.io.Files;
import common.Extensions;
import common.Track;
import ealvatag.audio.SupportedFileFormat;
import google.GoogleTrack;
import local.EalvaFile;
import common.File;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

import static common.Constants.validExtensions;

public class Tools {
    public boolean checkValidMusicFolder(List<Track> localTracks) {
        //need to create autoupdating baseline and compare data from folder with it. Need to check that there is no
        // more, for example, 10% of difference
        // if baseline is empty, no checking, only update
        if (false) {
            System.out.println("");
            return false;
        }
        return true;
    }

    public static boolean isMusicFile(Path path) {
        if (!validExtensions.contains(getExtension(path.toString()))) {
            System.out.println(String.format("Not supported file extension: %s", path.toString()));
            return false;
        }
        return true;
    }

    public static Extensions getExtension(String filename) {
        try {
            return Extensions.valueOf(Files.getFileExtension(filename).toUpperCase());
        } catch (IllegalArgumentException exc) {
            return null;
        }
    }

    public static String getNameWithoutExtension(String filename) {
        return Files.getNameWithoutExtension(filename);
    }

    public static int parseYear(String stringYear) {
        if (stringYear == null)
            return 0;
        if (stringYear.trim().length() != 4) {
            stringYear = Arrays.stream(stringYear.split("[^\\d]"))
                    .filter(element -> element.length() == 4)
                    .findFirst().orElse("0");
        }
        return Integer.parseInt(stringYear);
    }

    public static int parseInt(String stringNumber) {
        if (stringNumber == null || stringNumber.replaceAll("\\D+","") == "")
            return 0;
        return Integer.parseInt(stringNumber.replaceAll("\\D+",""));
    }

    public List<Track> getDifferenceBetweenLists(List<Track> mainList, List<Track> secondList) {
        Set<Track> mainSet = new HashSet<>(mainList);
        mainSet.removeAll(secondList);

        return new ArrayList<>(mainSet);
    }

    public static File convertFileToLibraryFile(java.io.File inputFile) {
        Extensions extension = Tools.getExtension(inputFile.getName());
        if (extension == null)
            return null;

        if (SupportedFileFormat.fromExtension(extension.toString()) != SupportedFileFormat.UNKNOWN)
            return new EalvaFile(inputFile);

        return null;
    }

    public List<Track> getListOfSameTracks(List<Track> mainList) {
        var unique = new HashSet<Track>(mainList);

        return mainList.stream()
                .filter(track -> unique.stream()
                        .filter(uniqueTrack -> uniqueTrack == track)
                        .count() == 0
                )
                .collect(Collectors.toList());
    }

    public void removeTracksFromList(List<Track> listOfTracks, List<Track> tracksToDelete) {

        tracksToDelete.stream().forEach(trackToDelete -> {
            Iterator iterator = listOfTracks.iterator();
            while (iterator.hasNext()) {
                Track track = (Track)iterator.next();
                if (track == trackToDelete)
                    iterator.remove();
            }
//            for (int i = 0; i < listOfTracks.size(); i++) {
//                 Track track = listOfTracks.get(i);
//                 if (track == trackToDelete)
//                     listOfTracks.remove(i);
//            }
        });
    }

    public static String saveTrackListToFile(TracksToStringConverter executingFunction, List<Track> trackList, String fileName) {
        java.io.File file = new java.io.File(System.getProperty("java.io.tmpdir") + fileName);
        try (PrintWriter out = new PrintWriter(file)) {
            out.println(executingFunction.convert(trackList));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return file.getAbsolutePath();
    }
}
