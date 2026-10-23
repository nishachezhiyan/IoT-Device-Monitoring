package iotmonitoring;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MaintenanceTicket {
    private final String id;
    private final String alertId;
    private final Technician technician;
    private final List<String> actionLogs = new ArrayList<>();
    private String status = "OPEN";
    private String resolutionNotes = "";
    private LocalDateTime closedAt;

    public MaintenanceTicket(String id, String alertId, Technician technician) {
        this.id = id;
        this.alertId = alertId;
        this.technician = technician;
    }

    public String getId() { return id; }
    public String getAlertId() { return alertId; }
    public Technician getTechnician() { return technician; }
    public List<String> getActionLogs() { return actionLogs; }
    public String getStatus() { return status; }

    public void addActionLog(String action) {
        if (action == null || action.trim().isEmpty()) {
            throw new IllegalArgumentException("Action log cannot be empty.");
        }
        actionLogs.add(LocalDateTime.now() + " - " + action.trim());
    }

    public void close(String notes) {
        if (actionLogs.isEmpty()) {
            throw new IllegalStateException("Add at least one technician action log before closing.");
        }
        if (notes == null || notes.trim().isEmpty()) {
            throw new IllegalArgumentException("Resolution notes cannot be empty.");
        }
        resolutionNotes = notes.trim();
        status = "CLOSED";
        closedAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return id + " | Alert: " + alertId + " | Technician: " + technician.getName()
                + " (" + technician.getId() + ") | Status: " + status
                + " | Action logs: " + actionLogs.size()
                + (status.equals("CLOSED") ? " | Resolution: " + resolutionNotes
                + " | Closed at: " + closedAt : "");
    }
}
