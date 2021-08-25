package ru.avalon.javapp.devj120.avalontelecom.ui;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import ru.avalon.javapp.devj120.avalontelecom.controllers.ClientsController;
import ru.avalon.javapp.devj120.avalontelecom.models.ClientInfo;
import ru.avalon.javapp.devj120.avalontelecom.models.CompanyClientInfo;
import ru.avalon.javapp.devj120.avalontelecom.models.PhoneNumber;

public class ClientsTableModel implements TableModel {

    private final String[] columnHeaders = new String[]{ // Заголовки столбцов
        "Phone number",
        "Registration date",
        "Client name",
        "Client address",
        "Info",
        
    };

    private final ClientsController controller;
    private final List<TableModelListener> modelListeners
            = new ArrayList<>();

    public ClientsTableModel(ClientsController controller) {
        this.controller = controller;
    }

    @Override
    public int getRowCount() {  // получить кол-во строк
        return controller.getClientsCount();
    }

    @Override
    public int getColumnCount() {    // получить кол-во столбцов
        return columnHeaders.length;
    }

    @Override
    public String getColumnName(int i) {  // получить название столбца
        return columnHeaders[i];
    }

    @Override
    public Class<?> getColumnClass(int i) {
        return String.class;
    }

    @Override
    public boolean isCellEditable(int i, int i1) {  //запрет на редактирование в таблице
        return false;
    }

    @Override
    public Object getValueAt(int row, int col) {  //  брать клиента с порядковым номером row и возвращать значение атрибута
        ClientInfo ci = controller.getClientInfo(row);
        switch (col) {
            case 0:
                return ci.getPhoneNumber().toString();
            case 1:
                return ci.getRegDate().toString();
            case 2:
                return ci.getName();
            case 3:
                return ci.getAddress();
            case 4:
                return (ci.getExtraInfo());
            default:
                throw new Error("Unreachable place.");
        }
    }

    @Override
    public void setValueAt(Object o, int i, int i1) {
            }

    @Override
    public void addTableModelListener(TableModelListener tl) {
        modelListeners.add(tl);
    }

    @Override
    public void removeTableModelListener(TableModelListener tl) {
        modelListeners.remove(tl);
    }

    public void personClientAdded(PhoneNumber number, String name, String address, String birthday) {
        controller.addPersonClient(number, name, address, birthday);
        int rowNdx = controller.getClientsCount() - 1;
        fireTableModelEvent(rowNdx, TableModelEvent.INSERT);
    }

    public void companyClientAdded(PhoneNumber number, String name, String address, String nameContact, String nameDirector) {
        controller.addCompanyClient(number, name, address, nameContact, nameDirector);
        int rowNdx = controller.getClientsCount() - 1;
        fireTableModelEvent(rowNdx, TableModelEvent.INSERT);
    }

    public void clientChanged(int ndx) {
        fireTableModelEvent(ndx, TableModelEvent.UPDATE);
    }

    public void clientDeleted(int ndx) {
        controller.remove(controller.getClientInfo(ndx));
        fireTableModelEvent(ndx, TableModelEvent.DELETE);
    }

    private void fireTableModelEvent(int rowNdx, int evtType) {
        TableModelEvent tme = new TableModelEvent(this, rowNdx, rowNdx,
                TableModelEvent.ALL_COLUMNS, evtType);
        for (TableModelListener l : modelListeners) {
            l.tableChanged(tme);
        }
    }
}
