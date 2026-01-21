package com.talentquery.model;

import com.talentquery.util.VectorConverter;
import jakarta.persistence.*;
import java.util.List;

@Entity
public class Candidate {

    public Candidate() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private int yearsOfExperience;

    @ElementCollection
    private List<String> skills;

    @Column(columnDefinition = "TEXT")
    private String bio;


    // This will be mapped to a pgvector column in the database
    @Convert(converter = VectorConverter.class)
    @Column(columnDefinition = "vector(1536)")
    private float[] embedding;

    // Use standard Getters and Setters to avoid Lombok compiler crashes
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public int getYearsOfExperience() { return yearsOfExperience; }
    public void setYearsOfExperience(int yearsOfExperience) { this.yearsOfExperience = yearsOfExperience; }

    public List<String> getSkills() { return skills; }
    public void setSkills(List<String> skills) { this.skills = skills; }

    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }

    public float[] getEmbedding() { return embedding; }
    public void setEmbedding(float[] embedding) { this.embedding = embedding; }
}
