package com.revature.dtos;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.AppUser;
import com.revature.models.AppUser;

import java.util.Objects;

public class Principal {

    private int id;
    private String userame;
    private String role;

    public Principal() {

    }

    public Principal(AppUser user) {
        this.id = user.getId();
        this.userame = user.getUsername();
        this.role = user.getRole().toString();

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserame() {
        return userame;
    }

    public void setUserame(String userame) {
        this.userame = userame;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


    public String stringify() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Principal principal = (Principal) o;
        return id == principal.id &&
                Objects.equals(userame, principal.userame) &&
                Objects.equals(role, principal.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userame, role);
    }

    @Override
    public String toString() {
        return "Principal{" +
                "id=" + id +
                ", userame='" + userame + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
