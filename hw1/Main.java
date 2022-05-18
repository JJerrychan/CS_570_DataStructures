package hw1;

public class Main {

    public static void main(String[] args) {
        // write your code here
        BinaryNumber bn = new BinaryNumber("1001");
        System.out.println(bn);
        System.out.println(bn.toDecimal());
        bn.add(new BinaryNumber("1100"));
        System.out.println(bn);
        System.out.println(bn.toDecimal());
    }
}
