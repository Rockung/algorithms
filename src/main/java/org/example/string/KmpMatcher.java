package org.example.string;

public class KmpMatcher {
    private String text;

    public static void main(String[] args) {
        String text = "Knuth-Morris-Pratt 字符串查找算法，简称为 “KMP算法”，" +
                "常用于在一个文本串S内查找一个模式串P 的出现位置，这个算法由" +
                "Donald Knuth、Vaughan Pratt、James H. Morris三人于1977年联合" +
                "发表，故取这3人的姓氏命名此算法。";
        KmpMatcher matcher = new KmpMatcher(text);
        System.out.println(matcher.match("Morris"));
        System.out.println(matcher.match("Morris?"));
        System.out.println(matcher.match("字符串"));
        System.out.println(matcher.match("符串查找算法"));
    }

    public KmpMatcher(String text) {
        this.text = text;
    }

    public int match(String pattern) {
        int[] next = kmpNext(pattern);

        for (int i = 0, j = 0; i < text.length(); i++) {
            // the core of KMP
            while (j > 0 && text.charAt(i) != pattern.charAt(j)) {
                j = next[j - 1];
            }

            if (text.charAt(i) == pattern.charAt(j)) {
                j++;
            }
            if (j == pattern.length()) {
                return i - j + 1;
            }
        }

        return -1;
    }

    /**
     * Next array keep the back positions for prefix of pattern
     *
     * @param pattern
     * @return next array of pattern
     */
    private int[] kmpNext(String pattern) {
        // next array for KMP
        int[] next = new int[pattern.length()];

        next[0] = 0; // 0 for the first prefix
        for (int i = 1, j = 0; i < pattern.length(); i++) {
            // the core of KMP
            while (j > 0 && pattern.charAt(i) != pattern.charAt(j)) {
                j = next[j - 1];
            }

            if (pattern.charAt(i) == pattern.charAt(j)) {
                j++;
            }
            next[i] = j;
        }

        return next;
    }

}
