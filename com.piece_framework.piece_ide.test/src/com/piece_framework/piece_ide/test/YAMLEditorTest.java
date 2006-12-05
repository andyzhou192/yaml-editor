package com.piece_framework.piece_ide.test;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;

import junit.framework.TestCase;

/**
 * YAML�G�f�B�^�[�e�X�g�N���X.
 * 
 * @author Hideharu Matsufuji
 * @version 0.1.0
 * @since 0.1.0
 */
public class YAMLEditorTest extends TestCase {
    
    private TestProject testProject;

    /**
     * ���j�b�g�e�X�g����������.
     * 
     * @throws Exception ��ʗ�O 
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        testProject = new TestProject();
        
    }

    /**
     * ���j�b�g�e�X�g�I������.
     * 
     * @throws Exception ��ʗ�O
     * @see junit.framework.TestCase#tearDown()
     */
    protected void tearDown() throws Exception {
        testProject.dispose();
    }
    
    /**
     * YAML�G�f�B�^�[�N���e�X�g.
     * 
     * @throws CoreException �����^�C���R�A��O
     */
    public void testOpenYAMLFile() throws CoreException {
         
        IFile file = null;
        try {
            IFolder folder = testProject.createFolder("config");
            file = testProject.createFile(folder, "test.yaml", "--- YAML%1.1");
            
        } catch (CoreException e) {
            e.printStackTrace();
            fail();
        }
        
        IWorkbenchPage page = getPage();
        IEditorPart part = IDE.openEditor(page, file);
        
        assertTrue(part.toString().indexOf("YAMLEditor") > 0);
    }
    
    /**
     * �A�N�e�B�u�y�[�W��Ԃ�.
     * 
     * @return �A�N�e�B�u�y�[�W
     */
    private IWorkbenchPage getPage() {
        IWorkbench workbench = PlatformUI.getWorkbench();
        IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
        
        return window.getActivePage();
    }
    
}
