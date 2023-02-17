package DisAsm;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;


public class Disassembler {
    final ElfFile file;

    public Disassembler(ElfFile file) {
        this.file = file;
    }

    private char[] readFourChars(Section section, int currentOffset) {
        return new char[]{section.getChar(currentOffset), section.getChar(currentOffset + 1),
                section.getChar(currentOffset + 2), section.getChar(currentOffset + 3)};
    }

    public void disassembly(PrintWriter out) {
        Section text = textSection();
        Map<Integer, String> addr = new HashMap<>();
        readSymtab(addr);
        addNewMarks(text, addr);
        int currentOffset = 0;
        while (currentOffset < text.getSize()) {
            int virtualAddress = text.getAddr() + currentOffset;
            long instruction = readInstructionLong(readFourChars(text, currentOffset));
            currentOffset += 4;
            int opcode = (int) (instruction % 128); // last 7 bits
            int func7 = (int) (instruction >> 25);
            String result;
            if (opcode == 0b0110011 && func7 == 1) {
                result = parseRV32MInstruction((int) instruction);
            } else {
                result = parseRV32IInstruction(instruction, addr, virtualAddress);
            }
            if (instruction != 0) {
                if (addr.containsKey(virtualAddress)) {
                    out.println(String.format("%08x:\t<%s>:", virtualAddress, addr.get(virtualAddress)));
                }
                if (!result.equals("")) {
                    out.println(String.format("  %05x:\t%08x\t%s", virtualAddress, instruction, result));
                }
            }
        }
    }

    private void addNewMarks(Section text, Map<Integer, String> addr) {
        int currentOffset = 0, LId = 0;
        while (currentOffset < text.getSize()) {
            int virtualAddress = currentOffset + text.getAddr();
            long instruction = readInstructionLong(readFourChars(text, currentOffset));
            currentOffset += 4;
            int opcode = (int) (instruction % 128);
            int offset = -1;
            if (opcode == 0b1101111) { // jal
                offset = virtualAddress + makeJTypeOffset(instruction);
            } else if (opcode == 0b1100011) { // b*
                offset = virtualAddress + makeBTypeOffset(instruction);
            }
            if (offset != -1 && !addr.containsKey(offset)) {
                addr.put(offset, String.format("L%d", LId++));
            }
        }
    }

    private String parseRV32IInstruction(long instruction, Map<Integer, String> addr, int virtualAddress) {
        int opcode = (int) (instruction % 128);
        String result = "";
        switch (opcode) {
            case 0b0110011 -> {
                int func7 = (int) (instruction >> 25), rs1 = (int) (instruction >> 15) % 32,
                        rs2 = (int) (instruction >> 20) % 32, rd = (int) (instruction >> 7) % 32;
                String command = "";
                switch (func7) {
                    case 0b0000000 -> {
                        int func3 = (int) (instruction >> 12) % 8;
                        command = switch (func3) {
                            case 0 -> "add";
                            case 1 -> "sll";
                            case 2 -> "slt";
                            case 3 -> "sltv";
                            case 4 -> "xor";
                            case 5 -> "slr";
                            case 6 -> "or";
                            case 7 -> "and";
                            default -> throw new IllegalStateException("Unexpected value: " + instruction);
                        };
                    } case 0b0100000 -> {
                        int func3 = (int) (instruction >> 12) % 8;
                        command = switch (func3) {
                            case 0 -> "sub";
                            case 5 -> "sra";
                            default -> throw new IllegalStateException("Unexpected value: " + instruction);
                        };
                    }
                }
                result = String.format("%7s\t%s, %s, %s", command, getRegisterABI(rd), getRegisterABI(rs1), getRegisterABI(rs2));
            }
            case 0b0010011 -> {
                int func3 = (int) (instruction >> 12) % 8;
                int func7 = (int) (instruction >> 25), rs1 = (int) (instruction >> 15) % 32, rd = (int) (instruction >> 7) % 32;
                int shamt = ((int) instruction >> 20) % 32, imm = (int) instruction >> 20;
                String command = switch (func3) {
                    case 0 -> "addi";
                    case 1 -> "slli";
                    case 2 -> "slti";
                    case 3 -> "sltiu";
                    case 4 -> "xori";
                    case 5 -> switch (func7) {
                        case 0 -> "srli";
                        case 0b0100000 -> "srai";
                        default -> throw new IllegalStateException("Unexpected value: " + instruction);
                    };
                    case 6 -> "ori";
                    case 7 -> "andi";
                    default -> throw new IllegalStateException("Unexpected value: " + instruction);
                };
                result = switch (command) {
                    case "slli", "srli", "srai" -> String.format("%7s\t%s, %s, %s", command, getRegisterABI(rd), getRegisterABI(rs1), shamt);
                    case "addi", "slti", "sltiu", "xori", "ori", "andi" -> String.format("%7s\t%s, %s, %s", command, getRegisterABI(rd), getRegisterABI(rs1), imm);
                    default -> throw new IllegalStateException("Unexpected value: " + instruction);
                };
            }
            case 0b0100011 -> {
                int func3 = (int) (instruction >> 12) % 8;
                int rs1 = (int) (instruction >> 15) % 32, rs2 = (int) (instruction >> 20) % 32;
                int offset = (int) ((instruction >> 7) % 32 + ((instruction >> 25) << 5) - ((instruction >> 31) % 2 == 0 ? 0 : 1 << 12));
                String command = switch (func3) {
                    case 0 -> "sb";
                    case 1 -> "sh";
                    case 2 -> "sw";
                    default -> throw new IllegalStateException("Unexpected value: " + instruction);
                };
                result = String.format("%7s\t%s, %s(%s)", command, getRegisterABI(rs2), offset, getRegisterABI(rs1));
            }
            case 0b0000011 -> {
                int func3 = (int) (instruction >> 12) % 8;
                int rs1 = (int) (instruction >> 15) % 32, rd = (int) (instruction >> 7) % 32;
                int offset = (int) ((instruction >> 20) - ((instruction >> 31) % 2 == 0 ? 0 : 1 << 12));
                String command = switch (func3) {
                    case 0 -> "lb";
                    case 1 -> "lh";
                    case 2 -> "lw";
                    case 4 -> "lbu";
                    case 5 -> "lhu";
                    default -> throw new IllegalStateException("Unexpected value: " + instruction);
                };
                result = String.format("%7s\t%s, %s(%s)", command, getRegisterABI(rd), offset, getRegisterABI(rs1));
            }
            case 0b1100011 -> {
                int func3 = (int) (instruction >> 12) % 8;
                int rs1 = (int) (instruction >> 15) % 32, rs2 = (int) (instruction >> 20) % 32;
                String command = switch (func3) {
                    case 0 -> "beq";
                    case 1 -> "bne";
                    case 2 -> "blt";
                    case 3 -> "bge";
                    case 4 -> "bltu";
                    case 5 -> "bgeu";
                    default -> throw new IllegalStateException("Unexpected value: " + instruction);
                };
                int offset = makeBTypeOffset(instruction) + virtualAddress;
                result = String.format("%7s\t%s, %s, 0x%x <%s>", command, getRegisterABI(rs1), getRegisterABI(rs2), offset, addr.get(offset));
            }
            case 0b0110111 -> {
                int rd = (int) (instruction >> 7) % 32;
                int imm = (int) instruction >> 12;
                result = String.format("%7s\t%s, 0x%x", "lui", getRegisterABI(rd), imm);
            }
            case 0b0010111 -> {
                int rd = (int) (instruction >> 7) % 32;
                int imm = (int) (instruction >> 12) << 12 + virtualAddress;
                result = String.format("%7s\t%s, %s", "auipc", getRegisterABI(rd), imm);
            }
            case 0b1101111 -> {
                int rd = (int) (instruction >> 7) % 32;
                int offset = makeJTypeOffset(instruction) + virtualAddress;
                result = String.format("%7s\t%s, 0x%x <%s>", "jal", getRegisterABI(rd), offset, addr.get(offset));
            }
            case 0b1100111 -> {
                int rd = (int) (instruction >> 7) % 32, rs1 = (int) (instruction >> 15) % 32;
                int offset = (int) ((instruction >> 20) - ((instruction >> 31) % 2 != 0 ? 1 << 12 : 0));
                result = String.format("%7s\t%s, 0x%x(%s)", "jalr", getRegisterABI(rd), offset, getRegisterABI(rs1));
            }
        }
        return result;
    }

    private int makeJTypeOffset(long instruction) {
        boolean[] inst = makeBooleanInst(instruction);
        boolean[] res = new boolean[21];
        res[1] = inst[21];
        res[2] = inst[22];
        res[3] = inst[23];
        res[4] = inst[24];
        res[5] = inst[25];
        res[6] = inst[26];
        res[7] = inst[27];
        res[8] = inst[28];
        res[9] = inst[29];
        res[10] = inst[30];
        res[11] = inst[20];
        res[12] = inst[12];
        res[13] = inst[13];
        res[14] = inst[14];
        res[15] = inst[15];
        res[16] = inst[16];
        res[17] = inst[17];
        res[18] = inst[18];
        res[19] = inst[19];
        res[20] = inst[31];
        return calcInInts(res) - (res[20] ? 1 << 21 : 0);
    }

    private int makeBTypeOffset(long instruction) {
        boolean[] inst = makeBooleanInst(instruction);
        boolean[] res = new boolean[13];
        res[1] = inst[8];
        res[2] = inst[9];
        res[3] = inst[10];
        res[4] = inst[11];
        res[5] = inst[25];
        res[6] = inst[26];
        res[7] = inst[27];
        res[8] = inst[28];
        res[9] = inst[29];
        res[10] = inst[30];
        res[11] = inst[7];
        res[12] = inst[31];
        return calcInInts(res) - (res[12] ? 1 << 13 : 0);
    }

    private boolean[] makeBooleanInst(long it) {
        boolean[] res = new boolean[32];
        for (int i = 0; i < 32; i++) {
            res[i] = (it % 2 == 1);
            it = it >> 1;
        }
        return res;
    }

    private int calcInInts(boolean... arr) {
        int ans = 0;
        for (int i = arr.length - 1; i >= 0; i--) {
            ans <<= 1;
            ans += arr[i] ? 1 : 0;
        }
        return ans;
    }

    private String parseRV32MInstruction(int instruction) {
        int func3 = (instruction >> 12) % 8;
        String command = switch (func3) {
            case 0 -> "mul";
            case 1 -> "mulh";
            case 2 -> "mulhsu";
            case 3 -> "mulhu";
            case 4 -> "div";
            case 5 -> "divu";
            case 6 -> "rem";
            case 7 -> "remu";
            default -> throw new IllegalStateException("Unexpected value: " + func3);
        };
        int rs1 = (instruction >> 15) % 32, rs2 = (instruction >> 20) % 32, rd = (instruction >> 7) % 32;
        return String.format("%7s\t%s, %s, %s", command, getRegisterABI(rs1), getRegisterABI(rs2), getRegisterABI(rd));
    }

    private String getRegisterABI(int reg) {
        return switch (reg) {
            case 0 -> "zero";
            case 1 -> "ra";
            case 2 -> "sp";
            case 3 -> "gp";
            case 4 -> "tp";
            case 5, 6, 7 -> "t" + (reg - 5);
            case 8 -> "s0";
            case 9 -> "s1";
            case 10, 11, 12, 13, 14, 15, 16, 17 -> "a" + (reg - 10);
            case 18, 19, 20, 21, 22, 23, 24, 25, 26, 27 -> "s" + (reg - 16);
            case 28, 29, 30, 31 -> "t" + (reg - 25);
            default -> throw new IllegalStateException("Unexpected value: " + reg);
        };
    }

    private long readInstructionLong(char... arr) {
        long res = 0;
        for (int i = arr.length - 1; i >= 0 ; i--) {
            res <<= 8;
            res += arr[i];
        }
        return res;
    }

    private Section textSection() {
        for (int i = 0; i < file.getShnum(); i++) {
            if (file.getSection(i).getName().equals(".text")) {
                return file.getSection(i);
            }
        }
        throw new AssertionError("No .text section in file");
    }

    private void readSymtab(Map <Integer, String> addr) {
        for (int i = 0; i < file.getSymbols(); i++) {
            Symbol symbol = file.getSymbol(i);
            if (symbol.getType().equals("FUNC")) {
                addr.put(symbol.getValue(), symbol.getName());
            }
        }
    }


}
