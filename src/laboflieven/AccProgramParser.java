package laboflieven;

import laboflieven.accinstructions.*;
import laboflieven.statements.Register;

import java.util.ArrayList;
import java.util.List;

public class AccProgramParser {

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

    public static InstructionMark parseOneInstruct(String s)
    {
        InstructionMark i = null;
        if ("left = left + right".equals(s.trim())) {
            return new Add();
        }
        if ("left = left / right".equals(s.trim())) {
            return new Div();
        }
        if ("left++".equals(s.trim())) {
            return new Inc();
        }
        String[] parts = s.trim().split("=");

        if (s.trim().contains("right = R")) {
            return new LoadIntoRightAcc(parseRegister(parts[1]));
        }
        if (s.trim().contains("left = R")) {
            return new LoadIntoLeftAcc(parseRegister(parts[1]));
        }
        if (s.trim().contains("= left")) {
            return new LoadAccLeftIntoRegister(parseRegister(parts[0]));
        }
        if (s.trim().contains("= right")) {
            return new LoadAccRightIntoRegister(parseRegister(parts[0]));
        }

        if (i == null) throw new RuntimeException("Unknown instruction " + s);
        return i;
    }

    public static Register parseRegister(String s)
    {
        return new Register(s.trim());
    }
}
