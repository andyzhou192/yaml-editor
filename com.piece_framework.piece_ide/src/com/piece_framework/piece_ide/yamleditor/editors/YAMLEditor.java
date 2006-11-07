package com.piece_framework.piece_ide.yamleditor.editors;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.IVerticalRuler;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.texteditor.AbstractDecoratedTextEditorPreferenceConstants;
import org.eclipse.ui.texteditor.IDocumentProvider;

/**
 * YAML �e�L�X�g�G�f�B�^�[.
 * �J���[�}�l�[�W���[�̐����A�h�L�������g�E�v���o�C�_�A
 * �\�[�X�r���[�R���t�B�M�����[�V�����̐ݒ���s���B
 * 
 * @author Hideharu Matsufuji
 * @version 0.1.0
 * @since 0.1.0
 * @see org.eclipse.ui.editors.text.TextEditor
 * 
 */
public class YAMLEditor extends TextEditor {
    
    /**
     * �G�f�B�^�[�̏������������s��.
     * �J���[�}�l�[�W���[�̐����A�h�L�������g�E�v���o�C�_�A
     * �\�[�X�E�r���[���E�R���t�B�M�����[�V�����̐ݒ���s���B
     * 
     * @see org.eclipse.ui.editors.text.TextEditor#initializeEditor()
     */
    protected void initializeEditor() {
        super.initializeEditor();
        
        setDocumentProvider(new YAMLDocumentProvider());
        setSourceViewerConfiguration(new YAMLConfiguration());
        
    }
    
    /**
     * �r���[�A�[���쐬����.
     * YAML �r���[�A�[���쐬���āA�Ԃ��B
     * 
     * @param parent �e�R���g���[��
     * @param ruler �������[���[
     * @param styles �X�^�C��
     * @return �r���[�A�[
     * @see org.eclipse.ui.texteditor.AbstractDecoratedTextEditor
     *          #createSourceViewer(
     *              org.eclipse.swt.widgets.Composite, 
     *              org.eclipse.jface.text.source.IVerticalRuler, int)
     */
    protected ISourceViewer createSourceViewer(
                                Composite parent, 
                                IVerticalRuler ruler, 
                                int styles) {
        
        YAMLViewer viewer = new YAMLViewer(parent, ruler, styles);
        
        // �h�L�������g��ݒ�
        viewer.setDocument(getDocumentProvider().getDocument(getEditorInput()));
        
        // �^�u�T�C�Y��ݒ�
            // ��ʁ��G�f�B�^�[���e�L�X�g�G�f�B�^�[�́u�\������^�u�T�C�Y�v
            // ����^�u�T�C�Y���擾
        int tabSize = getPreferenceStore().getInt(
            AbstractDecoratedTextEditorPreferenceConstants.EDITOR_TAB_WIDTH);
        viewer.setTabSize(tabSize);
        
        return viewer;
    }

    /**
     * �e�L�X�g�ۑ����������s�Ȃ�.
     * 
     * @param progressMonitor �v���O���X���j�^
     */
    public void doSave(IProgressMonitor progressMonitor) {
        super.doSave(progressMonitor);
        
        try {

            //�ҏW���e�L�X�g���擾
            IDocumentProvider provider = getDocumentProvider();
            IDocument document = provider.getDocument(getEditorInput());
            String yamlStr = document.get();
            System.out.println(yamlStr.substring(0, 2));
            
            
            //�o���f�[�V�������s
            //YAMLValidater.validation(yamlStr, yamlStr);
        } catch (Exception e) {
            // TODO �����������ꂽ catch �u���b�N
            e.printStackTrace();
        }
    }

    /**
     * �e�L�X�g�ۑ����������s�Ȃ�.
     */
    public void doSaveAs() {
        super.doSaveAs();
    }
}
