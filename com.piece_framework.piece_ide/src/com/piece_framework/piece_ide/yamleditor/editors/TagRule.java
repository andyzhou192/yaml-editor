package com.piece_framework.piece_ide.yamleditor.editors;

import org.eclipse.jface.text.rules.ICharacterScanner;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.MultiLineRule;


/**
 * YAML �^�O���[��.
 * �^�O���[�����`����B
 * TODO:�^�O���[���̌�����(���������^�O�łȂ��H)
 * 
 * @author Hideharu Matsufuji
 * @version 0.2.0
 * @since 0.2.0
 * @see org.eclipse.jface.text.rules.MultiLineRule
 * 
 */
public class TagRule extends MultiLineRule {

    /**
     * �R���X�g���N�^.
     * 
     * @param token �^�O���[���Ŏg�p����g�[�N��
     */
    public TagRule(IToken token) {
        super("<", ">", token);
    }
    
    /**
     * �^�O���[���ɍ��v���邩���o����.
     * 
     * @param scanner �L�����N�^�X�L���i�[
     * @param sequence ���o�ΏۃL�����N�^
     * @param eofAllowed EOF ����
     * @return �^�O�ł����true��Ԃ��B
     * @see org.eclipse.jface.text.rules.PatternRule
     *          #sequenceDetected(
     *              org.eclipse.jface.text.rules.ICharacterScanner, 
     *              char[], 
     *              boolean)
     *              
     */
    protected boolean sequenceDetected(
        ICharacterScanner scanner,
        char[] sequence,
        boolean eofAllowed) {
        
        int c = scanner.read();
        if (sequence[0] == '<') {
            if (c == '?') {
                // processing instruction - abort
                scanner.unread();
                return false;
            }
            if (c == '!') {
                scanner.unread();
                // comment - abort
                return false;
            }
        } else if (sequence[0] == '>') {
            scanner.unread();
        }
        return super.sequenceDetected(scanner, sequence, eofAllowed);
    }
}
