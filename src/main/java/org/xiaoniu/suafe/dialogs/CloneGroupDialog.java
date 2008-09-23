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
package org.xiaoniu.suafe.dialogs;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import org.xiaoniu.suafe.Constants;
import org.xiaoniu.suafe.UserPreferences;
import org.xiaoniu.suafe.beans.Document;
import org.xiaoniu.suafe.beans.Group;
import org.xiaoniu.suafe.beans.Message;
import org.xiaoniu.suafe.exceptions.ApplicationException;
import org.xiaoniu.suafe.resources.ResourceUtil;
import org.xiaoniu.suafe.validators.Validator;
import java.awt.Dimension;

/**
 * Dialog that allows a user to clone a group.
 * 
 * @author Shaun Johnson
 */
public class CloneGroupDialog extends ParentDialog implements ActionListener {

	/**
	 * Serial ID.
	 */
	private static final long serialVersionUID = 4712164932165750402L;
	
	private Message message = null;
	
	private JPanel jContentPane = null;
	
	private Group group = null;
	
	private JPanel buttonPanel = null;
	
	private JButton saveButton = null;
	
	private JButton cancelButton = null;
	
	private JPanel buttonSubPanel = null;
	
	private JPanel formPanel = null;
	
	private JPanel formSubPanel = null;
	
	private JTextField groupNameText = null;
	
	/**
	 * Default constructor.
	 */
	public CloneGroupDialog(Group group, Message message) {
		super();
		
		this.message = message;
		this.message.setState(Message.CANCEL);
		this.group = group;
		
		initialize();
	}
	/**
	 * This method initializes this
	 */
	private void initialize() {
		this.setResizable(false);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle(ResourceUtil.getString("clonegroup.title"));
		this.setContentPane(getJContentPane());
		
		getRootPane().setDefaultButton(saveButton);
		
		this.pack();
		this.setModal(true);
	}
	/**
	 * This method initializes jContentPane.
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if(jContentPane == null) {
			jContentPane = new javax.swing.JPanel(new BorderLayout());
			jContentPane.add(new JLabel(ResourceUtil.getFormattedString("clonegroup.instructions", group.getName())), java.awt.BorderLayout.NORTH);
			jContentPane.add(getFormPanel(), java.awt.BorderLayout.CENTER);
			jContentPane.add(getButtonPanel(), java.awt.BorderLayout.SOUTH);
		}
		
		return jContentPane;
	}
	
	/**
	 * This method initializes buttonPanel.	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getButtonPanel() {
		if (buttonPanel == null) {
			buttonPanel = new JPanel(new GridLayout(1, 1));
			buttonPanel.add(getButtonSubPanel());
		}
		
		return buttonPanel;
	}
	
	/**
	 * This method initializes saveButton.	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getSaveButton() {
		if (saveButton == null) {
			saveButton = new JButton();
			saveButton.addActionListener(this);
			saveButton.setActionCommand(Constants.SAVE_ACTION);
			saveButton.setText(ResourceUtil.getString("button.save"));
		}
		
		return saveButton;
	}
	
	/**
	 * This method initializes cancelButton.	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getCancelButton() {
		if (cancelButton == null) {
			cancelButton = new JButton();
			cancelButton.addActionListener(this);
			cancelButton.setActionCommand(Constants.CANCEL_ACTION);
			cancelButton.setText(ResourceUtil.getString("button.cancel"));
		}
		
		return cancelButton;
	}
	
	/**
	 * This method initializes buttonSubPanel.	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getButtonSubPanel() {
		if (buttonSubPanel == null) {
			buttonSubPanel = new JPanel();
			buttonSubPanel.add(getSaveButton());
			buttonSubPanel.add(getCancelButton());
		}
		
		return buttonSubPanel;
	}
	
	/**
	 * This method initializes formPanel.	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getFormPanel() {
		if (formPanel == null) {
			formPanel = new JPanel(new FlowLayout());
			formPanel.add(getFormSubPanel());
		}
		
		return formPanel;
	}
	
	/**
	 * This method initializes formSubPanel.	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getFormSubPanel() {
		if (formSubPanel == null) {
			formSubPanel = new JPanel(new FlowLayout());
			formSubPanel.add(new JLabel(ResourceUtil.getString("clonegroup.groupname")));
			formSubPanel.add(getGroupNameText());
		}
		
		return formSubPanel;
	}
	
	/**
	 * This method initializes groupNameText.	
	 * 	
	 * @return javax.swing.JTextField	
	 */    
	private JTextField getGroupNameText() {
		if (groupNameText == null) {
			groupNameText = new JTextField();
			groupNameText.setText(group.getName());
			groupNameText.setPreferredSize(new Dimension(340, 20));
			groupNameText.setFont(UserPreferences.getUserFont());
		}
		
		return groupNameText;
	}
	
	/**
	 * ActionPerformed event handler.
	 * 
	 * @param event ActionEvent object.
	 */
	public void actionPerformed(ActionEvent event) {
		if (event.getActionCommand().equals(Constants.SAVE_ACTION)) {
			try {
				String groupName = getGroupNameText().getText();
				
				Validator.validateNotEmptyString(ResourceUtil.getString("addgroup.groupname"), groupName);
				Validator.validateGroupName(groupName);
				
				Group existingGroup = Document.findGroup(groupName);
				
				if (existingGroup == null || existingGroup == group) {				
					message.setUserObject(Document.cloneGroup(group, groupName));
					message.setState(Message.SUCCESS);
					dispose();
				}
				else {
					displayError(ResourceUtil.getFormattedString("clonegroup.error.groupalreadyexists", groupName));
				}	
			}
			catch (ApplicationException ex) {
				displayError(ex.getMessage());
			}
		}
		else if (event.getActionCommand().equals(Constants.CANCEL_ACTION)) {
			message.setState(Message.CANCEL);
			dispose();
		}
	}
}
