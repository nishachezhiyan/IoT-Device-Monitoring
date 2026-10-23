package iotmonitoring;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Reading {
    private final String id;
    private final String sensorId;
    private final String deviceId;
    private final double value;
    private final LocalDateTime timestamp;

    public Reading(String id, String sensorId, String deviceId, double value) {
        this.id = id;
        this.sensorId = sensorId;
        this.deviceId = deviceId;
        this.value = value;
        this.timestamp = LocalDateTime.now();
    }

    public String getId() { return id; }
    public String getSensorId() { return sensorId; }
    public String getDeviceId() { return deviceId; }
    public double getValue() { return value; }
    public LocalDateTime getTimestamp() { return timestamp; }

    @Override
    public String toString() {
        return id + " | Device: " + deviceId + " | Sensor: " + sensorId
                + " | Value: " + value + " | Time: "
                + timestamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
