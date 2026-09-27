package com.hms.doctors;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.UUID;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Entity
@Table(name = "doctor_departments")
public class DoctorDepartment {
    @EmbeddedId private Key key;
    protected DoctorDepartment() { }
    public DoctorDepartment(UUID doctorId, UUID departmentId) { this.key = new Key(doctorId, departmentId); }
    public Key getKey() { return key; }

    @Embeddable
    public static class Key implements Serializable {
        @Column(name = "doctor_id") private UUID doctorId;
        @Column(name = "department_id") private UUID departmentId;
        protected Key() { }
        public Key(UUID doctorId, UUID departmentId) { this.doctorId = doctorId; this.departmentId = departmentId; }
        public UUID getDoctorId() { return doctorId; }
        public UUID getDepartmentId() { return departmentId; }
        @Override public boolean equals(Object o) { return o instanceof Key other && doctorId.equals(other.doctorId) && departmentId.equals(other.departmentId); }
        @Override public int hashCode() { return 31 * doctorId.hashCode() + departmentId.hashCode(); }
    }
}
