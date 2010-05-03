package nodebox.geo;

/**
 * Represents one command in a path.
 * <p/>
 * <p><b>Note</b>: Although this class is not final, it cannot be subclassed as
 * it has no public or protected constructors. Thus, instances of this type are
 * guaranteed to be immutable.
 */
public abstract class PathElement {

    public enum Type {
        MOVE_TO,
        LINE_TO,
        CURVE_TO,
        CLOSE
    }

    public static PathElement moveTo(Point point) {
        return new StraightPathElement(Type.MOVE_TO, point);
    }

    public static PathElement moveTo(float x, float y) {
        return new StraightPathElement(Type.MOVE_TO, new Point(x, y));
    }

    public static PathElement lineTo(Point point) {
        return new StraightPathElement(Type.LINE_TO, point);
    }

    public static PathElement lineTo(float x, float y) {
        return new StraightPathElement(Type.LINE_TO, new Point(x, y));
    }

    public static PathElement curveTo(Point control1, Point control2, Point point) {
        return new CurvedPathElement(control1, control2, point);
    }

    public static PathElement lineTo(float x1, float y1, float x2, float y2, float x3, float y3) {
        return new CurvedPathElement(new Point(x1, y1), new Point(x2, y2), new Point(x3, y3));
    }

    public static PathElement close() {
        return ClosePathElement.INSTANCE;
    }

    public abstract Type getType();

    public abstract Point getPoint();

    public abstract Point getControl1();

    public abstract Point getControl2();


    public static class StraightPathElement extends PathElement {

        final Type type;
        final Point point;

        public StraightPathElement(Type type, Point point) {
            this.type = type;
            this.point = point;
        }

        @Override
        public Type getType() {
            return type;
        }

        @Override
        public Point getPoint() {
            return point;
        }

        @Override
        public Point getControl1() {
            return Point.ZERO;
        }

        @Override
        public Point getControl2() {
            return Point.ZERO;
        }
    }

    public static class CurvedPathElement extends PathElement {

        final Point control1;
        final Point control2;
        final Point point;

        public CurvedPathElement(Point control1, Point control2, Point point) {
            this.control1 = control1;
            this.control2 = control2;
            this.point = point;
        }

        @Override
        public Type getType() {
            return Type.CURVE_TO;
        }

        @Override
        public Point getPoint() {
            return point;
        }

        @Override
        public Point getControl1() {
            return control1;
        }

        @Override
        public Point getControl2() {
            return control2;
        }
    }

    public static class ClosePathElement extends PathElement {

        public static ClosePathElement INSTANCE = new ClosePathElement();

        private ClosePathElement() {
        }

        @Override
        public Type getType() {
            return Type.CLOSE;
        }

        @Override
        public Point getPoint() {
            return Point.ZERO;
        }

        @Override
        public Point getControl1() {
            return Point.ZERO;
        }

        @Override
        public Point getControl2() {
            return Point.ZERO;
        }
    }

}
