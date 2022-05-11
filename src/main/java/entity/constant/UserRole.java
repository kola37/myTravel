package entity.constant;


/**
 * User role entity enum
 *
 * @author Anatolii Koliaka
 */
public enum UserRole {
    USER(1), REGISTERED_USER(2), MANAGER(3), ADMIN(4);

    private int index;

    UserRole(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    /**
     * Returns the user role from UserRole enum by specified index
     *
     * @param index index of the user role to return
     * @return the user role specified by index
     */
    public UserRole getRole(int index) {
        return UserRole.values()[index - 1];
    }

}
