/**
 * @copyright
 * ====================================================================
 * Copyright (c) 2006 Xiaoniu.org.  All rights reserved.
 *
 * This software is licensed as described in the file LICENSE, which
 * you should have received as part of this distribution.  The terms
 * are also available at http://code.google.com/p/suafe/.
 * If newer versions of this license are posted there, you may use a
 * newer version instead, at your option.
 *
 * This software consists of voluntary contributions made by many
 * individuals.  For exact contribution history, see the revision
 * history and logs, available at http://code.google.com/p/suafe/.
 * ====================================================================
 * @endcopyright
 */
package org.suafe.core.impl;

import org.suafe.core.AuthzAccessRule;
import org.suafe.core.AuthzPath;
import org.suafe.core.AuthzPermissionable;
import org.suafe.core.enums.AuthzAccessLevel;

import com.google.common.base.Preconditions;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Ordering;

/**
 * Authz access rule object implementation.
 * 
 * @since 2.0
 */
public class AuthzAccessRuleImpl implements AuthzAccessRule {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 893630563253107467L;

	/** The access level. */
	private final AuthzAccessLevel accessLevel;

	/** The path. */
	private final AuthzPath path;

	/** The group. */
	private final AuthzPermissionable permissionable;

	/**
	 * Instantiates a new authz access rule.
	 * 
	 * @param path the path
	 * @param permissionable the permissionable object
	 * @param accessLevel the access level
	 */
	protected AuthzAccessRuleImpl(final AuthzPath path, final AuthzPermissionable permissionable,
			final AuthzAccessLevel accessLevel) {
		super();

		Preconditions.checkNotNull(path, "Path is null");
		Preconditions.checkNotNull(permissionable, "Permissionable is null");
		Preconditions.checkNotNull(accessLevel, "Access level is null");

		this.path = path;
		this.permissionable = permissionable;
		this.accessLevel = accessLevel;
	}

	/**
	 * Compares this object with the provided AuthzAccessRule object.
	 * 
	 * @param authzAccessRule AuthzAccessRule to compare
	 * @return Returns 0 if rules are equal, less than 0 if this rule is less than the other or greater than 0 if this
	 *         rule is greater
	 */
	@Override
	public int compareTo(final AuthzAccessRule authzAccessRule) {
		final ComparisonChain comparisonChain = ComparisonChain.start();

		comparisonChain.compare(this.path, authzAccessRule.getPath());
		comparisonChain.compare(this.permissionable, authzAccessRule.getPermissionable(), Ordering.natural()
				.nullsLast());
		comparisonChain.compare(this.accessLevel, authzAccessRule.getAccessLevel());

		return comparisonChain.result();
	}

	/*
	 * (non-Javadoc)
	 * @see org.suafe.core.impl.AuthzAccessRuleIF#getAccessLevel()
	 */
	@Override
	public AuthzAccessLevel getAccessLevel() {
		return accessLevel;
	}

	/*
	 * (non-Javadoc)
	 * @see org.suafe.core.impl.AuthzAccessRuleIF#getPath()
	 */
	@Override
	public AuthzPath getPath() {
		return path;
	}

	@Override
	public AuthzPermissionable getPermissionable() {
		return permissionable;
	}
}