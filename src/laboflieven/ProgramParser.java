package laboflieven;

import laboflieven.statements.Cos;
import laboflieven.statements.Instruction;
import laboflieven.statements.Register;

import java.util.ArrayList;
import java.util.List;

public class ProgramParser {

    public static List<Instruction>  parse(String s)
    {
        List<Instruction> instructions = new ArrayList<>();
        s = s.substring(1, s.length() - 2);
        String[] elements = s.split(",");
        for(String element : elements) {
            instructions.add(parseOneInstruct(element));
        }
        return instructions;
    }

    public static Instruction parseOneInstruct(String s)
    {
        Instruction i = null;
        String[] parts = s.split(" ");
        if (s.contains("Cos")){
            i = new Cos(parseRegister(parts[1]));
        }
        return i;
    }

    public static Register parseRegister(String s)
    {
        return new Register(s.trim());
    }
}
