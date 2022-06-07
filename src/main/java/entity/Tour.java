package entity;

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

    private int id;
    private String name;
    private String description;
    private String image;
    private double price;
//    private double discount;
//    private double maxDiscount;
    private int tourTypeId;
    private int numOfPersons;
    private int hotelId;
    private int promotionId;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

//    public double getDiscount() {
//        return discount;
//    }
//
//    public void setDiscount(double discount) {
//        this.discount = discount;
//    }
//
//    public double getMaxDiscount() {
//        return maxDiscount;
//    }
//
//    public void setMaxDiscount(double maxDiscount) {
//        this.maxDiscount = maxDiscount;
//    }

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

    public int getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(int promotionId) {
        this.promotionId = promotionId;
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
        return id == tour.id && Double.compare(tour.price, price) == 0 && tourTypeId == tour.tourTypeId && numOfPersons == tour.numOfPersons && hotelId == tour.hotelId
                && promotionId == tour.promotionId && isHot == tour.isHot && name.equals(tour.name) && description.equals(tour.description) && Objects.equals(image, tour.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, image, price, tourTypeId, numOfPersons, hotelId, promotionId, isHot);
    }

    @Override
    public String toString() {
        return "Tour{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
//                ", discount=" + discount +
//                ", maxDiscount=" + maxDiscount +
                ", tourTypeId=" + tourTypeId +
                ", person=" + numOfPersons +
                ", hotelId=" + hotelId +
                ", promotionId=" + promotionId +
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

//        public Builder withDiscount(int discount){
//            tour.discount = discount;
//            return this;
//        }
//
//        public Builder withMaxDiscount(int maxDiscount){
//            tour.maxDiscount = maxDiscount;
//            return this;
//        }

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

        public Builder withPromotionId(int promotionId){
            tour.promotionId = promotionId;
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
