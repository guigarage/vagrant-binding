package com.guigarage.vagrant.configuration;

/**
 * Created with IntelliJ IDEA.
 * User: kpelykh
 * Date: 8/1/13
 * Time: 1:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class VagrantSyncFolder {

    private String source;
    private String destination;
    private boolean disabled;

    public VagrantSyncFolder(String source, String destination, boolean disabled) {
        this.source = source;
        this.destination = destination;
        this.disabled = disabled;
    }

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }

    public boolean isDisabled() {
        return disabled;
    }
}
