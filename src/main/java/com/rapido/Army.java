package com.rapido;

public class Army {
    private String rulerName;
    private Battalion horses;
    private Battalion elephants;
    private Battalion armouredTank;
    private Battalion slingGun;

    private Army(ArmyBuilder armyBuilder) {
        this.horses = armyBuilder.horses;
        this.elephants = armyBuilder.elephants;
        this.armouredTank = armyBuilder.armouredTank;
        this.slingGun = armyBuilder.slingGun;
        this.rulerName = armyBuilder.rulerName;
    }

    public String getRulerName() {
        return rulerName;
    }

    public Battalion getHorses() {
        return horses;
    }

    public Battalion getElephants() {
        return elephants;
    }

    public Battalion getArmouredTank() {
        return armouredTank;
    }

    public Battalion getSlingGun() {
        return slingGun;
    }

    public static class ArmyBuilder {
        private Battalion horses;
        private Battalion elephants;
        private Battalion armouredTank;
        private Battalion slingGun;
        private String rulerName;
        private int count;

        public ArmyBuilder() {
            count = 0;
        }

        public ArmyBuilder setRulerName(String rulerName) {
            this.rulerName = rulerName;
            count++;
            return this;
        }

        public ArmyBuilder setHorses(Battalion horses) {
            this.horses = horses;
            count++;
            return this;
        }

        public ArmyBuilder setElephants(Battalion elephants) {
            this.elephants = elephants;
            count++;
            return this;
        }

        public ArmyBuilder setArmouredTank(Battalion armouredTank) {
            this.armouredTank = armouredTank;
            count++;
            return this;
        }

        public ArmyBuilder setSlingGun(Battalion slingGun) {
            this.slingGun = slingGun;
            count++;
            return this;
        }

        public Army build() throws Exception{
            if(count != 5)
                throw new Exception();
            return new Army(this);
        }
    }
}
