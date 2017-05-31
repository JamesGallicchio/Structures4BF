package com.jamesgallicchio.s4bf.bf.runtime.exceptions;

public class BFMemoryException extends BFRuntimeException {
    public BFMemoryException(String reason, byte[] program, int index) {
        super(reason, program, index);
    }
}
