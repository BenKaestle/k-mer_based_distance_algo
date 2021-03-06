package hash_functions;

import java.io.UnsupportedEncodingException;

/*
 *  Murmur3_64.java Copyright (C) 2020 Algorithms in Bioinformatics, University of Tuebingen
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */


/**
 *
 * Benjamin Kaestle, 3.2020
 */
/**
 *  The MurmurHash3 algorithm was created by Austin Appleby and placed in the public domain.
 *  This java port was authored by Yonik Seeley and also placed into the public domain.
 *  The author hereby disclaims copyright to this source code.
 **/
public class Murmur3_64 implements HashFunction {


    int kmerSize;
    int seed;
    LongPair longPair = new LongPair();

    public Murmur3_64(int kmerSize, int seed) {
        this.kmerSize = kmerSize;
        this.seed = seed;
    }

    @Override
    public long hash(String kmer) {
        try {
            murmurhash3_x64_128(kmer.getBytes("UTF-8"),0,kmerSize,seed,longPair);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return longPair.val1;
    }


    /** 128 bits of state */
    public static final class LongPair {
        public long val1;
        public long val2;
    }

    public static final long fmix64(long k) {
        k ^= k >>> 33;
        k *= 0xff51afd7ed558ccdL;
        k ^= k >>> 33;
        k *= 0xc4ceb9fe1a85ec53L;
        k ^= k >>> 33;
        return k;
    }




    /** Gets a long from a byte buffer in little endian byte order. */
    public static final long getLongLittleEndian(byte[] buf, int offset) {
        return     ((long)buf[offset+7]    << 56)   // no mask needed
                | ((buf[offset+6] & 0xffL) << 48)
                | ((buf[offset+5] & 0xffL) << 40)
                | ((buf[offset+4] & 0xffL) << 32)
                | ((buf[offset+3] & 0xffL) << 24)
                | ((buf[offset+2] & 0xffL) << 16)
                | ((buf[offset+1] & 0xffL) << 8)
                | ((buf[offset  ] & 0xffL));        // no shift needed
    }


    /** Returns the MurmurHash3_x64_128 hash, placing the result in "out". */
    @SuppressWarnings("fallthrough")
    public static void murmurhash3_x64_128(byte[] key, int offset, int len, int seed, LongPair out) {
        long h1 = seed & 0x00000000FFFFFFFFL;
        long h2 = seed & 0x00000000FFFFFFFFL;

        final long c1 = 0x87c37b91114253d5L;
        final long c2 = 0x4cf5ad432745937fL;

        int roundedEnd = offset + (len & 0xFFFFFFF0);  // round down to 16 byte block
        for (int i=offset; i<roundedEnd; i+=16) {
            long k1 = getLongLittleEndian(key, i);
            long k2 = getLongLittleEndian(key, i+8);
            k1 *= c1; k1  = Long.rotateLeft(k1,31); k1 *= c2; h1 ^= k1;
            h1 = Long.rotateLeft(h1,27); h1 += h2; h1 = h1*5+0x52dce729;
            k2 *= c2; k2  = Long.rotateLeft(k2,33); k2 *= c1; h2 ^= k2;
            h2 = Long.rotateLeft(h2,31); h2 += h1; h2 = h2*5+0x38495ab5;
        }

        long k1 = 0;
        long k2 = 0;

        switch (len & 15) {
            case 15: k2  = (key[roundedEnd+14] & 0xffL) << 48;
            case 14: k2 |= (key[roundedEnd+13] & 0xffL) << 40;
            case 13: k2 |= (key[roundedEnd+12] & 0xffL) << 32;
            case 12: k2 |= (key[roundedEnd+11] & 0xffL) << 24;
            case 11: k2 |= (key[roundedEnd+10] & 0xffL) << 16;
            case 10: k2 |= (key[roundedEnd+ 9] & 0xffL) << 8;
            case  9: k2 |= (key[roundedEnd+ 8] & 0xffL);
                k2 *= c2; k2  = Long.rotateLeft(k2, 33); k2 *= c1; h2 ^= k2;
            case  8: k1  = ((long)key[roundedEnd+7]) << 56;
            case  7: k1 |= (key[roundedEnd+6] & 0xffL) << 48;
            case  6: k1 |= (key[roundedEnd+5] & 0xffL) << 40;
            case  5: k1 |= (key[roundedEnd+4] & 0xffL) << 32;
            case  4: k1 |= (key[roundedEnd+3] & 0xffL) << 24;
            case  3: k1 |= (key[roundedEnd+2] & 0xffL) << 16;
            case  2: k1 |= (key[roundedEnd+1] & 0xffL) << 8;
            case  1: k1 |= (key[roundedEnd  ] & 0xffL);
                k1 *= c1; k1  = Long.rotateLeft(k1,31); k1 *= c2; h1 ^= k1;
        }

        //----------
        // finalization

        h1 ^= len; h2 ^= len;

        h1 += h2;
        h2 += h1;

        h1 = fmix64(h1);
        h2 = fmix64(h2);

        h1 += h2;
        h2 += h1;

        out.val1 = h1;
        out.val2 = h2;
    }


}
