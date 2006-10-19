package com.piece_framework.piece_ide.yamleditor.editors;

import org.eclipse.jface.text.rules.IWhitespaceDetector;

/**
 * YAML �X�y�[�X���o�N���X.
 * 
 * @author Hideharu Matsufuji
 * @version 0.2.0
 * @since 0.2.0
 * @see org.eclipse.jface.text.rules.IWhitespaceDetector
 * 
 */
public class YAMLWhitespaceDetector implements IWhitespaceDetector {

    /**
     * �X�y�[�X�E�^�u�E���s(CR,LF)���𔻒肷��.
     * 
     * @param c ����Ώە���
     * @return �w�肳�ꂽ�������X�y�[�X�E�^�u�E���s(CR,LF)�̂����ꂩ�ł����true
     * @see org.eclipse.jface.text.rules.IWhitespaceDetector#isWhitespace(char)
     */
    public boolean isWhitespace(char c) {
        return (c == ' ' || c == '\t' || c == '\n' || c == '\r');
    }
}
