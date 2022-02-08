package org.springframework.samples.petclinic.prac;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.samples.petclinic.owner.Pet;

public class Owner {

	private Set<Pet> pets;

	public Set<Pet> getPetsInternal() {
		if (this.pets == null) {
			this.pets = new HashSet<>();
		}
		return pets;
	}

	public List<Pet> getPets() {
		List<Pet> sortedPets = new ArrayList<>(getPetsInternal());
		PropertyComparator.sort(sortedPets, new MutableSortDefinition("name", true, true));
		return Collections.unmodifiableList(sortedPets);
	}



//	public void setPets(Set<Pet> pets) {
//		this.pets = pets;
//	}

	protected void setPetsInternal(Set<Pet> pets) {
		this.pets = pets;
	}

//	public void addPet(Pet pet) {
//		if (pet.isNew()) {
//			getPetsInternal().add(pet);
//		}
//
//		pet.setOwner(this);
//	}

	public Pet getPet(String name) {
		return getPet(name, false);
	}

	public Pet getPet(String name, boolean ignoreNew) {
		name = name.toLowerCase();
		for (Pet pet : getPetsInternal()) {
			if (!ignoreNew || !pet.isNew()) {
				String compName = pet.getName();
				compName = compName.toLowerCase();
				if (compName.equals(name)) {
					return pet;
				}
			}
		}
		return null;
	}





}
