package com.ruslanburduzhan.telegrambot.service;

import com.ruslanburduzhan.telegrambot.config.BotConfig;
import com.ruslanburduzhan.telegrambot.entity.User;
import com.ruslanburduzhan.telegrambot.model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class TelegramBotService extends TelegramLongPollingBot {

    @Autowired
    private UserRepository userRepository;
    final BotConfig botConfig;

    public TelegramBotService(BotConfig botConfig) {
        this.botConfig = botConfig;
        List<BotCommand> listOfCommands = new ArrayList<>();
        listOfCommands.add(new BotCommand("/start", "hello"));
        listOfCommands.add(new BotCommand("/showmyinfo", "hello"));
        try {
            this.execute(new SetMyCommands(listOfCommands, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            e.getStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return botConfig.getBotName();
    }

    @Override
    public String getBotToken() {
        return botConfig.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String msgText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            switch (msgText.toLowerCase()) {
                case "/start" -> {
                    registerUser(update.getMessage());
                    startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                }
                case "/showmyinfo" -> messageInfo(chatId);
                case "рита" -> {
                    sendMessage(chatId, "Курочка моя <3");
                }
                default -> sendMessage(chatId, "Sorry, open beta.");
            }
        }
    }

    private void registerUser(Message msg) {
        if (userRepository.findByChatId(msg.getChatId()).isEmpty()) {
            var chatId = msg.getChatId();
            var chat = msg.getChat();
            User user = new User(chatId, chat.getFirstName(), chat.getLastName(),
                    chat.getUserName(), new Timestamp(System.currentTimeMillis()));
            userRepository.save(user);
        }

    }

    private void startCommandReceived(long chatId, String name) {
//        String answer = "Hi, " + name + ", nice to meet you!";
        String answer = "Велком ту клаб, бадди.";
        sendMessage(chatId, answer);
    }

    private void sendMessage(long chatId, String textToSend) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(textToSend);
        try {
            execute(message);
        } catch (TelegramApiException e) {
        }
    }

    private void messageInfo(long chatId) {
        var user = userRepository.findByChatId(chatId).get();

        sendMessage(chatId, "chat_id: " + user.getChatId() +
                "\nfirst_name: " + user.getFirstName() +
                "\nlast_name: " + user.getLastName() +
                "\nregistered_at: " + user.getRegisteredAt() +
                "\nuser_name: " + user.getUserName());
    }
}
