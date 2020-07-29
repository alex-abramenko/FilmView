package ru.alxabr.filmview.Model.Json;

import ru.alxabr.filmview.Model.Wrapper.Film;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

public class JsonHelper {

    public ArrayList<Film> getFilmList(String json) throws IOException, ParseException {
        StringReader reader = new StringReader(json);
        JSONParser jsonParser = new JSONParser();
        ObjectMapper mapper = new ObjectMapper();

        JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
        JSONArray jsonArray = (JSONArray) jsonObject.get("films");

        ArrayList<Film> filmList = new ArrayList<>();

        for (Object o : jsonArray) {
            reader = new StringReader(o.toString());
            Film film = mapper.readValue(reader, Film.class);
            filmList.add(film);
        }

        return filmList;
    }
}
