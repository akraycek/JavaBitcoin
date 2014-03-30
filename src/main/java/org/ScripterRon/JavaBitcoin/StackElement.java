/*
 * Copyright 2014 Ronald W Hoffman.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.ScripterRon.JavaBitcoin;

import java.math.BigInteger;

import java.util.Arrays;

/**
 * A stack element is a series of zero or more bytes.  The stack consists of stack elements
 * that are added and removed as the script is interpreted.  The byte array is processed
 * as a little-endian signed number for numeric script operations.
 */
public class StackElement implements Comparable<StackElement> {

    /** The bytes represented by this stack element */
    private final byte[] bytes;

    /**
     * Creates a new stack element from a byte array
     *
     * @param       bytes           The bytes for this element
     */
    public StackElement(byte[] bytes) {
        this.bytes = bytes;
    }
    
    /**
     * Creates a new stack element from an existing stack element
     * 
     * @param       elem            Existing stack element
     */
    public StackElement(StackElement elem) {
        bytes = elem.getBytes();
    }
    
    /**
     * Creates a new stack element from a BigInteger.  Numeric values are stored
     * on the stack in MPI format with the bytes reversed.
     * 
     * @param       bigInteger      BigInteger
     */
    public StackElement(BigInteger bigInteger) {
        bytes = Utils.reverseBytes(Utils.encodeMPI(bigInteger, false));
     }
    
    /**
     * Creates a new stack element containing TRUE or FALSE
     * 
     * @param       isTrue          TRUE if stack element is TRUE
     */
    public StackElement(boolean isTrue) {
        bytes = new byte[1];
        bytes[0] = isTrue ? (byte)1 : (byte)0;
    }

    /**
     * Returns the bytes for this stack element
     *
     * @return                      The bytes for this element
     */
    public byte[] getBytes() {
        return bytes;
    }

    /**
     * Returns a BigInteger representing the bytes for this stack element.  Numeric
     * values are stored on the stack in MPI format with the bytes reversed.
     * 
     * @return                      Unsigned BigInteger
     */
    public BigInteger getBigInteger() {
        return Utils.decodeMPI(Utils.reverseBytes(bytes), false);
    }

    /**
     * Tests if this element represents a TRUE or FALSE result.  Any non-zero value is TRUE
     * while any zero (positive or negative) is FALSE.  A zero-length stack element is FALSE.
     *
     * @return      TRUE or FALSE depending on the bytes in this element
     */
    public boolean isTrue() {
        boolean isTrue = false;
        for (int i=0; i<bytes.length && !isTrue; i++) {
            if (bytes[i] == (byte)0x80) {
                if (i != bytes.length-1)
                    isTrue = true;
            } else if (bytes[i] != 0) {
                isTrue = true;
            }
        }
        return isTrue;
    }

    /**
     * Return the hash code for this stack element
     *
     * @return      Hash code
     */
    @Override
    public int hashCode() {
        return Arrays.hashCode(bytes);
    }

    /**
     * Tests if two stack elements are equal
     *
     * @param       obj         Object to compare
     * @return                  TRUE if the two byte array are the same
     */
    @Override
    public boolean equals(Object obj) {
        boolean areEqual = false;
        if (obj != null && (obj instanceof StackElement))
            areEqual = Arrays.equals(bytes, ((StackElement)obj).bytes);

        return areEqual;
    }

    /**
     * Compares this stack element to another stack element
     *
     * @param       cmpElem     Element to compare
     * @return                  -1 if less than, 0 if equal, 1 if greater
     */
    @Override
    public int compareTo(StackElement cmpElem) {
        BigInteger cmp1 = getBigInteger();
        BigInteger cmp2 = cmpElem.getBigInteger();
        return cmp1.compareTo(cmp2);
    }
}   
