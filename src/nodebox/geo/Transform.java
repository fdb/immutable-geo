package nodebox.geo;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

import static com.google.common.base.Preconditions.checkNotNull;

public final class Transform {

    public static Transform translateInstance(float tx, float ty) {
        Transform t = new Transform();
        t.transform.translate(tx, ty);
        return t;
    }

    public static Transform rotateInstance(float r) {
        Transform t = new Transform();
        t.transform.rotate(r);
        return t;
    }

    public static Transform scaleInstance(float s) {
        Transform t = new Transform();
        t.transform.scale(s, s);
        return t;
    }

    public static Transform scaleInstance(float sx, float sy) {
        Transform t = new Transform();
        t.transform.scale(sx, sy);
        return t;
    }

    public static final Transform IDENTITY = new Transform();
    private final AffineTransform transform = new AffineTransform();

    private Transform() {
    }

    public final Geometry map(Geometry geo) {
        Geometry.Builder b = Geometry.builder();
        for (Path p : geo.getPaths()) {
            b.add(map(p));
        }
        return b.build();
    }

    public final Path map(Path path) {
        Path.Builder b = Path.builder();
        for (PathElement el : path.getElements()) {
            b.element(map(el));
        }
        return b.build();
    }

    public final PathElement map(PathElement el) {
        switch (el.getType()) {
            case MOVE_TO:
                return PathElement.moveTo(map(el.getPoint()));
            case LINE_TO:
                return PathElement.lineTo(map(el.getPoint()));
            case CURVE_TO:
                return PathElement.curveTo(
                        map(el.getControl1()),
                        map(el.getControl2()),
                        map(el.getPoint()));
            case CLOSE:
                return el;
            default:
                throw new AssertionError("Unknown element type.");
        }
    }

    public final Point map(Point point) {
        Point2D.Float src = new Point2D.Float(point.getX(), point.getY());
        Point2D.Float dst = new Point2D.Float();
        transform.transform(src, dst);
        return new Point(dst.x, dst.y);
    }

}
