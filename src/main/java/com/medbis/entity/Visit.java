package com.medbis.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity

@Table(name = "visits", schema = "public")
public class Visit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "visit_id")
    private int visitId;

    @NotNull(message = "is required")
    @Column(name = "employee_id")
    private int visitEmployeeId;

    @NotNull(message = "is required")
    @Column(name = "patient_id")
    private int visitPatientId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "is required")
    @Column(name = "date")
    private LocalDate visitDate;

    @Column(name = "status")
    private  Boolean visitStatus = false;

    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "employee_id", insertable = false, updatable = false)
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "patient_id", referencedColumnName = "patient_id", insertable = false, updatable = false)
    private Patient patient;



    @OneToMany(mappedBy = "primaryKey.visit", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<VisitTreatment> visitTreatments = new ArrayList<>();

    public List<VisitTreatment> getVisitTreatments() {
        return visitTreatments;
    }
    public void setVisitTreatments(List<VisitTreatment> visitTreatments) {
        this.visitTreatments = visitTreatments;
    }

    public void addVisitTreatment(VisitTreatment visitTreatment){
        this.visitTreatments.add(visitTreatment);
    }


    //    @ManyToMany(fetch = FetchType.LAZY,
//            cascade = { CascadeType.PERSIST, CascadeType.MERGE })
//    @JoinTable(
//            name = "visits_services",
//            joinColumns = { @JoinColumn(name = "visit_id") },
//            inverseJoinColumns = { @JoinColumn(name = "service_id") }
//    )
//    private List<Treatment> services = new ArrayList<>();

}
