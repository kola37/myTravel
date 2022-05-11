package entity;

import entity.constant.HotelType;

import java.io.Serializable;
import java.util.Objects;

/**
 * Hotel entity with getters, setters and
 * overridden equals, hashCode and toString() methods
 *
 * @author Anatolii Koliaka
 */
public class Hotel implements Serializable {

    //use serialVersionUID for interoperability
    private static final long serialVersionUID = 3274783900451435009L;

    private int hotelId;
    private String name;
    private HotelType type;

    public Hotel() {
    }

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HotelType getType() {
        return type;
    }

    public void setType(HotelType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hotel hotel = (Hotel) o;
        return hotelId == hotel.hotelId && Objects.equals(name, hotel.name) && type == hotel.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(hotelId, name, type);
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "hotelId=" + hotelId +
                ", name='" + name + '\'' +
                ", type=" + type +
                '}';
    }


    /**
     * Class to build a new Hotel object
     *
     * @author Anatolii Koliaka
     */
    public static class Builder {

        private Hotel hotel;

        public Builder() {
            hotel = new Hotel();
        }

        public Builder withName(String name) {
            hotel.name = name;
            return this;
        }

        public Builder withHotelType(HotelType type) {
            hotel.type = type;
            return this;
        }

        public Hotel build() {
            return hotel;
        }

    }

}




