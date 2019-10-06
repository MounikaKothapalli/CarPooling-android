package com.project.mounika.shareyourride;


public class ListItem
{
   String lstItmLabel;
    boolean ifValidationNeeded;
    public ListItem(String lstItmLabel, boolean ifValidationNeeded) {
        this.lstItmLabel = lstItmLabel;
        this.ifValidationNeeded = ifValidationNeeded;
    }

    public void setLstItmLabel(String lstItmLabel) {
        this.lstItmLabel = lstItmLabel;
    }

    public void setIfValidationNeeded(boolean ifValidationNeeded) {
        this.ifValidationNeeded = ifValidationNeeded;
    }

    public  String getLstItmLabel() {
        return lstItmLabel;
    }

    public boolean getIfValidationNeeded() {
        return ifValidationNeeded;
    }

    public ListItem()
    {
        lstItmLabel = null;
        ifValidationNeeded = false;

    }
}
