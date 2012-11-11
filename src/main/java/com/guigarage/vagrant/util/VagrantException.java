package com.guigarage.vagrant.util;

/**
 * Default Exception for the vagrant-binding. Any Exception in Ruby / Vagrant is wrapped in a VagrantException
 * @author hendrikebbers
 *
 */
public class VagrantException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public VagrantException() {
        super();
    }

    public VagrantException(String message) {
        super(message);
    }

    public VagrantException(String message, Throwable cause) {
        super(message, cause);
    }

    public VagrantException(Throwable cause) {
        super(cause);
    }
	
}
