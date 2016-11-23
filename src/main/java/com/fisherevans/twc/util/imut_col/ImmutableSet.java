package com.fisherevans.twc.util.imut_col;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class ImmutableSet<T> implements Set<T> {
    private final Set<T> set;

    public ImmutableSet(Set<T> set) {
        this.set = set;
    }

    @Override
    public int size() {
        return set.size();
    }

    @Override
    public boolean isEmpty() {
        return set.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return set.contains(o);
    }

    @Override
    public Iterator<T> iterator() {
        return new ImmutableIterator<>(set.iterator());
    }

    @Override
    public Object[] toArray() {
        return set.toArray();
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return set.toArray(a);
    }

    @Override
    public boolean add(T t) {
        throw new RuntimeException("Cannot modify an immutable set");
    }

    @Override
    public boolean remove(Object o) {
        throw new RuntimeException("Cannot modify an immutable set");
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return set.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        throw new RuntimeException("Cannot modify an immutable set");
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new RuntimeException("Cannot modify an immutable set");
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new RuntimeException("Cannot modify an immutable set");
    }

    @Override
    public void clear() {
        throw new RuntimeException("Cannot modify an immutable set");
    }
}
