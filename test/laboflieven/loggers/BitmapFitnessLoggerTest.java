package laboflieven.loggers;

import laboflieven.common.RegularInstructionOpcode;
import laboflieven.instructions.regular.Add;
import laboflieven.registers.Register;
import laboflieven.instructions.regular.RegularInstructionOpcodeEnum;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BitmapFitnessLoggerTest {
    @Test
    public void checkBitmap() throws IOException {
        File f = File.createTempFile("pref", "suff");
        BitmapFitnessLogger logger = new BitmapFitnessLogger(f, Arrays.stream(RegularInstructionOpcodeEnum.values()).map(RegularInstructionOpcode::new).collect(Collectors.toList()));
        logger.addFitness(List.of(new Add(new Register("R1"), new Register("R1"))), 2,2,3);
        logger.finish();
        final BufferedImage res = ImageIO.read(f);
        assertEquals(-16777216, res.getRGB(0,0 ));

        f.delete();

    }

}