package nodebox.geo;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;
import com.google.common.collect.UnmodifiableIterator;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A two-dimensional shape.
 * <p/>
 * A path can have only one color. Use geometry to store a collection of colored paths.
 */
public final class Path {

    public static Builder builder() { return new Builder(); }

    public static Path of() {
        return EMPTY_PATH;
    }

    public static Path of(PathElement... elements) {
        return new Path(ImmutableList.copyOf(elements));
    }

    public static Path of(Iterable<PathElement> elements) {
        return new Path(ImmutableList.copyOf(elements));
    }

    private static final Path EMPTY_PATH = new Path(ImmutableList.<PathElement>of());

    private final ImmutableList<PathElement> elements;

    private Path(ImmutableList<PathElement> elements) {
        this.elements = elements;
    }

    public ImmutableList<PathElement> getElements() {
        return elements;
    }

    public int getElementCount() {
        return elements.size();
    }

    public Iterable<Point> getPoints() {
        return Iterables.transform(elements, new Function<PathElement, Point>() {
            public Point apply(PathElement from) {
                checkNotNull(from);
                return from.getPoint();
            }
        });
    }

    public static final class Builder {
        ImmutableList.Builder<PathElement> elements = ImmutableList.builder();

        public Builder add(PathElement el) {
            elements.add(el);
            return this;
        }

        public Builder addAll(Iterable<PathElement> els) {
            elements.addAll(els);
            return this;
        }

        public Builder moveTo(Point point) {
            elements.add(PathElement.moveTo(point));
            return this;
        }

        public Builder moveTo(float x, float y) {
            elements.add(PathElement.moveTo(x, y));
            return this;
        }

        public Builder lineTo(Point point) {
            elements.add(PathElement.lineTo(point));
            return this;
        }

        public Builder lineTo(float x, float y) {
            elements.add(PathElement.lineTo(x, y));
            return this;
        }

        public Builder close() {
            elements.add(PathElement.close());
            return this;
        }

        public Path build() {
            return new Path(elements.build());
        }
    }
}
