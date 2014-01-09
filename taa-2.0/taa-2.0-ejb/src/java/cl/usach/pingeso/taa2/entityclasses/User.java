/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.usach.pingeso.taa2.entityclasses;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Diego
 */
@Entity
@Table(name = "user")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
    @NamedQuery(name = "User.findByRut", query = "SELECT u FROM User u WHERE u.rut = :rut"),
    @NamedQuery(name = "User.findByEmail", query = "SELECT u FROM User u WHERE u.email = :email"),
    @NamedQuery(name = "User.findByFirstName", query = "SELECT u FROM User u WHERE u.firstName = :firstName"),
    @NamedQuery(name = "User.findByMiddleName", query = "SELECT u FROM User u WHERE u.middleName = :middleName"),
    @NamedQuery(name = "User.findByPassword", query = "SELECT u FROM User u WHERE u.password = :password"),
    @NamedQuery(name = "User.findByPrimaryLastName", query = "SELECT u FROM User u WHERE u.primaryLastName = :primaryLastName"),
    @NamedQuery(name = "User.findByRol", query = "SELECT u FROM User u WHERE u.rol = :rol"),
    @NamedQuery(name = "User.findBySecondLastName", query = "SELECT u FROM User u WHERE u.secondLastName = :secondLastName"),
    @NamedQuery(name = "User.findByUserState", query = "SELECT u FROM User u WHERE u.userState = :userState")})
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "RUT")
    private String rut;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 255)
    @Column(name = "EMAIL")
    private String email;
    @Size(max = 255)
    @Column(name = "FIRST_NAME")
    private String firstName;
    @Size(max = 255)
    @Column(name = "MIDDLE_NAME")
    private String middleName;
    @Size(max = 255)
    @Column(name = "PASSWORD")
    private String password;
    @Size(max = 255)
    @Column(name = "PRIMARY_LAST_NAME")
    private String primaryLastName;
    @Size(max = 255)
    @Column(name = "ROL")
    private String rol;
    @Size(max = 255)
    @Column(name = "SECOND_LAST_NAME")
    private String secondLastName;
    @Size(max = 255)
    @Column(name = "USER_STATE")
    private String userState;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "user")
    private Student student;

    public User() {
    }

    public User(String rut) {
        this.rut = rut;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPrimaryLastName() {
        return primaryLastName;
    }

    public void setPrimaryLastName(String primaryLastName) {
        this.primaryLastName = primaryLastName;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getSecondLastName() {
        return secondLastName;
    }

    public void setSecondLastName(String secondLastName) {
        this.secondLastName = secondLastName;
    }

    public String getUserState() {
        return userState;
    }

    public void setUserState(String userState) {
        this.userState = userState;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rut != null ? rut.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.rut == null && other.rut != null) || (this.rut != null && !this.rut.equals(other.rut))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.usach.pingeso.taa2.entityclasses.User[ rut=" + rut + " ]";
    }
    
}
