package com.piece_framework.piece_ide.test;


import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.search.IJavaSearchConstants;
import org.eclipse.jdt.core.search.TypeNameRequestor;
import org.eclipse.jdt.core.search.SearchEngine;
import org.eclipse.jdt.core.search.SearchPattern;

/**
 * ���j�b�g�e�X�g�p�v���W�F�N�g�쐬�N���X.
 * 
 * @author Hideharu Matsufuji
 * @version 0.1.0
 * @since 0.1.0
 */
public class TestProject {
    private IProject project;
    
    /**
     * �R���X�g���N�^.
     * �e�X�g�v���W�F�N�g���쐬����B
     * 
     * @throws CoreException �����^�C���R�A��O
     */
    public TestProject() throws CoreException {
        IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
        project = root.getProject("TestProject");
        project.create(null);
        project.open(null);
        
    }
    
    /**
     * �v���W�F�N�g��Ԃ�.
     * 
     * @return �v���W�F�N�g
     */
    public IProject getProject() {
        return project;
    }
    
    /**
     * �t�H���_���쐬����.
     * 
     * @param folderName �t�H���_��
     * @return �쐬����Folder�I�u�W�F�N�g
     * @throws CoreException �����^�C���R�A��O
     */
    public IFolder createFolder(String folderName) throws CoreException { 
        IFolder folder = project.getFolder("src");
        folder.create(false, true, null);
        
        return folder;
    }
    
    /**
     * �t�@�C�����쐬����.
     * 
     * @param folder �t�@�C�����쐬����t�H���_
     * @param fileName �t�@�C����
     * @param source �t�@�C�����e
     * @return �쐬����File�I�u�W�F�N�g
     * @throws CoreException �����^�C���R�A��O
     */
    public IFile createFile(IFolder folder, 
                             String fileName, 
                             String source) throws CoreException {
        
        IFile file = folder.getFile(new Path(fileName));
        if (!file.exists()) {
            InputStream is = new ByteArrayInputStream(source.getBytes());
            file.create(is, false, null);
        }
        
        return file;
    }

    /**
     * �I���������s��.
     * �쐬�����e�X�g�p�v���W�F�N�g���폜����B
     * 
     * @throws CoreException �����^�C���R�A��O
     */
    public void dispose() throws CoreException {
        waitForIndexer();
        project.delete(true, true, null);
    }
    
    
    /**
     * �C���f�b�N�X�����̏I����ҋ@����.
     * Java�����͐��m�Ō����I�ɍs�����߂ɃC���f�b�N�X���g�p���Ă���B
     * �v���W�F�N�g�폜�ɔ����C���f�b�N�X�̊�������邽�߁A�C���f�b
     * �N�X�̊���U�肪�I����Ă���v���W�F�N�g�̍폜���s���B
     * 
     * @throws JavaModelException Java���f����O
     */
    private void waitForIndexer() throws JavaModelException {
        
        new SearchEngine().searchAllTypeNames(
            null, 
            null, 
            SearchPattern.R_EXACT_MATCH, 
            IJavaSearchConstants.CLASS, 
            SearchEngine.createJavaSearchScope(new IJavaElement[0]), 
            new TypeNameRequestor() {
                public void acceptType(
                            int modifiers, 
                            char[] packageName, 
                            char[] simpleTypeName, 
                            char[][] enclosingTypeNames, 
                            String path) {
                }
            }, 
            IJavaSearchConstants.WAIT_UNTIL_READY_TO_SEARCH, 
            null);
    }
}
