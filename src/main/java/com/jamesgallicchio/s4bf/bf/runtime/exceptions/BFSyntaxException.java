package com.jamesgallicchio.s4bf.bf.runtime.exceptions;

public class BFSyntaxException extends BFRuntimeException {
    public BFSyntaxException(String reason, byte[] program, int index) {
        super(reason, program, index);
    }
}
