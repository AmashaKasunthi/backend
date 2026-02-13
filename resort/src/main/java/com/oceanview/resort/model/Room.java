package com.oceanview.resort.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roomType;
    private double price;
    private int totalRooms;
    private int availableRooms;
    private String image;
    @Column(length = 1000)  // ADD THIS - allows up to 1000 characters
    private String description;
}
