package local;

import com.google.common.collect.ImmutableSet;
import common.File;
import common.Track;
import ealvatag.audio.AudioFile;
import ealvatag.audio.AudioFileIO;
import ealvatag.audio.exceptions.CannotReadException;
import ealvatag.audio.exceptions.InvalidAudioFrameException;
import ealvatag.tag.FieldKey;
import ealvatag.tag.NullTag;
import ealvatag.tag.Tag;
import ealvatag.tag.TagException;
import utils.Tools;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class EalvaFile extends File {
    private AudioFile audioFile;

    public EalvaFile(java.io.File file) {
        super(file);
        audioFile = getAudioFile();
    }

    @Override
    public Track convertToTrack() {
        if (audioFile == null)
            return null;

        var audioHeader = audioFile.getAudioHeader();
        var tag = audioFile.getTag().or(NullTag.INSTANCE);
        var supportedFields = tag.getSupportedFields();

        return new LocalTrack.Builder()
                .withExtension(Tools.getExtension(file.getPath()))
                .withDuration(audioHeader.getDuration(TimeUnit.MILLISECONDS, false))
                .withTitle(tag.getValue(FieldKey.TITLE).or(getTitleFromFilename()))
                .withArtist(getTagValue(supportedFields, tag, new FieldKey[]{FieldKey.ARTIST}))
                .withAlbum(getTagValue(supportedFields, tag, new FieldKey[]{FieldKey.ALBUM}))
                .withGenre(getTagValue(supportedFields, tag, new FieldKey[]{FieldKey.GENRE}))
                .withComposer(getTagValue(supportedFields, tag, new FieldKey[]{FieldKey.COMPOSER}))
                .withAlbumArtist(getTagValue(supportedFields, tag, new FieldKey[]{FieldKey.ALBUM_ARTIST}))
                .withYear(Tools.parseYear(tag.getValue(FieldKey.YEAR).orNull()))
                .withTrackNumber(Tools.parseInt(getTagValue(supportedFields, tag, new FieldKey[]{FieldKey.TRACK})))
                .withComment(getTagValue(supportedFields, tag, new FieldKey[]{FieldKey.COMMENT}))
                .withTotalTrackCount(Tools.parseInt(getTagValue(supportedFields, tag, new FieldKey[]{FieldKey.TRACK_TOTAL})))
                .withBitRate(audioHeader.getBitRate())
                .withEncodingType(audioHeader.getEncodingType())
                .withSize(file.length())
                .withPath(file.getAbsolutePath())
                .build();
    }

    private AudioFile getAudioFile() {
        try {
            return AudioFileIO.read(file);
        } catch (CannotReadException | IOException | TagException | InvalidAudioFrameException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getTagValue(ImmutableSet<FieldKey> supportedFields, Tag tag, FieldKey[] keys) {
        for (FieldKey key : keys) {
            if (!supportedFields.contains(key))
                continue;
            return tag.getValue(key).or("");
        }
        return "";
    }
}
