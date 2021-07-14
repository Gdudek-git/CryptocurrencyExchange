package model.database.entity;


import javax.persistence.*;

@Entity
@Table(name = "usercontact")
public  class UserContact {

    @Id
    @Column(name = "username")
    private String username;

    @Column(name = "phonenumber")
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "country")
    private String country;

    @OneToOne
    @MapsId
    @JoinColumn(name = "username")
    private User user;


    public void setUser(User user)
    {
        this.user = user;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

}