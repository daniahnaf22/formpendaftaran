package com.daniahnaf.formpendaftan.model;

public class Data {
    private String id, name, address, nohp, jk;

    public Data() {
    }

    public Data(String id, String name, String address, String nohp, String jk) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.nohp = nohp;
        this.jk = jk;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNohp() {
        return nohp;
    }

    public void setNohp(String nohp) {
        this.nohp = nohp;
    }

    public String getJk() {
        return jk;
    }

    public void setJk(String jk) {
        this.jk = jk;
    }
}
