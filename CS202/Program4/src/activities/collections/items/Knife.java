/* Alana Gilston - 3/5/21 - CS202 - Program 4
 * FountainPen.java
 *
 * The FountainPen class manages a fountain pen in a collection.
 */
package activities.collections.items;

public class Knife extends CollectionItem {
    /**
     * Length of the knife blade.
     */
    private double bladeLength;
    /**
     * Material of the blade, e.g. stainless steel, carbon steel, etc.
     */
    private String bladeMaterial;

    /**
     * Create a new Knife.
     */
    public Knife() {
        bladeLength = 0.0;
        bladeMaterial = "";
    }

    /**
     * Create a new Knife.
     * @param name Name of the knife
     * @param price Price paid for the knife
     * @param bladeLength Length of the blade
     * @param bladeMaterial Material of the blade
     */
    public Knife(String name, double price, double bladeLength, String bladeMaterial) {
        super(name, price);
        this.bladeLength = bladeLength;
        this.bladeMaterial = bladeMaterial;
    }

    /**
     * Create a new Knife from user input.
     * @return The Knife created
     */
    @Override
    public Knife create() {
        if(super.create() == null)
            return null;

        System.out.print("Blade length: ");
        bladeLength = getDoubleInput();

        System.out.print("Blade material: ");
        bladeMaterial = getStringInput();

        return this;
    }

    /**
     * Display the Knife.
     */
    public void display() {
        super.display();
        System.out.println("Blade material: " + bladeMaterial);
        System.out.println("Blade length: " + bladeLength);
    }
}
