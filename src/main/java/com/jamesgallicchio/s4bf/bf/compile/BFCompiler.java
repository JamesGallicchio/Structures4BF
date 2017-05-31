package com.jamesgallicchio.s4bf.bf.compile;

import com.jamesgallicchio.s4bf.bf.BFByteFormat;

public class BFCompiler {

    public static byte[] compile(String program) {
        byte[] bytes = new byte[program.length()];
        int count = 0;
        for(char c : program.toCharArray()) {
            switch(c) {
                case '.': bytes[count++] = BFByteFormat.OUT; break;
                case ',': bytes[count++] = BFByteFormat.IN; break;
                case '+': bytes[count++] = BFByteFormat.UP; break;
                case '-': bytes[count++] = BFByteFormat.DOWN; break;
                case '>': bytes[count++] = BFByteFormat.RIGHT; break;
                case '<': bytes[count++] = BFByteFormat.LEFT; break;
                case '[': bytes[count++] = BFByteFormat.BRAC_LEFT; break;
                case ']': bytes[count++] = BFByteFormat.BRAC_RIGHT; break;
            }
        }
        byte[] result = new byte[count];
        System.arraycopy(bytes, 0, result, 0, count);
        return result;
    }
}
