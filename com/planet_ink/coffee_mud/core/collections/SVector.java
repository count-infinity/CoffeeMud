package com.planet_ink.coffee_mud.core.collections;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.naming.OperationNotSupportedException;

/*
   Copyright 2010-2018 Bo Zimmerman

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

	   http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */

/*
 * A version of the CopyOnWriteArrayList class that provides to "safe" adds
 * and removes by copying the underlying CopyOnWriteArrayList whenever those
 * operations are done.
 */
public class SVector<T> implements Serializable, Iterable<T>, Collection<T>, CList<T>, RandomAccess
{
	private static final long	serialVersionUID	= 6687178785122561992L;
	private List<T> list = Collections.synchronizedList(new CopyOnWriteArrayList<T>());

	public SVector()
	{
		super();
	}

	public SVector(int size)
	{
		super();
	}

	public SVector(List<T> T)
	{
		super();
		if (T != null)
			this.addAll(T);
	}

	public SVector(T[] T)
	{
		super();
		if (T != null)
		{
			for (final T o : T)
				add(o);
		}
	}

	public SVector(Enumeration<T> T)
	{
		super();
		if (T != null)
		{
			for (; T.hasMoreElements();)
				list.add(T.nextElement());
		}
	}

	public SVector(Iterator<T> T)
	{
		super();
		if (T != null)
		{
			for (; T.hasNext();)
				list.add(T.next());
		}
	}

	public SVector(Set<T> T)
	{
		super();
		if (T != null)
		{
			for (final T o : T)
				list.add(o);
		}
	}

	public void addAll(Enumeration<T> T)
	{
		if (T != null)
		{
			for (; T.hasMoreElements();)
				list.add(T.nextElement());
		}
	}

	public void addAll(T[] T)
	{
		if (T != null)
		{
			for (final T e : T)
				list.add(e);
		}
	}

	public void addAll(Iterator<T> T)
	{
		if (T != null)
		{
			for (; T.hasNext();)
				list.add(T.next());
		}
	}

	public void removeAll(Enumeration<T> T)
	{
		if (T != null)
		{
			for (; T.hasMoreElements();)
				list.remove(T.nextElement());
		}
	}

	public void removeAll(Iterator<T> T)
	{
		if (T != null)
		{
			for (; T.hasNext();)
				list.remove(T.next());
		}
	}

	public void removeAll(List<T> T)
	{
		if (T != null)
		{
			for (final T o : T)
				list.remove(o);
		}
	}

	public int capacity()
	{
		return list.size();
	}

	public Vector<T> toVector()
	{
		return new XVector<T>(list);
	}

	@SuppressWarnings("unchecked")
	public SVector<T> copyOf()
	{
		try
		{
			SVector<T> copy= (SVector<T>) clone();
			copy.list = Collections.synchronizedList(copy);
			return copy;
		}
		catch (final Exception e)
		{
			return new SVector<T>(list);
		}
	}

	public void copyInto(Object[] anArray)
	{
		list.toArray(anArray);
	}

	public T elementAt(int index)
	{
		return list.get(index);
	}

	public Enumeration<T> elements()
	{
		return new Enumeration<T>() 
		{
			final Iterator<T>	i	= list.iterator();

			@Override
			public boolean hasMoreElements()
			{
				return i.hasNext();
			}

			@Override
			public T nextElement()
			{
				return i.next();
			}
		};
	}

	public void ensureCapacity(int minCapacity)
	{
		throw new IllegalArgumentException();
	}

	public T firstElement()
	{
		return (list.size() == 0) ? null : list.get(0);
	}

	public T lastElement()
	{
		return (list.size() == 0) ? null : list.get(size() - 1);
	}

	public void setElementAt(T obj, int index)
	{
		list.set(index, obj);
	}

	public void setSize(int newSize)
	{
		if (newSize == 0)
			list.clear();
		else
			throw new IllegalArgumentException();
	}

	public void trimToSize()
	{
	}

	public void addElement(T obj)
	{
		list.add(obj);
	}

	public void insertElementAt(T obj, int index)
	{
		list.add(index, obj);
	}

	public void removeAllElements()
	{
		list.clear();
	}

	@Override
	public int size()
	{
		return list.size();
	}
	
	@Override
	public boolean isEmpty()
	{
		return list.isEmpty();
	}
	
	@Override
	public boolean contains(Object o)
	{
		return list.contains(o);
	}
	
	@Override
	public Iterator<T> iterator()
	{
		return list.iterator();
	}
	
	@Override
	public Object[] toArray()
	{
		return list.toArray();
	}
	
	@SuppressWarnings("hiding")
	@Override
	public <T> T[] toArray(T[] a)
	{
		return list.toArray(a);
	}
	
	@Override
	public boolean add(T e)
	{
		return list.add(e);
	}
	
	@Override
	public boolean remove(Object o)
	{
		return list.remove(o);
	}
	
	@Override
	public boolean containsAll(Collection<?> c)
	{
		return list.containsAll(c);
	}
	
	@Override
	public boolean addAll(Collection<? extends T> c)
	{
		return list.addAll(c);
	}
	
	@Override
	public boolean addAll(int index, Collection<? extends T> c)
	{
		return list.addAll(c);
	}
	
	@Override
	public boolean removeAll(Collection<?> c)
	{
		return list.removeAll(c);
	}
	
	@Override
	public boolean retainAll(Collection<?> c)
	{
		return list.retainAll(c);
	}
	
	@Override
	public void clear()
	{
		list.clear();
	}
	
	@Override
	public boolean equals(Object o)
	{
		return list.equals(o);
	}
	
	@Override
	public int hashCode()
	{
		return list.hashCode();
	}
	
	@Override
	public T get(int index)
	{
		return list.get(index);
	}
	
	@Override
	public T set(int index, T element)
	{
		return list.set(index, element);
	}
	
	@Override
	public void add(int index, T element)
	{
		list.add(index,element);
	}
	
	@Override
	public T remove(int index)
	{
		return list.remove(index);
	}
	
	@Override
	public int indexOf(Object o)
	{
		return list.indexOf(o);
	}
	
	@Override
	public int lastIndexOf(Object o)
	{
		return list.lastIndexOf(o);
	}
	
	@Override
	public ListIterator<T> listIterator()
	{
		return list.listIterator();
	}
	
	@Override
	public ListIterator<T> listIterator(int index)
	{
		return list.listIterator(index);
	}
	
	@Override
	public List<T> subList(int fromIndex, int toIndex)
	{
		return list.subList(fromIndex, toIndex);
	}
	
	public boolean removeElement(Object obj)
	{
		return list.remove(obj);
	}

	public void removeElementAt(int index)
	{
		list.remove(index);
	}
}
