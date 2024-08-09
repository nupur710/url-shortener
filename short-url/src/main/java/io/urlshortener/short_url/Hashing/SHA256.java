package io.urlshortener.short_url.Hashing;

import java.util.ArrayList;

public class SHA256 {
    static int[] hash= {0x6a09e667, 0xbb67ae85, 0x3c6ef372, 0xa54ff53a,
            0x510e527f, 0x9b05688c, 0x1f83d9ab, 0x5be0cd19};
    static int[] k= {0x428a2f98, 0x71374491, 0xb5c0fbcf, 0xe9b5dba5,
            0x3956c25b, 0x59f111f1, 0x923f82a4, 0xab1c5ed5,
            0xd807aa98, 0x12835b01, 0x243185be, 0x550c7dc3,
            0x72be5d74, 0x80deb1fe, 0x9bdc06a7, 0xc19bf174,
            0xe49b69c1, 0xefbe4786, 0x0fc19dc6, 0x240ca1cc,
            0x2de92c6f, 0x4a7484aa, 0x5cb0a9dc, 0x76f988da,
            0x983e5152, 0xa831c66d, 0xb00327c8, 0xbf597fc7,
            0xc6e00bf3, 0xd5a79147, 0x06ca6351, 0x14292967,
            0x27b70a85, 0x2e1b2138, 0x4d2c6dfc, 0x53380d13,
            0x650a7354, 0x766a0abb, 0x81c2c92e, 0x92722c85,
            0xa2bfe8a1, 0xa81a664b, 0xc24b8b70, 0xc76c51a3,
            0xd192e819, 0xd6990624, 0xf40e3585, 0x106aa070,
            0x19a4c116, 0x1e376c08, 0x2748774c, 0x34b0bcb5,
            0x391c0cb3, 0x4ed8aa4a, 0x5b9cca4f, 0x682e6ff3,
            0x748f82ee, 0x78a5636f, 0x84c87814, 0x8cc70208,
            0x90befffa, 0xa4506ceb, 0xbef9a3f7, 0xc67178f2};

    public static ArrayList<Byte> preprocessing(String input) {
        //convert string to binary
        byte[] b= input.getBytes();
        //calculate len of byteArr
        int x= 0, len= 0;
        while(x < 512) {
            if(((b.length*8) +1 + x + 64) % 512 == 0) {
                break;
            } x++;
        }
        len= ((b.length*8)+1+x+64)/8;
        ArrayList<Byte> byteArr= new ArrayList<Byte>(len);
        for(int i= 0; i< b.length; i++) {
            byteArr.add(b[i]);
        }
        //append one
        byteArr.add((byte) 0x80);
        //pad with 0s
        while(byteArr.size() < len-8) {
            byteArr.add((byte) 0x00);
        }
        //append 64 bits of big endian integer
        byte[] end= new byte[8];
        for(int i= 0; i < 8; i++) {
            end[7 - i]= (byte) ((input.length()*8) >>> (i*8)); //to append input len as a big endian integer, unsigned right shift operation
        }
        for(byte bt : end) {
            byteArr.add(bt);
        }
        if((((byteArr.size() * 8) % 512) != 0) && (byteArr.size() != len)) {
            throw new AssertionError("Final byte array length should be a multiple of 512 but is " + byteArr.size());
        }
        return byteArr;
    }

    public static String chunkLoops(String url) {
        ArrayList<Byte> input = preprocessing(url);
        /**
         * For every 512 bit chunk of input, create a message schedule
         * of 64 bytes, the first 16 bytes are copied from the input, for
         * the next 48 bytes, we perform processing
         */
        int chunk= 0;
        while(chunk < input.size()) {
            //for inner loop start i at chunk & go up till chunk + 64;update chunk to chunk+64
            int[] messageSchedule = new int[64];
            int j = 0;
            for (int i = chunk; i < chunk+64; i += 4) {
                messageSchedule[j] = (input.get(i) << 24) | (input.get(i + 1) << 16) | (input.get(i + 2) << 8) | input.get(i + 3);
                j++;
            }

            for(int i= 16; i< messageSchedule.length; i++) {
                int s0= (rightRotate(messageSchedule[i-15], 7)) ^ (rightRotate(messageSchedule[i-15], 18)) ^ (messageSchedule[i-15]>>>3);
                int s1= (rightRotate(messageSchedule[i-2], 7)) ^ (rightRotate(messageSchedule[i-2], 19)) ^ (messageSchedule[i-2]>>>10);
                messageSchedule[i]= messageSchedule[i-16] + s0 + messageSchedule[i-7] + s1;
            }
            compress(messageSchedule);
            chunk= chunk+64;
        }

        StringBuilder str= new StringBuilder();
        for(int i : hash) {
            str.append(String.format("%08x",i));
        }
        return str.toString();
    }

    public static void compress(int[] messageSchedule) {
        int a= hash[0], b= hash[1], c= hash[2], d= hash[3], e= hash[4],
        f= hash[5], g= hash[6], h= hash[7];
        for(int i= 0; i < 64; i++) {
            int s1= (rightRotate(e, 6)) ^ (rightRotate(e, 11)) ^ (rightRotate(e, 25));
            int ch= (e&f)^((~e)&g);
            int temp1= h + s1 + ch + k[i] +messageSchedule[i];
            int s0= (rightRotate(a, 2)) ^ (rightRotate(a, 13)) ^ (rightRotate(a, 22));
            int maj= (a&b)^(a&c)^(b&c);
            int temp2= s0 + maj;
            h= g;
            g= f;
            f= e;
            e= d + temp1;
            d= c;
            c= b;
            b= a;
            a= temp1 + temp2;
        }
        hash[0]+= a;
        hash[1]+= b;
        hash[2]+= c;
        hash[3]+= d;
        hash[4]+= e;
        hash[5]+= f;
        hash[6]+= g;
        hash[7]+= h;
    }

    public static int rightRotate(int n, int d) {
        int bits= 32;
        d= d % 32; //for when d > 32
        return (n >>> d) | (n << (bits-d));
    }
}
