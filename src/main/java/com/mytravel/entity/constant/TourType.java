package com.mytravel.entity.constant;

/**
 * Tour type entity enum
 *
 * @author Anatolii Koliaka
 */
public enum TourType {
    HOLIDAYS(1), EXCURSION(2), SHOPPING(3);

    private int index;

    TourType(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    /**
     * Returns the tour type from TourType enum by specified index
     *
     * @param index index of the tour type to return
     * @return the tour type specified by index
     */
    public static TourType getType(int index) {
        return TourType.values()[index - 1];
    }

    public String getName() {
        return name().toLowerCase();
    }
}
