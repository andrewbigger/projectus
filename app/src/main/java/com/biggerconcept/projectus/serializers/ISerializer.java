package com.biggerconcept.projectus.serializers;

import java.io.IOException;

/**
 * Interface for a serialize-er.
 * 
 * @author Andrew Bigger
 */
public interface ISerializer {
    public void save() throws IOException;
}
