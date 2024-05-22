package DAO;

import Model.Message;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO {
    public Message createMessage(Message message) {
        Connection connection = null;
        try {
            connection = ConnectionUtil.getConnection();
            String sql = "INSERT INTO Message (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, message.getPosted_by());
            statement.setString(2, message.getMessage_text());
            statement.setLong(3, message.getTime_posted_epoch());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                message.setMessage_id(resultSet.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return message;
    }

    public List<Message> getAllMessages() {
        List<Message> messages = new ArrayList<>();
        Connection connection = null;
        try {
            connection = ConnectionUtil.getConnection();
            String sql = "SELECT * FROM Message";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Message message = new Message();
                message.setMessage_id(resultSet.getInt("message_id"));
                message.setPosted_by(resultSet.getInt("posted_by"));
                message.setMessage_text(resultSet.getString("message_text"));
                message.setTime_posted_epoch(resultSet.getLong("time_posted_epoch"));
                messages.add(message);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return messages;
    }

    public Message getMessageById(int messageId) {
        Message message = null;
        Connection connection = null;
        try {
            connection = ConnectionUtil.getConnection();
            String sql = "SELECT * FROM Message WHERE message_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, messageId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                message = new Message();
                message.setMessage_id(resultSet.getInt("message_id"));
                message.setPosted_by(resultSet.getInt("posted_by"));
                message.setMessage_text(resultSet.getString("message_text"));
                message.setTime_posted_epoch(resultSet.getLong("time_posted_epoch"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return message;
    }

    public void deleteMessage(int messageId) {
        Connection connection = null;
        try {
            connection = ConnectionUtil.getConnection();
            String sql = "DELETE FROM Message WHERE message_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, messageId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Message updateMessage(Message message) {
        Connection connection = null;
        try {
            connection = ConnectionUtil.getConnection();
            String sql = "UPDATE Message SET message_text = ? WHERE message_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, message.getMessage_text());
            statement.setInt(2, message.getMessage_id());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return message;
    }

    public List<Message> getMessagesByAccountId(int accountId) {
        List<Message> messages = new ArrayList<>();
        Connection connection = null;
        try {
            connection = ConnectionUtil.getConnection();
            String sql = "SELECT * FROM Message WHERE posted_by = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, accountId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Message message = new Message();
                message.setMessage_id(resultSet.getInt("message_id"));
                message.setPosted_by(resultSet.getInt("posted_by"));
                message.setMessage_text(resultSet.getString("message_text"));
                message.setTime_posted_epoch(resultSet.getLong("time_posted_epoch"));
                messages.add(message);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return messages;
    }
}