package ru.avalon.javapp.devj120.avalontelecom.ui;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import ru.avalon.javapp.devj120.avalontelecom.models.ClientInfo;


public class ClientDialog extends JDialog {

    private JTextField areaCode;
    private JTextField localNum;
    private JTextField clientName;
    private JTextField address;
    private JTextField birthday;
    private JTextField nameDirector;
    private JTextField nameContact;
    private JTextField extraInfo;
    private JLabel lblB;
    private JLabel lblND;
    private JLabel lblNC;

    private boolean okPressed;

    public ClientDialog(Frame owner) {

        super(owner, true);

        Container contentPane = getContentPane();
        contentPane.setLayout(new GridLayout(4, 5));

        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));  // Панель с кодом и номером телефона
        JLabel lbl = new JLabel("Phone number (");  // Название полей Phone number
        p.add(lbl);
        areaCode = new JTextField(3);  // Поле код
        p.add(areaCode);
        lbl.setLabelFor(areaCode);
        p.add(new JLabel(") "));
        localNum = new JTextField(10);  // Поле номер телефона
        p.add(localNum);
        contentPane.add(p);

        clientName = new JTextField(20);   // Поле Client name
        p = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lbl = new JLabel("Client name");  // Название поля Client name
        lbl.setLabelFor(clientName);
        p.add(lbl);
        p.add(clientName);
        contentPane.add(p);

        address = new JTextField(20);  // Поле Address
        p = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lbl = new JLabel("Address");  // Название поля Address
        lbl.setLabelFor(address);
        p.add(lbl);
        p.add(address);
        contentPane.add(p);


        birthday = new JTextField(20);  // Поле для Person
        p = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lblB = new JLabel("Birthday");
        lblB.setLabelFor(birthday);
        p.add(lblB);
        p.add(birthday);
        contentPane.add(p);



        nameDirector = new JTextField(30); // Поле для Company
        p = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lblND = new JLabel("Name of Director");
        lblND.setLabelFor(nameDirector);
        p.add(lblND);
        p.add(nameDirector);
        contentPane.add(p);

        nameContact = new JTextField(30);
        p = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lblNC = new JLabel("Name of Contact");
        lblNC.setLabelFor(nameContact);
        p.add(lblNC);
        p.add(nameContact);
        contentPane.add(p);


        p = new JPanel();
        JButton btn = new JButton("OK");

        btn.addActionListener(e -> hideDialog(true));
        p.add(btn);
        btn = new JButton("Cancel");

        btn.addActionListener(e -> hideDialog(false));
        p.add(btn);

        contentPane.add(p);
    }

    public void prepareForNewCompany() {
        setTitle("New client registration");

        areaCode.setText("");
        localNum.setText("");
        clientName.setText("");
        address.setText("");

        nameContact.setText("");
        nameDirector.setText("");
        birthday.setVisible(false);

        lblB.setVisible(false);

        areaCode.setEditable(true);
        localNum.setEditable(true);

        prepareForShow();
    }

    public void prepareForNewPerson() {
        setTitle("New client registration");

        areaCode.setText("");
        localNum.setText("");
        clientName.setText("");
        address.setText("");

        birthday.setText("");
        nameContact.setVisible(false);
        nameDirector.setVisible(false);

        nameContact.setText("");
        nameDirector.setText("");

        lblND.setVisible(false);
        lblNC.setVisible(false);

        areaCode.setEditable(true);
        localNum.setEditable(true);

        prepareForShow();
    }

    public void prepareForEditCompany(ClientInfo clientInfo) {
        setTitle("Editing Companyclient details");

        areaCode.setText(clientInfo.getPhoneNumber().getAreaCode());
        localNum.setText(clientInfo.getPhoneNumber().getLocalNum());
        clientName.setText(clientInfo.getName());
        address.setText(clientInfo.getAddress());

        nameDirector.setText(clientInfo.getNameDirector());
        nameContact.setText(clientInfo.getNameContact());
        birthday.setVisible(false);
        lblB.setVisible(false);

        areaCode.setEditable(false);
        localNum.setEditable(false);

        prepareForShow();
    }

    public void prepareForEditPerson(ClientInfo clientInfo) {
        setTitle("Editing Personclient details");

        areaCode.setText(clientInfo.getPhoneNumber().getAreaCode());
        localNum.setText(clientInfo.getPhoneNumber().getLocalNum());
        clientName.setText(clientInfo.getName());
        address.setText(clientInfo.getAddress());

        birthday.setText((clientInfo.getBirthday()));
        nameDirector.setVisible(false);
        nameContact.setVisible(false);
        lblND.setVisible(false);
        lblNC.setVisible(false);

        areaCode.setEditable(false);
        localNum.setEditable(false);

        prepareForShow();
    }

    private void prepareForShow() {
        okPressed = false;
        pack();
        setLocationRelativeTo(null);
    }

    private void hideDialog(boolean okPressed) {
        this.okPressed = okPressed;
        setVisible(false);
    }

    public boolean isOkPressed() {
        return okPressed;
    }

    public String getAreaCode() {
        return areaCode.getText();
    }

    public String getLocalNum() {
        return localNum.getText();
    }

    public String getClientName() {
        return clientName.getText();
    }

    public String getAddress() {
        return address.getText();
    }

    String getBirthday() {
        return birthday.getText();
    }

    String getNameDirector() {
        return nameDirector.getText();
    }

    String getNameContact() {
        return nameContact.getText();
    }

    String getExtraInfo() {
        return extraInfo.getText();
    }
}
