package cz.fel.cvut.behelpdesk.enumeration;

public enum CategoryEnum {
    INTRANET("Intranet"),
    SOFTWARE("Software"),
    HARDWARE("Hardware"),
    PROPERTY("Majetek"),
    PERMISSION("Oprávnění"),
    OTHER("Ostatní");

    private final String czechString;

    CategoryEnum(String czechString) {
        this.czechString = czechString;
    }

    public String getCzechString() {
        return this.czechString;
    }
}
