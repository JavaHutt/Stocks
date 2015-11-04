package quotes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.table.DefaultTableModel;


// Данный класс отвечает за создание графического интерфейса программы


public class Gui {

    // Переменные, возвращающие сегодняшнюю дату (день, месяц, год)

    public static int getCurrentDay()
    {
        Calendar calendar = java.util.Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        calendar.setTime(new java.util.Date());
        return calendar.get(Calendar.DAY_OF_MONTH);
    }
    public static int getCurrentMonth()
    {
        Calendar calendar = java.util.Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        calendar.setTime(new java.util.Date());
        return calendar.get(Calendar.MONTH);
    }
    public static int getCurrentYear()
    {
        Calendar calendar = java.util.Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        calendar.setTime(new java.util.Date());
        return calendar.get(java.util.Calendar.YEAR);
    }

    // Метод создающий форму, поля, кнопки, таблицу
    
    public static void build() {

        // Создаем массивы, используемые для выбора начальной и конечной дат

        ArrayList <String> daysList = new ArrayList<String>();
        for (int a = 1; a <=31;a++){
            daysList.add(Integer.toString(a));
        }
        String[] days = daysList.toArray(new  String [daysList.size()]);

        ArrayList <String> monthsList = new ArrayList<String>();
        for (int a = 1; a <=12;a++){
            monthsList.add(Integer.toString(a));
        }
        String[] months = monthsList.toArray(new  String [monthsList.size()]);

        ArrayList <String> yearsList = new ArrayList<String>();
        for (int a = 1970; a <=getCurrentYear();a++){
            yearsList.add(Integer.toString(a));
        }
        String[] years = yearsList.toArray(new  String [yearsList.size()]);
        Collections.reverse(yearsList);
        String[] years2 = yearsList.toArray(new  String [yearsList.size()]);

        // Создаем элементы интерфейса
        
        JComboBox dayFrom = new JComboBox(days);
        dayFrom.setEditable(true);
        dayFrom.setSize(40, 30);
        dayFrom.setLocation(80, 50);

        JComboBox monthFrom = new JComboBox(months);
        monthFrom.setEditable(true);
        monthFrom.setSize(40, 30);
        monthFrom.setLocation(120, 50);

        JComboBox yearFrom = new JComboBox(years);
        yearFrom.setEditable(true);
        yearFrom.setSize(55, 30);
        yearFrom.setLocation(160, 50);

        JComboBox dayTo = new JComboBox(days);
        dayTo.setEditable(true);
        dayTo.setSize(40, 30);
        dayTo.setLocation(80, 85);
        dayTo.setSelectedIndex(getCurrentDay() - 1);

        JComboBox monthTo = new JComboBox(months);
        monthTo.setEditable(true);
        monthTo.setSize(40, 30);
        monthTo.setLocation(120, 85);
        monthTo.setSelectedIndex(getCurrentMonth());

        JComboBox yearTo = new JComboBox(years2);
        yearTo.setEditable(true);
        yearTo.setSize(55, 30);
        yearTo.setLocation(160, 85);

        JLabel fromLabel = new JLabel("from");
        fromLabel.setSize(30, 30);
        fromLabel.setLocation(35, 50);

        JLabel toLabel = new JLabel("to");
        toLabel.setSize(30, 30);
        toLabel.setLocation(35, 85);

        JButton button1 = new JButton("Show");
        button1.setSize(105, 30);
        button1.setLocation(410, 85);
   
        JButton button5 = new JButton("Download");
        button5.setSize(105, 30);
        button5.setLocation(260, 85);
        
        JButton button6 = new JButton("Update all");
        button6.setSize(90, 20);
        button6.setLocation(427, 455);
               
        JTextField stockSymbol = new JTextField();
        AutoCompleteSetup.setupAutoComplete(stockSymbol);
        stockSymbol.setSize(255, 30);
        stockSymbol.setLocation(260, 50);
        
        JLabel stockLabel = new JLabel("Stock");
        stockLabel.setSize(90, 30);
        stockLabel.setLocation(370, 10);

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem helpItem = new JMenuItem("Help");
        JMenuItem aboutItem = new JMenuItem("About");
        fileMenu.add(helpItem);
        fileMenu.add(aboutItem);
        menuBar.add(fileMenu);
 
        DefaultTableModel model = new DefaultTableModel(); 
        model.addColumn("Ticker");  
        model.addColumn("Date from");  
        model.addColumn("Date to");   
      
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(35, 150, 482, 300);
        table.setEnabled(false);
        
        JPopupMenu popup = new JPopupMenu();
        JMenuItem menuItemShow = new JMenuItem("Show");
        JMenuItem menuItemRemove = new JMenuItem("Delete");
        JMenuItem menuItemUpdate = new JMenuItem("Update");
        popup.add(menuItemShow);
        popup.add(menuItemRemove);
        popup.add(menuItemUpdate);
                
        table.setComponentPopupMenu(popup);
        table.addMouseListener(new TableMouseListener(table));
        
        // Цепляем элементы на панель

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.add(button1);
        panel.add(stockSymbol);
        panel.add(stockLabel);
        panel.add(dayFrom);
        panel.add(monthFrom);
        panel.add(yearFrom);
        panel.add(dayTo);
        panel.add(monthTo);
        panel.add(yearTo);
        panel.add(fromLabel);
        panel.add(toLabel);
        panel.add(scrollPane);
        panel.add(button5);
        panel.add(button6);
        
        // Создаем две переменные, которые будут хранить выбранные даты

        GregorianCalendar start = new GregorianCalendar(Integer.parseInt((String) yearFrom.getSelectedItem()), Integer.parseInt((String) monthFrom.getSelectedItem())-1, Integer.parseInt((String) dayFrom.getSelectedItem()));
        GregorianCalendar end = new GregorianCalendar(Integer.parseInt((String) yearTo.getSelectedItem()), Integer.parseInt((String) monthTo.getSelectedItem())-1, Integer.parseInt((String) dayTo.getSelectedItem()));

        // Методы отвечающие за функциональность элементов формы
        
        // Устанавливаем возможность выбора даты, добавляем соответсвующие Listeners

        yearFrom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                start.set(1, Integer.parseInt((String) yearFrom.getSelectedItem()));
            }
        });

        monthFrom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                start.set(2, Integer.parseInt((String) monthFrom.getSelectedItem())-1);
            }
        });
        
        dayFrom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                start.set(5, Integer.parseInt((String) dayFrom.getSelectedItem()));
            }
        });

        yearTo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                end.set(1, Integer.parseInt((String) yearTo.getSelectedItem()));
            }
        });

        monthTo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                end.set(2, Integer.parseInt((String) monthTo.getSelectedItem())-1);
            }
        });

        dayTo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                end.set(5, Integer.parseInt((String) dayTo.getSelectedItem()));
            }
        });

        // Кнопка Show - Передает методу QuoteViewer.showChart тикер и выбранную дату
        // Далее появляется сам график ценной бумаги
        
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                QuotesViewer.showChart(stockSymbol.getText(), QuotesParser.makeQuoteArray(stockSymbol.getText(), start, end));
            }
        });

        // Кнопка File - Вызывает окно со справочной информацией
        
        helpItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JLabel htmlLabel = new JLabel("<html><br>"+
                        "&nbsp; Stock - choose the date and type your stock <br><br>"+
                        "&nbsp; Show - displays a chart, where: <br><br>"+
                        "&nbsp; MouseWheel - to zoom <br>" +
                        "&nbsp; Ctrl + LeftMouseClick - to move a chart <br>" +
                        "&nbsp; RightMouseClick - additional options <br><br>" +
                        "&nbsp; Download - creates a folder in the <br>" +
                        "&nbsp; program directory and downloads a stock<br><br>" +
                        "&nbsp; Update all - updates all stocks at downloads folder<br><br>" +
                        "&nbsp; RightClick on the table displays a popup menu,<br>" +
                        "&nbsp; where you can: <br><br>" +
                        "&nbsp; Show, Update, Delete your downloaded stock" +
                        "</html>");

                JFrame HelpWindow = new JFrame("Help");
                HelpWindow.setResizable(false);
                HelpWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                HelpWindow.setSize(300, 350);
                HelpWindow.setLocationRelativeTo(null);
                HelpWindow.add(htmlLabel, BorderLayout.NORTH);
                HelpWindow.setVisible(true);
            }
        });
        
        aboutItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            JEditorPane html;
            html = new JEditorPane();
            html.setContentType("text/html;Content-Type=UTF-8");
            html.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, Boolean.TRUE);
            html.setFont(new JLabel().getFont());
            html.setText("<html><br>"+
                    "&nbsp; StockDownloader_1.0 <br><br>"+
                    "&nbsp; Contacts: <br><br>"+
                    "&nbsp; <a href=\"https://github.com/SteshenkoMA\">github.com/SteshenkoM</a> <br><br>"+
                    "</html>");
            html.setBackground(new JFrame().getBackground());
            html.setEditable(false);
                          
            html.addHyperlinkListener(new HyperlinkListener() {
    public void hyperlinkUpdate(HyperlinkEvent e) {
        if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
            if (Desktop.isDesktopSupported()) {
                try {
                    Desktop.getDesktop().browse(e.getURL().toURI());
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (URISyntaxException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
});
            
            JFrame AboutWindow = new JFrame("About");
            JScrollPane sp = new JScrollPane(html);
            AboutWindow.getContentPane().add(sp);
            AboutWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            AboutWindow.setSize(200, 200);
            AboutWindow.setLocationRelativeTo(null);
            AboutWindow.setResizable(false);
            AboutWindow.setVisible(true);

            }
});
        
        
        
        // Кнопка Download - Передает методу QuotesDownloader.writeInFile тикер, дату
        // Далее этот метод скачивает данные в txt файл в папку downloads
        
        button5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                QuotesDownloader.writeInFile(stockSymbol.getText(), start, end);
                String lastLine = LineReader.readLastLine(stockSymbol.getText());  
                String firstLine = LineReader.readFirstLine(stockSymbol.getText());
                String[] data= { stockSymbol.getText(),firstLine, lastLine };
              model.addRow(data);
            }
        });
        
        // Ниже представлены Listeners отвечающие за функционал всплывающего меню при нажатии правой ннопкой мыши на строке таблицы
        // menuItemShow - вызвать график; 
        // menuItemRemove - удалить "выбранный_тикер".txt из папки downloads
        // menuItemUpdate - обновить исторические данные выбранного тикера
        
        menuItemShow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int sel = table.getSelectedRow();
                String symbol  = model.getValueAt(sel,0).toString();
               
                try {
                    
                    QuotesViewer.showChart(symbol, QuotesParser.makeQuoteArrayOffline(symbol));       
         
}  catch (Exception ex) {
            ex.printStackTrace();
        
        }   
            }
        });
          
        menuItemRemove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int sel = table.getSelectedRow();
                String symbol  =model.getValueAt(sel,0).toString();
                model.removeRow(sel);
                               
                try {
                    
                     File file = new File("downloads/"+symbol+".txt");
                     file.delete();
                }  
                catch (Exception ex) {
                     ex.printStackTrace();
                }   
            }
        });
        
        menuItemUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int sel = table.getSelectedRow();
                String symbol  =model.getValueAt(sel,0).toString();
              
                FileParserUpdate.download(symbol);
                String lastLine = LineReader.readLastLine(symbol);
                model.setValueAt(lastLine, sel, 2);
            }
        });
        
        // Создаем основное окно программы

        JFrame MainWindow = new JFrame("Stock Downloader");
        
        MainWindow.setJMenuBar(menuBar);
        MainWindow.setContentPane(panel);
        MainWindow.setResizable(false);
        MainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MainWindow.setSize(560, 560);
        MainWindow.setLocationRelativeTo(null);
        MainWindow.setVisible(true);
        
        // Когда окно создано, добавляем тикеры, которые находятся в папке downloads в таблицу
        // Для этого:
        // 1) считываем содержимое папки 
        // 2) убираем из названия файла ".txt"
        // 3) вызываем методы, которые считывают даты начала и конца содержмиого файла
        // 4) добавляем данные в таблицу (Название тикера, дата начала, дата конца)
        
        File dir = new File("downloads/");
        File []fList;
        fList = dir.listFiles();

        for(int i=0; i<fList.length; i++)
        {
         if(fList[i].isFile()){
         
         String ticker = fList[i].getName().replaceAll(".txt","");
         String lastLine = LineReader.readLastLine(ticker);  
         String firstLine = LineReader.readFirstLine(ticker);
         String[] dat= { ticker, firstLine, lastLine };
         model.addRow(dat);
         }
        }

        // Кнопка UpdateAll - отвечает за обновление всех тикеров из папки downloads
        
        button6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File dir = new File("downloads/");
                File []fList;
                fList = dir.listFiles();
                
                // Проходим циклом по директории downloads
                // Обновляем каждый тикер
                // Обновляем данные таблицы
                
                for(int i=0; i<fList.length; i++){
                    if(fList[i].isFile()){
                                        
                    String ticker = fList[i].getName().replaceAll(".txt","");
                    FileParserUpdate.download(ticker);
                     
                    // Эта часть кода работает следующим образом.
                    // 1) проходим циклом по всем строкам таблицы
                    // 2) если находим в первом столбце значение соответствующее названию нашего тикера (который обновили),
                    //    считываем конечную дату и содержимого файла и добавляем ее в соотвествующий столбец таблицы
                    
                    for (int d = model.getRowCount() - 1; d >= 0; --d) {
                         if (model.getValueAt(d, 0).equals(ticker)) {
                         String lastLine = LineReader.readLastLine(ticker);
                         model.setValueAt(lastLine, d, 2);
                         }
                    }
                     
                    }
                }
            }
        });
          
        }
      
    }