package io.urlshortener.short_url.Hashing;

import java.util.ArrayList;

public class SHA256 {

    public static void preprocessing(String input) {
        //convert string to binary
        byte[] b= input.getBytes();
        //calculate len of byteArr
        int x= 0, len= 0;
        while(x <= 512) {
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
        //pad with 0's
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
    }

    public static void main(String[] args) {
        preprocessing("https://www.google.com/search?q=sha256&rlz=1C1CHBD_enIN1068IN1068&oq=sha256&gs_lcrp=EgZjaHJvbWUqFQgAEAAYQxiDARjjAhixAxiABBiKBTIVCAAQABhDGIMBGOMCGLEDGIAEGIoFMhIIARAuGEMYgwEYsQMYgAQYigUyEggCEAAYQxiDARixAxiABBiKBTISCAMQABhDGIMBGLEDGIAEGIoFMgcIBBAAGIAEMgYIBRBFGDwyBggGEEUYPDIGCAcQRRg80gEIMjUzNmowajeoAgCwAgA&sourceid=chrome&ie=UTF-8");
    }

}
