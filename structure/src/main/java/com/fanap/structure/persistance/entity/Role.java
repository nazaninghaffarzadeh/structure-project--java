package com.fanap.structure.persistance.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Role")
public class Role implements Serializable{
    private Long id;
    private String title;
    private String position;

    @Id
    @Column(name ="ID",nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Column(name="Title",nullable = false,unique = true)
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    @Column(name = "Position",nullable = false,unique = true)
    public String getPosition() {
        return position;
    }
    public void setPosition(String position) {
        this.position = position;
    }
    

    

}
