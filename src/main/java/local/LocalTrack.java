package local;

import common.Extensions;
import common.Track;

import javax.annotation.Nonnull;
import java.util.Objects;

public class LocalTrack extends Track {

    private final int bitRate;
    private final String encodingType; //i think it's type
    private final long size;
    private final Extensions extension;
    private final String localPath;

    protected LocalTrack(Builder builder) {
        super(builder);
        bitRate = builder.bitRate;
        encodingType = builder.encodingType;
        size = builder.size;
        extension = builder.extension;
        localPath = builder.localPath;
    }

    public String getPath() {
        return localPath;
    }

    public static class Builder extends Track.Builder<Builder> {

        private int bitRate;
        private String encodingType;
        private long size;
        private Extensions extension;
        private String localPath;

        public Builder() {
        }

        public Builder withExtension(Extensions extension) {
//            Objects.requireNonNull(extension);

            this.extension = extension;
            return this;
        }

        public Builder withBitRate(@Nonnull int bitRate) {
            Objects.requireNonNull(bitRate);

            this.bitRate = bitRate;
            return this;
        }

        public Builder withEncodingType(@Nonnull String encodingType) {
            Objects.requireNonNull(encodingType);

            this.encodingType = encodingType;
            return this;
        }

        public Builder withSize(@Nonnull long size) {
            Objects.requireNonNull(size);

            this.size = size;
            return this;
        }

        public Builder withPath(@Nonnull String path) {
            Objects.requireNonNull(path);

            this.localPath = path;
            return this;
        }

        public LocalTrack build() {
            return new LocalTrack(this);
        }
    }
}
