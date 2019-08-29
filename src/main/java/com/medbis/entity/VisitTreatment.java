package com.medbis.entity;

import javax.persistence.*;

@Entity
@Table(name = "visits_services", schema = "public")
@AssociationOverrides({
        @AssociationOverride(name = "primaryKey.visit", joinColumns = @JoinColumn(name = "visit_id")),
        @AssociationOverride(name = "primaryKey.treatment", joinColumns = @JoinColumn(name = "service_id")) })

public class VisitTreatment {
    // composite-id key
    private VisitTreatmentId primaryKey = new VisitTreatmentId();

    // additional fields
    @Column(name = "note")
    private String note;

    @EmbeddedId
    public VisitTreatmentId getPrimaryKey() {
        return primaryKey;
    }
    public void setPrimaryKey(VisitTreatmentId primaryKey) {
        this.primaryKey = primaryKey;
    }



    @Transient
    public Visit getVisit() {
        return getPrimaryKey().getVisit();
    }
    public void setVisit(Visit visit) {
        getPrimaryKey().setVisit(visit);
    }

    @Transient
    public Treatment getTreatment() {
        return getPrimaryKey().getTreatment();
    }
    public void setTreatment(Treatment treatment) {
        getPrimaryKey().setTreatment(treatment);
    }

    public String getNote() {
        return note;
    }
    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "VisitTreatment{" +
                "primaryKey=" + primaryKey +
                ", note='" + note + '\'' +
                '}';
    }
}