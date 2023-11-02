package pl.egor.Bot.TelegramTranslateBot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;

public class Bot extends TelegramLongPollingBot{
	
	private String languageFrom= "";
	private String languageTo= "";
	private final String keyAPI = "AIzaSyD894lVRW4xnfNDDO0Egq40ejmNchRZYvs";
	private String markupString = "ru;en;es;fr";
	private final String BOT_TOKEN = "6837011716:AAEWOT9UdVAXf_CWSwHIA0SuDuilZm-fW7E";
	private final String BOT_NAME = "translateForMe1999Bot";

	@Override
	public void onUpdateReceived(Update update) {
//		ReplyKeyboardMarkup keyBoardMarkup = new ReplyKeyboardMarkup();
//	    List<KeyboardRow> keyboard = new ArrayList<>();
//	    KeyboardRow kr = new KeyboardRow();
//	    KeyboardButton k = new KeyboardButton();
//	    k.setText("g");
//	    KeyboardButton k1 = new KeyboardButton();
//	    k1.setText("k");
//	    kr.add(k1);
//	    kr.add(k);
//	    keyboard.add(kr);
//	    keyBoardMarkup.setKeyboard(keyboard);
//	    SendMessage s = new SendMessage();
//	    s.setReplyMarkup(keyBoardMarkup);
	  
		// TODO Auto-generated method stub
		if(update.hasMessage()) {
			long chatId = update.getMessage().getChatId();
			String userText = update.getMessage().getText();
		
			switch(userText) {
			
			case "/start" :
			//	sendMessage(chatId,"I will help you with translation, choose the language:");
				try {
                    execute(sendInlineKeyBoardMessage(update.getMessage().getChatId(),markupString));
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
			break;
			
			
			case "/help" :
				sendMessage(chatId,"Just input a word");
				break;
				
		    default :
		    	if(languageFrom=="") {
		    		languageFrom=detectLanguage(markupString,userText);
		    	}
		    	else if(languageTo=="") {
		    		languageTo=detectLanguage(markupString,userText);
		    	}
		    
		    	
		    	sendMessage(chatId,getTranslate(userText));
			}
		
		}
		 else if(update.hasCallbackQuery()){
    		 SendMessage s1 = new SendMessage();
    		 s1.setChatId(update.getCallbackQuery().getMessage().getChatId());
    		 s1.setText(update.getCallbackQuery().getData());
             try {
                 execute(s1);

             } catch (TelegramApiException e) {
                 e.printStackTrace();
             }
         }
	}

	@Override
	public String getBotUsername() {
		// TODO Auto-generated method stub
		return BOT_NAME;
	}

	@Override
	public String getBotToken() {
		// TODO Auto-generated method stub
		return BOT_TOKEN;
	}
	
	public void sendMessage(Long chatId, String textMessage) {
		SendMessage sendMessage = new SendMessage();
		sendMessage.setChatId(chatId);
		sendMessage.setText(textMessage);
		
		try {
			executeAsync(sendMessage);
		} catch (TelegramApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getTranslate(String textMessage) {
		Translate translate = TranslateOptions.newBuilder().setApiKey(keyAPI).build().getService();
		Translation t = translate.translate(textMessage, 
				Translate.TranslateOption.sourceLanguage(languageFrom),
				Translate.TranslateOption.targetLanguage(languageTo)
				);
		return t.getTranslatedText();
	}
	
	public String detectLanguage(String markupString,String textMessage) {
		String [] array = markupString.split(";");
		List<String> languages = Arrays.asList(array);
		
	
		
		if(languages.contains(textMessage)) {
		System.out.println(textMessage+" language has been defined");	
		}
		else {
			System.out.println("Cannot detect the language, try again");
			return null;
		
		}
		return textMessage;
	}
	public static SendMessage sendInlineKeyBoardMessage(long chatId,String markupString) {
	     InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
	  
		     
	     String [] array = markupString.split(";");
			List<String> languages = Arrays.asList(array);
			List<InlineKeyboardButton> buttons = new ArrayList<>();
			for(int i=0; i<languages.size();i++) {
				buttons.add(i, new InlineKeyboardButton());
				buttons.get(i).setText(languages.get(i));
				buttons.get(i).setCallbackData("the language "+languages.get(i) +" has been chosen");
			}
	     



	    
	     List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
	     rowList.add(buttons);

	     inlineKeyboardMarkup.setKeyboard(rowList);
	     SendMessage s = new SendMessage();
	     s.setChatId(chatId);
	     s.setText("I will help you with translation, choose the language:");
	     s.setReplyMarkup(inlineKeyboardMarkup);
	     return s;
	    }
	
	
	

}
