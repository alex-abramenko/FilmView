package ru.alxabr.filmview.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.util.ArrayList;

import ru.alxabr.filmview.ContractMVP;
import ru.alxabr.filmview.Model.Wrapper.Film;
import ru.alxabr.filmview.Model.Wrapper.Genre;
import ru.alxabr.filmview.Presenter.MainPresenter;
import ru.alxabr.filmview.R;
import ru.alxabr.filmview.View.Fragment.DetailFragment;
import ru.alxabr.filmview.View.Fragment.FilmsFragment;

public class MainActivity extends AppCompatActivity implements ContractMVP.View,
        FilmsFragment.EventListenerFilmsFragment, DetailFragment.EventListenerDetailFragment {
    private LinearLayout layout_load;
    private LinearLayout layout_error;
    private FrameLayout frame_content;

    private ContractMVP.Presenter presenter;
    private FragmentTransaction fragmentTransaction;
    private Fragment films_fragment;
    private Fragment detail_fragment;
    private Film film;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layout_load = findViewById(R.id.layout_load);
        layout_error = findViewById(R.id.layout_error);
        frame_content = findViewById(R.id.frame_content);

        presenter = new MainPresenter(this);

        films_fragment = new FilmsFragment();
        detail_fragment = new DetailFragment();

        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.frame_content, films_fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void showLoad() {
        layout_load.setVisibility(View.VISIBLE);
        layout_error.setVisibility(View.GONE);
        frame_content.setVisibility(View.GONE);
    }

    @Override
    public void showError() {
        layout_load.setVisibility(View.GONE);
        layout_error.setVisibility(View.VISIBLE);
        frame_content.setVisibility(View.GONE);
    }

    @Override
    public void showFrameContent() {
        layout_load.setVisibility(View.GONE);
        layout_error.setVisibility(View.GONE);
        frame_content.setVisibility(View.VISIBLE);
    }

    @Override
    public void updateFilmList(ArrayList<Film> filmList) {
        ((FilmsFragment) films_fragment).updateFilmList(filmList);
    }

    @Override
    public void updateGenreList(ArrayList<Genre> genreList) {
        ((FilmsFragment) films_fragment).updateGenreList(genreList);
    }

    @Override
    public void showGenreList() {
        presenter.showGenreList();
    }

    @Override
    public void showFilmList() {
        presenter.showFilmList();
    }

    @Override
    public void searchFilmByGenre(String genre) {
        presenter.showFilmListByGenre(genre);
    }

    @Override
    public void hideBackButton() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setTitle("Фильмы");
    }

    @Override
    public void toDetail(Film film) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(film.getLocalized_name());

        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_content, detail_fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        this.film = film;
    }

    @Override
    public void showContent() {
        if (film != null)
            ((DetailFragment) detail_fragment).showDetail(film);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame_content, films_fragment);
                fragmentTransaction.commit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
