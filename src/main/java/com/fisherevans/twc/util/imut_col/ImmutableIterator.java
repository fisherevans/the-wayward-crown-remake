package com.fisherevans.twc.util.imut_col;

import java.util.Iterator;

public class ImmutableIterator<T> implements Iterator<T> {
    private final Iterator<T> itr;

    public ImmutableIterator(Iterator<T> itr) {
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
    public void remove() {
        throw new RuntimeException("Cannot modify an immutable iterator");
    }
}
