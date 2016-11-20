package com.fisherevans.twc.util.imut_col;

import java.util.ListIterator;

public class ImmutableListIterator<T> implements ListIterator<T> {
    private final ListIterator<T> itr;

    public ImmutableListIterator(ListIterator<T> itr) {
        this.itr = itr;
    }

    @Override
    public boolean hasNext() {
        return itr.hasNext();
    }

    @Override
    public T next() {
        return itr.next();
    }

    @Override
    public boolean hasPrevious() {
        return itr.hasPrevious();
    }

    @Override
    public T previous() {
        return itr.previous();
    }

    @Override
    public int nextIndex() {
        return itr.nextIndex();
    }

    @Override
    public int previousIndex() {
        return itr.previousIndex();
    }

    @Override
    public void remove() {
        throw new RuntimeException("Cannot modify an immutable iterator");
    }

    @Override
    public void set(T t) {
        throw new RuntimeException("Cannot modify an immutable iterator");
    }

    @Override
    public void add(T t) {
        throw new RuntimeException("Cannot modify an immutable iterator");
    }
}
