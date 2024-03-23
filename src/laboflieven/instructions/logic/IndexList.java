package laboflieven.instructions.logic;

import java.util.ArrayList;
import java.util.List;

public class IndexList {
    private final int nrOptions;

    public IndexList(int nrOptions) {
        this.nrOptions = nrOptions;
    }
    public void update(ArrayList<Integer> optionList) {
        boolean carry;
        int currentIndex = 0;
        do {
            carry = false;
            if (optionList.get(currentIndex) + 1 == nrOptions) {
                optionList.set(currentIndex,0);
                currentIndex++;
                carry = true;
            } else {
                optionList.set(currentIndex, optionList.get(currentIndex) + 1);
            }
        } while(carry);
    }
}
