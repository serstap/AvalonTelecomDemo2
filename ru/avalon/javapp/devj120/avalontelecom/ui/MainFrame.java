package ru.avalon.javapp.devj120.avalontelecom.ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import ru.avalon.javapp.devj120.avalontelecom.controllers.ClientsController;
import ru.avalon.javapp.devj120.avalontelecom.models.ClientInfo;
import ru.avalon.javapp.devj120.avalontelecom.models.PersonClientInfo;
import ru.avalon.javapp.devj120.avalontelecom.models.CompanyClientInfo;
import ru.avalon.javapp.devj120.avalontelecom.models.PhoneNumber;

public class MainFrame extends JFrame {

    private ClientsController controller;
    private JTable clientsTable;
    private ClientsTableModel clientsTableModel;
    private boolean isPerson;
    private PersonClientInfo personClientInfo;
    private CompanyClientInfo companyClientInfo;
    private ClientInfo clientInfo;

    public MainFrame(ClientsController controller) {
        super("Avalon Telecom: clients list");
        setBounds(300, 200, 900, 600);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        this.controller = controller;
        Container contentPane = getContentPane();
        clientsTableModel = new ClientsTableModel(controller);
        clientsTable = new JTable(clientsTableModel);
        clientsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        contentPane.add(clientsTable.getTableHeader(), BorderLayout.NORTH);
        contentPane.add(new JScrollPane(clientsTable), BorderLayout.CENTER);

        JMenuBar menuBar = new JMenuBar();
        JMenu operations = new JMenu("Operations");
        operations.setMnemonic('O');
        menuBar.add(operations);

        addMenuItemTo(operations, "Add Person", 'A',
                KeyStroke.getKeyStroke('A', InputEvent.ALT_DOWN_MASK),
                this::addPersonClient);

        addMenuItemTo(operations, "Add Company", 'B',
                KeyStroke.getKeyStroke('B', InputEvent.ALT_DOWN_MASK),
                this::addCompanyClient);

        addMenuItemTo(operations, "Change Client", 'C',
                KeyStroke.getKeyStroke('C', InputEvent.ALT_DOWN_MASK),
                this::changeClient);

        addMenuItemTo(operations, "Delete", 'D',
                KeyStroke.getKeyStroke('D', InputEvent.ALT_DOWN_MASK),
                this::dropClient);

        setJMenuBar(menuBar);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exitWithConfirm();
            }
        });
    }

    private void addMenuItemTo(JMenu parent, String text, char mnemonic,
            KeyStroke accelerator, ActionListener al) {
        JMenuItem mi = new JMenuItem(text, mnemonic);
        mi.setAccelerator(accelerator);
        mi.addActionListener(al);
        parent.add(mi);
    }

    private void addPersonClient(ActionEvent ep) {

        ClientDialog cd = new ClientDialog(this);

        cd.prepareForNewPerson();
        cd.setVisible(true);
        while (cd.isOkPressed()) {
            try {
                clientsTableModel.personClientAdded(
                        new PhoneNumber(cd.getAreaCode(), cd.getLocalNum()),
                        cd.getClientName(),
                        cd.getAddress(),
                        cd.getBirthday());
                break;
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(),
                        "Error adding client", JOptionPane.ERROR_MESSAGE);
                cd.setVisible(true);
            }
        }
    }

    private void addCompanyClient(ActionEvent ec) {

        ClientDialog cd = new ClientDialog(this);
        cd.prepareForNewCompany();
        cd.setVisible(true);
        while (cd.isOkPressed()) {
            try {
                clientsTableModel.companyClientAdded(
                        new PhoneNumber(cd.getAreaCode(), cd.getLocalNum()),
                        cd.getClientName(),
                        cd.getAddress(),
                        cd.getNameContact(),
                        cd.getNameDirector());

                break;
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(),
                        "Error adding client", JOptionPane.ERROR_MESSAGE);
                cd.setVisible(true);
            }
        }
    }
    
    private void changeClient(ActionEvent ep) {
       

        ClientDialog cd = new ClientDialog(this);
        int rowNdx = clientsTable.getSelectedRow();
        if (rowNdx == -1) {
            return;
        }
        ClientInfo clientInfo = controller.getClientInfo(rowNdx);

        if (clientInfo instanceof PersonClientInfo) {

            cd.prepareForEditPerson((PersonClientInfo) clientInfo);
            cd.setVisible(true);
            if (cd.isOkPressed()) {
                clientInfo.setName(cd.getClientName());
                clientInfo.setAddress(cd.getAddress());
                clientInfo.setBirthday(cd.getBirthday());
                clientsTableModel.clientChanged(rowNdx);
            }
       }

        if (clientInfo instanceof CompanyClientInfo) {
            cd.prepareForEditCompany((CompanyClientInfo) clientInfo);
            cd.setVisible(true);
            if (cd.isOkPressed()) {

                clientInfo.setName(cd.getClientName());
                clientInfo.setAddress(cd.getAddress());
                clientInfo.setNameContact(cd.getNameContact());
                clientInfo.setNameDirector(cd.getNameDirector());

                clientsTableModel.clientChanged(rowNdx);
            }
       }

    }

    private void dropClient(ActionEvent e) {
        int rowNdx = clientsTable.getSelectedRow();
        if (rowNdx == -1) {
            return;
        }
        ClientInfo ci = controller.getClientInfo(rowNdx);
        if (JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete client with number "
                + ci.getPhoneNumber() + "?",
                "Delete confirm", JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
            clientsTableModel.clientDeleted(rowNdx);
        }
    }

    private void exitWithConfirm() {
        if (JOptionPane.showConfirmDialog(this, "Are you sure you want to exit?",
                "Exit confirmation",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE) == JOptionPane.NO_OPTION) {
            return;
        }

        try {
            controller.saveData();
        } catch (IOException ex) {
            if (JOptionPane.showConfirmDialog(this,
                    "Error happened while the data was saved: " + ex.getMessage() + ".\n"
                    + "Are you sure you want to exit and loose your data (Yes),"
                    + "or you want to keep working while trying to fix the problem (No)?",
                    "Exit confirmation",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.ERROR_MESSAGE) == JOptionPane.NO_OPTION) {
                return;
            }
        }

        dispose();
    }
}
