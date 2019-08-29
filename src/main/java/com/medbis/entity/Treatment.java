package com.medbis.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "services", schema = "public")
public class Treatment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "service_id")
    private int treatmentId;

    @Column(name = "services_name")
    @NotEmpty
    private String name;

    @Column(name = "category_id")
    @JoinTable(name = "categories",
    joinColumns = {@JoinColumn(name = "category_id", referencedColumnName = "category_id")},
    inverseJoinColumns = {@JoinColumn(name = "category_id", referencedColumnName = "category_id")})
    private int categoryId;

    @Column(name = "services_descriptions")
    @NotEmpty
    private String description;

    @OneToMany(mappedBy = "primaryKey.treatment", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<VisitTreatment> visitTreatments = new HashSet<>();

    public Set<VisitTreatment> geVisitTreatments() {
        return visitTreatments;
    }
    public void setVisitTreatments(Set<VisitTreatment> visitTreatments) {
        this.visitTreatments = visitTreatments;
    }

    public void addVisitTreatment(VisitTreatment visitTreatment){
        this.visitTreatments.add(visitTreatment);
    }

    //    @ManyToMany(fetch = FetchType.LAZY,
//            cascade = {
//                    CascadeType.PERSIST,
//                    CascadeType.MERGE
//            },
//            mappedBy = "services")
//    private Set<Visit> visits = new HashSet<>();



    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTreatmentId() {
        return treatmentId;
    }

    public void setTreatmentId(int treatmentId) {
        this.treatmentId = treatmentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public String toString() {
        return "Treatment{" +
                "treatmentId=" + treatmentId +
                ", name='" + name + '\'' +
                ", categoryId=" + categoryId +
                ", description='" + description + '\'' +
                '}';
    }
}
