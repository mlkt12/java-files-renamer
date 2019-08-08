package com.company;

import java.io.File;
import java.util.function.IntPredicate;
public class Main {

    public static void main(String[] args) {
        try {
            String path = args[0];
            listFilesForFolder(new File(path));
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private static void listFilesForFolder(final File folder) {
        try {
            for (final File fileEntry : folder.listFiles()) {
                if (fileEntry.isDirectory()) {
                    listFilesForFolder(fileEntry);
                } else {
                    if (getFileExtension(fileEntry).equals(".png") && containsUpperCase(fileEntry.getName()) || fileEntry.getName().contains("_")) {
                        renameMePlease(fileEntry);
                    }
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public static String getFileExtension(File file) {
        String name = file.getName();
        int lastIndexOf = name.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return ""; // empty extension
        }
        return name.substring(lastIndexOf);
    }

    private static void renameMePlease(File file) {
        String toKebabCase = file.getName().replaceAll("_","-"); // camelCase to kebabCase
        toKebabCase = toKebabCase.replaceAll("([A-Z]+)", "-$1").toLowerCase().replaceAll("--","-");

        String renamed = toKebabCase;

        if (renamed.startsWith("-"))
            renamed = renamed.substring(1);

        try {
            boolean result = file.renameTo(new File(file.toPath().resolveSibling(renamed).toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static boolean contains(String value, IntPredicate predicate) {
        return value.chars().anyMatch(predicate);
    }
    private static boolean containsUpperCase(String value) {
        return contains(value, i -> Character.isLetter(i) && Character.isUpperCase(i));
    }

}

