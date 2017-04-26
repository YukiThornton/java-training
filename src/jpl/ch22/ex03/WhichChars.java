package jpl.ch22.ex03;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.HashMap;
import java.util.List;

public class WhichChars {

    private HashMap<BitSet, List<BitSet>> map = new HashMap<>();
    
    public static void main (String[] args) {
        WhichChars whichChars = new WhichChars("ae2bdc");
        System.out.println(whichChars.toString());
    }
    
    public WhichChars(String str) {
        for (int i = 0; i < str.length(); i++) {
            BitSet bitset = BitSet.valueOf(new byte[]{(byte)str.charAt(i)});
            BitSet firstHalf = bitset.get(0, 4);
            BitSet lastHalf = bitset.get(4, 8);
            if (map.containsKey(firstHalf)) {
                map.get(firstHalf).add(lastHalf);
            } else {
                List<BitSet> list = new ArrayList<>(1);
                list.add(lastHalf);
                map.put(firstHalf, list);
            }
        }
    }
    
    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        for(BitSet firstHalfBitSet : map.keySet()) {
            for (BitSet lastHalfBitSet : map.get(firstHalfBitSet)) {
                BitSet wholeBitSet = combine(firstHalfBitSet, lastHalfBitSet);
                stringBuffer.append((char)toInt(wholeBitSet));
            }
        }
        return sortLetters(stringBuffer);
    }
    
    private BitSet combine(BitSet firstBitSet, BitSet secondBitSet) {
        BitSet wholeBitSet = new BitSet(8);
        for (int i = firstBitSet.nextSetBit(0); i >= 0; i = firstBitSet.nextSetBit(i + 1)) {
            wholeBitSet.set(i);
        }
        for (int i = secondBitSet.nextSetBit(0); i >= 0; i = secondBitSet.nextSetBit(i + 1)) {
            wholeBitSet.set(i + 4);
        }
        return wholeBitSet;
    }
    
    private int toInt(BitSet bitSet) {
        int result = 0;
        for (int i = 7; 0 <= i; i--) {
            result |= (bitSet.get(i) ? 1 : 0) << i;
        }
        return result;
    }
    
    private String sortLetters (StringBuffer stringBuffer) {
        char[] chars = stringBuffer.toString().toCharArray();
        Arrays.sort(chars);
        return new String(chars);    
    }
}
