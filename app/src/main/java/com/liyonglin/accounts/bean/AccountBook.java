package com.liyonglin.accounts.bean;

/**
 * Created by 永霖 on 2016/7/26.
 */
public class AccountBook {

    private String bookName;
    private int bookMode;
    private int bookId;

    public AccountBook(String bookName, int bookMode) {
        this.bookName = bookName;
        this.bookMode = bookMode;
    }

    public AccountBook(String bookName, int bookMode, int bookId) {
        this.bookName = bookName;
        this.bookMode = bookMode;
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public int getBookMode() {
        return bookMode;
    }

    public void setBookMode(int bookMode) {
        this.bookMode = bookMode;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }
}
