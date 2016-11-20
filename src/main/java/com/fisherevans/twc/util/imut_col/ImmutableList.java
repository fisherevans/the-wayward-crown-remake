package com.fisherevans.twc.util.imut_col;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ImmutableList<T> implements List<T> {
    private final List<T> list;

    public ImmutableList(List<T> list) {
        this.list = list;
    }

    @Override
    public Iterator<T> iterator() {
        return new ImmutableIterator<T>(list.iterator());
    }

    @Override
    public ListIterator<T> listIterator() {
        return new ImmutableListIterator<T>(list.listIterator());
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return new ImmutableListIterator<T>(list.listIterator(index));
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return list.contains(o);
    }

    @Override
    public Object[] toArray() {
        return list.toArray();
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return list.toArray(a);
    }

    @Override
    public boolean add(T t) {
        throw new RuntimeException("Canonot modify an immutable list");
    }

    @Override
    public boolean remove(Object o) {
        throw new RuntimeException("Canonot modify an immutable list");
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return list.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        throw new RuntimeException("Canonot modify an immutable list");
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        throw new RuntimeException("Canonot modify an immutable list");
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new RuntimeException("Canonot modify an immutable list");
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new RuntimeException("Canonot modify an immutable list");
    }

    @Override
    public void clear() {
        throw new RuntimeException("Canonot modify an immutable list");
    }

    @Override
    public T get(int index) {
        return list.get(index);
    }

    @Override
    public T set(int index, T element) {
        throw new RuntimeException("Canonot modify an immutable list");
    }

    @Override
    public void add(int index, T element) {
        throw new RuntimeException("Canonot modify an immutable list");
    }

    @Override
    public T remove(int index) {
        throw new RuntimeException("Canonot modify an immutable list");
    }

    @Override
    public int indexOf(Object o) {
        return list.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return list.lastIndexOf(o);
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        return list.subList(fromIndex, toIndex);
    }
}
