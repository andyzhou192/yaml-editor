package com.piece_framework.piece_ide.yamleditor.editors;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.DefaultLineTracker;
import org.eclipse.jface.text.DocumentCommand;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ILineTracker;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.source.IVerticalRuler;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.swt.widgets.Composite;

/**
 * YAML �r���[�A�[.
 * YAML �h�L�������g��\������r���[�A�[�N���X�B<br>
 * �^�u���X�y�[�X�ɕϊ�����B<br>
 * 
 * @author Hideharu Matsufuji
 * @version 0.1.0
 * @since 0.1.0
 * @see org.eclipse.jface.text.rules.SourceViewer
 * 
 */
public class YAMLViewer extends SourceViewer {
    
    // �f�t�H���g�^�u�T�C�Y
    private static final int DEFAULT_TABSIZE = 4;
    
    private int tabSize;
    private IDocument document;
    
    /**
     * �R���X�g���N�^.
     * 
     * @param parent �e�R���g���[��
     * @param ruler �������[���[
     * @param styles �X�^�C��
     */
    public YAMLViewer(Composite parent, IVerticalRuler ruler, int styles) {
        super(parent, ruler, styles);
        
        document = null;
        tabSize = DEFAULT_TABSIZE;
    }
    
    /**
     * �h�L�������g���Z�b�g����.
     * 
     * @param doc �h�L�������g
     * @see org.eclipse.jface.text.source.SourceViewer
     *          #setDocument(org.eclipse.jface.text.IDocument)
     */
    public void setDocument(IDocument doc) {
        document = doc;
    }
    
    /**
     * �^�u�T�C�Y���Z�b�g����.
     * 
     * @param size �^�u�T�C�Y
     */
    public void setTabSize(int size) {
        tabSize = size;
    }
    
    /**
     * �h�L�������g���J�X�^�}�C�Y����.
     * YAML �r���[�A�[�ł̓^�u���X�y�[�X�ɕϊ�����B
     * 
     * @param command �h�L�������g�R�}���h
     * @see org.eclipse.jface.text.TextViewer
     *          #customizeDocumentCommand(
     *              org.eclipse.jface.text.DocumentCommand)
     */
    protected void customizeDocumentCommand(DocumentCommand command) {
        String text = command.text;
        if (text == null || document == null) {
            return;
        }
        
        // �f�t�H���g�̃��C���g���b�J�[���g�p
        ILineTracker lineTracker = new DefaultLineTracker();
        
        int index = text.indexOf('\t');
        if (index > -1) {

            StringBuffer buffer = new StringBuffer();

            // ���C���g���b�J�[���g�p���ďC�����ꂽ�s�����擾
            // (�ʏ��1�s)
            lineTracker.set(command.text);
            int lines = lineTracker.getNumberOfLines();
            
            try {

                for (int i = 0; i < lines; i++) {
                    // 1�s���̕�������擾
                    int offset = lineTracker.getLineOffset(i);
                    int endOffset = offset + lineTracker.getLineLength(i);
                    String line = text.substring(offset, endOffset);
                    
                    // �C���ʒu���擾
                    int position = 0;
                    if (i == 0) {
                        IRegion firstLine = 
                            document.getLineInformationOfOffset(command.offset);
                        position = command.offset - firstLine.getOffset();
                    }
                    
                    // 1�s�̕�����𕶎��P�ʂŃ`�F�b�N
                    for (int j = 0; j < line.length(); j++) {
                        char c = line.charAt(j);
                        if (c == '\t') {
                            // �^�u���X�y�[�X�ϊ�
                            position += insertTabString(buffer, position);
                        } else {
                            buffer.append(c);
                            position++;
                        }
                    }

                }
                command.text = buffer.toString();

            } catch (BadLocationException x) {
            }
        }
    }
    
    /**
     * �^�u���X�y�[�X�ɕϊ�����.
     * �^�u�͏�Ɏw�肳�ꂽ�^�u�T�C�Y���A�Ԃ��󂯂���킯�ł͂Ȃ��B<br>
     * �Ⴆ�΁A�}���ʒu��6�����ڂŃ^�u�T�C�Y��4�̏ꍇ�A�}�������󔒂́A<br>
     * 4�ł͂Ȃ�2�łȂ���΂Ȃ炢�B<br>
     * 
     * @param buffer ������o�b�t�@
     * @param offsetInLine �X�y�[�X�}���ʒu
     * @return �}�����ꂽ�X�y�[�X��
     */
    private int insertTabString(StringBuffer buffer, int offsetInLine) {

        if (tabSize == 0) {
            return 0;
        }
        
        // �^�u�͏�Ɏw�肳�ꂽ�^�u�T�C�Y���A�Ԃ��󂯂���킯�ł͂Ȃ��B
        // �Ⴆ�΁A�}���ʒu��6�����ڂŃ^�u�T�C�Y��4�̏ꍇ�A�}�������󔒂́A
        // 4�ł͂Ȃ�2�łȂ���΂Ȃ炢�B
        int remainder = offsetInLine % tabSize;
        remainder = tabSize - remainder;
        for (int i = 0; i < remainder; i++) {
            buffer.append(' ');
        }
        
        return remainder;
    }
    
    
    
}
