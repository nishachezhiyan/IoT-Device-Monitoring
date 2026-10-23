package iotmonitoring;

import java.util.ArrayList;
import java.util.List;

public class Sensor {
    private final String id;
    private final String type;
    private final String deviceId;
    private Threshold threshold;
    private final List<Reading> readings = new ArrayList<>();

    public Sensor(String id, String type, String deviceId) {
        this.id = id;
        this.type = type;
        this.deviceId = deviceId;
    }

    public String getId() { return id; }
    public String getType() { return type; }
    public String getDeviceId() { return deviceId; }
    public Threshold getThreshold() { return threshold; }
    public List<Reading> getReadings() { return readings; }

    public void setThreshold(Threshold threshold) {
        this.threshold = threshold;
    }

    public void addReading(Reading reading) {
        readings.add(reading);
    }

    public Reading getLastReading() {
        if (readings.isEmpty()) return null;
        return readings.get(readings.size() - 1);
    }

    @Override
    public String toString() {
        return id + " | Type: " + type + " | Device: " + deviceId
                + " | Threshold: " + (threshold == null ? "Not set" : threshold);
    }
}
