package com.vtb.java.lesson14.homework;

import java.io.File;
import java.io.FilenameFilter;

public class TxtFileFilter implements FilenameFilter {
    @Override
    public boolean accept(File dir, String filename) {
        return filename.endsWith(".txt");
    }
}
