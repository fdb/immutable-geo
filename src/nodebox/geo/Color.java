package nodebox.geo;

public final class Color {

    public static final Color BLACK = new Color(0, 0, 0, 1);
    public static final Color WHITE = new Color(1, 1, 1, 1);

    public static Color gray(float v) {
        return new Color(v, v, v, 1f);
    }

    public static Color gray(float v, float a) {
        return new Color(v, v, v, a);
    }

    public static Color rgb(float r, float g, float b) {
        return new Color(r, g, b, 1f);
    }

    public static Color rgb(float r, float g, float b, float a) {
        return new Color(r, g, b, a);
    }

    private final float r, g, b, a;

    private Color(float r, float g, float b, float a) {
        this.r = clamp(r);
        this.g = clamp(g);
        this.b = clamp(b);
        this.a = clamp(a);
    }

    public float getR() {
        return r;
    }

    public float getRed() {
        return r;
    }

    public float getG() {
        return g;
    }

    public float getGreen() {
        return g;
    }

    public float getB() {
        return b;
    }

    public float getBlue() {
        return b;
    }

    public float getA() {
        return a;
    }

    public float getAlpha() {
        return a;
    }

    /**
     * Limit a value to the 0.0-1.0 range.
     * @param v the value
     * @return a value within range.
     */
    private float clamp(float v) {
        return v > 1f ? 1 : v < 0f ? 0 : v;
    }

}
