package com.softwareengineerandroid.davidmata.model.user;

/**
 * Created by davidmata on 19/10/2016.
 */

public class User {
    private int id;
    private String user;
    private String password;
    private int admin;// //1 for admin, 0 for low level permission

    /**
     *
     * @param id identified number related with the user
     * @param user name of the account, it have to be a email address
     * @param password any character password
     * @param admin permision for the user 1 for admin, 0 for low level permission user.
     */
    public User(int id, String user, String password, int admin){
        this.id=id;
        this.user=user;
        this.password=password;
        this.admin=admin;

    }

    public int getId() {
        return id;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public int getAdmin() {
        return admin;
    }
}
