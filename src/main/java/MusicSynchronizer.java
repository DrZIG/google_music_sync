import common.Client;
import utils.Loader;
import utils.Tools;

import static common.Constants.MUSIC_FOLDER;

public class MusicSynchronizer {

    private static void startSynchronize() {
        Loader.initialise();

        final var googleController = Client.getClient().getGoogleController();

        var completableLocalTracks = Loader.getCompletableFutureFunction(() -> {
            var localController = Client.getClient().getLocalController();
            var allLocalPaths = localController.getPathsListOfMusicFiles(MUSIC_FOLDER.get());
            return localController.getTracksFromListOfPaths(allLocalPaths);
        });
//        var tmp = localController.getTracksFromListOfPaths(allLocalPaths);
        var completableGoogleTracks = Loader.getCompletableFutureFunction(googleController::loadTracksFromGoogle);

        var allLocalTracks = Loader.getTrackList(completableLocalTracks);
        var allGoogleTracks = Loader.getTrackList(completableGoogleTracks);

        //TODO Implement this
        var tools = new Tools();
//        assert tools.checkValidMusicFolder(allLocalTracks);

        var tracksToDelete = tools.getListOfSameTracks(allGoogleTracks);
        tools.removeTracksFromList(allGoogleTracks, tracksToDelete);
        tracksToDelete.addAll(tools.getDifferenceBetweenLists(allGoogleTracks, allLocalTracks));
        googleController.deleteTracks(tracksToDelete);

        var tracksToUpload = tools.getDifferenceBetweenLists(allLocalTracks, allGoogleTracks);
        googleController.uploadTracks(tracksToUpload);
    }


    public static void main(String[] args) {
        startSynchronize();

        //0) implement autosynch by start from main
        /**
         * -a) check that directory is valid to prevent accidental delete
         * a) read data from folder to arraylist of <H1>Tracks</H1>
         * b) read from api track by track.
         *  - If there is no such track or metadata not equals them add apiTrack to delete list, else add to normal track
         * c) loop by (a) list. If track in normal track list - continue, else upload
         */

        //1) try to implement command line usage
        /**2) input choise:
         - synchronize music
         - create/update playlist
         a) by genres
         b) by parent folders
         */
        //999) implement javaFX or something modern
    }
}
