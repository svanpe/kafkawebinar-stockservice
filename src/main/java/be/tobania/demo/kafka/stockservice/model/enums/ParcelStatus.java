package be.tobania.demo.kafka.stockservice.model.enums;

public enum ParcelStatus {

    PLACED("placed"),

    PREPARED("prepared"),

    IN_DELIVERY("in_delivery"),

    DELIVERED("delivered");

    private String value;

    private ParcelStatus(String value){
        this.value = value;
    }

    public static ParcelStatus fromValue(String text) {
        for (ParcelStatus b : values()) {
            if (String.valueOf(b.value).equals(text)) {
                return b;
            }
        }
        return null;
    }
}
