package laboflieven.challenges;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class P1Test {

    @Test
    void mainTest(){
        assertEquals(1, mainT(15));
        assertEquals(0, mainT(14));
        assertEquals(1, mainT(10));
        assertEquals(1, mainT(9));
        assertEquals(0, mainT(11));

    }

    @Test
    void testAll()
    {
        double sum = 0;
        for (int i = 1; i < 1000; i++ ){
            sum += mainT(i)*i;
        }
        System.out.println(sum);
    }

    double mainT(double R1) {
        double  R2 = 3, R3 = 5;

        double left = 0, right = 0;

        left = R1 % R2;
        if (left != 0) {
            right = R3;  left = R1;
        }
        left = left % right;
        left = nand(left, right);

        return left;
    }

    static double nand(double left, double right) {
        boolean sourceB = !(left < 0.0000001);
        boolean destinationB = !(right < 0.0000001);
        boolean evaluation = !(sourceB && destinationB);
        return evaluation?1.0:0.0;
    }

}