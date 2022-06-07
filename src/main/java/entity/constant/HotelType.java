package entity.constant;

/**
 * Hotel type entity enum
 *
 * @author Anatolii Koliaka
 */
public enum HotelType {
    APARTMENT(1),
    HOSTEL(2),
    TOURIST_HOTEL(3),
    COMFORT_HOTEL(4),
    PREMIUM_HOTEL(5),
    BOUTIQUE_HOTEL(6);

    private int index;

    HotelType(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    /**
     * Returns the type of hotel from HotelType enum by specified index
     *
     * @param index index of the hotel type to return
     * @return the type of hotels specified by index
     */
    public static HotelType getType(int index) {
        return HotelType.values()[index - 1];
    }

    public String getName() {
        return name().toLowerCase();
    }
}
