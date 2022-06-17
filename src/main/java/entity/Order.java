package entity;

import entity.constant.OrderStatus;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

/**
 * Order entity with getters, setters and
 * overridden toString() method
 *
 * @author Anatolii Koliaka
 */
public class Order implements Serializable {

    //use serialVersionUID for interoperability
    private static final long serialVersionUID = 4983601923663389209L;

    private int id;
    private int userId;
    private int tourId;
    private int statusId;
    private Date orderDate;
    private int discount;
    private BigDecimal totalPrice;

    public Order() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getTourId() {
        return tourId;
    }

    public void setTourId(int tourId) {
        this.tourId = tourId;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id && userId == order.userId && tourId == order.tourId && statusId == order.statusId && discount == order.discount && order.totalPrice.equals(totalPrice) && orderDate.equals(order.orderDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, tourId, statusId, orderDate, discount, totalPrice);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", userId=" + userId +
                ", tourId=" + tourId +
                ", statusId=" + statusId +
                ", orderDate=" + orderDate +
                ", discount=" + discount +
                ", totalPrice=" + totalPrice +
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

        public Builder withId(int id) {
            order.id = id;
            return this;
        }

        public Builder withUserId(int userId) {
            order.userId = userId;
            return this;
        }

        public Builder withTourId(int tourId) {
            order.tourId = tourId;
            return this;
        }

        public Builder withOrderStatusId(int statusId) {
            order.statusId = statusId;
            return this;
        }

        public Builder withOrderDate(Date orderDate) {
            order.orderDate = orderDate;
            return this;
        }

        public Builder withDiscount(int discount) {
            order.discount = discount;
            return this;
        }

        public Builder withTotalPrice(BigDecimal totalPrice) {
            order.totalPrice = totalPrice;
            return this;
        }

        public Order build() {
            return order;
        }
    }
}
