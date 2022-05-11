package entity;

import entity.constant.TourType;

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

    private static final String YES = "yes";
    private static final String NO = "no";

    private int tourId;
    private String name;
    private String description;
    private String image;
    private double price;
    private double discount;
    private double maxDiscount;
    private TourType type;
    private int person;
    private Hotel hotel;
    private boolean isHot;

    public Tour() {
    }

    public int getTourId() {
        return tourId;
    }

    public void setTourId(int tourId) {
        this.tourId = tourId;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getMaxDiscount() {
        return maxDiscount;
    }

    public void setMaxDiscount(double maxDiscount) {
        this.maxDiscount = maxDiscount;
    }

    public TourType getType() {
        return type;
    }

    public void setType(TourType type) {
        this.type = type;
    }

    public int getPerson() {
        return person;
    }

    public void setPerson(int person) {
        this.person = person;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
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
        return tourId == tour.tourId && Double.compare(tour.price, price) == 0 && Double.compare(tour.discount, discount) == 0 && Double.compare(tour.maxDiscount, maxDiscount) == 0 && person == tour.person && isHot == tour.isHot && Objects.equals(name, tour.name) && Objects.equals(description, tour.description) && Objects.equals(image, tour.image) && type == tour.type && Objects.equals(hotel, tour.hotel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tourId, name, description, image, price, discount, maxDiscount, type, person, hotel, isHot);
    }

    @Override
    public String toString() {
        return "Tour{" +
                "tourId=" + tourId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", discount=" + discount +
                ", maxDiscount=" + maxDiscount +
                ", type=" + type +
                ", person=" + person +
                ", hotel=" + hotel +
                ", isHot='" + (isHot ? YES : NO) + '\'' +
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

        public Builder withDiscount(int discount){
            tour.discount = discount;
            return this;
        }

        public Builder withMaxDiscount(int maxDiscount){
            tour.maxDiscount = maxDiscount;
            return this;
        }

        public Builder withType(TourType type){
            tour.type = type;
            return this;
        }

        public Builder withPerson(int person){
            tour.person = person;
            return this;
        }

        public Builder withHotel(Hotel hotel){
            tour.hotel = hotel;
            return this;
        }

        public Builder isHotTour(boolean isHot){
            tour.isHot = isHot;
            return this;
        }

        public Tour build(){
            return tour;
        }
    }
}
