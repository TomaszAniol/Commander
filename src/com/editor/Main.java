package com.editor;


import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    private static Scanner in = new Scanner(System.in);

    public static void main(String[] args) {

        boolean quite = false;
        DataBaseOperator DboMenu = new DataBaseOperator();
        FilesOperatorMenu FoMenu = new FilesOperatorMenu();

        while (!quite) {
            try {
                System.out.println("1 - wejście do bazy danych, 2 - wejście do managera plików, 0 - koniec");
                int choosen = Integer.parseInt(in.nextLine());
                switch (choosen) {
                    case 0:
                        quite = true;
                        break;
                    case 1:
                        DboMenu.ShowMenu();
                        break;
                    case 2:
                        FoMenu.ShowMenu();
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Została wpisana litera zamiast cyfry - Wyjście do głównego menu");
                quite = true;
            } catch (NumberFormatException e) {
                System.out.println("Liczba z poza zakresu - Wyjście do głównego menu");
                quite = true;
            }
        }
    }
}
