package fol.template.excel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class SimpleArrayElement<E> implements ArrayElement<E>{
	
	private volatile List<E> elementList = new ArrayList<E>();
	
	private volatile boolean clear;
	
	@Override
	public int size() {
		return elementList.size();
	}

	@Override
	public boolean isEmpty() {
		return elementList.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return elementList.contains(o);
	}

	@Override
	public Iterator<E> iterator() {
		return elementList.iterator();
	}

	@Override
	public Object[] toArray() {
		return elementList.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return elementList.toArray(a);
	}

	@Override
	public boolean add(E e) {
		return elementList.add(e);
	}

	@Override
	public boolean remove(Object o) {
		return elementList.remove(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return elementList.containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		return elementList.addAll(c);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return elementList.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return elementList.retainAll(c);
	}

	@Override
	public void clear() {
		elementList.clear();
	}

	public synchronized void clearEmptyElement() {
		if(!clear) {
			List<E> tempList = new ArrayList<E>(elementList.size());
			for(E value : elementList) {
				if(value == null) {
					continue;
				}
				if(value instanceof Element) {
					((Element)value).clearEmptyElement();
				}
				if((!(value instanceof Element) || !((Element)value).isEmptyElement())) {
					tempList.add(value);
				}
			}
			this.elementList = tempList;
			this.clear = true;
		}
		
	}

	@Override
	public boolean isEmptyElement() {
		if(!clear) {
			clearEmptyElement();
		}
		return elementList.isEmpty();
	}

	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		return elementList.addAll(index, c);
	}

	@Override
	public E get(int index) {
		return elementList.get(index);
	}

	@Override
	public E set(int index, E element) {
		return elementList.set(index, element);
	}

	@Override
	public void add(int index, E element) {
		elementList.add(index, element);
	}

	@Override
	public E remove(int index) {
		return elementList.remove(index);
	}

	@Override
	public int indexOf(Object o) {
		return elementList.indexOf(o);
	}

	@Override
	public int lastIndexOf(Object o) {
		return elementList.lastIndexOf(o);
	}

	@Override
	public ListIterator<E> listIterator() {
		return elementList.listIterator();
	}

	@Override
	public ListIterator<E> listIterator(int index) {
		return elementList.listIterator(index);
	}

	@Override
	public List<E> subList(int fromIndex, int toIndex) {
		return subList(fromIndex, toIndex);
	}

	@Override
	public String toString() {
		return "SimpleArrayElement [elementList=" + elementList + "]";
	}
}
