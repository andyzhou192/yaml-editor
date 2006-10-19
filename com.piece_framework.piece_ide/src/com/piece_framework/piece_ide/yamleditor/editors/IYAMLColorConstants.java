package com.piece_framework.piece_ide.yamleditor.editors;

import org.eclipse.swt.graphics.RGB;

/**
 * YAML �J���[��`�C���^�[�t�F�C�X.
 * 
 * @author Hideharu Matsufuji
 * @version 0.2.0
 * @since 0.2.0
 * @see org.eclipse.jface.text.rules.MultiLineRule
 * 
 */
public interface IYAMLColorConstants {
    
    /** YAML �R�����g�F. */
    RGB YAML_COMMENT = new RGB(128, 128, 128);
    /** YAML �}�b�s���O(�L�[)�F. */
    RGB YAML_MAPPING_KEY = new RGB(0, 0, 128);
    /** YAML �}�b�s���O(�l)�F. */
    RGB YAML_MAPPING_VAL = new RGB(0, 128, 0);
    
    /** �^�O�F. */
    //RGB TAG = new RGB(0, 0, 128);
    
    /** �v���Z�X�����F. */
    RGB PROC_INSTR = new RGB(128, 128, 128);
    /** ������F. */
    RGB STRING = new RGB(0, 128, 0);
    /** �f�t�H���g�F. */
    RGB DEFAULT = new RGB(0, 0, 0);
    
    
    /** 
     * �_�~�[���\�b�h.
     * TODO:YAML �J���[��`�C���^�[�t�F�C�X�������B
     */
    void dummy();
}
