# IoT Device Monitoring & Intelligent Alert Management System

## 1. Project Overview

The IoT Device Monitoring & Intelligent Alert Management System is a Java-based console application designed to monitor IoT devices and their sensor readings. It identifies abnormal readings by comparing them with configured thresholds, generates alerts, and manages maintenance activities until issues are resolved.

The application demonstrates Object-Oriented Programming (OOP), input validation, exception handling, and menu-driven programming.

## 2. Project Objectives

- Register and manage IoT devices and sensors.
- Configure minimum and maximum sensor thresholds.
- Record sensor readings and detect abnormal values.
- Generate alerts automatically when thresholds are breached.
- Assign severity levels to alerts.
- Acknowledge alerts and create maintenance tickets.
- Record technician actions and resolution notes.
- Display device and sensor dashboard information.

## 3. Technologies Used

- **Programming Language:** Java
- **IDE:** Eclipse IDE
- **Interface:** Java Console
- **Version Control:** Git
- **Repository Hosting:** GitHub

## 4. Main Features

### Device Management
Register devices with their names and locations.

### Sensor Management
Add sensors to registered devices and maintain the relationship between devices and sensors.

### Threshold Configuration
Set minimum and maximum acceptable values for each sensor.

### Sensor Reading Monitoring
Enter sensor readings and compare them against configured thresholds.

### Intelligent Alert Management
Automatically generate alerts when readings exceed the allowed range. Alerts receive LOW, MEDIUM, or HIGH severity based on the degree of threshold violation.

### Maintenance Ticket Management
Create maintenance tickets for acknowledged alerts, record technician actions, and close tickets with resolution notes.

### Dashboard
Display the number of registered devices, sensors, readings, active alerts, and open maintenance tickets. View the latest reading for each sensor.

## 5. Object-Oriented Programming Concepts

The project uses the following OOP concepts:

- **Encapsulation:** Class data is maintained using private fields and public methods.
- **Classes and Objects:** Devices, sensors, readings, alerts, technicians, and maintenance tickets are represented as Java objects.
- **Association:** Readings and alerts maintain references to their related sensor and device through identifiers.
- **Composition/Aggregation:** Devices maintain collections of their sensors, and maintenance tickets maintain technician action logs.
- **Exception Handling:** Invalid thresholds and other invalid operations are handled using exceptions.
- **Collections:** ArrayList is used to maintain project records during execution.

## 6. Class Structure

| Class | Purpose |
|---|---|
| `Main` | Displays the menu and controls the application |
| `Device` | Stores device information and associated sensors |
| `Sensor` | Stores sensor details, thresholds, and readings |
| `Reading` | Stores sensor values, timestamps, and associated identifiers |
| `Threshold` | Checks acceptable reading limits and determines severity |
| `Alert` | Stores alert details, severity, and status |
| `Technician` | Stores technician identification and name |
| `MaintenanceTicket` | Manages technician actions and ticket resolution |

## 7. Menu Options

1. Add Device
2. Add Sensor
3. Set Threshold
4. Ingest Reading
5. Generate Alert
6. Acknowledge Alert
7. Create Maintenance Ticket
8. Close Ticket
9. View Live Alert List
10. Device/Sensor Dashboard
11. Add Technician Action Log
12. Exit

## 8. How to Run the Project in Eclipse

1. Install and open Eclipse IDE.
2. Create a new Java project named `IoTDeviceMonitoring`.
3. Create a package named `iotmonitoring`.
4. Add all eight Java source files to the package.
5. Open `Main.java`.
6. Right-click inside the editor.
7. Select **Run As → Java Application**.
8. Enter the required choices and values in the Eclipse Console.

## 9. Sample Test Scenario

1. Register a device named `Cold Storage Unit` at `Lab A`.
2. Add a Temperature sensor to the device.
3. Configure the minimum threshold as `2` and the maximum threshold as `8`.
4. Enter a sensor reading of `15`.
5. Verify that an alert is generated because the reading exceeds the maximum threshold.
6. Acknowledge the alert.
7. Create a maintenance ticket and enter a technician action log.
8. Close the ticket with resolution notes.
9. Verify that the linked alert status changes to `RESOLVED`.
10. View the dashboard to check the updated counts.

## 10. Additional Enhancement

### Smart Alert Priority and Device Health Monitoring

The system includes a smart alert severity mechanism that categorizes threshold violations as LOW, MEDIUM, or HIGH. It also provides a dashboard summary of devices, sensors, readings, alerts, and maintenance tickets to support monitoring and maintenance decisions.

## 11. Limitations

- Sensor readings are entered manually to simulate IoT data.
- Physical sensors and real-time hardware integration are not included.
- Data is stored in memory and is cleared when the application exits.
- The application is a console-based prototype.

## 12. Future Enhancements

- Integrate real IoT sensors and devices.
- Store records in a database such as MySQL.
- Add real-time monitoring and graphical dashboards.
- Implement email or SMS notifications for critical alerts.
- Add user authentication and administrator roles.
- Store historical readings for trend analysis.

## 13. Conclusion

The IoT Device Monitoring & Intelligent Alert Management System demonstrates how Java can be used to simulate device monitoring, threshold-based alert generation, and maintenance workflows. The project applies OOP concepts, collections, input validation, and exception handling to provide a foundation for a more advanced IoT monitoring application.
