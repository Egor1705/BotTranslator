package pl.egor.Bot.TelegramTranslateBot;

import java.util.ArrayList;
import java.util.List;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.BotSession;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {
    	ReplyKeyboardMarkup keyBoardMarkup = new ReplyKeyboardMarkup();
	    List<KeyboardRow> keyboard = new ArrayList<>();
	    KeyboardRow kr = new KeyboardRow();
	    KeyboardButton k = new KeyboardButton();
	    k.setText("g");
	    KeyboardButton k1 = new KeyboardButton();
	    k1.setText("k");
	    kr.add(k1);
	    kr.add(k);
	    keyboard.add(kr);
	    keyBoardMarkup.setKeyboard(keyboard);
	try {
		Bot bot = new Bot();
		TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(bot);
        
	} catch (TelegramApiException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
}
//	Translate translate = TranslateOptions.newBuilder().setApiKey("AIzaSyD894lVRW4xnfNDDO0Egq40ejmNchRZYvs").build().getService();
//	Translation t = translate.translate("Hello", Translate.TranslateOption.targetLanguage("ru"),Translate.TranslateOption.sourceLanguage("en"));
//	System.out.println(t.getTranslatedText());
    }

}
