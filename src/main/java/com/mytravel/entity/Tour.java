package com.mytravel.entity;

import java.io.Serializable;
import java.util.Objects;

/**
 * Tour entity with getters, setters and
 * overridden equals, hashCode and toString() methods
 *
 * @author Anatolii Koliaka
 */
public class Tour implements Serializable {

    //use serialVersionUID for interoperability
    private static final long serialVersionUID = -6872678767830240136L;


    private int id;
    private String name;
    private String description;
    private String image;
    private int price;
    private int discountRate;
    private int maxDiscount;
    private int tourTypeId;
    private int numOfPersons;
    private int hotelId;
    private boolean isHot;

    public Tour() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(int discountRate) {
        this.discountRate = discountRate;
    }

    public int getMaxDiscount() {
        return maxDiscount;
    }

    public void setMaxDiscount(int maxDiscount) {
        this.maxDiscount = maxDiscount;
    }

    public int getTourTypeId() {
        return tourTypeId;
    }

    public void setTourTypeId(int tourTypeId) {
        this.id = tourTypeId;
    }

    public int getNumOfPersons() {
        return numOfPersons;
    }

    public void setNumOfPersons(int numOfPersons) {
        this.numOfPersons = numOfPersons;
    }

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    public boolean isHot() {
        return isHot;
    }

    public void setHot(boolean hot) {
        isHot = hot;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tour tour = (Tour) o;
        return id == tour.id && tour.price == price && tourTypeId == tour.tourTypeId && numOfPersons == tour.numOfPersons && hotelId == tour.hotelId
                && isHot == tour.isHot && name.equals(tour.name) && description.equals(tour.description) && Objects.equals(image, tour.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, image, price, tourTypeId, numOfPersons, hotelId, isHot);
    }

    @Override
    public String toString() {
        return "Tour{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", discountRate=" + discountRate +
                ", maxDiscount=" + maxDiscount +
                ", tourTypeId=" + tourTypeId +
                ", person=" + numOfPersons +
                ", hotelId=" + hotelId +
                ", isHot='" + isHot +
                '}';
    }

    /**
     * Class to build a new Tour object
     *
     * @author Anatolii Koliaka
     */
    public static class Builder {

        private Tour tour;

        public Builder() {
            tour = new Tour();
        }

        public Builder withId(int id) {
            tour.id = id;
            return this;
        }

        public Builder withName(String name){
            tour.name = name;
            return this;
        }

        public Builder withDescription(String description){
            tour.description = description;
            return this;
        }

        public Builder withImage(String image){
            tour.image = image;
            return this;
        }

        public Builder withPrice(int price){
            tour.price = price;
            return this;
        }

        public Builder withDiscountRate(int discountRate){
            tour.discountRate = discountRate;
            return this;
        }

        public Builder withMaxDiscount(int maxDiscount){
            tour.maxDiscount = maxDiscount;
            return this;
        }

        public Builder withTourTypeId(int tourTypeId){
            tour.tourTypeId = tourTypeId;
            return this;
        }

        public Builder withNumOfPersons(int person){
            tour.numOfPersons = person;
            return this;
        }

        public Builder withHotelId(int hotelId){
            tour.hotelId = hotelId;
            return this;
        }

        public Builder isHot(boolean isHot){
            tour.isHot = isHot;
            return this;
        }

        public Tour build(){
            return tour;
        }
    }
}
