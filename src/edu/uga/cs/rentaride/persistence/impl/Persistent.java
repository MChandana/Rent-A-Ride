package edu.uga.cs.rentaride.persistence.impl;

import edu.uga.cs.rentaride.persistence.Persistable;
import edu.uga.cs.rentaride.persistence.PersistenceLayer;

public abstract class Persistent implements Persistable
{    
    private long id;
    private static PersistenceLayer persistencaLayer;

    public long getId()
    {
	return id;
    }

    public void setId( long id )
    {
	this.id = id;
    }
  
    public boolean isPersistent()
    {
	return id >= 0;
    }

}
