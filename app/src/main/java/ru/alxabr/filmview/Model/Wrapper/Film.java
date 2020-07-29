package ru.alxabr.filmview.Model.Wrapper;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect
public class Film implements Comparable<Film> {
    private int id;
    private String localized_name;
    private String name;
    private int year;
    private double rating;
    private String image_url;
    private String description;
    private String[] genres;

    public Film(int id, String localized_name, String name, int year, double rating, String image_url, String description, String[] genres) {
        this.id = id;
        this.localized_name = localized_name;
        this.name = name;
        this.year = year;
        this.rating = rating;
        this.image_url = image_url;
        this.description = description;
        this.genres = genres;
    }

    public Film() {
        id = 0;
        localized_name = "";
        name = "";
        year = 0;
        rating = 0;
        image_url = "";
        description = "";
        genres = new String[0];
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocalized_name() {
        return localized_name;
    }

    public void setLocalized_name(String localized_name) {
        this.localized_name = localized_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String[] getGenres() {
        return genres;
    }

    public void setGenres(String[] genres) {
        this.genres = genres;
    }

    @Override
    public int compareTo(Film o) {
        return this.localized_name.compareTo(o.localized_name);
    }
}
