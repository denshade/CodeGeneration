package laboflieven.loggers;

import laboflieven.statements.Add;
import laboflieven.statements.Register;
import laboflieven.statements.RegularInstructionOpcodeEnum;
import laboflieven.statements.Sub;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BitmapFitnessLoggerTest {
    @Test
    public void checkBitmap() throws IOException {
        File f = File.createTempFile("pref", "suff");
        BitmapFitnessLogger logger = new BitmapFitnessLogger(f, 2, List.of(RegularInstructionOpcodeEnum.values()));
        logger.addFitness(List.of(new Add(new Register("R0"), new Register("R0"))), 2,2,3);
        logger.finish();
        final BufferedImage res = ImageIO.read(f);
        assertEquals(-262144, res.getRGB(1,3 ));

        f.delete();

    }

}