package uz.pdp;

import com.google.gson.Gson;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Main2 {
    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi api = new TelegramBotsApi();
        try {
            api.registerBot(new WeatherBot());
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }



//        URL url = new URL("api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=be4c5331a79136ca700df931fb0b98d5");


//        String city ="Tashkent";
//
//        try {
//
//            Gson gson = new Gson();
//            URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=Sirdaryo&appid=be4c5331a79136ca700df931fb0b98d5");
//            URLConnection connection = url.openConnection();
//            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//            Response response = gson.fromJson(reader, Response.class);
//            for (WeatherItem weatherItem : response.getWeather()) {
//                System.out.println(weatherItem.getMain());
//            }
//
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


    }

}
