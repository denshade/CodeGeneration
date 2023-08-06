package laboflieven.programprinters;

import laboflieven.InstructionMark;
import laboflieven.Program;
import laboflieven.registers.Register;

import java.util.ArrayList;

public class JavaProgramPrinter implements ProgramPrinter {

    @Override
    public String toProgram(Program program) {
        StringBuilder programCode = new StringBuilder();
        programCode.append("double run( ");
        var parameters = new ArrayList<String>();
        for (Register r : program.getRegisters())
        {
            parameters.add("double " + r.name);
        }
        programCode.append(String.join(",", parameters));
        programCode.append(") {\n");
        for (InstructionMark mark : program.getInstructions())
        {
            programCode.append(mark.toString()).append(";\n");
        }
        programCode.append("return "+program.getRegisters().get(0).name+ ";\n");
        programCode.append("}");
        return programCode.toString();
    }
}
