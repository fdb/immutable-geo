package nodebox.geo;

import com.google.common.collect.ImmutableList;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TransformTest {

    @Test
    public void translatePath() {
        final Path.Builder b = new Path.Builder();
        b.moveTo(10, 20);
        b.lineTo(30, 40);
        final Path p = b.build();
        final Transform t = Transform.translateInstance(100, 200);
        final Path tp = t.map(p);
        ImmutableList<PathElement> elementList = ImmutableList.copyOf(tp.getElements());
        PathElement p0 = elementList.get(0);
        assertEquals(new Point(110, 220), p0.getPoint());
        PathElement p1 = elementList.get(1);
        assertEquals(new Point(130, 240), p1.getPoint());
    }

}
