package com.exercises.alura.Screen.Music.model;

import jakarta.persistence.*;
import jdk.jfr.Category;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "artists")
public class Artist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String artistName;
    @Enumerated(EnumType.STRING)
    private Configuration artistConfiguration;
    @OneToMany(mappedBy = "artist", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Music> musics = new ArrayList<>();

    public Artist(){}

    public Artist(String artistName, Configuration artistConfiguration){
        this.artistName = artistName;
        this.artistConfiguration = Configuration.valueOf(String.valueOf(artistConfiguration));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public Configuration getArtistConfiguration() {
        return artistConfiguration;
    }

    public void setArtistConfiguration(Configuration artistConfiguration) {
        this.artistConfiguration = artistConfiguration;
    }

    public List<Music> getMusics() {
        return musics;
    }

    public void setMusics(List<Music> musics) {
        this.musics = musics;
    }

    @Override
    public String toString() {
        return "'" + artistName + '\'' +
                " (" + artistConfiguration + "), " +
                "Songs = " + musics;
    }
}
