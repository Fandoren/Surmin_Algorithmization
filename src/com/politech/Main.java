package com.politech;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Double start, end, step, x;
        int err;

        do {
            System.out.println("Введите в одну строку:\nНачальный X, Конечный X, Дельта X (разделитель ';')");
            Scanner scanner = new Scanner(System.in);
            String[] numbers = scanner.nextLine().replace(" ", "").replace(",",".").split(";");
            err = 0;
            try {
                start = Double.parseDouble(numbers[0]);
                end = Double.parseDouble(numbers[1]);
                step = Double.parseDouble(numbers[2]);
            } catch (Exception e) {
                start = null;
                end = null;
                step = null;
            }

            if (start == null && end == null && step == null) {
                System.out.println("Введены данные в неверном формате. Пожалуйста, введите три цифры, разделённые знаком ';'");
                err = -1;
            } else {
                if (Math.abs(end - start) < step
                        || step == 0
                        || (end - start) / step < 0) {
                    err = -1;
                    System.out.println("Неверно задан шаг аргумента");
                }
                if (end - start < 0 && step >= 0) {
                    err = -1;
                    System.out.println("Неверно задан интервал");
                }
            }
        } while (err < 0);

        System.out.println(
                        "---------------------------------------------------------------------\n" +
                        "    N   |      X        |     Y1(x)     |     Y2(x)     |  MIN(Y1,Y2)\n" +
                        "---------------------------------------------------------------------");
        int currentStep, overallSteps;
        overallSteps = (int) (Math.abs((end - start) / step) + 1);
        x = start;
        for (currentStep = 1; currentStep <= overallSteps; currentStep++)
        {
            Double y1, y2;
            System.out.printf("%5d\t|%8.2f\t", currentStep , x);
            y1 = calculateFirstParam(x);
            if (y1 != null)
            {
                System.out.printf("\t|%10.3f", y1);
                System.out.print("\t");
            }
            else {
                System.out.print("\t|Не вычислимо");
            }
            y2 = calculateSecondParam(x);
            if (y2 != 0)
            {
                System.out.printf("\t|%10.3f", y2);
                System.out.print("\t");
            } else {
                System.out.print("\t|Не вычислимо");
            }

            if (y1 != null && y2 != null) {
                System.out.printf("\t|%10.3f", min(y1, y2));
                System.out.print("\t");
            } else {
                System.out.print("\t|Не вычислимо");
            }

            System.out.println();
            x += step;
        }
        System.out.print("---------------------------------------------------------------------\n");
    }

    private static double min(Double y1, Double y2) {
        return Math.min(y1, y2);
    }

    private static Double calculateFirstParam(double x) {
        double p1 = Math.cos(x - 85);
        if (p1 == 0) {
            return null;
        }
        double p2 = (1 - 85) / p1;
        if (p2 <= 0) {
            return null;
        }
        return Math.log(p2) / (Math.log(Math.abs(85 / 10) + 4));
    }

    private static Double calculateSecondParam(double x) {
        double p1 = Math.sin(x);
        if (p1 != 0) {
            return Math.sin(x) / 85;
        } else {
            return null;
        }
    }
}
