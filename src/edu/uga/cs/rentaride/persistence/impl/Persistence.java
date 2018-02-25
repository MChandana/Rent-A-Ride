package edu.uga.cs.rentaride.persistence.impl;

import edu.uga.cs.rentaride.persistence.Persistable;
import edu.uga.cs.rentaride.persistence.PersistenceLayer;

public class Persistence implements Persistable{

	private long ID;
	public static PersistenceLayer perLayer;
	
	public Persistence() {
		ID = -1;
	}
	
	public Persistence(long id) {
		this.ID = id;
	}
	
	public static PersistenceLayer getPersistenceLayer() {
		return perLayer;
	}
	
	public static void setPersistenceLayer(PersistenceLayer pl) {
		perLayer = pl;
	}
	
	@Override
	public long getId() {
		return ID;
	}

	@Override
	public void setId(long id) {
	ID = id;
		
	}

	@Override
	public boolean isPersistent() {
		
		return ID >= 0;
	}

}
