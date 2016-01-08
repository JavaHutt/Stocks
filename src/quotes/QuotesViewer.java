package quotes;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.SegmentedTimeline;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.CandlestickRenderer;
import org.jfree.data.xy.OHLCDataset;
import javax.swing.*;
import java.awt.*;


/*
   Данный класс обращается к QuotesParser и выводит массив исторических данных в виде графика

   This class accesses to QuotesParser and displays an array of historical data in a graph
*/

public class QuotesViewer {

    public static void showChart (String symbol,OHLCDataset dataset) {

        /*
        Создаем график, устанавливаем его параметры.
        
        Create the chart, set its options.
        */
                
        JFreeChart chart = ChartFactory.createCandlestickChart(null, "Time", "Price", dataset, false);
        chart.setBackgroundPaint(Color.white);

        // Добавляем зум колесиком мышки и отключаем увелчение при щелчке левой кнопкой мыши и одновремменым ее передвижением

        ChartPanel chartpanel = new ChartPanel(chart);
        chartpanel.setDomainZoomable(true);
        chartpanel.setRangeZoomable(true);
        chartpanel.setMouseWheelEnabled(true);
        chartpanel.setZoomTriggerDistance(Integer.MAX_VALUE);
        chartpanel.setFillZoomRectangle(false);
        chartpanel.setZoomOutlinePaint(new Color(0f, 0f, 0f, 0f));

        /*
           Задаем цвет фона
           Добавляем возможность перемещать график мышкой по осям координат
        
           Set background color
           Add the ability to move the graph with the mouse on the coordinate axes
        */
        
        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setDomainGridlinesVisible(true);
        plot.setRangePannable(true);
        plot.setDomainPannable(true);
        plot.setDomainGridlinePaint(Color.lightGray);
        plot.setRangeGridlinePaint(Color.lightGray);
        plot.setRangeAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);
        ((NumberAxis) plot.getRangeAxis()).setAutoRangeIncludesZero(false);

        /*
           Пропускаем выходные
        
           Skip weekends
        */

        ((DateAxis) plot.getDomainAxis()).setTimeline(SegmentedTimeline.newMondayThroughFridayTimeline());

        /*
           Отключаем volume
           
           Disable volume
        */        

        ((CandlestickRenderer) plot.getRenderer()).setDrawVolume(false);

        /*
           Настраиваем цвет свечек
           
           Custom candels color
        */

        CandlestickRenderer candlestickRenderer = (CandlestickRenderer)plot.getRenderer();
        candlestickRenderer.setUpPaint(Color.GREEN);
        candlestickRenderer.setDownPaint(Color.RED);
        candlestickRenderer.setSeriesPaint(0, Color.BLACK);

        /*
           Добавляем график на панель
           
           Add chart to panel
        */        

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(chartpanel, BorderLayout.CENTER);

        /*
           Создаем окно с панелью, задаем его настройки
                
           Create a window panel, set its settings
        */

        JFrame myFrame = new JFrame(symbol);
        myFrame.setResizable(true);
        myFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        myFrame.add(panel, BorderLayout.CENTER);
        myFrame.setSize (800,600);
        myFrame.setLocationRelativeTo(null);
        myFrame.setVisible(true);

    }

}
