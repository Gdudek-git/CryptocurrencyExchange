package database.tables;


import javax.persistence.*;

@Entity
@Table(name="userwallet")
public class UserWallet {

    @Id
    @Column(name = "username")
    private String username;


    @Column(name = "BTC")
    private double btc=0.0;

    @Column(name = "ETH")
    private double eth=0.0;

    @Column(name = "DOGE")
    private double doge=0.0;

    @Column(name = "USD")
    private double usd=0.0;

    @Column(name = "EUR")
    private double eur=0.0;

    @Column(name = "PLN")
    private double pln=0.0;

    @OneToOne
    @MapsId
    @JoinColumn(name = "username")
    private User user;

    public void setUser(User user)
    {
        this.user = user;
    }

    public double getBtc() {
        return btc;
    }

    public void setBtc(double btc) {
        this.btc = btc;
    }

    public double getEth() {
        return eth;
    }

    public void setEth(double eth) {
        this.eth = eth;
    }

    public double getDoge() {
        return doge;
    }

    public void setDoge(double doge) {
        this.doge = doge;
    }

    public double getUsd() {
        return usd;
    }

    public void setUsd(double usd) {
        this.usd = usd;
    }

    public double getEur() {
        return eur;
    }

    public void setEur(double eur) {
        this.eur = eur;
    }

    public double getPln() {
        return pln;
    }

    public void setPln(double pln) {
        this.pln = pln;
    }

}

