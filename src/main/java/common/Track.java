package common;

import com.google.common.io.Files;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;

public class Track {
    private final long duration;
    private final String title;
    private final String artist;
    private final String album;
    private final String genre;
    private final String composer;
    private final String albumArtist;
    private final int year;
    private final int trackNumber;
    private final String comment;
    private final int totalTrackCount;
//    private final Extensions extension;

    protected Track(Builder<?> builder) {
        duration = builder.duration;
        title = builder.title;
        artist = builder.artist;
        album  = builder.album;
        genre = builder.genre;
        composer = builder.composer;
        albumArtist = builder.albumArtist;
        year = builder.year;
        trackNumber = builder.trackNumber;
        comment = builder.comment;
        totalTrackCount = builder.totalTrackCount;
//        extension = builder.extension;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Track track = (Track) o;
        return Objects.equals(artist, track.artist) &&
                Objects.equals(title, track.title) &&
                Objects.equals(album, track.album) &&
                Objects.equals(genre, track.genre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(artist, title, album, genre);
    }

    public static class Builder<T extends Builder<T>> {

        private long duration;
        private String title;
        private String artist;
        private String album;
        private String genre;
        private String composer;
        private String albumArtist;
        private int year;
        private int trackNumber;
        private String comment;
        private int totalTrackCount;
//        private Extensions extension;

        public Builder() {
        }

//        public T withExtension(@Nonnull Extensions extension) {
//            Objects.requireNonNull(extension);
//
//            this.extension = extension;
//            return (T) this;
//        }

        public T withDuration(@Nonnull long duration) {
            Objects.requireNonNull(duration);

            this.duration = duration;
            return (T) this;
        }

        public T withTitle(@Nonnull String title) {
            Objects.requireNonNull(title);

            this.title = title;
            return (T) this;
        }

        public T withArtist(@Nonnull String artist) {
            Objects.requireNonNull(artist);

            this.artist = artist;
            return (T) this;
        }

        public T withAlbum(@Nonnull String album) {
            Objects.requireNonNull(album);

            this.album = album;
            return (T) this;
        }

        public T withGenre(@Nonnull String genre) {
            Objects.requireNonNull(genre);

            this.genre = genre;
            return (T) this;
        }

        public T withComposer(String composer) {
            if (composer != null)
                this.composer = composer;
            return (T) this;
        }

        public T withAlbumArtist(@Nullable String albumArtist) {
            if (albumArtist != null)
                this.albumArtist = albumArtist;
            return (T) this;
        }

        public T withYear(@Nonnull int year) {
            Objects.requireNonNull(year);

            this.year = year;
            return (T) this;
        }

        public T withTrackNumber(@Nonnull int trackNumber) {
            Objects.requireNonNull(trackNumber);

            this.trackNumber = trackNumber;
            return (T) this;
        }

        public T withComment(@Nonnull String comment) {
            Objects.requireNonNull(comment);

            this.comment = comment;
            return (T) this;
        }

        public T withTotalTrackCount(@Nonnull int totalTrackCount) {
            Objects.requireNonNull(totalTrackCount);

            this.totalTrackCount = totalTrackCount;
            return (T) this;
        }

        public Track build() {
            return new Track(this);
        }
    }
}
