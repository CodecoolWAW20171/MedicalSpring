package com.medbis.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "patients", schema = "public")
public class Patient extends User {
    public Patient() {
    }

    public Patient(int patientId, String comments, String mail) {
        this.patientId = patientId;
        this.mail = mail;
        this.comments = comments;
    }


    @Id
    @GeneratedValue(  strategy = GenerationType.IDENTITY)
    //@SequenceGenerator(name = "patients_patient_id_seq") generator = "patients_patient_id_seq",
    @Column(name = "patient_id")
    private int patientId;

    @OneToMany(mappedBy="patient", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private Set<Visit> visitsPatients;

    @Column(name = "comments")
    private String comments;

    @Column(name = "mail")
    @Email
    private String mail;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
            name = "patient_medicines",
            joinColumns = { @JoinColumn(name = "patient_id") },
            inverseJoinColumns = { @JoinColumn(name = "medicine_id") }
    )
    private List<Medicine> patientMedicines = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
            name = "patient_diseases",
            joinColumns = { @JoinColumn(name = "patient_id") },
            inverseJoinColumns = { @JoinColumn(name = "diseases_id") }
    )
    private List<Disease> patientDiseases = new ArrayList<>();

    @Override
    public String toString() {
        return "Patient{" +
                "patientId=" + patientId +
                ", comments='" + comments + '\'' +
                ", patientMedicines=" + patientMedicines +
                ", patientDiseases=" + patientDiseases +
                '}';
    }
}

