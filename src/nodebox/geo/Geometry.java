package nodebox.geo;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;

public final class Geometry {

    public static Geometry of() {
        return EMPTY_GEOMETRY;
    }

    public static Geometry of(Path... paths) {
        return new Geometry(ImmutableList.copyOf(paths));
    }

    public static Geometry of(Iterable<Path> paths) {
        return new Geometry(ImmutableList.copyOf(paths));
    }

    public static Builder builder() {
        return new Builder();
    }

    private final static Geometry EMPTY_GEOMETRY = new Geometry(ImmutableList.<Path>of());

    private final ImmutableList<Path> paths;

    private Geometry(ImmutableList<Path> paths) {
        this.paths = paths;
    }

    public Iterable<Path> getPaths() {
        return paths;
    }

    public Iterable<PathElement> getElements() {
        return ImmutableList.of();
    }

    public Iterable<Point> getPoints() {
        return ImmutableList.of();
    }

    public int getPointCount() {
        return 0;
    }

    public static final class Builder {
        ImmutableList.Builder<Path> paths = ImmutableList.builder();

        public Builder add(Path path) {
            paths.add(path);
            return this;
        }

        public Geometry build() {
            return new Geometry(paths.build());
        }
    }


}
