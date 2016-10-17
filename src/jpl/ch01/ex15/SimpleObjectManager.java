package jpl.ch01.ex15;

public class SimpleObjectManager implements ObjectManager {
	
	private String[] names;
	private Object[] values;

	@Override
	public Object find(String name) {
		if (names == null) {
			return null;
		}
		for (int i = 0; i < names.length; i++) {
			if (names[i].equals(name)) {
				return values[i];
			}
		}
		return null;
	}

	@Override
	public void add(String name, Object item) {
		if (names == null) {
			names = new String[1];
			values = new Object[1];
			names[0] = name;
			values[0] = item;
		} else {
			String[] newNames = new String[names.length + 1];
			for (int i = 0; i < names.length; i++) {
				newNames[i] = names[i];
			}
			newNames[newNames.length - 1] = name;
			names = newNames;
			Object[] newValues = new Object[values.length + 1];
			for (int i = 0; i < values.length; i++) {
				newValues[i] = values[i];
			}
			newValues[newValues.length - 1] = item;
			values = newValues;
		}
	}

	@Override
	public void remove(String name) {
		int index = indexOf(name);
		if (index > 0) {
			String[] newNames = new String[names.length - 1];
			int namesIndex = 0;
			for (int i = 0; i < newNames.length; i++) {
				if (i == index) {
					namesIndex++;
				}
				newNames[i] = names[namesIndex];
				namesIndex++;
			}
			names = newNames;
			Object[] newValues = new String[values.length - 1];
			int valuesIndex = 0;
			for (int i = 0; i < newValues.length; i++) {
				if (i == index) {
					valuesIndex++;
				}
				newValues[i] = values[valuesIndex];
				valuesIndex++;
			}
			values = newValues;
			
		}

	}
	
	private int indexOf(String name) {
		for (int i = 0; i < names.length; i++) {
			if (names[i].equals(name)) {
				return i;
			}
		}
		return -1;
	}

}
