package com.piece_framework.piece_ide.yamleditor.editors;

import org.eclipse.ui.editors.text.TextEditor;


/**
 * YAML �e�L�X�g�G�f�B�^�[.
 * �J���[�}�l�[�W���[�̐����A�R���t�B�M�����[�V�����A
 * �h�L�������g�v���o�C�_�[�̐ݒ���s���B
 * 
 * @author Hideharu Matsufuji
 * @version 0.2.0
 * @since 0.2.0
 * @see org.eclipse.ui.editors.text.TextEditor
 * 
 */
public class YAMLEditor extends TextEditor {

    // �J���[�}�l�[�W���[
    private ColorManager colorManager;

    /**
     * �R���X�g���N�^.
     * �J���[�}�l�[�W���[�̐����A�R���t�B�M�����[�V�����A
     * �h�L�������g�v���o�C�_�[�̐ݒ���s���B
     */
    public YAMLEditor() {
        super();
        colorManager = new ColorManager();
        setSourceViewerConfiguration(new YAMLConfiguration(colorManager));
        setDocumentProvider(new YAMLDocumentProvider());
    }
    
    /**
     * �J���[�}�l�[�W���[�̏I���������s��.
     * 
     * @see org.eclipse.ui.editors.text.TextEditor#dispose()
     */
    public void dispose() {
        colorManager.dispose();
        super.dispose();
    }

}
