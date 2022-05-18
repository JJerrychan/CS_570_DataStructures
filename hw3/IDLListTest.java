package hw3;

public class IDLListTest {
    public static void main(String[] args) {
        IDLList idlList = new IDLList();

        idlList.add(1);
        idlList.add('a');
        idlList.add(0, 3);
        idlList.append(4);

        System.out.println(idlList);
        System.out.println(idlList.get(2));
        try {
            System.out.println(idlList.get(-1));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            System.out.println(idlList.get(7));
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(idlList.getHead());
        System.out.println(idlList.getLast());
        System.out.println(idlList.size());
        System.out.println(idlList.removeAt(1));
        try {
            System.out.println(idlList.removeLast());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            System.out.println(idlList.remove());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            System.out.println(idlList.remove('a'));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
