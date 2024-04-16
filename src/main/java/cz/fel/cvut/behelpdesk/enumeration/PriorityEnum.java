package cz.fel.cvut.behelpdesk.enumeration;

public enum PriorityEnum {
    LOW("Nízká"),
    MEDIUM("Střední"),
    HIGH("Vysoká"),
    CRITICAL("Kritická");

    private final String czechString;

    PriorityEnum(String czechString) {
        this.czechString = czechString;
    }

    public String getCzechString() {
        return this.czechString;
    }
}
