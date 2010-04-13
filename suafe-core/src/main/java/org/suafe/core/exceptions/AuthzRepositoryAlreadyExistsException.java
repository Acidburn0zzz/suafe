package org.suafe.core.exceptions;

import org.suafe.core.utilities.AuthzErrorResourceKey;

/**
 * Repository already exists exception.
 * 
 * @since 2.0
 */
public class AuthzRepositoryAlreadyExistsException extends AuthzException {
    /** Serialization ID. */
    private static final long serialVersionUID = 1538917503012273189L;

    /**
     * Create exception with message text loaded using messageKey.
     */
    public AuthzRepositoryAlreadyExistsException() {
        super(AuthzErrorResourceKey.REPOSITORY_ALREADY_EXISTS);
    }
}
