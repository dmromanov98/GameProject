package Server;


import View_Controller.MainViewController;
import View_Controller.ServerMessagesController;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.net.*;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Map;
import java.util.Set;

public class Server extends Thread {
    private static DatagramSocket Socket; //сокет
    private static DatagramPacket RecievePacket; //пакет
    private int PORT;
    private byte[] RecieveData;
    private MainViewController mw;
    private Map<String,Client> ListOfClients;
    private byte[] SendData;
    private DatagramPacket SendPacket;

    //время ожидания сервера
    private static final int TIMEOUT = 30000;

    //максимальное количество байтов, которое может получить сервер
    private final static int RESBYTES = 256;

    public void setPORT(int PORT) {
        this.PORT = PORT;
    }

    public Server(String port, MainViewController mw) {
        ListOfClients = new Map<String,Client>() {
            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean containsKey(Object key) {
                return false;
            }

            @Override
            public boolean containsValue(Object value) {
                return false;
            }

            @Override
            public Client get(Object key) {
                return null;
            }

            @Override
            public Client put(String key, Client value) {
                return null;
            }

            @Override
            public Client remove(Object key) {
                return null;
            }

            @Override
            public void putAll(Map<? extends String, ? extends Client> m) {

            }

            @Override
            public void clear() {

            }

            @Override
            public Set<String> keySet() {
                return null;
            }

            @Override
            public Collection<Client> values() {
                return null;
            }

            @Override
            public Set<Entry<String,Client>> entrySet() {
                return null;
            }
        };
        this.mw = mw;
        this.PORT = Integer.valueOf(port);
        RecieveData = new byte[RESBYTES];
    }

    @Override
    public void run() {
        try {

            //запуск соединения
            ServerMessagesController.add("Server : Starting server. PORT : "+PORT);
            ServerMessagesController.add("Server : Connecting socket...");
            Socket = new DatagramSocket(PORT);
            ServerMessagesController.add("Connected...");
            ServerMessagesController.add("Waiting for clients...");

            try {
                Socket.setSoTimeout(TIMEOUT);
                RecievePacket = new DatagramPacket(RecieveData, RecieveData.length);
                while (true) {


                    Socket.receive(RecievePacket);
                    fromClient(new String(RecievePacket.getData(), 0, RecievePacket.getLength()),RecievePacket);


                }
            } catch (SocketTimeoutException e) {
               ServerMessagesController.add("-->!!!!!! SERVER : Clients who managed to connect!  TIMEOUT EXCEPTION \n\t-->"+e);
            } catch (IOException e) {
                ServerMessagesController.add("-->!!!!!! SERVER : Cant recieve data! (Maybe socket was closed) \n\t-->"+e);
            }
        } catch (SocketException e) {
            ServerMessagesController.add("-->!!!!!! SERVER : ERROR initialization socket(Socket already using) \n\t-->"+e);
        } catch (IllegalArgumentException e){
            ServerMessagesController.add("Port number out of range. \n\t-->"+e);
        } finally {
            closeServer();
            mw.closeServer();
        }
    }

    public void fromClient(String jp,DatagramPacket packet){
        switch (jp){
            case "Disconnected":
                mw.deleteFromClientList(packet.getAddress().getHostAddress());
                break;
            default:
                Gson gson = new GsonBuilder().registerTypeAdapter(Client.class, new Deserialize()).create();
                Client client = gson.fromJson(jp, Client.class);
                client.setIP(packet.getAddress().getHostAddress());
                client.setPORT(String.valueOf(packet.getPort()));
                mw.addClientToList(client);
                client = null;
                break;
        }
    }

    public void closeServer() {
        try {
            sendToClient("Server offline");
            Socket.close();
        }catch (NullPointerException e){
            ServerMessagesController.add("-->!!!!!! SERVER : is not initialize \n\t-->"+e);
        }
    }

    public void sendToClient(String s){
        if(mw.getClients().size()>0) {
            SendData = s.getBytes();
            for (Client cl : mw.getClients()) {
                try {
                    SendPacket = new DatagramPacket(SendData, SendData.length,
                            InetAddress.getByName(cl.getIP()), Integer.parseInt(cl.getPORT()));
                    Socket.send(SendPacket);
                } catch (UnknownHostException e) {
                    System.out.println("UnknownHostException(SERVER sendToClient) " + e.getMessage());
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Error send package");
                }
            }
            mw.clearClients();
        }
    }

    //получение Network hardware IP
    public static ObservableList<NetInterfaces> getNetworkAdresses() {
        ObservableList<NetInterfaces> NI = FXCollections.observableArrayList();
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {

                NetworkInterface iface = interfaces.nextElement();
                // filters out 127.0.0.1 and inactive interfaces
                if (iface.isLoopback() || !iface.isUp())
                    continue;

                Enumeration<InetAddress> addresses = iface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress addr = addresses.nextElement();

                    // *EDIT*
                    if (addr instanceof Inet6Address) continue;

                    NI.add(new NetInterfaces(addr.getHostAddress().toString(), iface.getDisplayName().toString()));
                }
            }
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
        return NI;
    }

}
