package laboflieven.challenges;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class P1Test {

    @Test
    void mainT() {
        double R1 = 0, R2 = 0;
        double left = 0, right = 0;

        right = R2; left = left % right;  right = R2;  right = R2;  left = R1; left = left % right; left = nand(left, right); left = left % right; left = nand(left, right); R1 = left; left = nand(left, right);
        R1 = left;

        System.out.println(R1);
    }

    static double nand(double left, double right) {
        boolean sourceB = left < 0.0000001 ? false: true;
        boolean destinationB = right < 0.0000001 ? false: true;
        boolean evaluation = !(sourceB && destinationB);
        return evaluation?1.0:0.0;
    }

}