package cz.fel.cvut.behelpdesk.enumeration;

public enum StateEnum {
    NEW("Nový"),
    IN_PROGRESS("V řešení"),
    SOLVED("Vyřešen"),
    INVALID("Neplatný");

    private final String czechString;

    StateEnum(String czechString) {
        this.czechString = czechString;
    }

    public String getCzechString() {
        return this.czechString;
    }
}
