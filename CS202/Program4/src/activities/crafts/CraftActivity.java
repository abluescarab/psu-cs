package activities.crafts;

import activities.*;
import activities.collections.items.*;
import activities.crafts.items.*;

public class CraftActivity extends Activity {
    private CraftType craftType;

    public CraftActivity() {
        super();
        craftType = CraftType.PAINTING;
    }

    @Override
    public CraftActivity create() {
        int option = 0;

        super.create();

        System.out.println("What craft projects are you working on?");
        System.out.println("1) Painting");
        System.out.println("2) Sculpture");
        System.out.println("3) Woodworking");

        option = getNumericInput(3);

        if(option == 1) {
            craftType = CraftType.PAINTING;
            addPaintingProject();
        }
        else if(option == 2) {
            craftType = CraftType.SCULPTURE;
            addSculptureProject();
        }
        else {
            craftType = CraftType.WOODWORKING;
            addWoodworkingProject();
        }

        return this;
    }

    public CraftType getType() {
        return craftType;
    }

    private void addPaintingProject() {
        boolean quit = false;

        do {
            System.out.print("Do you want to add a painting project? (y/n) ");

            if(getConfirmation())
                add(new PaintingProject().create());
            else
                quit = true;
        } while(!quit);
    }

    private void addSculptureProject() {
        boolean quit = false;

        do {
            System.out.print("Do you want to add a sculpture project? (y/n) ");

            if(getConfirmation())
                add(new SculptureProject().create());
            else
                quit = true;
        } while(!quit);
    }

    private void addWoodworkingProject() {
        boolean quit = false;

        do {
            System.out.print("Do you want to add a woodworking project? (y/n) ");

            if(getConfirmation())
                add(new WoodworkingProject().create());
            else
                quit = true;
        } while(!quit);
    }
}
