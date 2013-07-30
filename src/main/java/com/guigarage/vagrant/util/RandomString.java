package com.guigarage.vagrant.util;

import java.util.Random;

public class RandomString
{

    static final String alpha = "abcdefghiklmnopqrstuvwxyz";
    static Random random = new Random();

    public static String randomString( int len )
    {
        StringBuilder sb = new StringBuilder( len );
        for( int i = 0; i < len; i++ )
            sb.append( alpha.charAt( random.nextInt(alpha.length())));
        return sb.toString();
    }


}