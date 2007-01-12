package com.piece_framework.piece_ide.yamleditor;

import java.util.ArrayList;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.dialogs.NewFolderDialog;
import org.eclipse.ui.views.navigator.ResourceSorter;

public class FolderSelectionDialog extends ElementTreeSelectionDialog implements
        ISelectionChangedListener {

    private Button fNewFolderButton;
    private IContainer fSelectedContainer;

    public FolderSelectionDialog(Shell parent, ILabelProvider labelProvider, ITreeContentProvider contentProvider) {
        super(parent, labelProvider, contentProvider);
        setSorter(new ResourceSorter(ResourceSorter.NAME));
    }

     
    
    
    @Override
    protected Control createDialogArea(Composite parent) {
        Composite result= (Composite)super.createDialogArea(parent);
        
        getTreeViewer().addSelectionChangedListener(this);
        
        Button button = new Button(result, SWT.PUSH);
        button.setText("新規フォルダーの作成(&N)..."); 
        button.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent event) {
                newFolderButtonPressed();
            }
        });
        button.setFont(parent.getFont());
        fNewFolderButton= button;
        
        applyDialogFont(result);

        //PlatformUI.getWorkbench().getHelpSystem().setHelp(parent, IJavaHelpContextIds.BP_SELECT_DEFAULT_OUTPUT_FOLDER_DIALOG);
        
        return result;
    }

    private void updateNewFolderButtonState() {
        IStructuredSelection selection= (IStructuredSelection) getTreeViewer().getSelection();
        fSelectedContainer= null;
        if (selection.size() == 1) {
            Object first= selection.getFirstElement();
            if (first instanceof IContainer) {
                fSelectedContainer= (IContainer) first;
            }
        }
        fNewFolderButton.setEnabled(fSelectedContainer != null);
    }   
    
    protected void newFolderButtonPressed() {
        
        NewFolderDialog dialog= new NewFolderDialog(getShell(), fSelectedContainer) {
            protected Control createContents(Composite parent) {
                //PlatformUI.getWorkbench().getHelpSystem().setHelp(parent, IJavaHelpContextIds.BP_CREATE_NEW_FOLDER);
                return super.createContents(parent);
            }
        };
        if (dialog.open() == Window.OK) {
            TreeViewer treeViewer= getTreeViewer();
            treeViewer.refresh(fSelectedContainer);
            Object createdFolder= dialog.getResult()[0];
            treeViewer.reveal(createdFolder);
            treeViewer.setSelection(new StructuredSelection(createdFolder));
        }
    }
    

    public void selectionChanged(SelectionChangedEvent event) {
        updateNewFolderButtonState();

    }

}