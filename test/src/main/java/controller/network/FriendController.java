package controller.network;

import client.Packet;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class FriendController {
    public static boolean sendRequest(String username) throws IOException {
        Packet packet = new Packet("send request","friend");
        packet.addAttribute("username",username);
        packet.sendPacket();

        Packet receivePacket = Packet.receivePacket();
        return !receivePacket.command.equals("failed");
    }
    public static void deleteFriend(String username) throws IOException {
        Packet packet = new Packet("delete friend","friend");
        packet.addAttribute("username",username);
        packet.sendPacket();
    }


    public static void rejectFriend(String username) throws IOException {
        Packet packet = new Packet("reject request","friend");
        packet.addAttribute("username",username);
        packet.sendPacket();
    }

    public static boolean acceptRequest(String username) throws IOException {
        Packet packet = new Packet("accept request","friend");
        packet.addAttribute("username",username);
        packet.sendPacket();

        Packet receivePacket = Packet.receivePacket();
        return !receivePacket.command.equals("failed");
    }
    public static ArrayList<String> getFriends() throws IOException {
        Packet packet = new Packet("get friends","friend");
        packet.sendPacket();

        Packet receivePacket = Packet.receivePacket();
        if (receivePacket.attributes.get("friends") == null){
            return new ArrayList<>();
        }
        return new Gson().fromJson(receivePacket.attributes.get("friends").toString(), new TypeToken<ArrayList<String>>(){}.getType());
    }
    public static HashMap<String,String> getRequests() throws IOException {
        Packet packet = new Packet("get requests","friend");
        packet.sendPacket();

        Packet receivePacket = Packet.receivePacket();
        if (receivePacket.attributes.get("requests") == null){
            return new HashMap<>();
        }
        return new Gson().fromJson(receivePacket.attributes.get("requests").toString(), new TypeToken<HashMap<String,String>>(){}.getType());
    }
    public static ArrayList<String> searchUser(String word) throws IOException {
        Packet packet = new Packet("search user","friend");
        packet.addAttribute("word",word);
        packet.sendPacket();

        Packet receivePacket = Packet.receivePacket();
        if (receivePacket.attributes.get("users") == null){
            return new ArrayList<>();
        }
        return new Gson().fromJson(receivePacket.attributes.get("users").toString(), new TypeToken<ArrayList<String>>(){}.getType());
    }

    public static boolean shouldUpdate() throws IOException {
        Packet packet = new Packet("update user","friend");
        packet.sendPacket();
        Packet receivePacket = Packet.receivePacket();
        return (boolean) receivePacket.attributes.get("update");
    }

    public static int getRequestsCount(HashMap<String,String> requests){
        return Collections.frequency(requests.values(),"requested");
    }
}
