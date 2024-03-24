package laboflieven.instructions.logic;

import java.util.ArrayList;
import java.util.List;

public class SplitBooleanSource {
    static class ListPair  {
        List<List<Boolean>> successRecords;
        List<List<Boolean>> failRecords;
    }
    public static ListPair split(List<List<Boolean>> items) {
        var paramsForSuccess = new ArrayList<List<Boolean>>();
        var paramsForFailure = new ArrayList<List<Boolean>>();
        for (var item : items) {
            var selectedList = paramsForFailure;
            if (item.get(item.size() - 1)) {
                selectedList = paramsForSuccess;
            }
            selectedList.add(item.subList(0, item.size() - 1));
        }
        var listpair = new ListPair();
        listpair.successRecords = paramsForSuccess;
        listpair.failRecords = paramsForFailure;
        return listpair;

    }
}
