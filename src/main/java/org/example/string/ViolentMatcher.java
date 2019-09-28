package org.example.string;

public class ViolentMatcher {
    private String text;

    public static void main(String[] args) {
        ViolentMatcher matcher = new ViolentMatcher("Hello, world!");
        System.out.println(matcher.match("Hello"));
        System.out.println(matcher.match("world"));
        System.out.println(matcher.match("world?"));
    }

    public ViolentMatcher(String text) {
        this.text = text;
    }

    public int match(String pattern) {
        char[] txtChars = text.toCharArray();
        char[] patChars = pattern.toCharArray();

        int txtLen = txtChars.length;
        int patLen = patChars.length;

        int i = 0; // txtChars
        int j = 0; // patChars

        while (i < txtLen && j < patLen) {
            if (txtChars[i] == patChars[j]) {
                i++;
                j++;
            } else {             // match failed
                i = i - j + 1;   // backtrack to the start + 1
                j = 0;           // restart to match
            }
        }

        if (j == patLen) {       // match exactly
            return i - j;
        }

        return -1;               // no match
    }
}
