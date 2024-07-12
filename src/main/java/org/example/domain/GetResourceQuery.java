package org.example.domain;

public class GetResourceQuery {
    private String resource;

    public GetResourceQuery(String resource) {
        this.resource = resource;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }
}
