package com.jamesgallicchio.s4bf.bf.compile;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class BFTextFileCompiler {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("No file specified! Specify the file as the first argument.");
        } else {
            try {
                Path p = Paths.get(args[0]);
                String program = new String(Files.readAllBytes(p));
                p = p.resolveSibling(p.getFileName().toString().split(".")[0] + ".bf");
                Files.write(p, BFCompiler.compile(program));
            } catch (FileNotFoundException e) {
                System.out.println("File couldn't be found.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}