package org.suafe.core;

import java.util.Collection;
import java.util.Collections;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.suafe.core.exceptions.AuthzGroupMemberAlreadyExistsException;
import org.suafe.core.exceptions.AuthzNotGroupMemberException;

/**
 * Authz group object.
 * 
 * @since 2.0
 */
public final class AuthzGroup extends AuthzGroupMember implements
        Comparable<AuthzGroup> {
    /** Logger handle. */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(AuthzGroup.class);

    /** Serialization ID. */
    private static final long serialVersionUID = 7033919638521713150L;

    /** Collection of members. */
    private final Vector<AuthzGroupMember> members = new Vector<AuthzGroupMember>();

    /** Name of this group. */
    private final String name;

    /**
     * Constructor.
     * 
     * @param name User name
     */
    public AuthzGroup(final String name) {
        super();

        this.name = name;
    }

    /**
     * Adds a group member.
     * 
     * @param member AuthzGroupMember member to add
     * @return True if member added
     * @throws AuthzGroupMemberAlreadyExistsException If group member already
     *         exists
     */
    public boolean addMember(final AuthzGroupMember member)
            throws AuthzGroupMemberAlreadyExistsException {
        LOGGER.debug("addMember() entered. member={}", member);

        if (member == null) {
            LOGGER.error("addMember() member is null");

            throw new NullPointerException("Member is null");
        }

        if (members.contains(member)) {
            LOGGER.error("addMember() group member already exists");

            throw new AuthzGroupMemberAlreadyExistsException();
        }

        return members.add(member);

        // TODO Sort members
    }

    /**
     * Compares this object with the provided AuthzGroup object.
     * 
     * @param authzGroup AuthzGroup to compare
     * @return Returns 0 if groups are equal, less than 0 if this group is less
     *         than the other or greater than 0 if this group is greater
     */
    @Override
    public int compareTo(final AuthzGroup authzGroup) {
        final String myName = StringUtils.trimToEmpty(name);
        final String otherName = StringUtils.trimToEmpty(authzGroup.getName());

        return myName.compareTo(otherName);
    }

    /**
     * Compares this object with the provided AuthzGroup object for equality.
     * 
     * @param object Object to compare
     * @return True if this object matches the provided object, otherwise false
     */
    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (getClass() != object.getClass()) {
            return false;
        }
        final AuthzGroup other = (AuthzGroup) object;
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        }
        else if (!name.equals(other.name)) {
            return false;
        }
        return true;
    }

    /**
     * Returns an immutable collection of AuthzGroupMember objects.
     * 
     * @return Immutable collection of AuthzGroupMember objects
     */
    public Collection<AuthzGroupMember> getMembers() {
        return Collections.unmodifiableCollection(members);
    }

    /**
     * Gets the name of the group.
     * 
     * @return Name of group
     */
    public String getName() {
        return name;
    }

    /**
     * Calculates hashCode value of this group.
     * 
     * @return Hashcode of this object
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    /**
     * Remove group member.
     * 
     * @param member Member to remove
     * @return True if member removed
     * @throws AuthzNotGroupMemberException If provided member object is not a
     *         member of this group.
     */
    public boolean removeMember(final AuthzGroupMember member)
            throws AuthzNotGroupMemberException {
        LOGGER.debug("removeMember() entered. member={}", member);

        if (member == null) {
            LOGGER.error("removeMember() member is null");

            throw new NullPointerException("Member is null");
        }

        if (!members.contains(member)) {
            LOGGER.error("removeMember() member is not a member of this group");

            throw new AuthzNotGroupMemberException();
        }

        return members.remove(member);
    }

    /**
     * Creates a string representation of this group.
     * 
     * @return String representation of this group
     */
    @Override
    public String toString() {
        final ToStringBuilder toStringBuilder = new ToStringBuilder(this);

        toStringBuilder.append("name", name);

        return toStringBuilder.toString();
    }
}
