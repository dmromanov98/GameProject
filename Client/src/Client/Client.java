package Client;

import java.io.IOException;
import java.net.*;

public class Client extends Thread {
    private DatagramSocket Socket; //сокет
    private DatagramPacket ReceivePacket; //пакет принятия данных
    private DatagramPacket SendPacket; //пакет принятия данных
    private InetAddress inetAddress;

    private static String IP = "127.0.0.1";
    private static int PORT = 9879;
    private byte[] ReceivedData;
    private byte[] SendData;

    private static int TIMEOUT = 15000;
    private final int RECEIVESIZE = 256; //количество байт

    //временно
    private static String Nickname = "Objects";

    public static int getTIMEOUT() {
        return TIMEOUT;
    }

    public static void setTIMEOUT(int TIMEOUT) {
        Client.TIMEOUT = TIMEOUT;
    }

    public static String getNickname() {
        return Nickname;
    }

    public static void setNickname(String nickname) {
        Nickname = nickname;
    }

    public static String getIP() {
        return IP;
    }

    public static void setIP(String IP) {
        Client.IP = IP;
    }

    public static Integer getPORT() {
        return PORT;
    }

    public static void setPORT(Integer PORT) {
        Client.PORT = PORT;
    }

    @Override
    public void run() {
        try {
            System.out.println("Creating socket...");

            Socket = new DatagramSocket();
            inetAddress = InetAddress.getByName(IP);

            System.out.println("Socket created successful\n" +
                    "Waiting messages from server");

            ReceivedData = new byte[RECEIVESIZE];
            ReceivePacket = new DatagramPacket(ReceivedData, ReceivedData.length);

            Socket.setSoTimeout(TIMEOUT);

            while (true) {
                Socket.receive(ReceivePacket);
                System.out.println("RECEIVED!!!!");
                fromServer(new String(ReceivePacket.getData(), 0, ReceivePacket.getLength()));

                //TODO SOMETHING

            }

        } catch (SocketTimeoutException e) {
            System.out.println("Timeout exception");
        } catch (SocketException e) {
            //sentToServer("Disconnected");
            //e.printStackTrace();
            System.out.println("-->!!!!!! ERROR initialization socket");
        } catch (IOException e) {
            //sentToServer("Disconnected");
            //e.printStackTrace();
            System.out.println("-->!!!!!! Socket cant recieve packet");
        } finally {
            sentToServer("Disconnected");
            Socket.close();
        }

    }

    public void fromServer(String jp) {
        System.out.println(jp);
        switch (jp) {
            case "Server ofline":
                System.out.println(jp);
                Socket.close();
                break;
            default:
                break;
        }
    }

    public void sentToServer(String packedObj) {
        System.out.println(packedObj);
        SendData = packedObj.getBytes();
        SendPacket = new DatagramPacket(SendData, SendData.length, inetAddress, PORT);

        try {
            Socket.send(SendPacket);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error send package");
        }

        System.out.println("Data sended");

    }
}
