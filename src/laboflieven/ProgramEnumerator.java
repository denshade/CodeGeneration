package laboflieven;

import laboflieven.statements.*;

import java.util.List;

public class ProgramEnumerator
{
    public long convert(final List<InstructionMark> instructions)
    {
        long sum = 0;
        long l = 13;
        long index = 0;
        for (InstructionMark instruction : instructions) {
            if (instruction instanceof Add) {
                index = 0;
            }
            if (instruction instanceof Cos) {
                index = 1;
            }
            if (instruction instanceof Div) {
                index = 2;
            }
            if (instruction instanceof Invert) {
                index = 3;
            }
            if (instruction instanceof JumpIfRegister1Zero) {
                index = 4;
            }
            if (instruction instanceof Log) {
                index = 5;
            }
            if (instruction instanceof Mod) {
                index = 6;
            }
            if (instruction instanceof Move) {
                index = 7;
            }
            if (instruction instanceof Mul) {
                index = 8;
            }
            if (instruction instanceof Nand) {
                index = 9;
            }
            if (instruction instanceof Sin) {
                index = 10;
            }
            if (instruction instanceof Sqrt) {
                index = 11;
            }
            if (instruction instanceof Sub) {
                index = 12;
            }
            sum += index*l;
            l*=13;
        }
        return sum;
    }
}
