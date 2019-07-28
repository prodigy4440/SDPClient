/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fahdisa.sdpclient.model;

import java.io.Serializable;

/**
 * @author prodigy4440
 */
public class Status<E> implements Serializable {

    private Boolean status;

    private String description;

    private E data;

    public Status(Boolean status, String description) {
        this.status = status;
        this.description = description;
    }

    public Status(Boolean status, String description, E data) {
        this.status = status;
        this.description = description;
        this.data = data;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Status{" +
                "status=" + status +
                ", description='" + description + '\'' +
                ", data=" + data +
                '}';
    }
}
