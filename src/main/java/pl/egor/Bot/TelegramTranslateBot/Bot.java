package pl.egor.Bot.TelegramTranslateBot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
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
	//private String markupString = "ru;en;es;fr";
	private List<String> languages = new ArrayList<>();
	private final String BOT_TOKEN = "6837011716:AAEWOT9UdVAXf_CWSwHIA0SuDuilZm-fW7E";
	private final String BOT_NAME = "translateForMe1999Bot";

	@Override
	public void onUpdateReceived(Update update) {

	  
		// TODO Auto-generated method stub
		if(update.hasMessage()) {
			long chatId = update.getMessage().getChatId();
			String userText = update.getMessage().getText();
		
			switch(userText) {
			
			case "/start" :
				//sendMessage(chatId,"I will help you with translation, choose the language:");
				//parseLanguages(languages);
				
				try {
					//execute(createInlineKeyboard(update.getMessage().getChatId()));
                    execute(sendInlineKeyBoardMessage(update.getMessage().getChatId(),languages));
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
			break;
			

			
			case "/help" :
				sendMessage(chatId,"Just input a word");
				break;
				
			case "/reset":
				languageFrom = "";
				languageTo = "";
				sendMessage(chatId,"languages have been reset");
				
		    default :
		    	if(languageFrom=="") {
		    		languageFrom=detectLanguage(languages,userText);
		    	}
		    	else if(languageTo=="") {
		    		languageTo=detectLanguage(languages,userText);
		    	}
		    
		    	
		    	sendMessage(chatId,getTranslate(userText));
			}
		
		}
		 else if(update.hasCallbackQuery()){
    		 SendMessage s1 = new SendMessage();
    		 s1.setChatId(update.getCallbackQuery().getMessage().getChatId());
    		 s1.setText(update.getCallbackQuery().getData());
    		// System.out.println(s1.getText());
    		 if(languageFrom=="") {
		    		languageFrom=detectLanguage(languages,s1.getText());
		    	}
		    	else if(languageTo=="") {
		    		languageTo=detectLanguage(languages,s1.getText());
		    	}
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
	
	public String detectLanguage(List<String> languages,String textMessage) {
//		String [] array = markupString.split(";");
//		List<String> languages = Arrays.asList(array);
		
	System.out.println("in detect "+languages);
		
		if(languages.contains(textMessage)) {
		System.out.println(textMessage+" language has been defined");	
		}
		else {
			System.out.println("Cannot detect the language, try again");
			return "";
		
		}
		return textMessage;
	}
	public static SendMessage sendInlineKeyBoardMessage(long chatId,List<String> languages) {
	     InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
	  
		     System.out.println("in send "+languages);
//	     String [] array = markupString.split(";");
//			List<String> languages = Arrays.asList(array);
		//	System.out.println(languages);
			List<InlineKeyboardButton> buttons = new ArrayList<>();
			for(int i=0; i<languages.size();i++) {
				buttons.add(i, new InlineKeyboardButton());
				buttons.get(i).setText(languages.get(i));
				buttons.get(i).setCallbackData(languages.get(i));
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
	
	
//	public static SendMessage createInlineKeyboard(long chatId) {
//        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup(); // Create the Inline Keyboard Markup
//        List<List<InlineKeyboardButton>> inlineKeyboard = new ArrayList<>(); // Create the Inline Keyboard
//     
//        List<InlineKeyboardButton> keyboardButtonsSecondRow = new ArrayList<>();
//        List<InlineKeyboardButton> keyboardButtonsThirdRow = new ArrayList<>();
//
//        // Create buttons
//        
//        
//
//        InlineKeyboardButton ygoProDeck = new InlineKeyboardButton("YGOPRODECK");
//        ygoProDeck.setUrl("https://ygoprodeck.com/");
//
//        InlineKeyboardButton yugipedia = new InlineKeyboardButton("Yugipedia");
//        yugipedia.setUrl("https://yugipedia.com/wiki/Yugipedia");
//
//        InlineKeyboardButton cardmarket = new InlineKeyboardButton("Cardmarket");
//        cardmarket.setUrl("https://www.cardmarket.com/en");
//
//        InlineKeyboardButton tcgPlayer = new InlineKeyboardButton("TCGPlayer");
//        tcgPlayer.setUrl("https://www.tcgplayer.com/");
//
//        markupInline.setKeyboard(inlineKeyboard); // Add the keyboard to the markup
//
//        // Add the rows to the keyboard
//
//        inlineKeyboard.add(keyboardButtonsSecondRow);
//        inlineKeyboard.add(keyboardButtonsThirdRow);
//
//        // Add the buttons to the row
//        keyboardButtonsSecondRow.add(ygoProDeck);
//        keyboardButtonsSecondRow.add(yugipedia);
//        keyboardButtonsThirdRow.add(cardmarket);
//        keyboardButtonsThirdRow.add(tcgPlayer);
//
//        SendMessage s = new SendMessage();
//	     s.setChatId(chatId);
//	     s.setText("Hello, I will help you with translation, choose the language:");
//	     s.setReplyMarkup(markupInline);
//	     
//	     return s;
//        
//    }
	
	
	
	public List<String> parseLanguages(List<String> languages){
		Document doc;
		List<String> languageList = new ArrayList<>();
		try {
			doc = Jsoup.connect("https://cloud.google.com/translate/docs/languages")
					   .userAgent("Chrome/23.0.1271.95").referrer("http://www.google.com").get();
		 
		
			Elements rows = doc.select("table").select("tbody").select("tr").select("td");
			
			for (int i = 0;i<rows.size();i++) {			 
				if(i%2==0) {
					languageList.add(rows.get(i).text());
				}		
			}
			
			
			
			for(String s:languageList) {
				
				if(s.contains("Zulu")) {
					break;
				}
				languages.add(s);
			}

	
//	   languageList.stream().forEach(s -> {
//		   if(s.contains("Zulu")) {
//		   return;
//	   }
//	   newLanguageList.add(s);
//	   });
	   
	  
	   
	   System.out.println(languages);
	   
		}
	   catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return languages;
    	
	}
	
	

}
