package com.rapido;

import java.util.Map;

public class Substitution {
    private double horseToElephant;
    private double elephantToArmouredTank;
    private double armouredTankToSlingGun;

    private Substitution(SubstitutionBuilder substitutionBuilder) {
        this.horseToElephant = substitutionBuilder.horseToElephant;
        this.elephantToArmouredTank = substitutionBuilder.elephantToArmouredTank;
        this.armouredTankToSlingGun = substitutionBuilder.armouredTankToSlingGun;
    }

    private double getHorseToElephant() {
        return horseToElephant;
    }

    private double getElephantToArmouredTank() {
        return elephantToArmouredTank;
    }

    private double getArmouredTankToSlingGun() {
        return armouredTankToSlingGun;
    }

    public static class SubstitutionBuilder {
        private double horseToElephant;
        private double elephantToArmouredTank;
        private double armouredTankToSlingGun;

        public SubstitutionBuilder setHorseToElephantConversion(int horseToElephant) {
            this.horseToElephant = horseToElephant;
            return this;
        }

        public SubstitutionBuilder setElephantToArmouredTankConversion(int elephantToArmouredTank) {
            this.elephantToArmouredTank = elephantToArmouredTank;
            return this;
        }

        public SubstitutionBuilder setArmouredTankToSlingGunConversion(int armouredTankToSlingGun) {
            this.armouredTankToSlingGun = armouredTankToSlingGun;
            return this;
        }

        public Substitution build() {
            return new Substitution(this);
        }
    }

    public int substituteElephantWithHorses(int elephantsCount) {
        return (int) Math.ceil(elephantsCount * getHorseToElephant());
    }

    public int substituteHorsesWithElephant(int horsesCount) {
        return (int) Math.ceil(horsesCount / getHorseToElephant());
    }

    public int substituteArmouredTankWithElephants(int armouredTankCount) {
        return (int) Math.ceil(armouredTankCount * getElephantToArmouredTank());
    }

    public int substituteElephantsWithArmouredTank(int elephantsCount) {
        return (int) Math.ceil(elephantsCount / getElephantToArmouredTank());
    }

    public int substituteSlingGunWithArmouredTank(int slingGunCount) {
        return (int) Math.ceil(slingGunCount * getArmouredTankToSlingGun());
    }

    public int substituteArmouredTankWithSlingGun(int armouredTankCount) {
        return (int) Math.ceil(armouredTankCount / getArmouredTankToSlingGun());
    }
}
