package pack;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.chrono.ChronoPeriod;
import java.time.temporal.ChronoUnit;

public class Main {
    public static void main(String[] args) {
      //  System.out.println(new Time(new Date().getTime()));
/*
        System.out.println(LocalDate.now().plusDays(125));
        System.out.println(LocalTime.now());

        LocalDate of = LocalDate.of(1574, 12, 25);
        System.out.println(of);
        LocalDate now = LocalDate.now();
        System.out.println(now.minusYears(of.getYear()));

        System.out.println(now.minus(of.getYear(), ChronoUnit.YEARS));
        */

        User user = new User("Artem",35, LocalDateTime.now());

        Gson gson = new GsonBuilder().registerTypeAdapter(
               User.class, new TypeAdapter<User>(){

                    @Override
                    public void write(JsonWriter jsonWriter, User u) throws IOException {
                        jsonWriter.beginObject();
                        for (Field f : u.getClass().getDeclaredFields()) {
                            f.setAccessible(true);
                            try {
                                jsonWriter.name(f.getName()).value(f.get(u).toString());
                            } catch (IllegalAccessException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        jsonWriter.endObject();
                    }

                    @Override
                    public User read(JsonReader jsonReader) throws IOException {
                        User user= new User();
                        jsonReader.beginObject();
                        while (jsonReader.hasNext()){
                            System.out.println(jsonReader.nextName());
                            System.out.println(jsonReader.nextString());
                        }
                        jsonReader.endObject();
                        return user;
                    }


                }
        ).create();
        String json = gson.toJson(user);
        User u2 = gson.fromJson(json, User.class);
        System.out.println(u2);

    }
}