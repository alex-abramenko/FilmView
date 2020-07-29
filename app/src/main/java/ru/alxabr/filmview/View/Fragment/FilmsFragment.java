package ru.alxabr.filmview.View.Fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import ru.alxabr.filmview.Model.Wrapper.Film;
import ru.alxabr.filmview.Model.Wrapper.Genre;
import ru.alxabr.filmview.R;

public class FilmsFragment extends Fragment {
    public interface EventListenerFilmsFragment {
        void showGenreList();
        void showFilmList();
        void searchFilmByGenre(String genre);
        void hideBackButton();
        void toDetail(Film film);
    }

    private EventListenerFilmsFragment eventListener;
    private RecyclerView recyclerViewGenre;
    private ListAdapterGenre listAdapterGenre;
    private LinearLayoutManager genreLayoutManager;
    private RecyclerView recyclerViewFilm;
    private ListAdapterFilm listAdapterFilm;
    private GridLayoutManager filmLayoutManager;
    private Context mainContext;


    private int last_click_pos = -1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_films, null);
    }

    public void updateGenreList(ArrayList<Genre> genreList) {
        listAdapterGenre.updateData(genreList);
        recyclerViewGenre.getAdapter().notifyDataSetChanged();
    }

    public void updateFilmList(ArrayList<Film> filmList) {
        listAdapterFilm.updateData(filmList);
        recyclerViewFilm.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        eventListener = (EventListenerFilmsFragment) context;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        recyclerViewGenre = getActivity().findViewById(R.id.genre_list);
        listAdapterGenre = new ListAdapterGenre(getActivity(), new ArrayList<Genre>());
        genreLayoutManager = new LinearLayoutManager(getActivity());
        recyclerViewGenre.setAdapter(listAdapterGenre);
        recyclerViewGenre.setLayoutManager(genreLayoutManager);

        recyclerViewFilm = getActivity().findViewById(R.id.film_list);
        listAdapterFilm  = new ListAdapterFilm (getActivity(), new ArrayList<Film>());
        filmLayoutManager = new GridLayoutManager(getActivity(),2);
        recyclerViewFilm.setAdapter(listAdapterFilm);
        recyclerViewFilm.setLayoutManager(filmLayoutManager);

        mainContext = getActivity();

        eventListener.showFilmList();
        eventListener.showGenreList();
        eventListener.hideBackButton();
    }


    class ViewHolderGenre extends RecyclerView.ViewHolder {
        private TextView textView_name;

        ViewHolderGenre(View view){
            super(view);
            textView_name = view.findViewById(R.id.item_list_genre_name);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();

                    ArrayList<Genre> genreList = new ArrayList<>(listAdapterGenre.getData());
                    Genre genre = genreList.get(pos);

                    if (genre.isSelected()) {
                        genreList.get(pos).setSelected(false);
                        eventListener.showFilmList();
                    } else {
                        genreList.get(pos).setSelected(true);
                        eventListener.searchFilmByGenre(genre.getName());
                    }

                    if (last_click_pos != -1 && last_click_pos != pos)
                        genreList.get(last_click_pos).setSelected(false);

                    last_click_pos = pos;

                    updateGenreList(genreList);
                }
            });
        }
    }

    class ListAdapterGenre extends RecyclerView.Adapter<ViewHolderGenre> {
        private LayoutInflater inflater;
        private ArrayList<Genre> genreList;

        public ListAdapterGenre(Context context, ArrayList<Genre> genreList) {
            this.genreList = new ArrayList<>(genreList);
            this.inflater = LayoutInflater.from(context);
        }

        public void updateData(ArrayList<Genre> list) {
            genreList.clear();
            genreList.addAll(list);
        }

        public List<Genre> getData() {
            return genreList;
        }

        @Override
        public ViewHolderGenre onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.item_list_genre, parent, false);
            return new ViewHolderGenre(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolderGenre holder, final int position) {
            final Genre genre = genreList.get(position);

            holder.textView_name.setText(genre.getName());

            if(genre.isSelected())
                holder.textView_name.setBackgroundColor(Color.YELLOW);
            else
                holder.textView_name.setBackgroundColor(Color.WHITE);
        }

        @Override
        public int getItemCount() {
            return genreList.size();
        }
    }


    class ViewHolderFilm extends RecyclerView.ViewHolder {
        private TextView textView_name;
        private ImageView imageView;

        ViewHolderFilm(View view){
            super(view);
            textView_name = view.findViewById(R.id.item_list_film_name);
            imageView = view.findViewById(R.id.item_list_film_image);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    eventListener.toDetail(listAdapterFilm.getData().get(pos));
                }
            });
        }
    }

    class ListAdapterFilm extends RecyclerView.Adapter<ViewHolderFilm> {
        private LayoutInflater inflater;
        private ArrayList<Film> filmList;

        public ListAdapterFilm(Context context, ArrayList<Film> filmList) {
            this.filmList = new ArrayList<>(filmList);
            this.inflater = LayoutInflater.from(context);
        }

        public void updateData(ArrayList<Film> list) {
            filmList.clear();
            filmList.addAll(list);
        }

        public List<Film> getData() {
            return filmList;
        }

        @Override
        public ViewHolderFilm onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.item_list_film, parent, false);
            return new ViewHolderFilm(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolderFilm holder, final int position) {
            final Film film = filmList.get(position);

            holder.textView_name.setText(film.getLocalized_name());

            String mDrawableName = "non_image";
            int resID = mainContext.getResources().getIdentifier(mDrawableName ,
                    "drawable", mainContext.getPackageName());

            if(film.getImage_url() == null || film.getImage_url().equals(""))
                Picasso.get().load(resID).into(holder.imageView);
            else
                Picasso.get().load(film.getImage_url()).error(resID).into(holder.imageView);
        }

        @Override
        public int getItemCount() {
            return filmList.size();
        }
    }
}
