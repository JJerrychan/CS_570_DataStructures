package hw1;
/*
 * course: CS 570 ws
 * Homework Assignment 1
 * name: Junjie Chen
 * CWID: 10476718
 * */

public class BinaryNumber {
    private int[] data;
    private boolean overflow;

    public BinaryNumber(int length) {
        if (length < 1) throw new IllegalArgumentException("length of Binary number should be no less than 1");
        data = new int[length];
    }

    public BinaryNumber(String str) {
        int length = str.length();
        data = new int[length];
        for (int i = 0; i < length; i++) {
            if (str.charAt(i) != '0' && str.charAt(i) != '1') {
                throw new IllegalArgumentException(String.format("illegal character '%c' found in '%s'", str.charAt(i), str));
            }
            data[i] = Character.getNumericValue(str.charAt(i));
        }
    }

    public int getLength() {
        return data.length;
    }

    public int getDigit(int index) {//starting index is 0
        if (index < 0 || index >= data.length) {
            throw new IllegalArgumentException(String.format("index %d out of bounds for length %d", index, data.length));
        }
        return data[index];
    }

    public void shiftR(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("shift amount should be no less than 0");
        } else if (amount == 0) {
            return;
        } else {
            int[] newData = new int[data.length + amount];
            System.arraycopy(data, 0, newData, amount, data.length);
            data = newData;
        }
    }

    public void add(BinaryNumber aBinaryNumber) {
        int carried = 0;
        if (aBinaryNumber == null) throw new IllegalArgumentException("Cannot add Null");
        else if (aBinaryNumber.getLength() != getLength())
            throw new IllegalArgumentException("the lengths of the binary numbers do not coincide");
        else {
            for (int i = getLength() - 1; i >= 0; i--) {
                data[i] += aBinaryNumber.data[i] + carried;
                carried = 0;
                if (data[i] >= 2) {
                    data[i] -= 2;
                    carried = 1;
                }
            }
        }
        if (carried != 0) {
            overflow = true;
        }
    }

    public String toString() {
        if (overflow) return "Overflow!";
        StringBuilder s = new StringBuilder();
        for (int i : data) {
            s.append(i);
        }
        return s.toString();
    }

    public int toDecimal() {
        int Dec = 0;
        int n = 0;
        for (int i = data.length - 1; i >= 0; i--) {
            Dec += (int) (data[i] * Math.pow(2, n));
            n++;
        }
        return Dec;
    }

    public void clearOverflow() {
        overflow = false;
    }

}
