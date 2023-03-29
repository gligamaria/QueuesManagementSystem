package View;

import Controller.Controller;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimulationFrame extends JFrame {

    Color myPink = new Color(255, 204, 204);
    Color myDarkPink = new Color(226, 145, 145);

    //private static final long serialVersionUID = 1L;
    public JPanel contentPane;
    public JTextField timeLimit;
    public JTextField noClients;
    public JTextField noQueues;
    public JTextField arrivalTimeMin;
    public JTextField arrivalTimeMax;
    public JTextField serviceTimeMin;
    public JTextField serviceTimeMax;
    public JLabel queues;
    public JButton startButton;
    public JButton stopButton;
    public JComboBox<String> strategyBox;
    public JLabel labelActualPeekHour;
    public JLabel labelActualAvgServiceTime;
    public JLabel labelActualAvgArrivalTime;

    public Controller controller;

    public SimulationFrame(String name) {

        super(name);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(450, 190, 980, 430);
        setResizable(false);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel labelTimeLimit = new JLabel("Time limit");
        labelTimeLimit.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        labelTimeLimit.setBounds(60, 50, 150, 30);
        contentPane.add(labelTimeLimit);

        JLabel labelNoClients = new JLabel("Number of clients");
        labelNoClients.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        labelNoClients.setBounds(60, 80, 150, 30);
        contentPane.add(labelNoClients);

        JLabel labelNoQueues = new JLabel("Number of queues");
        labelNoQueues.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        labelNoQueues.setBounds(60, 110, 150, 30);
        contentPane.add(labelNoQueues);

        timeLimit = new JTextField();
        timeLimit.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        timeLimit.setBounds(210, 50, 200, 25);
        contentPane.add(timeLimit);
        timeLimit.setColumns(10);

        noClients = new JTextField();
        noClients.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        noClients.setBounds(210, 80, 200, 25);
        contentPane.add(noClients);
        noClients.setColumns(10);

        noQueues = new JTextField();
        noQueues.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        noQueues.setBounds(210, 110, 200, 25);
        contentPane.add(noQueues);
        noQueues.setColumns(10);

        JLabel labelStrategy = new JLabel("Strategy selection");
        labelStrategy.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        labelStrategy.setBounds(60, 140, 150, 25);
        contentPane.add(labelStrategy);

        String[] strategyOptions = {"Shortest time strategy", "Shortest queue strategy"};
        strategyBox = new JComboBox<>(strategyOptions);

        strategyBox.setBounds(210, 140, 200, 25);
        contentPane.add(strategyBox);

        strategyBox.setBackground(myDarkPink);

        JLabel labelArrivalMin = new JLabel("Arrival time min");
        labelArrivalMin.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        labelArrivalMin.setBounds(60, 210, 150, 25);
        contentPane.add(labelArrivalMin);

        JLabel labelArrivalMax = new JLabel("Arrival time max");
        labelArrivalMax.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        labelArrivalMax.setBounds(250, 210, 150, 25);
        contentPane.add(labelArrivalMax);

        JLabel labelServiceMin = new JLabel("Service time min");
        labelServiceMin.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        labelServiceMin.setBounds(60, 240, 150, 25);
        contentPane.add(labelServiceMin);

        JLabel labelServiceMax = new JLabel("Service time max");
        labelServiceMax.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        labelServiceMax.setBounds(250, 240, 150, 25);
        contentPane.add(labelServiceMax);

        arrivalTimeMin = new JTextField();
        arrivalTimeMin.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        arrivalTimeMin.setBounds(170, 210, 50, 25);
        contentPane.add(arrivalTimeMin);
        arrivalTimeMin.setColumns(10);

        arrivalTimeMax = new JTextField();
        arrivalTimeMax.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        arrivalTimeMax.setBounds(360, 210, 50, 25);
        contentPane.add(arrivalTimeMax);
        arrivalTimeMax.setColumns(10);

        serviceTimeMin = new JTextField();
        serviceTimeMin.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        serviceTimeMin.setBounds(170, 240, 50, 25);
        contentPane.add(serviceTimeMin);
        serviceTimeMin.setColumns(10);

        serviceTimeMax = new JTextField();
        serviceTimeMax.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        serviceTimeMax.setBounds(360, 240, 50, 25);
        contentPane.add(serviceTimeMax);
        serviceTimeMax.setColumns(10);

        JLabel labelAvgArrivalTime = new JLabel("AVERAGE ARRIVAL TIME:");
        labelAvgArrivalTime.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        labelAvgArrivalTime.setBounds(450, 50, 200, 25);
        contentPane.add(labelAvgArrivalTime);

        labelActualAvgArrivalTime = new JLabel("0");
        labelActualAvgArrivalTime.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        labelActualAvgArrivalTime.setBounds(645, 50, 150, 25);
        contentPane.add(labelActualAvgArrivalTime);
        labelActualAvgArrivalTime.setVisible(false);

        JLabel labelAvgServiceTime = new JLabel("AVERAGE SERVICE TIME:");
        labelAvgServiceTime.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        labelAvgServiceTime.setBounds(450, 95, 200, 25);
        contentPane.add(labelAvgServiceTime);

        labelActualAvgServiceTime = new JLabel("0");
        labelActualAvgServiceTime.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        labelActualAvgServiceTime.setBounds(635, 95, 150, 25);
        contentPane.add(labelActualAvgServiceTime);
        labelActualAvgServiceTime.setVisible(false);

        JLabel labelPeekHour = new JLabel("PEEK HOUR:");
        labelPeekHour.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        labelPeekHour.setBounds(450, 140, 150, 25);
        contentPane.add(labelPeekHour);

        labelActualPeekHour = new JLabel("0");
        labelActualPeekHour.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        labelActualPeekHour.setBounds(540, 140, 150, 25);
        contentPane.add(labelActualPeekHour);
        labelActualPeekHour.setVisible(false);

        queues = new JLabel();
        queues.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        queues.setBounds(450, 170, 450, 170);
        contentPane.add(queues);

        startButton = new JButton("Start");
        controller = new Controller(name, this);
        startButton.setActionCommand("start");
        startButton.addActionListener(controller);

        startButton.setFont(new Font("Times New Roman", Font.PLAIN, 22));
        startButton.setBounds(60, 280, 160, 60);
        startButton. setBackground(myDarkPink);
        contentPane.add(startButton);

        stopButton = new JButton("Stop");
        stopButton.setActionCommand("stop");
        stopButton.addActionListener(controller);

        stopButton.setFont(new Font("Times New Roman", Font.PLAIN, 22));
        stopButton.setBounds(250, 280, 160, 60);
        stopButton. setBackground(myDarkPink);
        contentPane.add(stopButton);

    }
    }

