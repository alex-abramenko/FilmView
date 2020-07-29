package ru.alxabr.filmview.View.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

import ru.alxabr.filmview.Model.Wrapper.Film;
import ru.alxabr.filmview.R;

public class DetailFragment extends Fragment {
    public interface EventListenerDetailFragment {
        void showContent();
    }

    private EventListenerDetailFragment eventListener;

    private ImageView image;
    private TextView name;
    private TextView year;
    private TextView rating;
    private TextView description;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_detail, null);
    }

    @Override
    public void onResume() {
        super.onResume();

        image = getActivity().findViewById(R.id.detail_image);
        name = getActivity().findViewById(R.id.detail_name);
        year = getActivity().findViewById(R.id.detail_year);
        rating = getActivity().findViewById(R.id.detail_rating);
        description = getActivity().findViewById(R.id.detail_desctiption);

        eventListener.showContent();
    }

    public void showDetail(Film film) {
        String mDrawableName = "non_image";
        int resID = getActivity().getResources().getIdentifier(mDrawableName ,
                "drawable", getActivity().getPackageName());
        if(film.getImage_url() == null || film.getImage_url().equals(""))
            Picasso.get().load(resID).into(image);
        else
            Picasso.get().load(film.getImage_url()).error(resID).into(image);

        name.setText(film.getName());
        year.setText(Integer.toString(film.getYear()));
        if(film.getRating() == 0f)
            rating.setText("-");
        else
            rating.setText(Double.toString(film.getRating()));
        description.setText(film.getDescription());
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        eventListener = (EventListenerDetailFragment) context;
    }
}
