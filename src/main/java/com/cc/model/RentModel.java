package com.cc.model;

/**
 * Created by Administrator on 2015-9-9.
 * sdfsd
 */
public class RentModel {
    private int sumElec;
    private int sumwater;
    private int costelec;
    private int costwater;

    public RentModel() {
    }

    public int getSumElec() {
        return sumElec;
    }

    public int getSumwater() {
        return sumwater;
    }

    public int getCostelec() {
        return costelec;
    }

    public int getCostwater() {
        return costwater;
    }

    public void setSumElec(int sumElec) {
        this.sumElec = sumElec;
    }

    public void setSumwater(int sumwater) {
        this.sumwater = sumwater;
    }

    public void setCostelec(int costelec) {
        this.costelec = costelec;
    }

    public void setCostwater(int cost) {
        this.costwater = cost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RentModel)) return false;

        RentModel rentModel = (RentModel) o;

        if (getSumElec() != rentModel.getSumElec()) return false;
        if (getSumwater() != rentModel.getSumwater()) return false;
        if (getCostelec() != rentModel.getCostelec()) return false;
        return getCostwater() == rentModel.getCostwater();

    }

    @Override
    public int hashCode() {
        int result = getSumElec();
        result = 31 * result + getSumwater();
        result = 31 * result + getCostelec();
        result = 31 * result + getCostwater();
        return result;
    }
}
