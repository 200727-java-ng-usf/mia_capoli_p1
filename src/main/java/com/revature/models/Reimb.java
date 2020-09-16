package com.revature.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "ers_reimbursments")
public class Reimb {

    //Reimb fields
    @Id
    @Column(name = "reimb_id")
    private Integer reimb_id;

    @Column(name = "amount")
    private double amount;

    @Column(name = "submitted")
    private Timestamp submitted;

    @Column(name = "first_name")
    private Timestamp resolved;

    @Column(name = "description")
    private String description;

    @Column(name = "author_id")
    private int author_id;

    @Column(name = "resolver_id")
    private int resolver_id;

    @Column(name = "reimb_status_id")
    private ReimbStatusTypes reimb_status;

    @Column(name = "reimb_type_id")
    private ReimbTypes reimb_type;

    public Reimb(Integer reimb_id, double amount, Timestamp submitted, Timestamp resolved, String description, int author_id, int resolver_id, ReimbStatusTypes reimb_status, int reimb_type_id) {
        this.reimb_id = reimb_id;
        this.amount = amount;
        this.submitted = submitted;
        this.resolved = resolved;
        this.description = description;
        this.author_id = author_id;
        this.resolver_id = resolver_id;
        this.reimb_status = reimb_status;
        this.reimb_type = ReimbTypes.getByID(reimb_type_id);
    }

    public Reimb() {

    }

    public Reimb(int amount, Timestamp submitted, Timestamp resolved, String description, int author_id, int resolver_id, ReimbStatusTypes reimb_status, int reimb_type_id) {
        this.amount = amount;
        this.submitted = submitted;
        this.resolved = resolved;
        this.description = description;
        this.author_id = author_id;
        this.resolver_id = resolver_id;
        this.reimb_status = reimb_status;
        this.reimb_type = ReimbTypes.getByID(reimb_type_id);

    }

    public Integer getReimb_id() {
        return reimb_id;
    }

    public double getAmount() {
        return amount;
    }

    public Timestamp getSubmitted() {
        return submitted;
    }

    public Timestamp getResolved() {
        return resolved;
    }

    public String getDescription() {
        return description;
    }

    public int getAuthor_id() {
        return author_id;
    }

    public int getResolver_id() {
        return resolver_id;
    }

    public ReimbStatusTypes getReimb_status() {
        return reimb_status;
    }

    public ReimbTypes getReimb_type() {
        return reimb_type;
    }

    public void setReimb_id(Integer reimb_id) {
        this.reimb_id = reimb_id;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setSubmitted(Timestamp submitted) {
        this.submitted = submitted;
    }

    public void setResolved(Timestamp resolved) {
        this.resolved = resolved;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAuthor_id(int author_id) {
        this.author_id = author_id;
    }

    public void setResolver_id(int resolver_id) {
        this.resolver_id = resolver_id;
    }

    public void setReimb_status_id(ReimbStatusTypes reimb_status) {
        this.reimb_status = reimb_status;
    }

    public void setReimb_typeId(int reimb_type_id) {
        int reimb_int = reimb_type_id;
        this.reimb_type = ReimbTypes.getByID(reimb_type_id);
    }

    public void setReimb_type(ReimbTypes reimb_type) {

        this.reimb_type = reimb_type;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reimb reimb = (Reimb) o;
        return Double.compare(reimb.amount, amount) == 0 &&
                author_id == reimb.author_id &&
                resolver_id == reimb.resolver_id &&
                reimb_status == reimb.reimb_status &&
                reimb_type == reimb.reimb_type &&
                Objects.equals(reimb_id, reimb.reimb_id) &&
                Objects.equals(submitted, reimb.submitted) &&
                Objects.equals(resolved, reimb.resolved) &&
                Objects.equals(description, reimb.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reimb_id, amount, submitted, resolved, description, author_id, resolver_id, reimb_status, reimb_type);
    }

    @Override
    public String toString() {
        return "Reimb{" +
                "reimb_id=" + reimb_id +
                ", amount=" + amount +
                ", submitted=" + submitted +
                ", resolved=" + resolved +
                ", description='" + description + '\'' +
                ", author_id=" + author_id +
                ", resolver_id=" + resolver_id +
                ", reimb_status=" + reimb_status +
                ", reimb_type_id=" + reimb_type +
                '}';
    }
}
