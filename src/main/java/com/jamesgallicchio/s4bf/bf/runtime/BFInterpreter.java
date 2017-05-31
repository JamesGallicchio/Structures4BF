package com.jamesgallicchio.s4bf.bf.runtime;

import com.jamesgallicchio.s4bf.bf.BFByteFormat;
import com.jamesgallicchio.s4bf.bf.runtime.exceptions.BFMemoryException;
import com.jamesgallicchio.s4bf.bf.runtime.exceptions.BFSyntaxException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class BFInterpreter {

    public static final int BYTES_PER_CHUNK = 1024;
    public static final int MAX_MEMORY_LENGTH = 1048576;

    public final byte[] bytes;
    public final InputStream in;
    public final OutputStream out;

    private int comPointer;
    private byte[] memory;
    private int memPointer;

    public BFInterpreter(byte[] bytes, InputStream in, OutputStream out) {
        this.bytes = bytes;
        this.in = in;
        this.out = out;
    }

    public void run() throws IOException {
        comPointer = 0;
        memPointer = 0;
        memory = new byte[BYTES_PER_CHUNK];

        List<Integer> leftBracs = new ArrayList<>();

        while(comPointer < bytes.length) {
            byte b = bytes[comPointer];
            switch(b) {
                case BFByteFormat.IN: //In
                    memory[memPointer] = (byte) in.read(); break;
                case BFByteFormat.OUT: //Out
                    out.write(memory[memPointer]); break;
                case BFByteFormat.UP: //++
                    memory[memPointer]++; break;
                case BFByteFormat.DOWN: //--
                    memory[memPointer]--; break;
                case BFByteFormat.RIGHT: //>
                    memPointer++;
                    if(memPointer == memory.length) {
                        if(memPointer >= MAX_MEMORY_LENGTH) {
                            throw new BFMemoryException("Exceeded maximum memory usage!", bytes, comPointer);
                        }
                        byte[] temp = new byte[memPointer + BYTES_PER_CHUNK];
                        System.arraycopy(memory, 0, temp, 0, memPointer);
                        memory = temp;
                    } break;
                case BFByteFormat.LEFT: //<
                    if (memPointer != 0) {
                        memPointer--;
                        break;
                    } else throw new BFMemoryException("Attempted to shift left past start!", bytes, comPointer);
                case BFByteFormat.BRAC_LEFT: //[
                    if (memory[memPointer] == 0) {
                        int otherBracs = 0;
                        int point = comPointer;
                        while (point < bytes.length && (bytes[point] != BFByteFormat.BRAC_RIGHT || otherBracs != 0)) {
                            if(bytes[point] == BFByteFormat.BRAC_LEFT) otherBracs++;
                            else if(bytes[point] == BFByteFormat.BRAC_RIGHT) otherBracs--;
                            point++;
                        } if (point == bytes.length || otherBracs != 0) {
                            throw new BFSyntaxException("Closing right bracket not found!", bytes, comPointer);
                        }
                    } else {
                        leftBracs.add(comPointer);
                    }
                case BFByteFormat.BRAC_RIGHT: //]
                    if (memory[memPointer] != 0) {
                        comPointer = leftBracs.get(leftBracs.size()-1);
                    } else {
                        if (leftBracs.size() == 0) {
                            throw new BFSyntaxException("Opening left brackekt not found!", bytes, comPointer);
                        }

                        leftBracs.remove(leftBracs.size()-1);
                    }
                default:
                    throw new BFSyntaxException("Encountered an invalid byte!", bytes, comPointer);
            }
            comPointer++;
        }
    }
}
