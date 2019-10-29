package com.medbis.entity;


import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class VisitTreatmentId implements Serializable {
    private Visit visit;
    private Treatment treatment;

    @ManyToOne(cascade = CascadeType.ALL)
    public Visit getVisit(){
        return visit;
    }

    public void setVisit(Visit visit) {
        this.visit = visit;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    public Treatment getTreatment() {
        return treatment;
    }

    public void setTreatment(Treatment treatment) {
        this.treatment = treatment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VisitTreatmentId)) return false;
        VisitTreatmentId that = (VisitTreatmentId) o;
        return Objects.equals(getVisit(), that.getVisit()) &&
                Objects.equals(getTreatment(), that.getTreatment());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getVisit(), getTreatment());
    }

    @Override
    public String toString() {
        return "VisitTreatmentId{" +
                "visit=" + visit +
                ", treatment=" + treatment +
                '}';
    }
}
