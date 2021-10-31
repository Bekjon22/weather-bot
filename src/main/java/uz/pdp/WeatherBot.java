package uz.pdp;

import com.google.gson.Gson;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class WeatherBot extends TelegramLongPollingBot {
    String city = null;

    @Override
    public void onUpdateReceived(Update update) {
        SendMessage sendMessage = new SendMessage();
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> list = new ArrayList<>();
        List<InlineKeyboardButton> buttonList1 = new ArrayList<>();
        List<InlineKeyboardButton> buttonList2 = new ArrayList<>();
        List<InlineKeyboardButton> buttonList3 = new ArrayList<>();
        List<InlineKeyboardButton> buttonList4 = new ArrayList<>();
        List<InlineKeyboardButton> buttonList5 = new ArrayList<>();
        List<InlineKeyboardButton> buttonList6 = new ArrayList<>();
        List<InlineKeyboardButton> buttonList7 = new ArrayList<>();


        if (update.hasCallbackQuery()) {
//            String callbackText = update.getCallbackQuery().getData();
            try {
                sendMessage.setChatId(update.getCallbackQuery().getMessage().getChatId());
                city = update.getCallbackQuery().getData();



                Gson gson = new Gson();
                URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=be4c5331a79136ca700df931fb0b98d5");
                URLConnection connection = url.openConnection();
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                Response response = gson.fromJson(reader, Response.class);
                sendMessage.setText("<b>" + " Last update  " + " \uD83D\uDD70" + LocalTime.ofSecondOfDay((response.getDt() + response.getTimezone()) % 86400)+"</b>");
                sendMessage.setParseMode("HTML");
                execute(sendMessage);
                String skyView = "";

                for (WeatherItem weatherItem : response.getWeather()) {
                    skyView = weatherItem.getDescription();

                }


                sendMessage.setText("Temp : " + Math.round(((response.getMain().getTemp() - 273.15) * 100) / 100.0) + "°C\n" +
                        "---------------------------------------\n"+
                        "Real feel : " + Math.round(((response.getMain().getFeelsLike() - 273.15) * 100) / 100.0) + "°C\n" +
                        "---------------------------------------\n"+
                        "Sunrise : " + LocalTime.ofSecondOfDay((response.getSys().getSunrise() + response.getTimezone()) % 86400) + "\n" +
                        "---------------------------------------\n"+
                        "Sunset : " + LocalTime.ofSecondOfDay((response.getSys().getSunset() + response.getTimezone()) % 86400) + "\n" +
                        "---------------------------------------\n"+
                        "Humidity : " + response.getMain().getHumidity() + "\n" +
                        "---------------------------------------\n"+
                        "Sky View : " + skyView + "\n"+
                        "---------------------------------------\n"+
                        "Wind Speed : "+response.getWind().getSpeed()+" m/s\n"+
                        "---------------------------------------\n"+
                        "Temp Max : "+Math.round(((response.getMain().getTempMax() - 273.15) * 100) / 100.0) + "°C\n" +
                        "---------------------------------------\n"+
                        "Temp Min : "+Math.round(((response.getMain().getTempMin() - 273.15) * 100) / 100.0) + "°C\n" +
                        "---------------------------------------\n"

                );


                execute(sendMessage);



            } catch (IOException | TelegramApiException e) {
                e.printStackTrace();
            }


        } else if (update.hasMessage()) {
            String inputText = update.getMessage().getText();

            if (inputText.equals("/start")) {
                String str = update.getMessage().getFrom().getFirstName();
                sendMessage.setText("<b>Assalomu alaykum " + str + "</b>");
                sendMessage.setParseMode("HTML");
                ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
                sendMessage.setReplyMarkup(keyboardMarkup);

                keyboardMarkup.setResizeKeyboard(true);
                keyboardMarkup.setOneTimeKeyboard(true);
                keyboardMarkup.setSelective(true);

                List<KeyboardRow> keyboard = new ArrayList<>();
                KeyboardRow row1 = new KeyboardRow();
                KeyboardRow row2 = new KeyboardRow();
                row1.add(new KeyboardButton("Weather in Uzbekistan"));
                row1.add(new KeyboardButton("2"));


                keyboard.add(row1);

                keyboardMarkup.setKeyboard(keyboard);

                sendMessage.setChatId(update.getMessage().getChatId());


                try {
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }


            } else if (inputText.equals("Weather in Uzbekistan")) {
                sendMessage.setChatId(update.getMessage().getChatId());
                sendMessage.setText("Choose your city \uD83D\uDC47 ");

                InlineKeyboardButton and = new InlineKeyboardButton("Andijon").setCallbackData("Andijan");
                InlineKeyboardButton bux = new InlineKeyboardButton("Buxoro").setCallbackData("Bukhara");
                InlineKeyboardButton jix = new InlineKeyboardButton("Jizzax").setCallbackData("Jizzakh");
                InlineKeyboardButton far = new InlineKeyboardButton("Farg'ona").setCallbackData("Fergana");
                InlineKeyboardButton qor = new InlineKeyboardButton("Qoraqalpog'iston Res").setCallbackData("Karakalpakstan");
                InlineKeyboardButton qas = new InlineKeyboardButton("Qashqadaryo").setCallbackData("Qashqadaryo");
                InlineKeyboardButton xor = new InlineKeyboardButton("Xorazm").setCallbackData("Urgench");
                InlineKeyboardButton nam = new InlineKeyboardButton("Namangan").setCallbackData("Namangan");
                InlineKeyboardButton nav = new InlineKeyboardButton("Navoi").setCallbackData("Navoiy");
                InlineKeyboardButton sam = new InlineKeyboardButton("Samarqand").setCallbackData("Samarkand");
                InlineKeyboardButton sur = new InlineKeyboardButton("Surxandaryo").setCallbackData("Nukus");
                InlineKeyboardButton sir = new InlineKeyboardButton("Sirdaryo").setCallbackData("Sirdaryo");
                InlineKeyboardButton tos = new InlineKeyboardButton("Tashkent").setCallbackData("Tashkent");

                buttonList1.add(and);
                buttonList1.add(bux);
                buttonList2.add(jix);
                buttonList2.add(far);
                buttonList3.add(qor);
                buttonList3.add(qas);
                buttonList4.add(xor);
                buttonList4.add(nam);
                buttonList5.add(nav);
                buttonList5.add(sam);
                buttonList6.add(sur);
                buttonList6.add(sir);
                buttonList7.add(tos);

                list.add(buttonList1);
                list.add(buttonList2);
                list.add(buttonList3);
                list.add(buttonList4);
                list.add(buttonList5);
                list.add(buttonList6);
                list.add(buttonList7);

                inlineKeyboardMarkup.setKeyboard(list);
                sendMessage.setReplyMarkup(inlineKeyboardMarkup);
                try {
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }


            }

        }

    }


    public void getWeather(SendMessage sendMessage, Update update, String cityName) {

        try {
            Gson gson = new Gson();
            URL url = new URL("api.openweathermap.org/data/2.5/weather?q=" + cityName + "&appid=be4c5331a79136ca700df931fb0b98d5");
            URLConnection connection = url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            Response response = gson.fromJson(reader, Response.class);


//            sendMessage.setText(String.valueOf(response.getMain().getTemp()));
            sendMessage.setText(response.getName());
            sendMessage.setChatId(update.getMessage().getChatId());
            execute(sendMessage);


            reader.close();


        } catch (IOException | TelegramApiException e) {
            e.printStackTrace();
        }

    }

    @Override
    public String getBotUsername() {
        return "DailyWeatherUzbekistanBot";
    }

    @Override
    public String getBotToken() {
        return "1775363175:AAH_tfF71wKK9gncWjPuldOxuONq9RAQJDM";
    }
}
