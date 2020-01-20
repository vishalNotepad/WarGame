package com.rapido;

import java.util.Scanner;

public class WarMain {

    public static void main(String[] args) {
        // Forming Lengaburu Army
        Battalion horses = new Horse(100);
        Battalion elephants = new Elephant(50);
        Battalion armouredTank = new ArmouredTank(10);
        Battalion slingGun = new SlingGun(5);

        Army lengaburuArmy;
        try {
            lengaburuArmy = new Army.ArmyBuilder()
                    .setHorses(horses)
                    .setElephants(elephants)
                    .setArmouredTank(armouredTank)
                    .setSlingGun(slingGun)
                    .setRulerName("Lengaburu")
                    .build();
        } catch (Exception ex) {
            System.out.println("Army could not be formed, set all necessary battalion");
            return;
        }

        // Forming Falcone Army
        Scanner sc = new Scanner(System.in);
        System.out.println("Al Falcone attacks with army size:");

        System.out.println("Horses: ");
        horses = new Horse(sc.nextInt());

        System.out.println("Elephants: ");
        elephants = new Elephant(sc.nextInt());

        System.out.println("Armoured Tank: ");
        armouredTank = new ArmouredTank(sc.nextInt());

        System.out.println("Sling Gun: ");
        slingGun = new SlingGun(sc.nextInt());

        Army falconeArmy;
        try {
            falconeArmy = new Army.ArmyBuilder()
                    .setHorses(horses)
                    .setElephants(elephants)
                    .setArmouredTank(armouredTank)
                    .setSlingGun(slingGun)
                    .setRulerName("Falcone")
                    .build();
        } catch (Exception ex) {
            System.out.println("Army could not be formed, set all necessary battalion");
            return;
        }

        Substitution substitution = new Substitution.SubstitutionBuilder()
                .setHorseToElephantConversion(2)
                .setElephantToArmouredTankConversion(2)
                .setArmouredTankToSlingGunConversion(2)
                .build();

        int lengaburuStrength = 2;  // 2X
        // Check army to be deployed and war results
        deployArmy(falconeArmy, lengaburuArmy, lengaburuStrength, substitution);
    }

    private static void deployArmy(Army attackerArmy, Army defenderArmy, int defenderStrength, Substitution substitution) {
        boolean won = true;
        int availableHorses = defenderArmy.getHorses().getCount();
        int availableElephants = defenderArmy.getElephants().getCount();
        int availableArmouredTank = defenderArmy.getArmouredTank().getCount();
        int availableSlingGun = defenderArmy.getSlingGun().getCount();

        int requiredHorses = (int) Math.ceil(attackerArmy.getHorses().getCount() / (1.0 * defenderStrength));
        int requiredElephants = (int) Math.ceil(attackerArmy.getElephants().getCount() / (1.0 * defenderStrength));
        int requiredArmouredTank = (int) Math.ceil(attackerArmy.getArmouredTank().getCount() / (1.0 * defenderStrength));
        int requiredSlingGun = (int) Math.ceil(attackerArmy.getSlingGun().getCount() / (1.0 * defenderStrength));

        boolean horsesScarce = requiredHorses > availableHorses;
        int remainingHorses = availableHorses - (!horsesScarce ? requiredHorses : availableHorses);

        boolean elephantsScarce = requiredElephants > availableElephants;
        int remainingElephants = availableElephants - (!elephantsScarce ? requiredElephants : availableElephants);

        boolean armouredTankScarce = requiredArmouredTank > availableArmouredTank;
        int remainingArmouredTank = availableArmouredTank - (!armouredTankScarce ? requiredArmouredTank : availableArmouredTank);

        boolean slingGunScarce = requiredSlingGun > availableSlingGun;
        int remainingSlingGun = availableSlingGun - (!slingGunScarce ? requiredSlingGun : availableSlingGun);


        // Keeping won flag right now so that later if requirement changes not to calculate
        // any more battalion count when defender is not winning then those changes can be
        // taken care of.
        if (horsesScarce) {
            int scarceHorseCount = requiredHorses - availableHorses;
            if (elephantsScarce)
                won = false;
            else {
                int neededElephants = substitution.substituteHorsesWithElephant(scarceHorseCount);
                if (neededElephants <= remainingElephants) {
                    remainingElephants -= neededElephants;
                    horsesScarce = false;
                } else
                    won = false;
            }
        }

        if (elephantsScarce) {
            int scarceElephantCount = requiredElephants - availableElephants;
            if (horsesScarce && armouredTankScarce)
                won = false;
            else if (!horsesScarce) {
                int neededHorses = substitution.substituteElephantWithHorses(scarceElephantCount);
                if (neededHorses <= remainingHorses) {
                    remainingHorses -= neededHorses;
                    elephantsScarce = false;
                } else if (remainingHorses > 0) {
                    scarceElephantCount -= substitution.substituteHorsesWithElephant(remainingHorses);
                    remainingHorses = 0;
                }
            }
            if (elephantsScarce && !armouredTankScarce) {
                int neededarmouredTank = substitution.substituteElephantsWithArmouredTank(scarceElephantCount);
                if (neededarmouredTank <= remainingArmouredTank) {
                    remainingArmouredTank -= neededarmouredTank;
                    elephantsScarce = false;
                } else
                    won = false;
            }
        }

        if (armouredTankScarce) {
            int scarceArmouredTankCount = requiredArmouredTank - availableArmouredTank;
            if (elephantsScarce && slingGunScarce)
                won = false;
            else if (!elephantsScarce) {
                int neededElephants = substitution.substituteArmouredTankWithElephants(scarceArmouredTankCount);
                if (neededElephants <= remainingElephants) {
                    remainingElephants -= neededElephants;
                    armouredTankScarce = false;
                } else if (remainingElephants > 0) {
                    scarceArmouredTankCount -= substitution.substituteElephantsWithArmouredTank(remainingElephants);
                    remainingElephants = 0;
                }
            }
            if (armouredTankScarce && !slingGunScarce) {
                int neededSlingGun = substitution.substituteArmouredTankWithSlingGun(scarceArmouredTankCount);
                if (neededSlingGun <= remainingSlingGun) {
                    remainingSlingGun -= neededSlingGun;
                    armouredTankScarce = false;
                } else
                    won = false;
            }
        }

        if (slingGunScarce) {
            int scarceSlingGunCount = requiredSlingGun - availableSlingGun;
            if (armouredTankScarce)
                won = false;
            else {
                int neededArmouredTank = substitution.substituteSlingGunWithArmouredTank(scarceSlingGunCount);
                if (neededArmouredTank <= remainingArmouredTank) {
                    remainingArmouredTank -= neededArmouredTank;
                    slingGunScarce = false;
                } else if (remainingArmouredTank > 0) {
                    scarceSlingGunCount -= substitution.substituteArmouredTankWithSlingGun(remainingArmouredTank);
                    remainingArmouredTank = 0;
                    won = false;
                }
            }
        }


        won = !horsesScarce && !elephantsScarce && !armouredTankScarce && !slingGunScarce;
        System.out.println(defenderArmy.getRulerName() + " deploys "
                + (availableHorses - remainingHorses) + " H, "
                + (availableElephants - remainingElephants) + " E, "
                + (availableArmouredTank - remainingArmouredTank) + " AT, "
                + (availableSlingGun - remainingSlingGun) + " SG and "
                + (won ? "wins" : "loses")
        );
    }
}
