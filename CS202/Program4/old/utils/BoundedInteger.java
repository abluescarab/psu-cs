package utils;

public class BoundedInteger {
    private int min;
    private int max;
    private int value;

    public BoundedInteger() {
        min = Integer.MIN_VALUE;
        max = Integer.MAX_VALUE;
        value = 0;
    }

    public BoundedInteger(int min, int max) {
        this.min = min;
        this.max = max;
        value = Math.max(0, min);
    }

    public BoundedInteger(int min, int max, int value) {
        this.min = min;
        this.max = max;
        value = Math.min(Math.max(value, min), max);
        this.value = value;
    }

    public BoundedInteger(BoundedInteger copy) {
        min = copy.min;
        max = copy.max;
        value = copy.value;
    }

    public void setMin(int min) {
        this.min = min;

        if(value < min)
            value = min;
    }

    public int getMin() {
        return min;
    }

    public void setMax(int max) {
        this.max = max;

        if(value > max)
            value = max;
    }

    public int getMax() {
        return max;
    }

    public void setValue(int value) {
        this.value = Math.min(Math.max(value, min), max);
    }

    public int getValue() {
        return value;
    }
}
