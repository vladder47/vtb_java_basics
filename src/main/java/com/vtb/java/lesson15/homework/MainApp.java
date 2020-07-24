package com.vtb.java.lesson15.homework;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class MainApp {
    public static void main(String[] args) {
        Path file = Paths.get("lesson14_files", "task1.txt");
        Path fileTwo = Paths.get("lesson14_files", "task21.txt");
        String subStr = "qwerty";
        String subStrTwo = "hello";
        int subStrCount = getSubstringCount(file, subStr);
        int subStrCountTwo = getSubstringCount(fileTwo, subStrTwo);
        System.out.printf("Подстрока %s встретилась в файле %d раз(а)\n", subStr, subStrCount);
        System.out.printf("Подстрока %s встретилась в файле %d раз(а)\n", subStrTwo, subStrCountTwo);

        int subStrCountThree = getSubstringCountTwo(file, subStr);
        int subStrCountFour = getSubstringCountTwo(fileTwo, subStrTwo);
        System.out.printf("Подстрока %s встретилась в файле %d раз(а)\n", subStr, subStrCountThree);
        System.out.printf("Подстрока %s встретилась в файле %d раз(а)\n", subStrTwo, subStrCountFour);

        Path mergedFile = Paths.get("lesson14_files", "merged_file_1.txt");
        Path catalog = Paths.get("lesson14_files", "txt_files");
        mergeTxtFiles(catalog, mergedFile);

        Path catalogToSearch = Paths.get("lesson14_files", "catalog");
        List<Path> smallFiles = searchSmallFilesInCatalog(catalogToSearch);
        System.out.println("Список файлов с размером менее 100 Кб в каталоге catalog (и его подкаталогах):");
        for (Path path : smallFiles) {
            System.out.println(path.getFileName());
        }
    }

    // метод, в котором размер ByteBuffer равен 8192 байтам
    public static int getSubstringCount(Path path, String substr) {
        int count = 0;
        try (RandomAccessFile file = new RandomAccessFile(path.toFile(), "r");
             FileChannel inChannel = file.getChannel()) {
            ByteBuffer buf = ByteBuffer.allocate(8192);
            int bytesRead = inChannel.read(buf);
            while (bytesRead != -1) {
                buf.flip();
                while (buf.hasRemaining()) {
                    int pos = buf.position();
                    // если дошли до момента, когда длина оставшейся части буфера
                    // меньше длины подстроки, то выходим из цикла (оставшаяся часть переносится в начало с помощью compact())
                    // (без такой проверки иногда выскакивало BufferUnderflowException)
                    if (pos <= buf.limit() - substr.length()) {
                        for (int i = 0; i < substr.length(); i++) {
                            int chr = buf.get();
                            if (substr.charAt(i) != chr) {
                                break;
                            }
                            if (i == substr.length() - 1) {
                                count += 1;
                            }
                        }
                        buf.position(pos + 1);
                    } else {
                        buf.compact();
                        break;
                    }
                }
                bytesRead = inChannel.read(buf);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return count;
    }

    // метод, в котором размер ByteBuffer равен длине подстроки, которую необходимо найти
    public static int getSubstringCountTwo(Path path, String substr) {
        int count = 0;
        try (RandomAccessFile file = new RandomAccessFile(path.toFile(), "r");
             FileChannel inChannel = file.getChannel()) {
            ByteBuffer buf = ByteBuffer.allocate(substr.length());
            // позиция, с которой будут считываться данные в канале
            int bufPos = 0;
            int bytesRead = inChannel.read(buf, bufPos);
            while (bytesRead != -1) {
                buf.flip();
                while (buf.hasRemaining()) {
                    int pos = buf.position();
                    int chr = buf.get();
                    if (chr != substr.charAt(pos)) {
                        break;
                    }
                    if (pos == buf.limit() - 1) {
                        count += 1;
                    }
                }
                buf.clear();
                bufPos += 1;
                bytesRead = inChannel.read(buf, bufPos);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return count;
    }

    public static void mergeTxtFiles(Path catalog, Path result) {
        if (Files.isDirectory(catalog)) {
            try (DirectoryStream<Path> files = Files.newDirectoryStream(catalog);
                 RandomAccessFile fileOut = new RandomAccessFile(result.toFile(), "rw");
                 FileChannel outChannel = fileOut.getChannel()) {
                for (Path path : files) {
                    if (path.toString().endsWith(".txt")) {
                        try (RandomAccessFile file = new RandomAccessFile(path.toFile(), "r");
                             FileChannel inChannel = file.getChannel()) {
                            ByteBuffer buf = ByteBuffer.allocate(8192);
                            int bytesRead = inChannel.read(buf);
                            while (bytesRead != -1) {
                                buf.flip();
                                outChannel.write(buf);
                                buf.clear();
                                bytesRead = inChannel.read(buf);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            throw new NotValidPathToFileException("Данный путь не является каталогом");
        }
    }

    public static List<Path> searchSmallFilesInCatalog(Path catalog) {
        List<Path> result = new ArrayList<>();
        if (Files.isDirectory(catalog)) {
            try {
                Files.walkFileTree(catalog, new FileVisitor<>() {
                    @Override
                    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
                        return FileVisitResult.CONTINUE;
                    }

                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                        if (Files.size(file) <= 102400) {
                            result.add(file);
                        }
                        return FileVisitResult.CONTINUE;
                    }

                    @Override
                    public FileVisitResult visitFileFailed(Path file, IOException exc) {
                        return FileVisitResult.TERMINATE;
                    }

                    @Override
                    public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
                        return FileVisitResult.CONTINUE;
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            throw new NotValidPathToFileException("Был передан путь до файла");
        }
        return result;
    }
}
