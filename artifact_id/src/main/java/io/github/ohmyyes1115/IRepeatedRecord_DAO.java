package io.github.ohmyyes1115;

interface IRepeatedRecord_DAO {
    
    public enum EResult {
        O,
        X
    }

    // Insert a record.
    // E.g. { 2022-11-27T14:06:06	Leetcode-BlindCurated75 - 5	O	Longest Palindromic Substring }
    boolean insert(RepeatedRecord_VO vo);
}