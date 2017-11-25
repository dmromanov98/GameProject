package Client;

import Person.Person;

import static Person.PackJSON.pack;

public class Main {
    private static Client client;

    public static void main(String[] args) throws InterruptedException {
        client = new Client();
        client.start();

        Thread.sleep(5000);

        Person p1 = new Person("Dmitry","Connected",new int[]{15,32});
        String PackedObj = pack(p1);
        client.sentToServer(PackedObj);

    }
}
