package entity;

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

    private int id;
    private String name;
    private int hotelTypeId;

    public Hotel() {
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

    public int getHotelTypeId() {
        return hotelTypeId;
    }

    public void setHotelTypeId(int hotelTypeId) {
        this.hotelTypeId = hotelTypeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hotel hotel = (Hotel) o;
        return id == hotel.id && Objects.equals(name, hotel.name) && hotelTypeId == hotel.hotelTypeId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, hotelTypeId);
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", hotelTypeId=" + hotelTypeId +
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

        public Builder withId(int id) {
            hotel.id = id;
            return this;
        }

        public Builder withName(String name) {
            hotel.name = name;
            return this;
        }

        public Builder withHotelTypeId(int hotelTypeId) {
            hotel.hotelTypeId = hotelTypeId;
            return this;
        }

        public Hotel build() {
            return hotel;
        }

    }

}




