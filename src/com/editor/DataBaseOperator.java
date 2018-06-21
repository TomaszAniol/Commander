package com.editor;


import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.*;

public class DataBaseOperator {
    private Scanner in = new Scanner(System.in);
    private List<DataList> addressBook = new ArrayList<>();
    private String fileName = "AddressBook.dat";

    public void ShowMenu() {
        int choosenMenu = 0;
        boolean quite = false;
        while (!quite) {
            try {
                System.out.println("1 - Dodaj adres, 2 - Zapisz dodane adresy, " +
                        "3 - Pokaż adresy, 4 - Usuń adres, 5 - Defragmentowanie pliku, 6 - Utwórz kopię zapasową, " +
                        "7 - Przywróć kopię zapasową, 0 - Wyjdź");
                choosenMenu = Integer.parseInt(in.nextLine());
                switch (choosenMenu) {
                    case 0:
                        quite = true;
                        break;
                    case 1:
                        AddAddressToList();
                        break;
                    case 2:
                        SaveToExistingFile();
                        break;
                    case 3:
                        LoadSavedFile();
                        break;
                    case 4:
                        DeleteRecord();
                        break;
                    case 5:
                        DefragmentBase();
                        break;
                    case 6:
                        MakeBackUpCopy();
                        break;
                    case 7:
                        RestoreDataBase();
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

    private void AddAddressToList() {
        String name;
        String surname;
        String city;
        String street;
        String zipCode;
        String phoneNumber;
        System.out.println("Podaj imie:");
        name = in.nextLine();
        System.out.println("Podaj nazwisko:");
        surname = in.nextLine();
        System.out.println("Podaj miasto:");
        city = in.nextLine();
        System.out.println("Podaj ulicę:");
        street = in.nextLine();
        System.out.println("Podaj kod pocztowy:");
        zipCode = in.nextLine();
        System.out.println("Podaj numer telefonu");
        phoneNumber = in.nextLine();
        addressBook.add(new DataList(name, surname, street, zipCode, city, phoneNumber));


    }

    private void CreateDataBase() {
        try (RandomAccessFile rao = new RandomAccessFile(fileName, "rwd")) {
            List<Integer> listOfRecords = new ArrayList<>();
            int endPosition = 0;
            rao.seek(1024 * Integer.BYTES);
            listOfRecords = WriteAddressBookToFile(rao, listOfRecords);
            endPosition = (int) rao.getFilePointer();
            rao.seek(0);
            rao.writeInt(endPosition);
            rao.writeInt(listOfRecords.size());
            for (int j = 0; j < listOfRecords.size(); j++) {
                rao.writeInt(listOfRecords.get(j));
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void SaveToExistingFile() {
        try (RandomAccessFile rao = new RandomAccessFile(fileName, "rwd")) {
            List<Integer> listOfRecords;
            int fileEndPosition = 0;
            listOfRecords = LoadListOfRecords(rao);
            rao.seek(0);
            fileEndPosition = rao.readInt();
            rao.seek(fileEndPosition);
            listOfRecords = WriteAddressBookToFile(rao, listOfRecords);
            fileEndPosition = (int) rao.getFilePointer();
            rao.seek(0);
            rao.writeInt(fileEndPosition);
            rao.writeInt(listOfRecords.size());
            for (int j = 0; j < listOfRecords.size(); j++) {
                rao.writeInt(listOfRecords.get(j));
            }
            System.out.println("Zapisane");
            addressBook = null;
            addressBook = new ArrayList<>();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private List<Integer> WriteAddressBookToFile(RandomAccessFile rao, List<Integer> listOfRecords)
            throws IOException {

        for (int j = 0; j < addressBook.size(); j++) {
            listOfRecords.add((int) rao.getFilePointer());
            rao.writeUTF(addressBook.get(j).getName());
            rao.writeUTF(addressBook.get(j).getSurname());
            rao.writeUTF(addressBook.get(j).getCity());
            rao.writeUTF(addressBook.get(j).getStreet());
            rao.writeUTF(addressBook.get(j).getZipCode());
            rao.writeUTF(addressBook.get(j).getPhoneNumber());
        }
        return listOfRecords;
    }

    private void DeleteRecord(){
        try(RandomAccessFile rao = new RandomAccessFile(fileName, "rwd")) {
            List<String> dataToCopy = new ArrayList<>();
            System.out.println("Podaj numer rekordu do usunięcia: ");
            int choosen = Integer.parseInt(in.nextLine()) - 1;
            List<Integer> listToRead = LoadListOfRecords(rao);
            listToRead.remove(choosen);
            rao.seek(0);
            rao.readInt();
            rao.writeInt(listToRead.size());
            for (int j = 0; j < listToRead.size(); j++) {
                rao.writeInt(listToRead.get(j));
            }

        }catch (IOException e){
            e.getMessage();
        }
    }

    private void LoadSavedFile() {
        try (RandomAccessFile rao = new RandomAccessFile(fileName, "rwd")) {
            List<Integer> listToRead;
            listToRead = LoadListOfRecords(rao);
            int positionOnTheList = 0;
            int pointerPosition = 0;
            boolean quiet = false;
            while (!quiet) {
                System.out.println("1 - dalej, 2 - powrót, 3 - pokaż rekordy według nazwiska, " +
                        "4 - Zobacz rekord wg numeru, 0 - Wyjdź");
                int choosenOption = Integer.parseInt(in.nextLine());
                switch (choosenOption) {
                    case 0:
                        quiet = true;
                        break;
                    case 1:
                        positionOnTheList++;
                        if (positionOnTheList > listToRead.size() - 1 || positionOnTheList < 0)
                        {
                            System.out.println("Brak kolejnych rekordów");
                            positionOnTheList--;
                        }else {
                        pointerPosition = listToRead.get(positionOnTheList);
                        rao.seek(pointerPosition);
                        PrintOutRecord(rao);
                        }
                        break;
                    case 2:
                        positionOnTheList--;
                        if (positionOnTheList < 0 || positionOnTheList > listToRead.size() - 1){
                            System.out.println("Brak kolejnych rekordów");
                            positionOnTheList++;
                        }else {

                            pointerPosition = listToRead.get(positionOnTheList);
                            rao.seek(pointerPosition);
                            PrintOutRecord(rao);
                        }
                        break;
                    case 3:
                        for (int i = 0; i < listToRead.size(); i++) {
                            System.out.print("Pozycja: " + (i + 1) + " - ");
                            pointerPosition = listToRead.get(i);
                            rao.seek(pointerPosition);
                            rao.readUTF();
                            System.out.println(rao.readUTF());
                        }
                        break;
                    case 4:
                        System.out.println("Podaj nr rekordu:");
                        positionOnTheList = Integer.parseInt(in.nextLine());
                        if (positionOnTheList < 1 || positionOnTheList > listToRead.size()){
                            System.out.println("Brak rekordu w bazie");
                        }else {
                            pointerPosition = listToRead.get(--positionOnTheList);
                            rao.seek(pointerPosition);
                            PrintOutRecord(rao);
                        }
                        break;
                }
            }
        } catch (IOException e) {
            e.getMessage();
        }
    }

    private List<Integer> LoadListOfRecords(RandomAccessFile rao) throws IOException {
        List<Integer> listToRead = new ArrayList<>();
        rao.seek(0);
        rao.readInt();
        int endPosition = rao.readInt() * Integer.BYTES + (int) rao.getFilePointer();
        while ((int) rao.getFilePointer() < endPosition) {
            listToRead.add(rao.readInt());
        }
        return listToRead;
    }


    private void PrintOutRecord(RandomAccessFile rao) throws IOException {
        System.out.print(rao.readUTF() + " ");
        System.out.println(rao.readUTF());
        System.out.println(rao.readUTF());
        System.out.print(rao.readUTF() + " ");
        System.out.println(rao.readUTF());
        System.out.println(rao.readUTF());
    }

    private void DefragmentBase(){
        try(RandomAccessFile rao = new RandomAccessFile(fileName, "rwd")){
            MakeBackUpCopy();
            List<Integer> listOfRecords = LoadListOfRecords(rao);
            for (int i = 0; i < listOfRecords.size(); i++) {
                int pointerPosition = listOfRecords.get(i);
                rao.seek(pointerPosition);
                String name = rao.readUTF();
                String surname = rao.readUTF();
                String city = rao.readUTF();
                String street = rao.readUTF();
                String zipCode = rao.readUTF();
                String phoneNumber = rao.readUTF();
                addressBook.add(new DataList(name, surname, street, zipCode, city, phoneNumber));
                CreateDataBase();
            }

        }catch (IOException e){
            e.getMessage();
        }
    }

    private void RestoreDataBase(){
        FilesOperator restoreBase = FilesOperator.getInstance();
        restoreBase.CopyFile("BackUpAddressBook", "AddressBook", "dat");
        System.out.println("Kopia przywrócona");
    }

    private void MakeBackUpCopy(){
        FilesOperator backUpDataBase = FilesOperator.getInstance();
        backUpDataBase.CopyFile("AddressBook", "BackUpAddressBook", "dat");
        System.out.println("Kopia utworzona");
    }
}
