package google;

import common.Track;

import javax.annotation.Nonnull;
import java.util.Objects;

public class GoogleTrack extends Track {

    private final String uuid;

    protected GoogleTrack(Builder builder) {
        super(builder);
        uuid = builder.uuid;
    }

    public String getID() {
        return uuid;
    }

    public static class Builder extends Track.Builder<Builder> {

        private String uuid;

        public Builder() {}

        public Builder withID(@Nonnull String ID) {
            Objects.requireNonNull(ID);

            this.uuid = ID;
            return this;
        }

        public GoogleTrack build() {
            return new GoogleTrack(this);
        }
    }
}
