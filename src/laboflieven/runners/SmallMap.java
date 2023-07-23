package laboflieven.runners;

import java.util.*;
import java.util.stream.Collectors;

public class SmallMap implements Map {
    private final List<String> keys;
    private final List<Object> values;

    public SmallMap(List<String> keys) {
        this.keys = keys;
        values = new ArrayList<>();
        for (int i = 0; i < keys.size(); i++) {
            values.add(null);
        }
    }

    @Override
    public int size() {
        return keys.size();
    }

    @Override
    public boolean isEmpty() {
        return keys.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return keys.contains(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return values.contains(value);
    }

    @Override
    public Object get(Object key) {
        var index = keys.indexOf(key);
        return values.get(index);
    }

    @Override
    public Object put(Object key, Object value) {
        var index = keys.indexOf(key);
        values.set(index, value);
        return null;
    }

    @Override
    public Object remove(Object key) {
        return null;
    }

    @Override
    public void putAll(Map m) {

    }

    @Override
    public void clear() {

    }

    @Override
    public Set keySet() {
        return keys.stream().collect(Collectors.toSet());
    }

    @Override
    public Collection values() {
        return null;
    }

    @Override
    public Set<Entry> entrySet() {
        return null;
    }
}
