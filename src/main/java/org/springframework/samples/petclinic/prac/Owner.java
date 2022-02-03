package org.springframework.samples.petclinic.prac;

import java.util.Set;
import org.springframework.samples.petclinic.owner.Pet;

public class Owner {

	private Set<Pet> pets;

	public Set<Pet> getPets() {
		return pets;
	}

	public void setPets(Set<Pet> pets) {
		this.pets = pets;
	}
}
