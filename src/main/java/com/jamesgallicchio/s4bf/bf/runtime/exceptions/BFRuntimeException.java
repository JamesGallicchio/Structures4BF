package com.jamesgallicchio.s4bf.bf.runtime.exceptions;

public class BFRuntimeException extends RuntimeException {

    public BFRuntimeException(String reason) {
        super(reason);
    }

    public BFRuntimeException(String reason, byte[] program, int index) {
        super(reason + " " + commandsAround(program, index));
    }

    private static String commandsAround(byte[] program, int index) {
        int start = index - 5, end = index + 5;
        if (start < 0) start = 0;
        if (end >= program.length) end = program.length-1;

        StringBuilder sb = new StringBuilder();
        if (index > 0) {
            sb.append("...");
        }
        for(int i = start; i <= end; i++) {
            if(i == index) {
                sb.append(" {");
            }
            switch(program[i]) {
                case 0b0000001: //In
                    sb.append(','); break;
                case 0b0000010: //Out
                    sb.append('.'); break;
                case 0b0000100: //++
                    sb.append('+'); break;
                case 0b0001000: //--
                    sb.append('-'); break;
                case 0b0010000: //>
                    sb.append('>'); break;
                case 0b0100000: //<
                    sb.append('<'); break;
                case 0b1000000: //[
                    sb.append('['); break;
                case -0b10000000: //]
                    sb.append(']'); break;
                default:
                    sb.append('?');
            }
            if(i == index) {
                sb.append("} ");
            }
        }

        if(end < program.length - 1) {
            sb.append("...");
        }

        return sb.toString();
    }
}
