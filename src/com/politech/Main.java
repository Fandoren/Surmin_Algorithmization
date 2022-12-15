package com.politech;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        double xn, xk, h, num, x;
        MutableDouble y1 = new MutableDouble(0.0);
        MutableDouble y2 = new MutableDouble(0.0);
        int err = 0, err2;

        do {
            System.out.println("Введите в одну строку:\nХначальное Хконечное Шаг ПраметрNUM (разделитель ',')");
            Scanner scanner = new Scanner(System.in);
            String[] numbers = scanner.nextLine().replace(" ", "").split(",");
            xn = Double.parseDouble(numbers[0]);
            xk = Double.parseDouble(numbers[1]);
            h = Double.parseDouble(numbers[2]);
            num = Double.parseDouble(numbers[3]);

            err = 0;

            if( Math.abs(xk - xn) < h
                    || h == 0
                    || (xk - xn) / h < 0) {
                err = -2;
                System.out.println("Неверно задан шаг аргумента");
            }
            if (xk - xn  < 0 && h >= 0) {
                err = -1;
                System.out.println("Неверно задан интервал");
            }
        } while (err < 0);

        System.out.println(
                "---------------------------------------------------------------------\n" +
                        "    N   |      X        |     Y1(x)     |     Y2(x)     |  MIN(Y1,Y2)\n" +
                        "---------------------------------------------------------------------");
        y1.setValue(0.0);
        y2.setValue(0.0);
        int n, m;
        m = (int) (Math.abs((xk - xn) / h) + 1);
        x = xn;
        for (n = 1; n <= m; n++)
        {
            System.out.printf("%5d\t|%8.2f\t", n , x);
            err = f1(y1, x, num);
            if (err > 0)
            {
                System.out.printf("\t|%10.3f", y1.getValue());
            }
            else {
                System.out.print("\t|Не вычислимо");
            }

            err2 = f2(y2, x, num);
            if (err2 > 0)
            {
                System.out.printf("\t|%10.3f\t", y2.getValue());
            } else {
                System.out.print("\t|Не вычислимо");
            }

            if (err > 0 && err2 > 0) {
                System.out.printf("\t|%10.3f\t", min(y1, y2));
            } else {
                System.out.print("\t|Не вычислимо");
            }

            System.out.println();
            x += h;
        }
        System.out.print("---------------------------------------------------------------------\n");
    }

    private static double min(MutableDouble y1, MutableDouble y2) {
        return Math.min(y1.getValue(), y2.getValue());
    }

    private static int f1(MutableDouble y, double x, double num) {
        double p1 = 0;
        double p2 = 0;
        p1 = Math.cos(x - num);
        if (p1 == 0)
            return -1;
        p2 = (1 - num) / p1;
        if (p2 <= 0)
            return -2;
        y.setValue(Math.log(p2) / (Math.log(Math.abs(num / 10) + 4))); // по свойству логарифма
        return 1;
    }

    private static int f2(MutableDouble y, double x, double num) {
        if (num == 0) {
            return -1;
        }
        double p1 = Math.sin(x);
        if (p1 != 0) {
            y.setValue(Math.sin(x) / num);
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
