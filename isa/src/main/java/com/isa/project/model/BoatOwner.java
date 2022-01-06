package com.isa.project.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class BoatOwner extends AppUser {

    @OneToMany(mappedBy = "boatOwner", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Boat> boats = new HashSet<Boat>();

    public BoatOwner() {
    }

    public BoatOwner(long id, String email, String password, String name, String surname, String address, String city, String country, String telephone) {
        super(id, email, password, name, surname, address, city, country, telephone);
        this.boats = new HashSet<Boat>();
    }

    public Set<Boat> getBoats() {
        return boats;
    }

    public void setBoats(Set<Boat> boats) {
        this.boats = boats;
    }

    public void addBoat(Boat boat) {
        boats.add(boat);
        boat.setBoatOwner(this);
    }

    public void removeBoat(Boat boat) {
        boats.remove(boat);
        boat.setBoatOwner(null);
    }
}
