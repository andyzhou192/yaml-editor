package com.piece_framework.piece_ide.yamleditor.editors;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextDoubleClickStrategy;
import org.eclipse.jface.text.ITextViewer;

//import org.eclipse.jface.text.*;

/**
 * YAML �G�f�B�^�[�̃_�u���N���b�N������N���X.
 * �_�u���N���b�N���̓�������肷��B
 * 
 * @author Hideharu Matsufuji
 * @version 0.1.0
 * @since 0.1.0
 * @see org.eclipse.jface.text.ITextDoubleClickStrategy
 * 
 */
public class YAMLDoubleClickStrategy implements ITextDoubleClickStrategy {
    private ITextViewer fText;

    /**
     * �_�u���N���b�N�C�x���g����.
     * �P��P�ʂőI����Ԃɂ���B
     * 
     * @param part �Ώۃr���[
     * @see org.eclipse.jface.text.ITextDoubleClickStrategy
     *          #doubleClicked(org.eclipse.jface.text.ITextViewer)
     */
    public void doubleClicked(ITextViewer part) {
        int pos = part.getSelectedRange().x;

        if (pos < 0) {
            return;
        }
        
        fText = part;
        
        selectWord(pos);
    }
    
    /**
     * �P���I����Ԃɂ���.
     * 
     * @param caretPos ���݂̃J�[�\���ʒu
     * @return �I����Ԃɂ����ꍇ��true
     */
    protected boolean selectWord(int caretPos) {

        IDocument doc = fText.getDocument();
        int startPos, endPos;

        try {

            int pos = caretPos;
            char c;

            while (pos >= 0) {
                c = doc.getChar(pos);
                if (!Character.isJavaIdentifierPart(c)) {
                    break;
                }
                --pos;
            }

            startPos = pos;

            pos = caretPos;
            int length = doc.getLength();

            while (pos < length) {
                c = doc.getChar(pos);
                if (!Character.isJavaIdentifierPart(c)) {
                    break;
                }
                ++pos;
            }

            endPos = pos;
            if (startPos != endPos) {
                selectRange(startPos, endPos);
            }
            return true;

        } catch (BadLocationException x) {
        }

        return false;
    }
    
    /**
     * �w�肳�ꂽ�͈͂�I����Ԃɂ���.
     * 
     * @param startPos �J�n�ʒu
     * @param stopPos �I���ʒu
     */
    private void selectRange(int startPos, int stopPos) {
        int offset = startPos + 1;
        int length = stopPos - offset;
        fText.setSelectedRange(offset, length);
    }
}