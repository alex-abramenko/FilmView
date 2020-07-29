package ru.alxabr.filmview;

import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;

import ru.alxabr.filmview.Model.Wrapper.Film;
import ru.alxabr.filmview.Model.Wrapper.Genre;

public interface ContractMVP {
    interface Model {
        ArrayList<Film> getFilmList() throws IOException, ParseException;
        ArrayList<Genre> getAllGenres(ArrayList<Film> filmList);
        ArrayList<Film> searchFilmByGenre(ArrayList<Film> filmList, String genre);
    }

    interface Presenter {
        void showFilmList();
        void showFilmListByGenre(String genre);
        void showGenreList();
    }

    interface View {
        void showFrameContent();
        void showLoad();
        void showError();
        void updateGenreList(ArrayList<Genre> genreList);
        void updateFilmList(ArrayList<Film> filmList);
    }
}
