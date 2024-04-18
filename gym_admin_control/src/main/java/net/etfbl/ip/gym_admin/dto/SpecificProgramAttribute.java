package net.etfbl.ip.gym_admin.dto;

import java.io.Serializable;

public class SpecificProgramAttribute implements Serializable {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
    private String attributeName;
    private Integer programTypeId; // Reference to FitnessProgramType

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public Integer getProgramType() {
        return programTypeId;
    }

    public void setProgramType(Integer programTypeId) {
        this.programTypeId = programTypeId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : (id.hashCode() + attributeName.hashCode() + programTypeId.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        SpecificProgramAttribute other = (SpecificProgramAttribute) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ID: ").append(this.id).append("\n");
        sb.append("Attribute Name: ").append(this.attributeName).append("\n");
        sb.append("Program Type ID: ").append(this.programTypeId);
        return sb.toString();
    }
}

