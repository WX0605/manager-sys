package org.example.repository;

import java.util.Objects;

public class ResourcePo {
    private Long userId;
    private String resource;

    public ResourcePo(Long userId, String resource) {
        this.userId = userId;
        this.resource = resource;
    }

    public Long getuserId() {
        return userId;
    }

    public void setuserId(Long userId) {
        userId = userId;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        resource = resource;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResourcePo that = (ResourcePo) o;
        return Objects.equals(userId, that.userId) && Objects.equals(resource, that.resource);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, resource);
    }
}
