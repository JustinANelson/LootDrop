package com.forgottenartsstudios.database.entities;

public class Player {

    String name;
    String job;
    Inventory[] bagInventory;
    Inventory[] equippedInventory;
    Stat stat;
    int currLootInField;
    int minLoopPerDrop;
    int maxLoopPerDrop;
    int maxLootInField;

}
