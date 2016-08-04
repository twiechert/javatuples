/*
 * =============================================================================
 * 
 *   Copyright (c) 2010, The JAVATUPLES team (http://www.javatuples.org)
 * 
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 * 
 * =============================================================================
 */
package org.javatuples;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;


/**
 * <p>
 * Abstract base class for all tuple classes.
 * </p> 
 * 
 * @since 1.0
 * 
 * @author Daniel Fern&aacute;ndez
 *
 */
public abstract class Tuple implements Serializable, Comparable<Tuple> {

    private static final long serialVersionUID = 5431085632328343101L;








    public abstract Object[] objectValues();




    protected Tuple(final Object... values) {
        super();
    }


    /**
     * <p>
     * Return the size of the tuple.
     * </p>
     * 
     * @return the size of the tuple.
     */
    @JsonIgnore
    public abstract int getSize();


    /**
     * <p>
     * Get the value at a specific position in the tuple. This method
     * has to return object, so using it you will lose the type-safety you
     * get with the <tt>getValueX()</tt> methods.
     * </p>
     *
     * @param pos the position of the value to be retrieved.
     * @return the value
     */
    @JsonIgnore
    public final Object getValue(final int pos) {
        if (pos >= getSize()) {
            throw new IllegalArgumentException(
                    "Cannot retrieve position " + pos + " in " + this.getClass().getSimpleName() +
                    ". Positions for this class start with 0 and end with " + (getSize() - 1));
        }
        return this.objectValues()[pos];
    }
    

    

    
    @Override
    public  String toString() {
        String str= "(";
        int i = this.objectValues().length;
        for(Object val: this.objectValues()) {
            str+=val.toString();
            if(i!= 1) {
                str+=", ";
            }
            i--;
        }
        return str+")";
    }
    
    
    public final boolean contains(final Object value) {
        for (final Object val : this.objectValues()) {
            if (val == null) {
                if (value == null) {
                    return true;
                }
            } else {
                if (val.equals(value)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    
    public final boolean containsAll(final Collection<?> collection) {
        for (final Object value : collection) {
            if (!contains(value)) {
                return false;
            }
        }
        return true;
    }
    
    
    public final boolean containsAll(final Object... values) {
        if (values == null) {
            throw new IllegalArgumentException("Values array cannot be null");
        }
        for (final Object value : values) {
            if (!contains(value)) {
                return false;
            }
        }
        return true;
    }

    
    

    

    
    

    
    
    
    public final Object[] toArray() {
        return this.objectValues().clone();
    }
    
    

    @Override
    public final int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((this.objectValues() == null) ? 0 : Arrays.hashCode(this.objectValues()));
        return result;
    }



    @Override
    public final boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Tuple other = (Tuple) obj;
        return Arrays.equals(this.objectValues(), other.objectValues());
    }



    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public int compareTo(final Tuple o) {
        
        final int tLen = this.objectValues().length;
        final Object[] oValues = o.objectValues();
        final int oLen = oValues.length;
        
        for (int i = 0; i < tLen && i < oLen; i++) {
            
            final Comparable tElement = (Comparable)this.objectValues()[i];
            final Comparable oElement = (Comparable)oValues[i];
            
            final int comparison = tElement.compareTo(oElement);
            if (comparison != 0) {
                return comparison;
            }
            
        }
        
        return (Integer.valueOf(tLen)).compareTo(Integer.valueOf(oLen));
        
    }
    
    
    
}
