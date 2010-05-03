package nodebox.geo;

import com.google.common.collect.ImmutableList;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TransformTest {

    @Test
    public void translateGeometry() {
        final Geometry.Builder b = new Geometry.Builder();
        b.add(rect(10, 20, 30, 40));
        b.add(rect(50, 60, 70, 80));
        final Geometry geo = b.build();
        final Transform t = Transform.translateInstance(100, 200);
        final Geometry tGeo = t.map(geo);
        assertEquals(2, tGeo.getPathCount());
        final Path p0 = tGeo.getPaths().get(0);
        final Path p1 = tGeo.getPaths().get(1);
        final PathElement p0e0 = p0.getElements().get(0);
        assertEquals(new Point(110, 220), p0e0.getPoint());
        final PathElement p1e0 = p1.getElements().get(0);
        assertEquals(new Point(150, 260), p1e0.getPoint());
    }

    @Test
    public void translatePath() {
        final Path.Builder b = new Path.Builder();
        b.moveTo(10, 20);
        b.lineTo(30, 40);
        final Path p = b.build();
        final Transform t = Transform.translateInstance(100, 200);
        final Path tp = t.map(p);
        ImmutableList<PathElement> elementList = ImmutableList.copyOf(tp.getElements());
        final PathElement p0 = elementList.get(0);
        assertEquals(new Point(110, 220), p0.getPoint());
        final PathElement p1 = elementList.get(1);
        assertEquals(new Point(130, 240), p1.getPoint());
    }


    @Test
    public void pmapPath() {
        final Path.Builder b = new Path.Builder();
        for (int i = 0; i < 1000; i++) {
            b.moveTo(i, -i);
        }
        final Path p = b.build();
        final Transform t = Transform.translateInstance(100, 200);
        final Path tp = t.pmap(p);
        ImmutableList<PathElement> elementList = ImmutableList.copyOf(tp.getElements());
        final PathElement p0 = elementList.get(0);
        assertEquals(new Point(100, 200), p0.getPoint());
        final PathElement p10 = elementList.get(10);
        assertEquals(new Point(110, 190), p10.getPoint());
    }

    private Path rect(float x, float y, float width, float height) {
        final Path.Builder b = new Path.Builder();
        b.moveTo(x, y);
        b.lineTo(x + width, y);
        b.lineTo(x + width, y + height);
        b.lineTo(x, y + height);
        b.close();
        return b.build();
    }

    /**
     * This is not a test, but a demo of the speed difference between regular time and parallel time.
     * Parallel transform is slower because of the task creation overhead. However, for complex transformations
     * (unrelated to AffineTransform) this will probably be faster.
     */
    public static void speedDiff() {

        final Geometry.Builder b = Geometry.builder();
        for (int i = 0; i < 100; i++) {
            final Path.Builder p = Path.builder();
            for (int j = 0; j < 2000; j++) {
                p.moveTo(i, -i);
            }
            b.add(p.build());
        }
        final Geometry geo = b.build();
        final Transform t = Transform.translateInstance(100, 200);

        // "Warm-up" transform.
        t.map(geo);

        long regularTime = System.nanoTime();
        t.map(geo);
        regularTime = System.nanoTime() - regularTime;

        long parallelTime = System.nanoTime();
        t.pmap(geo);
        parallelTime = System.nanoTime() - parallelTime;

        System.out.println("regularTime  = " + regularTime);
        System.out.println("parallelTime = " + parallelTime);
    }

    public static void main(String[] args) {
        speedDiff();
    }

}
