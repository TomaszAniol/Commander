package com.editor;

import java.util.InputMismatchException;
import java.util.Scanner;

public class FilesOperatorMenu {

    private Scanner in;
    private int selectedOption;
    private FilesOperator OperationOnFile;
    private String nameOfObject;
    private String nameOfDirectionToCopy;
    private String extensionOfObject;
    private boolean quite;



    public FilesOperatorMenu() {

        this.in = new Scanner(System.in);
        selectedOption = 0;
        OperationOnFile = FilesOperator.getInstance();
        nameOfObject = "";
        nameOfDirectionToCopy = "";
        extensionOfObject = "";
        quite = false;
    }

    private void WriteMenu() {
        System.out.println("1 - Utwórz plik");
        System.out.println("2 - Usuń plik");
        System.out.println("3 - Utwórz folder");
        System.out.println("4 - Usuń folder");
        System.out.println("5 - Kopiuj plik");
        System.out.println("6 - Pokaż pliki w katalogu");
        System.out.println("7 - Kopiuj wszystkie pliki");
        System.out.println("8 - Usuń folder z całą zawartością");
        System.out.println("9 - Pokaż menu");
        System.out.println("0 - Wyjdź");
    }

    public void ShowMenu() {

        WriteMenu();
        while (!quite) {

            try {

                System.out.println("Wybierz opcję: ");
                selectedOption = in.nextInt();
                if (selectedOption < 0 || selectedOption > 9)
                    System.out.println("Błędny zakres");
                else {
                    SelectMethod(selectedOption);
                }
            } catch (InputMismatchException e) {
                System.out.println("Powrót do głównego menu");
                quite = true;
            }


        }
    }


    private void SelectMethod(int selectedOption) {

        switch (selectedOption) {

            case 1:
                System.out.println("Podaj nazwę pliku: ");
                nameOfObject = in.next();
                System.out.println("Podaj rozszerzenie pliku: ");
                extensionOfObject = in.next();
                OperationOnFile.CreateFile(nameOfObject, extensionOfObject);
                break;

            case 2:
                System.out.println("Podaj nazwę pliku: ");
                nameOfObject = in.next();
                System.out.println("Podaj rozszerzenie pliku: ");
                extensionOfObject = in.next();
                OperationOnFile.DelFile(nameOfObject, extensionOfObject);
                break;

            case 3:
                System.out.println("Podaj nazwę folderu: ");
                nameOfObject = in.next();
                OperationOnFile.CreateDir(nameOfObject);
                break;

            case 4:
                System.out.println("Podaj nazwę folderu: ");
                nameOfObject = in.next();
                OperationOnFile.DelDir(nameOfObject);
                break;

            case 5:
                System.out.println("Podaj nazwę pliku: ");
                nameOfObject = in.next();
                System.out.println("Podaj nazwe kopii: ");
                String nameOfCopyFile = in.next();
                System.out.println("Podaj rozszerzenie pliku: ");
                extensionOfObject = in.next();
                OperationOnFile.CopyFile(nameOfObject, nameOfCopyFile, extensionOfObject);
                break;

            case 6:
                System.out.println("Podaj nazwę folderu: ");
                nameOfObject = in.next();
                OperationOnFile.ShowAllFiles(nameOfObject);
                break;

            case 7:
                System.out.println("Podaj nazwę folderu: ");
                nameOfObject = in.next();
                System.out.println("Podaj nazwę folderu fo którego mają być skopiowane pliki: ");
                nameOfDirectionToCopy = in.next();
                OperationOnFile.CopyTree(nameOfObject, nameOfDirectionToCopy);
                break;

            case 8:
                System.out.println("Podaj nazwę folderu: ");
                nameOfObject = in.next();
                OperationOnFile.DelFolderWithFilesInside(nameOfObject);
                break;

            case 9:
                WriteMenu();
                break;

            case 0:
                quite = true;
                break;
        }
    }
}
