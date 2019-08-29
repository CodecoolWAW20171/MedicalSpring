package com.medbis.entity;


import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity()
@Table(name="diseases", schema="public")
public class Disease {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="diseases_id")
    private int diseaseId;
    @Column(name = "diseases_code")
    @Size(min=1, max=6)
    private String diseaseCode;
    @Column(name="diseases_name")
    @NotEmpty
    private String diseaseName;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            mappedBy = "patientDiseases")
    private Set<Patient> patients = new HashSet<>();

    public Disease() {
    }

    public Disease(@Size(min = 1, max = 6) String diseaseCode, @NotEmpty String diseaseName, Set<Patient> patients) {
        this.diseaseCode = diseaseCode;
        this.diseaseName = diseaseName;
        this.patients = patients;
    }

    public int getDiseaseId() {
        return diseaseId;
    }

    public void setDiseaseId(int diseaseId) {
        this.diseaseId = diseaseId;
    }

    public String getDiseaseCode() {
        return diseaseCode;
    }

    public void setDiseaseCode(String diseaseCode) {
        this.diseaseCode = diseaseCode;
    }

    public String getDiseaseName() {
        return diseaseName;
    }

    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }

    public Set<Patient> getPatients() {
        return patients;
    }

    public void setPatients(Set<Patient> patients) {
        this.patients = patients;
    }

    @Override
    public String toString() {
        return "Disease{" +
                "diseaseId=" + diseaseId +
                ", diseaseCode='" + diseaseCode + '\'' +
                ", diseaseName='" + diseaseName + '\'' +
                '}';
    }
}
