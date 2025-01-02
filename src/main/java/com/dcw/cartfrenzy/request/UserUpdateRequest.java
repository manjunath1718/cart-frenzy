package com.dcw.cartfrenzy.request;

import com.dcw.cartfrenzy.model.Role;

import java.util.Collection;
import java.util.HashSet;

public class UserUpdateRequest {

    private String firstName;
    private String lastName;
    private Collection<Role> roles = new HashSet<>();

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

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }
}
