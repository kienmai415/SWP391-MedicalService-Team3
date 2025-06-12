/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author laptop368
 */
public class Specialization {

    private int id;
    private String name;
    private String description;

    public Specialization() {
    }

    public Specialization(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Specialization(int id, String name) {
        this.id = id;
        this.name = name;
    }

    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Specialization{" + "id=" + id + ", name=" + name + ", description=" + description + '}';
    }
    


}
