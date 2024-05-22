package Controller;

import Model.Account;
import Model.Message;
import Service.SocialMediaService;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    private SocialMediaService socialMediaService;

    public SocialMediaController() {
        socialMediaService = new SocialMediaService();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler);

        app.post("/register", this::registerHandler);
        app.post("/login", this::loginHandler);

        app.post("/messages", this::createMessageHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageByIdHandler);
        app.patch("/messages/{message_id}", this::updateMessageHandler);
        app.delete("/messages/{message_id}", this::deleteMessageHandler);
        app.get("/accounts/{account_id}/messages", this::getMessagesByAccountIdHandler);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    private void registerHandler(Context context) {
        Account account = context.bodyAsClass(Account.class);
        try {
            Account createdAccount = socialMediaService.createAccount(account);
            context.json(createdAccount);
        } catch (IllegalArgumentException e) {
            context.status(400);
            context.result("");
        }
    }

    private void loginHandler(Context context) {
        Account account = context.bodyAsClass(Account.class);
        Account loggedInAccount = socialMediaService.loginAccount(account.getUsername(), account.getPassword());
        if (loggedInAccount != null) {
            context.json(loggedInAccount);
        } else {
            context.status(401);
        }
    }

    private void createMessageHandler(Context context) {
        Message message = context.bodyAsClass(Message.class);
        try {
            Message createdMessage = socialMediaService.createMessage(message);
            context.json(createdMessage);
        } catch (IllegalArgumentException e) {
            context.status(400);
            context.result("");
        }
    }

    private void getAllMessagesHandler(Context context) {
        context.json(socialMediaService.getAllMessages());
    }

    private void getMessageByIdHandler(Context context) {
        int messageId = Integer.parseInt(context.pathParam("message_id"));
        Message message = socialMediaService.getMessageById(messageId);
        if (message != null) {
            context.json(message);
        } else {
            context.json("");
        }
    }

    private void updateMessageHandler(Context context) {
        int messageId = Integer.parseInt(context.pathParam("message_id"));
        Message updatedMessage = context.bodyAsClass(Message.class);
        updatedMessage.setMessage_id(messageId);
        try {
            Message message = socialMediaService.updateMessage(updatedMessage);
            context.json(message);
        } catch (IllegalArgumentException e) {
            context.status(400);
        }
    }

    private void deleteMessageHandler(Context context) {
        int messageId = Integer.parseInt(context.pathParam("message_id"));
        Message existingMessage = socialMediaService.getMessageById(messageId);
        if (existingMessage != null) {
            socialMediaService.deleteMessage(messageId);
            context.json(existingMessage);
        } else {
            context.json("");
        }
    }

    private void getMessagesByAccountIdHandler(Context context) {
        int accountId = Integer.parseInt(context.pathParam("account_id"));
        context.json(socialMediaService.getMessagesByAccountId(accountId));
    }
}