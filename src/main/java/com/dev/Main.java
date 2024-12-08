package com.dev;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.chart.plot.PlotOrientation;

import javax.swing.*;
import java.awt.*;
import static java.lang.Math.*;

public class MathCalcGraph extends JFrame {

    public MathCalcGraph(String title) {
        super(title);
        XYSeries series = new XYSeries("f(x)");

        double x = 0.5, a = -3, b = 4, c = 1.2;
       

        // Перша частина: e^(cubic root((ax^2 - b) / (c + x)))
        double ax2_minus_b = a * pow(x, 2) - b;       // Обчислення ax^2 - b
        double denominator1 = c + x;                 // c + x
        double cubicRoot = cbrt(ax2_minus_b / denominator1); // Кубічний корінь
        double expPart = exp(cubicRoot);             // eкспонента

        // Друга частина: cos(ax) / e^((x + b) / c)
        double cosPart = cos(a * x);                 // cos(ax)
        double denominator2 = exp((x + b) / c);      // e^((x + b) / c)
        double fractionPart = cosPart / denominator2; // Ділення

        // Загальний результат
        double result = expPart + fractionPart;

        System.out.printf("Результат обчислення: %.3f\n", result);

        double minX = Double.NaN, minF = Double.NaN, maxX = Double.NaN, maxF = Double.NaN;
        boolean first = true;

        System.out.printf("%-10s %-10s\n", "x", "f(x)");
        System.out.println("--------------------------");

        for (double xVal = -10; xVal <= 5; xVal += 0.1) {
            double f;
            try {
                if (c - cbrt(xVal) == 0) {
                    System.err.println("Ділення на нуль при x = " + xVal);
                    continue; // Пропустити цю ітерацію
                }
                f = exp(a * cos(xVal + 2)) - (exp(-sin(b * xVal))) / (c - cbrt(xVal));

                // Перевірка на NaN або безмежність
                if (Double.isNaN(f) || Double.isInfinite(f)) {
                    continue;
                }

                series.add(xVal, f);
                System.out.printf("%-10.2f %-10.2f\n", xVal, f);

                if (first || f < minF) {
                    minX = xVal;
                    minF = f;
                }
                if (first || f > maxF) {
                    maxX = xVal;
                    maxF = f;
                }

                first = false;
            } catch (Exception e) {
                System.err.println("Помилка обчислення значення функції при x = " + xVal + ": " + e.getMessage());
            }
        }

        System.out.println("--------------------------");
        System.out.printf("Знайдені екстремуми:%nМінімум: x = %.2f, f(x) = %.2f%nМаксимум: x = %.2f, f(x) = %.2f%n", minX, minF, maxX, maxF);

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);

        JFreeChart chart = ChartFactory.createXYLineChart(
                "Графік функції з екстремумами",
                "x", "f(x)",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(800, 600));
        setContentPane(chartPanel);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MathCalcGraph chart = new MathCalcGraph("Побудова графіку та екстремуми");
        });
    }
}
