// -----BEGIN DISCLAIMER-----
/*******************************************************************************
 * Copyright (c) 2011 JCrypTool Team and Contributors
 *
 * All rights reserved. This program and the accompanying materials are made available under the terms of the Eclipse
 * Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
// -----END DISCLAIMER-----
package org.jcryptool.games.numbershark.dialogs;

import javax.inject.Inject;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.e4.core.di.annotations.Creatable;
import org.eclipse.e4.core.services.log.Logger;
import org.eclipse.e4.tools.services.IResourcePool;
import org.eclipse.e4.tools.services.Translation;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Slider;
import org.eclipse.swt.widgets.Spinner;

@Creatable
public class NewGameDialog extends TitleAreaDialog {
	
	@Inject
	IResourcePool resources;
	
	@Inject
	Logger logger;
	
	@Inject
	@Translation
	Messages messages;
	
    private int numberOfFields = 40;;

    public NewGameDialog(Shell shell) {
        super(shell);
        setShellStyle(SWT.TITLE | SWT.APPLICATION_MODAL);
    }

    @Override
    protected Control createDialogArea(Composite parent) {
        setTitle(messages.NewGameDialog_0);
        setMessage(messages.NewGameDialog_1);
        try {
			setTitleImage(resources.getImage("/icons/new_game.png"));
		} catch (CoreException e1) {
			logger.error(e1);
		} //$NON-NLS-1$

        Composite area = (Composite) super.createDialogArea(parent);

        Group maximumNumberGroup = new Group(area, SWT.NONE);
        maximumNumberGroup.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, true, false));
        maximumNumberGroup.setLayout(new GridLayout(2, false));
        maximumNumberGroup.setText(messages.NewGameDialog_2);

        final Slider numSlider = new Slider(maximumNumberGroup, SWT.NONE);
        numSlider.setValues(numberOfFields, 1, 1024, 7, 1, 10);
        numSlider.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true));

        final Spinner numSpinner = new Spinner(maximumNumberGroup, SWT.BORDER);
        numSpinner.setValues(numberOfFields, 1, 1024, 0, 1, 10);

        numSpinner.addModifyListener(new ModifyListener() {
            public void modifyText(ModifyEvent e) {
                numSlider.setSelection(numSpinner.getSelection());
                numberOfFields = numSlider.getSelection();
            }

        });

        numSlider.addListener(SWT.Selection, new Listener() {
            public void handleEvent(Event e) {
                numSpinner.setSelection(numSlider.getSelection());
                numberOfFields = numSlider.getSelection();
            }
        });

        return area;
    }

    @Override
    protected Point getInitialSize() {
        return new Point(425, 250);
    }

    @Override
    protected void configureShell(Shell newShell) {
        super.configureShell(newShell);
        newShell.setText(messages.NewGameDialog_4);
    }

    /**
     * Create contents of the button bar.
     *
     * @param parent
     */
    @Override
    protected void createButtonsForButtonBar(Composite parent) {
        createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
        createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
    }

    public int getNumberOfFields() {
        return numberOfFields;
    }

}
