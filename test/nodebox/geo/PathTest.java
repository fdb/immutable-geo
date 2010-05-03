package nodebox.geo;

import com.google.common.collect.ImmutableList;
import org.junit.Test;

import static org.junit.Assert.*;

public class PathTest {

    @Test
    public void empty() {
        final Path p = Path.of();
        assertEquals(0, p.getElementCount());
        ImmutableList<PathElement> elementList = ImmutableList.copyOf(p.getElements());
        assertEquals(0, elementList.size());
    }

    @Test
    public void build() {
        final Path.Builder b = Path.builder();
        b.moveTo(0, 0);
        b.lineTo(100, 100);
        b.close();
        final Path p = b.build();
        assertEquals(3, p.getElementCount());
        ImmutableList<PathElement> elementList = ImmutableList.copyOf(p.getElements());
        assertEquals(3, elementList.size());
        final PathElement e0 = elementList.get(0);
        assertEquals(PathElement.Type.MOVE_TO, e0.getType());
        assertEquals(Point.ZERO, e0.getPoint());
        final PathElement e1 = elementList.get(1);
        assertEquals(PathElement.Type.LINE_TO, e1.getType());
        assertEquals(new Point(100, 100), e1.getPoint());
        final PathElement e2 = elementList.get(2);
        assertEquals(PathElement.Type.CLOSE, e2.getType());
        assertEquals(Point.ZERO, e2.getPoint());
    }

}
