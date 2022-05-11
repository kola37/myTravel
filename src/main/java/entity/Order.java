package entity;

import entity.constant.OrderStatus;

import java.io.Serializable;

/**
 * Order entity with getters, setters and
 * overridden toString() method
 *
 * @author Anatolii Koliaka
 */
public class Order implements Serializable {

    //use serialVersionUID for interoperability
    private static final long serialVersionUID = 4983601923663389209L;

    private int orderId;
    private User client;
    private Tour tour;
    private OrderStatus status;

    public Order() {
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public User getClient() {
        return client;
    }

    public void setClient(User client) {
        this.client = client;
    }

    public Tour getTour() {
        return tour;
    }

    public void setTour(Tour tour) {
        this.tour = tour;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", client=" + client +
                ", tour=" + tour +
                ", status=" + status +
                '}';
    }

    /**
     * Class to build a new Order object
     *
     * @author Anatolii Koliaka
     */
    public static class Builder {

        private Order order;

        public Builder() {
            order = new Order();
        }

        public Builder withClient(User client) {
            order.client = client;
            return this;
        }

        public Builder withTour(Tour tour) {
            order.tour = tour;
            return this;
        }

        public Builder withOrderStatus(OrderStatus status) {
            order.status = status;
            return this;
        }

        public Order build() {
            return order;
        }
    }
}
