package View_Controller;

import Server.Client;
import Server.NetInterfaces;
import Server.Server;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import javax.swing.*;
import java.net.URL;
import java.util.ResourceBundle;

import static View_Controller.MainView.OpenServerMasseges;

public class MainViewController implements Initializable {

    private static String port="9879";
    private static Server server;
    private static Thread serverThread;
    private static ObservableList<NetInterfaces> listInterfaces = FXCollections.observableArrayList();
    private ObservableList<Client> clients = FXCollections.observableArrayList();

    public ObservableList<Client> getClients() {
        return clients;
    }

    @FXML
    private Text ServerStatus;

    @FXML
    private Text textPort;

    @FXML
    private TableView<NetInterfaces> tableAboutServer;

    @FXML
    private TableColumn<NetInterfaces,String> serverNameColumn;

    @FXML
    private TableColumn<NetInterfaces,String> serverIpColumn;

    @FXML
    private TableView<Client> TABLEOFCLIENTS;

    @FXML
    private TableColumn<Client,String> ClientsIP;

    @FXML
    private TableColumn<Client,String> ClientsPORT;

    @FXML
    private TableColumn<Client,String> ClientsNickName;

    @FXML
    private TableColumn<Client,String> ClientsSTATUS;

    @FXML
    private void btnOpenServerMasseges(){
        OpenServerMasseges();
    }

    private void updateClientTable(){
        TABLEOFCLIENTS.setItems(clients);
    }

    public void addClientToList(Client client){
        clients.add(client);
        ServerMessagesController.add("NEW CLIENT! Name : "+client.nickNameProperty().get()+"; IP "+client.IPProperty().get()+":"+client.PORTProperty().get());
        updateClientTable();
    }

    public void deleteFromClientList(String ip){
        for (int i = 0;i<clients.size();i++)
            if(clients.get(i).getIP().equals(ip)){
                ServerMessagesController.add(clients.get(i).nickNameProperty().get()+" Has been disconnected!");
                clients.remove(i);
                updateClientTable();
                break;
            }
    }

    public void clearClients(){
        clients.clear();
        updateClientTable();
    }

    public void closeServer(){
        if(serverThread.isAlive()) {
            setOffline();
            clearClients();
            ServerMessagesController.add("--> Server : Socket closed");
        }else{
            ServerMessagesController.add("--> Server : Socket already closed");
        }
    }

    //Кнопка START SERVER
    @FXML
    private void startServerButton(){
        if (!serverThread.isAlive()) {
            serverThread = new Thread(server);
            serverThread.start();
            setOnline();
        }else{
            JOptionPane.showMessageDialog(null,"Server Already started");
        }
    }

    //Установка порта <<<<Усовершенствовать для рандомных значений и чтобы не было зарезервированных портов компьютера>>>>
    @FXML
    private void setPort(){
        port = JOptionPane.showInputDialog("Enter Port",port);
        try {
            int inttestport = Integer.parseInt(port);

            if(inttestport < 65535 && inttestport > 0) {
                textPort.setText(port);
                server.setPORT(inttestport);
            }else{
                JOptionPane.showMessageDialog(null,"PORT MUST BE 0 < NUMBER < 65535 ");
            }

        }catch (NumberFormatException e){
            JOptionPane.showMessageDialog(null,"PORT MUST BE A NUMBER!");
        }
    }

    @FXML
    private void closeServerButton(){
        server.closeServer();
    }

    //установка статуса сервера в OFFLINE
    private void setOffline(){
        ServerStatus.setText("Offline");
        ServerStatus.setFill(Color.rgb(255,0,0));
    }

    //установка статуса сервера в ONLINE
    private void setOnline(){
        ServerStatus.setText("Online");
        ServerStatus.setFill(Color.rgb(0,255,0));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        textPort.setText(port);

        server = new Server(port, this);
        serverThread = new Thread(server);

        ClientsIP.setCellValueFactory(cellData -> cellData.getValue().IPProperty());
        ClientsPORT.setCellValueFactory(cellData -> cellData.getValue().PORTProperty());
        ClientsNickName.setCellValueFactory(cellData -> cellData.getValue().nickNameProperty());
        ClientsSTATUS.setCellValueFactory(cellData -> cellData.getValue().statusProperty());

        listInterfaces = Server.getNetworkAdresses();
        serverNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        serverIpColumn.setCellValueFactory(cellData -> cellData.getValue().IPProperty());
        tableAboutServer.setItems(listInterfaces);

    }

}