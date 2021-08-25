package ru.avalon.javapp.devj120.avalontelecom.controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import ru.avalon.javapp.devj120.avalontelecom.io.ClientsStorage;
import ru.avalon.javapp.devj120.avalontelecom.models.ClientInfo;
import ru.avalon.javapp.devj120.avalontelecom.models.CompanyClientInfo;
import ru.avalon.javapp.devj120.avalontelecom.models.PersonClientInfo;
import ru.avalon.javapp.devj120.avalontelecom.models.PhoneNumber;

public class ClientsController {

    private List<ClientInfo> clients;
    private Set<PhoneNumber> numbers;

    public void addPersonClient(PhoneNumber number, String name, String address, String birthday) {
        if (numbers.contains(number)) {
            throw new IllegalArgumentException("Such a number has already been registered earlier.");
        }
        clients.add(new PersonClientInfo (number, name, address, birthday));
        numbers.add(number);
    }

    public void addCompanyClient(PhoneNumber number, String name, String address, String nameContact, String nameDirector) {
        if (numbers.contains(number)) {
            throw new IllegalArgumentException("Such a number has already been registered earlier.");
        }
        clients.add(new CompanyClientInfo(number, name, address, nameContact, nameDirector));
        numbers.add(number);

    }

    public void remove(ClientInfo clientInfo) {
        clients.remove(clientInfo);
        numbers.remove(clientInfo.getPhoneNumber());
    }

    public int getClientsCount() { // подсчет кол-ва клиентов
        return clients.size();
    }

    public ClientInfo getClientInfo(int index) {
        return clients.get(index);
    }

    public void loadData() throws Exception {
        this.clients = ClientsStorage.getInstance().load();
        this.numbers = new HashSet<>();
        for (ClientInfo client : clients) {
            numbers.add(client.getPhoneNumber());
        }
    }

    public void saveData() throws IOException {
        ClientsStorage.getInstance().save(clients);
    }

}
