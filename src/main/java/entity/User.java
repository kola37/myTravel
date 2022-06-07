package entity;

import java.io.Serializable;
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

    private int id;
    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private int roleId;
    private boolean isBlocked;

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && login.equals(user.login) && password.equals(user.password) && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(email, user.email) && roleId == user.roleId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password, firstName, lastName, email, roleId);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", roleId=" + roleId +
                ", isBlocked=" + isBlocked +
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

        public Builder withId(int id) {
            user.id = id;
            return this;
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

        public Builder withRoleId(int roleId) {
            user.roleId = roleId;
            return this;
        }

        public Builder withBlockedStatus(boolean isBlocked) {
            user.isBlocked = isBlocked;
            return this;
        }

        public User build() {
            return user;
        }
    }

}

