package com.politech;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        double start, end, step, x;
        MutableDouble y1 = new MutableDouble(0.0);
        MutableDouble y2 = new MutableDouble(0.0);
        int err = 0, err2;

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
                throw new IllegalArgumentException("Введены данные в неверном формате. Пожалуйста, вводите только цифры");
            }

            if( Math.abs(end - start) < step
                    || step == 0
                    || (end - start) / step < 0) {
                err = -1;
                System.out.println("Неверно задан шаг аргумента");
            }
            if (end - start  < 0 && step >= 0) {
                err = -1;
                System.out.println("Неверно задан интервал");
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
            System.out.printf("%5d\t|%8.2f\t", currentStep , x);
            err = calculateFirstParam(y1, x);
            if (err > 0)
            {
                System.out.printf("\t|%10.3f", y1.getValue());
                System.out.print("\t");
            }
            else {
                System.out.print("\t|Не вычислимо");
            }

            err2 = calculateSecondParam(y2, x);
            if (err2 > 0)
            {
                System.out.printf("\t|%10.3f", y2.getValue());
                System.out.print("\t");
            } else {
                System.out.print("\t|Не вычислимо");
            }

            if (err > 0 && err2 > 0) {
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

    private static double min(MutableDouble y1, MutableDouble y2) {
        return Math.min(y1.getValue(), y2.getValue());
    }

    private static int calculateFirstParam(MutableDouble y, double x) {
        double p1 = 0;
        double p2 = 0;
        p1 = Math.cos(x - 85);
        if (p1 == 0)
            return -1;
        p2 = (1 - 85) / p1;
        if (p2 <= 0)
            return -1;
        y.setValue(Math.log(p2) / (Math.log(Math.abs(85 / 10) + 4))); // по свойству логарифма
        return 1;
    }

    private static int calculateSecondParam(MutableDouble y, double x) {
        double p1 = Math.sin(x);
        if (p1 != 0) {
            y.setValue(Math.sin(x) / 85);
            return 1;
        } else {
            return -1;
        }
    }

    static class MutableDouble {

        private double value;

        public MutableDouble(double value) {
            this.value = value;
        }

        public double getValue() {
            return this.value;
        }

        public void setValue(double value) {
            this.value = value;
        }
    }
}
