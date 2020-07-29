package ru.alxabr.filmview.Model;

import ru.alxabr.filmview.ContractMVP;
import ru.alxabr.filmview.Model.Json.JsonHelper;
import ru.alxabr.filmview.Model.Network.GetDataAPI;
import ru.alxabr.filmview.Model.Wrapper.Genre;
import ru.alxabr.filmview.Model.Wrapper.Film;

import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class MainModel implements ContractMVP.Model {
    @Override
    public ArrayList<Film> getFilmList() throws IOException, ParseException {
        String url = "https://s3-eu-west-1.amazonaws.com/sequeniatesttask/films.json";
        String json = new GetDataAPI().getHTML(url);
        return new JsonHelper().getFilmList(json);
    }

    @Override
    public ArrayList<Genre> getAllGenres(ArrayList<Film> filmList) {
        ArrayList<String> genresListStr = new ArrayList<>();
        for (Film film : filmList) {
            String[] strings = film.getGenres();
            Collections.addAll(genresListStr, strings);
        }

        Set<String> set = new HashSet<>(genresListStr);
        genresListStr.clear();
        genresListStr.addAll(set);

        ArrayList<Genre> genresList = new ArrayList<>();
        for (String s : genresListStr)
            genresList.add(new Genre(s, false));

        return genresList;
    }

    @Override
    public ArrayList<Film> searchFilmByGenre(ArrayList<Film> filmList, String genre) {
        ArrayList<Film> filmListByGenre = new ArrayList<>();
        for (Film o : filmList) {
            String[] strings = o.getGenres();
            for (String string : strings) {
                if (string.equals(genre)) {
                    filmListByGenre.add(o);
                    break;
                }
            }
        }
        return filmListByGenre;
    }
}
