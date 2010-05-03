package nodebox.geo;

import com.google.common.collect.ImmutableList;
import org.junit.Test;

import static org.junit.Assert.*;

public class GeometryTest {

    @Test
    public void empty() {
        final Geometry geo = Geometry.of();
        assertEquals(0, geo.getPointCount());
        ImmutableList<Point> pointList = ImmutableList.copyOf(geo.getPoints());
        assertEquals(0, pointList.size());
    }

}
