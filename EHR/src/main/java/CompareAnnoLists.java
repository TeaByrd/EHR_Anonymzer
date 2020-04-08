import java.util.List;

public class CompareAnnoLists {


    public static List<String> findSame(List<String> listOne, List<String> listTwo ) {
        /**
         * finds same values shared between two lists
         * returns modified listOne that only contains the same words in the listTwo
          */
        listOne.retainAll(listTwo);
        return listOne;
    }
    public static List<String> findDiff(List<String> firstList, List<String> lastList) {
        /**
         * determines which annotations have been added to the lastList (user edited annotations)
         *  returns a list of new annotations
         */
        lastList.removeAll(firstList);

        return lastList;
    }

}
