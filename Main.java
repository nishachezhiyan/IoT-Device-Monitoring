package iotmonitoring;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner sc = new Scanner(System.in);
    private static final List<Device> devices = new ArrayList<>();
    private static final List<Sensor> sensors = new ArrayList<>();
    private static final List<Reading> readings = new ArrayList<>();
    private static final List<Alert> alerts = new ArrayList<>();
    private static final List<MaintenanceTicket> tickets = new ArrayList<>();
    private static int deviceCounter = 1;
    private static int sensorCounter = 1;
    private static int readingCounter = 1;
    private static int alertCounter = 1;
    private static int ticketCounter = 1;
    private static int technicianCounter = 1;

    public static void main(String[] args) {
        int choice;
        System.out.println("==============================================");
        System.out.println("  IoT DEVICE MONITORING & ALERT MANAGEMENT");
        System.out.println("==============================================");

        do {
            printMenu();
            choice = readInt("Enter your choice: ");

            try {
                switch (choice) {
                    case 1: addDevice(); break;
                    case 2: addSensor(); break;
                    case 3: setThreshold(); break;
                    case 4: ingestReading(); break;
                    case 5: generateAlertsForBreachedReadings(); break;
                    case 6: acknowledgeAlert(); break;
                    case 7: createMaintenanceTicket(); break;
                    case 8: closeTicket(); break;
                    case 9: showAlerts(); break;
                    case 10: showDashboard(); break;
                    case 11: addTechnicianActionLog(); break;
                    case 12:
                        System.out.println("Thank you for using IoT Monitoring System. Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a number from 1 to 12.");
                }
            } catch (IllegalArgumentException | IllegalStateException ex) {
                System.out.println("Error: " + ex.getMessage());
            } catch (Exception ex) {
                System.out.println("Unexpected error: " + ex.getMessage());
            }
            System.out.println();
        } while (choice != 12);

        sc.close();
    }

    private static void printMenu() {
        System.out.println("--------------- MAIN MENU ----------------");
        System.out.println("1. Add Device");
        System.out.println("2. Add Sensor");
        System.out.println("3. Set Threshold");
        System.out.println("4. Ingest Reading");
        System.out.println("5. Generate Alert");
        System.out.println("6. Acknowledge Alert");
        System.out.println("7. Create Maintenance Ticket");
        System.out.println("8. Close Ticket");
        System.out.println("9. View Live Alert List");
        System.out.println("10. Device/Sensor Dashboard");
        System.out.println("11. Add Technician Action Log");
        System.out.println("12. Exit");
        System.out.println("------------------------------------------");
    }

    private static void addDevice() {
        String name = readText("Enter device name: ");
        String location = readText("Enter device location: ");
        String id = String.format("D%03d", deviceCounter++);
        Device device = new Device(id, name, location);
        devices.add(device);
        System.out.println("Device registered successfully. Device ID: " + id);
    }

    private static void addSensor() {
        if (devices.isEmpty()) {
            System.out.println("Register a device before adding a sensor.");
            return;
        }
        String deviceId = readText("Enter registered device ID: ").toUpperCase();
        Device device = findDevice(deviceId);
        if (device == null) {
            System.out.println("Device not found. Sensor was not added.");
            return;
        }

        String type = readText("Enter sensor type (Temperature/Humidity/Gas): ");
        String id = String.format("S%03d", sensorCounter++);
        Sensor sensor = new Sensor(id, type, deviceId);
        sensors.add(sensor);
        device.addSensor(sensor);
        System.out.println("Sensor registered successfully. Sensor ID: " + id);
    }

    private static void setThreshold() {
        Sensor sensor = findSensor(readText("Enter registered sensor ID: ").toUpperCase());
        if (sensor == null) {
            System.out.println("Sensor not found.");
            return;
        }
        double min = readDouble("Enter minimum allowed value: ");
        double max = readDouble("Enter maximum allowed value: ");
        sensor.setThreshold(new Threshold(min, max));
        System.out.println("Threshold saved for sensor " + sensor.getId() + ": " + sensor.getThreshold());
    }

    private static void ingestReading() {
        String sensorId = readText("Enter registered sensor ID: ").toUpperCase();
        Sensor sensor = findSensor(sensorId);
        if (sensor == null) {
            System.out.println("Reading rejected: sensor is not registered.");
            return;
        }
        Device device = findDevice(sensor.getDeviceId());
        if (device == null) {
            System.out.println("Reading rejected: linked device is not registered.");
            return;
        }
        double value = readDouble("Enter sensor reading value: ");
        String id = String.format("R%03d", readingCounter++);
        Reading reading = new Reading(id, sensor.getId(), device.getId(), value);
        readings.add(reading);
        sensor.addReading(reading);
        System.out.println("Reading stored successfully: " + reading);

        if (sensor.getThreshold() == null) {
            System.out.println("Warning: no threshold is configured. Set a threshold to enable alerts.");
        } else if (sensor.getThreshold().isBreached(value)) {
            createAlertIfMissing(reading, sensor);
        } else {
            System.out.println("Reading is within the configured threshold.");
        }
    }

    private static void generateAlertsForBreachedReadings() {
        int generated = 0;
        for (Reading reading : readings) {
            Sensor sensor = findSensor(reading.getSensorId());
            if (sensor != null && sensor.getThreshold() != null
                    && sensor.getThreshold().isBreached(reading.getValue())) {
                if (createAlertIfMissing(reading, sensor)) generated++;
            }
        }
        if (generated == 0) {
            System.out.println("No new alerts to generate. No unreported threshold breaches were found.");
        } else {
            System.out.println(generated + " alert(s) generated.");
        }
    }

    private static boolean createAlertIfMissing(Reading reading, Sensor sensor) {
        for (Alert alert : alerts) {
            if (alert.getReadingId().equals(reading.getId())) return false;
        }
        Threshold threshold = sensor.getThreshold();
        String severity = threshold.getSeverity(reading.getValue());
        String message = sensor.getType() + " reading " + reading.getValue()
                + " is outside allowed range [" + threshold.getMinimum()
                + ", " + threshold.getMaximum() + "]";
        Alert alert = new Alert(String.format("A%03d", alertCounter++), reading, severity, message);
        alerts.add(alert);
        System.out.println("!!! ALERT GENERATED: " + alert);
        return true;
    }

    private static void acknowledgeAlert() {
        Alert alert = findAlert(readText("Enter alert ID to acknowledge: ").toUpperCase());
        if (alert == null) {
            System.out.println("Alert not found.");
            return;
        }
        if (!alert.getStatus().equals("NEW")) {
            System.out.println("Only NEW alerts can be acknowledged. Current status: " + alert.getStatus());
            return;
        }
        alert.setStatus("ACKNOWLEDGED");
        System.out.println("Alert " + alert.getId() + " acknowledged. Create a maintenance ticket to resolve it.");
    }

    private static void createMaintenanceTicket() {
        Alert alert = findAlert(readText("Enter acknowledged alert ID: ").toUpperCase());
        if (alert == null) {
            System.out.println("Alert not found.");
            return;
        }
        if (!alert.getStatus().equals("ACKNOWLEDGED")) {
            System.out.println("A maintenance ticket can only be created for an ACKNOWLEDGED alert.");
            return;
        }
        for (MaintenanceTicket ticket : tickets) {
            if (ticket.getAlertId().equals(alert.getId()) && !ticket.getStatus().equals("CLOSED")) {
                System.out.println("An open maintenance ticket already exists: " + ticket.getId());
                return;
            }
        }

        String technicianName = readText("Enter technician name: ");
        Technician technician = new Technician(String.format("T%03d", technicianCounter++), technicianName);
        MaintenanceTicket ticket = new MaintenanceTicket(
                String.format("M%03d", ticketCounter++), alert.getId(), technician);
        String firstAction = readText("Enter initial technician action log (required): ");
        ticket.addActionLog(firstAction);
        tickets.add(ticket);
        System.out.println("Maintenance ticket created: " + ticket.getId());
        System.out.println("Technician ID: " + technician.getId());
        System.out.println("Ticket status: " + ticket.getStatus() + " | Action logs: " + ticket.getActionLogs().size());
    }

    private static void addTechnicianActionLog() {
        MaintenanceTicket ticket = findTicket(readText("Enter open maintenance ticket ID: ").toUpperCase());
        if (ticket == null) {
            System.out.println("Maintenance ticket not found.");
            return;
        }
        if (ticket.getStatus().equals("CLOSED")) {
            System.out.println("Cannot add an action log to a closed ticket.");
            return;
        }
        ticket.addActionLog(readText("Enter technician action performed: "));
        System.out.println("Action log added successfully.");
    }

    private static void closeTicket() {
        MaintenanceTicket ticket = findTicket(readText("Enter maintenance ticket ID: ").toUpperCase());
        if (ticket == null) {
            System.out.println("Maintenance ticket not found.");
            return;
        }
        if (ticket.getStatus().equals("CLOSED")) {
            System.out.println("This ticket is already closed.");
            return;
        }
        if (ticket.getActionLogs().isEmpty()) {
            System.out.println("Cannot close ticket: at least one technician action log is required.");
            return;
        }
        String notes = readText("Enter resolution notes: ");
        ticket.close(notes);
        Alert alert = findAlert(ticket.getAlertId());
        if (alert != null) alert.setStatus("RESOLVED");
        System.out.println("Ticket closed successfully.");
        System.out.println(ticket);
        System.out.println("Linked alert status updated to RESOLVED.");
    }

    private static void showAlerts() {
        System.out.println("=============== LIVE ALERT LIST ===============");
        if (alerts.isEmpty()) {
            System.out.println("No alerts recorded.");
            return;
        }
        for (Alert alert : alerts) System.out.println(alert);
    }

    private static void showDashboard() {
        System.out.println("================ DEVICE/SENSOR DASHBOARD ================");
        if (devices.isEmpty()) {
            System.out.println("No devices registered yet.");
            return;
        }
        int activeAlerts = 0;
        int openTickets = 0;
        for (Alert alert : alerts) {
            if (!alert.getStatus().equals("RESOLVED")) activeAlerts++;
        }
        for (MaintenanceTicket ticket : tickets) {
            if (!ticket.getStatus().equals("CLOSED")) openTickets++;
        }
        System.out.println("Registered devices: " + devices.size());
        System.out.println("Registered sensors: " + sensors.size());
        System.out.println("Total readings: " + readings.size());
        System.out.println("Active alerts: " + activeAlerts);
        System.out.println("Open maintenance tickets: " + openTickets);
        System.out.println("---------------------------------------------------------");
        for (Device device : devices) {
            System.out.println(device);
            for (Sensor sensor : device.getSensors()) {
                System.out.println("   " + sensor);
                Reading last = sensor.getLastReading();
                System.out.println("   Last reading: " + (last == null ? "No readings yet" : last));
            }
        }
        System.out.println("=========================================================");
    }

    private static Device findDevice(String id) {
        for (Device device : devices) if (device.getId().equalsIgnoreCase(id)) return device;
        return null;
    }

    private static Sensor findSensor(String id) {
        for (Sensor sensor : sensors) if (sensor.getId().equalsIgnoreCase(id)) return sensor;
        return null;
    }

    private static Alert findAlert(String id) {
        for (Alert alert : alerts) if (alert.getId().equalsIgnoreCase(id)) return alert;
        return null;
    }

    private static MaintenanceTicket findTicket(String id) {
        for (MaintenanceTicket ticket : tickets) if (ticket.getId().equalsIgnoreCase(id)) return ticket;
        return null;
    }

    private static String readText(String prompt) {
        System.out.print(prompt);
        String value = sc.nextLine().trim();
        if (value.isEmpty()) throw new IllegalArgumentException("Input cannot be empty.");
        return value;
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException ex) {
                System.out.println("Please enter a valid whole number.");
            }
        }
    }

    private static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine().trim();
            try {
                double value = Double.parseDouble(input);
                if (Double.isNaN(value) || Double.isInfinite(value)) {
                    System.out.println("Enter a finite numeric value.");
                    continue;
                }
                return value;
            } catch (NumberFormatException ex) {
                System.out.println("Please enter a valid numeric value.");
            }
        }
    }
}
