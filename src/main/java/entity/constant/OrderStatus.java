package entity.constant;

/**
 * Order status entity enum
 *
 * @author Anatolii Koliaka
 */
public enum OrderStatus {
      REGISTERED(1), PAID(2), CANCELED(3);

    private int index;

    OrderStatus(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    /**
     * Returns the order status from OrderStatus enum by specified index
     *
     * @param index index of the order status to return
     * @return the order status specified by index
     */
    public static OrderStatus getStatus(int index) {
        return OrderStatus.values()[index - 1];
    }

}
