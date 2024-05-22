package Service;

import DAO.AccountDAO;
import DAO.MessageDAO;
import Model.Account;
import Model.Message;

import java.util.List;

public class SocialMediaService {
    private AccountDAO accountDAO;
    private MessageDAO messageDAO;

    public SocialMediaService() {
        accountDAO = new AccountDAO();
        messageDAO = new MessageDAO();
    }

    public Account createAccount(Account account) {
        if (account.getUsername() == null || account.getUsername().isEmpty() ||
                account.getPassword() == null || account.getPassword().length() < 4) {
            throw new IllegalArgumentException("Yo Dude, Invalid account details");
        }

        Account existingAccount = accountDAO.getAccountByUsername(account.getUsername());
        if (existingAccount != null) {
            throw new IllegalArgumentException("Bro, Username already exists");
        }

        return accountDAO.createAccount(account);
    }

    public Account loginAccount(String username, String password) {
        Account account = accountDAO.getAccountByUsername(username);
        if (account != null && account.getPassword().equals(password)) {
            return account;
        }
        return null;
    }

    public Message createMessage(Message message) {
        if (message.getMessage_text() == null || message.getMessage_text().isEmpty() ||
                message.getMessage_text().length() > 255) {
            throw new IllegalArgumentException("Invalid message text, get with it");
        }

        Account account = accountDAO.getAccountById(message.getPosted_by());
        if (account == null) {
            throw new IllegalArgumentException("User not found, who are you looking for???");
        }

        return messageDAO.createMessage(message);
    }

    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    public Message getMessageById(int messageId) {
        return messageDAO.getMessageById(messageId);
    }

    public Message updateMessage(Message message) {
        // Perform validation and business logic
        if (message.getMessage_text() == null || message.getMessage_text().isEmpty() ||
                message.getMessage_text().length() > 255) {
            throw new IllegalArgumentException("Invalid message text, help me understand!");
        }

        Message existingMessage = messageDAO.getMessageById(message.getMessage_id());
        if (existingMessage == null) {
            throw new IllegalArgumentException("Message not found, i would like to find something for you though!");
        }

        existingMessage.setMessage_text(message.getMessage_text());
        return messageDAO.updateMessage(existingMessage);
    }

    public void deleteMessage(int messageId) {
        Message existingMessage = messageDAO.getMessageById(messageId);
        if (existingMessage != null) {
            messageDAO.deleteMessage(messageId);
        }
    }

    public List<Message> getMessagesByAccountId(int accountId) {
        return messageDAO.getMessagesByAccountId(accountId);
    }
}