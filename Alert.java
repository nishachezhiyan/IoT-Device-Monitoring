package iotmonitoring;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Alert {
    private final String id;
    private final String readingId;
    private final String sensorId;
    private final String deviceId;
    private final String severity;
    private final String message;
    private final LocalDateTime createdAt;
    private String status;

    public Alert(String id, Reading reading, String severity, String message) {
        this.id = id;
        this.readingId = reading.getId();
        this.sensorId = reading.getSensorId();
        this.deviceId = reading.getDeviceId();
        this.severity = severity;
        this.message = message;
        this.status = "NEW";
        this.createdAt = LocalDateTime.now();
    }

    public String getId() { return id; }
    public String getReadingId() { return readingId; }
    public String getSensorId() { return sensorId; }
    public String getDeviceId() { return deviceId; }
    public String getSeverity() { return severity; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return id + " | " + severity + " | " + status + " | Device: " + deviceId
                + " | Sensor: " + sensorId + " | Reading: " + readingId
                + " | " + message + " | "
                + createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
