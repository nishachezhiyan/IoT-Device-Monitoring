package iotmonitoring;

public class Threshold {
    private final double minimum;
    private final double maximum;

    public Threshold(double minimum, double maximum) {
        if (minimum >= maximum) {
            throw new IllegalArgumentException("Minimum must be less than maximum.");
        }
        this.minimum = minimum;
        this.maximum = maximum;
    }

    public double getMinimum() { return minimum; }
    public double getMaximum() { return maximum; }

    public boolean isBreached(double value) {
        return value < minimum || value > maximum;
    }

    public String getSeverity(double value) {
        double distance;
        if (value < minimum) {
            distance = minimum - value;
        } else if (value > maximum) {
            distance = value - maximum;
        } else {
            return "NONE";
        }

        double range = maximum - minimum;
        double percentage = (distance / range) * 100.0;

        if (percentage >= 20.0) return "HIGH";
        if (percentage >= 10.0) return "MEDIUM";
        return "LOW";
    }

    @Override
    public String toString() {
        return "Min=" + minimum + ", Max=" + maximum;
    }
}
