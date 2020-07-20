package com.vtb.java.lesson14.homework;

import java.io.*;
import java.util.Vector;

public class MainApp {
    public static void main(String[] args) {
        File file = new File("lesson14_files/task1.txt");
        File fileTwo = new File("lesson14_files/task11.txt");
        String subStr = "qwerty";
        String subStrTwo = "qq";
        int subStrCount = getSubstringCount(file, subStr);
        int subStrCountTwo = getSubstringCount(fileTwo, subStrTwo);
        System.out.printf("Подстрока %s встретилась в файле %d раз(а)\n", subStr, subStrCount);
        System.out.printf("Подстрока %s встретилась в файле %d раз(а)\n", subStrTwo, subStrCountTwo);

        File catalog = new File("lesson14_files/txt_files");
        File mergedFile = new File("lesson14_files/merged_file.txt");
        try {
            mergeTextFiles(catalog, mergedFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        File catalogToDelete = new File("lesson14_files/catalog");
        deleteCatalog(catalogToDelete);
    }

    public static int getSubstringCount(File file, String substr) {
        int count = 0;
        try (RandomAccessFile reader = new RandomAccessFile(file, "r")) {
            int chr;
            int pos = 0;
            int length = substr.length();
            while ((chr = reader.read()) > 0) {
                for (int i = 0; i < length; i++) {
                    if (chr != substr.codePointAt(i)) {
                        pos += 1;
                        break;
                    } else {
                        chr = reader.read();
                    }
                    if (i == length - 1) {
                        count += 1;
                        pos += 1;
                    }
                }
                reader.seek(pos);
            }
            return count;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return count;
    }

    // использовал Vector для передачи Enumeration в конструктор SequenceInputStream
    public static void mergeTextFiles(File catalog, File result) throws FileNotFoundException {
        FilenameFilter txtFilter = new TxtFileFilter();
        Vector<InputStream> inputStreams = new Vector<>();
        if (catalog.isDirectory()) {
            File[] files = catalog.listFiles(txtFilter);
            if (files != null) {
                for (File f : files) {
                    inputStreams.add(new FileInputStream(f));
                }
                try (InputStream seq = new BufferedInputStream(new SequenceInputStream(inputStreams.elements()));
                     OutputStream out = new BufferedOutputStream(new FileOutputStream(result))) {
                    int chr;
                    while ((chr = seq.read()) != -1) {
                        out.write(chr);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                throw new NotValidPathToFileException("В директории нет текстовых файлов");
            }
        } else {
            throw new NotValidPathToFileException("Данный путь не является каталогом");
        }
    }

    // если в качестве параметра будет передан файл, то будет выброшено исключение
    public static void deleteCatalog(File catalog) {
        if (catalog.isDirectory()) {
            File[] files = catalog.listFiles();
            if (files != null) {
                for (File f : files) {
                    if (f.isDirectory()) {
                        deleteCatalog(f);
                    } else {
                        f.delete();
                    }
                }
            }
            catalog.delete();
        } else {
            throw new NotValidPathToFileException("Был передан путь до файла");
        }
    }

    // если в качестве параметра будет передан файл, то он будет удален
//    public static void deleteCatalog(File catalog) {
//        if (catalog.isDirectory()) {
//            File[] files = catalog.listFiles();
//            if (files != null) {
//                for (File f : files) {
//                    deleteCatalog(f);
//                }
//            }
//        }
//        catalog.delete();
//    }
}
