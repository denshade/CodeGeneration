package laboflieven;

import laboflieven.statements.*;

import java.util.ArrayList;
import java.util.List;

public class ProgramParser {

    public static List<InstructionMark>  parse(String s)
    {
        List<InstructionMark> instructions = new ArrayList<>();
        s = s.substring(1, s.length() - 1);
        String[] elements = s.split(",");
        for(String element : elements) {
            instructions.add(parseOneInstruct(element));
        }
        return instructions;
    }

    public static Instruction parseOneInstruct(String s)
    {
        Instruction i = null;
        String[] parts = s.trim().split(" ");
        if (s.contains("+=")){
            i = new Add(parseRegister(parts[2]), parseRegister(parts[0]));
        }
        if (s.contains("Cos")){
            i = new Cos(parseRegister(parts[1]));
        }
        if (s.contains("/=")){
            i = new Div(parseRegister(parts[2]), parseRegister(parts[0]));
        }
        if (s.contains("Invert")){
            i = new Invert(parseRegister(parts[1]));
        }
        if (s.contains("JumpIfRegister1Zero")){
            i = new JumpIfRegister1Zero(parseRegister(parts[1]), parseRegister(parts[3]));
        }
        if (s.contains("Log")){
            i = new Log(parseRegister(parts[1]));
        }
        if (s.contains("%")){
            i = new Mod(parseRegister(parts[4]), parseRegister(parts[0]));
        }
        if (s.contains("Move")){
            i = new Move(parseRegister(parts[1]), parseRegister(parts[3]));
        }
        if (s.contains("Mul")){
            i = new Mul(parseRegister(parts[1]), parseRegister(parts[3]));
        }
        if (s.contains("Nand")){
            i = new Nand(parseRegister(parts[1]), parseRegister(parts[3]));
        }
        if (s.contains("Sin")){
            i = new Sin(parseRegister(parts[1]));
        }
        if (s.contains("Sqrt")){
            i = new Sqrt(parseRegister(parts[1]));
        }
        if (s.contains("-=")){
            i = new Sub(parseRegister(parts[2]), parseRegister(parts[0]));
        }
        if (s.contains("One")) {
            i = new One(parseRegister(parts[1]));
        }
        if (s.contains("PI")) {
            i = new PI(parseRegister(parts[1]));
        }

        if (i == null) throw new RuntimeException("Unknown instruction " + s);
        return i;
    }

    public static Register parseRegister(String s)
    {
        return new Register(s.trim());
    }
}
