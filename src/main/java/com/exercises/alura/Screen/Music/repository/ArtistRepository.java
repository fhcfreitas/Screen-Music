package com.exercises.alura.Screen.Music.repository;

import com.exercises.alura.Screen.Music.model.Artist;
import com.exercises.alura.Screen.Music.model.Music;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ArtistRepository extends JpaRepository<Artist, Long> {
    Optional<Artist> findByArtistNameContainingIgnoreCase(String artistName);
    @Query("SELECT m FROM Artist a JOIN a.musics m WHERE a.artistName ILIKE %:artistName%")
    List<Music> searchMusicByArtist (String artistName);
}
