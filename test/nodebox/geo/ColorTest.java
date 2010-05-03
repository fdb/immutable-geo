package nodebox.geo;

import org.junit.Test;

import static org.junit.Assert.*;

public class ColorTest {

    private final float DELTA = 0.00001f;

    @Test
    public void constantColors() {
        final Color white = Color.WHITE;
        assertEquals(1, white.getRed(), DELTA);
        assertEquals(1, white.getGreen(), DELTA);
        assertEquals(1, white.getBlue(), DELTA);
        assertEquals(1, white.getAlpha(), DELTA);
        final Color black = Color.BLACK;
        assertEquals(0, black.getRed(), DELTA);
        assertEquals(0, black.getGreen(), DELTA);
        assertEquals(0, black.getBlue(), DELTA);
        assertEquals(1, black.getAlpha(), DELTA);
    }

    @Test
    public void clamp() throws Exception {
        final Color highs = Color.gray(500, 0.2f);
        assertEquals(1, highs.getRed(), DELTA);
        assertEquals(0.2f, highs.getAlpha(), DELTA);
        final Color lows = Color.gray(-4000, 0.1f);
        assertEquals(0, lows.getRed(), DELTA);
        assertEquals(0.1f, lows.getAlpha(), DELTA);
    }
}
