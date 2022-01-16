/* Alana Gilston - 3/3/21 - CS202 - Program 4
 * CustomItem.java
 *
 * This is the main file to manage the ActivityManager class.
 */
import utils.BoundedInteger;

import java.util.HashMap;
import java.util.Map;

public class CustomItem {
    private HashMap<String, String> stringProperties;
    private HashMap<String, Integer> intProperties;
    private HashMap<String, BoundedInteger> boundedIntProperties;
    private HashMap<String, Boolean> boolProperties;
    private HashMap<String, String[]> listProperties;
    private HashMap<String, Double> doubleProperties;

    public CustomItem() {
        stringProperties = null;
        boundedIntProperties = null;
        intProperties = null;
        boolProperties = null;
        listProperties = null;
        doubleProperties = null;
    }

    public CustomItem(CustomItem copy) {
        if(copy.stringProperties != null)
            stringProperties = new HashMap<>(copy.stringProperties);
        else
            stringProperties = null;

        if(copy.intProperties != null)
            intProperties = new HashMap<>(copy.intProperties);
        else
            intProperties = null;

        if(copy.boundedIntProperties != null) {
            boundedIntProperties = new HashMap<>();

            for(Map.Entry<String, BoundedInteger> entry : copy.boundedIntProperties.entrySet())
                boundedIntProperties.put(entry.getKey(), new BoundedInteger(entry.getValue()));
        }
        else
            boundedIntProperties = null;

        if(copy.boolProperties != null)
            boolProperties = new HashMap<>(copy.boolProperties);
        else
            boolProperties = null;

        if(copy.listProperties != null)
            listProperties = new HashMap<>(copy.listProperties);
        else
            listProperties = null;

        if(copy.doubleProperties != null)
            doubleProperties = new HashMap<>(copy.doubleProperties);
        else
            doubleProperties = null;
    }

    public boolean addStringProperty(String name, String defaultValue) {
        if(stringProperties == null)
            stringProperties = new HashMap<>();

        if(stringProperties.containsKey(name))
            return false;

        stringProperties.put(name, defaultValue);
        return true;
    }

    public boolean removeStringProperty(String name) {
        if(stringProperties == null || !stringProperties.containsKey(name))
            return false;

        stringProperties.remove(name);
        return true;
    }

    public boolean addIntProperty(String name, int defaultValue) {
        if(intProperties == null)
            intProperties = new HashMap<>();

        if(intProperties.containsKey(name))
            return false;

        intProperties.put(name, defaultValue);
        return true;
    }

    public boolean removeIntProperty(String name) {
        if(intProperties == null || !intProperties.containsKey(name))
            return false;

        intProperties.remove(name);
        return true;
    }

    public boolean addBoundedIntProperty(String name, BoundedInteger defaultValue) {
        if(boundedIntProperties == null)
            boundedIntProperties = new HashMap<>();

        if(boundedIntProperties.containsKey(name))
            return false;

        boundedIntProperties.put(name, defaultValue);
        return true;
    }

    public boolean removeBoundedIntProperty(String name) {
        if(boundedIntProperties == null || !boundedIntProperties.containsKey(name))
            return false;

        boundedIntProperties.remove(name);
        return true;
    }

    public boolean addBoolProperty(String name, boolean defaultValue) {
        if(boolProperties == null)
            boolProperties = new HashMap<>();

        if(boolProperties.containsKey(name))
            return false;

        boolProperties.put(name, defaultValue);
        return true;
    }

    public boolean removeBoolProperty(String name) {
        if(boolProperties == null || !boolProperties.containsKey(name))
            return false;

        boolProperties.remove(name);
        return true;
    }

    public boolean addListProperty(String name, String[] options) {
        if(listProperties == null)
            listProperties = new HashMap<>();

        if(listProperties.containsKey(name))
            return false;

        listProperties.put(name, options);
        return true;
    }

    public boolean removeListProperty(String name) {
        if(listProperties == null || !listProperties.containsKey(name))
            return false;

        listProperties.remove(name);
        return true;
    }

    public boolean addDoubleProperty(String name, double defaultValue) {
        if(doubleProperties == null)
            doubleProperties = new HashMap<>();

        if(doubleProperties.containsKey(name))
            return false;

        doubleProperties.put(name, defaultValue);
        return true;
    }

    public boolean removeDoubleProperty(String name) {
        if(doubleProperties == null || !doubleProperties.containsKey(name))
            return false;

        doubleProperties.remove(name);
        return true;
    }
}
