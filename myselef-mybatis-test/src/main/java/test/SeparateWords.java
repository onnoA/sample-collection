package test;

import java.util.Arrays;

public class SeparateWords {


    public int getMaxSegs(String S, String[] dics, int n) {

        if(n<1){
            return 0;
        }
        Arrays.sort(dics);

        int count=0;
        for (int i = 0; i < dics.length; i++) {
            int length=S.length();
            String dic = dics[i];
            S=S.replace(dic,"");
            count+=(length-S.length())/dic.length();
        }
        return count;

    }

    public static void main(String[] args) {
        SeparateWords separateWords = new SeparateWords();
        String s="aabbccdde";
        String[] arr={"ab","b","c","d"};
        System.out.println(separateWords.getMaxSegs(s,arr,4));
    }





    public int shortestSubsequence(int[] A, int n) {
        // write code here

        if(n<=1){
            return  0;
        }

        return 0;
    }
}
