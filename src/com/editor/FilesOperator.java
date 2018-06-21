package com.editor;


import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;


import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class FilesOperator {

    private static FilesOperator instance;

    private FilesOperator() {
        //Singleton
    }

    public static FilesOperator getInstance() {
        if (instance == null) {
            instance = new FilesOperator();
        }

        return instance;
    }

    public void CreateFile(String fileName, String fileExtend) {
        try {
            Path filePath = FileSystems.getDefault().getPath(fileName + "." + fileExtend);
            Files.createFile(filePath);
        } catch (IOException e) {
            e.getMessage();
        }
    }

    public void DelFile(String fileName, String fileExtend) {
        try {
            Path filePath = FileSystems.getDefault().getPath(fileName + "." + fileExtend);
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void CreateDir(String directoryName) {
        try {
            Path directoryPath = FileSystems.getDefault().getPath(directoryName);
            Files.createDirectory(directoryPath);
        } catch (IOException e) {
            e.getMessage();
        }
    }

    public void DelDir(String directoryName) {
        try {
            Path directoryPath = FileSystems.getDefault().getPath(directoryName);
            Files.deleteIfExists(directoryPath);
        } catch (IOException e) {
            e.getMessage();
        }
    }

    public void CopyFile(String fileName, String nameOfCopyFile, String fileExtend) {
        try {
            Path directoryPath = FileSystems.getDefault().getPath(fileName + "." + fileExtend);
            Path directoryPathCopy = FileSystems.getDefault().getPath(nameOfCopyFile + "." + fileExtend);
            Files.copy(directoryPath, directoryPathCopy, REPLACE_EXISTING);
        } catch (IOException e) {
            e.getMessage();
        }
    }

    public void ShowAllFiles(String filesDirectory) {
        try {
            Path filePath = FileSystems.getDefault().getPath(filesDirectory);
            Files.walkFileTree(filePath, new SimpleFileVisitor<Path>() {

                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    System.out.println(dir.toAbsolutePath());
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    System.out.println(file.toAbsolutePath());
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            e.getMessage();
        }
    }

    public void CopyTree(String oldDirectoryName, String newDirectoryName) {
        try {
            Path oldPath = FileSystems.getDefault().getPath(oldDirectoryName);
            Path newPath = FileSystems.getDefault().getPath(newDirectoryName);
            Files.walkFileTree(oldPath, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    Path targetFile = newPath.resolve(oldPath.relativize(file));
                    Files.copy(file, targetFile);

                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    Path targetDir = newPath.resolve(oldPath.relativize(dir));
                    Files.copy(dir, targetDir);
                    return FileVisitResult.CONTINUE;
                }

            });
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }

    public void DelFolderWithFilesInside(String directoryName) {

        try {
            Path filePath = FileSystems.getDefault().getPath(directoryName);
            Files.walkFileTree(filePath, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    Files.delete(dir);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    Files.delete(file);
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }


}
