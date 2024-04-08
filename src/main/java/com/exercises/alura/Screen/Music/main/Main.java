package com.exercises.alura.Screen.Music.main;

import com.exercises.alura.Screen.Music.model.Artist;
import com.exercises.alura.Screen.Music.model.Configuration;
import com.exercises.alura.Screen.Music.model.Music;
import com.exercises.alura.Screen.Music.repository.ArtistRepository;
import com.exercises.alura.Screen.Music.service.ChatGPT;
import jdk.jfr.Category;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    private ArtistRepository repository;
    private Scanner input = new Scanner(System.in);
    private List<Artist> artistList = new ArrayList<>();

    public Main(ArtistRepository repository) {
        this.repository = repository;
    }

    public void menu(){
        var option = -1;
        while (option != 0) {
            var menu = """
                    \nMenu
                    
                    1. Register Artist
                    2. Register Music
                    3. List of musics
                    4. Search music by Artist
                    5. Search information from Artist
                    
                    0. Log out
                    
                    Type an option:
                    """;

            System.out.println(menu);
            option = input.nextInt();
            input.nextLine();

            switch (option) {
                case 1:
                    registerArtist();
                    break;
                case 2:
                    registerMusic();
                    break;
                case 3:
                    listOfMusic();
                    break;
                case 4:
                    searchMusicByArtist();
                    break;
                case 5:
                    searchDataFromArtist();
                    break;
                case 0:
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid option");

            }
        }
    }

    private void listAllArtist() {
        List<Artist> artists = repository.findAll();
        artists.forEach(System.out::println);
    }
    private void registerArtist() {
        var registerNewArtists = "Y";

        while (registerNewArtists.equalsIgnoreCase("y")) {
            System.out.println("Type the Artist's name:");
            var artistName = input.nextLine();
            System.out.println("Band, Duo, Group or Solo?");
            var artistConfiguration = input.nextLine();
            Configuration configuration = Configuration.valueOf(artistConfiguration.toUpperCase());
            Artist artist = new Artist(artistName, configuration);
            repository.save(artist);

            System.out.println("Do you want to register another artist? (y/n)");
            registerNewArtists = input.nextLine();
        }
    }

    private void registerMusic() {
        var registerMusic = "Y";

        while (registerMusic.equalsIgnoreCase("y")){
            System.out.println("Artist of the music");
            var artistName = input.nextLine();
            Optional<Artist> artist = repository.findByArtistNameContainingIgnoreCase(artistName);
            if (artist.isPresent()) {
                System.out.println("Type the name of the music:");
                var musicName = input.nextLine();
                Music music = new Music(musicName);
                music.setArtist(artist.get());
                artist.get().getMusics().add(music);
                repository.save(artist.get());

                System.out.println("Do you want to register another artist? (y/n)");
                registerMusic = input.nextLine();
            } else {
                System.out.println("Artist not found.");
            }
        }
    }

    private void listOfMusic(){
        List<Artist> artists = repository.findAll();
        artists.forEach(a -> a.getMusics().forEach(System.out::println));
    }

    private void searchMusicByArtist() {
        listAllArtist();
        var searchArtist = "Y";

        while (searchArtist.equalsIgnoreCase("y")){
            System.out.println("Which artist do you want to listen?");
            var artistName = input.nextLine();
            List<Music> musics = repository.searchMusicByArtist(artistName);
            musics.forEach(System.out::println);

            System.out.println("Do you want to search another one?");
            searchArtist = input.nextLine();
        }
    }

    private void searchDataFromArtist() {
        System.out.println("Which artist do you want to search");
        var artistName = input.nextLine();
        var response = ChatGPT.obtainData(artistName);
        System.out.println(response.trim());
    }

}
