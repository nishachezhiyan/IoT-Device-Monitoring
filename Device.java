package iotmonitoring;

import java.util.ArrayList;
import java.util.List;

public class Device {
    private final String id;
    private final String name;
    private final String location;
    private final List<Sensor> sensors = new ArrayList<>();

    public Device(String id, String name, String location) {
        this.id = id;
        this.name = name;
        this.location = location;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getLocation() { return location; }
    public List<Sensor> getSensors() { return sensors; }

    public void addSensor(Sensor sensor) {
        sensors.add(sensor);
    }

    @Override
    public String toString() {
        return id + " | " + name + " | Location: " + location
                + " | Sensors: " + sensors.size();
    }
}
