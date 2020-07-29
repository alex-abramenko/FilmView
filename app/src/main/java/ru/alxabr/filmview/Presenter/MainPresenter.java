package ru.alxabr.filmview.Presenter;

import android.os.AsyncTask;

import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import ru.alxabr.filmview.ContractMVP;
import ru.alxabr.filmview.Model.MainModel;
import ru.alxabr.filmview.Model.Wrapper.Film;
import ru.alxabr.filmview.Model.Wrapper.Genre;

public class MainPresenter implements ContractMVP.Presenter {
    private ContractMVP.View view;
    private ContractMVP.Model model;

    private ArrayList<Film> filmList;
    private ArrayList<Genre> genreList;

    public MainPresenter(ContractMVP.View view) {
        this.view = view;
        this.model = new MainModel();

        filmList = new ArrayList<>();
        genreList = new ArrayList<>();
    }

    @Override
    public void showFilmList() {
        view.showLoad();
        if(filmList.size() == 0)
            new TaskForLoad().execute();
        else {
            view.showFrameContent();
            view.updateFilmList(filmList);
        }

    }

    @Override
    public void showFilmListByGenre(String genre) {
        ArrayList<Film> filmListByGenre = model.searchFilmByGenre(filmList, genre);
        Collections.sort(filmListByGenre);
        view.updateFilmList(filmListByGenre);
    }

    @Override
    public void showGenreList() {
        for (int i = 0; i < genreList.size(); i++)
            genreList.get(i).setSelected(false);
        view.updateGenreList(genreList);
    }

    class TaskForLoad extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                filmList = model.getFilmList();
                return false;
            } catch (IOException | ParseException e) {
                e.printStackTrace();
                return true;
            }
        }

        @Override
        protected void onPostExecute(Boolean isError) {
            super.onPostExecute(isError);
            if(isError)
                view.showError();
            else {
                view.showFrameContent();
                Collections.sort(filmList);
                genreList = model.getAllGenres(filmList);
                view.updateGenreList(genreList);
                view.updateFilmList(filmList);
            }

        }
    }
}
