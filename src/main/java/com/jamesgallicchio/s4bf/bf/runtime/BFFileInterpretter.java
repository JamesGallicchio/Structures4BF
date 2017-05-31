package com.jamesgallicchio.s4bf.bf.runtime;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class BFFileInterpretter {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("No file specified! Specify a file as the first argument.");
        } else if (!args[0].endsWith(".bf")) {
            System.out.println("File is not of type .bf! Use BFTextCompiler to compile a text file to .bf!");
        } else {
            try {
                byte[] bytes = Files.readAllBytes(Paths.get(args[0]));
                BFInterpreter interp = new BFInterpreter(bytes, System.in, System.out);
                interp.run();
            } catch (FileNotFoundException e) {
                System.out.println("Can't find that file!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
