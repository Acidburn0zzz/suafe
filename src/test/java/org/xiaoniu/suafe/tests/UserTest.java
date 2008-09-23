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
package org.xiaoniu.suafe.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.xiaoniu.suafe.beans.AccessRule;
import org.xiaoniu.suafe.beans.Group;
import org.xiaoniu.suafe.beans.User;

/**
 * @author Shaun Johnson
 */
public class UserTest {

	private String userName;

	@Before
	public void before() throws Exception {
		userName = "TestUserName";
	}

	/*
	 * Class under test for void User()
	 */
	@Test
	public void testUser() {
		User user = new User();

		assertNull("Name should be null", user.getName());
		assertNotNull("AccessRules should not be null", user.getAccessRules());
		assertTrue("AccessRules should be empty",
				user.getAccessRules().size() == 0);
		assertNotNull("Groups should not be null", user.getGroups());
		assertTrue("Groups should be empty", user.getGroups().size() == 0);
	}

	/*
	 * Class under test for void User(String)
	 */
	@Test
	public void testUserString() {
		User user = new User(userName);

		assertNotNull("Name should not be null", user.getName());
		assertEquals("Name should match", userName, user.getName());

		assertNotNull("AccessRules should not be null", user.getAccessRules());
		assertTrue("AccessRules should be empty",
				user.getAccessRules().size() == 0);
		assertNotNull("Groups should not be null", user.getGroups());
		assertTrue("Groups should be empty", user.getGroups().size() == 0);
	}

	/*
	 * Class under test for String toString()
	 */
	@Test
	public void testToString() {
		User user = new User();

		assertNotNull("toString() should be not be null", user.toString());
		assertEquals("toString() should be empty string", "", user.toString());

		user = new User(userName);

		assertNotNull("toString() should be not be null", user.toString());
		assertEquals("toString() should match userName", userName, user
				.toString());
	}

	@Test
	public void testGetName() {
		User user = new User();

		assertNull("getName() should be null", user.getName());

		user = new User(userName);

		assertNotNull("getName() should not be null", user.getName());
		assertEquals("getName() should match", userName, user.getName());
	}

	@Test
	public void testSetName() {
		User user = new User();

		assertNull("getName() should be null", user.getName());

		user.setName(userName);

		assertNotNull("getName() should not be null", user.getName());
		assertEquals("getName() should match", userName, user.getName());
	}

	@Test
	public void testGetGroups() {
		User user = new User();

		assertNotNull("Groups should not be null", user.getGroups());
		assertTrue("Groups should be empty", user.getGroups().size() == 0);
	}

	@Test
	public void testAddGroup() {
		User user = new User();

		assertNotNull("Groups should not be null", user.getGroups());
		assertTrue("Groups should be empty", user.getGroups().size() == 0);

		Group group = new Group();
		user.addGroup(group);

		assertNotNull("Groups should not be null", user.getGroups());
		assertTrue("Groups should have one element",
				user.getGroups().size() == 1);
		assertTrue("Groups should contain only the one object", user
				.getGroups().get(0) == group);

		assertNotNull("Group user memebers should not be null", user
				.getGroups().get(0).getUserMembers());
		assertTrue("Group user memebers should contain only one user", user
				.getGroups().get(0).getUserMembers().size() == 1);
		assertTrue("Group user memeber should match added user", user
				.getGroups().get(0).getUserMembers().get(0) == user);
	}

	@Test
	public void testGetAccessRules() {
		User user = new User();

		assertNotNull("AccessRules should not be null", user.getAccessRules());
		assertTrue("AccessRules should be empty",
				user.getAccessRules().size() == 0);
	}

	@Test
	public void testAddAccessRule() {
		User user = new User();

		assertNotNull("AccessRules should not be null", user.getAccessRules());
		assertTrue("AccessRules should be empty",
				user.getAccessRules().size() == 0);

		AccessRule rule = new AccessRule();
		user.addAccessRule(rule);

		assertNotNull("AccessRules should not be null", user.getAccessRules());
		assertTrue("AccessRules should have one element", user.getAccessRules()
				.size() == 1);
	}

	@Test
	public void testCompareTo() {
		User userA = new User();
		User userB = new User();

		assertTrue("User A should match user B", userA.compareTo(userB) == 0);
		assertTrue("User B should match user A", userB.compareTo(userA) == 0);

		userA = new User("user");
		userB = new User("user");

		assertTrue("User A should match user B", userA.compareTo(userB) == 0);
		assertTrue("User B should match user A", userB.compareTo(userA) == 0);

		userA = new User("userA");
		userB = new User(null);

		assertTrue("User A should not match user B",
				userA.compareTo(userB) != 0);
		assertTrue("User B should not match user A",
				userB.compareTo(userA) != 0);

		userA = new User("userA");
		userB = new User("userB");

		assertTrue("User A should not match user B",
				userA.compareTo(userB) != 0);
		assertTrue("User B should not match user A",
				userB.compareTo(userA) != 0);
		assertTrue("User A should be lower than user B",
				userA.compareTo(userB) < 0);
		assertTrue("User B should be higher than user A", userB
				.compareTo(userA) > 0);
	}
}
