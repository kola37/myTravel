package entity;

import entity.constant.UserRole;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * User entity with getters, setters and
 * overridden equals, hashCode and toString() methods
 *
 * @author Anatolii Koliaka
 */

public class User implements Serializable {

    //use serialVersionUID for interoperability
    private static final long serialVersionUID = 3965776531575479619L;

    private int userId;
    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private UserRole role;
    private List<Order> orders;
    private boolean isLoggedOn;

    public User() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public boolean isLoggedOn() {
        return isLoggedOn;
    }

    public void setLoggedOn(boolean loggedOn) {
        isLoggedOn = loggedOn;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userId == user.userId && login.equals(user.login) && password.equals(user.password) && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(email, user.email) && role.equals(user.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, login, password, firstName, lastName, email, role);
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                ", orderedToursCount=" + orders +
                ", isLoggedOn=" + isLoggedOn +
                '}';
    }

    /**
     * Class to build a new User object
     *
     * @author Anatolii Koliaka
     */
    public static class Builder {

        private User user;

        public Builder() {
            user = new User();
        }

        public Builder withLogin(String login) {
            user.login = login;
            return this;
        }

        public Builder withPassword(String password) {
            user.password = password;
            return this;
        }

        public Builder withFirstName(String firstName) {
            user.firstName = firstName;
            return this;
        }

        public Builder withLastName(String lastName) {
            user.lastName = lastName;
            return this;
        }

        public Builder withEmail(String email) {
            user.email = email;
            return this;
        }

        public Builder withRole(UserRole role) {
            user.role = role;
            return this;
        }

        public Builder withOrders(List<Order> orders) {
            user.orders = orders;
            return this;
        }

        public User build() {
            return user;
        }
    }

}

