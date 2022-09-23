package com.wcx.utils;

import java.util.HashMap;

public class Trie {
    static class TrieNode {
        // 字符为键，树节点为值
        private HashMap<Character, TrieNode> children = new HashMap<>();
        // 判断是否是树的叶子节点
        private boolean isEnd;
    }
    private TrieNode root;
    public Trie() {
        root = new TrieNode();
    }
    /** 插入一个字符串 */
    public void insert(String word) {
        TrieNode cur = root;
        for (int i = 0; i < word.length(); i ++) {
            char c = word.charAt(i);
            if (!cur.children.containsKey(c)) {
                cur.children.put(c, new TrieNode());
            }
            cur = cur.children.get(c);
        }
        cur.isEnd = true;
    }
    /** 得到前缀搜索得到的树节点 */
    private TrieNode searchPrefix(String prefix) {
        TrieNode cur = root;
        for (int i = 0; i < prefix.length(); i ++) {
            char c = prefix.charAt(i);
            if (cur.children.containsKey(c)) {
                cur = cur.children.get(c);
            } else {
                return null;
            }
        }
        return cur;
    }
    /** 搜索前缀树是否存在字符串word */
    public boolean searchWord(String word) {
        TrieNode cur = searchPrefix(word);
        return cur != null && cur.isEnd == true;
    }
    /** 搜索前缀树中是否存在前缀prefix */
    public boolean startPrefix(String prefix) {
        TrieNode cur = searchPrefix(prefix);
        return cur != null;
    }
    public static Trie constructTrie(String[] words) {
        Trie trie = new Trie();
        for (int i = 0; i < words.length; i ++) {
            trie.insert(words[i]);
        }
        return trie;
    }
}
