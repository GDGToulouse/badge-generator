package gdg.toulouse.interactive;

import gdg.toulouse.data.Unit;

interface Action {

    String description();

    Unit execute(String[] arguments);

}
