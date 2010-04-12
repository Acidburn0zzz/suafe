package org.suafe.core;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.suafe.core.exceptions.AuthzAlreadyMemberOfGroupException;
import org.suafe.core.exceptions.AuthzNotMemberOfGroupException;

/**
 * Authz group member object. Instances of this class or its subclasses are eligible to be a member of a group.
 * 
 * @since 2.0
 */
public abstract class AuthzGroupMember implements Serializable {
	private static final Logger logger = LoggerFactory.getLogger(AuthzGroupMember.class);

	private static final long serialVersionUID = -4348242302006857451L;

	private final Vector<AuthzGroup> groups = new Vector<AuthzGroup>();

	/**
	 * Adds group to collection of groups.
	 * 
	 * @param group Group to add to collection
	 * @return True if group added
	 * @throws AuthzAlreadyMemberOfGroupException If this object is already a member of the group
	 */
	public boolean addGroup(final AuthzGroup group) throws AuthzAlreadyMemberOfGroupException {
		logger.debug("addGroup() entered. group={}", group);

		if (group == null) {
			logger.error("addGroup() group is null");

			throw new NullPointerException("Group is null");
		}

		if (groups.contains(group)) {
			logger.error("addGroup() already a member of group");

			throw new AuthzAlreadyMemberOfGroupException();
		}

		return groups.add(group);
	}

	/**
	 * Returns an immutable collection of AuthzGroup objects.
	 * 
	 * @return Immutable collection of AuthzGroup object.
	 */
	public Collection<AuthzGroup> getGroups() {
		return Collections.unmodifiableCollection(groups);
	}

	/**
	 * Remove a group from the collection of groups.
	 * 
	 * @param group Group to remove from the collection
	 * @return True if group is removed
	 * @throws AuthzNotMemberOfGroupException
	 */
	public boolean removeGroup(final AuthzGroup group) throws AuthzNotMemberOfGroupException {
		logger.debug("removeGroup() entered. group={}", group);

		if (group == null) {
			logger.error("removeGroup() group is null");

			throw new NullPointerException("Group is null");
		}

		if (!groups.contains(group)) {
			logger.error("removeGroup() this object is not a member of the group");

			throw new AuthzNotMemberOfGroupException();
		}

		return groups.remove(group);
	}
}